package com.foursoft.gpa.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Custkey;
import com.foursoft.gpa.db.Defdata;
import com.foursoft.gpa.db.Feminb;
import com.foursoft.gpa.db.Femlog;
import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Tariff;
import com.foursoft.gpa.db.Tarlvo;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.GpaCalculator;
import com.foursoft.gpa.utils.MovementReferenceNumber;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class InboundProcessor extends Processors {

	private StringBuffer errorCollector= new StringBuffer();
		
	private Femlog logFile=new Femlog();
	private Femuti femuti=new Femuti();
	private Custkey kustkey= new Custkey();
	
	private ControleBrochure cb;
	protected List<DiskErrorsModel> errorsList;	
	
	private ArrayList<String> uniqueKeys;
	
	public InboundProcessor() {
		
	}


	@Override
	public void Process() {
		log.info(" ==> Entering Inbound processor. <==");
		
		try{
			ProcessSemaphore.feminbSemaphore.acquire();
			ProcessSemaphore.femutiSemaphore.acquire();
			log.info(" ==> Inbound processor acqured feminb and femuti semaphore<==");	
			Feminb interfaceFile=new Feminb();
			//process all customers
			boolean firstTime=true;
			String previousJobNumber="";
			String currentJobNumber="";
			ArrayList<TreeMap<String, String>> tempDetails=new ArrayList<TreeMap<String, String>>();
			TreeMap<String, String> tempDetail=new TreeMap<String, String>();
			uniqueKeys=kustkey.getCustkeys(Resources.getSetting("customer.name"), Femuti.DB_FEMUTI);
			cb= new ControleBrochure(Disk.ENTREPOT);
			
			ArrayList<TreeMap<String, String>> details=interfaceFile.readRecordsOfStatus("","P");
			if (details != null && details.size() > 0) {
				for (int i = 0; i < details.size(); i++) {
					tempDetail=details.get(i);
					currentJobNumber=tempDetail.get(Feminb.DB_FEMINB_ARRV_NO);
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
			e.printStackTrace();
		}finally{
			ProcessSemaphore.feminbSemaphore.release();
			ProcessSemaphore.femutiSemaphore.release();
			log.info(" ==> Inbound processor released feminb and femuti semaphores<==");	
		}
		
	}
	
	private boolean processOneJob(ArrayList<TreeMap<String, String>> details){
		TreeMap<String, String> errDetail=new TreeMap<String, String>();
		
		String interfaceName=Thread.currentThread().getName();
		
		errDetail.put(Femlog.DB_FEMLOG_INTERFACE_NAME, interfaceName);
		errDetail.put(Femlog.DB_FEMLOG_NAW_CODE_CM,details.get(0).get(Feminb.DB_FEMINB_NAW_CODE_CM));
		errDetail.put(Femlog.DB_FEMLOG_SHP_NO,details.get(0).get(Feminb.DB_FEMINB_ARRV_NO));
		errDetail.put(Femlog.DB_FEMLOG_SHP_DATE,details.get(0).get(Feminb.DB_FEMINB_ARRV_DATE));
						
		errorsList = new ArrayList<DiskErrorsModel>();
		boolean success=true;
		for (TreeMap<String, String> detail:details) {
			
			String accountId=detail.get(Feminb.DB_FEMINB_NAW_CODE_CM);	
			String jobNo=detail.get(Feminb.DB_FEMINB_ARRV_NO);	
			int line=getInt(detail.get(Feminb.DB_FEMINB_ARRIV_LINE_NO));			
			
			String serachField=accountId+"/"+jobNo+"/"+line;
			
			//Delete error log
			errDetail.put(Femlog.DB_FEMLOG_SHP_LINE_NO,""+line);			
			logFile.deleteFemlogForLine(errDetail);
			
					
			if(!isValid(detail,serachField)){
				success=false;
			}else{
				TreeMap<String, String>femutiDetail=femuti.formatRecord(mapToFemuti(detail));
				if(isValidBrochure(femuti.mapToDisk(femutiDetail),serachField)){
					Feminb interfaceFile=new Feminb();
					success=femuti.insertUpdateFemuti(femutiDetail);
					interfaceFile.updateStatus(detail.get(Feminb.DB_FEMINB_NAW_CODE_CM),detail.get(Feminb.DB_FEMINB_ARRV_NO),line,"X");
				}	
			}
			
			//Save errors if any	
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
		
	private boolean isValid(TreeMap<String, String> detail, String serachField ) {
		boolean valid = true;
		errorCollector = new StringBuffer();
		String currentValue="";
		String accountId=detail.get(Feminb.DB_FEMINB_NAW_CODE_CM).trim();
		readCompanyData(accountId);
		//Assuming arrival number is filled
		//Check if record already exists
		String feedSystem=getFeedingSystem();;
		String jobNo=detail.get(Feminb.DB_FEMINB_ARRV_NO);
		int line=Integer.parseInt(detail.get(Feminb.DB_FEMINB_ARRIV_LINE_NO));
		String transType=detail.get(Feminb.DB_FEMINB_TRANS_TYPE);
		
		TreeMap<String, String> tmpFemuti =  femuti.readFemutiRecord(feedSystem, INBOUND, jobNo,line);
		if(tmpFemuti!=null && tmpFemuti.size()>0){
			//Check the status
			if(tmpFemuti.get(Femuti.DB_FEMUTI_STAT).equals(FINAL)){
				//Write log if the record processed by the final run of GPA (FEMUTU_STAT=3)
				this.errorsList.add(new DiskErrorsModel(0,serachField,ControleBrochure.TYPE_ERROR,Disk.BLOCK_B,Disk.BLOCK_B2,"Job: "+jobNo+"/"+line+" is already processed by the final run of GPA."));
				valid = false;
			}
		}
		//If the record processed by the final run of GPA no need to execute other validations.
		if(valid){
		// Check mandatory fields
			
			if(transType.equals(BI)){
				for (int i = 0; i < Feminb.MANDATORY_FIELDS.length; i++) {
					currentValue=detail.get(Feminb.MANDATORY_FIELDS[i]);
					if ( currentValue== null|| currentValue.trim().equals("")) {
						this.errorsList.add(new DiskErrorsModel(0,serachField,ControleBrochure.TYPE_ERROR,Feminb.MANDATORY_FIELDS[i],"","Field " + Feminb.MANDATORY_FIELDS[i] + " is mandatory.\r\n"));
						valid = false;
					}
				}
			}
			//Other checks
			
			if(!detail.get(Feminb.DB_FEMINB_DOC_ISSUE_DATE).trim().equals("")){
				//Document issue date should be less or equals arrival date		
				if(!isValidDates(detail,Feminb.DB_FEMINB_DOC_ISSUE_DATE,Feminb.DB_FEMINB_ARRV_DATE)){
					valid = false;
				}
			}
			
			if(!detail.get(Feminb.DB_FEMINB_INV_DATE).trim().equals("")){
				//Invoice date should be less or equals arrival date			
				if(!isValidDates(detail,Feminb.DB_FEMINB_INV_DATE,Feminb.DB_FEMINB_ARRV_DATE)){
					valid = false;
				}
			}
		    //If incoterm is FOB, FAS or EXW, than freight cost must be filled
			String[] incoFreight= new String[] {"FOB","FAS","EXW"};
			if(Arrays.asList(incoFreight).contains(detail.get(Feminb.DB_FEMINB_INCO_TERM))){
				if(getDouble(detail.get(Feminb.DB_FEMINB_FRT_AMT)) <=0){
					errorCollector.append("Freight cost must be filled");
				}
			}		
			//If incoterm is FOB, FAS, EXW, CFR or CPT, than insurance cost must be filled
			String[] incoInsurance= new String[] {"FOB","FAS","EXW","CFR","CPT"};
			if(Arrays.asList(incoInsurance).contains(detail.get(Feminb.DB_FEMINB_INCO_TERM))){
				if(getDouble(detail.get(Feminb.DB_FEMINB_INS_AMT)) <=0){
					errorCollector.append("Insurance cost must be filled");
				}
			}	
			
			
			if(transType.equals(BI)){
				MovementReferenceNumber mrn= new MovementReferenceNumber(detail.get(Feminb.DB_FEMINB_MRN));
				if(!mrn.isValid()){
					//this.errorsList.add(new DiskErrorsModel(0,serachField,ControleBrochure.TYPE_ERROR,Disk.BLOCK_B,Disk.BLOCK_B2,mrn.getError()));
					//valid=false;
				}
			}
			
		}
		return valid;

	}
	
	private boolean isValidBrochure(TreeMap<String, String>diskDetail,String serachField){
		
		boolean retCode=false;
		
		cb.setDirection(INBOUND);
		cb.setDetail(diskDetail);
		cb.setLine(0);
		

		cb.setSearchField(serachField);
		
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
	
	private TreeMap<String, String> mapToFemuti(TreeMap<String, String> det){
		
		TreeMap<String, String> femutiDetail= new TreeMap<String, String> ();	
		//Retrieve company data
		String accountId=det.get(Feminb.DB_FEMINB_NAW_CODE_CM).trim();
		readCompanyData(accountId);
		//Retrieve default inbound data
		readDefaultData(det);
		
		TreeMap<String, String>tariffRecord=getTariff(det.get(Feminb.DB_FEMINB_ART_NO), accountId, det.get(Feminb.DB_CT_CODE_PROV),det.get(Feminb.DB_FEMINB_ARRV_DATE));
		
		femutiDetail.put(Femuti.DB_FEED_SYS_FEMUTI, getFeedingSystem());
		femutiDetail.put(Femuti.DB_FEMUTI_IN_OUT_BOUND, INBOUND);
		femutiDetail.put(Femuti.DB_FEMUTI_JOB_NO,det.get(Feminb.DB_FEMINB_ARRV_NO));
		femutiDetail.put(Femuti.DB_FEMUTI_LINE_NO, det.get(Feminb.DB_FEMINB_ARRIV_LINE_NO));
		femutiDetail.put(Femuti.DB_FEMUTI_TRANS_CODE, getTransactionCode(det.get(Feminb.DB_FEMINB_TRANS_TYPE)));
		femutiDetail.put(Femuti.DB_FEMUTI_BON_WRH_TYPE, getWarenhouseType());
		femutiDetail.put(Femuti.DB_FEMUTI_STAT_GDS, getDefaultFieldValue(Defdata.DB_DEFDATA_STAT_CUST_GDS));
		femutiDetail.put(Femuti.DB_FEMUTI_NAW_NO, getCompanyNumber());
		femutiDetail.put(Femuti.DB_FEMUTI_DOC_TYPE, det.get(Feminb.DB_FEMINB_DOC_TYPE));
		femutiDetail.put(Femuti.DB_FEMUTI_DOC_NO, getDocumentNumber(det.get(Feminb.DB_FEMINB_MRN),det.get(Feminb.DB_FEMINB_DOC_TYPE)));
		//femutiDetail.put(Femuti.DB_FEMUTI_PLACE_ISSUE_DOC, getPlaceIssuing(det.get(Feminb.DB_FEMINB_MRN),det.get(Feminb.DB_FEMINB_DOC_TYPE),det.get(Feminb.DB_FEMINB_DOC_ISSUE_PLACE)));
		femutiDetail.put(Femuti.DB_FEMUTI_PLACE_ISSUE_DOC, det.get(Feminb.DB_FEMINB_DOC_ISSUE_PLACE));
		femutiDetail.put(Femuti.DB_FEMUTI_DATE_ISSUE_DOC, det.get(Feminb.DB_FEMINB_DOC_ISSUE_DATE));
		femutiDetail.put(Femuti.DB_FEMUTI_TG_IND, det.get(Feminb.DB_FEMINB_TG_IND));
		femutiDetail.put(Femuti.DB_FEMUTI_PERIOD, getPeriod(det.get(Feminb.DB_FEMINB_ARRV_DATE)));
		femutiDetail.put(Femuti.DB_FEMUTI_STAT, PRELIMINARY);
		femutiDetail.put(Femuti.DB_FEMUTI_MOD, WAREHOUSE);		
		femutiDetail.put(Femuti.DB_FEMUTI_GR_WGT, calculateWeight(det.get(Feminb.DB_FEMINB_GR_WGT),det.get(Feminb.DB_FEMINB_NO_PCS_D12)));
		femutiDetail.put(Femuti.DB_KIND_CODE_D3, det.get(Feminb.DB_KIND_CODE_FEMINB));
		femutiDetail.put(Femuti.DB_FEMUTI_NO_PCS, det.get(Feminb.DB_FEMINB_NO_PCS));
		//femutiDetail.put(Femuti.DB_FEMUTI_ART_NO, constructArticleNumber(det.get(Feminb.DB_FEMINB_ART_NO),det.get(Feminb.DB_FEMINB_NAW_CODE_CM)));
		femutiDetail.put(Femuti.DB_FEMUTI_ART_NO, det.get(Feminb.DB_FEMINB_ART_NO));
		femutiDetail.put(Femuti.DB_FEMUTI_ART_DESC, det.get(Feminb.DB_FEMINB_ART_DESC));
		femutiDetail.put(Femuti.DB_FEMUTI_TARIFF_CODE, tariffRecord.get(Tariff.DB_TARIFF_CODE));
		femutiDetail.put(Femuti.DB_FEMUTI_IMP_CERTIFICATE, det.get(Feminb.DB_FEMINB_IMP_LIC));
		femutiDetail.put(Femuti.DB_FEMUTI_INVNO, det.get(Feminb.DB_FEMINB_INVNO));
		femutiDetail.put(Femuti.DB_FEMUTI_INV_LINE_NO, det.get(Feminb.DB_FEMINB_INV_LINE_NO));
		femutiDetail.put(Femuti.DB_FEMUTI_INV_DATE, det.get(Feminb.DB_FEMINB_INV_DATE));
		femutiDetail.put(Femuti.DB_KIND_CODE_D11, det.get(Feminb.DB_KIND_CODE_FEMINB_D11));
		femutiDetail.put(Femuti.DB_FEMUTI_NO_PCS_D12, det.get(Feminb.DB_FEMINB_NO_PCS_D12));
		femutiDetail.put(Femuti.DB_FEMUTI_NT_WGT, calculateWeight(det.get(Feminb.DB_FEMINB_NET_WGT),det.get(Feminb.DB_FEMINB_NO_PCS_D12)));
		femutiDetail.put(Femuti.DB_FEMUTI_LOC, det.get(Feminb.DB_FEMINB_LOC));
		femutiDetail.put(Femuti.DB_FEMUTI_SPARE_FLD_D15, "");
		femutiDetail.put(Femuti.DB_CT_CODE_ORG, det.get(Feminb.DB_CT_CODE_ORG));
		femutiDetail.put(Femuti.DB_CT_CODE_PROV, det.get(Feminb.DB_CT_CODE_PROV));
		femutiDetail.put(Femuti.DB_FEMUTI_PREF_CODE1, det.get(Feminb.DB_FEMINB_PREF_CODE1));
		femutiDetail.put(Femuti.DB_FEMUTI_PREF_CODE2, det.get(Feminb.DB_FEMINB_PREF_CODE2));
		
		femutiDetail.put(Femuti.DB_FEMUTI_NO_ADD_UNITS, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_UNITS, "");
		if(!tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS).equals("")){
			femutiDetail.put(Femuti.DB_FEMUTI_NO_ADD_UNITS, getUnitsNumber(det.get(Feminb.DB_FEMINB_NO_ADD_UNIT),det.get(Feminb.DB_FEMINB_NO_PCS_D12)));
			femutiDetail.put(Femuti.DB_FEMUTI_ADD_UNITS, tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS));
		}
		
		femutiDetail.put(Femuti.DB_FEMUTI_INV_PROFORMA, det.get(Feminb.DB_FEMINB_INVNO));
		femutiDetail.put(Femuti.DB_FEMUTI_INV_PROFORMA_LINE, det.get(Feminb.DB_FEMINB_INV_LINE_NO));
		double invoiceValue=calculateValue(det.get(Feminb.DB_FEMINB_INV_VALUE),det.get(Feminb.DB_FEMINB_NO_PCS_D12));
		femutiDetail.put(Femuti.DB_FEMUTI_INV_VALUE,""+invoiceValue);
		femutiDetail.put(Femuti.DB_CURR_CODE_FEMUTI, det.get(Feminb.DB_CURR_CODE_FEMINB));
		double rate=getRate(det.get(Feminb.DB_CURR_CODE_FEMINB),det.get(Feminb.DB_FEMINB_ARRV_DATE));
		femutiDetail.put(Femuti.DB_FEMUTI_EX_RATE, ""+rate);
		femutiDetail.put(Femuti.DB_FEMUTI_DOC_VAL, ""+calculateDocValue(invoiceValue,rate));
		femutiDetail.put(Femuti.DB_FEMUTI_INV_PROF_DATE, det.get(Feminb.DB_FEMINB_INV_DATE));
		femutiDetail.put(Femuti.DB_FEMUTI_COM_PREF, det.get(Feminb.DB_FEMINB_COM_PREF));
		//femutiDetail.put(Femuti.DB_FEMUTI_BOX37, getDefaultFieldValue(Defdata.DB_DEFDATA_BOX37));
		femutiDetail.put(Femuti.DB_FEMUTI_BOX37, getCodeVak37());
		femutiDetail.put(Femuti.DB_FEMUTI_TRANS_TYPE, getDefaultFieldValue(Defdata.DB_DEFDATA_TRANS_TYPE));
		femutiDetail.put(Femuti.DB_FEMUTI_RELATION,getDefaultFieldValue(Defdata.DB_DEFDATA_RELATION));
		femutiDetail.put(Femuti.DB_FEMUTI_RESTRIC, getDefaultFieldValue(Defdata.DB_DEFDATA_RESTRIC));
		femutiDetail.put(Femuti.DB_FEMUTI_LIC_FEE, getDefaultFieldValue(Defdata.DB_DEFDATA_LIC_FEE));
		femutiDetail.put(Femuti.DB_FEMUTI_CALC_METHOD, getDefaultFieldValue(Defdata.DB_DEFDATA_CALC_METHOD));
		femutiDetail.put(Femuti.DB_FEMUTI_VAL_DET, getDefaultFieldValue(Defdata.DB_DEFDATA_VAL_DET));
		femutiDetail.put(Femuti.DB_FEMUTI_TAR_CONT_REQ, getDefaultFieldValue(Defdata.DB_DEFDATA_TARIFF_CONT_REQ));
		femutiDetail.put(Femuti.DB_MOT_CODE_FEMUTI, det.get(Feminb.DB_MOT_CODE_FEMINB));
		femutiDetail.put(Femuti.DB_FEMUTI_CONT, getContainer(det.get(Feminb.DB_FEMINB_CONT)));
		femutiDetail.put(Femuti.DB_NAW_CODE_CM, det.get(Feminb.DB_FEMINB_NAW_CODE_CM).trim());
		femutiDetail.put(Femuti.DB_FEMUTI_SPEC_INDI, getIndicator(det.get(Feminb.DB_FEMINB_TRANS_TYPE)));
		femutiDetail.put(Femuti.DB_FEMUTI_STAN_PERC_DUTIES, tariffRecord.get(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS));
		femutiDetail.put(Femuti.DB_FEMUTI_TRANS_CODE_K7, det.get(Feminb.DB_FEMINB_TRANS_TYPE));
		femutiDetail.put(Femuti.DB_FEMUTI_TRANS_NO, det.get(Feminb.DB_FEMINB_TRANS_NO));
		femutiDetail.put(Femuti.DB_FEMUTI_TRANS_DATE, det.get(Feminb.DB_FEMINB_ARRV_DATE));
		femutiDetail.put(Femuti.DB_FEMUTI_NT_WGT_K13, calculateWeight(det.get(Feminb.DB_FEMINB_NET_WGT),det.get(Feminb.DB_FEMINB_NO_PCS_D12)));
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK15, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK16, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK17, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK18, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK19, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK20, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK21, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK22, "");
		femutiDetail.put(Femuti.DB_FEMUTI_ADD_FLDK23, "");
		femutiDetail.put(Femuti.DB_FEMUTI_DOC_CODE, "");
		femutiDetail.put(Femuti.DB_FEMUTI_LOC, Feminb.DB_FEMINB_LOC );
		
		//trim all fields
		for (String key : femutiDetail.keySet()) {
			femutiDetail.put(key, femutiDetail.get(key).trim());
		}
		
		//prepare key field
		String femutiKey="";
		if(uniqueKeys!=null && uniqueKeys.size()>0){
			for (String key : uniqueKeys) {
				femutiKey+=femutiDetail.get(key);		
			}
		}
		femutiDetail.put(Femuti.DB_FEMUTI_UNIQUE_KEY, femutiKey);
									
		return femutiDetail;
	}
			
	private String getTransactionCode(String type){

		String transCode="101000";
		
		if(type.equals("BI")){
			transCode="011000";
		}
		
		return transCode;
	}
			
	private double calculateDocValue(double invoiceValue,double rate){
		
		return GpaCalculator.round(invoiceValue*rate,2);
				
	}
	
	
	private double calculateValue(String value,String numberOfPieces){
			
		double valueNum=getDouble(value);			
		double nbrNum=getDouble(numberOfPieces);
		
		double totValue=GpaCalculator.round(valueNum*nbrNum,2);
		
		return totValue;				
	}
	
		
	private String getUnitsNumber(String noUnits,String noPcs){
		//TODO get additional units from tariff table
		if(noUnits.trim().equals("") || noUnits.equals("0")){
			noUnits=noPcs;
		}
		
		return noUnits;
		
	}
	
	private String getContainer(String container){
		
		String cont="0";
		
		if(container!=null && container.trim().equals("Y")){
			cont="1";
		}
		
		return cont;
	}
	
	private String getIndicator(String indicator){
		
		String ind="";
		String[] types= new String[] {"BM","BZ"};
		
		if(indicator!=null){
			if(Arrays.asList(types).contains(indicator.trim())){
				ind="G";
			}
		}

		
		return ind;
	}
	
	private String getCodeVak37(){
		
		String value= getDefaultFieldValue(Defdata.DB_DEFDATA_BOX37);
		if(value.equals("")){
			value="7100000";
			
		}		
		return value;
	}
	
	private boolean isValidDates(TreeMap<String, String> detail,String name1, String name2){
		
		boolean valid=true;
		
		switch (du.getDate(detail.get(name1)).compareTo(du.getDate(detail.get(name2)))) {
		 case -1:  break;
		 case 0:   break;
		 case 1:   
			 valid = false;
			 errorCollector.append(name1+" is after "+name2 +"\r\n");	
			 break;
		default:
			valid = false;
			errorCollector.append("Invalid results from date comparison("+name1+" "+name2+")\r\n");
		}
		
		return valid;
	}
}
