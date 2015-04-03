package com.foursoft.gpa.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class Femerr extends ConnectionDB {

	public static final String DB_FEMERR = "femerr";
	public static final String DB_FEMERR_INTERFACE_NAME = "FEMERR_INTERFACE_NAME";
	public static final String DB_FEMERR_NAW_CODE_CM = "FEMERR_NAW_CODE_CM";
	public static final String DB_FEMERR_SHP_NO = "FEMERR_SHP_NO";
	public static final String DB_FEMERR_ERR_TYPE = "FEMERR_ERR_TYPE";
	public static final String DB_FEMERR_ERR_TXT = "FEMERR_ERR_TXT";
	public static final String DB_FEMERR_ERR_DT = "FEMERR_ERR_DT";
	public static final String DB_FEMERR_ERR_TIME = "FEMERR_ERR_TIME";
	
	public String getSelectPart(){ 
		String selectPart ="SELECT " +DB_FEMERR_INTERFACE_NAME +
		"," +DB_FEMERR_NAW_CODE_CM +
		"," +DB_FEMERR_SHP_NO +
		"," +DB_FEMERR_ERR_TYPE +
		"," +DB_FEMERR_ERR_TXT +
		"," +DB_FEMERR_ERR_DT +
		"," +DB_FEMERR_ERR_TIME +
		" FROM "+getDbName()+DB_FEMERR;
	return selectPart;
	}
	
	
	public void insertUpdateFemerr(TreeMap<String, String> record){
		String statement = getSelectPart() + " WHERE " +DB_FEMERR_INTERFACE_NAME+"='"+record.get(DB_FEMERR_INTERFACE_NAME)+"'"+
				" AND "+DB_FEMERR_NAW_CODE_CM+"='"+record.get(DB_FEMERR_NAW_CODE_CM)+"'"+
				" AND "+DB_FEMERR_SHP_NO+"='"+record.get(DB_FEMERR_SHP_NO)+"'"+
				" AND "+DB_FEMERR_ERR_TYPE+"='"+record.get(DB_FEMERR_ERR_TYPE)+"'";
		
		TreeMap<String, String> tmpRecord = readTable(statement);
		
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFemerr(tmpRecord);
		}
		
		//insert record
		String sysdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int sysTime=Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));
		
		statement="INSERT INTO "+getDbName()+DB_FEMERR+" VALUES ('"+record.get(DB_FEMERR_INTERFACE_NAME)+"',"+
		"'"+record.get(DB_FEMERR_NAW_CODE_CM)+"',"+
		"'"+record.get(DB_FEMERR_SHP_NO)+"',"+
		"'"+record.get(DB_FEMERR_ERR_TYPE)+"',"+
		"'"+record.get(DB_FEMERR_ERR_TXT)+"',"+
		"'"+sysdate+"',"+
		sysTime+")";
		
		insertUpdateTable(statement);
	}
	

	
	public void deleteFemerr(TreeMap<String, String> record){
		
		String statement = "DELETE FROM " +getDbName()+DB_FEMERR+ " WHERE " +DB_FEMERR_INTERFACE_NAME+"='"+record.get(DB_FEMERR_INTERFACE_NAME)+"'"+
				" AND "+DB_FEMERR_NAW_CODE_CM+"='"+record.get(DB_FEMERR_NAW_CODE_CM)+"'"+
				" AND "+DB_FEMERR_SHP_NO+"='"+record.get(DB_FEMERR_SHP_NO)+"'"+
				" AND "+DB_FEMERR_ERR_TYPE+"='"+record.get(DB_FEMERR_ERR_TYPE)+"'";
		
		insertUpdateTable(statement);
	}
}
