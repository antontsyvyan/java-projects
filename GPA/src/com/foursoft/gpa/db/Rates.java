package com.foursoft.gpa.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.Main;
import com.foursoft.gpa.utils.Codetable;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.cache.CacheObject;

public class Rates extends ConnectionDB {
	
	public static final String DB_RATES = "RATES";
	public static final String DB_RATES_PERIOD = "RATES_PERIOD";
	public static final String DB_RATES_CUR_CODE = "RATES_CUR_CODE";
	public static final String DB_RATES_CNTR_CODE = "RATES_CNTR_CODE";
	public static final String DB_RATES_START_DATE = "RATES_START_DATE";
	public static final String DB_RATES_STD_RATE = "RATES_STD_RATE";
	public static final String DB_RATES_REV_RATE = "RATES_REV_RATE";
	public static final String DB_RATES_CRT_DATE = "RATES_CRT_DATE";
	
	public static final String COUNTRY_PREFIX= Codetable.GENERAL+"_"+Codetable.COUNTRY_CODES;
	public static final String CURRENCY_PREFIX=Codetable.GENERAL+"_"+Codetable.CURRENCY_CODES;
	
	public static final String COUNTRY_NAME = COUNTRY_PREFIX+"_"+Cdbk.DB_CDBK_DESC1;	
	public static final String CURRENCY_NAME = CURRENCY_PREFIX+"_"+Cdbk.DB_CDBK_DESC1;
	
	private DateUtils du = new DateUtils();
	
	
	public Rates() {
		this.tableDescription=getTableMetadata(DB_RATES);

	}
	
	public Rates(boolean closeFlag) {		
		super(closeFlag);
		this.tableDescription=getTableMetadata(DB_RATES);
	}

	
	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_RATES_PERIOD +
				"," +DB_RATES_CUR_CODE +
				"," +DB_RATES_CNTR_CODE +
				"," +DB_RATES_START_DATE +
				"," +DB_RATES_STD_RATE +
				"," +DB_RATES_REV_RATE +
				"," +DB_RATES_CRT_DATE +
				" FROM "+getDbName()+DB_RATES;
		return selectPart;
   }
	
	public String getSelectPartExtended(String language){ 
		String selectPart="SELECT "+DB_RATES_PERIOD +
				"," +DB_RATES_CUR_CODE +
				"," +DB_RATES_CNTR_CODE +
				"," +DB_RATES_START_DATE +
				"," +DB_RATES_STD_RATE +
				"," +DB_RATES_REV_RATE +
				"," +DB_RATES_CRT_DATE +
				"," +Cdbk.DB_CDBK_DESC1+
				" FROM "+getDbName()+DB_RATES;
		/*			
		statement+=" LEFT OUTER JOIN "+getDbName()+Cdbk.DB_CDBK+" "+COUNTRY_PREFIX+ 
				" ON ("+COUNTRY_PREFIX+"."+Cdbk.DB_CDBK_CODE+"="+DB_RATES_CNTR_CODE+
				" AND "+COUNTRY_PREFIX+"."+Cdbk.DB_CDBK_LAN+"='"+language.toUpperCase()+"'"+ 
				" AND "+COUNTRY_PREFIX+"."+Cdbk.DB_CDBK_DECL_TYPE+"='"+Codetable.GENERAL+"'"+ 
				" AND "+COUNTRY_PREFIX+"."+Cdbk.DB_CDBK_TBL_NM+"='"+Codetable.COUNTRY_CODES+"')";
		*/
		selectPart+=" LEFT OUTER JOIN "+getDbName()+Cdbk.DB_CDBK+" "+CURRENCY_PREFIX+ 
				" ON ("+CURRENCY_PREFIX+"."+Cdbk.DB_CDBK_CODE+"="+DB_RATES_CUR_CODE+
				" AND "+CURRENCY_PREFIX+"."+Cdbk.DB_CDBK_LAN+"='"+language.toUpperCase()+"'"+ 
				" AND "+CURRENCY_PREFIX+"."+Cdbk.DB_CDBK_DECL_TYPE+"='"+Codetable.GENERAL+"'"+ 
				" AND "+CURRENCY_PREFIX+"."+Cdbk.DB_CDBK_TBL_NM+"='"+Codetable.CURRENCY_CODES+"')";
		
		return selectPart;
	}
	
	public TreeMap<String, String> getRates(String period, String currency, String country, String date){
		
		String statement = getSelectPart() + " WHERE " +DB_RATES_CUR_CODE+"='"+currency+"'";
		statement +=" AND "+DB_RATES_PERIOD+"="+period;
		//statement +=" AND "+DB_RATES_CNTR_CODE+"='"+country+"'";
		statement +=" AND "+DB_RATES_START_DATE+"='"+date+"'";

		return readTable(statement);
	}
	
	public TreeMap<String, String> getRateForCurrencyFromDb(String currency, String date){
		
		TreeMap<String, String> record=null;
		
		String period=du.convertInhouseDateToPeriod(date);
		if(!period.equals(date)){
		
			String statement = getSelectPart() + " WHERE " +DB_RATES_CUR_CODE+"='"+currency+"'";
			//statement +=" AND "+DB_RATES_PERIOD+"="+period;
			statement +=" AND "+DB_RATES_START_DATE+"<='"+date+"'";
			statement +=" ORDER BY "+DB_RATES_START_DATE+ " DESC";
			
			ArrayList<TreeMap<String, String>> details = readTableMultiple(statement);
			
			if(details!=null && details.size()>0){
				record=new TreeMap<String, String>();
				record=details.get(0);
			}			
		}
		
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getRateForCurrency(String currency, String date){
		TreeMap<String, String> record= new TreeMap<String, String>();	
		
		String identifier=DB_RATES+"-"+currency+date;
		
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		if(cob!=null){
			record=(TreeMap<String, String>) cob.getValue();
		}else{
			record=getRateForCurrencyFromDb(currency,date);
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
				
		return record;
		
	}
	public ArrayList<TreeMap<String, String>> getRatesForPeriod(String period ,String language){
		
		ArrayList<TreeMap<String, String>> records=null;
		
		if(period!=null && !period.trim().equals("")){
			String statement=getSelectPartExtended(language);
			statement+=" WHERE  " + DB_RATES_PERIOD+"='"+period+"'";
			
			records=readTableMultiple(statement);
		}
		
		return records;
		
	}
	
	public ArrayList<TreeMap<String, String>> getRatesForPeriodByDate(String dateValue ,String language){
		
		SimpleDateFormat formatInhouse=new SimpleDateFormat(DateUtils.FORMAT_INHOUSE);
		SimpleDateFormat formatFirst = new SimpleDateFormat("yyyy-MM-01");
		ArrayList<TreeMap<String, String>> records=null;
		//parse date
		
		if(dateValue!=null && !dateValue.trim().equals("")){
			Date tempDate = new Date(); 
			try {
				tempDate=formatInhouse.parse(dateValue);
				
				Calendar c = Calendar.getInstance();
				c.setTime(tempDate);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				String firstDay=formatFirst.format(tempDate);
				String lastDay=formatInhouse.format(c.getTime());
				
				String statement=getSelectPartExtended(language);
				
				statement+=" WHERE  " + DB_RATES_START_DATE+">='"+firstDay+"'";
				statement+=" AND  " + DB_RATES_START_DATE+"<='"+lastDay+"'";
				
				
				records=readTableMultiple(statement);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		
		
		
		return records;
	}
	
	public ArrayList<String> getRatePeriods(){
		
		ArrayList<TreeMap<String, String>> records=null;
		
		String statement="SELECT "+DB_RATES_PERIOD + 
				" FROM "+getDbName()+DB_RATES+
				" GROUP BY "+DB_RATES_PERIOD +
				" ORDER BY "+DB_RATES_PERIOD +" DESC";
		
		records=readTableMultiple(statement);
		ArrayList<String> list=new ArrayList<String>();
		
		if(records!=null&& records.size()>0){
			for (TreeMap<String, String> res : records) {
			    list.add(res.get(DB_RATES_PERIOD));
			}
		}
		
		
		return list;
	}
	
	public boolean deleteRatesForPeriod(String period){
		String statement = "DELETE FROM " +getDbName()+DB_RATES+ " WHERE " +DB_RATES_PERIOD+"="+period;
		return insertUpdateTable(statement);
	}
	
	public boolean deleteRate(String period, String currency, String country, String date){
		String statement = "DELETE FROM " +getDbName()+DB_RATES+ " WHERE " +DB_RATES_PERIOD+"="+period;
		statement +=" AND "+DB_RATES_CUR_CODE+"='"+currency+"'";
		//statement +=" AND "+DB_RATES_CNTR_CODE+"='"+country+"'";
		statement +=" AND "+DB_RATES_START_DATE+"='"+date+"'";
		return insertUpdateTable(statement);
	}
	
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=false;
		//retrieve key values
		String period=in.get(DB_RATES_PERIOD);
		String currency=in.get(DB_RATES_CUR_CODE);
		String country=in.get(DB_RATES_CNTR_CODE);
		String date=in.get(DB_RATES_START_DATE);
		
		TreeMap<String, String> tmpRecord =  getRates(period,currency,country, date);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			deleteRate(period,currency,country, date);
		}
		
		retCode=insertRates(in);
				
		return retCode;
	}
	
	
	public boolean insertRates(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_RATES +" VALUES(";
		statement+=formatInt(record.get(DB_RATES_PERIOD))+"," ;
		statement+="'"+formatString(record.get(DB_RATES_CUR_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_RATES_CNTR_CODE))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_RATES_START_DATE))+"," ;
		statement+=formatDecimal(record.get(DB_RATES_STD_RATE))+"," ;
		statement+=formatDecimal(record.get(DB_RATES_REV_RATE))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_RATES_CRT_DATE))+")" ;

		return insertUpdateTable(statement);
	}

}
