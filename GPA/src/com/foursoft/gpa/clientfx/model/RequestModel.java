package com.foursoft.gpa.clientfx.model;

import java.util.TreeMap;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequestModel {

	
	private SimpleStringProperty type= new SimpleStringProperty();
	private SimpleStringProperty period= new SimpleStringProperty();
	private SimpleStringProperty status= new SimpleStringProperty();
	private SimpleObjectProperty<TreeMap<String, String>> details= new SimpleObjectProperty<TreeMap<String, String>>();
	private SimpleStringProperty id= new SimpleStringProperty();
	
	public RequestModel() {
		
	}
	
	public RequestModel(String type, String period, String status,TreeMap<String, String> details,String id) {
		setType(type);
		setPeriod(period);
		setStatus(status);
		setDetails(details);
		setId(id);
	}
	
	
	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public SimpleStringProperty typeProperty() {
		return type;
	}
	
	public String getPeriod() {
		return period.get();
	}

	public void setPeriod(String period) {
		this.period.set(period);
	}

	public SimpleStringProperty periodProperty() {
		return period;
	}
	
	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public SimpleStringProperty statusProperty() {
		return status;
	}
	
	public Object getDetails() {
		return details.get();
	}

	public void setDetails(TreeMap<String, String> details) {
		this.details.set(details);
	}

	public SimpleObjectProperty<TreeMap<String, String>> detailsProperty() {
		return details;
	}
	
	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public SimpleStringProperty idProperty() {
		return id;
	}

}
