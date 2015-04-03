package com.foursoft.gpa.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import com.foursoft.gpa.db.Femart;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.XmlDataProcessor;

public class XmlArticleProcessor extends XmlDataProcessor {

	@Override
	public void Process() {
		
		Protocol.initCsvProgress();
		Protocol.globalTotalRows=records.size();
		Protocol.globalCsvFileName=new File(fileName).getName();
		Protocol.globalCsvStartTime=new Date().getTime();
		
		boolean returnCode=true;
		Femart femart = new Femart();
		
		if(records!=null && records.size()>0){
			try {
				int row=0;
				for(TreeMap<String, String> record : records){
					
					TreeMap<String, String> fileRecord=femart.getEmptyDetails();			
					fileRecord.putAll(record);
					femart.insertUpdate(record);	
					
					Protocol.globalTotalProcessedRows=row++;
					
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
			}
		}
		
		Protocol.initCsvProgress();
		
	}

	public void setRecord(ArrayList<TreeMap<String, String>> records) {
		this.records = records;
	}

}
