package com.foursoft.gpa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	
	public static final String FORMAT_CSV="dd-MM-yyyy";
	public static final String FORMAT_INHOUSE="yyyy-MM-dd";
	public static final String FORMAT_CUSTOMS="yyyyMMdd";
	public static final String FORMAT_PERIOD="yyyyMM";
	public static final String FORMAT_PERIOD_FRIENDLY="yyyy MMMM";
	public static final String FORMAT_TIME_INHOUSE="HHmmss";
	public static final String FORMAT_TIME="HH:mm:ss";
	public static final String FORMAT_FIRST_DAY="yyyy-MM-01";
	
	//public static final SimpleDateFormat formatInhouse = new SimpleDateFormat(FORMAT_INHOUSE);
	//public static final SimpleDateFormat formatCsv = new SimpleDateFormat(FORMAT_CSV);
	//public static final SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
	//public static final SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
	//public static final SimpleDateFormat formatPeriodFriendly = new SimpleDateFormat(FORMAT_PERIOD_FRIENDLY);
	//public static final SimpleDateFormat formatTimeInhouse = new SimpleDateFormat(FORMAT_TIME_INHOUSE);
	//public static final SimpleDateFormat formatTime = new SimpleDateFormat(FORMAT_TIME);
	//public static final SimpleDateFormat formatFirstDay = new SimpleDateFormat(FORMAT_FIRST_DAY);
	
	public boolean isThisDateValid(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
 
			//if not valid, it will throw ParseException
			sdf.parse(dateToValidate);
			//System.out.println(date);
 
		} catch (ParseException e) {
 
			return false;
		}
 
		return true;
	}
	
	public String getLastDayOfMonth(String value) {  
	
        Date tempDate = new Date(); 
        SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
        
        try {
			tempDate=formatCustoms.parse(value);
			
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(tempDate);  

	        calendar.add(Calendar.MONTH, 1);  
	        calendar.set(Calendar.DAY_OF_MONTH, 1);  
	        calendar.add(Calendar.DATE, -1);  

	        Date lastDayOfMonth = calendar.getTime();
	        
	        value=formatCustoms.format(lastDayOfMonth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
        return value;
        
    }  
	
	public String calculatePreviousPeriod(String period){
		
		Date tempDate=new Date();
		SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
		try {
			tempDate=formatPeriod.parse(period);
			//Subtract one month
			Calendar cal = Calendar.getInstance();
			cal.setTime(tempDate);
			cal.add(Calendar.MONTH, -1);
			tempDate = cal.getTime();

			period=formatPeriod.format(tempDate);
		} catch (ParseException e) {

		}
		
		return period;
		
	}
	
	public static String convertDateToCustoms(String dateValue){
		
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		
		if(dateValue.equals("null")){
			dateValue="";
		}
			
		if(dateValue!=null && !dateValue.trim().equals("")){
			Date tempDate = new Date(); 			
			SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
			//remove quotes
			dateValue=dateValue.replaceAll("'", "");
		
			try {
				tempDate=formatInhouse.parse(dateValue);
				dateValue=formatCustoms.format(tempDate);
			} catch (ParseException e) {
				System.out.println("Value: "+dateValue);
				e.printStackTrace();
			}
		}
		return dateValue;
	}
	
	public String convertCsvDateToInhouse(String dateValue){
		
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		SimpleDateFormat formatCsv = new SimpleDateFormat(FORMAT_CSV);
		Date tempDate = new Date(); 
		if(dateValue!=null && !dateValue.trim().equals("")){
			try {
				tempDate=formatCsv.parse(dateValue);
				dateValue=formatInhouse.format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		return dateValue;
	}
	
	public String convertCsvDateToPeriod(String dateValue){
		
		Date tempDate = new Date(); 
		SimpleDateFormat formatCsv = new SimpleDateFormat(FORMAT_CSV);
		SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
		
		if(dateValue!=null && !dateValue.trim().equals("")){
			try {
				tempDate=formatCsv.parse(dateValue);
				dateValue=formatPeriod.format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		return dateValue;
	}
	
	public String convertInhouseDateToPeriod(String dateValue){
		
		Date tempDate = new Date(); 
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
		if(dateValue!=null && !dateValue.trim().equals("")){
			try {
				tempDate=formatInhouse.parse(dateValue);
				dateValue=formatPeriod.format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		
		return dateValue;
	}
	
	
	public String convertTime(String timeValue){
		
		Date tempDate = new Date(); 
		SimpleDateFormat formatTimeInhouse = new SimpleDateFormat(FORMAT_TIME_INHOUSE);
		SimpleDateFormat formatTime = new SimpleDateFormat(FORMAT_TIME);
		if(timeValue!=null && !timeValue.trim().equals("")){
			try {
				tempDate=formatTimeInhouse.parse(timeValue);
				timeValue=formatTime.format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		return timeValue;
	}
	public static String formatDate(String date) {

		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		String retDate = "null";
		if (date != null && !date.equals("")) {
			
			formatInhouse.setLenient(false);
			try {
				// if not valid, it will throw ParseException
				formatInhouse.parse(date);
				retDate = "'" + date + "'";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return retDate;
	}
	
	public String getCurrentDate(){	
		SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
		return formatCustoms.format(new Date());
				
	}
	
	public String getCurrentDateInhouse(){		
		
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		return formatInhouse.format(new Date());
				
	}
	
	public String getCurrentTimeInhouse(){
		SimpleDateFormat formatTimeInhouse = new SimpleDateFormat(FORMAT_TIME_INHOUSE);
		return formatTimeInhouse.format(new Date());
			
	}
	
	public Date getDate(String date){
		Date retDate=new Date();
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		try {
			retDate=formatInhouse.parse(date);
		}catch (ParseException e) {
			e.printStackTrace();
		}	
		return retDate;
	}
	
	public Date getDateFromCustoms(String date){
		Date retDate=new Date();
		SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
		try {
			retDate=formatCustoms.parse(date);
		}catch (ParseException e) {
			e.printStackTrace();
		}	
		return retDate;
	}
	public Date getDateCsv(String date){
		Date retDate=new Date();
		SimpleDateFormat formatCsv = new SimpleDateFormat(FORMAT_CSV);
		try {
			retDate=formatCsv.parse(date);
		}catch (ParseException e) {
			e.printStackTrace();
		}	
		return retDate;
	}
	
	public String getYear(String date){
		String year="";
		SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
		try {
			Date tmp=formatCustoms.parse(date);
			SimpleDateFormat formatYear = new SimpleDateFormat("yy");
			year=formatYear.format(tmp);
		}catch (ParseException e) {
			e.printStackTrace();
		}	
		return year;
	}
	
	public boolean isInPeriod(String date,String period){
		SimpleDateFormat formatCustoms = new SimpleDateFormat(FORMAT_CUSTOMS);
		SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
		try {
			Date tempDate=formatCustoms.parse(date);
			String tmpPeriod=formatPeriod.format(tempDate);
			if(period.equals(tmpPeriod)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		return false;
	}
	
	public static boolean isValidInhouseDate(String date){
		SimpleDateFormat formatInhouse=new SimpleDateFormat(FORMAT_INHOUSE);
		try {
			formatInhouse.parse(date);
		} catch (ParseException e) {
			return false;
		}
				
		return true;
	}
	
	public static String getFriendlyPeriod(String period){
		
		Date tempDate = new Date(); 
		SimpleDateFormat formatPeriod = new SimpleDateFormat(FORMAT_PERIOD);
		SimpleDateFormat formatPeriodFriendly = new SimpleDateFormat(FORMAT_PERIOD_FRIENDLY);
		if(period!=null && !period.trim().equals("")){
			try {
				tempDate=formatPeriod.parse(period);
				period=formatPeriodFriendly.format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		return period;
	}
	
	public static String getMonthName(String month){
		
		Date tempDate = new Date(); 
		if(month!=null && !month.trim().equals("")){
			try {
				tempDate=new SimpleDateFormat("MM").parse(month);
				month=new SimpleDateFormat("MMMM").format(tempDate);
			} catch (ParseException e) {
	
			}
		}
		return month;
	}
}
