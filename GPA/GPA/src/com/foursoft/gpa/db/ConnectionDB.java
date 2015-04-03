package com.foursoft.gpa.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.cache.MemoryCache;

public class ConnectionDB {

	private Logger log = Logger.getLogger(this.getClass().toString());
	
	public MemoryCache<String, TreeMap<String, String>> cache;
	
	protected TableDescription tableDescription[];
	
	private Connection conn = null;
	
	private ResultSet res;
	
	private boolean close=true;
	
	public String dsPrefix="";
	
	public int minutesToLive=10;
	
	public static DataSource dataSource;
	
	public boolean pooledConnection=true;
	
	public String connectionURL="";
		
	public ConnectionDB() {

	}
	/**
	 * If parameter false - you should self take care of closing connection.
	 * 
	 */
	
	public ConnectionDB(boolean close) {
		try {
			conn = ((conn == null) ? getConnection() : conn);
			this.close=close;
		} catch (Exception e) {

		}
	}
	
	protected Connection getConnection() throws Exception {
		
		Connection conn=null;
		String pref="";
		if(!dsPrefix.trim().equals("")){
			pref=dsPrefix+".";
		}
		try {
			Class.forName(Resources.getSetting("driver.class"));
			try {
				if(pooledConnection){
					//This will create pooled connections
					if(dataSource==null){
						dataSource = setupDataSource(pref,connectionURL);						
					}
					conn=dataSource.getConnection();
				}else{
					
					if(this.connectionURL.equals("")){
						conn = DriverManager.getConnection(Resources.getSetting(pref+"connection.url"),Resources.getSetting(pref+"user.name"),Resources.getSetting(pref+"password"));
					}else{
						conn = DriverManager.getConnection(connectionURL);
					}
					
				}
			} catch (SQLException e) {
				log.severe("Connection Failed! Check error log");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			log.severe("JDBC Driver is missing");
			e.printStackTrace();
		}

		return conn;
	}
	
	

	public TreeMap<String, String> readTable(String statement) {

		TreeMap<String, String> table =new TreeMap<String, String>();

		try {
			conn = ((conn == null) ? getConnection() : conn);
			PreparedStatement ps = conn.prepareStatement(statement,	ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			res = ps.executeQuery();

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

			ps.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.severe("" + sqle.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("" + e.getMessage());
		} finally {
			if(close){
				closeConnection();
			}
		}

		return table;
	}

	public ArrayList<TreeMap<String, String>> readTableMultiple(String statement) {

		ArrayList<TreeMap<String, String>> records = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> table =new TreeMap<String, String>();

		log.fine("SQL statement: " + statement);
		
		try {
			conn = ((conn == null) ? getConnection() : conn);
			if(conn!=null){
				//conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				PreparedStatement ps = conn.prepareStatement(statement,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	
				res = ps.executeQuery();
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
	
				ps.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.severe("" + sqle.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.severe("" + e.getMessage());
		} finally {
			if(close){
				closeConnection();
			}
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
			log.severe("ERROR:" + sqle.getMessage()+"\r\n Statement: "+statement+"\r\n Check error log for more details");
		} catch (Exception e) {
			log.severe("ERROR:" + e.getMessage()+"\r\n Statement: "+statement+"\r\n Check error log for more details");
			e.printStackTrace();
			success = false;
		} finally {
			if(close){
				closeConnection();
			}
		}
		return success;
	}
	
	public String getDbName() {			
		String pref="";
		if(!dsPrefix.trim().equals("")){
			pref=dsPrefix+".";
		}		
		
		String input=pref+"db.name";
		pref=Resources.getSetting(input.trim());
		
		if(!pref.trim().equals("")){
			pref=pref+".";
		}	
		
		return pref;
				
	}
	
	public void setClosingFlag(boolean close){		
		this.close=close;	
	}
	
	
	public void closeConnection(){
		if(conn != null){		
			try {
				conn.close();
				conn = null;
			} catch (SQLException sqle) {
				log.severe("Unable to close connection, reason: " + sqle);
			}			
		}
	}
	
	public void setAutoCommit(boolean flag){
		
		try {
			conn.setAutoCommit(flag);
		} catch (Exception e) {
			
		}

	}
	
	public void commit(){
		
		 try {
			conn.commit();
		} catch (SQLException e) {

		}

	}
	
	public void rollback(){		
		 try {
			conn.rollback();
		} catch (SQLException e) {

		}

	}
	
	public TableDescription[] getTableMetadata(String table){
		
		TableDescription[] metaData=null;
		Connection conn=null;
		try {
			conn = getConnection();
			
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getColumns(null, null, table, null);
			Vector<TableDescription> v=new Vector<TableDescription>();
			
			while (resultSet.next()) {		        
		        TableDescription td= new TableDescription();
		        String schemName=resultSet.getString(TableDescription.META_TABLE_SCHEM);
		        String tableName=resultSet.getString(TableDescription.META_TABLE_NAME);
		        String columnName=resultSet.getString(TableDescription.META_COLUMN_NAME);
		        String typeName=resultSet.getString(TableDescription.META_TYPE_NAME);
		        
		        if(schemName!=null){
		        	schemName=schemName.toUpperCase();
		        }
		        
		        if(tableName!=null){
		        	tableName=tableName.toUpperCase();
		        }
		        
		        if(columnName!=null){
		        	columnName=columnName.toUpperCase();
		        }
		        
		        if(typeName!=null){
		        	typeName=typeName.toUpperCase();
		        }
		        	        
		        td.setTableSchem(schemName);
		        td.setTableName(tableName);
		        td.setColumnName(columnName);
		        td.setTypeName(typeName);
		        td.setColumnSize(resultSet.getInt(TableDescription.META_COLUMN_SIZE));
		        td.setDecimalDigits(resultSet.getInt(TableDescription.META_DECIMAL_DIGITS));		        
		        v.add(td);		        
			}
			
			if(v!=null && v.size()>0){
				metaData= new TableDescription[v.size()];
				v.toArray(metaData);
			}			
		} catch (Exception e) {

		}finally{
			if(conn != null){		
				try {
					conn.close();
					conn = null;
				} catch (Exception e) {
					
				}
			}
		}
		
		return metaData;
	}
	
	protected String formatString(String str){
		String retString="";
		
		if(str!=null){
			retString=str;
		}
		retString=safeGuard(retString);
		return retString;
	}
	
	protected String formatInt(String val){
		String retVal="0";
		
		if(val!=null){
			try{
				int num = Integer.parseInt(val);
				String tmp=""+num;
				if(tmp.length()==val.length()){
					retVal=val;
				}
			}catch(NumberFormatException ex){
				
			}
		}
		
		return retVal;
	}
	
	protected String formatDecimal(String val){
		//TODO handling to format decimals
		String ret="0";
		if(val!=null && val.trim().length()>0){
			ret=val.trim();
			
			//replace (,) by (.)
			ret=ret.replace(",", ".");
		}
		
		return ret;
	}
	
	protected TreeMap<String, String> formatRecord(TreeMap<String, String> record,TableDescription[]td){
		
		//DateUtils du=new DateUtils();
		TreeMap<String, String> table =new TreeMap<String, String>();
		if(td!=null && td.length>0){
			
			if(record!=null && record.size()>0){
				for (int i=0;i<td.length;i++){
					
					String value="";
					if(record.get(td[i].getColumnName())!=null){
						value=record.get(td[i].getColumnName());
						
						if(td[i].getTypeName().toLowerCase().contains("char")){
							
							if(value.length()>td[i].getColumnSize()){
								value=value.substring(0,td[i].getColumnSize());
							}
							value=safeGuard(value);
							value=formatString(value);
						}
						
						if(td[i].getTypeName().toLowerCase().contains("int")){
							value=formatInt(value);
							
						}
						if(td[i].getTypeName().toLowerCase().contains("date")){
							//value=du.formatDate(value);
							
						}
						if(td[i].getTypeName().toLowerCase().contains("numeric")){
							value=formatDecimal(value);
							
						}
						if(td[i].getTypeName().toLowerCase().contains("decimal")){
							value=formatDecimal(value);
							
						}
												
						table.put(td[i].getColumnName(),value);
					}
				}
			}
			
			return table;
			
		}else{
			return record;
		}
		
		//format fields
		
		
				
	}
	
	public TreeMap<String, String> getEmptyDetails(){
		TreeMap<String, String> table =new TreeMap<String, String>();
		
		if(tableDescription!=null && tableDescription.length>0){
			for (int i=0;i<tableDescription.length;i++){
				table.put(tableDescription[i].getColumnName().toUpperCase(), "");
			}
			
		}
		
		return table;
		
	}
	
	public String safeGuard(String unsafeValue) {
        char quote = new String("'").charAt(0);
        return unsafeValue == null ? null : unsafeValue.replace(quote, '`');
    }
	
	
	
	public static DataSource setupDataSource(String pref,String connectionURL ) {
        //
        // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
		
		ConnectionFactory connectionFactory;
		if(connectionURL.equals("")){
			 connectionFactory = new DriverManagerConnectionFactory(Resources.getSetting(pref+"connection.url"),Resources.getSetting(pref+"user.name"),Resources.getSetting(pref+"password"));
		}else{
			 connectionFactory = new DriverManagerConnectionFactory(connectionURL,null);
		}
       
        
        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
     
        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);
        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        

        return dataSource;
    }

}
