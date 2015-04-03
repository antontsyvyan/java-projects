package com.foursoft.gpa.clientfx.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.utils.Processors;

public class RequestController {
	
	
	public static final String[] FEED_SYSTEMS=new String[]{Processors.DEFAULT_FEEDING_SYSTEM};
	
	public static final String[] DOMPROC_FEED_SYSTEMS=new String[]{Processors.DOMPROC_FEEDING_SYSTEM}; 
	
	public static final String IMP="IMP";
	public static final String ENT="ENT";
	
	public static final String REQUEST="REQUEST";
	public static final String RESET="RESET";
	
	private static String[] companyNames=new String[]{""};
	private static String[] companyCodes=new String[]{""};
	
	
    private String type=RequestController.ENT;
    private String action=RequestController.REQUEST;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="gridMain"
    private GridPane gridMain; // Value injected by FXMLLoader

    @FXML // fx:id="finalRun"
    private CheckBox finalRun; // Value injected by FXMLLoader

    @FXML // fx:id="submit"
    private Button submit; // Value injected by FXMLLoader

    @FXML // fx:id="clientCodeCombo"
    private ChoiceBox<String> clientCodeCombo; // Value injected by FXMLLoader

    @FXML // fx:id="feedingSystemCombo"
    private ChoiceBox<String> feedingSystemCombo; // Value injected by FXMLLoader

    @FXML // fx:id="adminPass"
    private PasswordField adminPass; // Value injected by FXMLLoader

    @FXML // fx:id="methodLabel"
    private Label methodLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="methodCombo"
    private ChoiceBox<String> methodCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="clientName"
    private Label clientName; // Value injected by FXMLLoader

    @FXML // fx:id="monthPr"
    private ChoiceBox<Integer> monthPr; // Value injected by FXMLLoader

    @FXML // fx:id="yearPr"
    private ChoiceBox<Integer> yearPr; // Value injected by FXMLLoader
       
    @FXML // fx:id="errorMessage"
    private Label errorMessage; // Value injected by FXMLLoader
    
	public RequestController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	public void initialize() {
		
		assert finalRun != null : "fx:id=\"finalRun\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert clientCodeCombo != null : "fx:id=\"clientCodeCombo\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert clientName != null : "fx:id=\"clientName\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert monthPr != null : "fx:id=\"monthPr\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert yearPr != null : "fx:id=\"yearPr\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert feedingSystemCombo != null : "fx:id=\"feedingSystemCombo\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert adminPass != null : "fx:id=\"adminPass\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
        assert methodCombo != null : "fx:id=\"methodCombo\" was not injected: check your FXML file 'DomprocRequest.fxml'.";
		
		//Populate feeding systems
        feedingSystemCombo.setItems(FXCollections.observableArrayList(FEED_SYSTEMS));
		feedingSystemCombo.getSelectionModel().selectFirst();
		
		//Populate Client codes
		clientCodeCombo.setItems(FXCollections.observableArrayList(companyCodes));
		clientCodeCombo.getSelectionModel().selectFirst();
		
		clientCodeCombo.getSelectionModel().selectedIndexProperty().addListener(
			new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable,Number oldValue, Number newValue) {
					clientName.setText(companyNames[newValue.intValue()]);
										
				}
			}
		);
		
		populatePeriod(0,0);
		
		adminPass.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
		    	changeSubmitState(newValue);
		    }
		});
		//Populate Methods
		methodCombo.setItems(FXCollections.observableArrayList(Processors.METHODS));
		methodCombo.getSelectionModel().selectFirst();
		
		finalRun.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	                if(new_val){
	                	//Checked
	                	adminPass.setVisible(true);
	                	adminPass.setTooltip(new Tooltip("Admin password"));
	                	changeSubmitState(adminPass.getText());
	                	
	                }else{
	                	//unchecked
	                	adminPass.setVisible(false);
	                	submit.setDisable(false);
	                }
	        }
	    });		
	}
	
	private void changeSubmitState(String value){
		if(value.trim().equals("admin")){
    		submit.setDisable(false);
    	}else{
    		submit.setDisable(true);
    	}
    	//System.out.println("TextField Text Changed (newValue: " + newValue + ")\n");
	}
	
	
	private void populatePeriod(Integer selectedYear,Integer selectedMonth){
		
		Integer curYear=Calendar.getInstance().get(Calendar.YEAR);
		
		
		//populate year
		if(selectedYear==0){
			selectedYear=curYear;
		}
		
		int selectedItem=0;
		int i=0;
		Vector<Integer> v= new Vector<Integer>();
		for (Integer year=2000; year<=curYear;year++){
			v.add(year);		
			if(year.equals(selectedYear)){
				selectedItem=i;
			}		
			i++;
		}
		//yearPr.getItems().addAll(v);
		yearPr.setItems(FXCollections.observableArrayList(v));
		//set selected;
		yearPr.getSelectionModel().select(selectedItem);
		
		//populate month
		Integer curMonth=Calendar.getInstance().get(Calendar.MONTH);
		if(selectedMonth==0){
			selectedMonth=curMonth;
		}
		
		selectedItem=0;
		i=0;
		v= new Vector<Integer>();
		for (Integer month=1; month<=12;month++){
			v.add(month);		
			if(month.equals(selectedMonth)){
				selectedItem=i;
			}		
			i++;
		}
				
		monthPr.setItems(FXCollections.observableArrayList(v));
		monthPr.getSelectionModel().select(selectedItem);
		
	}
	
	@FXML
    void passValueChanged(ActionEvent event) {

    }

	
	@FXML
    public void buttonPressed(ActionEvent event) {

		String rollback="N";
				
		errorMessage.setText("");
		/*
		String selectedFeedSystem=FEED_SYSTEMS[feedingSystemCombo.getSelectionModel().getSelectedIndex()];
		if(this.type.equals(IMP)){
			selectedFeedSystem=DOMPROC_FEED_SYSTEMS[feedingSystemCombo.getSelectionModel().getSelectedIndex()];
		}
		*/
		String selectedFeedSystem=feedingSystemCombo.getValue();
	    String selectedPeriod=yearPr.getValue().toString()+String.format("%02d", monthPr.getValue());
	    String selectedCustomer=companyCodes[clientCodeCombo.getSelectionModel().getSelectedIndex()];
	    String selectedMethod=Processors.METHODS[methodCombo.getSelectionModel().getSelectedIndex()];
	    
	    String type=Processors.DOMPROC_PROOF;
	    
	    if(finalRun.isSelected()){
	    	if(this.type.equals(IMP)){
	    		type=Processors.DOMPROC_FINAL;
	    	}else{
	    		type=Processors.GPA_FINAL;
	    	}
	    }else{
	    	if(this.type.equals(IMP)){
	    		type=Processors.DOMPROC_PROOF;
	    	}else{
	    		type=Processors.GPA_PROOF;
	    	}
	    }
	    
	    if(action.equals(RESET)){
			rollback="Y";
			if(this.type.equals(IMP)){
				type=Processors.DOMPROC_RESET;
			}else{
				type=Processors.GPA_RESET;
			}
			
			selectedMethod="";
		}
		
	    
	    Gpareq gpareq= new Gpareq();
	    TreeMap<String, String> gpareqDetail=gpareq.initRecord();
	    gpareqDetail.put(Gpareq.DB_GPAREQ_TYPE, type);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_RLBCK, rollback);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_CUS, selectedCustomer);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_FED_SYS, selectedFeedSystem);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_PER, selectedPeriod);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_MTHD, selectedMethod);
	    gpareqDetail.put(Gpareq.DB_GPAREQ_STAT, Processors.REQUEST_STATUS_NEW);
	    
	    if(gpareq.insertGpareq(gpareqDetail)){
	    	errorMessage.setTextFill(Color.GREEN);
			errorMessage.setText(type+" has been requested successfully!");
	    }else{
	    	errorMessage.setTextFill(Color.RED);
	    	errorMessage.setText("Request has been failed.");
	    }
		System.out.println(selectedPeriod);
		
		
    }

	public void init(String type,String action) {
		Account account= new Account();
		this.type=type;
		this.action=action;
		
		if(action.equals(RESET)){
			methodLabel.setVisible(false);
			methodCombo.setVisible(false);
			finalRun.setVisible(false);
		}
		
		if(type.equals(IMP)){
			feedingSystemCombo.setItems(FXCollections.observableArrayList(DOMPROC_FEED_SYSTEMS));
		}else{
			feedingSystemCombo.setItems(FXCollections.observableArrayList(account.getFeedingsSystems()));		
		}
		
		feedingSystemCombo.getSelectionModel().selectFirst();
		
	}
	

}
