package com.foursoft.gpa.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.db.Feminb;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.XmlDataProcessor;

public class XmlToInboundProcessor extends XmlDataProcessor {

	@Override
	public void Process() {
		
		setLocked(false);			
		boolean returnCode=true;
		Feminb feminb = new Feminb();
		
		if(records!=null && records.size()>0){
			try {			
				if(ProcessSemaphore.feminbSemaphore.tryAcquire()){
					Protocol.initCsvProgress();
					Protocol.globalTotalRows=records.size();
					Protocol.globalCsvFileName=new File(fileName).getName();
					Protocol.globalCsvStartTime=new Date().getTime();
					
					int row=0;
					for(TreeMap<String, String> record : records){
						
						TreeMap<String, String> fileRecord=feminb.getEmptyDetails();			
						fileRecord.putAll(record);
						feminb.insertUpdate(record);	
						
						Protocol.globalTotalProcessedRows=row++;
						
					}
				}else{
					log.severe("Unable to aquire feminb");
					setLocked(true);
				}
			} catch (Exception e) {
				log.severe("Error occured during the processing! Reason: "+e +" See procrun error file for more details.");			
				e.printStackTrace();
				log.severe("Error occured during the processing! File "+fileName+" is not loaded");
				returnCode=false;
			}finally{
		
				if(returnCode){
					//update DB
				}
				ProcessSemaphore.feminbSemaphore.release();
				Protocol.initCsvProgress();
			}
		}		
		
	}

	public void setRecord(ArrayList<TreeMap<String, String>> records) {
		this.records = records;
	}

}
