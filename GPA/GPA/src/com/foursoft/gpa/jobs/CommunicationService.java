package com.foursoft.gpa.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.foursoft.gpa.utils.FileUtils;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;


public class CommunicationService extends Thread {
	
	private ServerSocket listener;
	private boolean run=true;
	
	public CommunicationService() {
		super("COMM-SERVER");
	}
	
	@Override
	public void run() {
	
		try{
			int port=Integer.parseInt(Resources.getSetting("comm.server.port"));
			System.out.println("\r\nStarting communication server on port "+port);
			listener = new ServerSocket(port);
	        try {
	            while (run) {
	                new SocketProcessor(listener.accept()).start();
	            }
	        }catch(Exception ex){
	        	
	        } finally {
	        	if(listener!=null){
	        		listener.close();
	        	}
	        }
		}catch(Exception ex){
			System.out.println("\r\nCouldn't start communication server");
			ex.printStackTrace();
			
		}


	}
	
	
	public void end(){
		System.out.println("\r\nStopping communication server");
		run=false;
		if(listener!=null){
			try{
				listener.close();
			}catch(Exception ex){
				
			}
		}
	}
		
	private static class SocketProcessor extends Thread {
        private Socket socket;
        
        public SocketProcessor(Socket socket){
        	this.socket=socket;
        }
    	@Override
		public void run() {

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(),	true);

				while (true) {
					String input = in.readLine();
					//if (input == null || input.equals(".")) {											
					//}
					//System.out.println(input);
					
					if(input.startsWith(Protocol.GET_PROGRESS)){
						sendProgress(out);
						break;
					}
					
					if(input.startsWith(Protocol.GET_CSV_PROGRESS)){
						sendCsvProgress(out);
						break;
					}
					
					if(input.startsWith(Protocol.GET_VERSION_NUMBER)){
						sendVersionNumber(out);
						break;
					}		
					
				}

			} catch (IOException e) {
				System.out.println("Error handling socket client");

				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}

		}
    	
    	private void sendProgress(PrintWriter out){
    			
    		out.println(Protocol.getProgressMessage());
    		
    	}
    	
    	private void sendCsvProgress(PrintWriter out){
			
    		out.println(Protocol.getCsvProgressMessage());
    		
    	}
    	
    	private void sendVersionNumber(PrintWriter out){
			
    		out.println(FileUtils.getAboutItem());
    		
    	}
	}


}
