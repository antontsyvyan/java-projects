package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.clientfx.model.IntlogModel;
import com.foursoft.gpa.utils.DateUtils;

public class Intlog extends ConnectionDB {
	
	public static final String DB_INTLOG = "INTLOG";
	public static final String DB_INTLOG_INT_NAME = "INTLOG_INT_NAME";
	public static final String DB_INTLOG_SEQUENCE = "INTLOG_SEQUENCE";
	public static final String DB_INTLOG_FILE_NAME = "INTLOG_FILE_NAME";
	public static final String DB_INTLOG_PROC_DATE = "INTLOG_PROC_DATE";
	public static final String DB_INTLOG_PROC_TIME = "INTLOG_PROC_TIME";
	public static final String DB_INTLOG_STAT = "INTLOG_STAT";
	public static final String DB_INTLOG_ERR_MSG = "INTLOG_ERR_MSG";
	

	private static Intlog intlog = new Intlog();

	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_INTLOG_INT_NAME +
			"," +DB_INTLOG_SEQUENCE +
			"," +DB_INTLOG_FILE_NAME +
			"," +DB_INTLOG_PROC_DATE +
			"," +DB_INTLOG_PROC_TIME +
			"," +DB_INTLOG_STAT +
			"," +DB_INTLOG_ERR_MSG +
			" FROM "+getDbName()+DB_INTLOG;
	return selectPart;
	}
	
	public ArrayList<TreeMap<String, String>> readAllRecords(boolean asc){
		String order="ASC";
		if(!asc){
			order="DESC";
		}
		String statement=getSelectPart()+" ORDER BY "+DB_INTLOG_PROC_DATE+" "+order+","+DB_INTLOG_PROC_TIME+" "+order;
		
		return readTableMultiple(statement);
	}

	public ArrayList<IntlogModel>getIntlogModelCollection(){
		
		ArrayList<IntlogModel> modelList=new ArrayList<IntlogModel>();
		ArrayList<TreeMap<String, String>> data=readAllRecords(false);
		
		if(data!=null && data.size()>0){
			for (int i = 0; i < data.size(); i++) {
				TreeMap<String, String> tmp=data.get(i);				
				modelList.add(new IntlogModel(
						tmp.get(Intlog.DB_INTLOG_INT_NAME),
						tmp.get(Intlog.DB_INTLOG_SEQUENCE),
						tmp.get(Intlog.DB_INTLOG_FILE_NAME),
						tmp.get(Intlog.DB_INTLOG_PROC_DATE),
						tmp.get(Intlog.DB_INTLOG_PROC_TIME),
						tmp.get(Intlog.DB_INTLOG_STAT),
						tmp.get(Intlog.DB_INTLOG_ERR_MSG ),
						tmp)
				);
			}
		}
		
		return modelList;
		
	}
	

	public boolean insertIntlog(TreeMap<String, String> record){

		String db=getDbName();
		String interfaceName=record.get(DB_INTLOG_INT_NAME);
		String statement="INSERT INTO "+getDbName()+DB_INTLOG+ " VALUES(";
		statement+="'"+interfaceName+"', " ;
		statement+=getId(db,interfaceName)+"," ;
		statement+="'"+record.get(DB_INTLOG_FILE_NAME)+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_INTLOG_PROC_DATE))+"," ;
		statement+=record.get(DB_INTLOG_PROC_TIME)+", ";
		statement+="'"+record.get(DB_INTLOG_STAT)+"'," ;
		statement+="'"+formatString(record.get(DB_INTLOG_ERR_MSG))+"')" ;


		return insertUpdateTable(statement);
	}
	
	private static synchronized int getId(String db, String interfaceName){
		
		int id=0;
		String LAST_ID="LAST_ID";
		
		String statement="select MAX("+DB_INTLOG_SEQUENCE+") "+LAST_ID+" from " +db+DB_INTLOG;
		statement+=" WHERE "+DB_INTLOG_INT_NAME+"='"+interfaceName+"'";
		TreeMap<String, String> record = intlog.readTable(statement);
		
		if(record!=null && record.size()>0){
			if(record.get(LAST_ID)!=null && !record.get(LAST_ID).trim().equals("")){
				id=Integer.parseInt(record.get(LAST_ID));
				id++;
			}
		}
		
		return id;
		
	}
}
