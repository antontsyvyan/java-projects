package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Femart extends ConnectionDB {
	
	public static final String DB_FEMART = "FEMART";
	public static final String DB_FEMART_CODE = "FEMART_CODE";
	public static final String DB_ACCOUNT_ID = "ACCOUNT_ID";
	public static final String DB_FEMART_DESC = "FEMART_DESC";
	public static final String DB_FEMART_TARIFF_CODE = "FEMART_TARIFF_CODE";
	public static final String DB_FEMART_IMP_DUTY = "FEMART_IMP_DUTY";
	public static final String DB_FEMART_MASTER_ART_CODE = "FEMART_MASTER_ART_CODE";
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_FEMART_CODE +
		"," +DB_ACCOUNT_ID +
		"," +DB_FEMART_DESC +
		"," +DB_FEMART_TARIFF_CODE +
		"," +DB_FEMART_IMP_DUTY +
		"," +DB_FEMART_MASTER_ART_CODE +
		" FROM "+getDbName()+DB_FEMART;
		return selectPart;
		}

	
	

	public Femart() {
		super();
	}
	
	public TreeMap<String, String> readFemartRecord(String customerCode, String articleCode) {	
		
		String statement = getSelectPart() + " WHERE " + DB_ACCOUNT_ID + "='"+customerCode+"'";	
		statement += " AND " + DB_FEMART_CODE + "='"+articleCode+"'";
		
		TreeMap<String, String> records = readTable(statement);
		return records;
	}
	
	
	public ArrayList<TreeMap<String, String>> readFemart() {	
		
		String statement = getSelectPart();
		
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}

	public ArrayList<TreeMap<String, String>> readProductsPerCustomer(String customerCode) {	
		
		String statement = getSelectPart() + " WHERE " + DB_ACCOUNT_ID + "='"+customerCode+"'";
		
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		
		String clientCode=in.get(DB_ACCOUNT_ID);
		String articleCode=in.get(DB_FEMART_CODE);
		TreeMap<String, String> tmpRecord =  readFemartRecord(clientCode,articleCode);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFemartRecord(clientCode,articleCode);
		}
		
		retCode=insertFemart(in);
		
		return retCode;
		
		//retrieve key values
	}

	public boolean deleteFemartRecord(String customerCode, String articleCode) {
		String statement = "DELETE FROM " + DB_FEMART + " WHERE  "
				+ DB_ACCOUNT_ID + "= '" + customerCode + "' AND "
				+ DB_FEMART_CODE + "='" + articleCode + "'";

		boolean success = insertUpdateTable(statement);
		return success;
	}
	
	public boolean insertFemart(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_FEMART +" VALUES(";
		statement+="'"+formatString(record.get(DB_FEMART_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_ID))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMART_DESC))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMART_TARIFF_CODE))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMART_IMP_DUTY))+"," ;
		statement+="'"+formatString(record.get(DB_FEMART_MASTER_ART_CODE))+"')" ;

		return insertUpdateTable(statement);
		
	}

}
