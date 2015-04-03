package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Femimp extends ConnectionDB {

	public static final String DB_FEMIMP = "FEMIMP";
	public static final String DB_FEMIMP_NAW_CODE_CM = "FEMIMP_NAW_CODE_CM";
	public static final String DB_FEMIMP_SHP_NO = "FEMIMP_SHP_NO";
	public static final String DB_FEMIMP_SHP_LINE_NO = "FEMIMP_SHP_LINE_NO";
	public static final String DB_FEMIMP_ARRIV_NO = "FEMIMP_ARRIV_NO";
	public static final String DB_FEMIMP_DOC_TYPE = "FEMIMP_DOC_TYPE";
	public static final String DB_FEMIMP_ARRIV_LINE_NO = "FEMIMP_ARRIV_LINE_NO";
	public static final String DB_WRH_CODE = "WRH_CODE";
	public static final String DB_CM_ORD_NUM_FEMIMP = "CM_ORD_NUM_FEMIMP";
	public static final String DB_CM_ORD_POS_NO_FEMIMP = "CM_ORD_POS_NO_FEMIMP";
	public static final String DB_FEMIMP_SHP_DATE = "FEMIMP_SHP_DATE";
	public static final String DB_FEMIMP_ART_NO = "FEMIMP_ART_NO";
	public static final String DB_FEMIMP_ART_DESC = "FEMIMP_ART_DESC";
	public static final String DB_FEMIMP_QUAN = "FEMIMP_QUAN";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_CT_CODE_DEST = "CT_CODE_DEST";
	public static final String DB_FEMIMP_NAW_CODE_RCV_CM = "FEMIMP_NAW_CODE_RCV_CM";
	public static final String DB_FEMIMP_NAW_CODE_RCV_BILL = "FEMIMP_NAW_CODE_RCV_BILL";
	public static final String DB_FEMIMP_CUS_ART_CODE = "FEMIMP_CUS_ART_CODE";
	public static final String DB_FEMIMP_PREF_CODE1 = "FEMIMP_PREF_CODE1";
	public static final String DB_FEMIMP_PREF_CODE2 = "FEMIMP_PREF_CODE2";
	public static final String DB_FEMIMP_TRANS_CODE = "FEMIMP_TRANS_CODE";
	public static final String DB_MOT_CODE_FEMIMP = "MOT_CODE_FEMIMP";
	public static final String DB_FEMIMP_TRANS_TYPE = "FEMIMP_TRANS_TYPE";
	public static final String DB_FEMIMP_AUTH_NO = "FEMIMP_AUTH_NO";
	public static final String DB_FEMIMP_IMP_LIC = "FEMIMP_IMP_LIC";
	public static final String DB_FEMIMP_IMP_LIC_DATE = "FEMIMP_IMP_LIC_DATE";
	public static final String DB_FEMIMP_SPARE_FLD2 = "FEMIMP_SPARE_FLD2";
	public static final String DB_FEMIMP_SPARE_FLD1 = "FEMIMP_SPARE_FLD1";
	public static final String DB_FEMIMP_COM_PREF = "FEMIMP_COM_PREF";
	public static final String DB_FEMIMP_TRANS_NO = "FEMIMP_TRANS_NO";
	public static final String DB_FEMIMP_CRDATE = "FEMIMP_CRDATE";
	public static final String DB_FEMIMP_CRTIME = "FEMIMP_CRTIME";
	public static final String DB_FEMIMP_PROS_DATE = "FEMIMP_PROS_DATE";
	public static final String DB_FEMIMP_PROS_TIME = "FEMIMP_PROS_TIME";
	public static final String DB_FEMIMP_ARRIV_PLACE = "FEMIMP_ARRIV_PLACE";
	public static final String DB_FEMIMP_VAT_VAL = "FEMIMP_VAT_VAL";
	public static final String DB_FEMIMP_SLS_VALUE = "FEMIMP_SLS_VALUE";
	public static final String DB_CURR_CODE_SLS_FEMIMP = "CURR_CODE_SLS_FEMIMP";
	public static final String DB_FEMIMP_DOC_NO = "FEMIMP_DOC_NO";
	public static final String DB_FEMIMP_EXP_LIC = "FEMIMP_EXP_LIC";
	public static final String DB_FEMIMP_EXP_LIC_DATE = "FEMIMP_EXP_LIC_DATE";
	public static final String DB_FEMIMP_SPARE_FLD3 = "FEMIMP_SPARE_FLD3";
	public static final String DB_FEMIMP_SPARE_FLD4 = "FEMIMP_SPARE_FLD4";
	public static final String DB_FEMIMP_NO_ADD_UNITS = "FEMIMP_NO_ADD_UNITS";
	public static final String DB_FEMIMP_ADD_UNITS = "FEMIMP_ADD_UNITS";
	public static final String DB_FEMIMP_OTH_COST = "FEMIMP_OTH_COST";
	public static final String DB_FEMIMP_ADD_DOC_TYPE = "FEMIMP_ADD_DOC_TYPE";
	public static final String DB_FEMIMP_ADD_DOC_NO = "FEMIMP_ADD_DOC_NO";
	public static final String DB_FEMIMP_INT_STAT = "FEMIMP_INT_STAT";
	public static final String DB_FEMIMP_SCO_IND = "FEMIMP_SCO_IND";
	public static final String DB_FEMIMP_PRELIM_IND = "FEMIMP_PRELIM_IND";
	public static final String DB_FEMIMP_TA_IND = "FEMIMP_TA_IND";
	public static final String DB_FEMIMP_CERT_NO = "FEMIMP_CERT_NO";
	
	public static final String MANDATORY_FIELDS[] ={
		DB_FEMIMP_NAW_CODE_CM,
		DB_FEMIMP_SHP_NO,
		DB_FEMIMP_SHP_LINE_NO,
		DB_FEMIMP_ARRIV_NO,
		DB_FEMIMP_ARRIV_LINE_NO,
		DB_FEMIMP_SHP_DATE,
		DB_FEMIMP_ART_NO,
		DB_FEMIMP_ART_DESC,
		DB_FEMIMP_QUAN,
		DB_FEMIMP_ARRIV_PLACE,
		DB_FEMIMP_TRANS_TYPE,
		DB_FEMIMP_SLS_VALUE,
		DB_CURR_CODE_SLS_FEMIMP,
		DB_FEMIMP_INT_STAT
		//DB_FEMIMP_CRDATE,
		//DB_FEMIMP_CRTIME
	};
	
	public Femimp() {
		this.tableDescription=getTableMetadata(DB_FEMIMP);
	}
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_FEMIMP_NAW_CODE_CM +
		"," +DB_FEMIMP_SHP_NO +
		"," +DB_FEMIMP_SHP_LINE_NO +
		"," +DB_FEMIMP_ARRIV_NO +
		"," +DB_FEMIMP_ARRIV_LINE_NO +
		"," +DB_WRH_CODE +
		"," +DB_CM_ORD_NUM_FEMIMP +
		"," +DB_CM_ORD_POS_NO_FEMIMP +
		"," +DB_FEMIMP_SHP_DATE +
		"," +DB_FEMIMP_ART_NO +
		"," +DB_FEMIMP_ART_DESC +
		"," +DB_FEMIMP_QUAN +
		"," +DB_CT_CODE_ORG +
		"," +DB_CT_CODE_PROV +
		"," +DB_CT_CODE_DEST +
		"," +DB_FEMIMP_NAW_CODE_RCV_CM +
		"," +DB_FEMIMP_NAW_CODE_RCV_BILL +
		"," +DB_FEMIMP_CUS_ART_CODE +
		"," +DB_FEMIMP_ARRIV_PLACE +
		"," +DB_FEMIMP_PREF_CODE1 +
		"," +DB_FEMIMP_PREF_CODE2 +
		"," +DB_FEMIMP_TRANS_CODE +
		"," +DB_MOT_CODE_FEMIMP +
		"," +DB_FEMIMP_TRANS_TYPE +
		"," +DB_FEMIMP_AUTH_NO +
		"," +DB_FEMIMP_CERT_NO +
		"," +DB_FEMIMP_IMP_LIC +
		"," +DB_FEMIMP_IMP_LIC_DATE +
		"," +DB_FEMIMP_COM_PREF +
		"," +DB_FEMIMP_TRANS_NO +
		"," +DB_FEMIMP_VAT_VAL +
		"," +DB_FEMIMP_SLS_VALUE +
		"," +DB_CURR_CODE_SLS_FEMIMP +
		"," +DB_FEMIMP_DOC_TYPE +
		"," +DB_FEMIMP_DOC_NO +
		"," +DB_FEMIMP_TA_IND +
		"," +DB_FEMIMP_SPARE_FLD2 +
		"," +DB_FEMIMP_SPARE_FLD1 +		
		"," +DB_FEMIMP_EXP_LIC +
		"," +DB_FEMIMP_EXP_LIC_DATE +
		"," +DB_FEMIMP_SPARE_FLD3 +
		"," +DB_FEMIMP_SPARE_FLD4 +
		"," +DB_FEMIMP_SCO_IND +
		"," +DB_FEMIMP_PRELIM_IND +		
		"," +DB_FEMIMP_NO_ADD_UNITS +		
		"," +DB_FEMIMP_ADD_UNITS +
		"," +DB_FEMIMP_OTH_COST +
		"," +DB_FEMIMP_ADD_DOC_TYPE +
		"," +DB_FEMIMP_ADD_DOC_NO +		
		"," +DB_FEMIMP_INT_STAT +
		"," +DB_FEMIMP_CRDATE +
		"," +DB_FEMIMP_CRTIME +
		"," +DB_FEMIMP_PROS_DATE +
		"," +DB_FEMIMP_PROS_TIME +
		" FROM "+getDbName()+DB_FEMIMP;
		 
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> readRecordsOfStatus(String clientCode,String status) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEMIMP_INT_STAT + "='"+status+"'";
		if(!clientCode.trim().equals("")){
			statement += " AND " + DB_FEMIMP_NAW_CODE_CM + "='"+clientCode+"'";
		}
		statement +=" ORDER BY "+DB_FEMIMP_NAW_CODE_CM+","+DB_FEMIMP_SHP_NO;

		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	public TreeMap<String, String> readFemimpRecord(String clientCode,String number,int line) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEMIMP_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMIMP_SHP_NO + "='"+number+"'";
		statement += " AND " + DB_FEMIMP_SHP_LINE_NO + "="+line+"";

		TreeMap<String, String> records = readTable(statement);
		
		return records;
	}
	
	public void updateStatus(String clientCode, String shipmentNumber,int line,String status){
		
		String statement =" UPDATE "+getDbName()+DB_FEMIMP+
				" SET "+DB_FEMIMP_INT_STAT+"='"+status+"'"+
				" WHERE "+DB_FEMIMP_NAW_CODE_CM+"='"+clientCode+"'"+
				" AND "+DB_FEMIMP_SHP_NO+"='"+shipmentNumber+"'"+
				" AND "+DB_FEMIMP_SHP_LINE_NO+"="+line;
		
		insertUpdateTable(statement);
		
				
	}
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		
		//retrieve key values
		
		String clientCode=in.get(DB_FEMIMP_NAW_CODE_CM);
		String number=in.get(DB_FEMIMP_SHP_NO);
		int line=Integer.parseInt(in.get(DB_FEMIMP_SHP_LINE_NO));
		
		TreeMap<String, String> tmpRecord =  readFemimpRecord(clientCode,number,line);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFemimp(tmpRecord);
		}
		
		retCode=insertFemimp(in);
		
		return retCode;
	}
	
	public void deleteFemimp(TreeMap<String, String> record){

		String clientCode=record.get(DB_FEMIMP_NAW_CODE_CM);
		String number=record.get(DB_FEMIMP_SHP_NO);
		int line=Integer.parseInt(record.get(DB_FEMIMP_SHP_LINE_NO));
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMIMP+ " WHERE " + DB_FEMIMP_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMIMP_SHP_NO + "='"+number+"'";
		statement += " AND " + DB_FEMIMP_SHP_LINE_NO + "="+line+"";
		
		insertUpdateTable(statement);
	}
	
	public boolean insertFemimp(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_FEMIMP+ " VALUES(";
		statement+="'"+formatString(record.get(DB_FEMIMP_NAW_CODE_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SHP_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMIMP_SHP_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ARRIV_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMIMP_ARRIV_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_WRH_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_CM_ORD_NUM_FEMIMP))+"', " ;
		statement+=formatInt(record.get(DB_CM_ORD_POS_NO_FEMIMP))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMIMP_SHP_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ART_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ART_DESC))+"', " ;
		statement+=formatInt(record.get(DB_FEMIMP_QUAN))+"," ;
		statement+="'"+formatString(record.get(DB_CT_CODE_ORG))+"', " ;
		statement+="'"+formatString(record.get(DB_CT_CODE_PROV))+"', " ;
		statement+="'"+formatString(record.get(DB_CT_CODE_DEST))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_NAW_CODE_RCV_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_NAW_CODE_RCV_BILL))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_CUS_ART_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_PREF_CODE1))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_PREF_CODE2))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_TRANS_CODE))+"', " ;
		statement+=formatInt(record.get(DB_MOT_CODE_FEMIMP))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_TRANS_TYPE))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMIMP_VAT_VAL))+"," ;
		statement+=formatDecimal(record.get(DB_FEMIMP_SLS_VALUE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_AUTH_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_IMP_LIC))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMIMP_IMP_LIC_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_EXP_LIC))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SPARE_FLD2))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_COM_PREF))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_TRANS_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMIMP_NO_ADD_UNITS))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMIMP_CRDATE))+"," ;
		statement+=formatInt(record.get(DB_FEMIMP_CRTIME))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMIMP_PROS_DATE))+"," ;
		statement+=formatInt(record.get(DB_FEMIMP_PROS_TIME))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_DOC_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SPARE_FLD1))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ARRIV_PLACE))+"', " ;
		statement+="'"+formatString(record.get(DB_CURR_CODE_SLS_FEMIMP))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_CERT_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_DOC_NO))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMIMP_EXP_LIC_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SPARE_FLD3))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SPARE_FLD4))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ADD_UNITS))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMIMP_OTH_COST))+"," ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ADD_DOC_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_ADD_DOC_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_INT_STAT))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_SCO_IND))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_PRELIM_IND))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMIMP_TA_IND))+"')" ;

		return insertUpdateTable(statement);
	}

}
