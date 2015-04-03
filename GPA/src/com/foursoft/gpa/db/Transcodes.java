package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class Transcodes extends ConnectionDB {
	
	public static final String DB_TRANSCODES = "transcodes";
	public static final String DB_TRANS_CODE = "TRANS_CODE";
	public static final String DB_TRANS_BLCKS = "TRANS_BLCKS";
	
	private TreeMap<String, List<String>> transBlocks;
	
	
	public static final String DEFAULT_BLOCK_SEPARATOR=",";
	public String getSelectPart(){ 
	 String selectPart="SELECT "+DB_TRANS_CODE +
	"," +DB_TRANS_BLCKS +
	" FROM "+getDbName()+DB_TRANSCODES;
	return selectPart;
	}

	
	public TreeMap<String, String> getBlocks(String code) {
	
		String statement=getSelectPart()+ " WHERE " + DB_TRANS_CODE+"='"+ code+"'";
		TreeMap<String, String> record = readTable(statement);
		
		return record;
	}
	
	public String[] getBlocksArray(String code){
		
		String[] blockArray=null;
		TreeMap<String, String> record=getBlocks(code);
		Vector<String> v =new Vector<String>();
		if(record!=null && record.size()>0){
			String blockString=record.get(DB_TRANS_BLCKS);
			if(blockString.contains(DEFAULT_BLOCK_SEPARATOR)){
				StringTokenizer st = new StringTokenizer(blockString,DEFAULT_BLOCK_SEPARATOR);
				while (st.hasMoreTokens()) {
					v.add(st.nextToken());
			     }
			}else{
				v.add(blockString);
			}
		}
		
		if(v!=null && v.size()>0){
			blockArray= new String[v.size()];
			v.toArray(blockArray);
		}
		return blockArray;
	}
	
	
	public List<String>getTransBlocksList(String code){
		return transBlocks.get(code);
	}
	
	/*
	public List<String> getBlocksList(String code) {
			
		List<String> blockArray = new ArrayList<String>();
		TreeMap<String, String> record = getBlocks(code);
		if (record != null && record.size() > 0) {
			String blockString = record.get(DB_TRANS_BLCKS);
			if (blockString.contains(DEFAULT_BLOCK_SEPARATOR)) {
				StringTokenizer st = new StringTokenizer(blockString, DEFAULT_BLOCK_SEPARATOR);
				while (st.hasMoreTokens()) {
					blockArray.add(st.nextToken().trim());
				}
			} else {
				blockArray.add(blockString);
			}
		}

		return blockArray;
	}
	*/
	
	public void loadTranscodesTable(){
		
		String statement=getSelectPart();
		
		transBlocks=new TreeMap<String, List<String>>();
		
		ArrayList<TreeMap<String, String>> record= readTableMultiple(statement);
		for (int i = 0; i < record.size(); i++) {
			TreeMap<String, String> det=record.get(i); 
			transBlocks.put(det.get(DB_TRANS_CODE),createList(det.get(DB_TRANS_BLCKS)));
		}

	}
	
	
	private List<String>createList(String blockString){
		List<String> blocks=new ArrayList<String>();
		if (blockString.contains(DEFAULT_BLOCK_SEPARATOR)) {
			StringTokenizer st = new StringTokenizer(blockString, DEFAULT_BLOCK_SEPARATOR);
			while (st.hasMoreTokens()) {
				blocks.add(st.nextToken().trim());
			}
		} else {
			blocks.add(blockString);
		}
		return blocks;
	}
		
	
	public String getProcessCode(String processCodeIn){
		
		String processCodeOut=processCodeIn;
		try{
			processCodeOut=String.format("%06d", Integer.parseInt(processCodeIn));
			
			if(processCodeOut.length()==6){						
				String firstTwo=processCodeOut.substring(0,2);
				if(firstTwo.equals("10") || firstTwo.equals("11")){
					//replace first two numbers of processcode by 01	
					processCodeOut="01"+processCodeOut.substring(2);
				}
			}
			
		}catch(Exception e){
			
		}
			
		return processCodeOut;
	}
	
	public Transcodes() {
		// TODO Auto-generated constructor stub
	}

}
