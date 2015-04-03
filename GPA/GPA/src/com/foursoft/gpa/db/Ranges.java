package com.foursoft.gpa.db;

import java.util.TreeMap;

public class Ranges extends ConnectionDB {
	
	public static final String DB_RANGES = "RANGES";
	public static final String DB_RANGES_TYPE = "RANGES_TYPE";
	public static final String DB_CUSTOMER = "CUSTOMER";
	public static final String DB_RANGES_FROM = "RANGES_FROM";
	public static final String DB_RANGES_TO = "RANGES_TO";
	public static final String DB_RANGES_LAST_USED = "RANGES_LAST_USED";
	public static final String DB_RANGES_PREFIX = "RANGES_PREFIX";
	public static final String DB_RANGES_SUFFIX = "RANGES_SUFFIX";
	
	public static final String TYPE_A3="A3";

	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_RANGES_TYPE +
		"," +DB_CUSTOMER +
		"," +DB_RANGES_FROM +
		"," +DB_RANGES_TO +
		"," +DB_RANGES_LAST_USED +
		"," +DB_RANGES_PREFIX +
		"," +DB_RANGES_SUFFIX +
		" FROM "+getDbName()+DB_RANGES;
		 return selectPart;
	}
	
	public boolean insertRanges(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_RANGES+ " VALUES(";
		statement+="'"+formatString(record.get(DB_RANGES_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_CUSTOMER))+"', " ;
		statement+=formatDecimal(record.get(DB_RANGES_FROM))+"," ;
		statement+=formatDecimal(record.get(DB_RANGES_TO))+"," ;
		statement+=formatDecimal(record.get(DB_RANGES_LAST_USED))+"," ;
		statement+="'"+formatString(record.get(DB_RANGES_PREFIX))+"', " ;
		statement+="'"+formatString(record.get(DB_RANGES_SUFFIX))+"') " ;

		return insertUpdateTable(statement);
	}
	
	public TreeMap<String, String> getRanges(String type, String customer){
		
		String statement=getSelectPart() + " WHERE " +DB_RANGES_TYPE+"='"+type+"'";
		statement +=" AND "+DB_CUSTOMER+"='"+customer+"'";
		
		return readTable(statement);
	}
	
	public boolean updateLastUsed(String type, String customer, long lastUsed){
		
		
		String statement="UPDATE "+getDbName()+DB_RANGES+" SET "+DB_RANGES_LAST_USED+"="+lastUsed;
		statement+=" WHERE " + DB_RANGES_TYPE+"='"+ type+"'";
		statement+=" AND " + DB_CUSTOMER+"='"+ customer+"'";
		
		return insertUpdateTable(statement);
	}
	
	public synchronized String getUniqueIdentifier(String type, String customer){
		
		long sequence=-1;
		String uniqueIdentifier=null;
		TreeMap<String, String> record=getRanges(type,customer);
		
		if(record!=null && record.size()>0){
			
			long rangeFrom=Long.parseLong(record.get(DB_RANGES_FROM));
			long rangeTo=Long.parseLong(record.get(DB_RANGES_TO));
			sequence=Long.parseLong(record.get(DB_RANGES_LAST_USED));
			if(sequence==0){
				sequence=rangeFrom;
			}else{
				sequence++;
				if(sequence>rangeTo){
					sequence=-1;
				}
			}
			
			if(sequence>0){
				//update
				updateLastUsed(type,customer,sequence);
				uniqueIdentifier=new String();
				uniqueIdentifier=record.get(DB_RANGES_PREFIX)+sequence+record.get(DB_RANGES_SUFFIX);
			}
		}
		
		return uniqueIdentifier;
		
	}
	
	
	public synchronized TreeMap<String, String> getUniqueRange(String type, String customer){
		
		long sequence=-1;
		TreeMap<String, String> record=getRanges(type,customer);
		
		if(record!=null && record.size()>0){
			
			long rangeFrom=Long.parseLong(record.get(DB_RANGES_FROM));
			long rangeTo=Long.parseLong(record.get(DB_RANGES_TO));
			sequence=Long.parseLong(record.get(DB_RANGES_LAST_USED));
			if(sequence==0){
				sequence=rangeFrom;
			}else{
				sequence++;
				if(sequence>rangeTo){
					sequence=-1;
				}
			}
			
			if(sequence>0){
				//update
				updateLastUsed(type,customer,sequence);
				record.put(DB_RANGES_LAST_USED, ""+sequence);
			}
		}
		
		return record;
		
	}


}
