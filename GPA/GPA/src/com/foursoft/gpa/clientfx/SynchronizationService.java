package com.foursoft.gpa.clientfx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;

public class SynchronizationService extends Thread{

	private Socket progressSocket=null;
	
	//public static Timeline sync;
	public static boolean synchronize=true;
	
	public SynchronizationService() {
		super("SYNC-SERVER");
	}
	
	@Override
	public void run() {
				
		 //Submit synchronization background task
		/*
		sync = new Timeline(new KeyFrame(Duration.seconds(1),	new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				synchronizeProgress();
			}
		}));
   
        sync.setCycleCount(Timeline.INDEFINITE);
        sync.play();
        */
		//synchronization background task
		
        while(true){
        	if(synchronize){
        		synchronizeProgress();
        	}
        
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
               
	}
	
	public void synchronizeProgress(){
		
		Protocol pt= new Protocol();
		
		pt.sinchronizeCsvProcessStatus(getSocketResponce(Protocol.GET_CSV_PROGRESS));
		pt.sinchronizeVersionNumber(getSocketResponce(Protocol.GET_VERSION_NUMBER));
	}
	
	private String getSocketResponce(String action){
		
	   BufferedReader in;
	   PrintWriter out;
	   String responce="";
	   
	   int port=Integer.parseInt(Resources.getSetting("comm.server.port"));
	   try{			   
		   progressSocket = new Socket(Resources.getSetting("comm.server.host"), port);

	       in = new BufferedReader(new InputStreamReader(progressSocket.getInputStream()));
	       out = new PrintWriter(progressSocket.getOutputStream(), true);
	       
	       out.println(action);
	       
	       responce=in.readLine();      
	       		       
	   }catch(Exception ex){
		   //ex.printStackTrace();
	   }finally{
		   try {
			   if(progressSocket!=null){
				   progressSocket.close();
			   }
		} catch (Exception e) {

		}
	  }
	   return responce;
	}

}
