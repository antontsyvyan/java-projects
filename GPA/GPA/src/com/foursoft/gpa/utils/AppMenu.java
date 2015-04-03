package com.foursoft.gpa.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.db.Cdbksec;
import com.foursoft.gpa.db.Cdbktable;
import com.foursoft.gpa.db.MenuLinks;

public class AppMenu {
	
	public static final String ITEM_SEPARATOR = "ITEM_SEPARATOR";	
	public static final String ABOUT_CLIENT="ABOUT_CLIENT";
	public static final String ABOUT_SERVER="ABOUT_SERVER";
	
	public static final String METHOD="start";
	
	private static MenuLinks menuLinks=new MenuLinks();
	
	//public static HashMap <String,TreeMap<String, String>>menuMap;
	
	public static HashMap <TreeItem<String>,TreeMap<String, String>> menuMap;
	
	public static TreeItem<String> getMenu(String searchString,boolean expand){
		TreeItem<String> items=null;
		menuMap= new HashMap <TreeItem<String>,TreeMap<String, String>>();
		//Create root
		ArrayList<TreeMap<String, String>> records= menuLinks.getMenuItems("","");
		if(records!=null & records.size()>0 ){
			TreeMap<String, String> tmp=records.get(0);
			TreeItem<String> root = new TreeItem<String>(tmp.get(MenuLinks.MENU_TEXT_DESCRIPTION));
			String meniId=tmp.get(MenuLinks.MENU_ID);
			
			items=getTreeItems(root,meniId,searchString,expand);
			
			if(expand){
				items.setExpanded(true);
			}
		}				
		return items;
		
	}
	
	private static TreeItem<String> getTreeItems(TreeItem<String>parent, String parentId,String searchString,boolean expand){
		
		ArrayList<TreeMap<String, String>> list= menuLinks.getMenuItems(parentId,"");		
		if(list!=null & list.size()>0 ){
			
			for(TreeMap<String, String> item:list){
				String menuId=item.get(MenuLinks.MENU_ID);
				String description=item.get(MenuLinks.MENU_TEXT_DESCRIPTION);				
				Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream(item.get(MenuLinks.MENU_ICON))));
				TreeItem<String> menuItem = new TreeItem<String>(description,icon);
				menuItem.setExpanded(expand);
				if(menuId.equals("CODEBOOK")){
					parent.getChildren().add(getCodebookMenu(menuItem,searchString, expand));
				}else{				
					ArrayList<TreeMap<String, String>> list2= menuLinks.getMenuItems(menuId,"");
					if(list2!=null & list2.size()>0 ){
						parent.getChildren().add(getTreeItems(menuItem,menuId,searchString,expand));
					}else{
						//if has no children
						if(!searchString.equals("")){
							if(menuId.toUpperCase().contains(searchString.toUpperCase())){							
								parent.getChildren().add(menuItem);
								menuMap.put(menuItem, item);
							}
						}else{
							parent.getChildren().add(menuItem);
							menuMap.put(menuItem, item);
						}
					}
					
				}				
			}			
		}
		
		return parent;
		
	}
	
	private static TreeItem<String> getCodebookMenu(TreeItem<String>parent,String searchString,boolean expand){
		
		Cdbksec cdbksec = new Cdbksec();
		Cdbktable cdbktbl = new Cdbktable();

		ArrayList<TreeMap<String, String>> sections = cdbksec.getAllRecords(Processors.NL);
		if(menuMap==null){
			menuMap= new HashMap <TreeItem<String>,TreeMap<String, String>>();
		}

		for (int i = 0; i < sections.size(); i++) {
			TreeMap<String, String> det = sections.get(i);			
			ArrayList<TreeMap<String, String>> tables = cdbktbl.getAllRecords(Processors.NL, det.get(Cdbksec.DB_CDBKSEC_DECL_TYPE));
			if (tables != null && tables.size() > 0) {
				boolean foundItem = false;
				TreeItem<String> item = new TreeItem<String>(det.get(Cdbksec.DB_CDBKSEC_DESC));
				item.setExpanded(expand);
				for (int j = 0; j < tables.size(); j++) {
					boolean foundSubItem = false;
					TreeMap<String, String> sub = tables.get(j);
					String itemName = sub.get(Cdbktable.DB_CDBKTBL_NM) + " "+ sub.get(Cdbktable.DB_CDBKTBL_DESC);
					if(searchString.equals("")){
						foundSubItem = true;
						foundItem=true;
					}else{
						if(itemName.toUpperCase().contains(searchString.toUpperCase())){
							foundSubItem = true;	
							foundItem=true;
						}
					}
					if(foundSubItem){
						TreeItem<String> subItem = new TreeItem<String>(itemName);
						subItem.setExpanded(expand);
						item.getChildren().add(subItem);
						menuMap.put(subItem,sub);
					}
									
				}
				
				if(foundItem){
					parent.getChildren().add(item);
				}
				
			}
			
		}		
		return parent;
	}
	
	public static void setTopMenuBar(MenuBar menuBar){		
		//get top level options
		ArrayList<TreeMap<String, String>> records= menuLinks.getMenuItems("TOP_MENU","");
		if(records!=null & records.size()>0 ){
			for(TreeMap<String, String> item:records){	
				
				menuBar.getMenus().add(getMenuTree(item));
			}
		}
		
	}
	
	private static Menu getMenuTree(TreeMap<String, String> menuItem){		
		//Menu menu= getMenuItem(menuItem);
		Menu menu=new Menu(menuItem.get(MenuLinks.MENU_TEXT_DESCRIPTION));
		ArrayList<TreeMap<String, String>> records= menuLinks.getMenuItems(menuItem.get(MenuLinks.MENU_ID),"");					
		if(records!=null & records.size()>0 ){
			for(TreeMap<String, String> item:records){
				String menuId=item.get(MenuLinks.MENU_ID);
				ArrayList<TreeMap<String, String>> records2= menuLinks.getMenuItems(menuId,"");
				if(records2!=null & records2.size()>0 ){
					//Menu subMenu=getMenuTree(item);
					menu.getItems().add(getMenuTree(item));
				}else{
					menu.getItems().add(getMenuItem(item));
				}
				
			}
		}

		return menu;
	}
		
	private static MenuItem getMenuItem(TreeMap<String, String> item){
		MenuItem menu= new MenuItem(item.get(MenuLinks.MENU_TEXT_DESCRIPTION));
		menu.setUserData(item);
		String itemId=item.get(MenuLinks.MENU_ID);
			
		if (itemId.startsWith(ITEM_SEPARATOR)) {
			return new SeparatorMenuItem();
		}
		
		if (itemId.equals(ABOUT_CLIENT)) {
			menu.setText("Client version: "+FileUtils.getAboutItem());	
		}
		
		if (itemId.equals(ABOUT_SERVER)) {			
			//menu=new Menu("");
			//update server version in the loop
			Timeline timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(3),new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					menu.setText("Server version: "+Protocol.getGlobalServerVersion());
				}
			}));
			timer.setCycleCount(Timeline.INDEFINITE);
	        timer.play();
		}
		
		Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream(item.get(MenuLinks.MENU_ICON))));
		menu.setGraphic(icon);
		
		String className=item.get(MenuLinks.MENU_APP_CLASS).trim();
		
		if(!className.equals("")){
			//add event handling
			menu.setOnAction(new EventHandler<ActionEvent>() {
				@SuppressWarnings("unchecked")
				public void handle(ActionEvent event) {

					MenuItem item = (MenuItem) event.getSource();
					TreeMap<String, String> data=(TreeMap<String, String>) item.getUserData();
					System.out.println(data.get(MenuLinks.MENU_TEXT_DESCRIPTION));
					try {
						Class<?> c=Class.forName(className);
						Class<?> params[] = {};						
						Method m = c.getDeclaredMethod(METHOD, params);
						Object i = c.newInstance();
						m.invoke(i);							        							        			          	            
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		}
			
		if(item.get(MenuLinks.MENU_DISABLED).equals("1")){
			menu.setDisable(true);
		}
					
		return menu;

	}

	
}
