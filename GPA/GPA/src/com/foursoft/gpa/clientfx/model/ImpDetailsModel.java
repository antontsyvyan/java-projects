package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleStringProperty;

public class ImpDetailsModel {

	private SimpleStringProperty a3= new SimpleStringProperty();
	private SimpleStringProperty period= new SimpleStringProperty();
	private SimpleStringProperty article= new SimpleStringProperty();
	private SimpleStringProperty ref1= new SimpleStringProperty();
	private SimpleStringProperty ref2= new SimpleStringProperty();
	private SimpleStringProperty ref3= new SimpleStringProperty();
	private SimpleStringProperty ref4= new SimpleStringProperty();
	private SimpleStringProperty invoice= new SimpleStringProperty();
	private SimpleStringProperty custValue= new SimpleStringProperty();
	
	public ImpDetailsModel(String a3,String period, String article, String ref1, String ref2, String ref3, String ref4, String invoice, String custValue){
		setA3(a3);
		setPeriod(period);
		setArticle(article);
		setRef1(ref1);
		setRef2(ref2);
		setRef3(ref3);
		setRef4(ref4);
		setInvoice(invoice);
		setCustValue(custValue);
	}
	
	public String getA3() {
		return a3.get();
	}	
	public SimpleStringProperty a3Property() {
		return a3;
	}	
	public void setA3(String a3) {
		this.a3.set(a3);
	}
	
	
	public String getPeriod() {
		return period.get();
	}	
	public SimpleStringProperty periodProperty() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period.set(period);
	}
	
	
	public String getArticle() {
		return article.get();
	}	
	public SimpleStringProperty articleProperty() {
		return article;
	}		
	public void setArticle(String article) {
		this.article.set(article);
	}
	
	public String getRef1() {
		return ref1.get();
	}
	public SimpleStringProperty ref1Property() {
		return ref1;
	}
	public void setRef1(String ref1) {
		this.ref1.set(ref1);
	}
	
	
	public String getRef2() {
		return ref2.get();
	}
	public SimpleStringProperty ref2Property() {
		return ref2;
	}
	public void setRef2(String ref2) {
		this.ref2.set(ref2);
	}
	
	
	public String getRef3() {
		return ref3.get();
	}
	public SimpleStringProperty ref3Property() {
		return ref3;
	}
	public void setRef3(String ref3) {
		this.ref3.set(ref3);
	}
	
	
	public String getRef4() {
		return ref4.get();
	}
	public SimpleStringProperty ref4Property() {
		return ref4;
	}
	public void setRef4(String ref4) {
		this.ref4.set(ref4);
	}
	
	
	public String getInvoice() {
		return invoice.get();
	}	
	public SimpleStringProperty invoiceProperty() {
		return invoice;
	}
	
	public void setInvoice(String invoice) {
		this.invoice.set(invoice);
	}
	
	
	public String getCustValue() {
		return custValue.get();
	}	
	public SimpleStringProperty custValueProperty() {
		return custValue;
	}
	
	public void setCustValue(String custValue) {
		this.custValue.set(custValue);
	}
}
