package com.foursoft.gpa.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.db.Femtra;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.XmlDataProcessor;

public class XmlToTransitProcessor extends XmlDataProcessor {

	@Override
	public void Process() {	
		
		setLocked(false);	
		
		boolean returnCode=true;
		Femtra femtra = new Femtra();
		
		if(records!=null && records.size()>0){
			try {
				if(ProcessSemaphore.femtraSemaphore.tryAcquire()){
					
					Protocol.initCsvProgress();
					Protocol.globalTotalRows=records.size();
					Protocol.globalCsvFileName=new File(fileName).getName();
					Protocol.globalCsvStartTime=new Date().getTime();
					
					int row=0;
					for(TreeMap<String, String> record : records){
						
						TreeMap<String, String> fileRecord=femtra.getEmptyDetails();			
						fileRecord.putAll(record);
						femtra.insertUpdate(record);	
						
						Protocol.globalTotalProcessedRows=row++;
						
					}
				}else{
					log.severe("Unable to aquire femtra");
					setLocked(false);
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
				ProcessSemaphore.femtraSemaphore.release();
				Protocol.initCsvProgress();
			}
		}
		
		
		
	}

	public void setRecord(ArrayList<TreeMap<String, String>> records) {
		this.records = records;
	}

}
