package com.foursoft.gpa.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Custkey;
import com.foursoft.gpa.db.Defdata;
import com.foursoft.gpa.db.Femimp;
import com.foursoft.gpa.db.Feminb;
import com.foursoft.gpa.db.Femlog;
import com.foursoft.gpa.db.Femtra;
import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.db.Tariff;
import com.foursoft.gpa.db.Tarlvo;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.Excise;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class ImportProcessor extends Processors {

	private Femimp interfaceFile=new Femimp();
	private Femuti femuti= new Femuti();
	private Femutu femutu= new Femutu();
	private Feminb feminb= new Feminb();
	
	private Femlog logFile=new Femlog();
	
	private Custkey kustkey= new Custkey();
	private ControleBrochure cb;
	private ArrayList<String> uniqueKeys;
	
	protected List<DiskErrorsModel> errorsList;
	
	private TreeMap<String, String> femutiDetail=new TreeMap<String, String> ();	
	private TreeMap<String, String> feminbDetail = new TreeMap<String, String> ();
	
	private boolean inboundExists=true;
	
	@Override
	public void Process() {
		log.info(" ==> Entering Import processor. <==");	
		try{
			ProcessSemaphore.femimpSemaphore.acquire();
			ProcessSemaphore.femutuSemaphore.acquire();
			log.info(" ==> Import processor acqured femimp and femutu semaphores<==");	
			//process all customers
			boolean firstTime=true;
			String previousJobNumber="";
			String currentJobNumber="";
			cb= new ControleBrochure(Disk.ENTREPOT);
			ArrayList<TreeMap<String, String>> tempDetails=new ArrayList<TreeMap<String, String>>();
			TreeMap<String, String> tempDetail=new TreeMap<String, String>();
			uniqueKeys=kustkey.getCustkeys(Resources.getSetting("customer.name"), Femutu.DB_FEMUTU);
			ArrayList<TreeMap<String, String>> details=interfaceFile.readRecordsOfStatus("",STATUS_TO_BE_PROCESSED);
			if (details != null && details.size() > 0) {
				for (int i = 0; i < details.size(); i++) {
					tempDetail=details.get(i);
					currentJobNumber=tempDetail.get(Femimp.DB_FEMIMP_SHP_NO);
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
				
			}
		
		}catch(Exception e){
			
		}finally{
			ProcessSemaphore.femimpSemaphore.release();
			ProcessSemaphore.femutuSemaphore.release();
			log.info(" ==> Import processor released femimp and femutu semaphore<==");	
		}
	}
	
	
	private boolean processOneJob(ArrayList<TreeMap<String, String>> details){
		boolean success=true;

		TreeMap<String, String> errDetail=new TreeMap<String, String>();
		
		String interfaceName=Thread.currentThread().getName();
	
		errDetail.put(Femlog.DB_FEMLOG_INTERFACE_NAME, interfaceName);
		errDetail.put(Femlog.DB_FEMLOG_NAW_CODE_CM,details.get(0).get(Femimp.DB_FEMIMP_NAW_CODE_CM));
		errDetail.put(Femlog.DB_FEMLOG_SHP_NO,details.get(0).get(Femimp.DB_FEMIMP_SHP_NO));
		errDetail.put(Femlog.DB_FEMLOG_SHP_DATE,details.get(0).get(Femimp.DB_FEMIMP_SHP_DATE));

		errorsList = new ArrayList<DiskErrorsModel>();
		for (TreeMap<String, String> detail: details) {
			
			String accountId=detail.get(Femimp.DB_FEMIMP_NAW_CODE_CM);	
			String jobNo=detail.get(Femimp.DB_FEMIMP_SHP_NO);	
			int line=getInt(detail.get(Femimp.DB_FEMIMP_SHP_LINE_NO));	
			String serachField=accountId+"/"+jobNo+"/"+line;
			
			//Delete error log
			errDetail.put(Femlog.DB_FEMLOG_SHP_LINE_NO,""+line);			
			logFile.deleteFemlogForLine(errDetail);
			
			//Retrieve company data(needs for validations)
			readCompanyData(accountId);
			
			inboundExists=true;
			if(!isValid(detail,serachField)){
				success=false;
				//break;
			}else{
				TreeMap<String, String>femutuDetail=femutu.formatRecord(mapToFemutu(detail));	
				
				if(isValidBrochure(femutu.mapToDisk(femutuDetail),serachField)){
					success=femutu.insertUpdateFemutu(femutuDetail);
					interfaceFile.updateStatus(detail.get(Femimp.DB_FEMIMP_NAW_CODE_CM),detail.get(Femimp.DB_FEMIMP_SHP_NO),line,STATUS_PROCESSED);
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
		
		String currentValue="";		
		//Assuming shipment number is filled
		//Check if record already exists
		String feedSystem=getFeedingSystem();
		
		String jobNo=detail.get(Femimp.DB_FEMIMP_SHP_NO);
		int line=Integer.parseInt(detail.get(Femimp.DB_FEMIMP_SHP_LINE_NO));
		String accountId=detail.get(Femimp.DB_FEMIMP_NAW_CODE_CM);
		
		TreeMap<String, String> tmpFemutu =  femutu.readFemutuRecord(feedSystem, OUTBOUND, jobNo,line);
		if(tmpFemutu!=null && tmpFemutu.size()>0){
			//Check the status
			if(tmpFemutu.get(Femutu.DB_FEMUTU_STAT).equals(FINAL)){
				//Write log if the record processed by the final run of GPA (FEMUTU_STAT=3)
				//errorCollector.append("Job: "+jobNo+"/"+line+" is already processed by the final run of GPA.");
				this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","STAT","Job: "+jobNo+"/"+line+" is already processed by the final run of GPA."));
				valid = false;
			}
		
		}
		
		//If the record processed by the final run of GPA no need to execute other validations.
		if(valid){
			// Check mandatory fields
			for (int i = 0; i < Femimp.MANDATORY_FIELDS.length; i++) {
				currentValue=detail.get(Femimp.MANDATORY_FIELDS[i]);
				if ( currentValue== null|| currentValue.trim().equals("")) {
					valid = false;
					//errorCollector.append("Field " + Femimp.MANDATORY_FIELDS[i] + " is mandatory.\r\n");		
					this.errorsList.add(new DiskErrorsModel(0,serachField,ControleBrochure.TYPE_ERROR,"","MANDATORY","Field " + Femtra.MANDATORY_FIELDS[i] + " is mandatory.\r\n"));
				}
			}
		}

		//Check if inbound order exist in FEMUTI
		String jobNumber=detail.get(Femimp.DB_FEMIMP_ARRIV_NO);
		int jobLineNumber=getInt(detail.get(Femimp.DB_FEMIMP_ARRIV_LINE_NO));		
		femutiDetail=femuti.readFemutiRecord(getFeedingSystem(),INBOUND,jobNumber, jobLineNumber);
		if(femutiDetail==null || femutiDetail.size()<1){
			inboundExists=false;
			//The missing link to the inbound order line is only allowed when transaction types are: VM, VN, HW, BM, BZ, CO, VD, VT, PV and OE.
			if(!Arrays.asList(ControleBrochure.MUTATIE_SOORT_WITHOUT_OUTBOUND).contains(detail.get(Femimp.DB_FEMIMP_TRANS_TYPE))){
				//Add error message
				valid = false;
				//errorCollector.append("Inbound job : " + jobNumber+"/" +jobLineNumber+ " doesn`t exist in FEMUTI\r\n");
				this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","INB","Inbound job : " + jobNumber+"/" +jobLineNumber+ " doesn`t exist in FEMUTI\r\n"));
			}
		}
		
		if(valid && !inboundExists){
			double value=Double.parseDouble(detail.get(Femimp.DB_FEMIMP_SLS_VALUE));
			if(value==0.00){
				valid=false;
				this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","VAL","The sales value is mandatory"));
			}
			
			if(detail.get(Femimp.DB_CURR_CODE_SLS_FEMIMP).trim().equals("")){
				valid=false;
				this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","CUR","The sales currency is mandatory"));
			}			
		}
		
		TreeMap<String, String> tmpFeminb=new TreeMap<String, String>();
		if(valid){
			//Read original inbound line
			tmpFeminb =  feminb.readFeminbRecord(accountId, jobNumber, jobLineNumber);
			if(tmpFeminb==null){
				valid = false;
			}
		}
			
		if(valid){
			//Other checks when inbound has found
			if(inboundExists){
				//Shipment date should be great or equals arrival date		
				if(!isValidDates(detail.get(Femimp.DB_FEMIMP_SHP_DATE),tmpFeminb.get(Feminb.DB_FEMINB_ARRV_DATE),accountId)){
					valid = false;
				}				
				//Article checks
				//TODO Article number must be linked to valid tariff code in SME	
				
				//Article number must be the same as used in inbound job line number.
				String articleNumber=detail.get(Femimp.DB_FEMIMP_ART_NO);
				if(!femutiDetail.get(Femuti.DB_FEMUTI_ART_NO).trim().toUpperCase().equals(articleNumber.toUpperCase())){
					valid = false;
					//errorCollector.append("Article number : " + articleNumber +" is not the same as used in inbound "+jobNumber+"/"+jobLineNumber +"("+femutiDetail.get(Femuti.DB_FEMUTI_ART_NO)+")");	
					this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","ART","Article number : " + articleNumber +" is not the same as used in inbound "+jobNumber+"/"+jobLineNumber +"("+femutiDetail.get(Femuti.DB_FEMUTI_ART_NO)+")"));
				}
			}else{
				
			}
		}

		
		return valid;
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
	
	private TreeMap<String, String> mapToFemutu(TreeMap<String, String> det){
		
		TreeMap<String, String> femutuDetail=femutu.getEmptyDetailRecord();	
		String accountId=det.get(Femimp.DB_FEMIMP_NAW_CODE_CM).trim();
		
		try{
			//Retrieve inbound job
			readInboundJob(det);
			//Retrieve default outbound data
			readDefaultData(det);		
			//Retrieve import license
			readImportLicense();
			
			//get quantity
			int quantity=getInt(det.get(Femimp.DB_FEMIMP_QUAN));
			String shipmentDate=det.get(Femimp.DB_FEMIMP_SHP_DATE);
			
			TreeMap<String, String>tariffRecord=getTariff(det.get(Femimp.DB_FEMIMP_ART_NO), accountId, det.get(Femimp.DB_CT_CODE_PROV),det.get(Femimp.DB_FEMIMP_SHP_DATE));
			
			femutuDetail.put(Femutu.DB_FEED_SYS_FEMUTU, getFeedingSystem());
			femutuDetail.put(Femutu.DB_FEMUTU_IN_OUT_BOUND, OUTBOUND);
			femutuDetail.put(Femutu.DB_FEMUTU_JOB_NO, det.get(Femimp.DB_FEMIMP_SHP_NO));
			femutuDetail.put(Femutu.DB_FEMUTU_LINE_NO, det.get(Femimp.DB_FEMIMP_SHP_LINE_NO));
			femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE, getTransactionCode(det.get(Femimp.DB_FEMIMP_TRANS_CODE)));
			femutuDetail.put(Femutu.DB_FEMUTU_BON_WRH_TYPE, getWarenhouseType());
			femutuDetail.put(Femutu.DB_FEMUTU_STAT_GDS, getDefaultFieldValue(Defdata.DB_DEFDATA_STAT_CUST_GDS));
			femutuDetail.put(Femutu.DB_FEMUTU_NAW_NO, getCompanyNumber());
			femutuDetail.put(Femutu.DB_FEMUTU_DOC_TYPE, det.get(Femimp.DB_FEMIMP_DOC_TYPE));
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_DOC_NO, det.get(Femimp.DB_FEMIMP_ADD_DOC_NO));
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_DOC_CODE, det.get(Femimp.DB_FEMIMP_ADD_DOC_TYPE));			
			femutuDetail.put(Femutu.DB_FEMUTU_PLACE_ISSUE_DOC,"");
			femutuDetail.put(Femutu.DB_FEMUTU_DT_ISSUE_DOC,"");
			femutuDetail.put(Femutu.DB_FEMUTU_TG_IND, "");
			femutuDetail.put(Femutu.DB_FEMUTU_PERIOD, getPeriod(shipmentDate));
			femutuDetail.put(Femutu.DB_FEMUTU_STAT, PRELIMINARY);
			femutuDetail.put(Femutu.DB_FEMUTU_MOD, WAREHOUSE);
			femutuDetail.put(Femutu.DB_CT_CODE_ORG, getCountry(det.get(Femimp.DB_CT_CODE_ORG), Femuti.DB_CT_CODE_ORG));
			femutuDetail.put(Femutu.DB_CT_CODE_PROV, getCountry(det.get(Femimp.DB_CT_CODE_PROV), Femuti.DB_CT_CODE_PROV));
			femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE1, getPreferenceCode(det.get(Femimp.DB_FEMIMP_ART_NO)));
			femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE2, getPreferenceCode(det.get(Femimp.DB_FEMIMP_ART_NO)));
			femutuDetail.put(Femutu.DB_FEMUTU_AUTH_NO, det.get(Femimp.DB_FEMIMP_AUTH_NO));
			femutuDetail.put(Femutu.DB_FEMUTU_CERTIFICATE_NO,getCertificate());
			femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC, getImportLicenseNumber());
			femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC_DATE, getImportLicenseDate());
			femutuDetail.put(Femutu.DB_FEMUTU_SCO_IND, det.get(Femimp.DB_FEMIMP_SCO_IND));
			femutuDetail.put(Femutu.DB_FEMUTU_PRELIM_IND, det.get(Femimp.DB_FEMIMP_PRELIM_IND));
			//TODO below 4 fields to be filled from Company setup
			femutuDetail.put(Femutu.DB_NAW_CODE_CONS, "");
			femutuDetail.put(Femutu.DB_FEMUTU_CONS_NAME, "");
			femutuDetail.put(Femutu.DB_FEMUTU_CONS_CITY, "");
			femutuDetail.put(Femutu.DB_CONS_VAT_NO, "");
			//
			femutuDetail.put(Femutu.DB_FEMUTU_ART_NO, det.get(Femimp.DB_FEMIMP_ART_NO));
			femutuDetail.put(Femutu.DB_FEMUTU_ART_DESC, det.get(Femimp.DB_FEMIMP_ART_DESC));
			femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CODE, tariffRecord.get(Tariff.DB_TARIFF_CODE));	
			femutuDetail.put(Femutu.DB_FEMUTU_DOC_VAL, "");
			femutuDetail.put(Femutu.DB_FEMUTU_IMP_CERTIFICATE, "");
			femutuDetail.put(Femutu.DB_FEMUTU_INVNO, "");
			femutuDetail.put(Femutu.DB_FEMUTU_INV_LN_NO,"");
			femutuDetail.put(Femutu.DB_FEMUTU_INV_DATE, femutiDetail.get(Femuti.DB_FEMUTI_INV_DATE));
			femutuDetail.put(Femutu.DB_FEMUTU_KIND_CODE_D11, "");
			femutuDetail.put(Femutu.DB_FEMUTU_NO_PCS_D12, "");
			femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT, "");
			femutuDetail.put(Femutu.DB_FEMUTU_LOC, "");
			femutuDetail.put(Femutu.DB_FEMUTU_SPARE_FLD_D15, "");
			femutuDetail.put(Femutu.DB_FEMUTU_NO_ADD_UNITS, det.get(Femimp.DB_FEMIMP_NO_ADD_UNITS));
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_UNITS, tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS));
			femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF, femutiDetail.get(Femuti.DB_FEMUTI_INVNO));
			femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_LINE, ""+getInt(femutiDetail.get(Femuti.DB_FEMUTI_INV_LINE_NO)));
			femutuDetail.put(Femutu.DB_FEMUTU_INCO_TERMS,feminbDetail.get(Feminb.DB_FEMINB_INCO_TERM));
			femutuDetail.put(Femutu.DB_FEMUTU_DELIV_PLACE, feminbDetail.get(Feminb.DB_FEMINB_DELIV_PLACE));
			
			double invoiceValue=getInvoiceValue(det.get(Femimp.DB_FEMIMP_QUAN),det.get(Femimp.DB_FEMIMP_SLS_VALUE));
			double rate=getRate(femutiDetail.get(Femuti.DB_CURR_CODE_FEMUTI),det.get(Femimp.DB_FEMIMP_SHP_DATE));
			double freightCost=getCostEur(feminbDetail.get(Feminb.DB_FEMINB_FRT_AMT));
			double insuranceCost=getCostEur(feminbDetail.get(Feminb.DB_FEMINB_INS_AMT));
			double additionalCosts=getDouble(det.get(Femimp.DB_FEMIMP_OTH_COST));
			
			femutuDetail.put(Femutu.DB_FEMUTU_INV_VAL,""+invoiceValue);
			femutuDetail.put(Femutu.DB_FEMUTU_EX_RATE,""+rate);
			femutuDetail.put(Femutu.DB_CURR_CODE_FEMUTU, getInvoiceCurrency(det.get(Femimp.DB_CURR_CODE_SLS_FEMIMP)));
			femutuDetail.put(Femutu.DB_FEMUTU_FRT_AMT_EUR, ""+freightCost);
			femutuDetail.put(Femutu.DB_FEMUTU_INS_AMT_EUR, ""+insuranceCost);
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_DED_EUR, ""+additionalCosts);
			//calculate  total customs value in Euro(E31)
			double customsValue=(invoiceValue*rate)+freightCost+insuranceCost+additionalCosts;
			femutuDetail.put(Femutu.DB_FEMUTU_CUST_VAL_EUR, ""+customsValue);
			//get percentage import duties(E32)
			double percentageImportDuties=getPercentageImportDuties();
			//get amount relief(E33)
			double amountRelief=getAmountRelief();
			//get specific customs duties(E34)
			double specificCustomsDuties=getSpecificCustomsDuties();			
			//calculate  total duties(E35) E35=E31*E32/100-E33+E34
			double totalDuties=(customsValue*percentageImportDuties/100)-amountRelief+specificCustomsDuties;		
			femutuDetail.put(Femutu.DB_FEMUTU_IMP_DUT_PERC, ""+percentageImportDuties);
			femutuDetail.put(Femutu.DB_FEMUTU_AMT_RELIEF, ""+amountRelief);
			femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUT_SPEC, ""+specificCustomsDuties);
			femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUTIES, ""+totalDuties);
			femutuDetail.put(Femutu.DB_FEMUTU_BOX37, getDefaultFieldValue(Defdata.DB_DEFDATA_BOX37));	
			femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_DATE, feminbDetail.get(Feminb.DB_FEMINB_INV_DATE));
			femutuDetail.put(Femutu.DB_FEMUTU_DOC_NO,"");		
			femutuDetail.put(Femutu.DB_FEMUTU_COM_PREF, getPreferenceCode(det.get(Femimp.DB_FEMIMP_ART_NO)));
			
			femutuDetail.put(Femutu.DB_NAW_CODE_CM, det.get(Femimp.DB_FEMIMP_NAW_CODE_CM));
			femutuDetail.put(Femutu.DB_FEMUTU_TRANS_TYPE,det.get(Femimp.DB_FEMIMP_TRANS_TYPE));
			
			femutuDetail.put(Femutu.DB_FEMUTU_RELATION, getDefaultFieldValue(Defdata.DB_DEFDATA_RELATION));
			femutuDetail.put(Femutu.DB_FEMUTU_RESTRIC, getDefaultFieldValue(Defdata.DB_DEFDATA_RESTRIC));
			femutuDetail.put(Femutu.DB_FEMUTU_LIC_FEE, getDefaultFieldValue(Defdata.DB_DEFDATA_LIC_FEE));
			femutuDetail.put(Femutu.DB_FEMUTU_CALC_METHOD, getDefaultFieldValue(Defdata.DB_DEFDATA_CALC_METHOD));
			femutuDetail.put(Femutu.DB_FEMUTU_VAL_DET, getDefaultFieldValue(Defdata.DB_DEFDATA_VAL_DET));
			femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CONT_REQ, getDefaultFieldValue(Defdata.DB_DEFDATA_TARIFF_CONT_REQ));
			femutuDetail.put(Femutu.DB_MOT_CODE_FEMUTU, getDefaultFieldValue(Defdata.DB_MOT_CODE_DEFDATA));
			femutuDetail.put(Femutu.DB_FEMUTU_CONT, getDefaultFieldValue(Defdata.DB_DEFDATA_CONT));
			
			femutuDetail.put(Femutu.DB_FEMUTU_TYPE_OF_CHRGS, getTypeOfCharges());
			femutuDetail.put(Femutu.DB_FEMUTU_MANU, "");
			femutuDetail.put(Femutu.DB_FEMUTU_AMT_OF_CHRGS, "");
			femutuDetail.put(Femutu.DB_FEMUTU_AGRI_LEV, "");
			
			femutuDetail.put(Femutu.DB_FEMUTU_VAT_AMT, "");
			femutuDetail.put(Femutu.DB_FEMUTU_VAT_CONV_VAL, "");
			femutuDetail.put(Femutu.DB_FEMUTU_VAT_PREF_CODE, "");
			
			femutuDetail.put(Femutu.DB_FEMUTU_STAN_PERC_DUTIES, tariffRecord.get(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS));
			femutuDetail.put(Femutu.DB_FEMUTU_TRANS_DATE, det.get(Femimp.DB_FEMIMP_SHP_DATE));
						
			femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT_K13, calculateNetWeight(det.get(Femimp.DB_FEMIMP_QUAN)));					
			femutuDetail.put(Femutu.DB_FEMUTU_SPEC_INDI, "");		
			femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE_K7, det.get(Femimp.DB_FEMIMP_TRANS_TYPE));
			femutuDetail.put(Femutu.DB_FEMUTU_TRANS_NO, det.get(Femimp.DB_FEMIMP_TRANS_NO));		
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK15, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK16, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK17, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK18, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK19, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK20, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK21, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK22, "");
			femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK23, "");		
			femutuDetail.put(Femutu.DB_FEMUTU_VERIFY_BY_CUST, "");									
			
			//Excise goods
			femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G1, "");		
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
			String article=det.get(Femimp.DB_FEMIMP_ART_NO);
			Excise exc=new Excise(article,tariffRecord.get(Tariff.DB_TARIFF_CODE),quantity,shipmentDate);
			
			if(exc.isExcise()){
				TreeMap<String, String> exciseDetail=new TreeMap<String, String>();
				if(exc.hasMoreExcises()){
					exciseDetail=exc.nextExcise();
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G1,exciseDetail.get(Excise.EXCISE_MIDDEL_CODE));		
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G1, exciseDetail.get(Excise.EXCISE_AMOUNT));
				}else{
					//Setup for excise article doesn't exist
					//errorCollector.append(det.get(Femimp.DB_FEMIMP_SHP_NO)+"/"+"  Setup for excise article "+det.get(Femimp.DB_FEMIMP_ART_NO+" doesn't exist"));
					//ret=false;
				}
				
				
				if(exc.hasMoreExcises()){
					exciseDetail=exc.nextExcise();
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G3,exciseDetail.get(Excise.EXCISE_MIDDEL_CODE));		
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G3, exciseDetail.get(Excise.EXCISE_AMOUNT));
				}				
				if(exc.hasMoreExcises()){
					exciseDetail=exc.nextExcise();
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G5,exciseDetail.get(Excise.EXCISE_MIDDEL_CODE));		
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G5, exciseDetail.get(Excise.EXCISE_AMOUNT));
				}
				if(exc.hasMoreExcises()){
					exciseDetail=exc.nextExcise();
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G7,exciseDetail.get(Excise.EXCISE_MIDDEL_CODE));		
					femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G7, exciseDetail.get(Excise.EXCISE_AMOUNT));
				}
				
				femutuDetail.put(Femutu.DB_FEMUTU_EXT_PERC, exc.getExtractPercentage());
				femutuDetail.put(Femutu.DB_FEMUTU_ACT_ALCH_STE, exc.getAlcogolStrength());
				femutuDetail.put(Femutu.DB_FEMUTU_ALC_FREE_LIT, exc.getLitersAlcFree());
				femutuDetail.put(Femutu.DB_FEMUTU_LTS_OIL, exc.getLitersOil());
				femutuDetail.put(Femutu.DB_FEMUTU_PER_KG, "");
				femutuDetail.put(Femutu.DB_FEMUTU_PURE_ALC_LTS,exc.getLitersPureAlcohol());
			}
			
			
			femutuDetail.put(Femutu.DB_FEMUTU_PACK_UNIT, femutiDetail.get(Femuti.DB_KIND_CODE_D11));
			femutuDetail.put(Femutu.DB_FEMUTU_NO_OF_PACK, ""+quantity);
			femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS, "");
			femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_LINE, "");
			femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_DATE, "");
			femutuDetail.put(Femutu.DB_FEMUTU_QUAN1, "");
			femutuDetail.put(Femutu.DB_FEMUTU_QUAN2, "");
			femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_NUM, "");
			femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_DATE, "");
			femutuDetail.put(Femutu.DB_CT_CODE_DEST, "");
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
			
			//prepare key field
			String femutuKey="";
			if(uniqueKeys!=null && uniqueKeys.size()>0){
				for (String key : uniqueKeys) {
					femutuKey+=femutuDetail.get(key);		
				}
			}
			
			femutuDetail.put(Femutu.DB_FEMUTU_UNIQUE_KEY, femutuKey);
			//ret=femutu.insertUpdateFemutu(femutuDetail);
		
		}catch(Exception ex){
			log.severe("Error occured during the processing! Reason: "+ex +" See procrun error file for more details.");
			ex.printStackTrace();
		}
		
		return femutuDetail;
	}
	
	private void readInboundJob(TreeMap<String, String> det){
		
		femutiDetail=new TreeMap<String, String> ();
		femutiDetail=femuti.getEmptyDetailRecord();
		
		feminbDetail = new TreeMap<String, String> ();
		feminbDetail=feminb.getEmptyDetails();
		
		String jobNumber=det.get(Femimp.DB_FEMIMP_ARRIV_NO);
		int jobLineNumber=getInt(det.get(Femimp.DB_FEMIMP_ARRIV_LINE_NO));		
		String clientCode=det.get(Femimp.DB_FEMIMP_NAW_CODE_CM);
		
		femutiDetail = femuti.readFemutiRecord(getFeedingSystem(),INBOUND, jobNumber, jobLineNumber);		
		feminbDetail = feminb.readFeminbRecord(clientCode, jobNumber, jobLineNumber);
		
	}
	
	private void readImportLicense(){
		//TODO Retrieve Import license details
	}
	
	private String getImportLicenseNumber(){
		String licenseNumber="";
		//TODO return license number;
		return licenseNumber;
	}
	
	private String getImportLicenseDate(){
		String licenseNumber="";
		//TODO return license number;
		return licenseNumber;
	}
	private String getTransactionCode(String type){

		String transCode="101000";
		
		if(type.equals("AF")){
			//TODO  ??? Depending on blocks
			transCode="011000";
		}
		
		return transCode;
	}
	
	private String getCertificate(){
		String cert="";
		//TODO Determine certificate for
		return cert;
	}
		
	private double getInvoiceValue(String qty,String val){
		
		int quantity=getInt(qty);
		double value=getDouble(val);
		if(value==0){
			value=getInt(femutiDetail.get(Femuti.DB_FEMUTI_INV_VALUE));
		}			
		return quantity*value;
	}
	
	private String getInvoiceCurrency(String currency){
		
		if(currency.trim().equals("")){
			currency=femutiDetail.get(Femuti.DB_CURR_CODE_FEMUTI);
		}
		
		return currency;
	}
	
	private double getCostEur(String cost){
		double costEuro=getDouble(cost);
		//TODO get exchange rate
		
		//TODO calculate cost in Euro
		
		return costEuro;		
	}
	
	private double getPercentageImportDuties(){
		double duties=0;
		
		//TODO retrieve percentage import duties
		return duties;
	}
	
	private double getSpecificCustomsDuties(){
		double duties=0;
		
		//TODO retrieve specific customs duties
		return duties;
	}
	
	private double getAmountRelief(){
		double amount=0;
		
		//TODO retrieve amount relief
		return amount;
	}
	
	private String getTypeOfCharges(){
		String typeOfCharges="";
		//TODO retrieve type of charges
		
		return typeOfCharges;
	}

	private String calculateNetWeight(String numberOfPieces){
				
		int nop=getInt(numberOfPieces);
		//net weight will be set as a default value of 1 kg.
		int weightNum=nop*1;
		if(inboundExists){
			weightNum=getInt(femutiDetail.get(Femuti.DB_FEMUTI_NT_WGT))*nop;
		}
		
		return ""+weightNum;
				
	}	
	private boolean isValidDates(String shipmentDate, String arrivalDate, String accountId){
		
		boolean valid=true;
		
		switch (du.getDate(shipmentDate).compareTo(du.getDate(arrivalDate))) {
		 case -1:  
			 valid = false;
			 //errorCollector.append("Shipment date is before arrival date");	
			 this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","DAT1","Shipment date is before arrival date"));
			 break;
		 case 0:	break;
		 case 1:	break;
		default:
			valid = false;
			//errorCollector.append("Invalid results from date comparison(shipment and arrival dates)");
			this.errorsList.add(new DiskErrorsModel(0,accountId,ControleBrochure.TYPE_ERROR,"","DAT2","Invalid results from date comparison(shipment and arrival dates)"));
		}
		
		return valid;
	}

}
