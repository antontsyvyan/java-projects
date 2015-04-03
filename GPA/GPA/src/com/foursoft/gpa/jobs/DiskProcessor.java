package com.foursoft.gpa.jobs;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.utils.Processors;

public class DiskProcessor extends Processors {

	private GpaCore gpa;;
	private Gpareq gpareq= new Gpareq();
	
	public DiskProcessor() {
		
		cleanUpRunningRequests();
	}

	@Override
	public void Process() {
		log.info(" ==> Entering Disk processor. <==");
		
		if(DomprocProcessor.busy){
			log.info("Sleeping for a while.Domproc interface is running");
			return;
		}
		
		int method=0;
		//Read not processed GPA requests
		ArrayList<TreeMap<String, String>> details=gpareq.readRecordsForPeriod(Processors.REQUEST_STATUS_NEW);	
		//Process one by one in the loop
		if (details != null && details.size() > 0) {
			
			for (int i = 0; i < details.size(); i++) {
				String error="";
				String id=details.get(i).get(Gpareq.DB_GPAREQ_ID);
				method=0;
				if(!details.get(i).get(Gpareq.DB_GPAREQ_MTHD).trim().equals("")){
					method=Integer.parseInt(details.get(i).get(Gpareq.DB_GPAREQ_MTHD));
				}
				log.info("Found request "+id);
				gpa=new GpaCore(details.get(i).get(Gpareq.DB_GPAREQ_FED_SYS),details.get(i).get(Gpareq.DB_GPAREQ_CUS),details.get(i).get(Gpareq.DB_GPAREQ_PER));
				gpa.setMethod(method);
				gpa.setRequestId(id);
				
 				boolean updateStatus=false;
				//String error="";
				String status=Processors.REQUEST_STATUS_PROCESSED;
				
				boolean delete=false;
				if(details.get(i).get(Gpareq.DB_GPAREQ_RLBCK).equals("Y")){
					delete=true;
				}				
				if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.DOMPROC_FINAL)){				
					if(!gpa.finalDomproc()){
						status=Processors.REQUEST_STATUS_ERROR;
			    	}
					updateStatus=true;					
				}else if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.DOMPROC_PROOF)){					
					if(!gpa.testDomproc()){						
						status=Processors.REQUEST_STATUS_ERROR;
			    	}					
					updateStatus=true;					
				}else if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.DOMPROC_RESET)){					
					if(!gpa.resetGpaNew(Disk.INVOER, delete)){
						status=Processors.REQUEST_STATUS_ERROR;
					}									
					updateStatus=true;				
				}else if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.GPA_FINAL)){
					if(!gpa.finalGpa()){
						status=Processors.REQUEST_STATUS_ERROR;
					}
					updateStatus=true;
				}else if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.GPA_PROOF)){
					if(!gpa.testGpa()){
						status=Processors.REQUEST_STATUS_ERROR;
					}
					updateStatus=true;					
				}else if(details.get(i).get(Gpareq.DB_GPAREQ_TYPE).equals(Processors.GPA_RESET)){
					
					if(!gpa.resetGpaNew(Disk.ENTREPOT, delete)){
						status=Processors.REQUEST_STATUS_ERROR;
					} 
					updateStatus=true;
				}
				
				if(updateStatus){					
					if(!gpa.getErrorMessage().equals("")){
						error=gpa.getErrorMessage();
					}					
					gpareq.updateStatusGpareq(id, status,error,gpa.getDiskFileName());
				}
				
				log.info("Request "+id+" has been processed");
				
			}
		}
		//Retrieve customer setup(file location?, e-mail?)	
	}
	
	
	private void cleanUpRunningRequests(){
		//System.out.println("Cleaning up running requests");
		gpareq.resetStatusGpareq(Processors.REQUEST_STATUS_RUNNING, Processors.REQUEST_STATUS_NEW);
	}

}
