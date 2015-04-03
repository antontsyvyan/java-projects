package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Main;
import com.foursoft.gpa.clientfx.model.TariffModel;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.TariffCode;
import com.foursoft.gpa.utils.cache.CacheObject;

public class Tariff extends ConnectionDB {

	public static final String DB_TARIFF = "TARIFF";
	public static final String DB_TARIFF_CODE = "TARIFF_CODE";
	public static final String DB_TARIFF_VALID_FROM = "TARIFF_VALID_FROM";
	public static final String DB_TARIFF_VALID_THRU = "TARIFF_VALID_THRU";
	public static final String DB_TARIFF_DESCRIPTION = "TARIFF_DESCRIPTION";
	public static final String DB_VAT_PERC = "VAT_PERC";
	public static final String DB_TARIFF_PERCENTAGE_CUSTOMS = "TARIFF_PERCENTAGE_CUSTOMS";
	public static final String DB_TARIFF_ADDITIONAL_UNITS = "TARIFF_ADDITIONAL_UNITS";
	public static final String DB_COLLI_CODE = "COLLI_CODE";
	public static final String DB_TARIFF_OTHER_EXCISES = "TARIFF_OTHER_EXCISES";
	public static final String DB_TARIFF_DOC_CODE1 = "TARIFF_DOC_CODE1";
	public static final String DB_TARIFF_DOC_CODE2 = "TARIFF_DOC_CODE2";
	public static final String DB_TARIFF_DOC_CODE3 = "TARIFF_DOC_CODE3";
	public static final String DB_TARIFF_DOC_CODE4 = "TARIFF_DOC_CODE4";
	public static final String DB_TARIFF_DOC_CODE5 = "TARIFF_DOC_CODE5";
	public static final String DB_TARIFF_ADD_UNITS = "TARIFF_ADD_UNITS";
	
	public String getSelectPart(){ 
		 String selectPart="SELECT "+DB_TARIFF_CODE +
		"," +DB_TARIFF_VALID_FROM +
		"," +DB_TARIFF_VALID_THRU +
		"," +DB_TARIFF_DESCRIPTION +
		"," +DB_VAT_PERC +
		"," +DB_TARIFF_PERCENTAGE_CUSTOMS +
		"," +DB_TARIFF_ADDITIONAL_UNITS +
		"," +DB_COLLI_CODE +
		"," +DB_TARIFF_OTHER_EXCISES +
		"," +DB_TARIFF_DOC_CODE1 +
		"," +DB_TARIFF_DOC_CODE2 +
		"," +DB_TARIFF_DOC_CODE3 +
		"," +DB_TARIFF_DOC_CODE4 +
		"," +DB_TARIFF_DOC_CODE5 +
		"," +DB_TARIFF_ADD_UNITS +
		" FROM "+getDbName()+DB_TARIFF;
		return selectPart;
	}


	public TreeMap<String, String> initRecord(){
		
		TreeMap<String, String> tariffDetail= new TreeMap<String, String>();
		
		tariffDetail.put(Tariff.DB_TARIFF_CODE, "");
		tariffDetail.put(Tariff.DB_TARIFF_VALID_FROM, "");
		tariffDetail.put(Tariff.DB_TARIFF_VALID_THRU, "");
		tariffDetail.put(Tariff.DB_TARIFF_DESCRIPTION, "");
		tariffDetail.put(Tariff.DB_VAT_PERC, "");
		tariffDetail.put(Tariff.DB_TARIFF_PERCENTAGE_CUSTOMS, "");
		tariffDetail.put(Tariff.DB_TARIFF_ADDITIONAL_UNITS, "");
		tariffDetail.put(Tariff.DB_COLLI_CODE, "");
		tariffDetail.put(Tariff.DB_TARIFF_OTHER_EXCISES, "");
		tariffDetail.put(Tariff.DB_TARIFF_DOC_CODE1, "");
		tariffDetail.put(Tariff.DB_TARIFF_DOC_CODE2, "");
		tariffDetail.put(Tariff.DB_TARIFF_DOC_CODE3, "");
		tariffDetail.put(Tariff.DB_TARIFF_DOC_CODE4, "");
		tariffDetail.put(Tariff.DB_TARIFF_DOC_CODE5, "");
		tariffDetail.put(Tariff.DB_TARIFF_ADD_UNITS, "");
	
		return tariffDetail;
	}
	
	public TreeMap<String, String> getTariff(String tariffCode){
		String statement=getSelectPart() + " WHERE " +DB_TARIFF_CODE+"='"+tariffCode+"'";
		return readTable(statement);
	}
	
	
	public TreeMap<String, String> getTariff(String tariffCode, String date){
		
		String statement=getSelectPart() + " WHERE " +DB_TARIFF_CODE+"='"+tariffCode+"'";
		statement +=" AND "+DB_TARIFF_VALID_FROM+"<='"+date+"'";
		statement +=" AND "+DB_TARIFF_VALID_THRU+">='"+date+"'";
		
		TreeMap<String, String> record=readTable(statement);
		
		if(record==null || record.size()==0){
			statement=getSelectPart() + " WHERE " +DB_TARIFF_CODE+"='"+tariffCode+"'";
			statement +=" AND "+DB_TARIFF_VALID_FROM+"<='"+date+"'";
			statement +=" AND "+DB_TARIFF_VALID_THRU+" is null";
			record=readTable(statement);
		}
		
		
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getTariffCached(String tariffCode, String date){
		
		TreeMap<String, String> record= new TreeMap<String, String>();	
		String identifier=DB_TARIFF+"-"+tariffCode+date;
		
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		if(cob!=null){
			record=(TreeMap<String, String>) cob.getValue();
		}else{
			record=getTariff(tariffCode,date);
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
		
		
					
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getTariffCached(String tariffCode, String lvo, String date){
		
		Tarlvo tarlvo= new Tarlvo();
		TreeMap<String, String> recordTar= new TreeMap<String, String>();
		TreeMap<String, String> recordLvo= tarlvo.getEmptyDetails();
		TreeMap<String, String> record= new TreeMap<String, String>();
		
		String identifier=DB_TARIFF+"-"+tariffCode+date;
		
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		
		boolean exist=false;
		if(cob!=null){
			recordTar=(TreeMap<String, String>) cob.getValue();
			if(recordTar!=null){
				exist=true;
				record=(TreeMap<String, String>) recordTar.clone();
			}else{
				record=null;
			}
			
		}else{
			recordTar=getTariff(tariffCode,date);		
			if(recordTar!=null){
				//read default (where lvo="")
				recordLvo=tarlvo.getTariff(tariffCode, "", date);
				if(recordLvo==null){
					recordLvo= tarlvo.getEmptyDetails();
				}
				record=mapToTarif(recordTar,recordLvo);
				exist=true;
			}			
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
		
		if(exist && !lvo.trim().equals("")){
			identifier=DB_TARIFF+"-"+tariffCode+date+lvo;
			cob=Main.cacheInstance.get(identifier);
			
			if(cob!=null){
				record.clear();
				record=(TreeMap<String, String>) cob.getValue();
			}else{
				recordLvo=tarlvo.getTariff(tariffCode, lvo, date);
				if(recordLvo!=null){
					record.clear();
					record=mapToTarif(recordTar,recordLvo);
				}
				
				Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
			}
														
		}
						
		return record;
	}

	public ArrayList<TariffCode> getTariffList(String arg){
		ArrayList<TariffCode> list = new ArrayList<TariffCode>();
		String statement=getSelectPart();
		
		if(!arg.trim().equals("")){
			statement+=" WHERE "+DB_TARIFF_CODE+" LIKE '%"+arg+"%'";
		}				
		statement+=" ORDER BY "+ DB_TARIFF_CODE;
		
		ArrayList<TreeMap<String, String>> res=readTableMultiple(statement);
		
		if(res!=null && res.size()>0){
			for(TreeMap<String, String> item:res){
				list.add(new TariffCode(item.get(DB_TARIFF_CODE)));
			}
		}
				
		return list;
	}
	
	
	public ArrayList<TariffModel> getTariffModelCollection(String arg){
		
		ArrayList<TariffModel> modelList=new ArrayList<TariffModel>();
	
		String statement=getSelectPart();
		
		if(!arg.trim().equals("")){
			statement+=" WHERE "+DB_TARIFF_CODE+" LIKE '%"+arg+"%'";
		}				
		statement+=" ORDER BY "+ DB_TARIFF_CODE;
		
		ArrayList<TreeMap<String, String>> res=readTableMultiple(statement);
		
		if(res!=null && res.size()>0){
			for(TreeMap<String, String> item:res){
				modelList.add(new TariffModel(
						item.get(DB_TARIFF_CODE),
						item.get(DB_TARIFF_DESCRIPTION),
						item.get(DB_VAT_PERC),
						item.get(DB_TARIFF_VALID_FROM),
						item.get(DB_TARIFF_PERCENTAGE_CUSTOMS),
						item.get(DB_TARIFF_ADD_UNITS),
						item.get(DB_TARIFF_DOC_CODE1),
						item.get(DB_TARIFF_DOC_CODE2),
						item.get(DB_TARIFF_DOC_CODE3),
						item.get(DB_TARIFF_DOC_CODE4),
						item.get(DB_TARIFF_DOC_CODE5)
				));
			}
		}
		
		return modelList;
	}
	
	public boolean insertTariff(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_TARIFF+ " VALUES(";
		statement+="'"+formatString(record.get(DB_TARIFF_CODE))+"', " ;
		statement+=DateUtils.formatDate(record.get(DB_TARIFF_VALID_FROM))+"," ;
		statement+=DateUtils.formatDate(record.get(DB_TARIFF_VALID_THRU))+"," ;
		statement+="'"+formatString(record.get(DB_TARIFF_DESCRIPTION))+"', " ;
		statement+=formatDecimal(record.get(DB_TARIFF_PERCENTAGE_CUSTOMS))+"," ;
		statement+="'"+formatString(record.get(DB_COLLI_CODE))+"'," ;
		statement+="'"+formatString(record.get(DB_TARIFF_OTHER_EXCISES))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_DOC_CODE1))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_DOC_CODE2))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_DOC_CODE3))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_DOC_CODE4))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_DOC_CODE5))+"', " ;
		statement+="'"+formatString(record.get(DB_TARIFF_ADD_UNITS))+"')" ;


		return insertUpdateTable(statement);
	}
	
	public TreeMap<String, String> mapToTarif(TreeMap<String, String> inTariff, TreeMap<String, String> inLvo) {
				
		TreeMap<String, String> out=null;
		if (inLvo != null) {
			out= new TreeMap<String, String>();
			
			out.put(Tariff.DB_TARIFF_CODE, inTariff.get(Tariff.DB_TARIFF_CODE));
			out.put(Tarlvo.DB_TARLVO_ORIGIN, inLvo.get(Tarlvo.DB_TARLVO_ORIGIN));
			out.put(Tariff.DB_TARIFF_DESCRIPTION, inTariff.get(Tariff.DB_TARIFF_DESCRIPTION));
			out.put(Tariff.DB_VAT_PERC, inTariff.get(Tariff.DB_VAT_PERC));
			out.put(Tariff.DB_TARIFF_ADDITIONAL_UNITS, inTariff.get(Tariff.DB_VAT_PERC));
			out.put(Tariff.DB_TARIFF_ADD_UNITS,inTariff.get(Tariff.DB_TARIFF_ADD_UNITS));
			out.put(Tariff.DB_COLLI_CODE, inTariff.get(Tariff.DB_COLLI_CODE));
			out.put(Tariff.DB_TARIFF_OTHER_EXCISES, inTariff.get(Tariff.DB_COLLI_CODE));
			
			out.put(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS, inLvo.get(Tarlvo.DB_TARLVO_PERCENTAGE_CUSTOMS));
			out.put(Tarlvo.DB_TARLVO_DOC_CODE1, inLvo.get(Tarlvo.DB_TARLVO_DOC_CODE1));
			out.put(Tarlvo.DB_TARLVO_DOC_CODE2, inLvo.get(Tarlvo.DB_TARLVO_DOC_CODE2));
			out.put(Tarlvo.DB_TARLVO_DOC_CODE3, inLvo.get(Tarlvo.DB_TARLVO_DOC_CODE3));
			out.put(Tarlvo.DB_TARLVO_DOC_CODE4, inLvo.get(Tarlvo.DB_TARLVO_DOC_CODE4));
			out.put(Tarlvo.DB_TARLVO_DOC_CODE5, inLvo.get(Tarlvo.DB_TARLVO_DOC_CODE5));
			
		}		
		return out;
	}
}
