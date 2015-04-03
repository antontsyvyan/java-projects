package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

public abstract class XmlDataProcessor {

	public Logger log = Logger.getLogger(this.getClass().toString());
	protected ArrayList<TreeMap<String, String>> records;
	protected String fileName;
	
	private boolean locked=false;
		
	public abstract void Process();
	
	public void setRecord(ArrayList<TreeMap<String, String>> records) {
		this.records = records;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	
	
	
}
