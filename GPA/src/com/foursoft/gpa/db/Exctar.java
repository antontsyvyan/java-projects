package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Exctar extends ConnectionDB {

	public static final String DB_EXCTAR = "EXCTAR";
	public static final String DB_EXCTAR_MID_CODE = "EXCTAR_MID_CODE";
	public static final String DB_EXCTAR_SORT_ID = "EXCTAR_SORT_ID";
	public static final String DB_EXCTAR_START_DATE = "EXCTAR_START_DATE";
	public static final String DB_EXCTAR_UNIT = "EXCTAR_UNIT";
	public static final String DB_EXCTAR_UNIT_TAR = "EXCTAR_UNIT_TAR";
	public static final String DB_EXCTAR_SPL_UNIT_TAR = "EXCTAR_SPL_UNIT_TAR";
	public static final String DB_EXCTAR_MIN_UNIT_TAR = "EXCTAR_MIN_UNIT_TAR";
	public static final String DB_EXCTAR_PERC_RETAIL = "EXCTAR_PERC_RETAIL";
	
	private DateUtils du = new DateUtils();
	
	public Exctar() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_EXCTAR_MID_CODE +
		"," +DB_EXCTAR_SORT_ID +
		"," +DB_EXCTAR_START_DATE +
		"," +DB_EXCTAR_UNIT +
		"," +DB_EXCTAR_UNIT_TAR +
		"," +DB_EXCTAR_SPL_UNIT_TAR +
		"," +DB_EXCTAR_MIN_UNIT_TAR +
		"," +DB_EXCTAR_PERC_RETAIL +
		" FROM "+getDbName()+DB_EXCTAR;
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> getExcsrtRecords(String sortId) {
		
		String statement=getSelectPart();
		statement+=" WHERE  " + DB_EXCTAR_SORT_ID+"='"+sortId+"'";
		statement+=" ORDER BY "+DB_EXCTAR_START_DATE;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	
	public TreeMap<String, String> getExcsrtRecord(String sortId,String midCode,String date) {
		
		String statement=getSelectPart();
		statement+=" WHERE  " + DB_EXCTAR_SORT_ID+"='"+sortId+"'";
		if(!midCode.trim().equals("")){
			statement+=" AND  " + DB_EXCTAR_MID_CODE+"='"+midCode+"'";
		}
		statement+=" ORDER BY "+DB_EXCTAR_START_DATE+ " DESC";
		TreeMap<String, String> detail=new TreeMap<String, String>();
		
		ArrayList<TreeMap<String, String>> records= readTableMultiple(statement);
		
		if (records != null && records.size() > 0) {
			for (int i = 0; i < records.size(); i++) {
				detail=records.get(i);
				if(du.getDate(date).compareTo((du.getDate(detail.get(DB_EXCTAR_START_DATE))))>=0){
					return detail;
				}
			}
			//return the last record
			return records.get(records.size()-1);
		}
		//nothing found - > return null	
		return null;
	}

}
