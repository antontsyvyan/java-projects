package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Excgrp extends ConnectionDB {

	public static final String DB_EXCGRP = "EXCGRP";
	public static final String DB_EXCGRP_ID = "EXCGRP_ID";
	public static final String DB_EXCGRP_SEQ = "EXCGRP_SEQ";
	public static final String DB_EXCGRP_DESC = "EXCGRP_DESC";
	
	public Excgrp() {
		
	}

	public String getSelectPart(){ 
	 String selectPart="SELECT "+DB_EXCGRP_ID +
	"," +DB_EXCGRP_SEQ +
	"," +DB_EXCGRP_DESC +
	" FROM "+getDbName()+DB_EXCGRP;
	return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> getAllExcgrpRecords() {
		
		String statement=getSelectPart();
		statement+=" ORDER BY " + DB_EXCGRP_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	
}
