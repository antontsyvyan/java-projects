package com.foursoft.gpa.clientfx.controller;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.foursoft.gpa.clientfx.CodeBookMainApp;
import com.foursoft.gpa.clientfx.DraggableTab;
import com.foursoft.gpa.clientfx.PrmchkEntListApp;
import com.foursoft.gpa.clientfx.PrmchkListApp;
import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.db.Cdbktable;
import com.foursoft.gpa.db.MenuLinks;
import com.foursoft.gpa.db.OpenTabs;
import com.foursoft.gpa.utils.AppMenu;
import com.foursoft.gpa.utils.Processors;

public class DashboardNewController {

	@FXML
	// ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML
	// URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML
	// fx:id="menuBar"
	private MenuBar menuBar; // Value injected by FXMLLoader

	@FXML
	// fx:id="tabWrapPane"
	public static AnchorPane tabWrapPane; // Value injected by FXMLLoader

	@FXML
	// fx:id="bodyPane"
	private TabPane bodyTabPane; // Value injected by FXMLLoader

	@FXML
	// fx:id="statusPane"
	private TabPane statusTabPane; // Value injected by FXMLLoader

	@FXML
	// fx:id="maintenanceTreeView"
	private TreeView<String> maintenanceTreeView; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="impListView"
	private ListView<TreeMap<String, String>> impListView; // Value injected by
															// FXMLLoader

	@FXML
	// fx:id="searchMenuTextField"
	private TextField searchMenuTextField; // Value injected by FXMLLoader
	
	@FXML // fx:id="progressPane"
    private Pane statusPane; // Value injected by FXMLLoader
	

    public static VBox progressContainersPool=new VBox(); // Value injected by FXMLLoader
		

	public static TabPane publicBodyTabPane;
	
	
	private OpenTabs openTabs=new OpenTabs();
	
	private MenuLinks menuLinks=new MenuLinks();

	// private Tab GpaRequestLogTab;

	// private Tab FileInterfaceLog;

	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert bodyTabPane != null : "fx:id=\"bodyTabPane\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert statusTabPane != null : "fx:id=\"statusTabPane\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert maintenanceTreeView != null : "fx:id=\"maintenanceTreeView\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert impListView != null : "fx:id=\"impListView\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert searchMenuTextField != null : "fx:id=\"searchMenuTextField\" was not injected: check your FXML file 'Dashboard-new.fxml'.";
		assert tabWrapPane != null : "fx:id=\"tabWrapPane\" was not injected: check your FXML file 'Dashboard-new.fxml'.";

		publicBodyTabPane = bodyTabPane;

		maintenanceTreeView.requestFocus();

		maintenanceTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					TreeItem<String> item = maintenanceTreeView.getSelectionModel().getSelectedItem();
					if (item.isLeaf()) {
						TreeMap<String, String> data = AppMenu.menuMap.get(item);
						Node node = null;
						String className = data.get(MenuLinks.MENU_APP_CLASS);

						if (className == null) {
							// Code book detected
							node = getCodeBook(data.get(Cdbktable.DB_CDBKTBL_LAN),data.get(Cdbktable.DB_CDBKTBL_DECL_TYPE),data.get(Cdbktable.DB_CDBKTBL_NM));
							DraggableTab tab = new DraggableTab(item.getValue());	
							tab.setContent(node);
							bodyTabPane.getTabs().add(tab);
							bodyTabPane.getSelectionModel().select(tab);
						} else {
							setTab(data);
						}

					}
				}
			}

		});

		searchMenuTextField.textProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {

						if (!newValue.trim().equals("")) {
							populateSideMenu(newValue, true);
						} else {
							if (!oldValue.trim().equals("")) {
								populateSideMenu(newValue, false);
							}
						}
					}
				});

		createRightSide();
		generateMenuBar();		
		statusPane.getChildren().add(progressContainersPool);
		loadTabs();
			
	}


	private void setTab(TreeMap<String, String> data) {
		String description = data.get(MenuLinks.MENU_TEXT_DESCRIPTION);
		String menuId = data.get(MenuLinks.MENU_ID);
		boolean newTab = true;
		if (!data.get(MenuLinks.MENU_ALLOW_MULTIPLE).equals("1")) {
			// Check if tab already exists in the pane
			ObservableList<Tab> tabList = bodyTabPane.getTabs();
			if (tabList != null && tabList.size() > 0) {
				for (Tab t : tabList) {
					if (t != null) {
						String userData = (String) t.getUserData();
						if (userData.equals(menuId)) {
							DraggableTab tab = (DraggableTab) t;
							newTab = false;
							bodyTabPane.getSelectionModel().select(tab);
							System.out.println(tab.getText());
						}
					}
				}
			}
		}

		if (newTab) {
			String className = data.get(MenuLinks.MENU_APP_CLASS);
			if (!className.equals("")) {				
				Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream(data.get(MenuLinks.MENU_ICON))));
				Node iconLoading = new ImageView(new Image(ImageLoader.class.getResourceAsStream("loading_tab.gif")));
				DraggableTab tab = new DraggableTab(description, iconLoading);
					
				if (!data.get(MenuLinks.MENU_ALLOW_MULTIPLE).equals("1")) {
					tab.setDetachable(false);
				}
				tab.setOnCloseRequest(new EventHandler<Event>(){
					@Override
					public void handle(Event event) {
						openTabs.deleteOpenTab(event.getSource().toString());
					}
						 
				});
				
				// tab.setText(description);
				tab.setUserData(data.get(MenuLinks.MENU_ID));
				
				bodyTabPane.getTabs().add(tab);
											
				Task<Void> task = new Task<Void>() {
					@Override
					public void run() {
						
						try {						
							Class<?> c = Class.forName(className);
							Class<?> params[] = {};
							//Node node = null;
							Method m = c.getDeclaredMethod("getNode", params);
							Object i = c.newInstance();
							Object node = (Node) m.invoke(i);
														
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
																		
									tab.setGraphic(null);
									tab.setGraphic(icon);
									tab.setText(description);
									tab.setContent((Node)node);
									
									if (tab != null) {
										bodyTabPane.getSelectionModel().select(tab);
									}
									//save tab id
									TreeMap<String, String> record= new TreeMap<String, String>();				
									record.put(OpenTabs.DB_TAB_ID, tab.toString());
									record.put(OpenTabs.DB_MENU_ID, data.get(MenuLinks.MENU_ID));
									record.put(OpenTabs.DB_SEQUENCE,"0");
									openTabs.insertOpenTab(record);
									
								}
								
							});
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					@Override
					protected Void call() throws Exception {
						return null;
					}
					
				};
				
				new Thread(task).start();																						
			}
		}

	}
	
	private void loadTabs(){
		ArrayList<TreeMap<String,String>> list=openTabs.getOpenTabs();
		if(list!=null && list.size()>0){
			for(TreeMap<String,String> detail:list){
				TreeMap<String, String> data =menuLinks.getMenuItem(detail.get(OpenTabs.DB_MENU_ID));
				openTabs.deleteOpenTab(detail.get(OpenTabs.DB_TAB_ID));
				setTab(data);
			}
		}
	}

	private void createRightSide() {

		Tab tab = new Tab();
		tab.setText("IMP");
		tab.setClosable(false);
		PrmchkListApp app = new PrmchkListApp(Processors.DOMPROC_FEEDING_SYSTEM);
		tab.setContent(app.getNode());
		statusTabPane.getTabs().add(tab);
		
		Tab tabEnt = new Tab();
		tabEnt.setText("ENT");
		tabEnt.setClosable(false);
		PrmchkEntListApp appEnt = new PrmchkEntListApp();
		tabEnt.setContent(appEnt.getNode());
		statusTabPane.getTabs().add(tabEnt);
		
		statusTabPane.getSelectionModel().select(tab);
	}

	public void populateSideMenu(String searchString, boolean expand) {

		TreeItem<String> root = AppMenu.getMenu(searchString, expand);
		root.setExpanded(true);

		maintenanceTreeView.setRoot(root);
	}

	public void generateMenuBar() {

		// Remove default items
		menuBar.getMenus().clear();
		AppMenu.setTopMenuBar(menuBar);
	}

	// Quit the application.
	protected void quit() {
		System.exit(0);
	}

	private Node getCodeBook(String language, String type, String codeTable) {
		CodeBookMainApp app = new CodeBookMainApp(language, type, codeTable);

		return app.getNode();
	}

}
