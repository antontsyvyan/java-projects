package com.foursoft.gpa.clientfx.model;

import javafx.beans.property.SimpleStringProperty;

public class MenuModel {
	private SimpleStringProperty itemText = new SimpleStringProperty();
	private SimpleStringProperty itemIcon = new SimpleStringProperty();

	public MenuModel() {
	}

	public MenuModel(String itemText, String itemIcon) {
		setItemText(itemText);
		setItemIcon(itemIcon);
	}

	public String getItemText() {
		return itemText.get();
	}

	public void setItemText(String itemText) {
		this.itemText.set(itemText);
	}

	public SimpleStringProperty itemTextProperty() {
		return itemText;
	}

	public String getItemIcon() {
		return itemIcon.get();
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon.set(itemIcon);
	}

	public SimpleStringProperty itemIconProperty() {
		return itemIcon;
	}

}
