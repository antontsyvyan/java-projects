package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class Femuti extends ConnectionDB {
	public static final String DB_FEMUTI = "FEMUTI";
	public static final String DB_FEMUTI_UNIQUE_KEY = "FEMUTI_UNIQUE_KEY";
	public static final String DB_FEED_SYS_FEMUTI = "FEED_SYS_FEMUTI";
	public static final String DB_FEMUTI_IN_OUT_BOUND = "FEMUTI_IN_OUT_BOUND";
	public static final String DB_FEMUTI_JOB_NO = "FEMUTI_JOB_NO";
	public static final String DB_FEMUTI_LINE_NO = "FEMUTI_LINE_NO";
	public static final String DB_FEMUTI_TRANS_CODE = "FEMUTI_TRANS_CODE";
	public static final String DB_FEMUTI_BON_WRH_TYPE = "FEMUTI_BON_WRH_TYPE";
	public static final String DB_FEMUTI_STAT_GDS = "FEMUTI_STAT_GDS";
	public static final String DB_FEMUTI_NAW_NO = "FEMUTI_NAW_NO";
	public static final String DB_FEMUTI_DOC_TYPE = "FEMUTI_DOC_TYPE";
	public static final String DB_FEMUTI_DOC_NO = "FEMUTI_DOC_NO";
	public static final String DB_FEMUTI_DATE_ISSUE_DOC = "FEMUTI_DATE_ISSUE_DOC";
	public static final String DB_FEMUTI_TG_IND = "FEMUTI_TG_IND";
	public static final String DB_FEMUTI_PERIOD = "FEMUTI_PERIOD";
	public static final String DB_FEMUTI_MOD = "FEMUTI_MOD";
	public static final String DB_FEMUTI_PLACE_ISSUE_DOC = "FEMUTI_PLACE_ISSUE_DOC";
	public static final String DB_FEMUTI_DOC_VAL = "FEMUTI_DOC_VAL";
	public static final String DB_FEMUTI_GR_WGT = "FEMUTI_GR_WGT";
	public static final String DB_KIND_CODE_D3 = "KIND_CODE_D3";
	public static final String DB_FEMUTI_NO_PCS = "FEMUTI_NO_PCS";
	public static final String DB_FEMUTI_ART_NO = "FEMUTI_ART_NO";
	public static final String DB_FEMUTI_ART_DESC = "FEMUTI_ART_DESC";
	public static final String DB_FEMUTI_TARIFF_CODE = "FEMUTI_TARIFF_CODE";
	public static final String DB_FEMUTI_IMP_CERTIFICATE = "FEMUTI_IMP_CERTIFICATE";
	public static final String DB_FEMUTI_INVNO = "FEMUTI_INVNO";
	public static final String DB_FEMUTI_INV_LINE_NO = "FEMUTI_INV_LINE_NO";
	public static final String DB_FEMUTI_INV_DATE = "FEMUTI_INV_DATE";
	public static final String DB_KIND_CODE_D11 = "KIND_CODE_D11";
	public static final String DB_FEMUTI_NO_PCS_D12 = "FEMUTI_NO_PCS_D12";
	public static final String DB_FEMUTI_NT_WGT = "FEMUTI_NT_WGT";
	public static final String DB_FEMUTI_LOC = "FEMUTI_LOC";
	public static final String DB_FEMUTI_SPARE_FLD_D15 = "FEMUTI_SPARE_FLD_D15";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_FEMUTI_PREF_CODE1 = "FEMUTI_PREF_CODE1";
	public static final String DB_FEMUTI_PREF_CODE2 = "FEMUTI_PREF_CODE2";
	public static final String DB_FEMUTI_NO_ADD_UNITS = "FEMUTI_NO_ADD_UNITS";
	public static final String DB_FEMUTI_ADD_UNITS = "FEMUTI_ADD_UNITS";
	public static final String DB_FEMUTI_INV_PROFORMA = "FEMUTI_INV_PROFORMA";
	public static final String DB_FEMUTI_INV_PROFORMA_LINE = "FEMUTI_INV_PROFORMA_LINE";
	public static final String DB_FEMUTI_INV_VALUE = "FEMUTI_INV_VALUE";
	public static final String DB_CURR_CODE_FEMUTI = "CURR_CODE_FEMUTI";
	public static final String DB_FEMUTI_EX_RATE = "FEMUTI_EX_RATE";
	public static final String DB_FEMUTI_INV_PROF_DATE = "FEMUTI_INV_PROF_DATE";
	public static final String DB_FEMUTI_COM_PREF = "FEMUTI_COM_PREF";
	public static final String DB_FEMUTI_BOX37 = "FEMUTI_BOX37";
	public static final String DB_FEMUTI_TRANS_TYPE = "FEMUTI_TRANS_TYPE";
	public static final String DB_FEMUTI_RELATION = "FEMUTI_RELATION";
	public static final String DB_FEMUTI_RESTRIC = "FEMUTI_RESTRIC";
	public static final String DB_FEMUTI_LIC_FEE = "FEMUTI_LIC_FEE";
	public static final String DB_FEMUTI_CALC_METHOD = "FEMUTI_CALC_METHOD";
	public static final String DB_FEMUTI_VAL_DET = "FEMUTI_VAL_DET";
	public static final String DB_FEMUTI_TAR_CONT_REQ = "FEMUTI_TAR_CONT_REQ";
	public static final String DB_MOT_CODE_FEMUTI = "MOT_CODE_FEMUTI";
	public static final String DB_FEMUTI_CONT = "FEMUTI_CONT";
	public static final String DB_NAW_CODE_CM = "NAW_CODE_CM";
	public static final String DB_FEMUTI_SPEC_INDI = "FEMUTI_SPEC_INDI";
	public static final String DB_FEMUTI_STAN_PERC_DUTIES = "FEMUTI_STAN_PERC_DUTIES";
	public static final String DB_FEMUTI_TRANS_CODE_K7 = "FEMUTI_TRANS_CODE_K7";
	public static final String DB_FEMUTI_TRANS_NO = "FEMUTI_TRANS_NO";
	public static final String DB_FEMUTI_TRANS_DATE = "FEMUTI_TRANS_DATE";
	public static final String DB_FEMUTI_NT_WGT_K13 = "FEMUTI_NT_WGT_K13";
	public static final String DB_FEMUTI_ADD_FLDK15 = "FEMUTI_ADD_FLDK15";
	public static final String DB_FEMUTI_ADD_FLDK16 = "FEMUTI_ADD_FLDK16";
	public static final String DB_FEMUTI_ADD_FLDK17 = "FEMUTI_ADD_FLDK17";
	public static final String DB_FEMUTI_ADD_FLDK18 = "FEMUTI_ADD_FLDK18";
	public static final String DB_FEMUTI_ADD_FLDK19 = "FEMUTI_ADD_FLDK19";
	public static final String DB_FEMUTI_ADD_FLDK20 = "FEMUTI_ADD_FLDK20";
	public static final String DB_FEMUTI_ADD_FLDK21 = "FEMUTI_ADD_FLDK21";
	public static final String DB_FEMUTI_ADD_FLDK22 = "FEMUTI_ADD_FLDK22";
	public static final String DB_FEMUTI_ADD_FLDK23 = "FEMUTI_ADD_FLDK23";
	public static final String DB_FEMUTI_STAT = "FEMUTI_STAT";
	public static final String DB_FEMUTI_DOC_CODE = "FEMUTI_DOC_CODE";
	public static final String DB_FEMUTI_A3 = "FEMUTI_A3";
	
	private Ranges ranges= new Ranges();

	
	private TreeMap<String, String> emptyDetailRecord = new TreeMap<String, String>();
	
	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_FEMUTI_UNIQUE_KEY +
				"," +DB_FEED_SYS_FEMUTI +
				"," +DB_FEMUTI_IN_OUT_BOUND +
				"," +DB_FEMUTI_JOB_NO +
				"," +DB_FEMUTI_LINE_NO +
				"," +DB_FEMUTI_TRANS_CODE +
				"," +DB_FEMUTI_BON_WRH_TYPE +
				"," +DB_FEMUTI_STAT_GDS +
				"," +DB_FEMUTI_NAW_NO +
				"," +DB_FEMUTI_DOC_TYPE +
				"," +DB_FEMUTI_DOC_NO +
				"," +DB_FEMUTI_DATE_ISSUE_DOC +
				"," +DB_FEMUTI_TG_IND +
				"," +DB_FEMUTI_PERIOD +
				"," +DB_FEMUTI_MOD +
				"," +DB_FEMUTI_PLACE_ISSUE_DOC +
				"," +DB_FEMUTI_DOC_VAL +
				"," +DB_FEMUTI_GR_WGT +
				"," +DB_KIND_CODE_D3 +
				"," +DB_FEMUTI_NO_PCS +
				"," +DB_FEMUTI_ART_NO +
				"," +DB_FEMUTI_ART_DESC +
				"," +DB_FEMUTI_TARIFF_CODE +
				"," +DB_FEMUTI_IMP_CERTIFICATE +
				"," +DB_FEMUTI_INVNO +
				"," +DB_FEMUTI_INV_LINE_NO +
				"," +DB_FEMUTI_INV_DATE +
				"," +DB_KIND_CODE_D11 +
				"," +DB_FEMUTI_NO_PCS_D12 +
				"," +DB_FEMUTI_NT_WGT +
				"," +DB_FEMUTI_LOC +
				"," +DB_FEMUTI_SPARE_FLD_D15 +
				"," +DB_CT_CODE_ORG +
				"," +DB_CT_CODE_PROV +
				"," +DB_FEMUTI_PREF_CODE1 +
				"," +DB_FEMUTI_PREF_CODE2 +
				"," +DB_FEMUTI_NO_ADD_UNITS +
				"," +DB_FEMUTI_ADD_UNITS +
				"," +DB_FEMUTI_INV_PROFORMA +
				"," +DB_FEMUTI_INV_PROFORMA_LINE +
				"," +DB_FEMUTI_INV_VALUE +
				"," +DB_CURR_CODE_FEMUTI +
				"," +DB_FEMUTI_EX_RATE +
				"," +DB_FEMUTI_INV_PROF_DATE +
				"," +DB_FEMUTI_COM_PREF +
				"," +DB_FEMUTI_BOX37 +
				"," +DB_FEMUTI_TRANS_TYPE +
				"," +DB_FEMUTI_RELATION +
				"," +DB_FEMUTI_RESTRIC +
				"," +DB_FEMUTI_LIC_FEE +
				"," +DB_FEMUTI_CALC_METHOD +
				"," +DB_FEMUTI_VAL_DET +
				"," +DB_FEMUTI_TAR_CONT_REQ +
				"," +DB_MOT_CODE_FEMUTI +
				"," +DB_FEMUTI_CONT +
				"," +DB_NAW_CODE_CM +
				"," +DB_FEMUTI_SPEC_INDI +
				"," +DB_FEMUTI_STAN_PERC_DUTIES +
				"," +DB_FEMUTI_TRANS_CODE_K7 +
				"," +DB_FEMUTI_TRANS_NO +
				"," +DB_FEMUTI_TRANS_DATE +
				"," +DB_FEMUTI_NT_WGT_K13 +
				"," +DB_FEMUTI_ADD_FLDK15 +
				"," +DB_FEMUTI_ADD_FLDK16 +
				"," +DB_FEMUTI_ADD_FLDK17 +
				"," +DB_FEMUTI_ADD_FLDK18 +
				"," +DB_FEMUTI_ADD_FLDK19 +
				"," +DB_FEMUTI_ADD_FLDK20 +
				"," +DB_FEMUTI_ADD_FLDK21 +
				"," +DB_FEMUTI_ADD_FLDK22 +
				"," +DB_FEMUTI_ADD_FLDK23 +
				"," +DB_FEMUTI_STAT +
				"," +DB_FEMUTI_DOC_CODE +
				"," +DB_FEMUTI_A3 +
				" FROM "+getDbName()+DB_FEMUTI;
		return selectPart;
	}
	
	public Femuti() {
		this.tableDescription=getTableMetadata(DB_FEMUTI);		
		this.emptyDetailRecord=getEmptyDetails();
	}
	
	public TreeMap<String, String> readFemutiRecord(String uniqueKey) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEMUTI_UNIQUE_KEY+"='"+uniqueKey+"'";	
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}
	
	public TreeMap<String, String> readFemutiRecord(String feedSystem,String type, String jobNumber, int jobLineNumber) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEED_SYS_FEMUTI+"='"+feedSystem+"'"+
				" AND "+DB_FEMUTI_IN_OUT_BOUND+"='"+type+"'"+
				" AND "+DB_FEMUTI_JOB_NO+"='"+jobNumber+"'"+
				" AND "+DB_FEMUTI_LINE_NO+"="+jobLineNumber;
		
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}
	
	public ArrayList<TreeMap<String, String>> readRecordsForPeriod(String feedSystem, String clientCode,String period,String status) {
		String statement=getSelectPart()+ " WHERE " + DB_FEED_SYS_FEMUTI+"='"+ feedSystem+"'";
		if(!clientCode.trim().equals("")){
			statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
		}
		statement+=" AND "+DB_FEMUTI_PERIOD+"='"+period+"'";
		statement+=" AND "+DB_FEMUTI_STAT+"='"+status+"'";
		statement+=" ORDER BY "+DB_FEED_SYS_FEMUTI+","+DB_NAW_CODE_CM+","+DB_FEMUTI_ART_NO+","+DB_CURR_CODE_FEMUTI;
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	
	public ArrayList<TreeMap<String, String>> readArticleRecordsForPeriod(String feedSystem, String clientCode,String period,String article,String currency,String status) {
		String statement=getSelectPart()+ " WHERE " + DB_FEED_SYS_FEMUTI+"='"+ feedSystem+"'";
		statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
		statement+=" AND "+DB_FEMUTI_PERIOD+"='"+period+"'";
		statement+=" AND "+DB_FEMUTI_ART_NO+"='"+article+"'";
		statement+=" AND "+DB_CURR_CODE_FEMUTI+"='"+currency+"'";
		statement+=" AND "+DB_FEMUTI_STAT+"='"+status+"'";
		
		statement+=" ORDER BY "+DB_FEED_SYS_FEMUTI+","+DB_NAW_CODE_CM+","+DB_FEMUTI_ART_NO+","+DB_CURR_CODE_FEMUTI;
		ArrayList<TreeMap<String, String>> records = readTableMultiple(statement);
		return records;
	}
	
	public boolean updateFemutiStatusForPeriod(String feedSystem, String clientCode,String period,String statusFrom,String statusTo){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_FEMUTI+ " SET "+DB_FEMUTI_STAT+"='"+statusTo+"'";
			statement+=" WHERE " + DB_FEED_SYS_FEMUTI+"='"+ feedSystem+"'";
			
			if(!clientCode.trim().equals("")){
				statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
			}
			statement+=" AND "+DB_FEMUTI_PERIOD+"='"+period+"'";
			statement+=" AND "+DB_FEMUTI_STAT+"='"+statusFrom+"'";
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public boolean updateFemutiStatus(String feedSystem,String type, String jobNumber, int jobLineNumber,String status){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_FEMUTI+ " SET "+DB_FEMUTI_STAT+"='"+status+"'";
			statement+=" WHERE " + DB_FEED_SYS_FEMUTI+"='"+ feedSystem+"'";
			
			statement+=" AND "+DB_FEMUTI_IN_OUT_BOUND+"='"+type+"'";
			statement+=" AND "+DB_FEMUTI_JOB_NO+"='"+jobNumber+"'";
			statement+=" AND "+DB_FEMUTI_LINE_NO+"="+jobLineNumber;
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public boolean insertUpdateFemuti(TreeMap<String, String> record){
		
		boolean ret=true;
		//get keys	
		String uniqueKey=record.get(DB_FEMUTI_UNIQUE_KEY);
		
		String a3Field="";
		TreeMap<String, String> tmpRecord =readFemutiRecord(uniqueKey);
			
		if(tmpRecord!=null && tmpRecord.size()>0){
			a3Field=tmpRecord.get(DB_FEMUTI_A3);
			//delete record
			deleteFemuti(tmpRecord);
		}else{
			a3Field=ranges.getUniqueIdentifier(Ranges.TYPE_A3, Resources.getSetting("customer.name"));;
		}
		String statement="INSERT INTO "+getDbName()+DB_FEMUTI+ " VALUES(";		
		try{
			statement+="'"+formatString(record.get(DB_FEMUTI_UNIQUE_KEY))+"', " ;
			statement+="'"+formatString(record.get(DB_FEED_SYS_FEMUTI))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_IN_OUT_BOUND))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_JOB_NO))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_LINE_NO))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TRANS_CODE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_BON_WRH_TYPE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_STAT_GDS))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_NAW_NO))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_DOC_TYPE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_DOC_NO))+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTI_DATE_ISSUE_DOC))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TG_IND))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_PERIOD))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_MOD))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_PLACE_ISSUE_DOC))+"', " ;
			statement+=formatDecimal(record.get(DB_FEMUTI_DOC_VAL))+"," ;
			statement+=formatInt(record.get(DB_FEMUTI_GR_WGT))+"," ;
			statement+="'"+formatString(record.get(DB_KIND_CODE_D3))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_NO_PCS))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ART_NO))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ART_DESC))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TARIFF_CODE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_IMP_CERTIFICATE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_INVNO))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_INV_LINE_NO))+"," ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTI_INV_DATE))+"," ;
			statement+="'"+formatString(record.get(DB_KIND_CODE_D11))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_NO_PCS_D12))+"," ;
			statement+=formatInt(record.get(DB_FEMUTI_NT_WGT))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_LOC))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_SPARE_FLD_D15))+"', " ;
			statement+="'"+formatString(record.get(DB_CT_CODE_ORG))+"', " ;
			statement+="'"+formatString(record.get(DB_CT_CODE_PROV))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_PREF_CODE1))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_PREF_CODE2))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_NO_ADD_UNITS))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_UNITS))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_INV_PROFORMA))+"', " ;
			statement+=formatInt(record.get(DB_FEMUTI_INV_PROFORMA_LINE))+"," ;
			statement+=formatDecimal(record.get(DB_FEMUTI_INV_VALUE))+"," ;
			statement+="'"+formatString(record.get(DB_CURR_CODE_FEMUTI))+"', " ;
			statement+=formatDecimal(record.get(DB_FEMUTI_EX_RATE))+"," ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTI_INV_PROF_DATE))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_COM_PREF))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_BOX37))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TRANS_TYPE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_RELATION))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_RESTRIC))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_LIC_FEE))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_CALC_METHOD))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_VAL_DET))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TAR_CONT_REQ))+"', " ;
			statement+=formatInt(record.get(DB_MOT_CODE_FEMUTI))+"," ;
			statement+=formatInt(record.get(DB_FEMUTI_CONT))+"," ;
			statement+="'"+formatString(record.get(DB_NAW_CODE_CM))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_SPEC_INDI))+"', " ;
			statement+=formatDecimal(record.get(DB_FEMUTI_STAN_PERC_DUTIES))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TRANS_CODE_K7))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_TRANS_NO))+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTI_TRANS_DATE))+"," ;
			statement+=formatInt(record.get(DB_FEMUTI_NT_WGT_K13))+"," ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK15))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK16))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK17))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK18))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK19))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK20))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK21))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK22))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_ADD_FLDK23))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_STAT))+"', " ;
			statement+="'"+formatString(record.get(DB_FEMUTI_DOC_CODE))+"', " ;
			statement+="'"+a3Field+"')" ;
		
		
			ret=insertUpdateTable(statement);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
		
		return ret;
	}
		
	public void deletePeriod(String feedSystem,String period){
		
		String statement = "DELETE FROM " +getDbName()+DB_FEMUTI+ " WHERE " +DB_FEED_SYS_FEMUTI+"='"+feedSystem+"'"+
				" AND "+DB_FEMUTI_PERIOD+"='"+period+"'";
		
		insertUpdateTable(statement);
	}
	
	public void deleteFemuti(TreeMap<String, String> record){
		//get keys
	
		String uniqueKey=record.get(DB_FEMUTI_UNIQUE_KEY);
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMUTI+ " WHERE " +DB_FEMUTI_UNIQUE_KEY+"='"+uniqueKey+"'";

		insertUpdateTable(statement);
	}
	
	public TreeMap<String, String> mapToStock(TreeMap<String, String> in){
		
		TreeMap<String, String> out=null;
		
		if(in!=null){
			try{
				out=new TreeMap<String, String>();
								
				out.put(Stock.DB_FEED_SYS_STOCK, in.get(DB_FEED_SYS_FEMUTI));
				out.put(Stock.DB_STOCK_PERIOD, in.get(DB_FEMUTI_PERIOD));
				out.put(Stock.DB_STOCK_UNIQ_LN_NO, "");
				out.put(Stock.DB_STOCK_JOB_NO_IN, in.get(DB_FEMUTI_JOB_NO));
				out.put(Stock.DB_STOCK_LN_NO_IN, in.get(DB_FEMUTI_LINE_NO));				
				out.put(Stock.DB_STOCK_LN_NO_OUT, "");
				out.put(Stock.DB_STOCK_JOB_NO_OUT, "");				
				out.put(Stock.DB_NAW_CODE_CM, in.get(DB_NAW_CODE_CM));
				out.put(Stock.DB_STOCK_LEG_STAT, in.get(DB_FEMUTI_SPEC_INDI));			
				out.put(Stock.DB_STOCK_ART_NO, in.get(DB_FEMUTI_ART_NO));
				out.put(Stock.DB_STOCK_TARIFF_CODE, in.get(DB_FEMUTI_TARIFF_CODE));
				out.put(Stock.DB_STOCK_IMP_DUT_PERC, in.get(DB_FEMUTI_STAN_PERC_DUTIES));
				out.put(Stock.DB_STOCK_TRANS_NO, "");
				out.put(Stock.DB_STOCK_TRANS_TYPE, in.get(DB_FEMUTI_TRANS_CODE_K7));			
				out.put(Stock.DB_CURR_CODE_STOCK, in.get(DB_CURR_CODE_FEMUTI));
				out.put(Stock.DB_STOCK_EX_RATE, in.get(DB_FEMUTI_EX_RATE));				
				out.put(Stock.DB_STOCK_TRANS_DATE, in.get(DB_FEMUTI_TRANS_DATE));
				out.put(Stock.DB_STOCK_NT_WGT, in.get(DB_FEMUTI_NT_WGT_K13));
				out.put(Stock.DB_STOCK_FACULK15, in.get(DB_FEMUTI_ADD_FLDK15));
				out.put(Stock.DB_STOCK_FACULK16, in.get(DB_FEMUTI_ADD_FLDK16));
				out.put(Stock.DB_STOCK_FACULK17, in.get(DB_FEMUTI_ADD_FLDK17));
				out.put(Stock.DB_STOCK_FACULK18, in.get(DB_FEMUTI_ADD_FLDK18));
				out.put(Stock.DB_STOCK_FACULK19, in.get(DB_FEMUTI_ADD_FLDK19));
				out.put(Stock.DB_STOCK_FACULK20, in.get(DB_FEMUTI_ADD_FLDK20));
				out.put(Stock.DB_STOCK_FACULK21, in.get(DB_FEMUTI_ADD_FLDK21));
				out.put(Stock.DB_STOCK_FACULK22, in.get(DB_FEMUTI_ADD_FLDK22));
				out.put(Stock.DB_STOCK_FACULK23, in.get(DB_FEMUTI_ADD_FLDK23));				
				out.put(Stock.DB_STOCK_ADM_UNIT, in.get(DB_KIND_CODE_D11));
				out.put(Stock.DB_STOCK_PCS, in.get(DB_FEMUTI_NO_PCS_D12));
				out.put(Stock.DB_STOCK_AMT, in.get(DB_FEMUTI_INV_VALUE));
				
			}catch(Exception ex){
				return null;
			}
		}
		
		return out;
		
	}
	
	
	public TreeMap<String, String> mapToDisk(TreeMap<String, String> in) {

		TreeMap<String, String> out = null;
		if (in != null) {
			try {
				out = new TreeMap<String, String>();
				
				out.put(Disk.BLOCK_DIRECTION, Processors.INBOUND);
				
				out.put(Disk.BLOCK_A0, Disk.BLOCK_A);
				out.put(Disk.BLOCK_A1, in.get(DB_FEMUTI_PERIOD));
				out.put(Disk.BLOCK_A2, in.get(DB_FEMUTI_TRANS_CODE));
				out.put(Disk.BLOCK_A3, in.get(DB_FEMUTI_A3));
				out.put(Disk.BLOCK_B0, Disk.BLOCK_B);
				out.put(Disk.BLOCK_B1, in.get(DB_FEMUTI_DOC_TYPE));
				out.put(Disk.BLOCK_B2, in.get(DB_FEMUTI_DOC_NO));
				out.put(Disk.BLOCK_B3, in.get(DB_FEMUTI_PLACE_ISSUE_DOC));
				out.put(Disk.BLOCK_B4, DateUtils.convertDateToCustoms(in.get(DB_FEMUTI_DATE_ISSUE_DOC)));
				out.put(Disk.BLOCK_B5, in.get(DB_FEMUTI_TG_IND));
				out.put(Disk.BLOCK_D0, Disk.BLOCK_D);
				out.put(Disk.BLOCK_D1, in.get(DB_FEMUTI_DOC_VAL));
				out.put(Disk.BLOCK_D2, in.get(DB_FEMUTI_GR_WGT));
				out.put(Disk.BLOCK_D3, in.get(DB_KIND_CODE_D3));
				out.put(Disk.BLOCK_D4, in.get(DB_FEMUTI_NO_PCS));
				out.put(Disk.BLOCK_D5, in.get(DB_FEMUTI_ART_NO)); //
				out.put(Disk.BLOCK_D6, in.get(DB_FEMUTI_TARIFF_CODE));
				out.put(Disk.BLOCK_D7, in.get(DB_FEMUTI_IMP_CERTIFICATE));
				out.put(Disk.BLOCK_D8, in.get(DB_FEMUTI_INVNO));
				out.put(Disk.BLOCK_D9, in.get(DB_FEMUTI_INV_LINE_NO));
				out.put(Disk.BLOCK_D10,DateUtils.convertDateToCustoms(in.get(DB_FEMUTI_INV_DATE)));
				out.put(Disk.BLOCK_D11,in.get(DB_KIND_CODE_D11));
				out.put(Disk.BLOCK_D12,in.get(DB_FEMUTI_NO_PCS_D12));
				out.put(Disk.BLOCK_D13,in.get(DB_FEMUTI_NT_WGT_K13));
				out.put(Disk.BLOCK_D14,in.get(DB_FEMUTI_LOC));
				out.put(Disk.BLOCK_D15,"");
				out.put(Disk.BLOCK_E0, Disk.BLOCK_E);
				out.put(Disk.BLOCK_E1, in.get(DB_CT_CODE_ORG));
				out.put(Disk.BLOCK_E2, in.get(DB_CT_CODE_PROV));
				out.put(Disk.BLOCK_E3, "");
				out.put(Disk.BLOCK_E4, "");
				out.put(Disk.BLOCK_E5, "");
				out.put(Disk.BLOCK_E6, "");
				out.put(Disk.BLOCK_E7, "");
				out.put(Disk.BLOCK_E8, "");
				out.put(Disk.BLOCK_E9, "");
				out.put(Disk.BLOCK_E10,"");
				out.put(Disk.BLOCK_E11,"");
				out.put(Disk.BLOCK_E12,"");
				out.put(Disk.BLOCK_E13,"");
				out.put(Disk.BLOCK_E14,"");
				out.put(Disk.BLOCK_E15,"");
				out.put(Disk.BLOCK_E16,in.get(DB_FEMUTI_ART_NO));// Should be the same as in K.3 and as D5
				out.put(Disk.BLOCK_E17,in.get(DB_FEMUTI_TARIFF_CODE));
				out.put(Disk.BLOCK_E18,"N");
				out.put(Disk.BLOCK_E19,in.get(DB_FEMUTI_ADD_UNITS));
				out.put(Disk.BLOCK_E20,in.get(DB_FEMUTI_NO_ADD_UNITS));
				out.put(Disk.BLOCK_E21,in.get(DB_FEMUTI_INV_PROFORMA));
				out.put(Disk.BLOCK_E22,in.get(DB_FEMUTI_INV_PROFORMA_LINE));
				out.put(Disk.BLOCK_E23,"");
				out.put(Disk.BLOCK_E24,"");
				out.put(Disk.BLOCK_E25,in.get(DB_FEMUTI_INV_VALUE));
				out.put(Disk.BLOCK_E26,in.get(DB_CURR_CODE_FEMUTI));
				out.put(Disk.BLOCK_E27,in.get(DB_FEMUTI_EX_RATE));
				out.put(Disk.BLOCK_E28,"0");
				out.put(Disk.BLOCK_E29,"0");
				out.put(Disk.BLOCK_E30,"0");
				out.put(Disk.BLOCK_E31,"0");
				out.put(Disk.BLOCK_E32,in.get(DB_FEMUTI_STAN_PERC_DUTIES));
				out.put(Disk.BLOCK_E33,"0");
				out.put(Disk.BLOCK_E34,"");
				out.put(Disk.BLOCK_E35,"0");
				out.put(Disk.BLOCK_E36,in.get(DB_FEMUTI_BOX37));
				out.put(Disk.BLOCK_E37,DateUtils.convertDateToCustoms(in.get(DB_FEMUTI_INV_PROF_DATE)));
				out.put(Disk.BLOCK_E38,"");
				out.put(Disk.BLOCK_E39,"");
				out.put(Disk.BLOCK_E40,"");
				out.put(Disk.BLOCK_E41,"");
				out.put(Disk.BLOCK_E42,"");
				out.put(Disk.BLOCK_E43,"");
				out.put(Disk.BLOCK_E44,"");
				out.put(Disk.BLOCK_E45,in.get(DB_FEMUTI_CALC_METHOD));
				out.put(Disk.BLOCK_E46,"");
				out.put(Disk.BLOCK_E47,"");
				out.put(Disk.BLOCK_E48,in.get(DB_MOT_CODE_FEMUTI));
				out.put(Disk.BLOCK_E49,in.get(DB_FEMUTI_CONT));
				out.put(Disk.BLOCK_F0, Disk.BLOCK_F);
				out.put(Disk.BLOCK_F1, "");
				out.put(Disk.BLOCK_F2, "");
				out.put(Disk.BLOCK_F3, "");
				out.put(Disk.BLOCK_F4, "");
				out.put(Disk.BLOCK_F5, "");
				out.put(Disk.BLOCK_F6, "");
				out.put(Disk.BLOCK_F7, "");
				out.put(Disk.BLOCK_G0, Disk.BLOCK_G);
				out.put(Disk.BLOCK_G1, "");
				out.put(Disk.BLOCK_G2, "");
				out.put(Disk.BLOCK_G3, "");
				out.put(Disk.BLOCK_G4, "");
				out.put(Disk.BLOCK_G5, "");
				out.put(Disk.BLOCK_G6, "");
				out.put(Disk.BLOCK_G7, "");
				out.put(Disk.BLOCK_G8, "");
				out.put(Disk.BLOCK_G9, "");
				out.put(Disk.BLOCK_G10,"");
				out.put(Disk.BLOCK_G11,"");
				out.put(Disk.BLOCK_G12,"");
				out.put(Disk.BLOCK_G13,"");
				out.put(Disk.BLOCK_G14,"");
				out.put(Disk.BLOCK_G15,"");
				out.put(Disk.BLOCK_H0, "");
				out.put(Disk.BLOCK_H1, "");
				out.put(Disk.BLOCK_H2, "");
				out.put(Disk.BLOCK_H3, "");
				out.put(Disk.BLOCK_H4, "");
				out.put(Disk.BLOCK_H5, "");
				out.put(Disk.BLOCK_H6, "");
				out.put(Disk.BLOCK_H7, "");
				out.put(Disk.BLOCK_H8, "");
				out.put(Disk.BLOCK_H9, "");
				out.put(Disk.BLOCK_H10,"");
				out.put(Disk.BLOCK_H11,"");
				out.put(Disk.BLOCK_H12,"");
				out.put(Disk.BLOCK_H13,"");
				out.put(Disk.BLOCK_H14,"");
				out.put(Disk.BLOCK_H15,"");
				out.put(Disk.BLOCK_H16,"");
				out.put(Disk.BLOCK_H17,"");
				out.put(Disk.BLOCK_H18,"");
				out.put(Disk.BLOCK_J0, Disk.BLOCK_E);
				out.put(Disk.BLOCK_J1, "");
				out.put(Disk.BLOCK_J2, "");
				out.put(Disk.BLOCK_J3, "");
				out.put(Disk.BLOCK_J4, "");
				out.put(Disk.BLOCK_J5, "");
				out.put(Disk.BLOCK_K0, Disk.BLOCK_K);
				out.put(Disk.BLOCK_K1, in.get(DB_NAW_CODE_CM));
				out.put(Disk.BLOCK_K2, in.get(DB_FEMUTI_SPEC_INDI));
				out.put(Disk.BLOCK_K3, in.get(DB_FEMUTI_ART_NO));// Should be the same as in E.16
				out.put(Disk.BLOCK_K4, in.get(DB_FEMUTI_TARIFF_CODE));
				out.put(Disk.BLOCK_K5, in.get(DB_FEMUTI_STAN_PERC_DUTIES));
				out.put(Disk.BLOCK_K6, in.get(DB_KIND_CODE_D11));
				out.put(Disk.BLOCK_K7, in.get(DB_FEMUTI_TRANS_CODE_K7));
				out.put(Disk.BLOCK_K8, in.get(DB_FEMUTI_TRANS_NO));
				out.put(Disk.BLOCK_K9, in.get(DB_FEMUTI_NO_PCS_D12));
				out.put(Disk.BLOCK_K10,in.get(DB_FEMUTI_INV_VALUE));
				out.put(Disk.BLOCK_K11,in.get(DB_CURR_CODE_FEMUTI));
				out.put(Disk.BLOCK_K12,in.get(DB_FEMUTI_EX_RATE));
				out.put(Disk.BLOCK_K13,DateUtils.convertDateToCustoms(in.get(DB_FEMUTI_TRANS_DATE)));
				out.put(Disk.BLOCK_K14,in.get(DB_FEMUTI_NT_WGT_K13));
				out.put(Disk.BLOCK_K15,in.get(DB_FEMUTI_ADD_FLDK15));
				out.put(Disk.BLOCK_K16,in.get(DB_FEMUTI_ADD_FLDK16));
				out.put(Disk.BLOCK_K17,in.get(DB_FEMUTI_ADD_FLDK17));
				out.put(Disk.BLOCK_K18,in.get(DB_FEMUTI_ADD_FLDK18));
				out.put(Disk.BLOCK_K19,in.get(DB_FEMUTI_ADD_FLDK19));
				out.put(Disk.BLOCK_K20,in.get(DB_FEMUTI_ADD_FLDK20));
				out.put(Disk.BLOCK_K21,in.get(DB_FEMUTI_ADD_FLDK21));
				out.put(Disk.BLOCK_K22,in.get(DB_FEMUTI_ADD_FLDK22));
				out.put(Disk.BLOCK_K23,in.get(DB_FEMUTI_ADD_FLDK23));
				/*
				out.put(Disk.BLOCK_M0, Disk.BLOCK_M);
				out.put(Disk.BLOCK_M1, "");
				out.put(Disk.BLOCK_M2, "");
				out.put(Disk.BLOCK_M3, "0");
				out.put(Disk.BLOCK_M4, "");
				out.put(Disk.BLOCK_M5, "0.0");
				out.put(Disk.BLOCK_M6, "");
				out.put(Disk.BLOCK_M7, "0.0");
				out.put(Disk.BLOCK_M8, "");
				out.put(Disk.BLOCK_M9, "");
				out.put(Disk.BLOCK_M10, "");
				out.put(Disk.BLOCK_M11, "");
				out.put(Disk.BLOCK_M12, "");
				out.put(Disk.BLOCK_M13, "");
				out.put(Disk.BLOCK_M14, "");
				out.put(Disk.BLOCK_M15, "");
				out.put(Disk.BLOCK_M16, "");

				*/
				//TODO Mapping femuti to disk
			} catch (Exception ex) {
				return null;
			}
		}
		return out;
	}
	public TreeMap<String, String> formatRecord(TreeMap<String, String> record){
		
		return formatRecord(record,tableDescription);
	}
	
	public TreeMap<String, String> getEmptyDetailRecord() {
		return emptyDetailRecord;
	}
	/*
	private String getUniqueIdentifier(TreeMap<String, String> in){
		String identifier="";
		identifier+="2";
		identifier+=String.format("%-3s", in.get(DB_NAW_CODE_CM));
		identifier+="I";
		identifier+=String.format("%8s", in.get(DB_FEMUTI_JOB_NO)).replace(' ', '0');
		identifier+=String.format("%7s", in.get(DB_FEMUTI_LINE_NO)).replace(' ', '0');
		return identifier;
	}
	*/
}
