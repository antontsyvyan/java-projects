package com.foursoft.gpa.utils;

import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;

public class DiskErrorsModel {

	private static final String SEPARATOR=";";
	
	public static final String TYPE_ERROR="ERROR";
	public static final String TYPE_WARNING="WARNING";
	
	private SimpleStringProperty line= new SimpleStringProperty();
	private SimpleStringProperty a3= new SimpleStringProperty();
	private SimpleStringProperty type= new SimpleStringProperty();
	private SimpleStringProperty block= new SimpleStringProperty();
	private SimpleStringProperty field= new SimpleStringProperty();
	private SimpleStringProperty message= new SimpleStringProperty();
	
	
	public DiskErrorsModel(){
	}
	
	public DiskErrorsModel(Integer line, String a3, String type, String block, String field, String message){
		
		
		setLine(String.valueOf(line));
		setA3((a3.equals("")?" ":a3));
		setType((type.equals("")?" ":type));
		setBlock((block.equals("")?" ":block));
		setField((field.equals("")?" ":field));
		setMessage((message.equals("")?" ":message));
	}
	
	public String getLine() {
		return line.get();
	}
	public void setLine(String line) {
		this.line.set(line);
	}
	
	public SimpleStringProperty lineProperty(){
		return line;
	}
	//
	public String getA3() {
		return a3.get();
	}
	public void setA3(String a3) {
		this.a3.set(a3);
	}	
	public SimpleStringProperty a3Property() {
		return a3;
	}
	//
	public String getType() {
		return type.get();
	}
	public void setType(String type) {
		this.type.set(type);
	}	
	public SimpleStringProperty typeProperty() {
		return type;
	}
	//
	public String getBlock() {
		return block.get();
	}
	public void setBlock(String block) {
		this.block.set(block);
	}
	public SimpleStringProperty blockProperty() {
		return block;
	}
	//
	public String getField() {
		return field.get();
	}
	public void setField(String field) {
		this.field.set(field);
	}
	public SimpleStringProperty fieldProperty() {
		return field;
	}
	//
	public String getMessage() {
		return message.get();
	}
	public void setMessage(String message) {
		this.message.set(message);
	}
	public SimpleStringProperty messageProperty() {
		return message;
	}
	
	public String toString(){
		
		return getLine()+SEPARATOR+getA3()+SEPARATOR+getType()+SEPARATOR+getBlock()+SEPARATOR+getField()+SEPARATOR+getMessage();
	}
	
	public void set(String line){
		if(line!=null && line.length()>0){
			StringTokenizer st = new StringTokenizer(line,SEPARATOR);
			int teller=0;
			while (st.hasMoreElements()) {
				switch (teller){
					case 0: setLine((String)st.nextElement()); break;
					case 1: setA3((String)st.nextElement()); break;
					case 2: setType((String)st.nextElement());break;
					case 3: setBlock((String)st.nextElement()); break;
					case 4: setField((String)st.nextElement()); break;
					case 5: setMessage((String)st.nextElement()); break;
				}
				
				teller++;
			}
		}
	}
}
