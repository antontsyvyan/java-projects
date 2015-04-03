package com.foursoft.gpa.jobs;

import static com.foursoft.gpa.utils.GpaCalculator.getCustomsDutyAmout;
import static com.foursoft.gpa.utils.GpaCalculator.getTaxValue;
import static com.foursoft.gpa.utils.GpaCalculator.round;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVReader;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Article;
import com.foursoft.gpa.db.Arttar;
import com.foursoft.gpa.db.Custkey;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.db.Prmchk;
import com.foursoft.gpa.db.Rates;
import com.foursoft.gpa.db.Tariff;
import com.foursoft.gpa.db.Tarlvo;
import com.foursoft.gpa.utils.Codetable;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.FileProcessors;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;

public class DomprocProcessor extends FileProcessors {

	private static final String EXTENSION = "csv";
	private static final int CSV_NBR_COLUMNS=130;
	
	public static boolean busy=false;
	
	//private boolean firstLine=true;
	
	//private String lastPeriod="";
	
	private DateUtils du= new  DateUtils();
	
	private ControleBrochure cb;
	
	private Prmchk prmchk= new Prmchk();
	
	private Rates rates = new Rates();
	
	private Article article = new Article();
	
	private Account account= new Account();
	
	private Arttar arttar= new Arttar();
	
	private Tariff tariff= new Tariff();
	
	private Custkey kustkey= new Custkey();
		
	private TreeMap<String, String> countries;
	
	private TreeMap<String, String> currencies;
	
	private TreeMap<String, String> incoterms;
	
	private TreeMap<String, String> documentCodes;
	
	private ArrayList<String> uniqueKeys;
	
	private TreeMap<String, String> prmchkDetails;
	
	private String searchField;
	
	private Femutu femutu;
		
	//Errors
	public static final String ERROR_PERIOD_CLOSED="Period %s has been already closed.";
	public static final String ERROR_DATE_FORMAT_INVALID="Date format in column %s is not valid. The valid formats are:  "+DateUtils.FORMAT_CSV+","+DateUtils.FORMAT_INHOUSE;
	public static final String ERROR_TRANSACTION_DATE="Transaction date date is before Proforma invoice date.";
	public static final String ERROR_CODE_IS_INVALID="%s in column %s is not valid.";
	public static final String ERROR_VALUE_IS_EMPTY="%s in column %s is empty.";
	public static final String ERROR_VALUE_IS_NAN="Value in column %s is not a number.";
	public static final String ERROR_COLUMNS_INCORRECT="The row contains wrong number of columns(%s). The right number is "+CSV_NBR_COLUMNS+".";
	
	public static final String WARNING_EXCHANGE_RATE_DIFFERENCE="Aangeleverde koers voor %s (%s) wijkt van de officiele (%s) af."; 
	public static final String WARNING_CUSTOMS_VALUE_DIFFERENCE="Douane waarde(%s) wijkt af (%s)."; 
	public static final String WARNING_VAT_DIFFERENCE="BTW nummer wijkt af."; 
	public static final String WARNING_DOC_CODE="Bescheidcode %s wijkt af (%s).";
	public static final String WARNING_HS_CODE="Tariffcode uit csv %s wijkt af van setup(%s)."; 

	//remove quotes from date fields. 		
	public static final String dateFields[]=new String[]{Femutu.DB_FEMUTU_TRANS_DATE,Femutu.DB_FEMUTU_DT_ISSUE_DOC,Femutu.DB_FEMUTU_INV_PROF_DATE};
	
	@Override
	public void init() {
		setExtention(EXTENSION);
		femutu=new Femutu();
	}
	
	@Override
	public boolean loadFile(String fileName){
			
		boolean returnCode=true;				
		CSVReader reader=null;
		String [] nextLine;
				
		SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMddHHmmss");
		String jobNo=fmt.format(new Date());
		
	
		//TreeMap<String, String>femutuDetail=new TreeMap<String, String>();
		//firstLine=true;
		//lastPeriod="";
		//load general data
		Codetable ct=new Codetable();
		countries=ct.getCountriesGeneral();
		currencies=ct.getCurrenciesGeneral();
		incoterms=ct.getIncotermsGeneral();
		documentCodes=ct.getDocumentCodesInvoer();
		uniqueKeys=kustkey.getCustkeys(Resources.getSetting("customer.name"), Femutu.DB_FEMUTU);
		cb= new ControleBrochure(Disk.INVOER);
		prmchkDetails=prmchk.getAllPeriods(Processors.DOMPROC_FEEDING_SYSTEM);
		
		int i = 0;		
		
		ArrayList<TreeMap<String,String>> records= new ArrayList<TreeMap<String,String>>();
		try {
			ProcessSemaphore.femutuSemaphore.acquire();	
			
			reader = new CSVReader(new FileReader(fileName),';');
			List<String[]> myEntries = reader.readAll();
			if(myEntries!=null && myEntries.size()>0){ 
				//Process csv file in the loop. This operation is atomic(insert all records or nothing)
				Protocol.initCsvProgress();
				Protocol.globalTotalRows=myEntries.size();
				Protocol.globalCsvFileName=new File(fileName).getName();
				Protocol.globalCsvStartTime=new Date().getTime();
				for (i = 0; i < myEntries.size(); i++) {
					//nextLine[] is an array of values from the line
					nextLine=myEntries.get(i);
					
					//Check if row contains a right number of columns
					if(nextLine.length<CSV_NBR_COLUMNS){
						this.errorsList.add(new DiskErrorsModel(i+1,"",ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_COLUMNS_INCORRECT,nextLine.length)));
						returnCode=false;
						//Do not validate , go to the next row.
						continue;
					}
					
					if(isValid(nextLine,i)){
						//map, format and complete femutu record
						//femutuDetail.clear();
						TreeMap<String, String> femutuDetail=femutu.formatRecord(mapToFemutu(nextLine,jobNo));
						if(completeRecord(femutuDetail,i)){									
							//validate according to "brochure". The same validation used during creation of the disk file.
							if(isValidBrochure(femutu.mapToDisk(femutuDetail),i)){
								records.add(femutuDetail);
								/*
							 	if(!femutu.insertUpdateFemutu(femutuDetail)){
							 		log.severe("Error occured during the processing! Record not inserted in FEMUTU.");
							 		returnCode=false;
							 		//break;
							 		log.severe(i+1+" row in error");
							 	}
							 	*/
							}else{
								returnCode=false;
							}
						}else{
							returnCode=false;
	
						}				
					}else{
						returnCode=false;
					}
					
					Protocol.globalTotalProcessedRows=i+1;
					
					if(i % 500 == 0){
						Disk.sleep(100000);
					}
										
				}
				//log.severe("\r\n"+errorsList.toString());
			}else{
				if(myEntries.size()<CSV_NBR_COLUMNS){
					this.errorsList.add(new DiskErrorsModel(i+1,"",ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_COLUMNS_INCORRECT,myEntries.size())));
					returnCode=false;
				}
			}
						
		} catch (Exception e) {
			log.severe("Error occured during the processing! Reason: "+e +" See procrun error file for more details.");			
			e.printStackTrace();
			log.severe("Error occured during the processing! File "+fileName+" is not loaded");
			this.errorsList.add(new DiskErrorsModel(i+1,"",ControleBrochure.TYPE_ERROR,"","",e.toString()));
			returnCode=false;
		}finally{
			if(returnCode){
				storeData(records,fileName);
			}						
			//femutu.closeConnection();			
			try {
				reader.close();
			} catch (IOException e) {

			}
			cb=null;
			ct=null;
			
			ProcessSemaphore.femutuSemaphore.release();
		}
		
		Protocol.initCsvProgress();	
		return returnCode;
	}
	
	private String getCustomerName(){
		return  "";
	}
	
	private final boolean storeData(ArrayList<TreeMap<String,String>> records,String fileName){
		
		Protocol.initCsvProgress();
		Protocol.globalTotalRows=records.size();
		Protocol.globalCsvFileName=new File(fileName).getName();
		Protocol.globalCsvStartTime=new Date().getTime();
		
		TreeMap<String,String>record=new TreeMap<String,String>();
		if(records !=null && records.size()>0){
			int row=1;
			Femutu femutu= new Femutu(false);
			try{
				for (int i=0;i<records.size();i++) {
					record.clear();
					record=records.get(i);
					femutu.insertUpdateFemutu(record);
					Protocol.globalTotalProcessedRows=row++;
					if(row % 500 == 0){
						Disk.sleep(10000);
					}
				}
			}catch(Exception e){
				
			}finally{
				femutu.closeConnection();
			}
			
			Protocol.initCsvProgress();		
			
		}
		
		return true;
	}
	
	private boolean isValid(String [] line,int lineNumber){
				
		boolean returnCode=true;
		StringBuffer buffer= new StringBuffer("");
		String value="";
		boolean check=true;
		
		
		
		//check dates
		int columns[]=new int[]{50,105};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("")){			
				if(!du.isThisDateValid(value, DateUtils.FORMAT_INHOUSE)){
					if(!du.isThisDateValid(value, DateUtils.FORMAT_CSV)){
						//buffer.append("Date format in column "+columns[i]+" is not valid. The valid formats are:  "+DateUtils.FORMAT_CSV+","+DateUtils.FORMAT_INHOUSE+"\r\n");
						this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_DATE_FORMAT_INVALID, columns[i]) ));
						
						returnCode=false;
						check=false;
					}else{
						//convert to inhouse and replace
						line[columns[i]]=du.convertCsvDateToInhouse(value);
					}
				}
				
			}else{
				//buffer.append("Date in column "+columns[i]+" is empty."+"\r\n");
				check=false;
			}
		}
		
		if(check){		
			//Check period. If closed - exit validation for this line
			String period=du.convertInhouseDateToPeriod(line[105]);
			//TreeMap<String, String> prmchkDetail=prmchk.readPrmchkRecord(Processors.DOMPROC_FEEDING_SYSTEM,period);
			
			if(prmchkDetails!=null){
				if(prmchkDetails.containsKey(period)){				
					if(prmchkDetails.get(period).equals(DISK_FINAL)){
						this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_PERIOD_CLOSED, period) ));
						return false;
					}
					
				}
			}

			//Transaction date should be great or equals Proforma invoice date		
			if(!isValidDates(line[columns[1]].trim(),line[columns[0]].trim())){
				//buffer.append("Transaction date date is before Proforma invoice date\r\n");	
				this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",ERROR_TRANSACTION_DATE));		
				returnCode=false;
			}
		}
		
		columns=new int[]{12};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("")){
				if(!du.isThisDateValid(value, DateUtils.FORMAT_INHOUSE)){
					if(!du.isThisDateValid(value, DateUtils.FORMAT_CSV)){
						//buffer.append("Date format in column "+columns[i]+" is not valid. The valid formats are:  "+DateUtils.FORMAT_CSV+","+DateUtils.FORMAT_INHOUSE+"\r\n");
						this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_DATE_FORMAT_INVALID, columns[i]) ));
						returnCode=false;
						check=false;
					}else{
						//convert to inhouse
						line[columns[i]]=du.convertCsvDateToInhouse(value);
					}
				}
			}else{
				//buffer.append("Date in column "+columns[i]+" is empty."+"\r\n");
			}
		}
		
		//check currency codes		
		columns=new int[]{37};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("")){
				if(!currencies.containsKey(value.toUpperCase())){
					//buffer.append("Currency in column "+columns[i]+" is not valid."+"\r\n");
					this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_CODE_IS_INVALID,"Currency",columns[i]) ));
					
					returnCode=false;
				}
			}else{
				//buffer.append("Currency in column "+columns[i]+" is empty."+"\r\n");
				this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_VALUE_IS_EMPTY,"Currency",columns[i]) ));
				check=false;
			}
		}
		
		//Check country codes;
		columns=new int[]{13,14};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("")){
				if(!countries.containsKey(value.toUpperCase())){
					//buffer.append("Country code in column "+columns[i]+" is not valid."+"\r\n");
					this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_CODE_IS_INVALID,"Country code",columns[i]) ));
					returnCode=false;
				}
			}else{
				//buffer.append("Country code in column "+columns[i]+" is empty."+"\r\n");
			}
		}
		
		
		//Check incoterm
		columns=new int[]{34};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("")){
				if(!incoterms.containsKey(value.toUpperCase())){
					//buffer.append("Incoterme in column "+columns[i]+" is not valid."+"\r\n");
					this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_CODE_IS_INVALID,"Incoterme",columns[i]) ));
					returnCode=false;
				}
			}else{
				//buffer.append("Incoterm in column "+columns[i]+" is empty."+"\r\n");
			}
		}
		
		
		//Check document codes;
		columns=new int[]{97,98,99,100};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(!value.equals("") && !value.equals("0")){
				if(!documentCodes.containsKey(value.toUpperCase())){
					//buffer.append("Document code in column "+columns[i]+" is not valid."+"\r\n");
					this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_CODE_IS_INVALID,"Document code",columns[i]) ));
					returnCode=false;
				}
			}else{
				//buffer.append("Country code in column "+columns[i]+" is empty."+"\r\n");
			}
		}
		
		//Check mandatory values;
		columns=new int[]{36,106};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			if(value.equals("") || value.equals("0")){
				buffer.append("Waarde in column "+columns[i]+" is verplicht."+"\r\n");
				returnCode=false;
				
				this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_VALUE_IS_EMPTY,"Value",columns[i]) ));
				returnCode=false;
			}
		}
		
		
		//VAT identification number validation
		//This will be checked in Controle Brochure later 
		/*
		columns=new int[]{26};
		value=line[columns[0]].trim();
		VatNumber vat=new VatNumber(value);
		if(!vat.isValid()){
			buffer.append("VAT identification number in column "+columns[0]+" is not valid."+"\r\n");
			
		}
		*/
		
		//Check number fields
		//INT
		columns=new int[]{33};
		for(int i=0;i<columns.length;i++){
			value=line[columns[i]].trim();
			try{
				Integer.parseInt(value); 
			}catch (NumberFormatException nfe){
				this.errorsList.add(new DiskErrorsModel(lineNumber+1,line[8],ControleBrochure.TYPE_ERROR,"","",String.format(ERROR_VALUE_IS_NAN,columns[i]) ));
				returnCode=false;
			}
			
		}
		
				
		return returnCode;
	}
	
	private boolean isValidBrochure(TreeMap<String, String>detail,int line){
		
		boolean retCode=false;
		
		cb.setDetail(detail);
		cb.setLine(line+1);
		cb.setSearchField(searchField);
		
		try {
			if(cb.isValidDetail()){
				retCode= true;
			}
		} catch (Exception e) {
			retCode= false;
			e.printStackTrace();
		}
		
		if(cb.getErrorsList()!=null && cb.getErrorsList().size()>0){
			errorsList.addAll(cb.getErrorsList());
		}
		
		return retCode;
	}
	
	private TreeMap<String, String> mapToFemutu(String[] line,String jobNo){
				
		TreeMap<String, String> femutuDetail=femutu.getEmptyDetailRecord();
		
		femutuDetail.put(Femutu.DB_FEED_SYS_FEMUTU, Processors.DOMPROC_FEEDING_SYSTEM);
		femutuDetail.put(Femutu.DB_FEMUTU_IN_OUT_BOUND, Processors.DOMPROC);
		//femutuDetail.put(Femutu.DB_FEMUTU_JOB_NO, jobNo);
		femutuDetail.put(Femutu.DB_FEMUTU_JOB_NO, line[8]);
		femutuDetail.put(Femutu.DB_FEMUTU_LINE_NO, "0");
		femutuDetail.put(Femutu.DB_FEMUTU_A3, line[8]);
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE,String.format("%6s", line[7]).replace(' ', '0'));
		femutuDetail.put(Femutu.DB_FEMUTU_BON_WRH_TYPE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_STAT_GDS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NAW_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_TYPE, line[9]);
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_NO, line[10]);
		femutuDetail.put(Femutu.DB_FEMUTU_PLACE_ISSUE_DOC, line[11]);		
		femutuDetail.put(Femutu.DB_FEMUTU_DT_ISSUE_DOC, line[12]);
		femutuDetail.put(Femutu.DB_FEMUTU_TG_IND, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_DOC_NO, "");
		//femutuDetail.put(Femutu.DB_FEMUTU_PERIOD, line[10]+String.format("%2s", line[11]).replace(' ', '0'));
		femutuDetail.put(Femutu.DB_FEMUTU_PERIOD, du.convertInhouseDateToPeriod(line[105]));
		femutuDetail.put(Femutu.DB_FEMUTU_MOD, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_VAL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ART_NO, line[1]);
		femutuDetail.put(Femutu.DB_FEMUTU_ART_DESC, line[28]);
		femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CODE, line[29]);
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_CERTIFICATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INVNO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_LN_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_KIND_CODE_D11, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NO_PCS_D12, "");
		femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LOC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_SPARE_FLD_D15, "");
		femutuDetail.put(Femutu.DB_CT_CODE_ORG, line[13].toUpperCase());
		femutuDetail.put(Femutu.DB_CT_CODE_PROV, line[14].toUpperCase());
		femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PREF_CODE2, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_UNITS, line[30]);
		femutuDetail.put(Femutu.DB_FEMUTU_NO_ADD_UNITS, line[31]);
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF, line[32]);
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_LINE, line[33]);
		femutuDetail.put(Femutu.DB_FEMUTU_INV_VAL, line[36]);
		femutuDetail.put(Femutu.DB_CURR_CODE_FEMUTU, line[37]);
		femutuDetail.put(Femutu.DB_FEMUTU_EX_RATE, line[38]);
		femutuDetail.put(Femutu.DB_FEMUTU_INV_PROF_DATE, line[50]);
		femutuDetail.put(Femutu.DB_FEMUTU_COM_PREF, line[53]);
		femutuDetail.put(Femutu.DB_FEMUTU_BOX37, getCodeBox37(line[47],line[48],line[49]));
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_TYPE,line[54]);
		femutuDetail.put(Femutu.DB_FEMUTU_RELATION, "");
		femutuDetail.put(Femutu.DB_FEMUTU_RESTRIC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LIC_FEE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CALC_METHOD, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAL_DET, "1");
		femutuDetail.put(Femutu.DB_FEMUTU_TARIFF_CONT_REQ, "");
		femutuDetail.put(Femutu.DB_MOT_CODE_FEMUTU, line[61]);
		femutuDetail.put(Femutu.DB_FEMUTU_CONT,line[62]);
		femutuDetail.put(Femutu.DB_NAW_CODE_CM, getCustomerName());
		femutuDetail.put(Femutu.DB_FEMUTU_SPEC_INDI, "");
		femutuDetail.put(Femutu.DB_FEMUTU_STAN_PERC_DUTIES, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_CODE_K7, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_NO, line[116]);
		femutuDetail.put(Femutu.DB_FEMUTU_TRANS_DATE, line[105]);
		femutuDetail.put(Femutu.DB_FEMUTU_NT_WGT_K13, roundUp(line[106]));
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK15, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK16, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK17, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK18, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK19, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK20, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK21, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK22, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLDK23, "");
		femutuDetail.put(Femutu.DB_FEMUTU_AUTH_NO, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_LIC_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_SCO_IND, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PRELIM_IND, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CERTIFICATE_NO, "");
		femutuDetail.put(Femutu.DB_NAW_CODE_CONS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CONS_NAME,line[24]);
		femutuDetail.put(Femutu.DB_FEMUTU_CONS_ADDR,line[25]);
		femutuDetail.put(Femutu.DB_FEMUTU_CONS_CITY, line[26]);
		femutuDetail.put(Femutu.DB_CONS_VAT_NO, line[27]);
		femutuDetail.put(Femutu.DB_FEMUTU_VERIFY_BY_CUST, "N");
		femutuDetail.put(Femutu.DB_FEMUTU_INCO_TERMS, line[34]);
		femutuDetail.put(Femutu.DB_FEMUTU_DELIV_PLACE, line[35]);
		
		//Temporary workaround toggle sign of the other costs (E28)
		femutuDetail.put(Femutu.DB_FEMUTU_FRT_AMT_EUR, toggleSign(line[39],line[34]));
		
		femutuDetail.put(Femutu.DB_FEMUTU_INS_AMT_EUR, line[40]);
		
		//Temporary workaround toggle sign of the other costs (E30)
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_DED_EUR, toggleSignE30(line[41],line[34]));
		
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_VAL_EUR, line[42]);		
		femutuDetail.put(Femutu.DB_FEMUTU_IMP_DUT_PERC, line[43]);
		
		femutuDetail.put(Femutu.DB_FEMUTU_AMT_RELIEF, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUT_SPEC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_DUTIES, line[46]);		
		femutuDetail.put(Femutu.DB_FEMUTU_TYPE_OF_CHRGS, line[63]);
		femutuDetail.put(Femutu.DB_FEMUTU_MANU, line[64]);
		femutuDetail.put(Femutu.DB_FEMUTU_AMT_OF_CHRGS, line[65]);
		femutuDetail.put(Femutu.DB_FEMUTU_AGRI_LEV, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_AMT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_CONV_VAL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_VAT_PREF_CODE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G3, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G3, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G5, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G5, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_CODE_G7, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXCISE_AMT_G7, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXT_PERC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ACT_ALCH_STE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_ALC_FREE_LIT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_LTS_OIL, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PER_KG, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PURE_ALC_LTS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_TRAN_DOC, "");
		femutuDetail.put(Femutu.DB_FEMUTU_PACK_UNIT, line[101]);
		femutuDetail.put(Femutu.DB_FEMUTU_NO_OF_PACK, line[102]);
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_LINE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_INV_SLS_DATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_QUAN1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_QUAN2, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_NUM, "");
		femutuDetail.put(Femutu.DB_FEMUTU_EXP_LIC_DATE, "");
		femutuDetail.put(Femutu.DB_CT_CODE_DEST, "");
		femutuDetail.put(Femutu.DB_FEMUTU_BASE_CODE2, "");		
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE1, getCode(line[97]));
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE2, getCode(line[98]));
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE3, getCode(line[99]));
		femutuDetail.put(Femutu.DB_FEMUTU_DOC_CODE4, getCode(line[100]));
		femutuDetail.put(Femutu.DB_FEMUTU_BASE_CODE1, "");
		femutuDetail.put(Femutu.DB_FEMUTU_MTH_PAY, "");
		femutuDetail.put(Femutu.DB_FEMTU_STAT, "");
		femutuDetail.put(Femutu.DB_FEMUTU_MEM_STATE, "");
		femutuDetail.put(Femutu.DB_FEMUTU_STAT, Processors.PRELIMINARY);
		
		
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M8, getCode(line[107]));
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M9, line[108]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M10, line[109]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M11, line[110]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M12, line[111]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M13, line[112]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M14, line[113]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M15, line[114]);
		femutuDetail.put(Femutu.DB_FEMUTU_ADD_FLD_M16, line[115]);
		
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF1, line[2]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF2, line[3]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF3, line[4]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF4, line[5]);
		
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF5, line[116]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF6, line[117]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF7, line[118]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF8, line[119]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF9, line[120]);
		femutuDetail.put(Femutu.DB_FEMUTU_CUST_REF10, line[121]);
		
		//trim all fields
		for (String key : femutuDetail.keySet()) {
			femutuDetail.put(key, femutuDetail.get(key).trim());
		}
									
		return femutuDetail;

	}
	
	private boolean completeRecord(TreeMap<String, String>detail, int lineNumber){
				
		boolean valid=true;
		
		if(Resources.getSetting("autocomplete").equals("Y")){
			
			String accountId="";
						
			TreeMap<String, String> accountRecord=null;
			TreeMap<String, String> articleRecord=null;
			TreeMap<String, String> arttarRecord=null;
			TreeMap<String, String> tariffRecord=null;
					
			for(int i=0;i<dateFields.length;i++){
				detail.put(dateFields[i],detail.get(dateFields[i]).replaceAll("'", ""));
			}
			
			//Retrieve currency rate
			if(Resources.getSetting("autocomplete.rate").equals("Y")){
				if(detail.get(Femutu.DB_CURR_CODE_FEMUTU).equals("EUR")){
					detail.put(Femutu.DB_FEMUTU_EX_RATE, "1");
				}else{
					TreeMap<String, String> rateRecord=rates.getRateForCurrency(detail.get(Femutu.DB_CURR_CODE_FEMUTU), detail.get(Femutu.DB_FEMUTU_TRANS_DATE));							
					if(rateRecord!=null){					
						String csvRate=(detail.get(Femutu.DB_FEMUTU_EX_RATE));
						detail.put(Femutu.DB_FEMUTU_EX_RATE, rateRecord.get(Rates.DB_RATES_REV_RATE));
						
						
						try{
							double cvsRateDouble=Double.parseDouble(csvRate);
							if(cvsRateDouble>0.00){
								if(cvsRateDouble!=Double.parseDouble(rateRecord.get(Rates.DB_RATES_REV_RATE)) ){
									//Warning exchange rate differs from the official
									this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),ControleBrochure.TYPE_WARNING,"","",String.format(WARNING_EXCHANGE_RATE_DIFFERENCE,detail.get(Femutu.DB_CURR_CODE_FEMUTU),csvRate,rateRecord.get(Rates.DB_RATES_REV_RATE))));
								}
							}
						}catch(Exception e){
							this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),ControleBrochure.TYPE_WARNING,"","","Rate is not a decimal value"));				
						}
					}				
				}
			}
											
			//Calculate customs value
			if(Resources.getSetting("calculate.tax.value").equals("Y")){
				double custValue=getTaxValue(detail.get(Femutu.DB_FEMUTU_INV_VAL), 
						detail.get(Femutu.DB_FEMUTU_EX_RATE), 
						detail.get(Femutu.DB_FEMUTU_FRT_AMT_EUR), 
						detail.get(Femutu.DB_FEMUTU_INS_AMT_EUR), 
						detail.get(Femutu.DB_FEMUTU_ADD_DED_EUR));
				
				String csvCustValue=detail.get(Femutu.DB_FEMUTU_CUST_VAL_EUR);
				
				detail.put(Femutu.DB_FEMUTU_CUST_VAL_EUR,custValue+"");			
				try{	
					double csvCustValueDouble=round(csvCustValue,2);
					if(csvCustValueDouble>0.00){
						if(!String.valueOf(custValue).equals(String.valueOf(csvCustValueDouble))){
							//Warning when customs value differs from the calculated
							this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),ControleBrochure.TYPE_WARNING,"","",String.format(WARNING_CUSTOMS_VALUE_DIFFERENCE,custValue,csvCustValueDouble)));				
						}
					}
				}catch(Exception e){
					this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),ControleBrochure.TYPE_WARNING,"","","Tax is not a decimal value"));
				}
								
			}
			//Retrieve account details
			if(Resources.getSetting("autocomplete.account").equals("Y")){				
				accountId=detail.get(Femutu.DB_FEMUTU_CONS_NAME);
				accountRecord=account.getAccountDetailsCached(accountId);							
				if(accountRecord!=null){
					if(detail.get(Femutu.DB_CONS_VAT_NO).equals("")){							
							detail.put(Femutu.DB_FEMUTU_CONS_NAME,accountRecord.get(Account.DB_ACCOUNT_NAME1));
							detail.put(Femutu.DB_FEMUTU_CONS_ADDR,accountRecord.get(Account.DB_ACCOUNT_ADDRESS1));
							detail.put(Femutu.DB_FEMUTU_CONS_CITY, accountRecord.get(Account.DB_ACCOUNT_CITY));
							detail.put(Femutu.DB_CONS_VAT_NO, accountRecord.get(Account.DB_ACCOUNT_VAT_NUMBER));
		
					}else{
						if(!detail.get(Femutu.DB_CONS_VAT_NO).equals(accountRecord.get(Account.DB_ACCOUNT_VAT_NUMBER))){
							//Warning when VAT number differs from the setup
							this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),ControleBrochure.TYPE_WARNING,"","",WARNING_VAT_DIFFERENCE));				
		
						}
					}
				}
			}
							
			//Retrieve article data		
			if(Resources.getSetting("autocomplete.article").equals("Y")){	
				articleRecord=article.getArticleDataCached(detail.get(Femutu.DB_FEMUTU_ART_NO).trim(), accountId);
				
				if(articleRecord!=null){
					double netWeight=Double.parseDouble(articleRecord.get(Article.DB_ARTICLE_NET_WEIGHT));
					double quantity=Double.parseDouble(detail.get(Femutu.DB_FEMUTU_NO_OF_PACK));				
					double totNetWeight=Math.ceil(netWeight*quantity);			
					
					if(detail.get(Femutu.DB_FEMUTU_NT_WGT_K13).equals("0") || detail.get(Femutu.DB_FEMUTU_NT_WGT_K13).equals("") || detail.get(Femutu.DB_FEMUTU_PACK_UNIT).equals("")){
						detail.put(Femutu.DB_FEMUTU_NT_WGT_K13,(int)totNetWeight+"");
					}else{
						//TODO
						//Warning net weight differs
					}
					
					if(detail.get(Femutu.DB_FEMUTU_PACK_UNIT).equals("")){
						detail.put(Femutu.DB_FEMUTU_PACK_UNIT, Article.DB_COLLI_CODE);
					}else{
						//TODO
						//Warning colli code differs
					}
					
					if(Resources.getSetting("switch.3").toUpperCase().equals("Y")){
						//Fill M13,14 and 15 with the article description from article table
						detail.put(Femutu.DB_FEMUTU_ADD_FLD_M13, "");
						detail.put(Femutu.DB_FEMUTU_ADD_FLD_M14, "");
						detail.put(Femutu.DB_FEMUTU_ADD_FLD_M15, "");
						
						String descr=articleRecord.get(Article.DB_ARTICLE_DESCRIPTION);
						if(descr.length()>10){
							detail.put(Femutu.DB_FEMUTU_ADD_FLD_M13, descr.substring(0,10));
							if(descr.length()>20){
								detail.put(Femutu.DB_FEMUTU_ADD_FLD_M14, descr.substring(10,20));
								if(descr.length()>35){
									detail.put(Femutu.DB_FEMUTU_ADD_FLD_M15, descr.substring(20,35));
								}else{
									detail.put(Femutu.DB_FEMUTU_ADD_FLD_M15, descr.substring(20));
								}								
							}else{
								detail.put(Femutu.DB_FEMUTU_ADD_FLD_M14, descr.substring(10));
							}
						}else{
							detail.put(Femutu.DB_FEMUTU_ADD_FLD_M13, descr);
						}						
					}
				}
			}
							
			//Tariff and duty rate
			if(Resources.getSetting("autocomplete.tariff").equals("Y")){	
				arttarRecord=arttar.getCurrentTariffCached(detail.get(Femutu.DB_FEMUTU_ART_NO), accountId, detail.get(Femutu.DB_CT_CODE_PROV),detail.get(Femutu.DB_FEMUTU_TRANS_DATE));			
				if(arttarRecord!=null){				
					tariffRecord=tariff.getTariffCached(arttarRecord.get(Arttar.DB_TARIFF_CODE),detail.get(Femutu.DB_CT_CODE_PROV),detail.get(Femutu.DB_FEMUTU_TRANS_DATE));
					if(tariffRecord!=null){
						
						String tariffCodeFemutu=detail.get(Femutu.DB_FEMUTU_TARIFF_CODE);
						String tariffCodeTariff=tariffRecord.get(Tariff.DB_TARIFF_CODE);
						detail.put(Femutu.DB_FEMUTU_TARIFF_CODE, tariffRecord.get(Tariff.DB_TARIFF_CODE));
						
						if(tariffCodeTariff.length()>=tariffCodeFemutu.length() && tariffCodeFemutu.length()>0){
							String tariffTmp=tariffCodeTariff.substring(0,tariffCodeFemutu.length());
							if(!tariffCodeFemutu.equals(tariffTmp)){
								//Warning tariff code differs from setup
								String errorType=(Resources.getSetting("switch.2").toUpperCase().equals("Y"))?ControleBrochure.TYPE_ERROR:ControleBrochure.TYPE_WARNING;						
								this.errorsList.add(new DiskErrorsModel(lineNumber+1,detail.get(Femutu.DB_FEMUTU_A3),errorType,"","",String.format(WARNING_HS_CODE,tariffCodeFemutu,tariffTmp)));	
								if(Resources.getSetting("switch.2").toUpperCase().equals("Y")){
									valid=false;
								}
							}
						}
												
						String addUnitsFemutu=detail.get(Femutu.DB_FEMUTU_ADD_UNITS);
						String addUnitsTariff=tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS);
						detail.put(Femutu.DB_FEMUTU_ADD_UNITS,tariffRecord.get(Tariff.DB_TARIFF_ADD_UNITS));
						
						if(!addUnitsFemutu.equals(addUnitsTariff)){
							//Warning additional units differs
						}
											
						detail=inspectDocCodes(detail,tariffRecord,lineNumber);
						
						detail.put(Femutu.DB_FEMUTU_IMP_DUT_PERC,tariffRecord.get(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS));
																	
						double custDuties=getCustomsDutyAmout(detail.get(Femutu.DB_FEMUTU_CUST_VAL_EUR),
								detail.get(Femutu.DB_FEMUTU_IMP_DUT_PERC),
								detail.get(Femutu.DB_FEMUTU_AMT_RELIEF),
								detail.get(Femutu.DB_FEMUTU_CUST_DUT_SPEC));
						
						detail.put(Femutu.DB_FEMUTU_CUST_DUTIES,custDuties+"");
											
														
					}else{
						//error tariff code is not setup or not valid anymore
					}
											
				}else{
					//error active tariff code not found					
					detail.put(Femutu.DB_FEMUTU_TARIFF_CODE,"");
				}
			}
			
			//Apply other switches
			if(Resources.getSetting("switch.1").toUpperCase().equals("Y")){
				if(!detail.get(Femutu.DB_FEMUTU_ADD_UNITS).equals("") && detail.get(Femutu.DB_FEMUTU_NO_ADD_UNITS).equals("0")){
					detail.put(Femutu.DB_FEMUTU_NO_ADD_UNITS,detail.get(Femutu.DB_FEMUTU_NO_OF_PACK));
				}
			}
						
		}
		
		//prepare key field
		String femutuKey="";
		String sep="";
		searchField="";
		if(uniqueKeys!=null && uniqueKeys.size()>0){
			for (String key : uniqueKeys) {
				femutuKey+=detail.get(key);
				searchField+=sep+detail.get(key);
				sep="|";			
			}
		}
		detail.put(Femutu.DB_FEMUTU_UNIQUE_KEY, femutuKey);
				

		return valid;
	}
	
	
	private TreeMap<String, String> inspectDocCodes(TreeMap<String, String> detail,TreeMap<String, String> tariffRecord, int lineNumber){
		
		//String[] ar1={detail.get(Femutu.DB_FEMUTU_DOC_CODE1),detail.get(Femutu.DB_FEMUTU_DOC_CODE2),detail.get(Femutu.DB_FEMUTU_DOC_CODE3),detail.get(Femutu.DB_FEMUTU_DOC_CODE4),detail.get(Femutu.DB_FEMUTU_ADD_FLD_M8)};
		String[] ar2={tariffRecord.get(Tarlvo.DB_TARLVO_DOC_CODE1),tariffRecord.get(Tarlvo.DB_TARLVO_DOC_CODE2),tariffRecord.get(Tarlvo.DB_TARLVO_DOC_CODE3),tariffRecord.get(Tarlvo.DB_TARLVO_DOC_CODE4),tariffRecord.get(Tarlvo.DB_TARLVO_DOC_CODE5)};
			
		//Arrays.sort(ar1,Collections.reverseOrder());
		Arrays.sort(ar2,Collections.reverseOrder());
			
		detail.put(Femutu.DB_FEMUTU_DOC_CODE1, ar2[0]);
		detail.put(Femutu.DB_FEMUTU_DOC_CODE2, ar2[1]);
		detail.put(Femutu.DB_FEMUTU_DOC_CODE3, ar2[2]);
		detail.put(Femutu.DB_FEMUTU_DOC_CODE4, ar2[3]);
		detail.put(Femutu.DB_FEMUTU_ADD_FLD_M8, ar2[4]);
		
		return detail;
	}
	
	
	private String toggleSignE30(String value,String term){
		
		if(!value.trim().equals("")){
			value=value.replace(",", ".");
			double decValue=Double.parseDouble(value);
			
			if(decValue>0){
				if(Arrays.asList(ControleBrochure.INCO_TERMS_5).contains(term.trim())){
					decValue=-1*decValue;
					value=decValue+"";
				}
			}
		
		}
		return value;
	}
	
	private String toggleSign(String value,String term){
		
		if(!value.trim().equals("")){
			value=value.replace(",", ".");
			double decValue=Double.parseDouble(value);
			
			if(decValue>0){
				if(Arrays.asList(ControleBrochure.INCO_TERMS_2).contains(term.trim())){
					decValue=-1*decValue;
					value=decValue+"";
				}
			}
		
		}
		return value;
	}
	
	
	
	private String roundUp (String value){
		
		value=value.replace(",", ".");
		
		int val = (int)Math.ceil((double)Double.parseDouble(value));  
		
		return val+"";
	}
	
	private boolean isValidDates(String shipmentDate, String arrivalDate){
		
		boolean valid=true;
		
		switch (du.getDate(shipmentDate).compareTo(du.getDate(arrivalDate))) {
		 case -1:  
			 valid = false;
			 //errorCollector.append("Shipment date is before arrival date");	
			 break;
		 case 0:	break;
		 case 1:	break;
		default:
			valid = false;
			
		}
		
		return valid;
	}
	private String getCode(String code){
		
		if(code.trim().equals("0")){
			code="";
		}
		return code;
	}
	
	private String getCodeBox37(String code1, String code2, String code3){
		
		String code37="";
		
		int code1Int=0;
		int code2Int=0;
		int code3Int=0;
		
		try{
			code1Int=Integer.parseInt(code1.trim());
		}catch(Exception ex){
			
		}
		
		try{
			code2Int=Integer.parseInt(code2.trim());
		}catch(Exception ex){
			
		}
		
		try{
			code3Int=Integer.parseInt(code3.trim());
		}catch(Exception ex){
			
		}
		
		code37=String.format("%2s",code1Int)+String.format("%2s",code2Int)+String.format("%3s",code3Int);
		
		return code37.replace(' ', '0');
	}

}
