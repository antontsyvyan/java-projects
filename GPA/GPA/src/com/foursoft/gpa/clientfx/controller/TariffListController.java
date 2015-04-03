package com.foursoft.gpa.clientfx.controller;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import com.foursoft.gpa.clientfx.model.TariffModel;
import com.foursoft.gpa.db.Tariff;


public class TariffListController {
	
  	@FXML // fx:id="docCode2Column"
    private TableColumn<TariffModel, String> docCode2Column; // Value injected by FXMLLoader

    @FXML // fx:id="vatColumn"
    private TableColumn<TariffModel, String> vatColumn; // Value injected by FXMLLoader

    @FXML // fx:id="docCode3Column"
    private TableColumn<TariffModel, String> docCode3Column; // Value injected by FXMLLoader

    @FXML // fx:id="tariffCodeColumn"
    private TableColumn<TariffModel, String> tariffCodeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="admColliColumn"
    private TableColumn<TariffModel, String> admColliColumn; // Value injected by FXMLLoader

    @FXML // fx:id="grid"
    private TableView<TariffModel> grid; // Value injected by FXMLLoader

    @FXML // fx:id="tariffDescriptionColumn"
    private TableColumn<TariffModel, String> tariffDescriptionColumn; // Value injected by FXMLLoader

    @FXML // fx:id="docCode4Column"
    private TableColumn<TariffModel, String> docCode4Column; // Value injected by FXMLLoader

    @FXML // fx:id="articleNumberColumn"
    private TableColumn<TariffModel, String> articleNumberColumn; // Value injected by FXMLLoader

    @FXML // fx:id="searchTariff"
    private TextField searchTariff; // Value injected by FXMLLoader

    @FXML // fx:id="validFromColumn"
    private TableColumn<TariffModel, String> validFromColumn; // Value injected by FXMLLoader

    @FXML // fx:id="dutyRateColumn"
    private TableColumn<TariffModel, String> dutyRateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="docCode1Column"
    private TableColumn<TariffModel, String> docCode1Column; // Value injected by FXMLLoader

    @FXML // fx:id="docCode5Column"
    private TableColumn<TariffModel, String> docCode5Column; // Value injected by FXMLLoader
    
    private ObservableList<TariffModel> data = FXCollections.observableArrayList();
    
    public TariffListController() {
		// TODO Auto-generated constructor stub
	}
    
    @FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
    	
    	assert docCode2Column != null : "fx:id=\"docCode2Column\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert vatColumn != null : "fx:id=\"vatColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert docCode3Column != null : "fx:id=\"docCode3Column\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert tariffCodeColumn != null : "fx:id=\"tariffCodeColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert admColliColumn != null : "fx:id=\"admColliColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert grid != null : "fx:id=\"grid\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert tariffDescriptionColumn != null : "fx:id=\"tariffDescriptionColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert docCode4Column != null : "fx:id=\"docCode4Column\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert articleNumberColumn != null : "fx:id=\"articleNumberColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert searchTariff != null : "fx:id=\"searchTariff\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert validFromColumn != null : "fx:id=\"validFromColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert dutyRateColumn != null : "fx:id=\"dutyRateColumn\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert docCode1Column != null : "fx:id=\"docCode1Column\" was not injected: check your FXML file 'TariffList.fxml'.";
        assert docCode5Column != null : "fx:id=\"docCode5Column\" was not injected: check your FXML file 'TariffList.fxml'.";
    	
    	tariffCodeColumn.setCellValueFactory(cellData -> cellData.getValue().tariffCodeColumnProperty());
    	admColliColumn.setCellValueFactory(cellData -> cellData.getValue().admColliColumnProperty());
    	tariffDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().tariffDescriptionColumnProperty());
    	vatColumn.setCellValueFactory(cellData -> cellData.getValue().vatColumnProperty());
    	validFromColumn.setCellValueFactory(cellData -> cellData.getValue().validFromColumnProperty());
    	dutyRateColumn.setCellValueFactory(cellData -> cellData.getValue().dutyRateColumnProperty());
    	docCode1Column.setCellValueFactory(cellData -> cellData.getValue().docCode1ColumnProperty());
    	docCode2Column.setCellValueFactory(cellData -> cellData.getValue().docCode2ColumnProperty());
    	docCode3Column.setCellValueFactory(cellData -> cellData.getValue().docCode3ColumnProperty());
    	docCode4Column.setCellValueFactory(cellData -> cellData.getValue().docCode4ColumnProperty());
    	docCode5Column.setCellValueFactory(cellData -> cellData.getValue().docCode5ColumnProperty());
    	
    	try {
			populateData("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// 1. Wrap the ObservableList in a FilteredList (initially display all data).
    	FilteredList<TariffModel> filteredData = new FilteredList<>(data, p -> true);
    	searchTariff.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tariff -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tariff.getTariffCodeColumn().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} 
				return false; // Does not match.
			});
		});
    	// 3. Wrap the FilteredList in a SortedList. 
		SortedList<TariffModel> sortedData = new SortedList<>(filteredData);		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(grid.comparatorProperty());		
		// 5. Add sorted (and filtered) data to the table.
		grid.setItems(sortedData);
  	
    }

    void buttonPressed(ActionEvent event) {
    	//Disable search field and button                        
    	searchTariff.setDisable(true);
 			
    	grid.setPlaceholder(new Label(""));
    	
    	Task<Void> task = new Task<Void>() {
			@Override
			public void run() {
				try {
					populateData(searchTariff.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Platform.runLater(new Runnable() {
                     @Override public void run() {
                    	 
                    	//grid.setPlaceholder(emptyTableMessage);
                    	//Enable search field and button  
                    	 searchTariff.setDisable(false);
                 		//progressPane.toBack();
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

    @FXML
    void addTariffPressed(ActionEvent event) {

    }
	
	
	public void populateData(String searchString) throws Exception{
	    
		Tariff tariff= new Tariff();
		
		ArrayList<TariffModel> list =new ArrayList<TariffModel>();
    	
    	data.clear();
    	
    	list=tariff.getTariffModelCollection(searchString);
    	data.addAll(list);
    		
    	//grid.setItems(data);
	}

}
