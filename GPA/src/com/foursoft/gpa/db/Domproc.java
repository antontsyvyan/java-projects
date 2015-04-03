package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Domproc extends ConnectionDB {

	public static final String DB_DOMPROC = "domproc";
	
	public static final String MANDATORY_FIELDS[] ={};
	
	
	public Domproc() {
		super();
	}

	
	public ArrayList<TreeMap<String, String>> readRecordsOfStatus(String clientCode,String status) {
		
		String statement = "";

		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}


}
