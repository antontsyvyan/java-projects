package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

public class MenuLinks extends ConnectionLocal {
	
	public static final String MENU_LINKS="MENU_LINKS";
	public static final String MENU="MENU";
	public static final String MENU_ID="MENU_ID";
	public static final String MENU_TEXT_DESCRIPTION="MENU_TEXT_DESCRIPTION";
	public static final String MENU_APP_CLASS="MENU_APP_CLASS";
	public static final String MENU_ICON="MENU_ICON";	
	public static final String MENU_ID_PARENT="MENU_ID_PARENT";
	public static final String MENU_ALLOW_MULTIPLE ="MENU_ALLOW_MULTIPLE";
	public static final String MENU_DISABLED ="MENU_DISABLED";
			
	public MenuLinks(){
		
	}
	

	public ArrayList<TreeMap<String, String>> getMenuItems(String parentId,String searchString){
				
		String statement = "select "+MENU_LINKS+"."+
		MENU_ID+", "+
		MENU_ID_PARENT+", "+
		MENU_TEXT_DESCRIPTION+","+
		MENU_APP_CLASS+", "+
		MENU_ICON+", "+
		MENU_DISABLED+", "+
		MENU_ALLOW_MULTIPLE+
		" from menu_links" + 
		" join menu_text on menu_links.menu_id=menu_text.menu_id and menu_text_language='EN'" +
		" join menu on menu.menu_id=menu_links.menu_id" +
		" where menu_id_parent='"+parentId+"'"+
		" and menu_text_description like '%"+searchString+"%' "+
		" order by menu_links.menu_links_seq";
			
		return readTableMultiple(statement);		
	}
	
	
	public TreeMap<String, String> getMenuItem(String menuId){
		
		String statement = "select "+MENU+"."+MENU_ID+", "+				
				MENU_TEXT_DESCRIPTION+","+
				MENU_APP_CLASS+", "+
				MENU_ICON+", "+
				MENU_DISABLED+", "+
				MENU_ALLOW_MULTIPLE+
				" from " + MENU+
				" join menu_text on "+MENU+"."+MENU_ID+"=menu_text.menu_id and menu_text_language='EN'" +
				" WHERE "+ MENU+"."+MENU_ID+"='"+menuId+"'";

		
		return readTable(statement);
	}

}
