package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Main;
import com.foursoft.gpa.utils.cache.CacheObject;

public class Account extends ConnectionDB {
	
	public static final String DB_ACCOUNT = "ACCOUNT";
	public static final String DB_ACCOUNT_ID = "ACCOUNT_ID";
	public static final String DB_ACCOUNT_EORI_NUMBER = "ACCOUNT_EORI_NUMBER";
	public static final String DB_ACCOUNT_SEARCH_NAME = "ACCOUNT_SEARCH_NAME";
	public static final String DB_ACCOUNT_VAT_NUMBER = "ACCOUNT_VAT_NUMBER";
	public static final String DB_ACCOUNT_NAME1 = "ACCOUNT_NAME1";
	public static final String DB_ACCOUNT_NAME2 = "ACCOUNT_NAME2";
	public static final String DB_ACCOUNT_ADDRESS1 = "ACCOUNT_ADDRESS1";
	public static final String DB_ACCOUNT_ADDRESS2 = "ACCOUNT_ADDRESS2";
	public static final String DB_ACCOUNT_POSTALCODE = "ACCOUNT_POSTALCODE";
	public static final String DB_ACCOUNT_CITY = "ACCOUNT_CITY";
	public static final String DB_ACCOUNT_COUNTRY_CODE = "ACCOUNT_COUNTRY_CODE";
	public static final String DB_ACCOUNT_PHONE_NUMBER1 = "ACCOUNT_PHONE_NUMBER1";
	public static final String DB_ACCOUNT_PHONE_NUMBER2 = "ACCOUNT_PHONE_NUMBER2";
	public static final String DB_ACCOUNT_MOBILE_NUMBER1 = "ACCOUNT_MOBILE_NUMBER1";
	public static final String DB_ACCOUNT_MOBILE_NUMBER2 = "ACCOUNT_MOBILE_NUMBER2";
	public static final String DB_ACCOUNT_FAX_NUMBER = "ACCOUNT_FAX_NUMBER";
	public static final String DB_CURRENCY_CODE = "CURRENCY_CODE";
	public static final String DB_INCOTERM_CODE = "INCOTERM_CODE";
	public static final String DB_ACCOUNT_GPA_CODE = "ACCOUNT_GPA_CODE";
	public static final String DB_ACCOUNT_FEEDING_SYSTEM = "ACCOUNT_FEEDING_SYSTEM";
	public static final String DB_ACCOUNT_DECLARATION_TYPE = "ACCOUNT_DECLARATION_TYPE";
	public static final String DB_ACOOUNT_VAT_AT_CLEARANCE = "ACOOUNT_VAT_AT_CLEARANCE";

	public String getSelectPart(){ 
	 String selectPart="SELECT "+DB_ACCOUNT_ID +
		"," +DB_ACCOUNT_EORI_NUMBER +
		"," +DB_ACCOUNT_SEARCH_NAME +
		"," +DB_ACCOUNT_VAT_NUMBER +
		"," +DB_ACCOUNT_NAME1 +
		"," +DB_ACCOUNT_NAME2 +
		"," +DB_ACCOUNT_ADDRESS1 +
		"," +DB_ACCOUNT_ADDRESS2 +
		"," +DB_ACCOUNT_POSTALCODE +
		"," +DB_ACCOUNT_CITY +
		"," +DB_ACCOUNT_COUNTRY_CODE +
		"," +DB_ACCOUNT_PHONE_NUMBER1 +
		"," +DB_ACCOUNT_PHONE_NUMBER2 +
		"," +DB_ACCOUNT_MOBILE_NUMBER1 +
		"," +DB_ACCOUNT_MOBILE_NUMBER2 +
		"," +DB_ACCOUNT_FAX_NUMBER +
		"," +DB_CURRENCY_CODE +
		"," +DB_INCOTERM_CODE +
		"," +DB_ACCOUNT_GPA_CODE +
		"," +DB_ACCOUNT_FEEDING_SYSTEM +
		"," +DB_ACCOUNT_DECLARATION_TYPE +
		"," +DB_ACOOUNT_VAT_AT_CLEARANCE +
		" FROM "+getDbName()+DB_ACCOUNT;
	return selectPart;
	}

	public TreeMap<String, String> initRecord(){
		
		TreeMap<String, String> accountDetail= new TreeMap<String, String>();
		
		accountDetail.put(Account.DB_ACCOUNT_ID, "");
		accountDetail.put(Account.DB_ACCOUNT_EORI_NUMBER, "");
		accountDetail.put(Account.DB_ACCOUNT_SEARCH_NAME, "");
		accountDetail.put(Account.DB_ACCOUNT_VAT_NUMBER, "");
		accountDetail.put(Account.DB_ACCOUNT_NAME1, "");
		accountDetail.put(Account.DB_ACCOUNT_NAME2, "");
		accountDetail.put(Account.DB_ACCOUNT_ADDRESS1, "");
		accountDetail.put(Account.DB_ACCOUNT_ADDRESS2, "");
		accountDetail.put(Account.DB_ACCOUNT_POSTALCODE, "");
		accountDetail.put(Account.DB_ACCOUNT_CITY, "");
		accountDetail.put(Account.DB_ACCOUNT_COUNTRY_CODE, "");
		accountDetail.put(Account.DB_ACCOUNT_PHONE_NUMBER1, "");
		accountDetail.put(Account.DB_ACCOUNT_PHONE_NUMBER2, "");
		accountDetail.put(Account.DB_ACCOUNT_MOBILE_NUMBER1, "");
		accountDetail.put(Account.DB_ACCOUNT_MOBILE_NUMBER2, "");
		accountDetail.put(Account.DB_ACCOUNT_FAX_NUMBER, "");
		accountDetail.put(Account.DB_CURRENCY_CODE, "");
		accountDetail.put(Account.DB_INCOTERM_CODE, "");
		accountDetail.put(Account.DB_ACCOUNT_GPA_CODE, "");
		accountDetail.put(Account.DB_ACCOUNT_FEEDING_SYSTEM, "");
		accountDetail.put(Account.DB_ACCOUNT_DECLARATION_TYPE, "");
		accountDetail.put(Account.DB_ACOOUNT_VAT_AT_CLEARANCE, "");
	
		return accountDetail;
	}
	
	
	public TreeMap<String, String> getAccountByGpaCode(String code){
				
		String statement = getSelectPart() + " WHERE "+DB_ACCOUNT_GPA_CODE+"='"+code+"'";
		
		return readTable(statement);
		
	}

	public TreeMap<String, String> getAccountDetails(String accountId){
		
		String statement = getSelectPart() + " WHERE "+DB_ACCOUNT_ID+"='"+accountId+"'";
		
		return readTable(statement);
		
	}
	
	public ArrayList <String> getFeedingsSystems(){
		
		ArrayList<String> feedingsSystems=new ArrayList<String>();
		feedingsSystems.add("");
		
		String statement="SELECT "+DB_ACCOUNT_FEEDING_SYSTEM +
				" FROM "+getDbName()+DB_ACCOUNT+
				" GROUP BY "+ DB_ACCOUNT_FEEDING_SYSTEM;
		
		
		ArrayList <TreeMap<String, String>> records= readTableMultiple(statement);
		
		if(records!=null && records.size()>0){
			for(TreeMap<String, String> tmp:records){
				if(!tmp.get(Account.DB_ACCOUNT_FEEDING_SYSTEM).equals("")){
					feedingsSystems.add(tmp.get(Account.DB_ACCOUNT_FEEDING_SYSTEM));
				}
			}
		}		
		
		return feedingsSystems;
		
	}
	
	public ArrayList <String> getCustomers(){
		
		ArrayList<String> customers=new ArrayList<String>();
		customers.add("");
		
		String statement="SELECT "+DB_ACCOUNT_ID +
				" FROM "+getDbName()+DB_ACCOUNT;
		
		
		ArrayList <TreeMap<String, String>> records= readTableMultiple(statement);
		
		if(records!=null && records.size()>0){
			for(TreeMap<String, String> tmp:records){
				if(!tmp.get(Account.DB_ACCOUNT_ID).equals("")){
					customers.add(tmp.get(Account.DB_ACCOUNT_ID));
				}
			}
		}		
		
		return customers;
		
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getAccountDetailsCached(String accountId){
		
		TreeMap<String, String> record= new TreeMap<String, String>();
		
		String identifier=DB_ACCOUNT+"-"+accountId;
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		if(cob!=null){
			record=(TreeMap<String, String>) cob.getValue();
		}else{
			record=getAccountDetails(accountId);
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
		
		return record;
	}
	
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		
		String accountId=in.get(DB_ACCOUNT_ID);
		TreeMap<String, String> tmpRecord =  getAccountDetails(accountId);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteAccountRecord(accountId);
		}
		
		retCode=insertAccount(in);
		
		return retCode;
	}
	
	public boolean deleteAccountRecord(String accountId) {
		String statement = "DELETE FROM " + DB_ACCOUNT + " WHERE  "
				+ DB_ACCOUNT_ID + "= '" + accountId + "'";

		boolean success = insertUpdateTable(statement);
		return success;
	}
	
	public boolean insertAccount(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_ACCOUNT +" VALUES(";
		statement+="'"+formatString(record.get(DB_ACCOUNT_ID))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_EORI_NUMBER))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_SEARCH_NAME))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_VAT_NUMBER))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_NAME1))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_NAME2))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_ADDRESS1))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_ADDRESS2))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_POSTALCODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_CITY))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_COUNTRY_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_PHONE_NUMBER1))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_PHONE_NUMBER2))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_MOBILE_NUMBER1))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_MOBILE_NUMBER2))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_FAX_NUMBER))+"', " ;
		statement+="'"+formatString(record.get(DB_CURRENCY_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_INCOTERM_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_GPA_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_FEEDING_SYSTEM))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_DECLARATION_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACOOUNT_VAT_AT_CLEARANCE))+"')" ;

	
		return insertUpdateTable(statement);
	}
}
