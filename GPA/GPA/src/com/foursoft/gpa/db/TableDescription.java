package com.foursoft.gpa.db;

import com.foursoft.gpa.utils.Details;


public class TableDescription extends Details{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String META_TABLE_SCHEM="TABLE_SCHEM";
	public static final String META_TABLE_NAME="TABLE_NAME";
	public static final String META_COLUMN_NAME="COLUMN_NAME";
	public static final String META_TYPE_NAME="TYPE_NAME";
	public static final String META_COLUMN_SIZE="COLUMN_SIZE";
	public static final String META_DECIMAL_DIGITS="DECIMAL_DIGITS";
	
	
	private String tableSchem;
	private String tableName;
	private String columnName;
	private String typeName;
	private int columnSize;
	
	public TableDescription() {
	}
	
	public String getTableSchem() {
		return tableSchem;
	}

	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	private int decimalDigits;
	

}
