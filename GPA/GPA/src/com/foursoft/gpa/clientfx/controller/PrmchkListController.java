package com.foursoft.gpa.clientfx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.JComponent;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.clientfx.DraggableTab;
import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.db.Prmchk;
import com.foursoft.gpa.reporting.TotalsDetailsReport;
import com.foursoft.gpa.utils.DateUtils;

public class PrmchkListController {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="prmchkList"
    private TreeView<String> prmchkList; // Value injected by FXMLLoader

    private Prmchk prmchk=new Prmchk();	
    
	private String feedingSystem;
	
	private TreeMap<String,Boolean> itemState=new TreeMap<String,Boolean>();
	
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert prmchkList != null : "fx:id=\"prmchkList\" was not injected: check your FXML file 'PrmchkList.fxml'.";
        prmchkList.setShowRoot(false);

    }

	public void populateData(){
		ArrayList<TreeMap<String,String>> list=prmchk.getAllPeriodsList(feedingSystem);
		if(list!=null && list.size()>0){
			updateItemState(list);
			int selectedRow=-1;
			try{
				selectedRow =prmchkList.getSelectionModel().getSelectedIndex();
			}catch(Exception ex){
				
			}
			String lastYear="";
			TreeItem<String> yearItem=null;
			boolean firstTime=true;
			TreeItem<String> root = new TreeItem<String>("Totals per period");
			root.setExpanded(true);
			for(TreeMap<String,String> item: list){
				String curYear=item.get(Prmchk.DB_PRMCHK_PERIOD).substring(0, 4);
				String curMonth=item.get(Prmchk.DB_PRMCHK_PERIOD).substring(4, 6);
				boolean createItem=false;
				if(!firstTime){
					if(!lastYear.equals(curYear)){
						root.getChildren().add(yearItem);
						createItem=true;
					}				
					
				}else{
					createItem=true;					
					firstTime=false;				
				}
				
				if(createItem){
					Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream("folder_yellow.png")));
					yearItem=new TreeItem<String>(curYear,icon);
					yearItem.expandedProperty().addListener(new ChangeListener<Boolean>() {
					    @Override
					    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					        //System.out.println(curYear+" newValue = " + newValue);
					    	//Save current item state
					    	itemState.put(curYear,newValue);
					    }

					});
					//set expanded or collapsed
					if(itemState.get(curYear)!=null){
						if(itemState.get(curYear)){
							yearItem.setExpanded(true);
						}else{
							yearItem.setExpanded(false);
						}
					}else{
						yearItem.setExpanded(true);
					}
				}
				
				TreeItem<String> month = new TreeItem<String>("");
				HBox box=new HBox();
				box.setSpacing(5) ;
				Label statusLabel=getStatusLabel(item.get(Prmchk.DB_PRMCHK_DISK_CREATED));
				Label label= new Label(DateUtils.getMonthName(curMonth));
				box.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent mouseEvent) {
						if (mouseEvent.getClickCount() == 2) {
							TotalsDetailsReport tdr=new TotalsDetailsReport(item.get(Prmchk.DB_FEED_SYS_PRMCHK),item.get(Prmchk.DB_PRMCHK_PERIOD));
							tdr.generateReport();
							if(tdr.isValid()){
								SwingNode swingNode=new SwingNode();
								swingNode.setContent((JComponent) tdr.getNode());
								DraggableTab tab = new DraggableTab("Totalen "+item.get(Prmchk.DB_PRMCHK_PERIOD));
								//tab.setText("Totalen "+item.get(Prmchk.DB_PRMCHK_PERIOD));
								tab.setUserData(item.get(Prmchk.DB_PRMCHK_PERIOD));
								tab.setContent(swingNode);
								DashboardNewController.publicBodyTabPane.getTabs().add(tab);
								DashboardNewController.publicBodyTabPane.getSelectionModel().select(tab);
							}else{
								Dialogs.create()
								  .nativeTitleBar()
							      .title("Message")
							      .masthead(null)
							      .message( "There is nothing to show for period "+item.get(Prmchk.DB_PRMCHK_PERIOD))
							      .showInformation();
							}
						}
						
					}
					
				});
				
				box.getChildren().addAll(statusLabel,label);
				month.setGraphic(box);			
				yearItem.getChildren().add(month);
				
				lastYear=curYear;
			}
			
			if(yearItem!=null&& yearItem.getChildren().size()>0){
				root.getChildren().add(yearItem);
			}
			
			
		
			prmchkList.setRoot(root);
			
			if(selectedRow>=0){
				prmchkList.getSelectionModel().select(selectedRow);
			}
		}
	}
	
	private void updateItemState(ArrayList<TreeMap<String,String>> list){
		TreeMap<String,Boolean> out=new TreeMap<String,Boolean>();
		for(TreeMap<String,String> item: list){
			String year=item.get(Prmchk.DB_PRMCHK_PERIOD).substring(0,4);
			if(itemState.get(year)!=null){
				out.put(year, itemState.get(year));
			}
		}
		itemState.clear();
		itemState.putAll(out);
		
	}
	
	private Label getStatusLabel(String created){
		String text ="PROOF";
		String statusClass="proof-status";
		if(created.equals("Y")){
			 text ="FINAL";
			 statusClass="final-status";
		}
		
		
		Label label= new Label(text);
		label.setRotate(0);
		label.setOpacity(0.5);
		label.setFont(Font.font(null, FontWeight.NORMAL,8));
		label.getStyleClass().add(statusClass);
		
		return label;
	}

	public String getFeedingSystem() {
		return feedingSystem;
	}

	public void setFeedingSystem(String feedingSystem) {
		this.feedingSystem = feedingSystem;
	}
	
}
