package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Prmchk extends ConnectionDB {

	public static final String DB_PRMCHK = "PRMCHK";
	public static final String DB_FEED_SYS_PRMCHK = "FEED_SYS_PRMCHK";
	public static final String DB_PRMCHK_PERIOD = "PRMCHK_PERIOD";
	public static final String DB_PRMCHK_CRDATE = "PRMCHK_CRDATE";
	public static final String DB_PRMCHK_DISK_CREATED = "PRMCHK_DISK_CREATED";
	public static final String DB_PRMCHK_IMP_DUTIES = "PRMCHK_IMP_DUTIES";
	public static final String DB_PRMCHK_AMT_RELIEF = "PRMCHK_AMT_RELIEF";
	public static final String DB_PRMCHK_SPEC_CUST_DUTIES = "PRMCHK_SPEC_CUST_DUTIES";
	public static final String DB_PRMCHK_IMP_DUTIES_E35 = "PRMCHK_IMP_DUTIES_E35";
	public static final String DB_PRMCHK_PRELIM_AMT_CHRGS = "PRMCHK_PRELIM_AMT_CHRGS";
	public static final String DB_PRMCHK_FINAL_AMT_CHRGS = "PRMCHK_FINAL_AMT_CHRGS";
	public static final String DB_PRMCHK_VAT_AMT = "PRMCHK_VAT_AMT";
	public static final String DB_PRMCHK_AGR_LEVIES = "PRMCHK_AGR_LEVIES";
	public static final String DB_PRMCHK_CUST_VAL = "PRMCHK_CUST_VAL";
	public static final String DB_PRMCHK_TRANS_CODE_NO = "PRMCHK_TRANS_CODE_NO";
	public static final String DB_PRMCHK_REC_NO = "PRMCHK_REC_NO";
	public static final String DB_PRMCHK_DECL_REC_NO = "PRMCHK_DECL_REC_NO";
	public static final String DB_PRMCHK_EXCISE_AMT1 = "PRMCHK_EXCISE_AMT1";
	public static final String DB_PRMCHK_EXCISE_CODE1 = "PRMCHK_EXCISE_CODE1";
	public static final String DB_PRMCHK_EXCISE_AMT2 = "PRMCHK_EXCISE_AMT2";
	public static final String DB_PRMCHK_EXCISE_CODE2 = "PRMCHK_EXCISE_CODE2";
	public static final String DB_PRMCHK_EXCISE_AMT3 = "PRMCHK_EXCISE_AMT3";
	public static final String DB_PRMCHK_EXCISE_CODE3 = "PRMCHK_EXCISE_CODE3";
	public static final String DB_PRMCHK_EXCISE_AMT4 = "PRMCHK_EXCISE_AMT4";
	public static final String DB_PRMCHK_EXCISE_CODE4 = "PRMCHK_EXCISE_CODE4";

	
	public String getSelectPart(){ 
		
		String selectPart="SELECT "+DB_FEED_SYS_PRMCHK +
		"," +DB_PRMCHK_PERIOD +
		"," +DB_PRMCHK_CRDATE +
		"," +DB_PRMCHK_DISK_CREATED +
		"," +DB_PRMCHK_IMP_DUTIES +
		"," +DB_PRMCHK_AMT_RELIEF +
		"," +DB_PRMCHK_SPEC_CUST_DUTIES +
		"," +DB_PRMCHK_IMP_DUTIES_E35 +
		"," +DB_PRMCHK_PRELIM_AMT_CHRGS +
		"," +DB_PRMCHK_FINAL_AMT_CHRGS +
		"," +DB_PRMCHK_VAT_AMT +
		"," +DB_PRMCHK_AGR_LEVIES +
		"," +DB_PRMCHK_CUST_VAL +
		"," +DB_PRMCHK_TRANS_CODE_NO +
		"," +DB_PRMCHK_REC_NO +
		"," +DB_PRMCHK_DECL_REC_NO +
		"," +DB_PRMCHK_EXCISE_AMT1 +
		"," +DB_PRMCHK_EXCISE_CODE1 +
		"," +DB_PRMCHK_EXCISE_AMT2 +
		"," +DB_PRMCHK_EXCISE_CODE2 +
		"," +DB_PRMCHK_EXCISE_AMT3 +
		"," +DB_PRMCHK_EXCISE_CODE3 +
		"," +DB_PRMCHK_EXCISE_AMT4 +
		"," +DB_PRMCHK_EXCISE_CODE4 +
		" FROM "+getDbName()+DB_PRMCHK;
		
		return selectPart;
	}
	
	public TreeMap<String, String> initRecord(){
		
		TreeMap<String, String> prmchkDetail= new TreeMap<String, String>();
		
		prmchkDetail.put(Prmchk.DB_FEED_SYS_PRMCHK, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_PERIOD, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_CRDATE, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_DISK_CREATED, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_IMP_DUTIES, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_AMT_RELIEF, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_SPEC_CUST_DUTIES, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_IMP_DUTIES_E35, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_PRELIM_AMT_CHRGS, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_FINAL_AMT_CHRGS, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_VAT_AMT, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_AGR_LEVIES, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_CUST_VAL, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_TRANS_CODE_NO, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_REC_NO, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_DECL_REC_NO, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_AMT1, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_CODE1, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_AMT2, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_CODE2, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_AMT3, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_CODE3, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_AMT4, "");
		prmchkDetail.put(Prmchk.DB_PRMCHK_EXCISE_CODE4, "");
		
		return prmchkDetail;

	}
	
	public TreeMap<String, String> readPrmchkRecord(String feedSystem, String period) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEED_SYS_PRMCHK+ "='"+feedSystem+"'"+
		" AND "+DB_PRMCHK_PERIOD+"='"+period+"'";
		
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}
	
	public TreeMap<String, String> getAllPeriods(String feedSystem){
				
		String statement = getSelectPart() + " WHERE " +DB_FEED_SYS_PRMCHK+ "='"+feedSystem+"'";
	
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		TreeMap<String, String> retTable= new TreeMap<String,String>();
				
		for (TreeMap<String, String> item : records) {
			retTable.put(item.get(DB_PRMCHK_PERIOD), item.get(DB_PRMCHK_DISK_CREATED));
		}				
		return retTable;
	}
	
	public ArrayList<TreeMap<String, String>> getAllPeriodsList(String feedSystem){
		
		String statement = getSelectPart() + " WHERE " +DB_FEED_SYS_PRMCHK+ "='"+feedSystem+"'";
		statement += " ORDER BY "+ DB_PRMCHK_PERIOD + " DESC";
		return readTableMultiple(statement);
	}
	
	public boolean insertUpdatePrmchk(TreeMap<String, String> record){
		
		boolean ret=true;
		
		String feedSystem=record.get(DB_FEED_SYS_PRMCHK);
		String period=record.get(DB_PRMCHK_PERIOD);
		
		TreeMap<String, String> tmpRecord = readPrmchkRecord(feedSystem, period);
		
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deletePrmchk(feedSystem,period);
		}
		
		String statement="INSERT INTO "+getDbName()+DB_PRMCHK+ " VALUES(";
		try{
			statement+="'"+formatString(record.get(DB_FEED_SYS_PRMCHK))+"', " ;
			statement+=formatInt(record.get(DB_PRMCHK_PERIOD))+"," ;
			statement+=DateUtils.formatDate(record.get(DB_PRMCHK_CRDATE))+"," ;
			statement+="'"+formatString(record.get(DB_PRMCHK_DISK_CREATED))+"', " ;
			statement+=formatDecimal(record.get(DB_PRMCHK_IMP_DUTIES))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_AMT_RELIEF))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_SPEC_CUST_DUTIES))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_IMP_DUTIES_E35))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_PRELIM_AMT_CHRGS))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_FINAL_AMT_CHRGS))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_VAT_AMT))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_AGR_LEVIES))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_CUST_VAL))+"," ;
			statement+=formatInt(record.get(DB_PRMCHK_TRANS_CODE_NO))+"," ;
			statement+=formatInt(record.get(DB_PRMCHK_REC_NO))+"," ;
			statement+=formatInt(record.get(DB_PRMCHK_DECL_REC_NO))+"," ;
			statement+=formatDecimal(record.get(DB_PRMCHK_EXCISE_AMT1))+"," ;
			statement+="'"+formatString(record.get(DB_PRMCHK_EXCISE_CODE1))+"', " ;
			statement+=formatDecimal(record.get(DB_PRMCHK_EXCISE_AMT2))+"," ;
			statement+="'"+formatString(record.get(DB_PRMCHK_EXCISE_CODE2))+"', " ;
			statement+=formatDecimal(record.get(DB_PRMCHK_EXCISE_AMT3))+"," ;
			statement+="'"+formatString(record.get(DB_PRMCHK_EXCISE_CODE3))+"', " ;
			statement+=formatDecimal(record.get(DB_PRMCHK_EXCISE_AMT4))+"," ;
			statement+="'"+formatString(record.get(DB_PRMCHK_EXCISE_CODE4))+"')" ;
			
			ret=insertUpdateTable(statement);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
		
		return ret;
	}
	
	public void deletePrmchk(String feedSystem,String period){
	
		
		String statement = "DELETE FROM " +getDbName()+DB_PRMCHK+ " WHERE " +DB_FEED_SYS_PRMCHK+"='"+feedSystem+"'"+
				" AND "+DB_PRMCHK_PERIOD+"='"+period+"'";
		
		insertUpdateTable(statement);
	}
	
	public boolean updatePrmchkStatus(String feedSystem,String period, String status){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_PRMCHK+ " SET "+DB_PRMCHK_DISK_CREATED+"='"+status+"'";
			statement+=" WHERE " + DB_FEED_SYS_PRMCHK+"='"+ feedSystem+"'";
			
			statement+=" AND "+DB_PRMCHK_PERIOD+"='"+period+"'";
			
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public Prmchk() {
		
	}

}
