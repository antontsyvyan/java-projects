package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Excsrt extends ConnectionDB {

	public static final String DB_EXCSRT = "excsrt";
	public static final String DB_EXCSRT_GRP_ID = "EXCSRT_GRP_ID";
	public static final String DB_EXCSRT_DESC = "EXCSRT_DESC";
	public static final String DB_EXCSRT_ID = "EXCSRT_ID";
	public static final String DB_EXCSRT_SEQ = "EXCSRT_SEQ";
	public static final String DB_EXCSRT_CODE = "EXCSRT_CODE";
	
	public Excsrt() {

	}
	public String getSelectPart(){ 
		
		String selectPart="SELECT "+DB_EXCSRT_GRP_ID +
				"," +DB_EXCSRT_DESC +
				"," +DB_EXCSRT_ID +
				"," +DB_EXCSRT_SEQ +
				"," +DB_EXCSRT_CODE +
				" FROM "+getDbName()+DB_EXCSRT;
		return selectPart;
		}
	
	public ArrayList<TreeMap<String, String>> getExcsrtRecords(String groupId) {
		
		String statement=getSelectPart();
		statement+=" WHERE  " + DB_EXCSRT_GRP_ID+"='"+groupId+"'";
		statement+=" ORDER BY "+DB_EXCSRT_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	
	public TreeMap<String, String> getExcsrtRecordsBySortId(String sortId) {
		
		String statement=getSelectPart();
		
		statement+=" WHERE  " + DB_EXCSRT_ID+"='"+sortId+"'";
		statement+=" ORDER BY "+DB_EXCSRT_SEQ;
		
		TreeMap<String, String> record= readTable(statement);
		
		return record;
	}
	
	public TreeMap<String, String> getExcsrtRecordsByExciseCode(String code) {
		
		String statement=getSelectPart();
		
		statement+=" WHERE  " + DB_EXCSRT_CODE+"='"+code+"'";
		statement+=" ORDER BY "+DB_EXCSRT_SEQ;
		
		TreeMap<String, String> record= readTable(statement);
		
		return record;
	}
	

}
