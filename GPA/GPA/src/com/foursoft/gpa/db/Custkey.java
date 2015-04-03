package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.StringTokenizer;

public class Custkey extends ConnectionDB {

	public static final String DB_CUSTKEY = "CUSTKEY";
	public static final String DB_CUSTKEY_CUSTOMER = "CUSTKEY_CUSTOMER";
	public static final String DB_CUSTKEY_TABLE = "CUSTKEY_TABLE";
	public static final String DB_CUST_KEY_KEYS = "CUST_KEY_KEYS";
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_CUSTKEY_CUSTOMER +
		"," +DB_CUSTKEY_TABLE +
		"," +DB_CUST_KEY_KEYS +
		" FROM "+getDbName()+DB_CUSTKEY;
		return selectPart;
	}
	
	public TreeMap<String, String> initRecord(){
		TreeMap<String, String> custkeyDetail= new TreeMap<String, String>();
		custkeyDetail.put(Custkey.DB_CUSTKEY_CUSTOMER, "");
		custkeyDetail.put(Custkey.DB_CUSTKEY_TABLE, "");
		custkeyDetail.put(Custkey.DB_CUST_KEY_KEYS, "");

		return custkeyDetail;
	}

	public boolean insertCustkey(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_CUSTKEY+ " VALUES(";
		statement+="'"+formatString(record.get(DB_CUSTKEY_CUSTOMER))+"', " ;
		statement+="'"+formatString(record.get(DB_CUSTKEY_TABLE))+"', " ;
		statement+="'"+formatString(record.get(DB_CUST_KEY_KEYS))+"') " ;

		return insertUpdateTable(statement);
	}
	
	public TreeMap<String, String> getCustkey(String client, String table){
		
		String statement=getSelectPart() + " WHERE " +DB_CUSTKEY_CUSTOMER+"='"+client+"'";
		statement +=" AND "+DB_CUSTKEY_TABLE+"='"+table+"'";
	
		return readTable(statement);
	}
	
	
	public ArrayList<String> getCustkeys(String client, String table){
		
		ArrayList<String> list = new ArrayList<String>();
		
		TreeMap<String, String> record= getCustkey(client,table);
		
		if(record!=null){
			String keys=record.get(DB_CUST_KEY_KEYS);
			if(keys.length()>0){
				StringTokenizer st = new StringTokenizer(keys, ",");
				while (st.hasMoreTokens()) {
					list.add(st.nextToken().toUpperCase());
				}
			}
		}
		
		return list;		
	}

}
