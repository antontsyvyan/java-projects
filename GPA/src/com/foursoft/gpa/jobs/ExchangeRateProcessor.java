package com.foursoft.gpa.jobs;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.foursoft.gpa.db.Rates;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.XmlDataProcessor;

import static com.foursoft.gpa.utils.DateUtils.isValidInhouseDate;

public class ExchangeRateProcessor extends XmlDataProcessor {
	
	public static final String DOUANE_MAAND_WISSELKOERS="douanemaandwisselkoers";
	public static final String MUNT_CODE="muntcode";
	public static final String MUNT_SOORT="muntsoort";
	public static final String TARIEF_IN_VREEMDE_VALUTA="tariefinvreemdevaluta";	
	public static final String TARIEF_IN_EURO="tariefineuro";
	public static final String WIJZIGINGS_DATUM="wijzigingsdatum";	
	public static final String JAAR="jaar";
	public static final String MAAND="maand";
	public static final String URL="http://www.belastingdienst.nl/data/douane_wisselkoersen/";
	public static final String PREFIX="wks.douane.wisselkoersen.dd";
	public static final String EXT =".xml";
	
	private int year;
	private int month;
		
	private String period;
	
	private boolean updated;
	private boolean connectionError;
	
	private String ratesUrl="";

	@Override
	public void Process() {
		connectionError=false;
		updated=false;
		Date curDate=new Date();
		SimpleDateFormat formatPeriod = new SimpleDateFormat(DateUtils.FORMAT_PERIOD);
		String curentPeriod=formatPeriod.format(curDate);		
		int lastDay=getLastDay();		
		if(curentPeriod.equals(period)){
			SimpleDateFormat formatDay = new SimpleDateFormat("dd");
			lastDay=Integer.parseInt(formatDay.format(curDate));
		}
		boolean foundUpdate=false;
		for(int i=1; i<=lastDay; i++){
		//for(int i=lastDay; i>=1; i--){
			String requestUrl=URL+PREFIX+period+String.format("%02d", i)+EXT;
			if(isPageExist(requestUrl)){
				this.ratesUrl=requestUrl;
				System.out.println(requestUrl);
				foundUpdate=true;
				parsePage(requestUrl);
				if(updateRates()){
					updated=true;
				}
				//break;
			}else{
				if(connectionError){
					updated=false;
					System.out.println("Connection error.No connection to the interner or proxy settings are wrong.");
					break;
				}
			}			
		}	
		if(!connectionError){
			if(!foundUpdate){
				System.out.println("No update site found for this period");
			}
		}
	}
	
	
	public boolean isUpdated(){
		return updated;
	}
	
	private boolean isPageExist(String requestUrl){
		
		boolean retCode=false;
		
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con=null;
		try {
			con = (HttpURLConnection) new URL(requestUrl).openConnection();
			con.setRequestMethod("HEAD");		
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
				retCode=true;
			}
		} catch (Exception e) {
			connectionError=true;
			e.printStackTrace();
		}finally{
			if(con!=null){
				con.disconnect();
			}
		}
	    		
		return retCode;
	}
	
	private void parsePage(String requestUrl){
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();	
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			RateExchangeXmlHandler handler = new RateExchangeXmlHandler();
			URL url = new URL(requestUrl);
			URLConnection conn = url.openConnection();
			saxParser.parse(conn.getInputStream(), handler);
			records=handler.getRecords();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	private boolean updateRates(){
		TreeMap<String, String> ratesDetail=new TreeMap<String, String>(); 
		boolean success=true;
		if(records!=null && records.size()>0){
			Rates rates = new Rates(false);
			rates.setAutoCommit(false);
			
			SimpleDateFormat formatInhouse=new SimpleDateFormat(DateUtils.FORMAT_INHOUSE);
			String cueDate=formatInhouse.format(new Date());		
			//delete all rates for certain period
			//rates.deleteRatesForPeriod(period);
			
			try{
				//Populate rates
				for(TreeMap<String, String> record : records){
					ratesDetail.clear();
					ratesDetail=rates.getEmptyDetails();
					
					//ratesDetail.put(Rates.DB_RATES_PERIOD, period);
					ratesDetail.put(Rates.DB_RATES_PERIOD, record.get(JAAR)+record.get(MAAND));
					ratesDetail.put(Rates.DB_RATES_CUR_CODE, record.get(MUNT_CODE));
					ratesDetail.put(Rates.DB_RATES_CNTR_CODE, "");
					ratesDetail.put(Rates.DB_RATES_START_DATE, record.get(WIJZIGINGS_DATUM));
					ratesDetail.put(Rates.DB_RATES_STD_RATE, record.get(TARIEF_IN_VREEMDE_VALUTA).replace(".", ""));
					ratesDetail.put(Rates.DB_RATES_REV_RATE, record.get(TARIEF_IN_EURO).replace(".", ""));
					ratesDetail.put(Rates.DB_RATES_CRT_DATE, cueDate);
					
					if (!rates.insertUpdate(ratesDetail)){
						success=false;
						break;
					}
				}
				
				if(success){
					rates.commit();
				}else{
					rates.rollback();
				}
			}catch(Exception e){
				
			}finally{
				rates.closeConnection();
			}
		}
		
		return success;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period=period;
		try {
			this.year=Integer.parseInt(period.substring(0, 4));
			this.month=Integer.parseInt(period.substring(4, 6));
			
		} catch (Exception e) {

		}
	}
	
	
	private int getLastDay(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	
	
	public String getRatesUrl() {
		return ratesUrl;
	}



	class RateExchangeXmlHandler extends DefaultHandler {

		private ArrayList<TreeMap<String, String>> records = new ArrayList<TreeMap<String, String>>();
		private TreeMap<String, String> rateInfo;
		private String value = null;
		private int updateYear;
		private int updateMonth;
		
		@Override
		public void startElement(String uri, String localName, String tag,	Attributes attributes) throws SAXException {
			tag=tag.toLowerCase(); 
			switch (tag) {
				
		        case DOUANE_MAAND_WISSELKOERS: {
		        	 rateInfo = new TreeMap<String, String>();
		        	 rateInfo.put(JAAR, updateYear+"");
		        	 rateInfo.put(MAAND, String.format("%02d", updateMonth));
		        	 rateInfo.put(WIJZIGINGS_DATUM, updateYear+"-"+String.format("%02d", updateMonth)+"-01");
		             break;
		        }
			}

		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			value = String.copyValueOf(ch, start, length).trim();
		}

		@Override
		public void endElement(String uri, String localName, String tag) throws SAXException {

			tag = tag.toLowerCase();
			switch (tag) {
				case JAAR: {
					updateYear=Integer.parseInt(value);
					break;
				}
				case MAAND: {
					updateMonth=Integer.parseInt(value);
					break;
				}
				case DOUANE_MAAND_WISSELKOERS: {
					records.add(rateInfo);
					break;
				}
				case MUNT_CODE: {
					rateInfo.put(MUNT_CODE, value);
					break;
				}
				case MUNT_SOORT: {
					rateInfo.put(MUNT_SOORT, value);
					break;
				}
				case TARIEF_IN_VREEMDE_VALUTA: {
					rateInfo.put(TARIEF_IN_VREEMDE_VALUTA, value);
					break;
				}
				case TARIEF_IN_EURO: {
					rateInfo.put(TARIEF_IN_EURO, value);
					break;
				}
				case WIJZIGINGS_DATUM: {				
					if(isValidInhouseDate(value)){
						rateInfo.put(WIJZIGINGS_DATUM, value);
					}
					break;
				}
			}
		}
		
		public ArrayList<TreeMap<String, String>> getRecords() {
			return records;
		}
		
		
	}

}
