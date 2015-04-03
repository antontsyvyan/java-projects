package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.TreeMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlHandler extends DefaultHandler {
	
	public static final String ROOT_TAG="message";
	
	private ArrayList<TreeMap<String,String>> records=new ArrayList<>();
	private TreeMap<String,String> record=null;
	private String startTag;
	
	private int numberOfRecords=0;
	//Root element flag(message)
	boolean startRootTag=false;
	//Record element flag(etc feminb, femart)
	boolean startRecord=false;	
	boolean addFlag=false;
	
	private String currentTag="";
	
	@Override
	public void startElement(String uri, String localName, String tag,	Attributes attributes) throws SAXException {

		if (startRootTag) {
			this.startTag=tag;
			startRootTag=false;
		}
		
		if (tag.equalsIgnoreCase(ROOT_TAG)) {
			startRootTag = true;
		}

		if (tag.equalsIgnoreCase(startTag)) {
			record = new TreeMap<String, String>();
			startRecord = true;
			numberOfRecords++;
		}
		
		if(startRecord && !currentTag.equalsIgnoreCase(startTag)){
			addFlag=true;
		}
		
		this.currentTag = tag;

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if(addFlag){
			String value=new String(ch, start, length);
			record.put(currentTag.toUpperCase(),value.trim());
		}

	}
	
	@Override
	public void endElement(String uri, String localName, String tag)throws SAXException {

		if (tag.equalsIgnoreCase(ROOT_TAG)) {
			 startRootTag=false;
		}
		
		if (tag.equalsIgnoreCase(startTag)) {
			 records.add(record);
			 startRecord=false;		
		}
		
		addFlag=false;
	}
	

	public ArrayList<TreeMap<String, String>> getRecords() {
		return records;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getStartTag() {
		return startTag;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	

	

}
