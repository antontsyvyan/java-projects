package com.foursoft.gpa.clientfx.controller;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import javax.swing.JComponent;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.clientfx.DraggableTab;
import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.clientfx.model.ImpDetailsModel;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.reporting.ImpDetailsReport;
import com.foursoft.gpa.utils.Processors;

public class ImpDetailsController {

	@FXML // fx:id="ref1Column"
	private TableColumn<ImpDetailsModel, String> ref1Column; // Value injected by FXMLLoader
    
	@FXML // fx:id="ref2Column"
    private TableColumn<ImpDetailsModel, String> ref2Column; // Value injected by FXMLLoader

    @FXML // fx:id="ref3Column"
    private TableColumn<ImpDetailsModel, String> ref3Column; // Value injected by FXMLLoader
    
    @FXML // fx:id="ref4Column"
    private TableColumn<ImpDetailsModel, String> ref4Column; // Value injected by FXMLLoader
    
    @FXML // fx:id="invoiceColumn"
    private TableColumn<ImpDetailsModel, String> invoiceColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="a3Column"
    private TableColumn<ImpDetailsModel, String> a3Column; // Value injected by FXMLLoader

    @FXML // fx:id="articleColumn"
	private TableColumn<ImpDetailsModel, String> articleColumn; // Value injected by FXMLLoader

    @FXML // fx:id="grid"
    private TableView<ImpDetailsModel> grid; // Value injected by FXMLLoader

    @FXML // fx:id="periodColumn"
    private TableColumn<ImpDetailsModel, String> periodColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="custValueColumn"
    private TableColumn<ImpDetailsModel, String> custValueColumn; // Value injected by FXMLLoader

    @FXML // fx:id="ref1Search"
    private TextField ref1Search; // Value injected by FXMLLoader
    
    @FXML // fx:id="refCombo"
    private ComboBox<String> refCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="conditionCombo"
    private ComboBox<String> conditionCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="searchBox"
    private HBox searchBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="progressPane"
    private BorderPane progressPane; // Value injected by FXMLLoader
    
    private ArrayList<TreeMap<String, String>> data;
    private ObservableList<ImpDetailsModel> impDetailsData = FXCollections.observableArrayList();
    
	private static String[] refNames=new String[]{"Int. Order nr.","Order nr.","Stock nr.","Project nr.","Invoice nr.", "A3 field"};
	private static String[] refCodes=new String[]{Femutu.DB_FEMUTU_CUST_REF1,Femutu.DB_FEMUTU_CUST_REF2,Femutu.DB_FEMUTU_CUST_REF3,Femutu.DB_FEMUTU_CUST_REF4,Femutu.DB_FEMUTU_INV_PROF, Femutu.DB_FEMUTU_A3};
	
	private static String[] conditionNames=new String[]{Processors.COND_BEGINS_WITH,Processors.COND_EQUALS,Processors.COND_CONTAINS};
	private static String[] conditionCodes=new String[]{Processors.COND_BEGINS_WITH,Processors.COND_EQUALS,Processors.COND_CONTAINS};
	
	private int previousSelectedConditionsIndex=0;
	private boolean a3Selected=false;
	
	private Label emptyTableMessage;
	 
	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		
		assert ref2Column != null : "fx:id=\"ref2Column\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert ref4Column != null : "fx:id=\"ref4Column\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert ref1Column != null : "fx:id=\"ref1Column\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert grid != null : "fx:id=\"grid\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert periodColumn != null : "fx:id=\"periodColumn\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert ref1Search != null : "fx:id=\"ref1Search\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert a3Column != null : "fx:id=\"a3Column\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert ref3Column != null : "fx:id=\"ref3Column\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert articleColumn != null : "fx:id=\"articleColumn\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert refCombo != null : "fx:id=\"refCombo\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert conditionCombo != null : "fx:id=\"conditionCombo\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert progressPane != null : "fx:id=\"progressPane\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert invoiceColumn != null : "fx:id=\"invoiceColumn\" was not injected: check your FXML file 'ImpDetails.fxml'.";
        assert custValueColumn != null : "fx:id=\"custValueColumn\" was not injected: check your FXML file 'ImpDetails.fxml'.";

        a3Column.setCellValueFactory(cellData -> cellData.getValue().a3Property());
        periodColumn.setCellValueFactory(cellData -> cellData.getValue().periodProperty());
        articleColumn.setCellValueFactory(cellData -> cellData.getValue().articleProperty());
        ref1Column.setCellValueFactory(cellData -> cellData.getValue().ref1Property());
        ref2Column.setCellValueFactory(cellData -> cellData.getValue().ref2Property());
        ref3Column.setCellValueFactory(cellData -> cellData.getValue().ref3Property());
        ref4Column.setCellValueFactory(cellData -> cellData.getValue().ref4Property());
        invoiceColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceProperty());
        custValueColumn.setCellValueFactory(cellData -> cellData.getValue().custValueProperty());
        
        //set texts
        
        ref1Column.setText(refNames[0]);
        ref2Column.setText(refNames[1]);
        ref3Column.setText(refNames[2]);
        ref4Column.setText(refNames[3]);
        invoiceColumn.setText(refNames[4]);
        
        /*
        custValueColumn.setCellFactory(new Callback<TableColumn<ImpDetailsModel, String>, TableCell<ImpDetailsModel, String>>() {
            public TableCell<ImpDetailsModel, String> call(TableColumn<ImpDetailsModel, String> p) {
            	 TableCell<ImpDetailsModel, String> cell = new TableCell<ImpDetailsModel, String>() {
                    
            	 };

                cell.setStyle("-fx-alignment: top-right;");
                return cell;
            }
        });
        */
      
        searchButton.setDisable(true);
        
        //Populate combo boxes  
        refCombo.setItems(FXCollections.observableArrayList(refNames));
        refCombo.getSelectionModel().selectFirst();
        
        conditionCombo.setItems(FXCollections.observableArrayList(conditionNames));
        conditionCombo.getSelectionModel().selectFirst();
               
        emptyTableMessage=(Label)grid.getPlaceholder();
        refCombo.getSelectionModel().selectedIndexProperty().addListener(
    			new ChangeListener<Number>() {
    				@Override
    				public void changed(ObservableValue<? extends Number> observable,Number oldValue, Number newValue) {
    					conditionCombo.setDisable(false);
    					if(refCodes[refCombo.getSelectionModel().getSelectedIndex()].equals(Femutu.DB_FEMUTU_A3)){
    						//save current index
    						previousSelectedConditionsIndex=conditionCombo.getSelectionModel().getSelectedIndex();
    						//for A3 selection only EQUALS allowed
    						conditionCombo.getSelectionModel().select(1);
    						conditionCombo.setDisable(true);
    						a3Selected=true;
    					}else{
    						if(a3Selected){
    							conditionCombo.getSelectionModel().select(previousSelectedConditionsIndex);
    						}
    						
    						a3Selected=false;
    					}
    										
    				}
    			}
    		);
        
        a3Column.setCellFactory(new Callback<TableColumn<ImpDetailsModel, String>, TableCell<ImpDetailsModel, String>>() {
			@Override
			public TableCell<ImpDetailsModel, String> call(	TableColumn<ImpDetailsModel, String> arg0) {
				TableCell<ImpDetailsModel, String> cell = new TableCell<ImpDetailsModel, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						Hyperlink link = new Hyperlink();
					    link.setText(item);
					    link.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {

															
								Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream("loading_tab.gif")));
								String labelText="Details "+item;
								DraggableTab tab = new DraggableTab(labelText,icon);
								ImpDetailsReport idr= new ImpDetailsReport(item);
								
								Task<Void> task = new Task<Void>() {
									
									@Override
									public void run() {	
										
										if(idr.generateReport()){
											Platform.runLater(new Runnable() {												
												@Override
												public void run() {
													SwingNode swingNode=new SwingNode();
													swingNode.setContent((JComponent) idr.getNode());
													tab.setContent(swingNode);
													tab.setGraphic(null);
													tab.setText(labelText);	
													tab.setUserData(item);
													DashboardNewController.publicBodyTabPane.getTabs().add(tab);													
													DashboardNewController.publicBodyTabPane.getSelectionModel().select(tab);
													
												}
												
											});
							        	}else{
							        		Dialogs.create()
											  .nativeTitleBar()
										      .title("Message")
										      .masthead(null)
										      .message( "There is nothing to show for period "+item)
										      .showInformation();
							        	}									
									}

									@Override
									protected Void call() throws Exception {
										// TODO Auto-generated method stub
										return null;
									}
								};
								
								new Thread(task).start();
															
							}
					    	
					    });
					    
					    setGraphic(link);
					}
				};
				return cell;
			}
        	
        });
     
        ref1Search.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
		    	changeSerchButtonState(newValue);
		    }
		});
               
	}
	
    @FXML
    void buttonPressed(ActionEvent event) {
    	//Disable search field and button                        
		ref1Search.setDisable(true);
		searchButton.setDisable(true);
		
		grid.setPlaceholder(new Label(""));
		//bring busy layer to front
		progressPane.toFront();
		      	
		
		Task<Void> task = new Task<Void>() {
			@Override
			public void run() {
				try {
					populateData();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Platform.runLater(new Runnable() {
                     @Override public void run() {
                    	 
                    	grid.setPlaceholder(emptyTableMessage);
                    	//Enable search field and button  
                 		ref1Search.setDisable(false);
                 		searchButton.setDisable(false);
                 		progressPane.toBack();
                     }
                 });

			}

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		ProgressBar bar = new ProgressBar();
		bar.progressProperty().bind(task.progressProperty());       		
		new Thread(task).start();   

    }
	
	public void populateData() throws Exception{
		Femutu femutu= new Femutu();
				
		String search=ref1Search.getText();
		String selectedReference=refCodes[refCombo.getSelectionModel().getSelectedIndex()];
	    String selectedCondition=conditionCodes[conditionCombo.getSelectionModel().getSelectedIndex()];
	    
    
		if(!search.trim().equals("")){		
			this.data=femutu.readRecordsByRef(selectedReference,selectedCondition,search);
			impDetailsData.clear();
			if(data!=null && data.size()>0){
				for (int i = 0; i < this.data.size(); i++) {
					TreeMap<String, String> tmp=data.get(i);
					impDetailsData.add(new ImpDetailsModel(
							tmp.get(Femutu.DB_FEMUTU_A3),
							tmp.get(Femutu.DB_FEMUTU_PERIOD),
							tmp.get(Femutu.DB_FEMUTU_ART_NO),
							tmp.get(Femutu.DB_FEMUTU_CUST_REF1),
							tmp.get(Femutu.DB_FEMUTU_CUST_REF2),
							tmp.get(Femutu.DB_FEMUTU_CUST_REF3),
							tmp.get(Femutu.DB_FEMUTU_CUST_REF4),
							tmp.get(Femutu.DB_FEMUTU_INV_PROF),
							tmp.get(Femutu.DB_FEMUTU_CUST_VAL_EUR)
							));
				}
			}
			grid.setItems(impDetailsData);	
			
		}	
	}
	
	private void changeSerchButtonState(String value){
		if(value.trim().length()>=3){
			searchButton.setDisable(false);
    	}else{
    		searchButton.setDisable(true);
    	}
	}

}
