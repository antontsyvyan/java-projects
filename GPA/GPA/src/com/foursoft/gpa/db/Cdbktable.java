package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.db.ConnectionDB;

public class Cdbktable extends ConnectionDB {

	public static final String DB_CDBKTBL = "cdbktbl";
	public static final String DB_CDBKTBL_LAN = "CDBKTBL_LAN";
	public static final String DB_CDBKTBL_DECL_TYPE = "CDBKTBL_DECL_TYPE";
	public static final String DB_CDBKTBL_DESC = "CDBKTBL_DESC";
	public static final String DB_CDBKTBL_NM = "CDBKTBL_NM";


	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_CDBKTBL_LAN +
		"," +DB_CDBKTBL_DECL_TYPE +
		"," +DB_CDBKTBL_DESC +
		"," +DB_CDBKTBL_NM +
		" FROM "+getDbName()+DB_CDBKTBL;
		return selectPart;
	}
	
	public Cdbktable() {
		// TODO Auto-generated constructor stub
	}	
	
	public ArrayList<TreeMap<String, String>> getAllRecords(String language,String type) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBKTBL_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBKTBL_DECL_TYPE+"='"+type+"'";
	
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	
	public TreeMap<String, String> getTableRecord(String language,String type,String code) {
		
		String statement=getSelectPart();
		
		statement+=" WHERE  " + DB_CDBKTBL_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBKTBL_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBKTBL_NM+"='"+code+"'";
		
		TreeMap<String, String> record=readTable(statement);
		
		return record;
	}


}
