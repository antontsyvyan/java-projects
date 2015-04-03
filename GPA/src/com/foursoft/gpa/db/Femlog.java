package com.foursoft.gpa.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Femlog extends ConnectionDB {

	public static final String DB_FEMLOG = "FEMLOG";
	public static final String DB_FEMLOG_INTERFACE_NAME = "FEMLOG_INTERFACE_NAME";
	public static final String DB_FEMLOG_NAW_CODE_CM = "FEMLOG_NAW_CODE_CM";
	public static final String DB_FEMLOG_SHP_NO = "FEMLOG_SHP_NO";
	public static final String DB_FEMLOG_SHP_LINE_NO = "FEMLOG_SHP_LINE_NO";
	public static final String DB_FEMLOG_FIELD = "FEMLOG_FIELD";
	public static final String DB_FEMLOG_BLOCK = "FEMLOG_BLOCK";
	public static final String DB_FEMLOG_SHP_DATE = "FEMLOG_SHP_DATE";
	public static final String DB_FEMLOG_ERR_TYPE = "FEMLOG_ERR_TYPE";
	public static final String DB_FEMLOG_ERR_TXT = "FEMLOG_ERR_TXT";
	public static final String DB_FEMLOG_ERR_DT = "FEMLOG_ERR_DT";
	public static final String DB_FEMLOG_ERR_TIME = "FEMLOG_ERR_TIME";
		
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_FEMLOG_INTERFACE_NAME +
		"," +DB_FEMLOG_NAW_CODE_CM +
		"," +DB_FEMLOG_SHP_NO +
		"," +DB_FEMLOG_SHP_LINE_NO +
		"," +DB_FEMLOG_FIELD +
		"," +DB_FEMLOG_BLOCK +
		"," +DB_FEMLOG_SHP_DATE +
		"," +DB_FEMLOG_ERR_TYPE +
		"," +DB_FEMLOG_ERR_TXT +
		"," +DB_FEMLOG_ERR_DT +
		"," +DB_FEMLOG_ERR_TIME +
		" FROM "+getDbName()+DB_FEMLOG;
		return selectPart;
	}
	
	
	public TreeMap<String, String> readFemlogRecord(String interfaceName, String accountId, String shipmentNo, String lineNo, String field) {	
		
		String statement = getSelectPart() + " WHERE " +DB_FEMLOG_INTERFACE_NAME+"='"+interfaceName+"'"+
				" AND "+DB_FEMLOG_NAW_CODE_CM + "='"+accountId+"'"+
				" AND "+DB_FEMLOG_SHP_NO + "='"+shipmentNo+"'"+
				" AND "+DB_FEMLOG_SHP_LINE_NO + "="+lineNo+
				" AND "+DB_FEMLOG_FIELD + "='"+field+"'";
		
		TreeMap<String, String> records = readTable(statement);
		return records;
	}
	
	public boolean insertFemlog(TreeMap<String, String> record){
		
		//insert record
		String sysdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		int sysTime=Integer.parseInt(new SimpleDateFormat("HHmmss").format(new Date()));

		String statement="INSERT INTO "+getDbName()+DB_FEMLOG +" VALUES(";
		statement+="'"+formatString(record.get(DB_FEMLOG_INTERFACE_NAME))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMLOG_NAW_CODE_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMLOG_SHP_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMLOG_SHP_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_FEMLOG_FIELD))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMLOG_BLOCK))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMLOG_SHP_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMLOG_ERR_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMLOG_ERR_TXT))+"', " ;
		statement+="'"+sysdate+"'," ;
		statement+=sysTime+")" ;

		return insertUpdateTable(statement);
	}
	
	
	public boolean insertUpdate(TreeMap<String, String> record){
		boolean retCode=false;
		//Retrieve keys
		String interfaceName=record.get(DB_FEMLOG_INTERFACE_NAME);
		String accountId=record.get(DB_FEMLOG_NAW_CODE_CM);
		String shipmentNo=record.get(DB_FEMLOG_SHP_NO);
		String lineNo=record.get(DB_FEMLOG_SHP_LINE_NO);
		String field=record.get(DB_FEMLOG_FIELD);
		
		TreeMap<String, String> tmpRecord =  readFemlogRecord(interfaceName,accountId,shipmentNo,lineNo,field);
		
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFemlogRecord(tmpRecord);
		}
		
		retCode=insertFemlog(record);
		
		return retCode;
	}
	
	public void deleteFemlogRecord(TreeMap<String, String> record){
		
		//Retrieve keys
		String interfaceName=record.get(DB_FEMLOG_INTERFACE_NAME);
		String accountId=record.get(DB_FEMLOG_NAW_CODE_CM);
		String shipmentNo=record.get(DB_FEMLOG_SHP_NO);
		String lineNo=record.get(DB_FEMLOG_SHP_LINE_NO);
		String field=record.get(DB_FEMLOG_FIELD);
		
		
		String statement = "DELETE FROM " +getDbName()+DB_FEMLOG+ " WHERE " +DB_FEMLOG_INTERFACE_NAME+"='"+interfaceName+"'"+
				" AND "+DB_FEMLOG_NAW_CODE_CM+"='"+accountId+"'"+
				" AND "+DB_FEMLOG_SHP_NO+"='"+shipmentNo+"'"+
				" AND "+DB_FEMLOG_SHP_LINE_NO+"="+lineNo+
				" AND "+DB_FEMLOG_FIELD+"='"+field+"'";
		
		insertUpdateTable(statement);
	}
	
	public void deleteFemlogForShipment(TreeMap<String, String> record){
		
		//Retrieve keys
		String interfaceName=record.get(DB_FEMLOG_INTERFACE_NAME);
		String accountId=record.get(DB_FEMLOG_NAW_CODE_CM);
		String shipmentNo=record.get(DB_FEMLOG_SHP_NO);
		
		
		String statement = "DELETE FROM " +getDbName()+DB_FEMLOG+ " WHERE " +DB_FEMLOG_INTERFACE_NAME+"='"+interfaceName+"'"+
				" AND "+DB_FEMLOG_NAW_CODE_CM+"='"+accountId+"'"+
				" AND "+DB_FEMLOG_SHP_NO+"='"+shipmentNo+"'";
		
		insertUpdateTable(statement);
	}
	
	
	public void deleteFemlogForLine(TreeMap<String, String> record){
		
		//Retrieve keys
		String interfaceName=record.get(DB_FEMLOG_INTERFACE_NAME);
		String accountId=record.get(DB_FEMLOG_NAW_CODE_CM);
		String shipmentNo=record.get(DB_FEMLOG_SHP_NO);
		String lineNo=record.get(DB_FEMLOG_SHP_LINE_NO);
		
		
		String statement = "DELETE FROM " +getDbName()+DB_FEMLOG+ " WHERE " +DB_FEMLOG_INTERFACE_NAME+"='"+interfaceName+"'"+
				" AND "+DB_FEMLOG_NAW_CODE_CM+"='"+accountId+"'"+
				" AND "+DB_FEMLOG_SHP_NO+"='"+shipmentNo+"'"+
				" AND "+DB_FEMLOG_SHP_LINE_NO+"="+lineNo;
		
		insertUpdateTable(statement);
	}


}
