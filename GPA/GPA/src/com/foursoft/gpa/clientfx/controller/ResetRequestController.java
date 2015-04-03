package com.foursoft.gpa.clientfx.controller;

import java.util.Calendar;
import java.util.TreeMap;
import java.util.Vector;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

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
import javafx.scene.paint.Color;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.utils.Processors;

public class ResetRequestController {
	
	private String type=Disk.ENTREPOT;
	
	public static final String[] DOMPROC_FEED_SYSTEMS=new String[]{Processors.DOMPROC_FEEDING_SYSTEM}; 
	
    @FXML // fx:id="submit"
    private Button submit; // Value injected by FXMLLoader

    @FXML // fx:id="pass"
    private PasswordField pass; // Value injected by FXMLLoader

    @FXML // fx:id="monthPr"
    private ChoiceBox<Integer> monthPr; // Value injected by FXMLLoader

    @FXML // fx:id="yearPr"
    private ChoiceBox<Integer> yearPr; // Value injected by FXMLLoader

    @FXML // fx:id="feedingSystemCombo"
    private ChoiceBox<String> feedingSystemCombo; // Value injected by FXMLLoader

    @FXML // fx:id="delData"
    private CheckBox delData; // Value injected by FXMLLoader
    
    @FXML // fx:id="errorMessage"
    private Label errorMessage; // Value injected by FXMLLoader
	

	@FXML
	public void initialize() {
		
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        assert monthPr != null : "fx:id=\"monthPr\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        assert yearPr != null : "fx:id=\"yearPr\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        assert feedingSystemCombo != null : "fx:id=\"feedingSystemCombo\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        assert delData != null : "fx:id=\"delData\" was not injected: check your FXML file 'ResetRequest.fxml'.";
        
        
        populatePeriod(0,0);
		
		pass.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
		    	changeSubmitState(newValue);
		    }
		});
		
		
	}
	
	
	 public void buttonPressed(ActionEvent event) {

			String rollback="N";				
			errorMessage.setText("");								
			String selectedFeedSystem=feedingSystemCombo.getValue();
		    String selectedPeriod=yearPr.getValue().toString()+String.format("%02d", monthPr.getValue());
		    		    
		    if(delData.isSelected()){		    	
		    	Action response = Dialogs.create()
		    			.title("Confirm Dialog")
		    	        .masthead("All data for the period "+selectedPeriod+ "will be deleted.")
		    	        .message("Do you want to continue?")
		    	        .actions(Dialog.Actions.OK, Dialog.Actions.CANCEL)
		    	        .showConfirm();

		    	if (response == Dialog.Actions.OK) {
		    		rollback="Y";
		    	} else {
		    	   return;
		    	}		    	
		    }	
		    
		    String type=Processors.DOMPROC_RESET;
		    
		    if(this.type.equals(Disk.ENTREPOT)){
		    	type=Processors.GPA_RESET;
		    }
		    	    
		    Gpareq gpareq= new Gpareq();
		    TreeMap<String, String> gpareqDetail=gpareq.initRecord();
		    gpareqDetail.put(Gpareq.DB_GPAREQ_TYPE, type);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_RLBCK, rollback);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_CUS, "");
		    gpareqDetail.put(Gpareq.DB_GPAREQ_FED_SYS, selectedFeedSystem);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_PER, selectedPeriod);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_MTHD, "");
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
	 
	 
	 public void init(String type)
	    {
	        Account account = new Account();
	        this.type = type;
	        
	        if(type.equals("IMP"))
	            feedingSystemCombo.setItems(FXCollections.observableArrayList(DOMPROC_FEED_SYSTEMS));
	        else
	            feedingSystemCombo.setItems(FXCollections.observableArrayList(account.getFeedingsSystems()));
	            feedingSystemCombo.getSelectionModel().selectFirst();
	    }

	
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	

}
