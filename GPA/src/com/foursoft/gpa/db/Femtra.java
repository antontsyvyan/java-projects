package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Femtra extends ConnectionDB {

	public static final String DB_FEMTRA = "FEMTRA";
	public static final String DB_FEMTRA_NAW_CODE_CM = "FEMTRA_NAW_CODE_CM";
	public static final String DB_FEMTRA_SHP_NO = "FEMTRA_SHP_NO";
	public static final String DB_FEMTRA_SHP_LINE_NO = "FEMTRA_SHP_LINE_NO";
	public static final String DB_FEMTRA_ARRIV_NO = "FEMTRA_ARRIV_NO";
	public static final String DB_FEMTRA_ARRIV_LINE_NO = "FEMTRA_ARRIV_LINE_NO";
	public static final String DB_WRH_CODE = "WRH_CODE";
	public static final String DB_FEMTRA_CM_ORD_NUM = "FEMTRA_CM_ORD_NUM";
	public static final String DB_FEMTRA_CM_ORD_POS_NO = "FEMTRA_CM_ORD_POS_NO";
	public static final String DB_FEMTRA_SHP_DATE = "FEMTRA_SHP_DATE";
	public static final String DB_FEMTRA_ART_NO = "FEMTRA_ART_NO";
	public static final String DB_FEMTRA_ART_DESC = "FEMTRA_ART_DESC";
	public static final String DB_FEMTRA_QUAN = "FEMTRA_QUAN";
	public static final String DB_FEMTRA_GR_WGT = "FEMTRA_GR_WGT";
	public static final String DB_FEMTRA_NT_WGT = "FEMTRA_NT_WGT";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_CT_CODE_DEST = "CT_CODE_DEST";
	public static final String DB_FEMTRA_MRN = "FEMTRA_MRN";
	public static final String DB_FEMTRA_DOC_TYPE = "FEMTRA_DOC_TYPE";
	public static final String DB_FEMTRA_DOC_ISSUE_PLACE = "FEMTRA_DOC_ISSUE_PLACE";
	public static final String DB_FEMTRA_DOC_ISSUE_DATE = "FEMTRA_DOC_ISSUE_DATE";
	public static final String DB_FEMTRA_OUT_GNG_VAL = "FEMTRA_OUT_GNG_VAL";
	public static final String DB_CURR_CODE_FEMTRA = "CURR_CODE_FEMTRA";
	public static final String DB_NAW_CODE_RCV_CM = "NAW_CODE_RCV_CM";
	public static final String DB_NAW_CODE_BILL_CM = "NAW_CODE_BILL_CM";
	public static final String DB_FEMTRA_CUS_ART_CODE = "FEMTRA_CUS_ART_CODE";
	public static final String DB_FEMTRA_FRG_AMT = "FEMTRA_FRG_AMT";
	public static final String DB_FEMTRA_LOC_AMT = "FEMTRA_LOC_AMT";
	public static final String DB_FEMTRA_INT_STAT = "FEMTRA_INT_STAT";
	public static final String DB_FEMTRA_MASTER_NO = "FEMTRA_MASTER_NO";
	public static final String DB_FEMTRA_SPARE_FLD = "FEMTRA_SPARE_FLD";
	public static final String DB_FEMTRA_CRDATE = "FEMTRA_CRDATE";
	public static final String DB_FEMTRA_CRTIME = "FEMTRA_CRTIME";
	public static final String DB_FEMTRA_PROS_DATE = "FEMTRA_PROS_DATE";
	public static final String DB_FEMTRA_PROS_TIME = "FEMTRA_PROS_TIME";
	public static final String DB_FEMTRA_TA_INDICATOR = "FEMTRA_TA_INDICATOR";
		
	public Femtra(){
		this.tableDescription=getTableMetadata(DB_FEMTRA);
	}
	
	public static final String MANDATORY_FIELDS[] ={
		DB_FEMTRA_NAW_CODE_CM,
		DB_FEMTRA_SHP_NO,
		DB_FEMTRA_SHP_LINE_NO,
		DB_FEMTRA_ARRIV_NO,
		DB_FEMTRA_ARRIV_LINE_NO,
		DB_FEMTRA_SHP_DATE,
		DB_FEMTRA_ART_NO,
		DB_FEMTRA_ART_DESC,
		DB_FEMTRA_QUAN,
		DB_FEMTRA_MRN,
		DB_FEMTRA_DOC_TYPE,
		DB_FEMTRA_DOC_ISSUE_PLACE,
		DB_FEMTRA_DOC_ISSUE_DATE,
		DB_FEMTRA_INT_STAT,
		DB_FEMTRA_CRDATE,
		DB_FEMTRA_CRTIME		
	};
	
	public String getSelectPart(){ 
		
		String selectPart="SELECT " +
		DB_FEMTRA_NAW_CODE_CM +
		"," +DB_FEMTRA_SHP_NO +
		"," +DB_FEMTRA_SHP_LINE_NO +
		"," +DB_FEMTRA_ARRIV_NO +
		"," +DB_FEMTRA_ARRIV_LINE_NO +
		"," +DB_WRH_CODE +
		"," +DB_FEMTRA_CM_ORD_NUM +
		"," +DB_FEMTRA_CM_ORD_POS_NO +
		"," +DB_FEMTRA_SHP_DATE +
		"," +DB_FEMTRA_ART_NO +
		"," +DB_FEMTRA_ART_DESC +
		"," +DB_FEMTRA_QUAN +
		"," +DB_FEMTRA_GR_WGT +
		"," +DB_FEMTRA_NT_WGT +
		"," +DB_CT_CODE_ORG +
		"," +DB_CT_CODE_PROV +
		"," +DB_CT_CODE_DEST +
		"," +DB_FEMTRA_MRN +
		"," +DB_FEMTRA_DOC_TYPE +
		"," +DB_FEMTRA_DOC_ISSUE_PLACE +
		"," +DB_FEMTRA_DOC_ISSUE_DATE +
		"," +DB_FEMTRA_OUT_GNG_VAL +
		"," +DB_CURR_CODE_FEMTRA +
		"," +DB_NAW_CODE_RCV_CM +
		"," +DB_NAW_CODE_BILL_CM +
		"," +DB_FEMTRA_CUS_ART_CODE +
		"," +DB_FEMTRA_FRG_AMT +
		"," +DB_FEMTRA_LOC_AMT +
		"," +DB_FEMTRA_INT_STAT +
		"," +DB_FEMTRA_MASTER_NO +
		"," +DB_FEMTRA_SPARE_FLD +
		"," +DB_FEMTRA_CRDATE +
		"," +DB_FEMTRA_PROS_DATE +
		"," +DB_FEMTRA_PROS_TIME +
		"," +DB_FEMTRA_CRTIME +
		"," +DB_FEMTRA_TA_INDICATOR +
		" FROM "+getDbName()+DB_FEMTRA;
		 
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> readRecordsOfStatus(String clientCode, String status) {

		String statement = getSelectPart() + " WHERE " + DB_FEMTRA_INT_STAT	+ "='" + status + "'";

		if (!clientCode.trim().equals("")) {
			statement += " AND " + DB_FEMTRA_NAW_CODE_CM + "='" + clientCode + "'";
		}
		statement += " ORDER BY " + DB_FEMTRA_NAW_CODE_CM + "," + DB_FEMTRA_SHP_NO;

		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	public TreeMap<String, String> readFemtraRecord(String clientCode,String number,int line) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEMTRA_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMTRA_SHP_NO + "='"+number+"'";
		statement += " AND " + DB_FEMTRA_SHP_LINE_NO + "="+line+"";

		TreeMap<String, String> records = readTable(statement);
		
		return records;
	}
	
	public void updateStatus(String clientCode, String shipmentNumber,int line,String status){
		
		String statement =" UPDATE "+getDbName()+DB_FEMTRA+
				" SET "+DB_FEMTRA_INT_STAT+"='"+status+"'"+
				" WHERE "+DB_FEMTRA_NAW_CODE_CM+"='"+clientCode+"'"+
				" AND "+DB_FEMTRA_SHP_NO+"='"+shipmentNumber+"'"+
				" AND "+DB_FEMTRA_SHP_LINE_NO+"="+line;
		
		insertUpdateTable(statement);
		
				
	}
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		
		//retrieve key values
		
		String clientCode=in.get(DB_FEMTRA_NAW_CODE_CM);
		String number=in.get(DB_FEMTRA_SHP_NO);
		int line=Integer.parseInt(in.get(DB_FEMTRA_SHP_LINE_NO));
		
		TreeMap<String, String> tmpRecord =  readFemtraRecord(clientCode,number,line);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFemtra(tmpRecord);
		}
		
		retCode=insertFemtra(in);
		
		return retCode;
	}
	
	public void deleteFemtra(TreeMap<String, String> record){

		String clientCode=record.get(DB_FEMTRA_NAW_CODE_CM);
		String number=record.get(DB_FEMTRA_SHP_NO);
		int line=Integer.parseInt(record.get(DB_FEMTRA_SHP_LINE_NO));
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMTRA+ " WHERE " + DB_FEMTRA_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMTRA_SHP_NO + "='"+number+"'";
		statement += " AND " + DB_FEMTRA_SHP_LINE_NO + "="+line+"";
		
		insertUpdateTable(statement);
	}
	
	public boolean insertFemtra(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_FEMTRA+ " VALUES(";
		statement+="'"+formatString(record.get(DB_FEMTRA_NAW_CODE_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_SHP_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMTRA_SHP_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_FEMTRA_ARRIV_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMTRA_ARRIV_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_WRH_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_CM_ORD_NUM))+"', " ;
		statement+=formatInt(record.get(DB_FEMTRA_CM_ORD_POS_NO))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMTRA_SHP_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMTRA_ART_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_ART_DESC))+"', " ;
		statement+=formatInt(record.get(DB_FEMTRA_QUAN))+"," ;
		statement+=formatDecimal(record.get(DB_FEMTRA_GR_WGT))+"," ;
		statement+=formatDecimal(record.get(DB_FEMTRA_NT_WGT))+"," ;
		statement+="'"+formatString(record.get(DB_CT_CODE_ORG))+"', " ;
		statement+="'"+formatString(record.get(DB_CT_CODE_PROV))+"', " ;
		statement+="'"+formatString(record.get(DB_CT_CODE_DEST))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_MRN))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_DOC_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_DOC_ISSUE_PLACE))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMTRA_DOC_ISSUE_DATE))+"," ;
		statement+=formatDecimal(record.get(DB_FEMTRA_OUT_GNG_VAL))+"," ;
		statement+="'"+formatString(record.get(DB_CURR_CODE_FEMTRA))+"', " ;
		statement+="'"+formatString(record.get(DB_NAW_CODE_RCV_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_NAW_CODE_BILL_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_CUS_ART_CODE))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMTRA_FRG_AMT))+"," ;
		statement+=formatDecimal(record.get(DB_FEMTRA_LOC_AMT))+"," ;
		statement+="'"+formatString(record.get(DB_FEMTRA_INT_STAT))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_MASTER_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMTRA_SPARE_FLD))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMTRA_CRDATE))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMTRA_PROS_DATE))+"," ;
		statement+=formatInt(record.get(DB_FEMTRA_PROS_TIME))+"," ;
		statement+=formatInt(record.get(DB_FEMTRA_CRTIME))+"," ;
		statement+="'"+formatString(record.get(DB_FEMTRA_TA_INDICATOR))+"')" ;

		return insertUpdateTable(statement);
	}
	
}
