package com.foursoft.gpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;
import java.util.logging.LogManager;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import com.foursoft.gpa.db.Interfaces;
import com.foursoft.gpa.jobs.CommunicationService;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.cache.MemoryCache;

public class Main implements Daemon{
	
    public static final String START_COMMAND = "start";
    public static final String STOP_COMMAND = "stop";
    
    private static Main serviceInstance = new Main();
    
    /**
     * Flag to know if this service
     * instance has been stopped.
     */
    public static boolean stopped = false;

	/**
	 * @param args
	 */
	
	private final static long defaultDelay = 5000;
	
	private final static long cleanCacheIntervalSec=20; 
	
	private static final String CACHE="MEMORY_CACHE"; 
	
	private Vector<Timer> timers=new Vector<Timer>();
	
	private CommunicationService commService;
	
	public static MemoryCache<Object, Object> cacheInstance = MemoryCache.getMemoryCacheSingletonInstance();
	
	public static void main(String[] args) {
	
		if(args!=null && args.length>0){
			Resources.setPropertiesFile(args[0]);
		}
		
		serviceInstance.initialize();
	}
	
	private void initialize(){
		
		setLogger();
		
		Interfaces db = new Interfaces();
		ArrayList<TreeMap<String, String>> interf = null;
		
		//Try to read interfaces
		while (interf==null || interf.size()==0){
			
			if(stopped){
				//exit when service stopped
				break;
			}
			
			interf = db.readAllInterfaces();
			if(interf==null || interf.size()==0){
				//sleep before next attempt
				delay(30);
			}
		}
		
		if (interf != null && interf.size() > 0) {
			
			//Start communication server
			commService=new CommunicationService();
			commService.start();
			
			//Submit active interfaces
			for (int i = 0; i < interf.size(); i++) {
				
				String name = getField(interf, i, Interfaces.DB_NAME);
				long delay = getDelay(getField(interf, i, Interfaces.DB_DELAY_SEC));

				Timer cTimer = new Timer(name);
				InterfaceController ic = new InterfaceController();
				ic.setInterfaceName(name);
				cTimer.schedule(ic, delay, delay);
				timers.add(cTimer);
			}
		}
		
		//prepare and init cache	
		Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(cleanCacheIntervalSec * 1000);
                    } catch (InterruptedException ex) {
                    }
                    cacheInstance.cleanup();
                }
            }
        });
		t.setName(CACHE);
        t.setDaemon(true);
        t.start();

		
	}
	
	public static void windowsService(String args[]) {
		String cmd = START_COMMAND;
		if (args.length > 0) {
			cmd = args[0];
			if(args.length >1){	
				Resources.setPropertiesFile(args[1]);
			}
		}

		if (START_COMMAND.equals(cmd)) {
			serviceInstance.start();
		} else {
			serviceInstance.stop();
		}
	}
	  
	public void start() {

		stopped = false;
		initialize();
	}
	  
	/**
	 * Stop this service instance
	 */
	public void stop() {
		stopped = true;
		synchronized (this) {
			// this.notify();
			Iterator<Timer> itr = timers.iterator();
			while (itr.hasNext()) {
				Timer t = (Timer) itr.next();
				t.cancel();
			}
		}
		//Start communication server
		commService.end();		
	}
	
	private static long getDelay(String value){
		long delay = defaultDelay;
		
		try{
			delay=Long.parseLong(value);
			//Calculate delay in milliseconds;
			delay=delay*1000;
		}catch(Exception ex){
			
		}
		
		return delay;
	}
		
	
	private static String getField(ArrayList<TreeMap<String, String>> records, int recordNo, String fieldName){       
        return (String)records.get(recordNo).get(fieldName);
    }
	
	private void setLogger() {
		// Configure the logging
		File propertyFile=null;
		try {
			File currentFile = new File(".");
			propertyFile= new File(currentFile.getCanonicalPath()+"\\conf\\", "Logging.properties");
			LogManager.getLogManager().readConfiguration(new FileInputStream(propertyFile.getPath()));
			checkLogFolder(currentFile.getCanonicalPath()+"\\"+LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern"));
		} catch (IOException e) {
			System.out.println(propertyFile.getPath());
			System.err.println("Could not setup logger configuration: "	+ e.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	private void checkLogFolder(String path){
		File temp = new File(path);
		String filePath = temp.getAbsolutePath().substring(0,temp.getAbsolutePath().lastIndexOf(File.separator));
		
		temp = new File(filePath);
		if (temp.isDirectory() == false) {
            temp.mkdir();
        }
		
	}
	
	private synchronized void delay(int seconds) {
		try {
			wait(seconds * 1000);// set delay in seconds
		} catch (InterruptedException e) {
			// log.error(e);
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		// TODO Auto-generated method stub
		
	}

}
