package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.StringTokenizer;


public class Mapin extends ConnectionDB {
	
	
	public static final String DB_MAPIN = "mapin";
	public static final String DB_MAPIN_ID = "MAPIN_ID";
	public static final String DB_MAPIN_FLD_NAME = "MAPIN_FLD_NAME";
	public static final String DB_MAPIN_BLCK = "MAPIN_BLCK";
	public static final String DB_MAPIN_SEQ = "MAPIN_SEQ";
	public static final String DB_MAPIN_FLD_TYPE = "MAPIN_FLD_TYPE";
	public static final String DB_MAPIN_FLD_LN = "MAPIN_FLD_LN";
	public static final String DB_MAPIN_DEC = "MAPIN_DEC";
	public static final String DB_MAPIN_ALLOW_NEG = "MAPIN_ALLOW_NEG";
	public static final String DB_MAPIN_MAND = "MAPIN_MAND";
	
	public String getSelectPart() {
		String selectPart = "SELECT " + DB_MAPIN_ID + 
				"," + DB_MAPIN_FLD_NAME+ 
				"," + DB_MAPIN_BLCK + 
				"," + DB_MAPIN_SEQ + 
				"," + DB_MAPIN_FLD_TYPE + 
				"," + DB_MAPIN_FLD_LN + 
				"," + DB_MAPIN_DEC + 
				"," + DB_MAPIN_ALLOW_NEG + 
				"," + DB_MAPIN_MAND
				+ " FROM " + getDbName() + DB_MAPIN;
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> getMappingDetails(String id,String blockString) {
		StringBuffer blocks=new StringBuffer("");
		if(blockString.contains(Transcodes.DEFAULT_BLOCK_SEPARATOR)){
			String separator="";
			StringTokenizer st = new StringTokenizer(blockString,Transcodes.DEFAULT_BLOCK_SEPARATOR);
			while (st.hasMoreTokens()) {
				blocks.append(separator);
				blocks.append("'"+st.nextToken().trim()+"'");
		        separator=Transcodes.DEFAULT_BLOCK_SEPARATOR;
		     }
		}else{
			blocks.append("'"+blockString+"'");
		}
		String statement=getSelectPart()+ " WHERE " + DB_MAPIN_ID+"='"+id+"'";
		statement+=" AND "+ DB_MAPIN_BLCK+" IN("+blocks.toString()+")";
		statement+=" ORDER BY "+ DB_MAPIN_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	
	public ArrayList<TreeMap<String, String>> getMappingDetails(String id,List<String> blocksList) {
		StringBuffer blocks=new StringBuffer("");
		
		String separator="";
		for (int i = 0; i < blocksList.size(); i++) {
			blocks.append(separator);
			blocks.append("'"+blocksList.get(i).trim()+"'");
	        separator=Transcodes.DEFAULT_BLOCK_SEPARATOR;
		}

		String statement=getSelectPart()+ " WHERE " + DB_MAPIN_ID+"='"+id+"'";
		statement+=" AND "+ DB_MAPIN_BLCK+" IN("+blocks.toString()+")";
		statement+=" ORDER BY "+ DB_MAPIN_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	public ArrayList<TreeMap<String, String>> getMappingBlockDetails(String id,String blockName) {
		
		String statement=getSelectPart()+ " WHERE " + DB_MAPIN_ID+"='"+id+"'";
		statement+=" AND "+ DB_MAPIN_BLCK+"="+blockName;
		statement+=" ORDER BY " + DB_MAPIN_BLCK +","+ DB_MAPIN_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}
	
	public ArrayList<TreeMap<String, String>> getMappingDetails(String id) {
		
		String statement=getSelectPart()+ " WHERE " + DB_MAPIN_ID+"='"+id+"'";
		statement+=" ORDER BY " + DB_MAPIN_BLCK +","+ DB_MAPIN_SEQ;
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		
		return record;
	}

	public Mapin() {
		// TODO Auto-generated constructor stub
	}

}
