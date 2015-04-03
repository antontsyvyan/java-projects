package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Cdbk extends ConnectionDB {
	
	public static final String DB_CDBK = "cdbk";
	public static final String DB_CDBK_LAN = "CDBK_LAN";
	public static final String DB_CDBK_DECL_TYPE = "CDBK_DECL_TYPE";
	public static final String DB_CDBK_TBL_NM = "CDBK_TBL_NM";
	public static final String DB_CDBK_CODE = "CDBK_CODE";
	public static final String DB_CDBK_DESC1 = "CDBK_DESC1";
	public static final String DB_CDBK_DESC2 = "CDBK_DESC2";
	public static final String DB_CDBK_REM = "CDBK_REM";
	public static final String DB_CDBK_UP_DT = "CDBK_UP_DT";
	

	public Cdbk() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_CDBK_LAN +
		"," +DB_CDBK_DECL_TYPE +
		"," +DB_CDBK_TBL_NM +
		"," +DB_CDBK_CODE +
		"," +DB_CDBK_DESC1 +
		"," +DB_CDBK_DESC2 +
		"," +DB_CDBK_REM +
		"," +DB_CDBK_UP_DT +
		" FROM "+getDbName()+DB_CDBK;
		return selectPart;
	}

	
	public ArrayList<TreeMap<String, String>> getTable(String language,String type,String tableName) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBK_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBK_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBK_TBL_NM+"='"+tableName+"'";
	
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	public ArrayList<TreeMap<String, String>> getTableRange(String language,String type,String tableName,String from, String to) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBK_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBK_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBK_TBL_NM+"='"+tableName+"'";
		statement+=" AND  " + DB_CDBK_CODE+">='"+from+"'";
		statement+=" AND  " + DB_CDBK_CODE+"<='"+to+"'";
	
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	public ArrayList<TreeMap<String, String>> getTablePart(String language,String type,String tableName,String selection) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBK_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBK_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBK_TBL_NM+"='"+tableName+"'";
		statement+=" AND  " + DB_CDBK_REM+"='"+selection+"'";
	
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	public ArrayList<TreeMap<String, String>> getTablePart(String language,String type,String tableName,String[] notRemarks) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBK_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBK_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBK_TBL_NM+"='"+tableName+"'";
		if(notRemarks!=null && notRemarks.length>0){
			for(int i=0;i<notRemarks.length;i++){
				statement+=" AND  " + DB_CDBK_REM+"<>'"+notRemarks[i]+"'";
			}
		}
	
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
		
	}
	
	public TreeMap<String, String> getTableRecord(String language,String type,String tableName,String code) {
		
		String statement=getSelectPart();
	
		statement+=" WHERE  " + DB_CDBK_LAN+"='"+language+"'";
		statement+=" AND  " + DB_CDBK_DECL_TYPE+"='"+type+"'";
		statement+=" AND  " + DB_CDBK_TBL_NM+"='"+tableName+"'";
		statement+=" AND  " + DB_CDBK_CODE+"='"+code+"'";
	
		TreeMap<String, String> record= readTable(statement);
		
		return record;
		
	}
}
