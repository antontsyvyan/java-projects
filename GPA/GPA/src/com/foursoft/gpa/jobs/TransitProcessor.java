package com.foursoft.gpa.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Custkey;
import com.foursoft.gpa.db.Defdata;
import com.foursoft.gpa.db.Femlog;
import com.foursoft.gpa.db.Femtra;
import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.db.Tariff;
import com.foursoft.gpa.db.Tarlvo;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.GpaCalculator;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class TransitProcessor extends Processors {
	
	private StringBuffer errorCollector= new StringBuffer();
	
	private Femuti femuti= new Femuti();
	private Femutu femutu= new Femutu();
	private Femlog logFile=new Femlog();
	private Custkey kustkey= new Custkey();
	private Femtra interfaceFile=new Femtra();
	private ControleBrochure cb;
	protected List<DiskErrorsModel> errorsList;
	
	private ArrayList<String> uniqueKeys;
	
	public TransitProcessor() {

	}

	@Override
	public void Process() {	
		log.info(" ==> Entering Transit processor. <==");
		
		try{
			ProcessSemaphore.femtraSemaphore.acquire();
			ProcessSemaphore.femutuSemaphore.acquire();			
			log.info(" ==> Transit processor acqured femtra and femutu semaphores<==");	
			
			//process all customers
			boolean firstTime=true;
			String previousJobNumber="";
			String currentJobNumber="";
			ArrayList<TreeMap<String, String>> tempDetails=new ArrayList<TreeMap<String, String>>();
			TreeMap<String, String> tempDetail=new TreeMap<String, String>();
			uniqueKeys=kustkey.getCustkeys(Resources.getSetting("customer.name"), Femutu.DB_FEMUTU);
			cb= new ControleBrochure(Disk.ENTREPOT);
			ArrayList<TreeMap<String, String>> details=interfaceFile.readRecordsOfStatus("","K");
			if (details != null && details.size() > 0) {
				System.out.println("Start Transit: "+ new Date());
				
				for (int i = 0; i < details.size(); i++) {
					tempDetail=details.get(i);
					currentJobNumber=tempDetail.get(Femtra.DB_FEMTRA_SHP_NO);
					if(!firstTime){				
						if(!currentJobNumber.equals(previousJobNumber)){
							//Process complete job
							processOneJob(tempDetails);
							tempDetails=new ArrayList<TreeMap<String, String>>();						
						}
						
					}else{
						firstTime=false;
					}
					
					tempDetails.add(tempDetail);
					previousJobNumber=currentJobNumber;
				}
				
				//process latest set
				if (tempDetails != null && tempDetails.size() > 0) {
					processOneJob(tempDetails);
				}
				System.out.println("End Transit: "+ new Date());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ProcessSemaphore.femtraSemaphore.release();
			ProcessSemaphore.femutuSemaphore.release();			
			log.info(" ==> Transit processor released femtra and femutu semaphores<==");	
		}

	}
	
	private boolean processOneJob(ArrayList<TreeMap<String, String>> details){
		boolean success=true;
		
		TreeMap<String, String> errDetail=new TreeMap<String, String>();
		
		String interfaceName=Thread.currentThread().getName();
		errDetail.put(Femlog.DB_FEMLOG_INTERFACE_NAME, interfaceName);
		errDetail.put(Femlog.DB_FEMLOG_NAW_CODE_CM,details.get(0).get(Femtra.DB_FEMTRA_NAW_CODE_CM));
		errDetail.put(Femlog.DB_FEMLOG_SHP_NO,details.get(0).get(Femtra.DB_FEMTRA_SHP_NO));
		errDetail.put(Femlog.DB_FEMLOG_SHP_DATE,details.get(0).get(Femtra.DB_FEMTRA_SHP_DATE));
		//Delete error log
		//logFile.deleteFemlogForShipment(errDetail);
		errorsList = new ArrayList<DiskErrorsModel>();
		for (TreeMap<String, String> detail: details) {
			
			//Retrieve company data(needs for validations)
		
			String accountId=detail.get(Femtra.DB_FEMTRA_NAW_CODE_CM);	
			String jobNo=detail.get(Femtra.DB_FEMTRA_SHP_NO);	
			int line=getInt(detail.get(Femtra.DB_FEMTRA_SHP_LINE_NO));	
			String serachField=accountId+"/"+jobNo+"/"+line;
			
			//Delete error log
			errDetail.put(Femlog.DB_FEMLOG_SHP_LINE_NO,""+line);			
			logFile.deleteFemlogForLine(errDetail);
			
			readCompanyData(accountId);

			if(!isValid(detail,serachField)){
				success=false;
			}else{
				TreeMap<String, String>femutuDetail=completeRecord(femutu.formatRecord(mapToFemutu(detail)));	
				
				if(isValidBrochure(femutu.mapToDisk(femutuDetail),serachField)){
					success=femutu.insertUpdateFemutu(femutuDetail);
					interfaceFile.updateStatus(detail.get(Femtra.DB_FEMTRA_NAW_CODE_CM),detail.get(Femtra.DB_FEMTRA_SHP_NO),line,"X");
				}
			}
			
			
			if(errorsList!=null && errorsList.size()>0){
				//save errors/warnings
				for(DiskErrorsModel dem:errorsList){
									
					errDetail.put(Femlog.DB_FEMLOG_NAW_CODE_CM,accountId);
					errDetail.put(Femlog.DB_FEMLOG_SHP_NO,jobNo);
					errDetail.put(Femlog.DB_FEMLOG_ERR_TYPE,dem.getType());
					errDetail.put(Femlog.DB_FEMLOG_FIELD,dem.getField());
					errDetail.put(Femlog.DB_FEMLOG_BLOCK,dem.getBlock());
					errDetail.put(Femlog.DB_FEMLOG_ERR_TXT,dem.getMessage());
					logFile.insertUpdate(errDetail);
				}
			}
		}
		
		return success;
	}
	
	private boolean isValid(TreeMap<String, String> detail,String serachField) {
		boolean valid = true;
		errorCollector = new StringBuffer();
		String currentValue="";	
		
		//Assuming shipment number is filled
		//Check if record already exists
		String feedSystem=getFeedingSystem();
		String type=OUTBOUND;
		String accountId=detail.get(Femtra.DB_FEMTRA_NAW_CODE_CM).trim();
		String jobNo=detail.get(Femtra.DB_FEMTRA_SHP_NO);
		int line=Integer.parseInt(detail.get(Femtra.DB_FEMTRA_SHP_LINE_NO));
		TreeMap<String, String> tmpFemutu =  femutu.readFemutuRecord(feedSystem, type, jobNo,line);
		if(tmpFemutu!=null && tmpFemutu.size()>0){
			//Check the status
			if(tmpFemutu.get(Femutu.DB_FEMUTU_STAT).equals(FINAL)){
				//Write log if the record processed by the final run of GPA (FEMUTU_STAT=3)	
				this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","STAT","Job: "+jobNo+"/"+line+" is already processed by the final run of GPA."));
				valid = false;
			}
		
		}
		//If the record processed by the final run of GPA no need to execute other validations.
		if(valid){
			// Check mandatory fields
			for (int i = 0; i < Femtra.MANDATORY_FIELDS.length; i++) {
				currentValue=detail.get(Femtra.MANDATORY_FIELDS[i]);
				if ( currentValue== null|| currentValue.trim().equals("")) {
					valid = false;
					this.errorsList.add(new DiskErrorsModel(0,serachField,ControleBrochure.TYPE_ERROR,"","MANDATORY","Field " + Femtra.MANDATORY_FIELDS[i] + " is mandatory.\r\n"));
				}
			}
			
			/*
			//Check if inbound order exist in FEMUTI
			String jobNumber=detail.get(Femtra.DB_FEMTRA_ARRIV_NO);
			int jobLineNumber=getInt(detail.get(Femtra.DB_FEMTRA_ARRIV_LINE_NO));		
			femutiDetail=femuti.readFemutiRecord(getFeedingSystem(),INBOUND,jobNumber, jobLineNumber);
			if(femutiDetail==null || femutiDetail.size()<1){
				//Add error message if doesn't exist
				valid = false;
				errorCollector.append("Inbound job : " + jobNumber+"/" +jobLineNumber+ " doesn`t exist in FEMUTI\r\n");			
			}
			*/
			
			//Other checks
			
			//Document issue date should be less or equals to shipment date		
			if(!isValidDates(detail,Femtra.DB_FEMTRA_DOC_ISSUE_DATE,Femtra.DB_FEMTRA_SHP_DATE)){
				valid = false;
			}				
			
		}		
		return valid;
	}
	
	private TreeMap<String, String> mapToFemutu(TreeMap<String, String> det){
		
		TreeMap<String, String> femutuDetail=femutu.getEmptyDetailRecord();
	
		String accountId=det.get(Femtra.DB_FEMTRA_NAW_CODE_CM).trim();
				
		//Retrieve inbound job
		readInboundJob(det);
		//Retrieve default outbound data
		readDefaultData(det);
		
		femutuDetail.put(Femutu.DB_FEED_SYS_FEMUTU, getFeedingSystem());
		femutuDetail.put(Femutu.DB_FEMUTU_IN_OUT_BOUND, OUTBOUND);
		femutuDetail.put(Femutu.DB_FEMUTU_JOB_NO, det.get(Femtra.DB_FEMTRA_SHP_NO));
		femutuDetail.put(Femutu.DB_FEMUTU_LINE_NO, det.get(Femtra.DB_FEMTRA_SHP_LINE_NO));
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE, "100100");
		femutuDetail.put(Femutu.DB_FEMUTU_A3,"");
		femutuDetail.put(Femutu.DB_FEMUTU_BON_WRH_TYPE, getWarenhouseType());
		femutuDetail.put(Femutu.DB_FEMUTU_STAT_GDS, getDefaultFieldValue(Defdata.DB_DEFDATA_STAT_CUST_GDS));
		femutuDetail.put(Femutu.DB_FEMUTU_NAW_NO, getCompanyNumber());
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_TYPE, det.get(Femtra.DB_FEMTRA_DOC_TYPE));
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_NO, getDocumentNumber(det.get(Femtra.DB_FEMTRA_MRN),det.get(Femtra.DB_FEMTRA_DOC_TYPE)));
		//femutuDetail.put(Femutu.DB_FEMUTU_PLACE_ISSUE_DOC, getPlaceIssuing(det.get(Femtra.DB_FEMTRA_MRN),det.get(Femtra.DB_FEMTRA_DOC_TYPE),det.get(Femtra.DB_FEMTRA_DOC_ISSUE_PLACE)));
		femutuDetail.put(Femutu.DB_FEMUTU_PLACE_ISSUE_DOC, det.get(Femtra.DB_FEMTRA_DOC_ISSUE_PLACE));		
		femutuDetail.put(Femutu.DB_FEMUTU_DT_ISSUE_DOC,det.get(Femtra.DB_FEMTRA_DOC_ISSUE_DATE));
		femutuDetail.put(Femutu.DB_FEMUTU_TG_IND, det.get(Femtra.DB_FEMTRA_TA_INDICATOR));
		femutuDetail.put(Femutu.DB_FEMUTU_PERIOD, getPeriod(det.get(Femtra.DB_FEMTRA_SHP_DATE)));
		femutuDetail.put(Femutu.DB_FEMUTU_STAT, PRELIMINARY);
		femutuDetail.put(Femutu.DB_FEMUTU_MOD, WAREHOUSE);
		//femutuDetail.put(Femutu.DB_CT_CODE_ORG, getCountry(det.get(Femtra.DB_CT_CODE_ORG), Femuti.DB_CT_CODE_ORG));
		femutuDetail.put(Femutu.DB_CT_CODE_PROV, getCountry(det.get(Femtra.DB_CT_CODE_PROV), Femuti.DB_CT_CODE_PROV));
		femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE2, "");
		femutuDetail.put(Femutu.DB_FEMUTU_AUTH_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CERTIFICATE_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_SCO_IND, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PRELIM_IND, "");		
		femutuDetail.put(Femutu.DB_NAW_CODE_CONS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CONS_NAME, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CONS_CITY, "");
		femutuDetail.put(Femutu.DB_CONS_VAT_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ART_NO, det.get(Femtra.DB_FEMTRA_ART_NO));
		femutuDetail.put(Femutu.DB_FEMUTU_ART_DESC, det.get(Femtra.DB_FEMTRA_ART_DESC));
		femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CODE,"");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_VAL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_CERTIFICATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INVNO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_LN_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_KIND_CODE_D11, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NO_PCS_D12, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LOC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_SPARE_FLD_D15, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NO_ADD_UNITS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_UNITS,"");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_LINE, "");
		double invoiceValue=getInvoiceValue(det.get(Femtra.DB_FEMTRA_OUT_GNG_VAL),det.get(Femtra.DB_FEMTRA_QUAN));
		String currencyCode=(det.get(Femtra.DB_CURR_CODE_FEMTRA).equals(""))?femutiDetail.get(Femuti.DB_CURR_CODE_FEMUTI):det.get(Femtra.DB_CURR_CODE_FEMTRA);		
		double rate=getRate(currencyCode,det.get(Femtra.DB_FEMTRA_SHP_DATE));
		femutuDetail.put(Femutu.DB_FEMUTU_INV_VAL,""+invoiceValue);
		femutuDetail.put(Femutu.DB_FEMUTU_EX_RATE,""+rate);
		femutuDetail.put(Femutu.DB_CURR_CODE_FEMUTU, currencyCode);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_VAL_EUR, ""+(invoiceValue*rate));
		femutuDetail.put(Femutu.DB_NAW_CODE_CM, accountId);
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_TYPE,"");
		femutuDetail.put(Femutu.DB_FEMUTU_STAN_PERC_DUTIES, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_DATE, det.get(Femtra.DB_FEMTRA_SHP_DATE));
		femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT_K13, calculateNetWeight(det.get(Femtra.DB_FEMTRA_NT_WGT),det.get(Femtra.DB_FEMTRA_QUAN)));		
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_COM_PREF, "");
		femutuDetail.put(Femutu.DB_FEMUTU_BOX37, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_RELATION, "");
		femutuDetail.put(Femutu.DB_FEMUTU_RESTRIC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LIC_FEE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CALC_METHOD, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAL_DET, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CONT_REQ, "");
		femutuDetail.put(Femutu.DB_MOT_CODE_FEMUTU, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CONT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_SPEC_INDI, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE_K7, AF);
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_NO, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK15, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK16, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK17, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK18, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK19, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK20, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK21, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK22, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK23, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_VERIFY_BY_CUST, "N");
		femutuDetail.put(Femutu.DB_FEMUTU_INCO_TERMS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DELIV_PLACE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_FRT_AMT_EUR, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INS_AMT_EUR, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_DED_EUR, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_DUT_PERC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_AMT_RELIEF, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUT_SPEC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUTIES, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TYPE_OF_CHRGS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_MANU, "");
		femutuDetail.put(Femutu.DB_FEMUTU_AMT_OF_CHRGS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_AGRI_LEV, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_AMT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_CONV_VAL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_PREF_CODE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G3, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G3, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G5, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G5, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G7, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G7, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXT_PERC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ACT_ALCH_STE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ALC_FREE_LIT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LTS_OIL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PER_KG, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PURE_ALC_LTS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TRAN_DOC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PACK_UNIT, "STKS");
		femutuDetail.put(Femutu.DB_FEMUTU_NO_OF_PACK, ""+getInt(det.get(Femtra.DB_FEMTRA_QUAN)));
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_LINE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_QUAN1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_QUAN2, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_NUM, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_DATE, "");
		femutuDetail.put(Femutu.DB_CT_CODE_DEST, det.get(Femtra.DB_CT_CODE_DEST));
		femutuDetail.put(Femutu.DB_FEMUTU_BASE_CODE2, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE2, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE3, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE4, "");
		femutuDetail.put(Femutu.DB_FEMUTU_BASE_CODE1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_MTH_PAY, "");
		femutuDetail.put(Femutu.DB_FEMTU_STAT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_MEM_STATE, "");
		
		//trim all fields
		for (String key : femutuDetail.keySet()) {
			femutuDetail.put(key, femutuDetail.get(key).trim());
		}
		
				
		return femutuDetail;
	}
	
	private TreeMap<String, String> completeRecord(TreeMap<String, String>detail){
		
		String accountId=detail.get(Femutu.DB_NAW_CODE_CM);
		TreeMap<String, String>tariffRecord=getTariff(detail.get(Femutu.DB_FEMUTU_ART_NO), accountId, detail.get(Femutu.DB_CT_CODE_PROV),detail.get(Femutu.DB_FEMUTU_TRANS_DATE));
		
		detail.put(Femutu.DB_FEMUTU_TARIFF_CODE,tariffRecord.get(Tariff.DB_TARIFF_CODE));
		detail.put(Femutu.DB_FEMUTU_ADD_UNITS,tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS));
		detail.put(Femutu.DB_FEMUTU_STAN_PERC_DUTIES, tariffRecord.get(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS));
		
		//prepare key field
		String femutuKey="";
		if(uniqueKeys!=null && uniqueKeys.size()>0){
			for (String key : uniqueKeys) {
				femutuKey+=detail.get(key);		
			}
		}
		detail.put(Femutu.DB_FEMUTU_UNIQUE_KEY, femutuKey);
		return detail;
	}
	
	
	private boolean isValidBrochure(TreeMap<String, String>diskDetail,String searchField){
		
		boolean retCode=false;
		
		cb.setDetail(diskDetail);
		cb.setLine(0);
		
		cb.setSearchField(searchField);
		
		try {
			if(cb.isValidDetail()){
				retCode= true;
			}
		} catch (Exception e) {
			retCode= false;
			e.printStackTrace();
		}
		
		if(cb.getErrorsList()!=null && cb.getErrorsList().size()>0){
			errorsList.addAll(cb.getErrorsList());
		}
		
		return retCode;
	}
			
	private void readInboundJob(TreeMap<String, String> det){
		
		femutiDetail=new TreeMap<String, String> ();
		String jobNumber=det.get(Femtra.DB_FEMTRA_ARRIV_NO);
		int jobLineNumber=getInt(det.get(Femtra.DB_FEMTRA_ARRIV_LINE_NO));		
		femutiDetail=femuti.readFemutiRecord(getFeedingSystem(),INBOUND, jobNumber, jobLineNumber);
		
	}
					
	private double getInvoiceValue(String value, String qty){
		
		double invoiceValueOut=0;
		double quantityOut=getDouble(qty);
		if(value.equals("")|| value.equals("0")){			
			double invoiceValueInb=getDouble(femutiDetail.get(Femuti.DB_FEMUTI_INV_VALUE));
			double quantityInb=getDouble(femutiDetail.get(Femuti.DB_FEMUTI_NO_PCS));
			if(quantityInb>0){
				invoiceValueOut=quantityOut*(invoiceValueInb/quantityInb);
			}
		}else{
			invoiceValueOut=getDouble(value)*quantityOut;
		}	
				
		return GpaCalculator.round(invoiceValueOut,2);
	}
	
	private String calculateNetWeight(String weight,String numberOfPieces){
		
		int weightNum= (int)getDouble(weight)*getInt(numberOfPieces);
		
		if(weightNum==0){
			weightNum=getInt(femutiDetail.get(Femuti.DB_FEMUTI_NT_WGT))*getInt(numberOfPieces);
		}

		
		return ""+weightNum;
				
	}	
	
	private boolean isValidDates(TreeMap<String, String> detail,String name1, String name2){
			
			boolean valid=true;
			
			switch (du.getDate(detail.get(name1)).compareTo(du.getDate(detail.get(name2)))) {
			 case -1:  break;
			 case 0:   break;
			 case 1:   
				 valid = false;
				 errorCollector.append(name2+" is after "+name2);	
				 break;
			default:
				valid = false;
				errorCollector.append("Invalid results from date comparison("+name1+" "+name2+")");
			}
			
			return valid;
		}
		
	
}
