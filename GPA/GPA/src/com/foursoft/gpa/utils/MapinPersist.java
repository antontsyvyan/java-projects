package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;

import com.foursoft.gpa.db.Mapin;


public class MapinPersist {

	private ArrayList<TreeMap<String, String>> mappingDetails;
	
	private TreeMap<String, ArrayList<TreeMap<String, String>>> blocks;
	
	private Mapin mapin=new Mapin();

	public MapinPersist(String id) {
	
		mappingDetails=mapin.getMappingDetails(id);
		blocks=new TreeMap<String, ArrayList<TreeMap<String, String>>>();
		
	}

	public ArrayList<TreeMap<String, String>> getMappingDetails() {
		return mappingDetails;
	}
	
	public ArrayList<TreeMap<String, String>> getMappingDetails(List<String> blocksList) {
		
		ArrayList<TreeMap<String, String>> records = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> data = new TreeMap<String, String>();
		String blocksString=blocksList.toString();
		
		if(blocks.get(blocksString)!=null){
			return blocks.get(blocksString);
		}
		
		if(mappingDetails!=null && mappingDetails.size()>0){
			for(int i=0;i<mappingDetails.size();i++){
				data=mappingDetails.get(i);
				if (blocksList.contains(data.get(Mapin.DB_MAPIN_BLCK))){
					records.add(data);
				}				
			}
			blocks.put(blocksString, records);
		}
			
		return records;
	}
		
}
