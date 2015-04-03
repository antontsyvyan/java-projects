package com.foursoft.gpa.db;

import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;

public class Stock extends ConnectionDB {

	public static final String DB_STOCK = "STOCK";
	public static final String DB_FEED_SYS_STOCK = "FEED_SYS_STOCK";
	public static final String DB_STOCK_PERIOD = "STOCK_PERIOD";
	public static final String DB_STOCK_UNIQ_LN_NO = "STOCK_UNIQ_LN_NO";
	public static final String DB_STOCK_LN_NO_IN = "STOCK_LN_NO_IN";
	public static final String DB_STOCK_JOB_NO_IN = "STOCK_JOB_NO_IN";
	public static final String DB_STOCK_LN_NO_OUT = "STOCK_LN_NO_OUT";
	public static final String DB_STOCK_JOB_NO_OUT = "STOCK_JOB_NO_OUT";
	public static final String DB_NAW_CODE_CM = "NAW_CODE_CM";
	public static final String DB_STOCK_LEG_STAT = "STOCK_LEG_STAT";
	public static final String DB_STOCK_ART_NO = "STOCK_ART_NO";
	public static final String DB_STOCK_TARIFF_CODE = "STOCK_TARIFF_CODE";
	public static final String DB_STOCK_IMP_DUT_PERC = "STOCK_IMP_DUT_PERC";
	public static final String DB_STOCK_TRANS_TYPE = "STOCK_TRANS_TYPE";
	public static final String DB_STOCK_TRANS_NO = "STOCK_TRANS_NO";
	public static final String DB_CURR_CODE_STOCK = "CURR_CODE_STOCK";
	public static final String DB_STOCK_EX_RATE = "STOCK_EX_RATE";
	public static final String DB_STOCK_TRANS_DATE = "STOCK_TRANS_DATE";
	public static final String DB_STOCK_NT_WGT = "STOCK_NT_WGT";
	public static final String DB_STOCK_FACULK15 = "STOCK_FACULK15";
	public static final String DB_STOCK_FACULK16 = "STOCK_FACULK16";
	public static final String DB_STOCK_FACULK17 = "STOCK_FACULK17";
	public static final String DB_STOCK_FACULK18 = "STOCK_FACULK18";
	public static final String DB_STOCK_FACULK19 = "STOCK_FACULK19";
	public static final String DB_STOCK_FACULK20 = "STOCK_FACULK20";
	public static final String DB_STOCK_FACULK21 = "STOCK_FACULK21";
	public static final String DB_STOCK_FACULK22 = "STOCK_FACULK22";
	public static final String DB_STOCK_FACULK23 = "STOCK_FACULK23";
	public static final String DB_STOCK_ADM_UNIT = "STOCK_ADM_UNIT";
	public static final String DB_STOCK_PCS = "STOCK_PCS";
	public static final String DB_STOCK_AMT = "STOCK_AMT";
	
	
	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_FEED_SYS_STOCK +
				"," +DB_STOCK_PERIOD +
				"," +DB_STOCK_UNIQ_LN_NO +
				"," +DB_STOCK_LN_NO_IN +
				"," +DB_STOCK_JOB_NO_IN +
				"," +DB_STOCK_LN_NO_OUT +
				"," +DB_STOCK_JOB_NO_OUT +
				"," +DB_NAW_CODE_CM +
				"," +DB_STOCK_LEG_STAT +
				"," +DB_STOCK_ART_NO +
				"," +DB_STOCK_TARIFF_CODE +
				"," +DB_STOCK_IMP_DUT_PERC +
				"," +DB_STOCK_TRANS_TYPE +
				"," +DB_STOCK_TRANS_NO +
				"," +DB_CURR_CODE_STOCK +
				"," +DB_STOCK_EX_RATE +
				"," +DB_STOCK_TRANS_DATE +
				"," +DB_STOCK_NT_WGT +
				"," +DB_STOCK_FACULK15 +
				"," +DB_STOCK_FACULK16 +
				"," +DB_STOCK_FACULK17 +
				"," +DB_STOCK_FACULK18 +
				"," +DB_STOCK_FACULK19 +
				"," +DB_STOCK_FACULK20 +
				"," +DB_STOCK_FACULK21 +
				"," +DB_STOCK_FACULK22 +
				"," +DB_STOCK_FACULK23 +
				"," +DB_STOCK_ADM_UNIT +
				"," +DB_STOCK_PCS +
				"," +DB_STOCK_AMT +
				"," +DB_STOCK_PERIOD +
				" FROM "+getDbName()+DB_STOCK;
		return selectPart;
	}
	public Stock() {
		// TODO Auto-generated constructor stub
	}
	
	public TreeMap<String, String> getBeginStock(String feedSystem, String clientCode,String period,String article,String currency) {
		String statement=getSelectPart()+ " WHERE " + DB_FEED_SYS_STOCK+"='"+ feedSystem+"'";

		statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
		statement+=" AND "+DB_STOCK_PERIOD+"='"+period+"'";
		statement+=" AND "+DB_STOCK_ART_NO+"='"+article+"'";
		statement+=" AND "+DB_CURR_CODE_STOCK+"='"+currency+"'";
		statement+=" AND "+DB_STOCK_TRANS_TYPE+"='"+Processors.EV+"'";
		
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}
	
	public boolean deleteStockForPeriod(String feedSystem, String clientCode,String period){
		
		boolean ret=true;
		try{
			String statement = "DELETE FROM " +getDbName()+DB_STOCK+ " WHERE " +DB_FEED_SYS_STOCK+"='"+feedSystem+"'";
			
			if(!clientCode.trim().equals("")){
				statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
			}
			statement+=" AND "+DB_STOCK_PERIOD+"='"+period+"'";
	
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
		return ret;
	}
		
	
	public static synchronized boolean insertStock(TreeMap<String, String> record){
		ConnectionDB conn= new ConnectionDB();
		boolean ret=true;
		String statement="INSERT INTO "+conn.getDbName()+DB_STOCK+ " VALUES(";
		try{	
			statement+="'"+conn.formatString(record.get(DB_FEED_SYS_STOCK))+"', " ;
			statement+=conn.formatInt(record.get(DB_STOCK_PERIOD))+"," ;
			statement+=getSequenceNumber()+"," ;
			statement+=conn.formatInt(record.get(DB_STOCK_LN_NO_IN))+"," ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_JOB_NO_IN))+"', " ;
			statement+=conn.formatInt(record.get(DB_STOCK_LN_NO_OUT))+"," ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_JOB_NO_OUT))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_NAW_CODE_CM))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_LEG_STAT))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_ART_NO))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_TARIFF_CODE))+"', " ;
			statement+=conn.formatDecimal(record.get(DB_STOCK_IMP_DUT_PERC))+"," ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_TRANS_TYPE))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_TRANS_NO))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_CURR_CODE_STOCK))+"', " ;
			statement+=conn.formatDecimal(record.get(DB_STOCK_EX_RATE))+"," ;
			statement+=DateUtils.formatDate(record.get(DB_STOCK_TRANS_DATE))+"," ;
			statement+=conn.formatInt(record.get(DB_STOCK_NT_WGT))+"," ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK15))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK16))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK17))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK18))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK19))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK20))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK21))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK22))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_FACULK23))+"', " ;
			statement+="'"+conn.formatString(record.get(DB_STOCK_ADM_UNIT))+"', " ;
			statement+=conn.formatInt(record.get(DB_STOCK_PCS))+"," ;
			statement+=conn.formatDecimal(record.get(DB_STOCK_AMT))+")" ;
		
			ret=conn.insertUpdateTable(statement);	
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
		return ret;
	}

	private static synchronized int getSequenceNumber(){
		int sequenceNumber=0;
		ConnectionDB conn= new ConnectionDB();
		try{
			String statement ="SELECT MAX("+DB_STOCK_UNIQ_LN_NO+") AS SEQN FROM "+conn.getDbName()+DB_STOCK;
			TreeMap<String, String> record = conn.readTable(statement);
			
			if(record!=null){
				sequenceNumber=Integer.parseInt(record.get("SEQN"));
				sequenceNumber=sequenceNumber+10;
			}			
		}catch(Exception ex){
			//ex.printStackTrace();

		}
		return sequenceNumber;
	}
}
