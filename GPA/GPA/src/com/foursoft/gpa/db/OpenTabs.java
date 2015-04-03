package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class OpenTabs extends ConnectionLocal {
	
	public static final String DB_OPEN_TABS="OPEN_TABS";
	public static final String DB_TAB_ID="TAB_ID";
	public static final String DB_MENU_ID="MENU_ID";
	public static final String DB_SEQUENCE="SEQUENCE";

	public String getSelectPart(){ 
		
		 String selectPart="select "+
					DB_TAB_ID+", "+
					DB_MENU_ID+", "+
					DB_SEQUENCE+
					" FROM "+DB_OPEN_TABS;
		 
		return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> getOpenTabs(){
		String statement = getSelectPart();
		statement+=" ORDER BY "+DB_SEQUENCE;
		
		return readTableMultiple(statement);
	}

	
	
	public boolean insertOpenTab(TreeMap<String, String> record){

		String statement="INSERT INTO "+DB_OPEN_TABS+ " VALUES(";
		statement+="'"+record.get(DB_TAB_ID)+"', " ;
		statement+="'"+record.get(DB_MENU_ID)+"', " ;
		statement+=record.get(DB_SEQUENCE)+")" ;
	
		return insertUpdateTable(statement);
	}
	
	
	public boolean deleteOpenTab(String tabId) {
		String statement = "DELETE FROM " + DB_OPEN_TABS + " WHERE  "
				+ DB_TAB_ID + "='" + tabId + "'";

		return insertUpdateTable(statement);
	}
}
