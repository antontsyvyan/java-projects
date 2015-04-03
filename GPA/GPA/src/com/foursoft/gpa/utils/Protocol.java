package com.foursoft.gpa.utils;

import java.util.TreeMap;
import java.util.StringTokenizer;

public class Protocol {
	
	//Protocol commands
	public static String GET_PROGRESS="GET_PROGRESS";
	public static String GET_CSV_PROGRESS="GET_CSV_PROGRESS";
	public static String GET_VERSION_NUMBER="GET_VERSION_NUMBER";
	public static String SEPARATOR=";";
	public static String STATUS_COLLECTING="COLLECTING";
	public static String STATUS_VALIDATING="VALIDATING";
	public static String STATUS_CHECK_CONSISTENCY="CONSISTENCY";
	public static String STATUS_CREATING="CREATING";
	public static String STATUS_FINALIZING="FINALIZING";
	
	public static final TreeMap<String,String> STATUS;
	static {
		STATUS=new TreeMap<String,String>();
		STATUS.put(STATUS_COLLECTING,"Collecting data");
		STATUS.put(STATUS_VALIDATING,"Validating");
		STATUS.put(STATUS_CHECK_CONSISTENCY,"Checking consistency");
		STATUS.put(STATUS_CREATING,"Creating the disk file");
		STATUS.put(STATUS_FINALIZING,"Finalizing");		
	}
	

	public static int globalTotalRecords;
	public static int globalTotalProcessedRecords;	
	public static String globalStatus;
	
	public static int globalTotalRows;
	public static int globalTotalProcessedRows;	
	public static String globalCsvStatus;
	public static String globalCsvFileName;
	public static long globalCsvStartTime;
	public static String globalServerVersion;
	
	public int totalRecords;
	public int totalProcessedRecords;
	public String statusCode;
	public String statusDescription;
	
	public int totalRows;
	public int totalProcessedRows;
	public String csvStatusCode;
	public String csvStatusDescription;
	
	

	public Protocol() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getProgressMessage(){
		
		String totRecords="0";
		String totProcessedRecords="0";
		String status="";
		
		try{
			totRecords=String.valueOf(Protocol.globalTotalRecords);
		}catch(Exception ex){
			
		}
		
		try{
			totProcessedRecords=String.valueOf(Protocol.globalTotalProcessedRecords);
		}catch(Exception ex){
			
		}
		
		try{
			status=Protocol.globalStatus;
		}catch(Exception ex){
			
		}
		
		return status+";"+totRecords+";"+totProcessedRecords;
		
	}
	
	public static String getCsvProgressMessage(){
		
		String totRecords="0";
		String totProcessedRecords="0";
		String csvStartTime="0";
		
		try{
			totRecords=String.valueOf(Protocol.globalTotalRows);
		}catch(Exception ex){
			
		}
		
		try{
			totProcessedRecords=String.valueOf(Protocol.globalTotalProcessedRows);
		}catch(Exception ex){
			
		}
		
		try{
			csvStartTime=String.valueOf(Protocol.globalCsvStartTime);
		}catch(Exception ex){
			
		}				
		
		return totRecords+";"+totProcessedRecords+";"+Protocol.globalCsvFileName+";"+csvStartTime;
		
	}
		
	public void parceProgressMessage(String message){
		setTotalRecords(0);
		setTotalProcessedRecords(0);
		setStatusCode("");
		
		StringTokenizer st = new StringTokenizer(message,SEPARATOR);
		int teller=0;
		while (st.hasMoreElements()) {
			switch (teller){
				case 0: 
					try{
						setStatusCode((String)st.nextElement());
					}catch(Exception ex){						
					}
					break;
				case 1: 
					try{
						setTotalRecords(Integer.parseInt((String)st.nextElement()));
					}catch(Exception ex){						
					}
					break;
				case 2: 
					try{
						setTotalProcessedRecords(Integer.parseInt((String)st.nextElement()));
					}catch(Exception ex){				
					}
					break;
			}
			
			teller++;
		}
		
	}

	
	public void sinchronizeCsvProcessStatus(String message){
		Protocol.globalTotalRows=0;
		Protocol.globalTotalProcessedRows=0;
		Protocol.globalCsvStatus="";
		
		StringTokenizer st = new StringTokenizer(message,SEPARATOR);
		int teller=0;
		while (st.hasMoreElements()) {
			switch (teller){
				case 0: 
					try{
						Protocol.globalTotalRows=Integer.parseInt((String)st.nextElement());
					}catch(Exception ex){						
					}
					break;
				case 1: 
					try{
						globalTotalProcessedRows=Integer.parseInt((String)st.nextElement());
					}catch(Exception ex){				
					}
					break;
				case 2: 
					try{
						globalCsvFileName=(String)st.nextElement();
					}catch(Exception ex){				
					}
					break;
				case 3: 
					try{
						globalCsvStartTime=Long.parseLong((String)st.nextElement());
					}catch(Exception ex){				
					}
					break;
			}
			
			teller++;
		}
				
	}
	
	public void sinchronizeVersionNumber(String message){
		
		try{
			Protocol.globalServerVersion=message;
		}catch(Exception ex){						
		}
	}
		
	
	public double calculateDiskProgress(){
		
		return calculateProgress(getTotalProcessedRecords(),getTotalRecords());
	}
	
	public double calculateCsvProgress(){
		
		return calculateProgress(globalTotalProcessedRows,globalTotalRows);
	}
	
	public double calculateProgress(int processedRecords,int totalRecords){
		double progress=0;
		if(totalRecords>0){
			 //System.out.println("Proc: "+getTotalProcessedRecords());
			 //System.out.println("Tot: "+getTotalRecords());
			 progress=(processedRecords*1.0)/(totalRecords*1.0);
			 progress=progress*100.0;
			 progress=Math.round(progress)/100.0;
			 
			 //System.out.println("Prog rounded: "+progress);
		}
		
		return progress;
	}
	
	public static void initProgress(){
		Protocol.globalTotalRecords=0;
		Protocol.globalTotalProcessedRecords=0;
		Protocol.globalStatus="";
	
	}
	
	public static void initCsvProgress(){
		Protocol.globalTotalRows=0;
		Protocol.globalTotalProcessedRows=0;
		Protocol.globalCsvStatus="";
		Protocol.globalCsvFileName="";
	
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalProcessedRecords() {
		return totalProcessedRecords;
	}

	public void setTotalProcessedRecords(int totalProcessedRecords) {
		this.totalProcessedRecords = totalProcessedRecords;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return STATUS.get(statusCode);
	}

	
	public int getTotalRows() {
		return totalRows;
	}
	

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	

	public int getTotalProcessedRows() {
		return totalProcessedRows;
	}
	

	public void setTotalProcessedRows(int totalProcessedRows) {
		this.totalProcessedRows = totalProcessedRows;
	}
	

	public String getCsvStatusCode() {
		return csvStatusCode;
	}
	

	public void setCsvStatusCode(String csvStatusCode) {
		this.csvStatusCode = csvStatusCode;
	}

	public static String getGlobalServerVersion() {
		return globalServerVersion;
	}

}
