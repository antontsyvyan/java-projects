package com.foursoft.gpa.clientfx.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import com.foursoft.gpa.clientfx.Dashboard;
import com.foursoft.gpa.clientfx.model.RatesModel;
import com.foursoft.gpa.db.Cdbk;
import com.foursoft.gpa.db.Rates;
import com.foursoft.gpa.jobs.ExchangeRateProcessor;
import com.foursoft.gpa.utils.DateUtils;

public class RateListController  {

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;
	    
	    @FXML // fx:id="grid"
	    private TableView<RatesModel> grid; // Value injected by FXMLLoader

	    @FXML // fx:id="currencyColumn"
	    private TableColumn<RatesModel, String> currencyNameColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="startDateColumn"
	    private TableColumn<RatesModel, String> startDateColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="periodCombo"
	    private ComboBox<String> periodCombo; // Value injected by FXMLLoader

	    @FXML // fx:id="rateColumn"
	    private TableColumn<RatesModel, String> rateColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="currencyCodeColumn"
	    private TableColumn<RatesModel, String> currencyCodeColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="countryNameColumn"
	    private TableColumn<RatesModel, String> countryNameColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="rateReverseColumn"
	    private TableColumn<RatesModel, String> reverseRateColumn; // Value injected by FXMLLoader
	    
	    @FXML // fx:id="updateButton"
	    private Button updateButton; // Value injected by FXMLLoader
	    
    
	    @FXML // fx:id="progressPane"
	    private BorderPane progressPane; // Value injected by FXMLLoader
	    
	    @FXML // fx:id="infoBox"
	    private HBox infoBox; // Value injected by FXMLLoader

	    
	    private ArrayList<TreeMap<String, String>> data;
	    private ObservableList<RatesModel> detailsData = FXCollections.observableArrayList();
	    
	    //private Rates rates = new Rates();
	    
	    private ArrayList<String> periods;
	    
	    private Label emptyTableMessage;
	    
	    private boolean done;
	    
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	    	
	        assert currencyNameColumn != null : "fx:id=\"currencyNameColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert startDateColumn != null : "fx:id=\"startDateColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert periodCombo != null : "fx:id=\"periodCombo\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert rateColumn != null : "fx:id=\"rateColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert currencyCodeColumn != null : "fx:id=\"currencyCodeColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert countryNameColumn != null : "fx:id=\"countryNameColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert reverseRateColumn != null : "fx:id=\"reverseRateColumn\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert updateButton != null : "fx:id=\"updateButton\" was not injected: check your FXML file 'RateList.fxml'.";
	        assert progressPane != null : "fx:id=\"progressPane\" was not injected: check your FXML file 'ImpDetails.fxml'.";
	        assert infoBox != null : "fx:id=\"infoBox\" was not injected: check your FXML file 'RateList.fxml'.";
	        
	        countryNameColumn.setCellValueFactory(cellData -> cellData.getValue().countryNameProperty());
	        startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
	        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty());
	        currencyCodeColumn.setCellValueFactory(cellData -> cellData.getValue().currencyCodeProperty());
	        currencyNameColumn.setCellValueFactory(cellData -> cellData.getValue().currencyNameProperty());
	        reverseRateColumn.setCellValueFactory(cellData -> cellData.getValue().reverseRateProperty());
	    
	        //periods=rates.getRatePeriods();
	        periods=getRatePeriods();
	        
	        periodCombo.setItems(FXCollections.observableArrayList(periods));
	        periodCombo.getSelectionModel().selectFirst();
	        
	        rateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	        
	        rateColumn.setOnEditCommit(
	        	    new EventHandler<CellEditEvent<RatesModel, String>>() {
	        	        @Override
	        	        public void handle(CellEditEvent<RatesModel, String> t) {
	        	            ((RatesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRate(t.getNewValue());
	        	            System.out.println("New value : "+t.getNewValue());
	        	        }
	        	    }
	        	);

	        
	        try {
				populateData(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	        periodCombo.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,	String oldValue, String newValue) {
					 try {
						infoBox.getChildren().clear();
						populateData(newValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
		    	
		    });

	    }
	    	    
	    public void populateData(String period) throws Exception{
	    	Rates rates=new Rates();
	    	String selectedPeriod=period;
	    	String firstDate;
	    	if(period==null){
	    		selectedPeriod=periods.get(periodCombo.getSelectionModel().getSelectedIndex());
	    	}
	    	this.data=rates.getRatesForPeriod(selectedPeriod,"NL");
	    	detailsData.clear();
			if(data!=null && data.size()>0){
				for (int i = 0; i < this.data.size(); i++) {
					TreeMap<String, String> tmp=data.get(i);
					String countryName="";
					String currencyName="";
					StringTokenizer st = new StringTokenizer(tmp.get(Cdbk.DB_CDBK_DESC1), "-"); 
					if(st.hasMoreTokens()){
						countryName=st.nextToken().trim();
					}
					if(st.hasMoreTokens()){
						currencyName=st.nextToken().trim();
					}
					SimpleDateFormat formatPeriod = new SimpleDateFormat(DateUtils.FORMAT_PERIOD);
					SimpleDateFormat formatFirstDay = new SimpleDateFormat(DateUtils.FORMAT_FIRST_DAY);
					firstDate=formatFirstDay.format(formatPeriod.parse(tmp.get(Rates.DB_RATES_PERIOD)));
					
					if(tmp.get(Rates.DB_RATES_START_DATE).equals(firstDate)){
						tmp.put(Rates.DB_RATES_START_DATE, "");
					}
					detailsData.add(new RatesModel(						
							countryName, 							
							tmp.get(Rates.DB_RATES_START_DATE), 
							new BigDecimal(tmp.get(Rates.DB_RATES_STD_RATE)).stripTrailingZeros().toPlainString(), 
							tmp.get(Rates.DB_RATES_CUR_CODE), 
							currencyName,							
							new BigDecimal(tmp.get(Rates.DB_RATES_REV_RATE)).stripTrailingZeros().toPlainString()
							));
				}				
			}
			grid.setItems(detailsData);
	    }
	    
	    @FXML
	    void buttonPressed(ActionEvent event) {
	    	
	    	ExchangeRateProcessor proc=new ExchangeRateProcessor();
	    	//clear grid
	    	detailsData.clear();
	    	grid.setItems(detailsData);	    	
	    	grid.setPlaceholder(new Label(""));
	    	infoBox.getChildren().clear();
	    	//disable controls
	    	updateButton.setDisable(true);
	    	periodCombo.setDisable(true);
	    	//bring busy layer to front
			progressPane.toFront();
			Task<Void> task = new Task<Void>() {
				@Override
				public void run() {					
					done=false;
					String period=periodCombo.getSelectionModel().getSelectedItem();
					try {		    					    		
			    		proc.setPeriod(period);
				    	proc.Process();
				    	if(proc.isUpdated()){			    		
							done=true;
				    	}
						
					} catch (Exception e) {
						e.printStackTrace();
						
					}
					Platform.runLater(new Runnable() {
	                     @Override 
	                     public void run() {             	 
	                    	grid.setPlaceholder(emptyTableMessage);
	                    	//Enable controls
	            	    	updateButton.setDisable(false);
	            	    	periodCombo.setDisable(false);
	                 		
	            	    	progressPane.toBack();
	            	    	
	            	    	ImageView imageview = new ImageView();
							imageview.setFitHeight(16);
							imageview.setFitWidth(16); 
							
							Label messageLable= new Label();
							messageLable.setMinWidth(180);
							Hyperlink link = new Hyperlink();
							
							if(!proc.getRatesUrl().equals("")){
								link.setText(proc.getRatesUrl());
	            	    		link.setOnAction(new EventHandler<ActionEvent>() {
	            	                @Override
	            	                public void handle(ActionEvent t) {
	            	                	try {
											java.awt.Desktop.getDesktop().browse(new URI(link.getText()));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (URISyntaxException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

	            	                }
	            	            });
							}
	            	    	
	            	    	if(done){
	            	    		imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/ok.png")); 
	            	    		messageLable.setText("Period has been updated. URL: " );        	    		

	            	    	}else{
	            	    		imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/error.png")); 
	            	    		messageLable.setText("Error updating rates.");
	            	    	}
	            	    	
	            	    	infoBox.getChildren().addAll(imageview,messageLable,link);
	            	    	try {
								populateData(period);
							} catch (Exception e) {
								e.printStackTrace();
							}
	                     }
	                 });

				}

				@Override
				protected Void call() throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
			};
			     		
			new Thread(task).start();   
	    }
	
	    private ArrayList<String> getRatePeriods(){
	    	
	    	int year = Calendar.getInstance().get(Calendar.YEAR);
	    	int month=Calendar.getInstance().get(Calendar.MONTH)+1;
	    	
	    	ArrayList<String> list = new ArrayList<String>();
	    	
	    	for(int i=year;i>=year-6;i--){
	    		for(int j=month;j>=1;j--){
	    			list.add(i+String.format("%02d", j));
	    		}
	    		month=12;
	    	}	    	
	    	return list;
	    }

}
