package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class Interfaces extends ConnectionDB {
	
	// Field names
	public static final String DB_INTERFACES = "interfaces";
	public static final String DB_NAME = "INTERFACE_NAME";
	public static final String DB_DESCRIPTION = "INTERFACE_DESC";
	public static final String DB_CLASS = "INTERFACE_CLASS";
	public static final String DB_LOG_FILE = "INTERFACE_LOG_FILE";
	public static final String DB_LOG_LEVEL = "INTERFACE_LOG_LVL";
	public static final String DB_DELAY_SEC = "INTERFACE_DALAY_SEC";
	public static final String DB_ACTIVE = "INTERFACE_ACTIVE";
	
	
		
	public Interfaces() {
		super();
	}

	public ArrayList<TreeMap<String, String>> readAllInterfaces() {
		String statement = getSelectPart();
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
		

	public ArrayList<TreeMap<String, String>> readActiveInterfaces() {
		
		String statement = getSelectPart() + " WHERE " + DB_ACTIVE + "='Y'";

		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}

	public TreeMap<String, String> readInterface(String interfaceName) {

		String statement = getSelectPart() + " WHERE " + DB_NAME + "='"	+ interfaceName + "'";
		TreeMap<String, String> record = readTable(statement);
		return record;
	}
	
	public String getSelectPart(){
		
		String selectPart = "SELECT " + 
				DB_NAME + "," + 
				DB_DESCRIPTION + "," +  
				DB_CLASS + "," + 
				DB_LOG_FILE + ","+ 
				DB_LOG_LEVEL + "," + 
				DB_DELAY_SEC + "," + 
				DB_ACTIVE + 
				" FROM "+getDbName()+DB_INTERFACES;
		
		return selectPart;
	}

}
