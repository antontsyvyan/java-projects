package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.utils.DateUtils;

public class Feminb extends ConnectionDB {

	public static final String DB_FEMINB = "FEMINB";
	public static final String DB_FEMINB_NAW_CODE_CM = "FEMINB_NAW_CODE_CM";
	public static final String DB_FEMINB_ARRV_NO = "FEMINB_ARRV_NO";
	public static final String DB_FEMINB_ARRIV_LINE_NO = "FEMINB_ARRIV_LINE_NO";
	public static final String DB_WRH_CODE = "WRH_CODE";
	public static final String DB_FEMINB_DOC_TYPE = "FEMINB_DOC_TYPE";
	public static final String DB_FEMINB_ARRV_DATE = "FEMINB_ARRV_DATE";
	public static final String DB_FEMINB_MRN = "FEMINB_MRN";
	public static final String DB_FEMINB_DOC_ISSUE_PLACE = "FEMINB_DOC_ISSUE_PLACE";
	public static final String DB_FEMINB_DOC_ISSUE_DATE = "FEMINB_DOC_ISSUE_DATE";
	public static final String DB_FEMINB_TG_IND = "FEMINB_TG_IND";
	public static final String DB_FEMINB_INVNO = "FEMINB_INVNO";
	public static final String DB_FEMINB_INV_LINE_NO = "FEMINB_INV_LINE_NO";
	public static final String DB_FEMINB_INV_DATE = "FEMINB_INV_DATE";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_FEMINB_ART_NO = "FEMINB_ART_NO";
	public static final String DB_FEMINB_ART_DESC = "FEMINB_ART_DESC";
	public static final String DB_KIND_CODE_FEMINB = "KIND_CODE_FEMINB";
	public static final String DB_FEMINB_NO_PCS = "FEMINB_NO_PCS";
	public static final String DB_FEMINB_NO_ADD_UNIT = "FEMINB_NO_ADD_UNIT";
	public static final String DB_FEMINB_ADD_UNITS = "FEMINB_ADD_UNITS";
	public static final String DB_FEMINB_INV_VALUE = "FEMINB_INV_VALUE";
	public static final String DB_CURR_CODE_FEMINB = "CURR_CODE_FEMINB";
	public static final String DB_FEMINB_GR_WGT = "FEMINB_GR_WGT";
	public static final String DB_FEMINB_NET_WGT = "FEMINB_NET_WGT";
	public static final String DB_FEMINB_INCO_TERM = "FEMINB_INCO_TERM";
	public static final String DB_FEMINB_DELIV_PLACE = "FEMINB_DELIV_PLACE";
	public static final String DB_CURR_CODE_FRT_FEMINB = "CURR_CODE_FRT_FEMINB";
	public static final String DB_FEMINB_FRT_AMT = "FEMINB_FRT_AMT";
	public static final String DB_CURR_CODE_INS_FEMINB = "CURR_CODE_INS_FEMINB";
	public static final String DB_FEMINB_INS_AMT = "FEMINB_INS_AMT";
	public static final String DB_FEMINB_PREF_CODE1 = "FEMINB_PREF_CODE1";
	public static final String DB_FEMINB_PREF_CODE2 = "FEMINB_PREF_CODE2";
	public static final String DB_FEMINB_TRANS_CODE = "FEMINB_TRANS_CODE";
	public static final String DB_MOT_CODE_FEMINB = "MOT_CODE_FEMINB";
	public static final String DB_FEMINB_TRANS_TYPE = "FEMINB_TRANS_TYPE";
	public static final String DB_FEMINB_CONT = "FEMINB_CONT";
	public static final String DB_CONT_NO_FEMINB = "CONT_NO_FEMINB";
	public static final String DB_FEMINB_AUTH_NO = "FEMINB_AUTH_NO";
	public static final String DB_FEMINB_IMP_LIC = "FEMINB_IMP_LIC";
	public static final String DB_FEMINB_IMP_LIC_DATE = "FEMINB_IMP_LIC_DATE";
	public static final String DB_FEMINB_LOC = "FEMINB_LOC";
	public static final String DB_FEMINB_SPARE_FLD = "FEMINB_SPARE_FLD";
	public static final String DB_FEMINB_COM_PREF = "FEMINB_COM_PREF";
	public static final String DB_FEMINB_TRANS_NO = "FEMINB_TRANS_NO";
	public static final String DB_FEMINB_INT_STAT = "FEMINB_INT_STAT";
	public static final String DB_FEMINB_CRDATE = "FEMINB_CRDATE";
	public static final String DB_FEMINB_CRTIME = "FEMINB_CRTIME";
	public static final String DB_FEMINB_PROS_DATE = "FEMINB_PROS_DATE";
	public static final String DB_FEMINB_PROS_TIME = "FEMINB_PROS_TIME";
	public static final String DB_KIND_CODE_FEMINB_D11 = "KIND_CODE_FEMINB_D11";
	public static final String DB_FEMINB_NO_PCS_D12 = "FEMINB_NO_PCS_D12";
		
	public static final String MANDATORY_FIELDS[] ={
			 DB_FEMINB_NAW_CODE_CM,
			 DB_FEMINB_ARRV_NO,
			 DB_FEMINB_ARRIV_LINE_NO,
			 DB_FEMINB_ARRV_DATE,
			 DB_FEMINB_DOC_TYPE,
			 DB_FEMINB_MRN,
			 DB_FEMINB_DOC_ISSUE_PLACE,
			 DB_FEMINB_DOC_ISSUE_DATE,
			 DB_FEMINB_TG_IND,
			 DB_FEMINB_INVNO,
			 DB_FEMINB_INV_DATE,
			 DB_CT_CODE_ORG,
			 DB_CT_CODE_PROV,
			 DB_FEMINB_ART_NO,
			 DB_FEMINB_ART_DESC,
			 DB_KIND_CODE_FEMINB,
			 DB_FEMINB_NO_PCS,
			 DB_KIND_CODE_FEMINB_D11,
			 DB_FEMINB_NO_PCS_D12,
			 //DB_FEMINB_NO_ADD_UNIT,
			 //DB_FEMINB_ADD_UNITS,
			 DB_FEMINB_INV_VALUE,
			 DB_CURR_CODE_FEMINB,
			 DB_FEMINB_GR_WGT,
			 DB_FEMINB_NET_WGT,
			 DB_FEMINB_INCO_TERM,
			 DB_FEMINB_DELIV_PLACE,
			 DB_MOT_CODE_FEMINB,
			 DB_FEMINB_TRANS_TYPE,
			 DB_FEMINB_CONT,
			 DB_FEMINB_CRDATE,
			 DB_FEMINB_CRTIME				 
			 				 
	 };
	
	public Feminb() {
		this.tableDescription=getTableMetadata(DB_FEMINB);
	}
	
	public Feminb(boolean flag){
		super(flag);
		this.tableDescription=getTableMetadata(DB_FEMINB);
	}
	
	public String getSelectPart(){
		
		String selectPart ="SELECT " +
				DB_FEMINB_NAW_CODE_CM +
				"," +DB_FEMINB_ARRIV_LINE_NO +
				"," +DB_WRH_CODE +
				"," +DB_FEMINB_DOC_TYPE +
				"," +DB_FEMINB_ARRV_DATE +
				"," +DB_FEMINB_ARRV_NO +
				"," +DB_FEMINB_MRN +
				"," +DB_FEMINB_DOC_ISSUE_PLACE +
				"," +DB_FEMINB_DOC_ISSUE_DATE +
				"," +DB_FEMINB_TG_IND +
				"," +DB_FEMINB_INVNO +
				"," +DB_FEMINB_INV_LINE_NO +
				"," +DB_FEMINB_INV_DATE +
				"," +DB_CT_CODE_ORG +
				"," +DB_CT_CODE_PROV +
				"," +DB_FEMINB_ART_NO +
				"," +DB_FEMINB_ART_DESC +
				"," +DB_KIND_CODE_FEMINB +
				"," +DB_FEMINB_NO_PCS +
				"," +DB_FEMINB_NO_ADD_UNIT +
				"," +DB_FEMINB_ADD_UNITS +
				"," +DB_FEMINB_INV_VALUE +
				"," +DB_CURR_CODE_FEMINB +
				"," +DB_FEMINB_GR_WGT +
				"," +DB_FEMINB_NET_WGT +
				"," +DB_FEMINB_INCO_TERM +
				"," +DB_FEMINB_DELIV_PLACE +
				"," +DB_CURR_CODE_FRT_FEMINB +
				"," +DB_FEMINB_FRT_AMT +
				"," +DB_CURR_CODE_INS_FEMINB +
				"," +DB_FEMINB_INS_AMT +
				"," +DB_FEMINB_PREF_CODE1 +
				"," +DB_FEMINB_PREF_CODE2 +
				"," +DB_FEMINB_TRANS_CODE +
				"," +DB_MOT_CODE_FEMINB +
				"," +DB_FEMINB_TRANS_TYPE +
				"," +DB_FEMINB_CONT +
				"," +DB_CONT_NO_FEMINB +
				"," +DB_FEMINB_AUTH_NO +
				"," +DB_FEMINB_IMP_LIC +
				"," +DB_FEMINB_IMP_LIC_DATE +
				"," +DB_FEMINB_LOC +
				"," +DB_FEMINB_SPARE_FLD +
				"," +DB_FEMINB_COM_PREF +
				"," +DB_FEMINB_TRANS_NO +
				"," +DB_FEMINB_INT_STAT +
				"," +DB_FEMINB_CRDATE +
				"," +DB_FEMINB_CRTIME +
				"," +DB_FEMINB_PROS_DATE +
				"," +DB_FEMINB_PROS_TIME +
				"," +DB_KIND_CODE_FEMINB_D11 +
				"," +DB_FEMINB_NO_PCS_D12 +
				" FROM "+getDbName()+DB_FEMINB;
		
		return selectPart;
	}
	
	
	public TreeMap<String, String> readFeminbRecord(String clientCode,String number,int line) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEMINB_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMINB_ARRV_NO + "='"+number+"'";
		statement += " AND " + DB_FEMINB_ARRIV_LINE_NO + "="+line+"";

		TreeMap<String, String> records = readTable(statement);
		
		return records;
	}
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		
		//retrieve key values
		
		String clientCode=in.get(DB_FEMINB_NAW_CODE_CM);
		String number=in.get(DB_FEMINB_ARRV_NO);
		int line=Integer.parseInt(in.get(DB_FEMINB_ARRIV_LINE_NO));
		
		TreeMap<String, String> tmpRecord =  readFeminbRecord(clientCode,number,line);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteFeminb(tmpRecord);
		}
		
		retCode=insertFeminb(in);
		
		return retCode;
	}
	
	
	public void deleteFeminb(TreeMap<String, String> record){

		String clientCode=record.get(DB_FEMINB_NAW_CODE_CM);
		String number=record.get(DB_FEMINB_ARRV_NO);
		int line=Integer.parseInt(record.get(DB_FEMINB_ARRIV_LINE_NO));
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMINB+ " WHERE " + DB_FEMINB_NAW_CODE_CM + "='"+clientCode+"'";
		statement += " AND " + DB_FEMINB_ARRV_NO + "='"+number+"'";
		statement += " AND " + DB_FEMINB_ARRIV_LINE_NO + "="+line+"";
		
		insertUpdateTable(statement);
	}
	
	public ArrayList<TreeMap<String, String>> readRecordsOfStatus(String clientCode,String status) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEMINB_INT_STAT + "='"+status+"'";
		if(!clientCode.trim().equals("")){
			statement += " AND " + DB_FEMINB_NAW_CODE_CM + "='"+clientCode+"'";
		}
		statement +=" ORDER BY "+DB_FEMINB_NAW_CODE_CM+","+DB_FEMINB_ARRV_NO;

		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	
	public void updateStatus(String clientCode, String arrivalNumber,int line,String status){
		
		String statement =" UPDATE "+getDbName()+DB_FEMINB+
				" SET "+DB_FEMINB_INT_STAT+"='"+status+"'"+
				" WHERE "+DB_FEMINB_NAW_CODE_CM+"='"+clientCode+"'"+
				" AND "+DB_FEMINB_ARRV_NO+"='"+arrivalNumber+"'"+
				" AND "+DB_FEMINB_ARRIV_LINE_NO+"="+line;
		
		insertUpdateTable(statement);
		
				
	}
	
	public boolean insertFeminb(TreeMap<String, String> record){
		
		
		String statement="INSERT INTO "+getDbName()+DB_FEMINB+ " VALUES(";
		statement+="'"+formatString(record.get(DB_FEMINB_NAW_CODE_CM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_ARRV_NO))+"', " ;
		statement+=formatInt(record.get(DB_FEMINB_ARRIV_LINE_NO))+"," ;
		statement+="'"+formatString(record.get(DB_WRH_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_DOC_TYPE))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_ARRV_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_MRN))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_DOC_ISSUE_PLACE))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_DOC_ISSUE_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_TG_IND))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_INVNO))+"', " ;
		statement+=formatInt(record.get(DB_FEMINB_INV_LINE_NO))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_INV_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_CT_CODE_ORG))+"', " ;
		statement+="'"+formatString(record.get(DB_CT_CODE_PROV))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_ART_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_ART_DESC))+"', " ;
		statement+="'"+formatString(record.get(DB_KIND_CODE_FEMINB))+"', " ;
		statement+=formatInt(record.get(DB_FEMINB_NO_PCS))+"," ;
		statement+=formatInt(record.get(DB_FEMINB_NO_ADD_UNIT))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_ADD_UNITS))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMINB_INV_VALUE))+"," ;
		statement+="'"+formatString(record.get(DB_CURR_CODE_FEMINB))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMINB_GR_WGT))+"," ;
		statement+=formatDecimal(record.get(DB_FEMINB_NET_WGT))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_INCO_TERM))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_DELIV_PLACE))+"', " ;
		statement+="'"+formatString(record.get(DB_CURR_CODE_FRT_FEMINB))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMINB_FRT_AMT))+"," ;
		statement+="'"+formatString(record.get(DB_CURR_CODE_INS_FEMINB))+"', " ;
		statement+=formatDecimal(record.get(DB_FEMINB_INS_AMT))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_PREF_CODE1))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_PREF_CODE2))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_TRANS_CODE))+"', " ;
		statement+=formatInt(record.get(DB_MOT_CODE_FEMINB))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_TRANS_TYPE))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_CONT))+"', " ;
		statement+="'"+formatString(record.get(DB_CONT_NO_FEMINB))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_AUTH_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_IMP_LIC))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_IMP_LIC_DATE))+"," ;
		statement+="'"+formatString(record.get(DB_FEMINB_LOC))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_SPARE_FLD))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_COM_PREF))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_TRANS_NO))+"', " ;
		statement+="'"+formatString(record.get(DB_FEMINB_INT_STAT))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_CRDATE))+"," ;
		statement+=formatInt(record.get(DB_FEMINB_CRTIME))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_FEMINB_PROS_DATE))+"," ;
		statement+=formatInt(record.get(DB_FEMINB_PROS_TIME))+"," ;
		statement+="'"+formatString(record.get(DB_KIND_CODE_FEMINB_D11))+"', " ;
		statement+=formatInt(record.get(DB_FEMINB_NO_PCS_D12))+")" ;
		
		return insertUpdateTable(statement);
	}
}
