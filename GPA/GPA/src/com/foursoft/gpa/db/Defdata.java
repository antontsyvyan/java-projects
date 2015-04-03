package com.foursoft.gpa.db;

import java.util.TreeMap;

public class Defdata extends ConnectionDB {
	
	//Table name
	public static final String DB_DEFDATA = "defdata";
	//Keys
	public static final String DB_FEED_SYS_DEFDATA = "FEED_SYS_DEFDATA";
	public static final String DB_DEFDATA_PRGM = "DEFDATA_PRGM";
	public static final String DB_DEFDATA_IN_OUT_BOUND = "DEFDATA_IN_OUT_BOUND";
	public static final String DB_DEFDATA_TRANS_CODE = "DEFDATA_TRANS_CODE";
	public static final String DB_DEFDATA_DECL_TYPE = "DEFDATA_DECL_TYPE";
	public static final String DB_DEFDATA_TARIFF_CODE = "DEFDATA_TARIFF_CODE";
	//Other fields
	public static final String DB_DEFDATA_NAW_NO = "DEFDATA_NAW_NO";
	public static final String DB_DEFDATA_DOC_TYPE = "DEFDATA_DOC_TYPE";
	public static final String DB_DOC_NO = "DOC_NO";
	public static final String DB_DEFDATA_DATE_ISSUE_DOC = "DEFDATA_DATE_ISSUE_DOC";
	public static final String DB_DEFDATA_TG_IND = "DEFDATA_TG_IND";
	public static final String DB_DEFDATA_PLACE_ISSUE_DOC = "DEFDATA_PLACE_ISSUE_DOC";
	public static final String DB_DEFDATA_DEFDATA_DOC_VAL = "DEFDATA_DEFDATA_DOC_VAL";
	public static final String DB_DEFDATA_ART_NO = "DEFDATA_ART_NO";
	public static final String DB_DEFDATA_ART_DESC = "DEFDATA_ART_DESC";
	public static final String DB_DEFDATA_IMP_CERTIFICATE = "DEFDATA_IMP_CERTIFICATE";
	public static final String DB_DEFDATA_INVNO = "DEFDATA_INVNO";
	public static final String DB_DEFDATA_INV_LN_NO = "DEFDATA_INV_LN_NO";
	public static final String DB_DEFDATA_INV_DATE = "DEFDATA_INV_DATE";
	public static final String DB_DEFDATA_KIND_CODE_D11 = "DEFDATA_KIND_CODE_D11";
	public static final String DB_DEFDATA_NO_PCS_D12 = "DEFDATA_NO_PCS_D12";
	public static final String DB_DEFDATA_NT_WGT = "DEFDATA_NT_WGT";
	public static final String DB_DEFDATA_LOC = "DEFDATA_LOC";
	public static final String DB_DEFDATA_SPARE_FLD_D15 = "DEFDATA_SPARE_FLD_D15";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_DEFDATA_PREF_CODE1 = "DEFDATA_PREF_CODE1";
	public static final String DB_DEFDATA_PREF_CODE2 = "DEFDATA_PREF_CODE2";
	public static final String DB_DEFDATA_NO_ADD_UNITS = "DEFDATA_NO_ADD_UNITS";
	public static final String DB_DEFDATA_ADD_UNITS = "DEFDATA_ADD_UNITS";
	public static final String DB_DEFDATA_INV_PROF = "DEFDATA_INV_PROF";
	public static final String DB_DEFDATA_INV_PROF_LINE = "DEFDATA_INV_PROF_LINE";
	public static final String DB_DEFDATA_INV_VAL = "DEFDATA_INV_VAL";
	public static final String DB_CURR_CODE_DEFDATA = "CURR_CODE_DEFDATA";
	public static final String DB_DEFDATA_EX_RATE = "DEFDATA_EX_RATE";
	public static final String DB_DEFDATA_INV_PROF_DATE = "DEFDATA_INV_PROF_DATE";
	public static final String DB_DEFDATA_COM_PREF = "DEFDATA_COM_PREF";
	public static final String DB_DEFDATA_BOX37 = "DEFDATA_BOX37";
	public static final String DB_DEFDATA_TRANS_TYPE = "DEFDATA_TRANS_TYPE";
	public static final String DB_DEFDATA_RELATION = "DEFDATA_RELATION";
	public static final String DB_DEFDATA_RESTRIC = "DEFDATA_RESTRIC";
	public static final String DB_DEFDATA_LIC_FEE = "DEFDATA_LIC_FEE";
	public static final String DB_DEFDATA_CALC_METHOD = "DEFDATA_CALC_METHOD";
	public static final String DB_DEFDATA_VAL_DET = "DEFDATA_VAL_DET";
	public static final String DB_DEFDATA_TARIFF_CONT_REQ = "DEFDATA_TARIFF_CONT_REQ";
	public static final String DB_MOT_CODE_DEFDATA = "MOT_CODE_DEFDATA";
	public static final String DB_DEFDATA_CONT = "DEFDATA_CONT";
	public static final String DB_NAW_CODE_CM = "NAW_CODE_CM";
	public static final String DB_DEFDATA_SPEC_INDI = "DEFDATA_SPEC_INDI";
	public static final String DB_DEFDATA_STAN_PERC_DUTIES = "DEFDATA_STAN_PERC_DUTIES";
	public static final String DB_DEFDATA_TRANS_CODE_K7 = "DEFDATA_TRANS_CODE_K7";
	public static final String DB_DEFDATA_TRANS_NO = "DEFDATA_TRANS_NO";
	public static final String DB_DEFDATA_TRANS_DATE = "DEFDATA_TRANS_DATE";
	public static final String DB_DEFDATA_AUTH_NO = "DEFDATA_AUTH_NO";
	public static final String DB_DEFDATA_IMP_LIC = "DEFDATA_IMP_LIC";
	public static final String DB_DEFDATA_IMP_LIC_DATE = "DEFDATA_IMP_LIC_DATE";
	public static final String DB_DEFDATA_SCO_IND = "DEFDATA_SCO_IND";
	public static final String DB_DEFDATA_PRELIM_IND = "DEFDATA_PRELIM_IND";
	public static final String DB_DEFDATA_CERTIFICATE_NO = "DEFDATA_CERTIFICATE_NO";
	public static final String DB_NAW_CODE_CONS = "NAW_CODE_CONS";
	public static final String DB_DEFDATA_CONS_NAME = "DEFDATA_CONS_NAME";
	public static final String DB_DEFDATA_CONS_CITY = "DEFDATA_CONS_CITY";
	public static final String DB_CONS_VAT_NO = "CONS_VAT_NO";
	public static final String DB_DEFDATA_VERIFY_BY_CUST = "DEFDATA_VERIFY_BY_CUST";
	public static final String DB_DEFDATA_INCO_TERMS = "DEFDATA_INCO_TERMS";
	public static final String DB_DEFDATA_DELIV_PLACE = "DEFDATA_DELIV_PLACE";
	public static final String DB_DEFDATA_FRT_AMT_EUR = "DEFDATA_FRT_AMT_EUR";
	public static final String DB_DEFDATA_INS_AMT_EUR = "DEFDATA_INS_AMT_EUR";
	public static final String DB_DEFDATA_ADD_DED_EUR = "DEFDATA_ADD_DED_EUR";
	public static final String DB_DEFDATA_CUST_VAL_EUR = "DEFDATA_CUST_VAL_EUR";
	public static final String DB_DEFDATA_IMP_DUT_PERC = "DEFDATA_IMP_DUT_PERC";
	public static final String DB_DEFDATA_AMT_RELIEF = "DEFDATA_AMT_RELIEF";
	public static final String DB_DEFDATA_CUST_DUT_SPEC = "DEFDATA_CUST_DUT_SPEC";
	public static final String DB_DEFDATA_CUST_DUTIES = "DEFDATA_CUST_DUTIES";
	public static final String DB_DEFDATA_DOC_NO = "DEFDATA_DOC_NO";
	public static final String DB_DEFDATA_TYPE_OF_CHRGS = "DEFDATA_TYPE_OF_CHRGS";
	public static final String DB_DEFDATA_MANFACT = "DEFDATA_MANFACT";
	public static final String DB_DEFDATA_AMT_OF_CHRGS = "DEFDATA_AMT_OF_CHRGS";
	public static final String DB_DEFDATA_AGRI_LEV = "DEFDATA_AGRI_LEV";
	public static final String DB_DEFDATA_VAT_AMT = "DEFDATA_VAT_AMT";
	public static final String DB_DEFDATA_VAT_CONV_VAL = "DEFDATA_VAT_CONV_VAL";
	public static final String DB_DEFDATA_EXCISE_CODE_G1 = "DEFDATA_EXCISE_CODE_G1";
	public static final String DB_DEFDATA_EXCISE_AMT_G1 = "DEFDATA_EXCISE_AMT_G1";
	public static final String DB_DEFDATA_EXCISE_CODE_G3 = "DEFDATA_EXCISE_CODE_G3";
	public static final String DB_DEFDATA_EXCISE_AMT_G3 = "DEFDATA_EXCISE_AMT_G3";
	public static final String DB_DEFDATA_EXCISE_CODE_G5 = "DEFDATA_EXCISE_CODE_G5";
	public static final String DB_DEFDATA_EXCISE_AMT_G5 = "DEFDATA_EXCISE_AMT_G5";
	public static final String DB_DEFDATA_EXCISE_CODE_G7 = "DEFDATA_EXCISE_CODE_G7";
	public static final String DB_DEFDATA_EXCISE_AMT_G7 = "DEFDATA_EXCISE_AMT_G7";
	public static final String DB_DEFDATA_EXT_PERC = "DEFDATA_EXT_PERC";
	public static final String DB_DEFDATA_ACT_ALCH_STE = "DEFDATA_ACT_ALCH_STE";
	public static final String DB_DEFDATA_ALC_FREE_LIT = "DEFDATA_ALC_FREE_LIT";
	public static final String DB_DEFDATA_LTS_OIL = "DEFDATA_LTS_OIL";
	public static final String DB_DEFDATA_PER_KG = "DEFDATA_PER_KG";
	public static final String DB_DEFDATA_PURE_ALC_LTS = "DEFDATA_PURE_ALC_LTS";
	public static final String DB_DEFDATA_TRAN_DOC = "DEFDATA_TRAN_DOC";
	public static final String DB_DEFDATA_PACK_UNIT = "DEFDATA_PACK_UNIT";
	public static final String DB_DEFDATA_NO_OF_PACK = "DEFDATA_NO_OF_PACK";
	public static final String DB_DEFDATA_INV_SLS = "DEFDATA_INV_SLS";
	public static final String DB_DEFDATA_INV_SLS_LINE = "DEFDATA_INV_SLS_LINE";
	public static final String DB_DEFDATA_INV_SLS_DATE = "DEFDATA_INV_SLS_DATE";
	public static final String DB_DEFDATA_QUAN1 = "DEFDATA_QUAN1";
	public static final String DB_DEFDATA_QUAN2 = "DEFDATA_QUAN2";
	public static final String DB_DEFDATA_EXP_LIC_NUM = "DEFDATA_EXP_LIC_NUM";
	public static final String DB_DEFDATA_EXP_LIC_DATE = "DEFDATA_EXP_LIC_DATE";
	public static final String DB_CT_CODE_DEST = "CT_CODE_DEST";
	public static final String DB_DEFDATA_BASE_CODE2 = "DEFDATA_BASE_CODE2";
	public static final String DB_DEFDATA_DOC_CODE1 = "DEFDATA_DOC_CODE1";
	public static final String DB_DEFDATA_DOC_CODE2 = "DEFDATA_DOC_CODE2";
	public static final String DB_DEFDATA_DOC_CODE3 = "DEFDATA_DOC_CODE3";
	public static final String DB_DEFDATA_DOC_CODE4 = "DEFDATA_DOC_CODE4";
	public static final String DB_DEFDATA_BASE_CODE1 = "DEFDATA_BASE_CODE1";
	public static final String DB_DEFDATA_MTH_PAY = "DEFDATA_MTH_PAY";
	public static final String DB_DEFDATA_LIC_NO = "DEFDATA_LIC_NO";
	public static final String DB_CT_CODE_DISPATCH = "CT_CODE_DISPATCH";
	public static final String DB_DEFDATA_COMM_CODE = "DEFDATA_COMM_CODE";
	public static final String DB_DEFDATA_STAT_CUST_GDS = "DEFDATA_STAT_CUST_GDS";
	public static final String DB_DEFDATA_CRN_NO = "DEFDATA_CRN_NO";
	public static final String DB_DEFDATA_GR_WGT = "DEFDATA_GR_WGT";
	public static final String DB_DEFDATA_KIND_CODE_D3 = "DEFDATA_KIND_CODE_D3";
	public static final String DB_DEFDATA_TRANS_INBOUND = "DEFDATA_TRANS_INBOUND";
	public static final String DB_DEFDATA_SPARE_FLD = "DEFDATA_SPARE_FLD";
	public static final String DB_DEFDATA_CM_CODE = "DEFDATA_CM_CODE";
	public static final String DB_DEFDATA_TRANS_TYPE_L6 = "DEFDATA_TRANS_TYPE_L6";
	public static final String DB_DEFDATA_TRANS_NO_L7 = "DEFDATA_TRANS_NO_L7";
	public static final String DB_DEFDATA_DOC_TYPE_L13 = "DEFDATA_DOC_TYPE_L13";
	public static final String DB_DEFDATA_DOC_NO_L14 = "DEFDATA_DOC_NO_L14";
	public static final String DB_DEFDATA_SPARE_FLD_L16 = "DEFDATA_SPARE_FLD_L16";
	public static final String DB_DEFDATA_SPARE_FLD_M7 = "DEFDATA_SPARE_FLD_M7";
	public static final String DB_DEFDATA_CM_CODE_N1 = "DEFDATA_CM_CODE_N1";
	public static final String DB_DEFDATA_IND_LIQ_FREE = "DEFDATA_IND_LIQ_FREE";
	public static final String DB_DEFDATA_TRANS_TYPE_N6 = "DEFDATA_TRANS_TYPE_N6";
	public static final String DB_DEFDATA_TRANS_NO_N7 = "DEFDATA_TRANS_NO_N7";
	public static final String DB_DEFDATA_SPARE_FLD_N16 = "DEFDATA_SPARE_FLD_N16";
	public static final String DB_DEFDATA_IND_LIQ_DUE = "DEFDATA_IND_LIQ_DUE";
	public static final String DB_DEFDATA_MEM_ST = "DEFDATA_MEM_ST";
	public static final String DB_DEFDATA_DOC_CODE = "DEFDATA_DOC_CODE";
	public static final String DB_DEFDATA_ART_NO_E16 = "DEFDATA_ART_NO_E16";
	public static final String DB_DEFDATA_ART_DESC_E22 = "DEFDATA_ART_DESC_E22";
	public static final String DB_DEFDATA_NO_OF_PCS = "DEFDATA_NO_OF_PCS";
	public static final String DB_DEFDATA_VAT_PREF_CODE = "DEFDATA_VAT_PREF_CODE";

	public Defdata(){
		super();
	}
	
	public String getSelectPart(){ 
		
	String selectPart="SELECT " +DB_FEED_SYS_DEFDATA +
			"," +DB_DEFDATA_IN_OUT_BOUND +
			"," +DB_DEFDATA_TRANS_CODE +
			"," +DB_DEFDATA_NAW_NO +
			"," +DB_DEFDATA_DOC_TYPE +
			"," +DB_DOC_NO +
			"," +DB_DEFDATA_DATE_ISSUE_DOC +
			"," +DB_DEFDATA_TG_IND +
			"," +DB_DEFDATA_PLACE_ISSUE_DOC +
			"," +DB_DEFDATA_DEFDATA_DOC_VAL +
			"," +DB_DEFDATA_ART_NO +
			"," +DB_DEFDATA_ART_DESC +
			"," +DB_DEFDATA_IMP_CERTIFICATE +
			"," +DB_DEFDATA_INVNO +
			"," +DB_DEFDATA_INV_LN_NO +
			"," +DB_DEFDATA_INV_DATE +
			"," +DB_DEFDATA_KIND_CODE_D11 +
			"," +DB_DEFDATA_NO_PCS_D12 +
			"," +DB_DEFDATA_NT_WGT +
			"," +DB_DEFDATA_LOC +
			"," +DB_DEFDATA_SPARE_FLD_D15 +
			"," +DB_CT_CODE_ORG +
			"," +DB_CT_CODE_PROV +
			"," +DB_DEFDATA_PREF_CODE1 +
			"," +DB_DEFDATA_PREF_CODE2 +
			"," +DB_DEFDATA_NO_ADD_UNITS +
			"," +DB_DEFDATA_ADD_UNITS +
			"," +DB_DEFDATA_INV_PROF +
			"," +DB_DEFDATA_INV_PROF_LINE +
			"," +DB_DEFDATA_INV_VAL +
			"," +DB_CURR_CODE_DEFDATA +
			"," +DB_DEFDATA_EX_RATE +
			"," +DB_DEFDATA_INV_PROF_DATE +
			"," +DB_DEFDATA_COM_PREF +
			"," +DB_DEFDATA_BOX37 +
			"," +DB_DEFDATA_TRANS_TYPE +
			"," +DB_DEFDATA_RELATION +
			"," +DB_DEFDATA_RESTRIC +
			"," +DB_DEFDATA_LIC_FEE +
			"," +DB_DEFDATA_CALC_METHOD +
			"," +DB_DEFDATA_VAL_DET +
			"," +DB_DEFDATA_TARIFF_CONT_REQ +
			"," +DB_MOT_CODE_DEFDATA +
			"," +DB_DEFDATA_CONT +
			"," +DB_NAW_CODE_CM +
			"," +DB_DEFDATA_SPEC_INDI +
			"," +DB_DEFDATA_STAN_PERC_DUTIES +
			"," +DB_DEFDATA_TRANS_CODE_K7 +
			"," +DB_DEFDATA_TRANS_NO +
			"," +DB_DEFDATA_TRANS_DATE +
			"," +DB_DEFDATA_AUTH_NO +
			"," +DB_DEFDATA_IMP_LIC +
			"," +DB_DEFDATA_IMP_LIC_DATE +
			"," +DB_DEFDATA_SCO_IND +
			"," +DB_DEFDATA_PRELIM_IND +
			"," +DB_DEFDATA_CERTIFICATE_NO +
			"," +DB_NAW_CODE_CONS +
			"," +DB_DEFDATA_CONS_NAME +
			"," +DB_DEFDATA_CONS_CITY +
			"," +DB_CONS_VAT_NO +
			"," +DB_DEFDATA_VERIFY_BY_CUST +
			"," +DB_DEFDATA_INCO_TERMS +
			"," +DB_DEFDATA_DELIV_PLACE +
			"," +DB_DEFDATA_FRT_AMT_EUR +
			"," +DB_DEFDATA_INS_AMT_EUR +
			"," +DB_DEFDATA_ADD_DED_EUR +
			"," +DB_DEFDATA_CUST_VAL_EUR +
			"," +DB_DEFDATA_IMP_DUT_PERC +
			"," +DB_DEFDATA_AMT_RELIEF +
			"," +DB_DEFDATA_CUST_DUT_SPEC +
			"," +DB_DEFDATA_CUST_DUTIES +
			"," +DB_DEFDATA_DOC_NO +
			"," +DB_DEFDATA_TYPE_OF_CHRGS +
			"," +DB_DEFDATA_MANFACT +
			"," +DB_DEFDATA_AMT_OF_CHRGS +
			"," +DB_DEFDATA_AGRI_LEV +
			"," +DB_DEFDATA_VAT_AMT +
			"," +DB_DEFDATA_VAT_CONV_VAL +
			"," +DB_DEFDATA_EXCISE_CODE_G1 +
			"," +DB_DEFDATA_EXCISE_AMT_G1 +
			"," +DB_DEFDATA_EXCISE_CODE_G3 +
			"," +DB_DEFDATA_EXCISE_AMT_G3 +
			"," +DB_DEFDATA_EXCISE_CODE_G5 +
			"," +DB_DEFDATA_EXCISE_AMT_G5 +
			"," +DB_DEFDATA_EXCISE_CODE_G7 +
			"," +DB_DEFDATA_EXCISE_AMT_G7 +
			"," +DB_DEFDATA_EXT_PERC +
			"," +DB_DEFDATA_ACT_ALCH_STE +
			"," +DB_DEFDATA_ALC_FREE_LIT +
			"," +DB_DEFDATA_LTS_OIL +
			"," +DB_DEFDATA_PER_KG +
			"," +DB_DEFDATA_PURE_ALC_LTS +
			"," +DB_DEFDATA_TRAN_DOC +
			"," +DB_DEFDATA_PACK_UNIT +
			"," +DB_DEFDATA_NO_OF_PACK +
			"," +DB_DEFDATA_INV_SLS +
			"," +DB_DEFDATA_INV_SLS_LINE +
			"," +DB_DEFDATA_INV_SLS_DATE +
			"," +DB_DEFDATA_QUAN1 +
			"," +DB_DEFDATA_QUAN2 +
			"," +DB_DEFDATA_EXP_LIC_NUM +
			"," +DB_DEFDATA_EXP_LIC_DATE +
			"," +DB_CT_CODE_DEST +
			"," +DB_DEFDATA_BASE_CODE2 +
			"," +DB_DEFDATA_DOC_CODE1 +
			"," +DB_DEFDATA_DOC_CODE2 +
			"," +DB_DEFDATA_DOC_CODE3 +
			"," +DB_DEFDATA_DOC_CODE4 +
			"," +DB_DEFDATA_BASE_CODE1 +
			"," +DB_DEFDATA_MTH_PAY +
			"," +DB_DEFDATA_PRGM +
			"," +DB_DEFDATA_DECL_TYPE +
			"," +DB_DEFDATA_TARIFF_CODE +
			"," +DB_DEFDATA_LIC_NO +
			"," +DB_CT_CODE_DISPATCH +
			"," +DB_DEFDATA_COMM_CODE +
			"," +DB_DEFDATA_STAT_CUST_GDS +
			"," +DB_DEFDATA_CRN_NO +
			"," +DB_DEFDATA_GR_WGT +
			"," +DB_DEFDATA_KIND_CODE_D3 +
			"," +DB_DEFDATA_TRANS_INBOUND +
			"," +DB_DEFDATA_SPARE_FLD +
			"," +DB_DEFDATA_CM_CODE +
			"," +DB_DEFDATA_TRANS_TYPE_L6 +
			"," +DB_DEFDATA_TRANS_NO_L7 +
			"," +DB_DEFDATA_DOC_TYPE_L13 +
			"," +DB_DEFDATA_DOC_NO_L14 +
			"," +DB_DEFDATA_SPARE_FLD_L16 +
			"," +DB_DEFDATA_SPARE_FLD_M7 +
			"," +DB_DEFDATA_CM_CODE_N1 +
			"," +DB_DEFDATA_IND_LIQ_FREE +
			"," +DB_DEFDATA_TRANS_TYPE_N6 +
			"," +DB_DEFDATA_TRANS_NO_N7 +
			"," +DB_DEFDATA_SPARE_FLD_N16 +
			"," +DB_DEFDATA_IND_LIQ_DUE +
			"," +DB_DEFDATA_MEM_ST +
			"," +DB_DEFDATA_DOC_CODE +
			"," +DB_DEFDATA_ART_NO_E16 +
			"," +DB_DEFDATA_ART_DESC_E22 +
			"," +DB_DEFDATA_NO_OF_PCS +
			"," +DB_DEFDATA_VAT_PREF_CODE +
			" FROM "+getDbName()+DB_DEFDATA;
	
	return selectPart;
	}
	
	public TreeMap<String, String> readDefaultData(String feedSystem,String program,String type,String transCode,String declType,String tariffCode) {
		
		String statement = getSelectPart() + " WHERE " + DB_FEED_SYS_DEFDATA + "='"	+ feedSystem + "'"+
				" AND " + DB_DEFDATA_PRGM + "='"	+ program + "'"+
				" AND " + DB_DEFDATA_IN_OUT_BOUND + "='"+ type + "'"+
				" AND " + DB_DEFDATA_TRANS_CODE + "='"+ transCode + "'"+
				" AND " + DB_DEFDATA_DECL_TYPE + "='"+ declType + "'"+
				" AND " + DB_DEFDATA_TARIFF_CODE + "='"+ tariffCode + "'"
				;
		TreeMap<String, String> record = readTable(statement);
		return record;
	}

}
