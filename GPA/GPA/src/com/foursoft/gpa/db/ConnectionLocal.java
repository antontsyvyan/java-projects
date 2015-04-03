package com.foursoft.gpa.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

public class ConnectionLocal{
		
	private Connection conn;
	
	public ConnectionLocal() {
				
	}
	protected Connection getConnection() throws Exception {
		Connection conn=null;
		String connectionURL;
		File currentFile = new File(".");		
		try {
			Class.forName("org.sqlite.JDBC");
			String db = currentFile.getCanonicalPath()+"\\conf\\client.db";
			//System.out.println(db);
			connectionURL="jdbc:sqlite:"+db;
			conn=DriverManager.getConnection(connectionURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public TreeMap<String, String> readTable(String statement) {

		TreeMap<String, String> table =new TreeMap<String, String>();
		Statement stmt = null;

		try {
			conn = ((conn == null) ? getConnection() : conn);
			stmt = conn.createStatement();

			ResultSet res  = stmt.executeQuery(statement);

			if (res.next()) {
				ResultSetMetaData rsmd = res.getMetaData();
				int numColumns = rsmd.getColumnCount();

				for (int i = 1; i < numColumns + 1; i++) {

					String columnName = rsmd.getColumnLabel(i);
					String fieldValue = res.getString(i);

					if (fieldValue == null || fieldValue.equals("null")) {
						fieldValue = "";
					}

					table.put(columnName.toUpperCase(), fieldValue);
				}
			} else {
				table = null;
			}

			stmt.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();

		}

		return table;
	}
	
	public ArrayList<TreeMap<String, String>> readTableMultiple(String statement) {

		ArrayList<TreeMap<String, String>> records = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> table =new TreeMap<String, String>();
		
		Statement stmt = null;
		
		try {
			conn = ((conn == null) ? getConnection() : conn);
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(statement);
			ResultSetMetaData rsmd = res.getMetaData();
			while (res.next()) {				
				int numColumns = rsmd.getColumnCount();
				table = new TreeMap<String, String>();

				for (int i = 1; i < numColumns + 1; i++) {

					String columnName = rsmd.getColumnLabel(i);
					String fieldValue = res.getString(i);

					if (fieldValue == null || fieldValue.equals("null")) {
						fieldValue = "";
					}

					table.put(columnName.toUpperCase(), fieldValue);
				}
				records.add(table);
			}
			
			stmt.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();

		}
		
		
		return records;
	}	
	
	
	public boolean insertUpdateTable(String statement) {
		boolean success = true;

		try {
			conn = ((conn == null) ? getConnection() : conn);
			PreparedStatement ps = conn.prepareStatement(statement);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException sqle) {
			success = false;
			sqle.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			success = false;
		} finally {

			closeConnection();

		}
		return success;
	}
	
	public void closeConnection(){
		if(conn != null){		
			try {
				conn.close();
				conn = null;
			} catch (SQLException sqle) {
			}			
		}
	}
	
}
