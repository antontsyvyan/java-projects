package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleStringProperty;

public class CodeBookModel {

	private SimpleStringProperty code= new SimpleStringProperty();
	private SimpleStringProperty description= new SimpleStringProperty();
	private SimpleStringProperty remarks= new SimpleStringProperty();
	
	public CodeBookModel(String code, String description, String remarks) {
		setCode(code);
		setDescription(description);
		setRemarks(remarks);	
	}
	
	public String getCode() {
		return code.get();
	}

	public void setCode(String code) {
		this.code.set(code);
	}

	public SimpleStringProperty codeProperty() {
		return code;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public SimpleStringProperty descriptionProperty() {
		return description;
	}

	public String getRemarks() {
		return remarks.get();
	}

	public void setRemarks(String remarks) {
		this.remarks.set(remarks);
	}

	public SimpleStringProperty remarksProperty() {
		return remarks;
	}

	public CodeBookModel() {
		// TODO Auto-generated constructor stub
	}

}
