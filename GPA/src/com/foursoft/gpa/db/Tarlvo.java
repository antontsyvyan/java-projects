package com.foursoft.gpa.db;

import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Tarlvo extends ConnectionDB {
	
	public static final String DB_TARLVO = "TARLVO";
	public static final String DB_TARIFF_CODE = "TARIFF_CODE";
	public static final String DB_TARLVO_ORIGIN = "TARLVO_ORIGIN";
	public static final String DB_TARLVO_VALID_FROM = "TARLVO_VALID_FROM";
	public static final String DB_TARLVO_VALID_THRU = "TARLVO_VALID_THRU";
	public static final String DB_TARLVO_PERCENTAGE_CUSTOMS = "TARLVO_PERCENTAGE_CUSTOMS";
	public static final String DB_TARLVO_DOC_CODE1 = "TARLVO_DOC_CODE1";
	public static final String DB_TARLVO_DOC_CODE2 = "TARLVO_DOC_CODE2";
	public static final String DB_TARLVO_DOC_CODE3 = "TARLVO_DOC_CODE3";
	public static final String DB_TARLVO_DOC_CODE4 = "TARLVO_DOC_CODE4";
	public static final String DB_TARLVO_DOC_CODE5 = "TARLVO_DOC_CODE5";
	

	public Tarlvo() {
		this.tableDescription=getTableMetadata(DB_TARLVO);		
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_TARIFF_CODE +
		"," +DB_TARLVO_ORIGIN +
		"," +DB_TARLVO_VALID_FROM +
		"," +DB_TARLVO_VALID_THRU +
		"," +DB_TARLVO_PERCENTAGE_CUSTOMS +
		"," +DB_TARLVO_DOC_CODE1 +
		"," +DB_TARLVO_DOC_CODE2 +
		"," +DB_TARLVO_DOC_CODE3 +
		"," +DB_TARLVO_DOC_CODE4 +
		"," +DB_TARLVO_DOC_CODE5 +
		" FROM "+getDbName()+DB_TARLVO;
		 
		return selectPart;
	}

	public TreeMap<String, String> initRecord(){
		TreeMap<String, String> tarlvoDetail= new TreeMap<String, String>();
		tarlvoDetail.put(Tarlvo.DB_TARIFF_CODE, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_ORIGIN, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_VALID_FROM, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_VALID_THRU, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_DOC_CODE1, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_DOC_CODE2, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_DOC_CODE3, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_DOC_CODE4, "");
		tarlvoDetail.put(Tarlvo.DB_TARLVO_DOC_CODE5, "");

		return tarlvoDetail;
	}

	public TreeMap<String, String> getTariff(String tariffCode, String lvo, String date){
		
		String statement=getSelectPart() + " WHERE " +DB_TARIFF_CODE+"='"+tariffCode+"'";
		statement +=" AND "+DB_TARLVO_ORIGIN+"='"+lvo+"'";
		statement +=" AND "+DB_TARLVO_VALID_FROM+"<='"+date+"'";
		statement +=" AND "+DB_TARLVO_VALID_THRU+">='"+date+"'";
		
		TreeMap<String, String> record=readTable(statement);
		
		if(record==null || record.size()==0){
			statement=getSelectPart() + " WHERE " +DB_TARIFF_CODE+"='"+tariffCode+"'";
			statement +=" AND "+DB_TARLVO_ORIGIN+"='"+lvo+"'";
			statement +=" AND "+DB_TARLVO_VALID_FROM+"<='"+date+"'";
			statement +=" AND "+DB_TARLVO_VALID_THRU+" is null";
			record=readTable(statement);
		}
				
		return record;
	}
	
	public boolean insertTarlvo(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_TARLVO +" VALUES(";
		statement+="'"+formatString(record.get(DB_TARIFF_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_TARLVO_ORIGIN))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_TARLVO_VALID_FROM))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_TARLVO_VALID_THRU))+"," ;
		statement+=formatDecimal(record.get(DB_TARLVO_PERCENTAGE_CUSTOMS))+"," ;
		statement+="'"+formatString(record.get(DB_TARLVO_DOC_CODE1))+"', " ;
		statement+="'"+formatString(record.get(DB_TARLVO_DOC_CODE2))+"', " ;
		statement+="'"+formatString(record.get(DB_TARLVO_DOC_CODE3))+"', " ;
		statement+="'"+formatString(record.get(DB_TARLVO_DOC_CODE4))+"', " ;
		statement+="'"+formatString(record.get(DB_TARLVO_DOC_CODE5))+"')" ;

		return insertUpdateTable(statement);
	}

	public Tarlvo(boolean close) {
		super(close);
		// TODO Auto-generated constructor stub
	}

}
