package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Cdbksec extends ConnectionDB{

	public static final String DB_CDBKSEC = "cdbksec";
	public static final String DB_CDBKSEC_LAN = "CDBKSEC_LAN";
	public static final String DB_CDBKSEC_DECL_TYPE = "CDBKSEC_DECL_TYPE";
	public static final String DB_CDBKSEC_DESC = "CDBKSEC_DESC";
	public static final String DB_CDBKSEC_RNK = "CDBKSEC_RNK";
	
	public Cdbksec() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_CDBKSEC_LAN +
		"," +DB_CDBKSEC_DECL_TYPE +
		"," +DB_CDBKSEC_DESC +
		"," +DB_CDBKSEC_RNK +
		" FROM "+getDbName()+DB_CDBKSEC;
		return selectPart;
	}
	
	
	public ArrayList<TreeMap<String, String>> getAllRecords(String language) {
		
		String statement=getSelectPart();
		if(!language.trim().equals("")){
			statement+=" WHERE  " + DB_CDBKSEC_LAN+"='"+language+"'";
		}
		
		statement+=" ORDER BY " + DB_CDBKSEC_RNK;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	public TreeMap<String, String> getRecord(String language,String type) {
		
		String statement=getSelectPart();
		if(!language.trim().equals("")){
			statement+=" WHERE  " + DB_CDBKSEC_LAN+"='"+language+"'";
			statement+=" AND  " + DB_CDBKSEC_DECL_TYPE+"='"+type+"'";
		}
				
		return readTable(statement);
		
	}



}
