package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleStringProperty;

public class RatesModel {
	
	private SimpleStringProperty countryName = new SimpleStringProperty();
	private SimpleStringProperty startDate = new SimpleStringProperty();
	private SimpleStringProperty rate = new SimpleStringProperty();
	private SimpleStringProperty currencyCode = new SimpleStringProperty();
	private SimpleStringProperty currencyName = new SimpleStringProperty();
	private SimpleStringProperty reverseRate = new SimpleStringProperty();

	public RatesModel() {
	}

	public RatesModel(String countryName, 
			String startDate, 
			String rate,
			String currencyCode, 
			String currencyName, 
			String reverseRate) {
		
		setCountryName(countryName);
		setStartDate(startDate);
		setRate(rate);
		setCurrencyCode(currencyCode);
		setCurrencyName(currencyName);
		setReverseRate(reverseRate);
	}

	public String getCountryName() {
		return countryName.get();
	}

	public void setCountryName(String countryName) {
		this.countryName.set(countryName);
	}

	public SimpleStringProperty countryNameProperty() {
		return countryName;
	}

	public String getStartDate() {
		return startDate.get();
	}

	public void setStartDate(String startDate) {
		this.startDate.set(startDate);
	}

	public SimpleStringProperty startDateProperty() {
		return startDate;
	}

	public String getRate() {
		return rate.get();
	}

	public void setRate(String rate) {
		this.rate.set(rate);
	}

	public SimpleStringProperty rateProperty() {
		return rate;
	}

	public String getCurrencyCode() {
		return currencyCode.get();
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode.set(currencyCode);
	}

	public SimpleStringProperty currencyCodeProperty() {
		return currencyCode;
	}

	public String getCurrencyName() {
		return currencyName.get();
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName.set(currencyName);
	}

	public SimpleStringProperty currencyNameProperty() {
		return currencyName;
	}

	public String getReverseRate() {
		return reverseRate.get();
	}

	public void setReverseRate(String reverseRate) {
		this.reverseRate.set(reverseRate);
	}

	public SimpleStringProperty reverseRateProperty() {
		return reverseRate;
	}

}