package com.foursoft.gpa.clientfx.model;

import java.util.TreeMap;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class IntlogModel {
	private SimpleStringProperty interfaceName = new SimpleStringProperty();
	private SimpleStringProperty seqNumber = new SimpleStringProperty();
	private SimpleStringProperty fileName = new SimpleStringProperty();
	private SimpleStringProperty procDate = new SimpleStringProperty();
	private SimpleStringProperty procTime = new SimpleStringProperty();
	private SimpleStringProperty status = new SimpleStringProperty();
	private SimpleStringProperty errorMesssage = new SimpleStringProperty();
	//private SimpleObjectProperty<IntlogModel> details= new SimpleObjectProperty<IntlogModel>();
	private SimpleObjectProperty<TreeMap<String, String>> intlogDetails= new SimpleObjectProperty<TreeMap<String, String>>();

	public IntlogModel() {
	}

	public IntlogModel(String interfaceName, String seqNumber, String fileName,
			String procDate, String procTime, String status,
			String errorMesssage,TreeMap<String, String> details) {
		setInterfaceName(interfaceName);
		setSeqNumber(seqNumber);
		setFileName(fileName);
		setProcDate(procDate);
		setProcTime(procTime);
		setStatus(status);
		setErrorMesssage(errorMesssage);
		setIntlogDetails(details);
	}

	public String getInterfaceName() {
		return interfaceName.get();
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName.set(interfaceName);
	}

	public SimpleStringProperty interfaceNameProperty() {
		return interfaceName;
	}

	public String getSeqNumber() {
		return seqNumber.get();
	}

	public void setSeqNumber(String seqNumber) {
		this.seqNumber.set(seqNumber);
	}

	public SimpleStringProperty seqNumberProperty() {
		return seqNumber;
	}

	public String getFileName() {
		return fileName.get();
	}

	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	public SimpleStringProperty fileNameProperty() {
		return fileName;
	}

	public String getProcDate() {
		return procDate.get();
	}

	public void setProcDate(String procDate) {
		this.procDate.set(procDate);
	}

	public SimpleStringProperty procDateProperty() {
		return procDate;
	}

	public String getProcTime() {
		return procTime.get();
	}

	public void setProcTime(String procTime) {
		this.procTime.set(procTime);
	}

	public SimpleStringProperty procTimeProperty() {
		return procTime;
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

	public String getErrorMesssage() {
		return errorMesssage.get();
	}

	public void setErrorMesssage(String errorMesssage) {
		this.errorMesssage.set(errorMesssage);
	}

	public SimpleStringProperty errorMesssageProperty() {
		return errorMesssage;
	}
	
	public Object getIntlogDetails() {
		return intlogDetails.get();
	}

	private void setIntlogDetails(TreeMap<String, String> details) {	
		
		this.intlogDetails.set(details);
	}

	public SimpleObjectProperty<TreeMap<String, String>> intlogDetailsProperty() {
		return intlogDetails;
	}

}