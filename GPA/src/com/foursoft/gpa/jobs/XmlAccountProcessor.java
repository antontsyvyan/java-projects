package com.foursoft.gpa.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Femrel;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.XmlDataProcessor;

public class XmlAccountProcessor extends XmlDataProcessor {

	@Override
	public void Process() {
		
		Protocol.initCsvProgress();
		Protocol.globalTotalRows=records.size();
		Protocol.globalCsvFileName=new File(fileName).getName();
		Protocol.globalCsvStartTime=new Date().getTime();
		
		boolean returnCode=true;
		Account account = new Account();
		
		if(records!=null && records.size()>0){
			try {
				int row=0;
				for(TreeMap<String, String> record : records){
					
					TreeMap<String, String> accountDetail=account.getEmptyDetails();							
					accountDetail.put(Account.DB_ACCOUNT_ID, record.get(Femrel.DB_NAW_CODE_CM_FEMIMP));
					accountDetail.put(Account.DB_ACCOUNT_EORI_NUMBER, record.get(Femrel.DB_FEMREL_EORI));
					accountDetail.put(Account.DB_ACCOUNT_SEARCH_NAME, record.get(Femrel.DB_FEMREL_CM_SRCH_NAME));
					accountDetail.put(Account.DB_ACCOUNT_VAT_NUMBER, record.get(Femrel.DB_FEMREL_VAT_NO));
					accountDetail.put(Account.DB_ACCOUNT_NAME1, record.get(Femrel.DB_FEMREL_CM_NAME));
					accountDetail.put(Account.DB_ACCOUNT_NAME2, "");
					accountDetail.put(Account.DB_ACCOUNT_ADDRESS1, record.get(Femrel.DB_FEMREL_CM_ADDR1));
					accountDetail.put(Account.DB_ACCOUNT_ADDRESS2, record.get(Femrel.DB_FEMREL_CM_ADDR2));
					accountDetail.put(Account.DB_ACCOUNT_POSTALCODE, record.get(Femrel.DB_FEMREL_CM_PC));
					accountDetail.put(Account.DB_ACCOUNT_CITY, record.get(Femrel.DB_FEMREL_CM_CITY));
					accountDetail.put(Account.DB_ACCOUNT_COUNTRY_CODE, record.get(Femrel.DB_CT_CODE_CM));
					accountDetail.put(Account.DB_ACCOUNT_PHONE_NUMBER1, "");
					accountDetail.put(Account.DB_ACCOUNT_PHONE_NUMBER2, "");
					accountDetail.put(Account.DB_ACCOUNT_MOBILE_NUMBER1, "");
					accountDetail.put(Account.DB_ACCOUNT_MOBILE_NUMBER2, "");
					accountDetail.put(Account.DB_ACCOUNT_FAX_NUMBER, "");
					accountDetail.put(Account.DB_CURRENCY_CODE, record.get(Femrel.DB_CURR_CODE_FEMREL));
					accountDetail.put(Account.DB_INCOTERM_CODE, "");
					accountDetail.put(Account.DB_ACCOUNT_GPA_CODE, record.get(Femrel.DB_FEMIMP_CM_CODE_MAS));
					accountDetail.put(Account.DB_ACCOUNT_FEEDING_SYSTEM, "");
					accountDetail.put(Account.DB_ACCOUNT_DECLARATION_TYPE, record.get(Femrel.DB_FEMREL_REP_TYPE));
					accountDetail.put(Account.DB_ACOOUNT_VAT_AT_CLEARANCE, "");
					
					account.insertUpdate(accountDetail);
					
					Protocol.globalTotalProcessedRows=row++;
					
				}
			} catch (Exception e) {
				log.severe("Error occured during the processing! Reason: "+e +" See procrun error file for more details.");			
				e.printStackTrace();
				log.severe("Error occured during the processing! File "+fileName+" is not loaded");
				returnCode=false;
			}finally{
		
				if(returnCode){
					//update DB
				}
			}
		}
		
		Protocol.initCsvProgress();
		
	}

	public void setRecord(ArrayList<TreeMap<String, String>> records) {
		this.records = records;
	}

}
