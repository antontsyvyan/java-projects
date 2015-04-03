package com.foursoft.gpa.db;

import java.util.TreeMap;

public class Exc extends ConnectionDB {

	public static final String DB_EXC = "EXC";
	public static final String DB_EXC_ART_CODE = "EXC_ART_CODE";
	public static final String DB_EXC_TAX_TYPE = "EXC_TAX_TYPE";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_EXC_ALC_PERC = "EXC_ALC_PERC";
	public static final String DB_EXC_SKU_HECTO_LT = "EXC_SKU_HECTO_LT";
	public static final String DB_EXC_EXT_LVL = "EXC_EXT_LVL";
	public static final String DB_EXC_KIND_TAX = "EXC_KIND_TAX";
	public static final String DB_EXC_SPARK = "EXC_SPARK";
	
	public Exc() {		
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_EXC_ART_CODE +
		"," +DB_EXC_TAX_TYPE +
		"," +DB_CT_CODE_ORG +
		"," +DB_EXC_ALC_PERC +
		"," +DB_EXC_SKU_HECTO_LT +
		"," +DB_EXC_EXT_LVL +
		"," +DB_EXC_KIND_TAX +
		"," +DB_EXC_SPARK +
		" FROM "+getDbName()+DB_EXC;
		return selectPart;
	}
	
	public TreeMap<String, String> getExc(String article) {
		String statement=getSelectPart()+ " WHERE " + DB_EXC_ART_CODE+"='"+article+"'";
		
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}

	
	
}
