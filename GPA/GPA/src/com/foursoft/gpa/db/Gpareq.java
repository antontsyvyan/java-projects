package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Gpareq extends ConnectionDB {
	
	public static final String DB_GPAREQ = "gpareq";
	public static final String DB_GPAREQ_ID = "GPAREQ_ID";
	public static final String DB_GPAREQ_TYPE = "GPAREQ_TYPE";
	public static final String DB_GPAREQ_RLBCK = "GPAREQ_RLBCK";
	public static final String DB_GPAREQ_CUS = "GPAREQ_CUS";
	public static final String DB_GPAREQ_FED_SYS = "GPAREQ_FED_SYS";
	public static final String DB_GPAREQ_PER = "GPAREQ_PER";
	public static final String DB_GPAREQ_STAT = "GPAREQ_STAT";
	public static final String DB_GAPREQ_ERR_MSG = "GAPREQ_ERR_MSG";
	public static final String DB_GPAREQ_DISC_FILE = "GPAREQ_DISC_FILE";
	public static final String DB_GPAREQ_PROC_DATE = "GPAREQ_PROC_DATE";
	public static final String DB_GPAREQ_PROC_TIME = "GPAREQ_PROC_TIME";
	public static final String DB_GPAREQ_MTHD = "GPAREQ_MTHD";


	private static Gpareq gpareq = new Gpareq();
	
	public Gpareq() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_GPAREQ_ID +
				"," +DB_GPAREQ_TYPE +
				"," +DB_GPAREQ_RLBCK +
				"," +DB_GPAREQ_CUS +
				"," +DB_GPAREQ_FED_SYS +
				"," +DB_GPAREQ_PER +
				"," +DB_GPAREQ_STAT +
				"," +DB_GAPREQ_ERR_MSG +
				"," +DB_GPAREQ_DISC_FILE +
				"," +DB_GPAREQ_PROC_DATE +
				"," +DB_GPAREQ_PROC_TIME +
				"," +DB_GPAREQ_MTHD +
				" FROM "+getDbName()+DB_GPAREQ;
		return selectPart;
		}
	
	public ArrayList<TreeMap<String, String>> readRecordsForPeriod(String status){
		String statement = getSelectPart() + " WHERE "+DB_GPAREQ_STAT+"='"+status+"'";
		
		return readTableMultiple(statement);
	}
	
	public ArrayList<TreeMap<String, String>> readAllRecords(boolean asc){
		String order="ASC";
		if(!asc){
			order="DESC";
		}
		String statement=getSelectPart()+" ORDER BY "+DB_GPAREQ_ID+" "+order;
		
		return readTableMultiple(statement);
	}
	
	public boolean insertGpareq(TreeMap<String, String> record){
		
		String db=getDbName();
		String statement="INSERT INTO "+db+DB_GPAREQ+ " VALUES(";
		statement+=getId(db)+"," ;
		statement+="'"+formatString(record.get(DB_GPAREQ_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_GPAREQ_RLBCK))+"', " ;
		statement+="'"+formatString(record.get(DB_GPAREQ_CUS))+"', " ;
		statement+="'"+formatString(record.get(DB_GPAREQ_FED_SYS))+"', " ;
		statement+=formatInt(record.get(DB_GPAREQ_PER))+"," ;
		statement+="'"+formatString(record.get(DB_GPAREQ_STAT))+"', " ;
		statement+="'"+formatString(record.get(DB_GAPREQ_ERR_MSG))+"', " ;
		statement+="'"+formatString(record.get(DB_GPAREQ_DISC_FILE))+"', " ;
		statement+="'"+formatString(record.get(DB_GPAREQ_MTHD))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_GPAREQ_PROC_DATE))+"," ;
		statement+=formatInt(record.get(DB_GPAREQ_PROC_TIME))+")";		
			
		return insertUpdateTable(statement);
	}
	
	
	public boolean updateStatusGpareq(String id,String status, String error, String diskFileName){
		
		boolean ret=true;
		
		String statement="UPDATE "+getDbName()+DB_GPAREQ+ 
				
				" SET "+DB_GPAREQ_STAT+"='"+status+"',"+
				DB_GAPREQ_ERR_MSG+"='"+error+"',"+
				DB_GPAREQ_DISC_FILE+"='"+diskFileName+"'";
		
		statement+=" WHERE " + DB_GPAREQ_ID+"="+ id;
		
		ret=insertUpdateTable(statement);
		
		return ret;
		
	}
	
	public boolean resetStatusGpareq(String statusFrom,String statusTo){
		
		boolean ret=true;
		
		String statement="UPDATE "+getDbName()+DB_GPAREQ+ 
				
				" SET "+DB_GPAREQ_STAT+"='"+statusTo+"',"+
				DB_GAPREQ_ERR_MSG+"='',"+
				DB_GPAREQ_DISC_FILE+"=''";
		
		statement+=" WHERE " + DB_GPAREQ_STAT+"='"+ statusFrom+"'";
		
		ret=insertUpdateTable(statement);
		
		return ret;
		
	}
	
	
	public TreeMap<String, String> initRecord(){
			
			TreeMap<String, String> gpareqDetail= new TreeMap<String, String>();
			
			gpareqDetail.put(Gpareq.DB_GPAREQ_ID, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_TYPE, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_RLBCK, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_CUS, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_FED_SYS, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_PER, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_STAT, "");
			gpareqDetail.put(Gpareq.DB_GAPREQ_ERR_MSG, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_DISC_FILE, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_PROC_DATE, "");
			gpareqDetail.put(Gpareq.DB_GPAREQ_PROC_TIME, "");
			
			return gpareqDetail;
	}
	

	
	
	private static synchronized int getId(String db){
		
		int id=0;
		String LAST_ID="LAST_ID";
		
		String statement="select MAX("+DB_GPAREQ_ID+") "+LAST_ID+" from " +db+DB_GPAREQ;
		TreeMap<String, String> record = gpareq.readTable(statement);
		
		if(record!=null && record.size()>0){
			if(record.get(LAST_ID)!=null && !record.get(LAST_ID).trim().equals("")){
				id=Integer.parseInt(record.get(LAST_ID));
				id++;
			}
		}
		
		return id;
		
	}

}
