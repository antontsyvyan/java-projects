package com.foursoft.gpa.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Arttar;
import com.foursoft.gpa.db.Defdata;
import com.foursoft.gpa.db.Rates;
import com.foursoft.gpa.db.Tariff;

public abstract class Processors {

	public Logger log = Logger.getLogger(this.getClass().toString());
	
	public static final String WAREHOUSE="W";
	public static final String PRELIMINARY="2";
	public static final String FINAL="3";
	public static final String DEFAULT_FEEDING_SYSTEM="DEFAULT";
	public static final String DOMPROC_FEEDING_SYSTEM="DOMPROC";
	public static final String AF="AF";
	public static final String BI="BI";
	public static final String BV="BV";
	public static final String EV="EV";
	public static final String HW="HW";
	public static final String INBOUND="I";
	public static final String OUTBOUND="O";
	public static final String DOMPROC="D";
	public static final String DISK_TEST="N";
	public static final String DISK_FINAL="Y";
	public static final String CHARGES_PRELIMINARY="V";
	public static final String CHARGES_FINAL="D";
	
	public static final String STATUS_TO_BE_PROCESSED="P";
	public static final String STATUS_PROCESSED="X";
	
	//Request types in GPAREQ table
	public static final String DOMPROC_PROOF="IMP-PROOF";
	public static final String DOMPROC_FINAL="IMP-FINAL";
	public static final String DOMPROC_RESET="IMP-RESET";
	public static final String GPA_PROOF="ENT-PROOF";
	public static final String GPA_FINAL="ENT-FINAL";
	public static final String GPA_RESET="ENT-RESET";
	//Statuses in GPAREQ table
	public static final String REQUEST_STATUS_NEW="NEW";
	public static final String REQUEST_STATUS_RUNNING="RUNNING";
	public static final String REQUEST_STATUS_PROCESSED="PROCESSED";
	public static final String REQUEST_STATUS_ERROR="ERROR";
	public static final String REQUEST_STATUS_WARNING="WARNING";
	
	//Conditions	
	public static final String COND_BEGINS_WITH="BEGINS WITH";
	public static final String COND_EQUALS="EQUALS";
	public static final String COND_CONTAINS="CONTAINS";
	
	
	//Languages
	public static final String NL="NL";
	
	public static final int DISK_METHOD_1=1;
	public static final int DISK_METHOD_2=2;
	public static final int DISK_METHOD_3=3;
	
	public static final String LINE_SEPARATOR=System.getProperty("line.separator");
	
	public static String[] METHODS=new String[]{Integer.toString(DISK_METHOD_1),Integer.toString(DISK_METHOD_2),Integer.toString(DISK_METHOD_3)};
	
	protected TreeMap<String, String> companyDetail= new TreeMap<String, String> ();
	protected TreeMap<String, String> defaultDetail= new TreeMap<String, String> ();
	protected TreeMap<String, String> femutiDetail=new TreeMap<String, String> ();
	
	protected DateUtils du=new DateUtils();
	private Account account=new Account();
	private Arttar arttar= new Arttar();
	private Tariff tariff= new Tariff();
	private Rates rates = new Rates();
	
	
	public abstract void Process();
	
	
	protected void readCompanyData(String accountId){
			
		companyDetail=account.getAccountDetailsCached(accountId);
	}
	
	protected void readDefaultData(TreeMap<String, String> det){
		Defdata defdata=new Defdata();
		//TODO Retrieve default values
		String program=Thread.currentThread().getName();
		String inout="";
		String code="";
		String type="";
		String tariff="99999999999999";
		
		defaultDetail=defdata.readDefaultData(getFeedingSystem(),program , inout,code ,type, tariff);
	}
	
	
	protected String getFeedingSystem(){
		
		String feedingSystem=DEFAULT_FEEDING_SYSTEM;
		if(companyDetail!=null && companyDetail.size()>0){
			if(!companyDetail.get(Account.DB_ACCOUNT_FEEDING_SYSTEM).trim().equals("")){
				feedingSystem=companyDetail.get(Account.DB_ACCOUNT_FEEDING_SYSTEM).trim();
			}
		}				
		return feedingSystem;
		
	}
	
	protected String getCountry(String country,String field){
		
		if(country==null ||country.trim().length()==0){
			if(femutiDetail!=null && femutiDetail.size()>0){
				country=femutiDetail.get(field);
			}
		}
		
		return country;
	}
	
	protected String getWarenhouseType(){
		
		String type="";		
		if(companyDetail!=null){
			//TODO get type from company details
		}		
		return type;	
	}
	
	protected String getDefaultFieldValue(String field){
		String value="";
		if(defaultDetail!=null && defaultDetail.size()>0){
			try{
				value=defaultDetail.get(field);
			}catch(Exception ex){
				
			}
		}
		return value;
	}
	
	protected String getCompanyNumber(){
		String company="";
		//TODO Determine where to find the company number
		
		return company;
	}
	
	protected String getPeriod(String arrivalDate){
		String period="";
		if(arrivalDate!=null && arrivalDate.length()>7){
			//period = position 1-7 from the date field excluding "-" e.g. 201307
			period=arrivalDate.substring(0,4)+arrivalDate.substring(5,7);
		}
		
		
		return period;
	}
	
	protected String calculateWeight(String weight,String numberOfPieces){
		
		int weightNum= (int)getDouble(weight)*getInt(numberOfPieces);
		
		return ""+weightNum;
				
	}	
	
	protected String getPreferenceCode(String articleCode){
		String pref="";
		//TODO Retrieve preference code
		return pref;
	}
	
	
	protected TreeMap<String, String> getTariff(String article,String accountId, String codeProv, String date){
		TreeMap<String, String>tariffRecord=null;
		TreeMap<String, String> arttarRecord=arttar.getCurrentTariffCached(article, accountId, codeProv, date);
		boolean found=false;
		if(arttarRecord!=null){	
			tariffRecord=tariff.getTariffCached(arttarRecord.get(Arttar.DB_TARIFF_CODE), codeProv, date);
			if(tariffRecord!=null &&tariffRecord.size()>0){	
				found=true;
			}
		}
		
		if(!found){
			return tariff.initRecord();
		}
		
		return tariffRecord;
	}
	
	
	protected double getRate(String currency, String jobDate){
		double rateValue=0;
		
		if(currency.equals("EUR")){
			rateValue=1.0;
		}else{		
			TreeMap<String, String> rateRecord=rates.getRateForCurrency(currency, jobDate);
			if(rateRecord!=null){	
				rateValue=Double.parseDouble(rateRecord.get(Rates.DB_RATES_REV_RATE));
			}
		}
		
		return rateValue;
	}
	
	protected String getDocumentNumber(String value,String type){
		int pos=8;
		if(type.equals("821")&& value.length()>pos){
			value=value.substring(value.length()-pos);
		}
		
		return value;
	}
	/*
	protected String getPlaceIssuing(String value,String type,String place){
		int pos=10;
		if(type.equals("821")&& value.length()>=pos){
			place=value.substring(2,pos);
		}
		
		return place;
	}
	
	*/
	
	protected int getInt(String val){
		int nbr=0;
		try{
			nbr =Integer.parseInt(val);

		}catch(Exception ex){
			
		}
		return nbr;
	}
	
	protected float getFloat(String val){
		float nbr=0;
		
		try{
			nbr =Float.parseFloat(val);	

		}catch(Exception ex){
			
		}
		return nbr;
	}
	
	protected double getDouble(String val){
		double nbr=0;
		
		try{
			nbr =Double.parseDouble(val);	

		}catch(Exception ex){
			
		}
		return nbr;
	}
	
	protected void saveFile(File f, String path){
		
		File folder= new File(path);
		//create folder if not exist
		if (folder.isDirectory() == false) {
			folder.mkdir();
        }
		//get file name
		String name = f.getName();		
		String newFileName=path+ "\\"+name;
		if(new File(newFileName).exists()){
			SimpleDateFormat dateFormat = new SimpleDateFormat(	"yyyyMMddHHmmssS");
			String prefix = dateFormat.format(new Date());
			newFileName = path + "\\" + prefix + "-"+ name;
		}
		//move file to folder
		f.renameTo(new File(newFileName));
	}
	
}
