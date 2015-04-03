package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleStringProperty;

public class TariffModel {
	private SimpleStringProperty tariffCodeColumn = new SimpleStringProperty();
	private SimpleStringProperty admColliColumn = new SimpleStringProperty();
	private SimpleStringProperty tariffDescriptionColumn = new SimpleStringProperty();
	private SimpleStringProperty vatColumn = new SimpleStringProperty();
	private SimpleStringProperty validFromColumn = new SimpleStringProperty();
	private SimpleStringProperty dutyRateColumn = new SimpleStringProperty();
	private SimpleStringProperty docCode1Column = new SimpleStringProperty();
	private SimpleStringProperty docCode2Column = new SimpleStringProperty();
	private SimpleStringProperty docCode3Column = new SimpleStringProperty();
	private SimpleStringProperty docCode4Column = new SimpleStringProperty();
	private SimpleStringProperty docCode5Column = new SimpleStringProperty();

	public TariffModel() {
	}

	public TariffModel(
			String tariffCodeColumn,
			String tariffDescriptionColumn,
			String vatColumn, 
			String validFromColumn, 
			String dutyRateColumn,
			String admColliColumn, 
			String docCode1Column, 
			String docCode2Column,
			String docCode3Column, 
			String docCode4Column, 
			String docCode5Column) {
		
		
		setTariffCodeColumn(tariffCodeColumn);
		setAdmColliColumn(admColliColumn);
		setTariffDescriptionColumn(tariffDescriptionColumn);
		setVatColumn(vatColumn);
		setValidFromColumn(validFromColumn);
		setDutyRateColumn(dutyRateColumn);
		setDocCode1Column(docCode1Column);
		setDocCode2Column(docCode2Column);
		setDocCode3Column(docCode3Column);
		setDocCode4Column(docCode4Column);
		setDocCode5Column(docCode5Column);
	}

	public String getTariffCodeColumn() {
		return tariffCodeColumn.get();
	}

	public void setTariffCodeColumn(String tariffCodeColumn) {
		this.tariffCodeColumn.set(tariffCodeColumn);
	}

	public SimpleStringProperty tariffCodeColumnProperty() {
		return tariffCodeColumn;
	}

	public String getAdmColliColumn() {
		return admColliColumn.get();
	}

	public void setAdmColliColumn(String admColliColumn) {
		this.admColliColumn.set(admColliColumn);
	}

	public SimpleStringProperty admColliColumnProperty() {
		return admColliColumn;
	}

	public String getTariffDescriptionColumn() {
		return tariffDescriptionColumn.get();
	}

	public void setTariffDescriptionColumn(String tariffDescriptionColumn) {
		this.tariffDescriptionColumn.set(tariffDescriptionColumn);
	}

	public SimpleStringProperty tariffDescriptionColumnProperty() {
		return tariffDescriptionColumn;
	}

	public String getVatColumn() {
		return vatColumn.get();
	}

	public void setVatColumn(String vatColumn) {
		this.vatColumn.set(vatColumn);
	}

	public SimpleStringProperty vatColumnProperty() {
		return vatColumn;
	}

	public String getValidFromColumn() {
		return validFromColumn.get();
	}

	public void setValidFromColumn(String validFromColumn) {
		this.validFromColumn.set(validFromColumn);
	}

	public SimpleStringProperty validFromColumnProperty() {
		return validFromColumn;
	}

	public String getDutyRateColumn() {
		return dutyRateColumn.get();
	}

	public void setDutyRateColumn(String dutyRateColumn) {
		this.dutyRateColumn.set(dutyRateColumn);
	}

	public SimpleStringProperty dutyRateColumnProperty() {
		return dutyRateColumn;
	}

	public String getDocCode1Column() {
		return docCode1Column.get();
	}

	public void setDocCode1Column(String docCode1Column) {
		this.docCode1Column.set(docCode1Column);
	}

	public SimpleStringProperty docCode1ColumnProperty() {
		return docCode1Column;
	}

	public String getDocCode2Column() {
		return docCode2Column.get();
	}

	public void setDocCode2Column(String docCode2Column) {
		this.docCode2Column.set(docCode2Column);
	}

	public SimpleStringProperty docCode2ColumnProperty() {
		return docCode2Column;
	}

	public String getDocCode3Column() {
		return docCode3Column.get();
	}

	public void setDocCode3Column(String docCode3Column) {
		this.docCode3Column.set(docCode3Column);
	}

	public SimpleStringProperty docCode3ColumnProperty() {
		return docCode3Column;
	}

	public String getDocCode4Column() {
		return docCode4Column.get();
	}

	public void setDocCode4Column(String docCode4Column) {
		this.docCode4Column.set(docCode4Column);
	}

	public SimpleStringProperty docCode4ColumnProperty() {
		return docCode4Column;
	}

	public String getDocCode5Column() {
		return docCode5Column.get();
	}

	public void setDocCode5Column(String docCode5Column) {
		this.docCode5Column.set(docCode5Column);
	}

	public SimpleStringProperty docCode5ColumnProperty() {
		return docCode5Column;
	}

}
