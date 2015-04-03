package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Excrel extends ConnectionDB {

	public static final String DB_EXCREL = "EXCREL";
	public static final String DB_EXCREL_CODE = "EXCREL_CODE";
	public static final String DB_EXCREL_MID_CODE = "EXCREL_MID_CODE";
	public static final String DB_EXCREL_ID = "EXCREL_ID";
	
	public Excrel() {
		
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_EXCREL_CODE +
		"," +DB_EXCREL_MID_CODE +
		"," +DB_EXCREL_ID +
		" FROM "+getDbName()+DB_EXCREL;
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> getExcrelRecords(String code) {
		
		String statement=getSelectPart();
		statement+=" WHERE  " + DB_EXCREL_CODE+"='"+code+"'";
		statement+=" ORDER BY "+DB_EXCREL_CODE;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}

}
