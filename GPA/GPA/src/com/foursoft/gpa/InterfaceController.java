package com.foursoft.gpa;

import java.util.TreeMap;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Logger;

import com.foursoft.gpa.db.Interfaces;
import com.foursoft.gpa.utils.GenericClass;

public class InterfaceController extends TimerTask {
	
	private Logger log = Logger.getLogger(this.getClass().toString());
	private String interfaceName;
	private boolean previousActive=false;
	private GenericClass gen = new GenericClass();
	private String className;
	private Vector<String> parms=new Vector<String>();
	
	private static final String METHOD="Process";

	@Override
	public void run() {
		
		//Get interface details
		Interfaces db=new Interfaces();
		TreeMap<String, String> details=db.readInterface(interfaceName);
		
		//If active start processing otherwise do nothing.
		if(details.get(Interfaces.DB_ACTIVE).equals("Y")){
			//Some log info
			if(!previousActive){
				log.info("Starting interface "+interfaceName);		
			}
			//Loading Interface processing class			
			className=details.get(Interfaces.DB_CLASS);
			gen.callGenericClassMethod(className,METHOD,parms);	
			
			previousActive=true;
		}else{		
			if(previousActive){
				log.info("Stopping interface "+interfaceName);
			}			
			previousActive=false;
		}	
		db=null;
		details.clear();
		details=null;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
}
