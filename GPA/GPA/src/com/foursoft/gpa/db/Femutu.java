package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class Femutu extends ConnectionDB {

	public static final String DB_FEMUTU = "FEMUTU";
	public static final String DB_FEED_SYS_FEMUTU = "FEED_SYS_FEMUTU";
	public static final String DB_FEMUTU_IN_OUT_BOUND = "FEMUTU_IN_OUT_BOUND";
	public static final String DB_FEMUTU_JOB_NO = "FEMUTU_JOB_NO";
	public static final String DB_FEMUTU_LINE_NO = "FEMUTU_LINE_NO";
	public static final String DB_FEMUTU_TRANS_CODE = "FEMUTU_TRANS_CODE";
	public static final String DB_FEMUTU_BON_WRH_TYPE = "FEMUTU_BON_WRH_TYPE";
	public static final String DB_FEMUTU_STAT_GDS = "FEMUTU_STAT_GDS";
	public static final String DB_FEMUTU_NAW_NO = "FEMUTU_NAW_NO";
	public static final String DB_FEMUTU_DOC_TYPE = "FEMUTU_DOC_TYPE";
	public static final String DB_FEMUTU_ADD_DOC_NO = "FEMUTU_ADD_DOC_NO";
	public static final String DB_FEMUTU_DT_ISSUE_DOC = "FEMUTU_DT_ISSUE_DOC";
	public static final String DB_FEMUTU_TG_IND = "FEMUTU_TG_IND";
	public static final String DB_FEMUTU_PERIOD = "FEMUTU_PERIOD";
	public static final String DB_FEMUTU_MOD = "FEMUTU_MOD";
	public static final String DB_FEMUTU_PLACE_ISSUE_DOC = "FEMUTU_PLACE_ISSUE_DOC";
	public static final String DB_FEMUTU_DOC_VAL = "FEMUTU_DOC_VAL";
	public static final String DB_FEMUTU_ART_NO = "FEMUTU_ART_NO";
	public static final String DB_FEMUTU_ART_DESC = "FEMUTU_ART_DESC";
	public static final String DB_FEMUTU_TARIFF_CODE = "FEMUTU_TARIFF_CODE";
	public static final String DB_FEMUTU_IMP_CERTIFICATE = "FEMUTU_IMP_CERTIFICATE";
	public static final String DB_FEMUTU_INVNO = "FEMUTU_INVNO";
	public static final String DB_FEMUTU_INV_LN_NO = "FEMUTU_INV_LN_NO";
	public static final String DB_FEMUTU_INV_DATE = "FEMUTU_INV_DATE";
	public static final String DB_FEMUTU_KIND_CODE_D11 = "FEMUTU_KIND_CODE_D11";
	public static final String DB_FEMUTU_NO_PCS_D12 = "FEMUTU_NO_PCS_D12";
	public static final String DB_FEMUTU_NT_WGT = "FEMUTU_NT_WGT";
	public static final String DB_FEMUTU_LOC = "FEMUTU_LOC";
	public static final String DB_FEMUTU_SPARE_FLD_D15 = "FEMUTU_SPARE_FLD_D15";
	public static final String DB_CT_CODE_ORG = "CT_CODE_ORG";
	public static final String DB_CT_CODE_PROV = "CT_CODE_PROV";
	public static final String DB_FEMUTU_PREF_CODE1 = "FEMUTU_PREF_CODE1";
	public static final String DB_FEMUTU_PREF_CODE2 = "FEMUTU_PREF_CODE2";
	public static final String DB_FEMUTU_NO_ADD_UNITS = "FEMUTU_NO_ADD_UNITS";
	public static final String DB_FEMUTU_ADD_UNITS = "FEMUTU_ADD_UNITS";
	public static final String DB_FEMUTU_INV_PROF = "FEMUTU_INV_PROF";
	public static final String DB_FEMUTU_INV_PROF_LINE = "FEMUTU_INV_PROF_LINE";
	public static final String DB_FEMUTU_INV_VAL = "FEMUTU_INV_VAL";
	public static final String DB_CURR_CODE_FEMUTU = "CURR_CODE_FEMUTU";
	public static final String DB_FEMUTU_EX_RATE = "FEMUTU_EX_RATE";
	public static final String DB_FEMUTU_INV_PROF_DATE = "FEMUTU_INV_PROF_DATE";
	public static final String DB_FEMUTU_COM_PREF = "FEMUTU_COM_PREF";
	public static final String DB_FEMUTU_BOX37 = "FEMUTU_BOX37";
	public static final String DB_FEMUTU_TRANS_TYPE = "FEMUTU_TRANS_TYPE";
	public static final String DB_FEMUTU_RELATION = "FEMUTU_RELATION";
	public static final String DB_FEMUTU_RESTRIC = "FEMUTU_RESTRIC";
	public static final String DB_FEMUTU_LIC_FEE = "FEMUTU_LIC_FEE";
	public static final String DB_FEMUTU_CALC_METHOD = "FEMUTU_CALC_METHOD";
	public static final String DB_FEMUTU_VAL_DET = "FEMUTU_VAL_DET";
	public static final String DB_FEMUTU_TARIFF_CONT_REQ = "FEMUTU_TARIFF_CONT_REQ";
	public static final String DB_MOT_CODE_FEMUTU = "MOT_CODE_FEMUTU";
	public static final String DB_FEMUTU_CONT = "FEMUTU_CONT";
	public static final String DB_NAW_CODE_CM = "NAW_CODE_CM";
	public static final String DB_FEMUTU_SPEC_INDI = "FEMUTU_SPEC_INDI";
	public static final String DB_FEMUTU_STAN_PERC_DUTIES = "FEMUTU_STAN_PERC_DUTIES";
	public static final String DB_FEMUTU_TRANS_CODE_K7 = "FEMUTU_TRANS_CODE_K7";
	public static final String DB_FEMUTU_TRANS_NO = "FEMUTU_TRANS_NO";
	public static final String DB_FEMUTU_TRANS_DATE = "FEMUTU_TRANS_DATE";
	public static final String DB_FEMUTU_NT_WGT_K13 = "FEMUTU_NT_WGT_K13";
	public static final String DB_FEMUTU_ADD_FLDK15 = "FEMUTU_ADD_FLDK15";
	public static final String DB_FEMUTU_ADD_FLDK16 = "FEMUTU_ADD_FLDK16";
	public static final String DB_FEMUTU_ADD_FLDK17 = "FEMUTU_ADD_FLDK17";
	public static final String DB_FEMUTU_ADD_FLDK18 = "FEMUTU_ADD_FLDK18";
	public static final String DB_FEMUTU_ADD_FLDK19 = "FEMUTU_ADD_FLDK19";
	public static final String DB_FEMUTU_ADD_FLDK20 = "FEMUTU_ADD_FLDK20";
	public static final String DB_FEMUTU_ADD_FLDK21 = "FEMUTU_ADD_FLDK21";
	public static final String DB_FEMUTU_ADD_FLDK22 = "FEMUTU_ADD_FLDK22";
	public static final String DB_FEMUTU_ADD_FLDK23 = "FEMUTU_ADD_FLDK23";
	public static final String DB_FEMUTU_AUTH_NO = "FEMUTU_AUTH_NO";
	public static final String DB_FEMUTU_IMP_LIC = "FEMUTU_IMP_LIC";
	public static final String DB_FEMUTU_IMP_LIC_DATE = "FEMUTU_IMP_LIC_DATE";
	public static final String DB_FEMUTU_SCO_IND = "FEMUTU_SCO_IND";
	public static final String DB_FEMUTU_PRELIM_IND = "FEMUTU_PRELIM_IND";
	public static final String DB_FEMUTU_CERTIFICATE_NO = "FEMUTU_CERTIFICATE_NO";
	public static final String DB_NAW_CODE_CONS = "NAW_CODE_CONS";
	public static final String DB_FEMUTU_CONS_NAME = "FEMUTU_CONS_NAME";
	public static final String DB_FEMUTU_CONS_CITY = "FEMUTU_CONS_CITY";
	public static final String DB_CONS_VAT_NO = "CONS_VAT_NO";
	public static final String DB_FEMUTU_VERIFY_BY_CUST = "FEMUTU_VERIFY_BY_CUST";
	public static final String DB_FEMUTU_INCO_TERMS = "FEMUTU_INCO_TERMS";
	public static final String DB_FEMUTU_DELIV_PLACE = "FEMUTU_DELIV_PLACE";
	public static final String DB_FEMUTU_FRT_AMT_EUR = "FEMUTU_FRT_AMT_EUR";
	public static final String DB_FEMUTU_INS_AMT_EUR = "FEMUTU_INS_AMT_EUR";
	public static final String DB_FEMUTU_ADD_DED_EUR = "FEMUTU_ADD_DED_EUR";
	public static final String DB_FEMUTU_CUST_VAL_EUR = "FEMUTU_CUST_VAL_EUR";
	public static final String DB_FEMUTU_IMP_DUT_PERC = "FEMUTU_IMP_DUT_PERC";
	public static final String DB_FEMUTU_AMT_RELIEF = "FEMUTU_AMT_RELIEF";
	public static final String DB_FEMUTU_CUST_DUT_SPEC = "FEMUTU_CUST_DUT_SPEC";
	public static final String DB_FEMUTU_CUST_DUTIES = "FEMUTU_CUST_DUTIES";
	public static final String DB_FEMUTU_DOC_NO = "FEMUTU_DOC_NO";
	public static final String DB_FEMUTU_TYPE_OF_CHRGS = "FEMUTU_TYPE_OF_CHRGS";
	public static final String DB_FEMUTU_MANU = "FEMUTU_MANU";
	public static final String DB_FEMUTU_AMT_OF_CHRGS = "FEMUTU_AMT_OF_CHRGS";
	public static final String DB_FEMUTU_AGRI_LEV = "FEMUTU_AGRI_LEV";
	public static final String DB_FEMUTU_VAT_AMT = "FEMUTU_VAT_AMT";
	public static final String DB_FEMUTU_VAT_CONV_VAL = "FEMUTU_VAT_CONV_VAL";
	public static final String DB_FEMUTU_EXCISE_CODE_G1 = "FEMUTU_EXCISE_CODE_G1";
	public static final String DB_FEMUTU_VAT_PREF_CODE = "FEMUTU_VAT_PREF_CODE";
	public static final String DB_FEMUTU_EXCISE_AMT_G1 = "FEMUTU_EXCISE_AMT_G1";
	public static final String DB_FEMUTU_EXCISE_CODE_G3 = "FEMUTU_EXCISE_CODE_G3";
	public static final String DB_FEMUTU_EXCISE_AMT_G3 = "FEMUTU_EXCISE_AMT_G3";
	public static final String DB_FEMUTU_EXCISE_CODE_G5 = "FEMUTU_EXCISE_CODE_G5";
	public static final String DB_FEMUTU_EXCISE_AMT_G5 = "FEMUTU_EXCISE_AMT_G5";
	public static final String DB_FEMUTU_EXCISE_CODE_G7 = "FEMUTU_EXCISE_CODE_G7";
	public static final String DB_FEMUTU_EXCISE_AMT_G7 = "FEMUTU_EXCISE_AMT_G7";
	public static final String DB_FEMUTU_EXT_PERC = "FEMUTU_EXT_PERC";
	public static final String DB_FEMUTU_ACT_ALCH_STE = "FEMUTU_ACT_ALCH_STE";
	public static final String DB_FEMUTU_ALC_FREE_LIT = "FEMUTU_ALC_FREE_LIT";
	public static final String DB_FEMUTU_LTS_OIL = "FEMUTU_LTS_OIL";
	public static final String DB_FEMUTU_PER_KG = "FEMUTU_PER_KG";
	public static final String DB_FEMUTU_PURE_ALC_LTS = "FEMUTU_PURE_ALC_LTS";
	public static final String DB_FEMUTU_TRAN_DOC = "FEMUTU_TRAN_DOC";
	public static final String DB_FEMUTU_PACK_UNIT = "FEMUTU_PACK_UNIT";
	public static final String DB_FEMUTU_NO_OF_PACK = "FEMUTU_NO_OF_PACK";
	public static final String DB_FEMUTU_INV_SLS = "FEMUTU_INV_SLS";
	public static final String DB_FEMUTU_INV_SLS_LINE = "FEMUTU_INV_SLS_LINE";
	public static final String DB_FEMUTU_INV_SLS_DATE = "FEMUTU_INV_SLS_DATE";
	public static final String DB_FEMUTU_QUAN1 = "FEMUTU_QUAN1";
	public static final String DB_FEMUTU_QUAN2 = "FEMUTU_QUAN2";
	public static final String DB_FEMUTU_EXP_LIC_NUM = "FEMUTU_EXP_LIC_NUM";
	public static final String DB_FEMUTU_EXP_LIC_DATE = "FEMUTU_EXP_LIC_DATE";
	public static final String DB_CT_CODE_DEST = "CT_CODE_DEST";
	public static final String DB_FEMUTU_BASE_CODE2 = "FEMUTU_BASE_CODE2";
	public static final String DB_FEMUTU_DOC_CODE1 = "FEMUTU_DOC_CODE1";
	public static final String DB_FEMUTU_DOC_CODE2 = "FEMUTU_DOC_CODE2";
	public static final String DB_FEMUTU_DOC_CODE3 = "FEMUTU_DOC_CODE3";
	public static final String DB_FEMUTU_DOC_CODE4 = "FEMUTU_DOC_CODE4";
	public static final String DB_FEMUTU_BASE_CODE1 = "FEMUTU_BASE_CODE1";
	public static final String DB_FEMUTU_MTH_PAY = "FEMUTU_MTH_PAY";
	public static final String DB_FEMTU_STAT = "FEMTU_STAT";
	public static final String DB_FEMUTU_MEM_STATE = "FEMUTU_MEM_STATE";
	public static final String DB_FEMUTU_STAT = "FEMUTU_STAT";
	public static final String DB_FEMUTU_ADD_FLD_M8 = "FEMUTU_ADD_FLD_M8";
	public static final String DB_FEMUTU_ADD_FLD_M9 = "FEMUTU_ADD_FLD_M9";
	public static final String DB_FEMUTU_ADD_FLD_M10 = "FEMUTU_ADD_FLD_M10";
	public static final String DB_FEMUTU_ADD_FLD_M11 = "FEMUTU_ADD_FLD_M11";
	public static final String DB_FEMUTU_ADD_FLD_M12 = "FEMUTU_ADD_FLD_M12";
	public static final String DB_FEMUTU_ADD_FLD_M13 = "FEMUTU_ADD_FLD_M13";
	public static final String DB_FEMUTU_ADD_FLD_M14 = "FEMUTU_ADD_FLD_M14";
	public static final String DB_FEMUTU_ADD_FLD_M15 = "FEMUTU_ADD_FLD_M15";
	public static final String DB_FEMUTU_ADD_FLD_M16 = "FEMUTU_ADD_FLD_M16";
	public static final String DB_FEMUTU_CONS_ADDR = "FEMUTU_CONS_ADDR";
	public static final String DB_FEMUTU_ADD_DOC_CODE = "FEMUTU_ADD_DOC_CODE";
	public static final String DB_FEMUTU_A3 = "FEMUTU_A3";
	public static final String DB_FEMUTU_UNIQUE_KEY = "FEMUTU_UNIQUE_KEY";
	public static final String DB_FEMUTU_CUST_REF1 = "FEMUTU_CUST_REF1";
	public static final String DB_FEMUTU_CUST_REF2 = "FEMUTU_CUST_REF2";
	public static final String DB_FEMUTU_CUST_REF3 = "FEMUTU_CUST_REF3";
	public static final String DB_FEMUTU_CUST_REF4 = "FEMUTU_CUST_REF4";
	public static final String DB_FEMUTU_CUST_REF5 = "FEMUTU_CUST_REF5";
	public static final String DB_FEMUTU_CUST_REF6 = "FEMUTU_CUST_REF6";
	public static final String DB_FEMUTU_CUST_REF7 = "FEMUTU_CUST_REF7";
	public static final String DB_FEMUTU_CUST_REF8 = "FEMUTU_CUST_REF8";
	public static final String DB_FEMUTU_CUST_REF9 = "FEMUTU_CUST_REF9";
	public static final String DB_FEMUTU_CUST_REF10 = "FEMUTU_CUST_REF10";
	public static final String DB_FEMUTU_CUST_REF11 = "FEMUTU_CUST_REF11";
	public static final String DB_FEMUTU_CUST_REF12 = "FEMUTU_CUST_REF12";
	public static final String DB_FEMUTU_CUST_REF13 = "FEMUTU_CUST_REF13";
	public static final String DB_FEMUTU_CUST_REF14 = "FEMUTU_CUST_REF14";
	public static final String DB_FEMUTU_CUST_REF15 = "FEMUTU_CUST_REF15";

	
	private Ranges ranges= new Ranges();
		
	private TreeMap<String, String> emptyDetailRecord = new TreeMap<String, String>();
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_FEED_SYS_FEMUTU +
			"," +DB_FEMUTU_IN_OUT_BOUND +
			"," +DB_FEMUTU_JOB_NO +
			"," +DB_FEMUTU_LINE_NO +
			"," +DB_FEMUTU_TRANS_CODE +
			"," +DB_FEMUTU_BON_WRH_TYPE +
			"," +DB_FEMUTU_STAT_GDS +
			"," +DB_FEMUTU_NAW_NO +
			"," +DB_FEMUTU_DOC_TYPE +
			"," +DB_FEMUTU_ADD_DOC_NO +
			"," +DB_FEMUTU_DT_ISSUE_DOC +
			"," +DB_FEMUTU_TG_IND +
			"," +DB_FEMUTU_PERIOD +
			"," +DB_FEMUTU_MOD +
			"," +DB_FEMUTU_PLACE_ISSUE_DOC +
			"," +DB_FEMUTU_DOC_VAL +
			"," +DB_FEMUTU_ART_NO +
			"," +DB_FEMUTU_ART_DESC +
			"," +DB_FEMUTU_TARIFF_CODE +
			"," +DB_FEMUTU_IMP_CERTIFICATE +
			"," +DB_FEMUTU_INVNO +
			"," +DB_FEMUTU_INV_LN_NO +
			"," +DB_FEMUTU_INV_DATE +
			"," +DB_FEMUTU_KIND_CODE_D11 +
			"," +DB_FEMUTU_NO_PCS_D12 +
			"," +DB_FEMUTU_NT_WGT +
			"," +DB_FEMUTU_LOC +
			"," +DB_FEMUTU_SPARE_FLD_D15 +
			"," +DB_CT_CODE_ORG +
			"," +DB_CT_CODE_PROV +
			"," +DB_FEMUTU_PREF_CODE1 +
			"," +DB_FEMUTU_PREF_CODE2 +
			"," +DB_FEMUTU_NO_ADD_UNITS +
			"," +DB_FEMUTU_ADD_UNITS +
			"," +DB_FEMUTU_INV_PROF +
			"," +DB_FEMUTU_INV_PROF_LINE +
			"," +DB_FEMUTU_INV_VAL +
			"," +DB_CURR_CODE_FEMUTU +
			"," +DB_FEMUTU_EX_RATE +
			"," +DB_FEMUTU_INV_PROF_DATE +
			"," +DB_FEMUTU_COM_PREF +
			"," +DB_FEMUTU_BOX37 +
			"," +DB_FEMUTU_TRANS_TYPE +
			"," +DB_FEMUTU_RELATION +
			"," +DB_FEMUTU_RESTRIC +
			"," +DB_FEMUTU_LIC_FEE +
			"," +DB_FEMUTU_CALC_METHOD +
			"," +DB_FEMUTU_VAL_DET +
			"," +DB_FEMUTU_TARIFF_CONT_REQ +
			"," +DB_MOT_CODE_FEMUTU +
			"," +DB_FEMUTU_CONT +
			"," +DB_NAW_CODE_CM +
			"," +DB_FEMUTU_SPEC_INDI +
			"," +DB_FEMUTU_STAN_PERC_DUTIES +
			"," +DB_FEMUTU_TRANS_CODE_K7 +
			"," +DB_FEMUTU_TRANS_NO +
			"," +DB_FEMUTU_TRANS_DATE +
			"," +DB_FEMUTU_NT_WGT_K13 +
			"," +DB_FEMUTU_ADD_FLDK15 +
			"," +DB_FEMUTU_ADD_FLDK16 +
			"," +DB_FEMUTU_ADD_FLDK17 +
			"," +DB_FEMUTU_ADD_FLDK18 +
			"," +DB_FEMUTU_ADD_FLDK19 +
			"," +DB_FEMUTU_ADD_FLDK20 +
			"," +DB_FEMUTU_ADD_FLDK21 +
			"," +DB_FEMUTU_ADD_FLDK22 +
			"," +DB_FEMUTU_ADD_FLDK23 +
			"," +DB_FEMUTU_AUTH_NO +
			"," +DB_FEMUTU_IMP_LIC +
			"," +DB_FEMUTU_IMP_LIC_DATE +
			"," +DB_FEMUTU_SCO_IND +
			"," +DB_FEMUTU_PRELIM_IND +
			"," +DB_FEMUTU_CERTIFICATE_NO +
			"," +DB_NAW_CODE_CONS +
			"," +DB_FEMUTU_CONS_NAME +
			"," +DB_FEMUTU_CONS_CITY +
			"," +DB_CONS_VAT_NO +
			"," +DB_FEMUTU_VERIFY_BY_CUST +
			"," +DB_FEMUTU_INCO_TERMS +
			"," +DB_FEMUTU_DELIV_PLACE +
			"," +DB_FEMUTU_FRT_AMT_EUR +
			"," +DB_FEMUTU_INS_AMT_EUR +
			"," +DB_FEMUTU_ADD_DED_EUR +
			"," +DB_FEMUTU_CUST_VAL_EUR +
			"," +DB_FEMUTU_IMP_DUT_PERC +
			"," +DB_FEMUTU_AMT_RELIEF +
			"," +DB_FEMUTU_CUST_DUT_SPEC +
			"," +DB_FEMUTU_CUST_DUTIES +
			"," +DB_FEMUTU_DOC_NO +
			"," +DB_FEMUTU_TYPE_OF_CHRGS +
			"," +DB_FEMUTU_MANU +
			"," +DB_FEMUTU_AMT_OF_CHRGS +
			"," +DB_FEMUTU_AGRI_LEV +
			"," +DB_FEMUTU_VAT_AMT +
			"," +DB_FEMUTU_VAT_CONV_VAL +
			"," +DB_FEMUTU_EXCISE_CODE_G1 +
			"," +DB_FEMUTU_VAT_PREF_CODE +
			"," +DB_FEMUTU_EXCISE_AMT_G1 +
			"," +DB_FEMUTU_EXCISE_CODE_G3 +
			"," +DB_FEMUTU_EXCISE_AMT_G3 +
			"," +DB_FEMUTU_EXCISE_CODE_G5 +
			"," +DB_FEMUTU_EXCISE_AMT_G5 +
			"," +DB_FEMUTU_EXCISE_CODE_G7 +
			"," +DB_FEMUTU_EXCISE_AMT_G7 +
			"," +DB_FEMUTU_EXT_PERC +
			"," +DB_FEMUTU_ACT_ALCH_STE +
			"," +DB_FEMUTU_ALC_FREE_LIT +
			"," +DB_FEMUTU_LTS_OIL +
			"," +DB_FEMUTU_PER_KG +
			"," +DB_FEMUTU_PURE_ALC_LTS +
			"," +DB_FEMUTU_TRAN_DOC +
			"," +DB_FEMUTU_PACK_UNIT +
			"," +DB_FEMUTU_NO_OF_PACK +
			"," +DB_FEMUTU_INV_SLS +
			"," +DB_FEMUTU_INV_SLS_LINE +
			"," +DB_FEMUTU_INV_SLS_DATE +
			"," +DB_FEMUTU_QUAN1 +
			"," +DB_FEMUTU_QUAN2 +
			"," +DB_FEMUTU_EXP_LIC_NUM +
			"," +DB_FEMUTU_EXP_LIC_DATE +
			"," +DB_CT_CODE_DEST +
			"," +DB_FEMUTU_BASE_CODE2 +
			"," +DB_FEMUTU_DOC_CODE1 +
			"," +DB_FEMUTU_DOC_CODE2 +
			"," +DB_FEMUTU_DOC_CODE3 +
			"," +DB_FEMUTU_DOC_CODE4 +
			"," +DB_FEMUTU_BASE_CODE1 +
			"," +DB_FEMUTU_MTH_PAY +
			"," +DB_FEMTU_STAT +
			"," +DB_FEMUTU_MEM_STATE +
			"," +DB_FEMUTU_STAT +
			"," +DB_FEMUTU_ADD_FLD_M8 +
			"," +DB_FEMUTU_ADD_FLD_M9 +
			"," +DB_FEMUTU_ADD_FLD_M10 +
			"," +DB_FEMUTU_ADD_FLD_M11 +
			"," +DB_FEMUTU_ADD_FLD_M12 +
			"," +DB_FEMUTU_ADD_FLD_M13 +
			"," +DB_FEMUTU_ADD_FLD_M14 +
			"," +DB_FEMUTU_ADD_FLD_M15 +
			"," +DB_FEMUTU_ADD_FLD_M16 +
			"," +DB_FEMUTU_CONS_ADDR +
			"," +DB_FEMUTU_ADD_DOC_CODE +
			"," +DB_FEMUTU_A3 +
			"," +DB_FEMUTU_UNIQUE_KEY +
			"," +DB_FEMUTU_CUST_REF1 +
			"," +DB_FEMUTU_CUST_REF2 +
			"," +DB_FEMUTU_CUST_REF3 +
			"," +DB_FEMUTU_CUST_REF4 +
			"," +DB_FEMUTU_CUST_REF5 +
			"," +DB_FEMUTU_CUST_REF6 +
			"," +DB_FEMUTU_CUST_REF7 +
			"," +DB_FEMUTU_CUST_REF8 +
			"," +DB_FEMUTU_CUST_REF9 +
			"," +DB_FEMUTU_CUST_REF10 +
			"," +DB_FEMUTU_CUST_REF11 +
			"," +DB_FEMUTU_CUST_REF12 +
			"," +DB_FEMUTU_CUST_REF13 +
			"," +DB_FEMUTU_CUST_REF14 +
			"," +DB_FEMUTU_CUST_REF15 +
			" FROM "+getDbName()+DB_FEMUTU;
		return selectPart;
		}

	public Femutu() {
		this.tableDescription=getTableMetadata(DB_FEMUTU);		
		this.emptyDetailRecord=getEmptyDetails();
	}
	
	public Femutu(boolean flag ) {
		super(flag);
		this.tableDescription=getTableMetadata(DB_FEMUTU);
		this.emptyDetailRecord=getEmptyDetails();
	}
	
	public TreeMap<String, String> readFemutuRecord(String feedSystem, String type, String jobNumber, int jobLineNumber) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEED_SYS_FEMUTU+"='"+feedSystem+"'"+
				" AND "+DB_FEMUTU_IN_OUT_BOUND+"='"+type+"'"+
				" AND "+DB_FEMUTU_LINE_NO+"="+jobLineNumber;
				
		if(jobNumber!=null){
			statement += " AND "+DB_FEMUTU_JOB_NO+"='"+jobNumber+"'";
		}
	
		
		return readTable(statement);
	}
	
	
	public TreeMap<String, String> readFemutuRecord(int jobLineNumber) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEMUTU_LINE_NO+"='"+jobLineNumber+"'";
	
		return readTable(statement);
	}
	
	public TreeMap<String, String> readFemutuRecord(String uniqueKey) {
		
		String statement = getSelectPart() + " WHERE " +DB_FEMUTU_UNIQUE_KEY+"='"+uniqueKey+"'";
		
		return readTable(statement);
	}
	
	public ArrayList<TreeMap<String, String>> readRecordsForPeriod(String feedSystem, String clientCode,String period,String type,String status) {
		String statement=getSelectPart()+ " WHERE " + DB_FEED_SYS_FEMUTU+"='"+ feedSystem+"'";
		if(!clientCode.trim().equals("")){
			statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
		}
		statement+=" AND "+DB_FEMUTU_IN_OUT_BOUND+"='"+type+"'";
		statement+=" AND "+DB_FEMUTU_PERIOD+"='"+period+"'";
		statement+=" AND "+DB_FEMUTU_STAT+"='"+status+"'";
		statement+=" ORDER BY "+DB_FEED_SYS_FEMUTU+","+DB_NAW_CODE_CM;
		return readTableMultiple(statement);
	}
	
	public ArrayList<TreeMap<String, String>> readDomprocRecordsForPeriod(String feedSystem, String clientCode,String period,String status) {
			
		return readDomprocRecordsForPeriodOrdered(feedSystem, clientCode,period,status, DB_FEMUTU_UNIQUE_KEY);
	}
	
	
	public ArrayList<TreeMap<String, String>> readDomprocRecordsForPeriodOrdered(String feedSystem, String clientCode,String period,String status, String orderBy) {
		String statement=getSelectPart()+ " WHERE " + DB_FEED_SYS_FEMUTU+"='"+ feedSystem+"'";
		if(!clientCode.trim().equals("")){
			statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
		}
		statement+=" AND "+DB_FEMUTU_IN_OUT_BOUND+"='"+Processors.DOMPROC+"'";
		statement+=" AND "+DB_FEMUTU_PERIOD+"='"+period+"'";
		statement+=" AND "+DB_FEMUTU_STAT+"='"+status+"'";
		statement+=" ORDER BY "+orderBy;
		
		return readTableMultiple(statement);
	}
	
	
	public ArrayList<TreeMap<String, String>> readRecordsByRef(String refName, String condition, String value) {
		
		String statement="";
		
		if(condition.equals(Processors.COND_BEGINS_WITH)){
			statement=getSelectPart()+ " WHERE " + refName+" LIKE '"+ value+"%'";
		}else if (condition.equals(Processors.COND_CONTAINS)){
			statement=getSelectPart()+ " WHERE " + refName+" LIKE '%"+ value+"%'";
		}else{
			statement=getSelectPart()+ " WHERE " + refName+"='"+ value+"'";
		}
		
		return readTableMultiple(statement);
	}
	
	public boolean updateFemutuStatusForPeriod(String feedSystem, String clientCode,String period,String statusFrom,String statusTo){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_FEMUTU+ " SET "+DB_FEMUTU_STAT+"='"+statusTo+"'";
			statement+=" WHERE " + DB_FEED_SYS_FEMUTU+"='"+ feedSystem+"'";
			
			if(!clientCode.trim().equals("")){
				statement+=" AND " + DB_NAW_CODE_CM+"='"+ clientCode+"'";
			}
			statement+=" AND "+DB_FEMUTU_PERIOD+"='"+period+"'";
			statement+=" AND "+DB_FEMUTU_STAT+"='"+statusFrom+"'";
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public boolean updateFemutuStatus(String feedSystem,String type, String jobNumber, int jobLineNumber,String status){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_FEMUTU+ " SET "+DB_FEMUTU_STAT+"='"+status+"'";
			statement+=" WHERE " + DB_FEED_SYS_FEMUTU+"='"+ feedSystem+"'";
			
			statement+=" AND "+DB_FEMUTU_IN_OUT_BOUND+"='"+type+"'";
			statement+=" AND "+DB_FEMUTU_JOB_NO+"='"+jobNumber+"'";
			statement+=" AND "+DB_FEMUTU_LINE_NO+"="+jobLineNumber;
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public boolean updateFemutuStatus(String uniqueKey,String status){
		boolean ret=true;
		try{
			String statement="UPDATE "+getDbName()+DB_FEMUTU+ " SET "+DB_FEMUTU_STAT+"='"+status+"'";
			statement+=" WHERE " + DB_FEMUTU_UNIQUE_KEY+"='"+ uniqueKey+"'";
			
			ret=insertUpdateTable(statement);		
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
				
		return ret;
	}
	
	public boolean insertUpdateFemutu(TreeMap<String, String> in){
		return insertUpdate(in, false);
	}
	
	public boolean insertUpdateFemutuFormatted(TreeMap<String, String> in){
		
		return insertUpdate(in, true);
	}
	
	
	private boolean insertUpdate(TreeMap<String, String> in,boolean formated){
		
		//get keys	
		//String feedSystem=in.get(DB_FEED_SYS_FEMUTU);
		//String type=in.get(DB_FEMUTU_IN_OUT_BOUND);
		//String jobNumber=in.get(DB_FEMUTU_JOB_NO);
		//int line=Integer.parseInt(in.get(DB_FEMUTU_LINE_NO));
		
		boolean retCode=false;
		
		String a3Field="";
		
		String uniqueKey=in.get(DB_FEMUTU_UNIQUE_KEY);
		
		TreeMap<String, String>record =  readFemutuRecord(uniqueKey);
						
		if(record!=null && record.size()>0){
			
			a3Field=record.get(DB_FEMUTU_A3);
			//delete record
			deleteFemutu(record);
		}else{
			a3Field=in.get(DB_FEMUTU_A3);
		}
		
		if(a3Field.equals("")){
			a3Field=ranges.getUniqueIdentifier(Ranges.TYPE_A3, Resources.getSetting("customer.name"));
		}
		
		if(a3Field!=null){
			in.put(DB_FEMUTU_A3, a3Field);
			retCode=insertFemutu(in,formated);
		}else{
			System.out.println("Can't determine A3. Please check configuration");
		}
		return retCode;
	}
	
	private boolean insertFemutu(TreeMap<String, String> in, boolean formated){
		
		boolean ret=true;
		TreeMap<String, String> record=new TreeMap<String, String>();
		
		if(!formated){
			record=formatRecord(in, tableDescription);
		}else{
			record.putAll(in);
		}
		
		String statement="INSERT INTO "+getDbName()+DB_FEMUTU+ " VALUES(";
		try{
			statement+="'"+record.get(DB_FEED_SYS_FEMUTU)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_IN_OUT_BOUND)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_JOB_NO)+"', " ;
			statement+=record.get(DB_FEMUTU_LINE_NO)+"," ;
			statement+="'"+record.get(DB_FEMUTU_TRANS_CODE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_BON_WRH_TYPE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_STAT_GDS)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_NAW_NO)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DOC_TYPE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_DOC_NO)+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_DT_ISSUE_DOC))+"," ;
			statement+="'"+record.get(DB_FEMUTU_TG_IND)+"', " ;
			statement+=record.get(DB_FEMUTU_PERIOD)+"," ;
			statement+="'"+record.get(DB_FEMUTU_MOD)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_PLACE_ISSUE_DOC)+"', " ;
			statement+=record.get(DB_FEMUTU_DOC_VAL)+"," ;
			statement+="'"+record.get(DB_FEMUTU_ART_NO)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ART_DESC)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_TARIFF_CODE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_IMP_CERTIFICATE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_INVNO)+"', " ;
			statement+=record.get(DB_FEMUTU_INV_LN_NO)+"," ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_INV_DATE))+"," ;
			statement+="'"+record.get(DB_FEMUTU_KIND_CODE_D11)+"', " ;
			statement+=record.get(DB_FEMUTU_NO_PCS_D12)+"," ;
			statement+=record.get(DB_FEMUTU_NT_WGT)+"," ;
			statement+="'"+record.get(DB_FEMUTU_LOC)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_SPARE_FLD_D15)+"', " ;
			statement+="'"+record.get(DB_CT_CODE_ORG)+"', " ;
			statement+="'"+record.get(DB_CT_CODE_PROV)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_PREF_CODE1)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_PREF_CODE2)+"', " ;
			statement+=record.get(DB_FEMUTU_NO_ADD_UNITS)+"," ;
			statement+="'"+record.get(DB_FEMUTU_ADD_UNITS)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_INV_PROF)+"', " ;
			statement+=record.get(DB_FEMUTU_INV_PROF_LINE)+"," ;
			statement+=record.get(DB_FEMUTU_INV_VAL)+"," ;
			statement+="'"+record.get(DB_CURR_CODE_FEMUTU)+"', " ;
			statement+=record.get(DB_FEMUTU_EX_RATE)+"," ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_INV_PROF_DATE))+"," ;
			statement+="'"+record.get(DB_FEMUTU_COM_PREF)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_BOX37)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_TRANS_TYPE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_RELATION)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_RESTRIC)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_LIC_FEE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CALC_METHOD)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_VAL_DET)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_TARIFF_CONT_REQ)+"', " ;
			statement+=record.get(DB_MOT_CODE_FEMUTU)+"," ;
			statement+=record.get(DB_FEMUTU_CONT)+"," ;
			statement+="'"+record.get(DB_NAW_CODE_CM)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_SPEC_INDI)+"', " ;
			statement+=record.get(DB_FEMUTU_STAN_PERC_DUTIES)+"," ;
			statement+="'"+record.get(DB_FEMUTU_TRANS_CODE_K7)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_TRANS_NO)+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_TRANS_DATE))+"," ;
			statement+=record.get(DB_FEMUTU_NT_WGT_K13)+"," ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK15)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK16)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK17)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK18)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK19)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK20)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK21)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK22)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLDK23)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_AUTH_NO)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_IMP_LIC)+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_IMP_LIC_DATE))+"," ;
			statement+="'"+record.get(DB_FEMUTU_SCO_IND)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_PRELIM_IND)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CERTIFICATE_NO)+"', " ;
			statement+="'"+record.get(DB_NAW_CODE_CONS)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CONS_NAME)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CONS_CITY)+"', " ;
			statement+="'"+record.get(DB_CONS_VAT_NO)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_VERIFY_BY_CUST)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_INCO_TERMS)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DELIV_PLACE)+"', " ;
			statement+=record.get(DB_FEMUTU_FRT_AMT_EUR)+"," ;
			statement+=record.get(DB_FEMUTU_INS_AMT_EUR)+"," ;
			statement+=record.get(DB_FEMUTU_ADD_DED_EUR)+"," ;
			statement+=record.get(DB_FEMUTU_CUST_VAL_EUR)+"," ;
			statement+=record.get(DB_FEMUTU_IMP_DUT_PERC)+"," ;
			statement+=record.get(DB_FEMUTU_AMT_RELIEF)+"," ;
			statement+=record.get(DB_FEMUTU_CUST_DUT_SPEC)+"," ;
			statement+=record.get(DB_FEMUTU_CUST_DUTIES)+"," ;
			statement+="'"+record.get(DB_FEMUTU_DOC_NO)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_TYPE_OF_CHRGS)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_MANU)+"', " ;
			statement+=record.get(DB_FEMUTU_AMT_OF_CHRGS)+"," ;
			statement+=record.get(DB_FEMUTU_AGRI_LEV)+"," ;
			statement+=record.get(DB_FEMUTU_VAT_AMT)+"," ;
			statement+=record.get(DB_FEMUTU_VAT_CONV_VAL)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_CODE_G1)+"," ;
			statement+=record.get(DB_FEMUTU_VAT_PREF_CODE)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_AMT_G1)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_CODE_G3)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_AMT_G3)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_CODE_G5)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_AMT_G5)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_CODE_G7)+"," ;
			statement+=record.get(DB_FEMUTU_EXCISE_AMT_G7)+"," ;
			statement+=record.get(DB_FEMUTU_EXT_PERC)+"," ;
			statement+=record.get(DB_FEMUTU_ACT_ALCH_STE)+"," ;
			statement+=record.get(DB_FEMUTU_ALC_FREE_LIT)+"," ;
			statement+=record.get(DB_FEMUTU_LTS_OIL)+"," ;
			statement+=record.get(DB_FEMUTU_PER_KG)+"," ;
			statement+=record.get(DB_FEMUTU_PURE_ALC_LTS)+"," ;
			statement+="'"+record.get(DB_FEMUTU_TRAN_DOC)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_PACK_UNIT)+"', " ;
			statement+=record.get(DB_FEMUTU_NO_OF_PACK)+"," ;
			statement+="'"+record.get(DB_FEMUTU_INV_SLS)+"', " ;
			statement+=record.get(DB_FEMUTU_INV_SLS_LINE)+"," ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_INV_SLS_DATE))+"," ;
			statement+=record.get(DB_FEMUTU_QUAN1)+"," ;
			statement+=record.get(DB_FEMUTU_QUAN2)+"," ;
			statement+="'"+record.get(DB_FEMUTU_EXP_LIC_NUM)+"', " ;
			statement+=DateUtils.formatDate(record.get(DB_FEMUTU_EXP_LIC_DATE))+"," ;
			statement+="'"+record.get(DB_CT_CODE_DEST)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_BASE_CODE2)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DOC_CODE1)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DOC_CODE2)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DOC_CODE3)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_DOC_CODE4)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_BASE_CODE1)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_MTH_PAY)+"', " ;
			statement+="'"+record.get(DB_FEMTU_STAT)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_MEM_STATE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_STAT)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M8)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M9)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M10)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M11)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M12)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M13)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M14)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M15)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_FLD_M16)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CONS_ADDR)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_ADD_DOC_CODE)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_A3)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_UNIQUE_KEY)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF1)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF2)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF3)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF4)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF5)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF6)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF7)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF8)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF9)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF10)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF11)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF12)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF13)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF14)+"', " ;
			statement+="'"+record.get(DB_FEMUTU_CUST_REF15)+"')"  ;
			
			ret=insertUpdateTable(statement);
			
		}catch(Exception ex){
			ex.printStackTrace();
			ret=false;
		}
		
		return ret;
	}
	
	
	
	public void deletePeriod(String feedSystem,String period){
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMUTU+ " WHERE " +DB_FEED_SYS_FEMUTU+"='"+feedSystem+"' AND "+DB_FEMUTU_PERIOD+"='"+period+"'";
		
		insertUpdateTable(statement);
	}
	
	public void deleteFemutu(TreeMap<String, String> record){
		//get keys
		//String feedSystem=record.get(DB_FEED_SYS_FEMUTU);
		//String type=record.get(DB_FEMUTU_IN_OUT_BOUND);
		//String jobNo=record.get(DB_FEMUTU_JOB_NO);
		//int line=Integer.parseInt(record.get(DB_FEMUTU_LINE_NO));
		
		String uniqueKey=record.get(DB_FEMUTU_UNIQUE_KEY);
				
		String statement = "DELETE FROM " +getDbName()+DB_FEMUTU+ " WHERE " +DB_FEMUTU_UNIQUE_KEY+"='"+uniqueKey+"'";
		
		insertUpdateTable(statement);
	}
	
	public TreeMap<String, String> mapToStock(TreeMap<String, String> in){
		
		TreeMap<String, String> record=new TreeMap<String, String>();
		
		if(in!=null){
			try{				
			
			    record.put(Stock.DB_FEED_SYS_STOCK, in.get(DB_FEED_SYS_FEMUTU));
				record.put(Stock.DB_STOCK_PERIOD,in.get(DB_FEMUTU_PERIOD));
				record.put(Stock.DB_STOCK_UNIQ_LN_NO, "");			
				record.put(Stock.DB_STOCK_JOB_NO_OUT, in.get(DB_FEMUTU_JOB_NO));
				record.put(Stock.DB_STOCK_LN_NO_OUT, in.get(DB_FEMUTU_LINE_NO));				
				record.put(Stock.DB_STOCK_JOB_NO_IN, "");
				record.put(Stock.DB_STOCK_LN_NO_IN, "");							
				record.put(Stock.DB_NAW_CODE_CM, in.get(DB_NAW_CODE_CM));
				record.put(Stock.DB_STOCK_LEG_STAT, in.get(DB_FEMUTU_SPEC_INDI));
				record.put(Stock.DB_STOCK_ART_NO, in.get(DB_FEMUTU_ART_NO));
				record.put(Stock.DB_STOCK_TARIFF_CODE, in.get(DB_FEMUTU_TARIFF_CODE));
				record.put(Stock.DB_STOCK_IMP_DUT_PERC, in.get(DB_FEMUTU_STAN_PERC_DUTIES));				
				record.put(Stock.DB_STOCK_TRANS_TYPE, in.get(DB_FEMUTU_TRANS_CODE_K7));
				record.put(Stock.DB_STOCK_TRANS_NO, "");
				record.put(Stock.DB_CURR_CODE_STOCK, in.get(DB_CURR_CODE_FEMUTU));
				record.put(Stock.DB_STOCK_EX_RATE, in.get(DB_FEMUTU_EX_RATE));
				record.put(Stock.DB_STOCK_TRANS_DATE, in.get(DB_FEMUTU_TRANS_DATE));
				record.put(Stock.DB_STOCK_NT_WGT, in.get(DB_FEMUTU_NT_WGT_K13));
				record.put(Stock.DB_STOCK_FACULK15, in.get(DB_FEMUTU_ADD_FLDK15));
				record.put(Stock.DB_STOCK_FACULK16, in.get(DB_FEMUTU_ADD_FLDK16));
				record.put(Stock.DB_STOCK_FACULK17, in.get(DB_FEMUTU_ADD_FLDK17));
				record.put(Stock.DB_STOCK_FACULK18, in.get(DB_FEMUTU_ADD_FLDK18));
				record.put(Stock.DB_STOCK_FACULK19, in.get(DB_FEMUTU_ADD_FLDK19));
				record.put(Stock.DB_STOCK_FACULK20, in.get(DB_FEMUTU_ADD_FLDK20));
				record.put(Stock.DB_STOCK_FACULK21, in.get(DB_FEMUTU_ADD_FLDK21));
				record.put(Stock.DB_STOCK_FACULK22, in.get(DB_FEMUTU_ADD_FLDK22));
				record.put(Stock.DB_STOCK_FACULK23, in.get(DB_FEMUTU_ADD_FLDK23));
				record.put(Stock.DB_STOCK_ADM_UNIT, in.get(DB_FEMUTU_PACK_UNIT));
				record.put(Stock.DB_STOCK_PCS, in.get(DB_FEMUTU_NO_OF_PACK));
				record.put(Stock.DB_STOCK_AMT, in.get(DB_FEMUTU_INV_VAL));		
				
			}catch(Exception ex){
				return null;
			}
		}
		
		return record;
		
	}


	public TreeMap<String, String> mapToDisk(TreeMap<String, String> in) {
	
		TreeMap<String, String> record=null;
		if (in != null) {
			try {
				record = new TreeMap<String, String>();
				//Mapping femutu to disk
				String dateIssue=DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_DT_ISSUE_DOC));
				
				record.put(Disk.BLOCK_DIRECTION, Processors.OUTBOUND);
				
				record.put(Disk.BLOCK_A0, Disk.BLOCK_A);
				record.put(Disk.BLOCK_A1,in.get(DB_FEMUTU_PERIOD));
				record.put(Disk.BLOCK_A2, in.get(DB_FEMUTU_TRANS_CODE));
				record.put(Disk.BLOCK_A3, in.get(DB_FEMUTU_A3));
				record.put(Disk.BLOCK_B0, Disk.BLOCK_B);
				record.put(Disk.BLOCK_B1, in.get(DB_FEMUTU_DOC_TYPE));
				record.put(Disk.BLOCK_B2, in.get(DB_FEMUTU_DOC_NO));
				record.put(Disk.BLOCK_B3, in.get(DB_FEMUTU_PLACE_ISSUE_DOC));
				record.put(Disk.BLOCK_B4, dateIssue);
				record.put(Disk.BLOCK_B5, in.get(DB_FEMUTU_TG_IND));
				record.put(Disk.BLOCK_D0, Disk.BLOCK_D);
				record.put(Disk.BLOCK_D1, "");
				record.put(Disk.BLOCK_D2, "");
				record.put(Disk.BLOCK_D3, "");
				record.put(Disk.BLOCK_D4, "");
				record.put(Disk.BLOCK_D5, "");
				record.put(Disk.BLOCK_D6, "");
				record.put(Disk.BLOCK_D7, "");
				record.put(Disk.BLOCK_D8, "");
				record.put(Disk.BLOCK_D9, "");
				record.put(Disk.BLOCK_D10, "");
				record.put(Disk.BLOCK_D11, "");
				record.put(Disk.BLOCK_D12, "");
				record.put(Disk.BLOCK_D13, "");
				record.put(Disk.BLOCK_D14, "");
				record.put(Disk.BLOCK_D15, "");
				record.put(Disk.BLOCK_E0, Disk.BLOCK_E);
				record.put(Disk.BLOCK_E1, in.get(DB_CT_CODE_ORG));
				record.put(Disk.BLOCK_E2, in.get(DB_CT_CODE_PROV));
				record.put(Disk.BLOCK_E3, in.get(DB_FEMUTU_PREF_CODE1));
				record.put(Disk.BLOCK_E4, in.get(DB_FEMUTU_PREF_CODE2));
				record.put(Disk.BLOCK_E5, in.get(DB_FEMUTU_AUTH_NO));
				record.put(Disk.BLOCK_E6, in.get(DB_FEMUTU_CERTIFICATE_NO));
				record.put(Disk.BLOCK_E7, in.get(DB_FEMUTU_IMP_LIC));
				record.put(Disk.BLOCK_E8, DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_IMP_LIC_DATE)));
				record.put(Disk.BLOCK_E9, in.get(DB_FEMUTU_SCO_IND));
				record.put(Disk.BLOCK_E10, in.get(DB_FEMUTU_PRELIM_IND));
				record.put(Disk.BLOCK_E11, "");
				record.put(Disk.BLOCK_E12, in.get(DB_FEMUTU_CONS_NAME));
				record.put(Disk.BLOCK_E13, in.get(DB_FEMUTU_CONS_ADDR));
				record.put(Disk.BLOCK_E14, in.get(DB_FEMUTU_CONS_CITY));
				record.put(Disk.BLOCK_E15, in.get(DB_CONS_VAT_NO));
				record.put(Disk.BLOCK_E16, in.get(DB_FEMUTU_ART_NO));
				record.put(Disk.BLOCK_E17, in.get(DB_FEMUTU_TARIFF_CODE));
				record.put(Disk.BLOCK_E18, in.get(DB_FEMUTU_VERIFY_BY_CUST));
				record.put(Disk.BLOCK_E19,in.get(DB_FEMUTU_ADD_UNITS));
				record.put(Disk.BLOCK_E20,in.get(DB_FEMUTU_NO_ADD_UNITS));
				record.put(Disk.BLOCK_E21,in.get(DB_FEMUTU_INV_PROF));
				record.put(Disk.BLOCK_E22,in.get(DB_FEMUTU_INV_PROF_LINE));
				record.put(Disk.BLOCK_E23,in.get(DB_FEMUTU_INCO_TERMS));
				record.put(Disk.BLOCK_E24,in.get(DB_FEMUTU_DELIV_PLACE));
				record.put(Disk.BLOCK_E25,in.get(DB_FEMUTU_INV_VAL));
				record.put(Disk.BLOCK_E26,in.get(DB_CURR_CODE_FEMUTU));
				record.put(Disk.BLOCK_E27,in.get(DB_FEMUTU_EX_RATE));
				record.put(Disk.BLOCK_E28,in.get(DB_FEMUTU_FRT_AMT_EUR));
				record.put(Disk.BLOCK_E29,in.get(DB_FEMUTU_INS_AMT_EUR));
				record.put(Disk.BLOCK_E30,in.get(DB_FEMUTU_ADD_DED_EUR));
				record.put(Disk.BLOCK_E31,in.get(DB_FEMUTU_CUST_VAL_EUR));
				record.put(Disk.BLOCK_E32,in.get(DB_FEMUTU_IMP_DUT_PERC));
				record.put(Disk.BLOCK_E33,in.get(DB_FEMUTU_AMT_RELIEF));
				record.put(Disk.BLOCK_E34,in.get(DB_FEMUTU_CUST_DUT_SPEC));
				record.put(Disk.BLOCK_E35,in.get(DB_FEMUTU_CUST_DUTIES));
				record.put(Disk.BLOCK_E36,in.get(DB_FEMUTU_BOX37));
				record.put(Disk.BLOCK_E37,DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_INV_PROF_DATE)));
				record.put(Disk.BLOCK_E38,in.get(DB_FEMUTU_ADD_DOC_CODE));
				record.put(Disk.BLOCK_E39,in.get(DB_FEMUTU_ADD_DOC_NO));
				record.put(Disk.BLOCK_E40,in.get(DB_FEMUTU_COM_PREF));
				record.put(Disk.BLOCK_E41,in.get(DB_FEMUTU_TRANS_TYPE));
				record.put(Disk.BLOCK_E42,in.get(DB_FEMUTU_RELATION));
				record.put(Disk.BLOCK_E43,in.get(DB_FEMUTU_RESTRIC));
				record.put(Disk.BLOCK_E44,in.get(DB_FEMUTU_LIC_FEE));
				record.put(Disk.BLOCK_E45,in.get(DB_FEMUTU_CALC_METHOD));
				record.put(Disk.BLOCK_E46,in.get(DB_FEMUTU_VAL_DET));
				record.put(Disk.BLOCK_E47,in.get(DB_FEMUTU_TARIFF_CONT_REQ));
				record.put(Disk.BLOCK_E48,in.get(DB_MOT_CODE_FEMUTU));
				record.put(Disk.BLOCK_E49,in.get(DB_FEMUTU_CONT));
				record.put(Disk.BLOCK_F0, Disk.BLOCK_F);
				record.put(Disk.BLOCK_F1, in.get(DB_FEMUTU_TYPE_OF_CHRGS));
				record.put(Disk.BLOCK_F2, in.get(DB_FEMUTU_MANU));
				record.put(Disk.BLOCK_F3, in.get(DB_FEMUTU_AMT_OF_CHRGS));
				record.put(Disk.BLOCK_F4, in.get(DB_FEMUTU_AGRI_LEV));
				record.put(Disk.BLOCK_F5, in.get(DB_FEMUTU_VAT_AMT));
				record.put(Disk.BLOCK_F6, in.get(DB_FEMUTU_VAT_CONV_VAL));
				//Temporary workaround due to wrong field type in the database
				//record.put(Disk.BLOCK_F7, in.get(DB_FEMUTU_VAT_PREF_CODE));
				record.put(Disk.BLOCK_F7, "");
				record.put(Disk.BLOCK_G0, Disk.BLOCK_G);
				record.put(Disk.BLOCK_G1, in.get(DB_FEMUTU_EXCISE_CODE_G1));
				record.put(Disk.BLOCK_G2, in.get(DB_FEMUTU_EXCISE_AMT_G1));
				record.put(Disk.BLOCK_G3, in.get(DB_FEMUTU_EXCISE_CODE_G3));
				record.put(Disk.BLOCK_G4, in.get(DB_FEMUTU_EXCISE_AMT_G3));
				record.put(Disk.BLOCK_G5, in.get(DB_FEMUTU_EXCISE_CODE_G5));
				record.put(Disk.BLOCK_G6, in.get(DB_FEMUTU_EXCISE_AMT_G5));
				record.put(Disk.BLOCK_G7, in.get(DB_FEMUTU_EXCISE_CODE_G7));
				record.put(Disk.BLOCK_G8, in.get(DB_FEMUTU_EXCISE_AMT_G7));
				record.put(Disk.BLOCK_G9, in.get(DB_FEMUTU_EXT_PERC));
				record.put(Disk.BLOCK_G10, in.get(DB_FEMUTU_ACT_ALCH_STE));
				record.put(Disk.BLOCK_G11, in.get(DB_FEMUTU_ALC_FREE_LIT));
				record.put(Disk.BLOCK_G12, in.get(DB_FEMUTU_LTS_OIL));
				record.put(Disk.BLOCK_G13, in.get(DB_FEMUTU_PER_KG));
				record.put(Disk.BLOCK_G14, in.get(DB_FEMUTU_PURE_ALC_LTS));
				record.put(Disk.BLOCK_G15, in.get(DB_FEMUTU_TRAN_DOC));
				record.put(Disk.BLOCK_H0, Disk.BLOCK_H);
				
				if(in.get(Femutu.DB_FEMUTU_IN_OUT_BOUND).equals(Processors.DOMPROC)){
					record.put(Disk.BLOCK_H1, "");
					record.put(Disk.BLOCK_H2, "0");
					record.put(Disk.BLOCK_H8, "");
					record.put(Disk.BLOCK_H9, "00000000");
				}else{		
					record.put(Disk.BLOCK_H1, in.get(DB_FEMUTU_PACK_UNIT));
					record.put(Disk.BLOCK_H2, in.get(DB_FEMUTU_NO_OF_PACK));
					record.put(Disk.BLOCK_H8, in.get(DB_FEMUTU_EXP_LIC_NUM));
					record.put(Disk.BLOCK_H9, DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_EXP_LIC_DATE)));
				}
							
				record.put(Disk.BLOCK_H3, in.get(DB_FEMUTU_INV_SLS));
				record.put(Disk.BLOCK_H4, in.get(DB_FEMUTU_INV_SLS_LINE));
				record.put(Disk.BLOCK_H5, DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_INV_SLS_DATE)));
				record.put(Disk.BLOCK_H6, in.get(DB_FEMUTU_QUAN1));
				record.put(Disk.BLOCK_H7, in.get(DB_FEMUTU_QUAN2));

				record.put(Disk.BLOCK_H10, in.get(DB_CT_CODE_DEST));
				record.put(Disk.BLOCK_H11, in.get(DB_FEMUTU_BASE_CODE1));
				record.put(Disk.BLOCK_H12, in.get(DB_FEMUTU_BASE_CODE2));
				record.put(Disk.BLOCK_H13, in.get(DB_FEMUTU_DOC_CODE1));
				record.put(Disk.BLOCK_H14, in.get(DB_FEMUTU_DOC_CODE2));
				record.put(Disk.BLOCK_H15, in.get(DB_FEMUTU_DOC_CODE3));
				record.put(Disk.BLOCK_H16, in.get(DB_FEMUTU_DOC_CODE4));
				record.put(Disk.BLOCK_H17, in.get(DB_FEMUTU_MTH_PAY));
				record.put(Disk.BLOCK_H18, in.get(DB_FEMUTU_MEM_STATE));
				record.put(Disk.BLOCK_J0, Disk.BLOCK_J);
				record.put(Disk.BLOCK_J1, in.get(DB_FEMUTU_DOC_TYPE));
				record.put(Disk.BLOCK_J2, in.get(DB_FEMUTU_DOC_NO));
				record.put(Disk.BLOCK_J3, in.get(DB_FEMUTU_PLACE_ISSUE_DOC));
				record.put(Disk.BLOCK_J4, dateIssue);
				record.put(Disk.BLOCK_J5, in.get(DB_FEMUTU_TG_IND));
				record.put(Disk.BLOCK_K0, Disk.BLOCK_K);
				record.put(Disk.BLOCK_K1, in.get(DB_NAW_CODE_CM));
				record.put(Disk.BLOCK_K2, in.get(DB_FEMUTU_SPEC_INDI));
				record.put(Disk.BLOCK_K3, in.get(DB_FEMUTU_ART_NO));
				record.put(Disk.BLOCK_K4, in.get(DB_FEMUTU_TARIFF_CODE));
				record.put(Disk.BLOCK_K5, in.get(DB_FEMUTU_STAN_PERC_DUTIES));
				record.put(Disk.BLOCK_K6, in.get(DB_FEMUTU_PACK_UNIT));
				record.put(Disk.BLOCK_K7, in.get(DB_FEMUTU_TRANS_CODE_K7));
				record.put(Disk.BLOCK_K8, in.get(DB_FEMUTU_TRANS_NO));
				record.put(Disk.BLOCK_K9, "-"+in.get(DB_FEMUTU_NO_OF_PACK));
				record.put(Disk.BLOCK_K10,"-"+in.get(DB_FEMUTU_INV_VAL));
				record.put(Disk.BLOCK_K11,in.get(DB_CURR_CODE_FEMUTU));
				record.put(Disk.BLOCK_K12,in.get(DB_FEMUTU_EX_RATE));
				record.put(Disk.BLOCK_K13,DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_TRANS_DATE)));
				record.put(Disk.BLOCK_K14,in.get(DB_FEMUTU_NT_WGT_K13));
				record.put(Disk.BLOCK_K15,in.get(DB_FEMUTU_ADD_FLDK15));
				record.put(Disk.BLOCK_K16,in.get(DB_FEMUTU_ADD_FLDK16));
				record.put(Disk.BLOCK_K17,in.get(DB_FEMUTU_ADD_FLDK17));
				record.put(Disk.BLOCK_K18,in.get(DB_FEMUTU_ADD_FLDK18));
				record.put(Disk.BLOCK_K19,in.get(DB_FEMUTU_ADD_FLDK19));
				record.put(Disk.BLOCK_K20,in.get(DB_FEMUTU_ADD_FLDK20));
				record.put(Disk.BLOCK_K21,in.get(DB_FEMUTU_ADD_FLDK21));
				record.put(Disk.BLOCK_K22,in.get(DB_FEMUTU_ADD_FLDK22));
				record.put(Disk.BLOCK_K23,in.get(DB_FEMUTU_ADD_FLDK23));				
				record.put(Disk.BLOCK_M0, Disk.BLOCK_M);
				record.put(Disk.BLOCK_M1, in.get(DB_FEMUTU_PACK_UNIT));
				record.put(Disk.BLOCK_M2, in.get(DB_FEMUTU_NO_OF_PACK));
				record.put(Disk.BLOCK_M3, in.get(DB_FEMUTU_INV_VAL));
				record.put(Disk.BLOCK_M4, in.get(DB_CURR_CODE_FEMUTU));
				record.put(Disk.BLOCK_M5, in.get(DB_FEMUTU_EX_RATE));
				record.put(Disk.BLOCK_M6, DateUtils.convertDateToCustoms(in.get(DB_FEMUTU_TRANS_DATE)));
				record.put(Disk.BLOCK_M7, in.get(DB_FEMUTU_NT_WGT_K13));
				record.put(Disk.BLOCK_M8, in.get(DB_FEMUTU_ADD_FLD_M8));
				record.put(Disk.BLOCK_M9, in.get(DB_FEMUTU_ADD_FLD_M9));
				record.put(Disk.BLOCK_M10, in.get(DB_FEMUTU_ADD_FLD_M10));
				record.put(Disk.BLOCK_M11, in.get(DB_FEMUTU_ADD_FLD_M11));
				record.put(Disk.BLOCK_M12, in.get(DB_FEMUTU_ADD_FLD_M12));
				record.put(Disk.BLOCK_M13, in.get(DB_FEMUTU_ADD_FLD_M13));
				record.put(Disk.BLOCK_M14, in.get(DB_FEMUTU_ADD_FLD_M14));
				record.put(Disk.BLOCK_M15, in.get(DB_FEMUTU_ADD_FLD_M15));
				record.put(Disk.BLOCK_M16, in.get(DB_FEMUTU_ADD_FLD_M16));
			} catch (Exception ex) {
				
				ex.printStackTrace();
				return null;
			}
		}
		return record;
	}
	
	
	
	public TreeMap<String, String> getEmptyDetailRecord() {
		return emptyDetailRecord;
	}


	public TreeMap<String, String> formatRecord(TreeMap<String, String> record){
		
		return formatRecord(record,tableDescription);
	}

}
