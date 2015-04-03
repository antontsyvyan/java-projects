package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ArticleModel {
	private SimpleStringProperty articleNumberColumn = new SimpleStringProperty();
	private SimpleStringProperty articleDescriptionColumn = new SimpleStringProperty();
	private SimpleStringProperty accountColumn = new SimpleStringProperty();
	private SimpleStringProperty tariffCodeColumn = new SimpleStringProperty();
	private SimpleDoubleProperty dutyRateColumn = new SimpleDoubleProperty();
	private SimpleDoubleProperty vatrateColumn = new SimpleDoubleProperty();
	private SimpleStringProperty validFromColumn = new SimpleStringProperty();
	private SimpleStringProperty originColumn = new SimpleStringProperty();
	private SimpleStringProperty aadUnitsColumn = new SimpleStringProperty();
	private SimpleStringProperty docCodeColumn = new SimpleStringProperty();
	private SimpleStringProperty colliCodeColumn = new SimpleStringProperty();

	public ArticleModel() {
	}

	public ArticleModel(String articleNumberColumn,
			String articleDescriptionColumn, String accountColumn,
			String tariffCodeColumn, Double dutyRateColumn,
			Double vatrateColumn, String validFromColumn, String originColumn,
			String aadUnitsColumn, String docCodeColumn,String colliCodeColumn) {
		
		setArticleNumberColumn(articleNumberColumn);
		setArticleDescriptionColumn(articleDescriptionColumn);
		setAccountColumn(accountColumn);
		setTariffCodeColumn(tariffCodeColumn);
		setDutyRateColumn(dutyRateColumn);
		setVatrateColumn(vatrateColumn);
		setValidFromColumn(validFromColumn);
		setOriginColumn(originColumn);
		setAadUnitsColumn(aadUnitsColumn);
		setDocCodeColumn(docCodeColumn);
		setColliCodeColumn(colliCodeColumn);
	}

	public String getArticleNumberColumn() {
		return articleNumberColumn.get();
	}

	public void setArticleNumberColumn(String articleNumberColumn) {
		this.articleNumberColumn.set(articleNumberColumn);
	}

	public SimpleStringProperty articleNumberColumnProperty() {
		return articleNumberColumn;
	}

	public String getArticleDescriptionColumn() {
		return articleDescriptionColumn.get();
	}

	public void setArticleDescriptionColumn(String articleDescriptionColumn) {
		this.articleDescriptionColumn.set(articleDescriptionColumn);
	}

	public SimpleStringProperty articleDescriptionColumnProperty() {
		return articleDescriptionColumn;
	}

	public String getAccountColumn() {
		return accountColumn.get();
	}

	public void setAccountColumn(String accountColumn) {
		this.accountColumn.set(accountColumn);
	}

	public SimpleStringProperty accountColumnProperty() {
		return accountColumn;
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

	public double getDutyRateColumn() {
		return dutyRateColumn.get();
	}

	public void setDutyRateColumn(Double dutyRateColumn) {
		this.dutyRateColumn.set(dutyRateColumn);
	}

	public SimpleDoubleProperty dutyRateColumnProperty() {
		return dutyRateColumn;
	}

	public double getVatrateColumn() {
		return vatrateColumn.get();
	}

	public void setVatrateColumn(Double vatrateColumn) {
		this.vatrateColumn.set(vatrateColumn);
	}

	public SimpleDoubleProperty vatrateColumnProperty() {
		return vatrateColumn;
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

	public String getOriginColumn() {
		return originColumn.get();
	}

	public void setOriginColumn(String originColumn) {
		this.originColumn.set(originColumn);
	}

	public SimpleStringProperty originColumnProperty() {
		return originColumn;
	}

	public String getAadUnitsColumn() {
		return aadUnitsColumn.get();
	}

	public void setAadUnitsColumn(String aadUnitsColumn) {
		this.aadUnitsColumn.set(aadUnitsColumn);
	}

	public SimpleStringProperty aadUnitsColumnProperty() {
		return aadUnitsColumn;
	}

	public String getDocCodeColumn() {
		return docCodeColumn.get();
	}

	public void setDocCodeColumn(String docCodeColumn) {
		this.docCodeColumn.set(docCodeColumn);
	}

	public SimpleStringProperty docCodeColumnProperty() {
		return docCodeColumn;
	}
	
	public String getColliCodeColumn() {
		return colliCodeColumn.get();
	}

	public void setColliCodeColumn(String colliCodeColumn) {
		this.colliCodeColumn.set(colliCodeColumn);
	}

	public SimpleStringProperty colliCodeColumnProperty() {
		return colliCodeColumn;
	}

}
