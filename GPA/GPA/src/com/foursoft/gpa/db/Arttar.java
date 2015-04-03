package com.foursoft.gpa.db;

import java.util.ArrayList;
import java.util.TreeMap;

import com.foursoft.gpa.Main;
import com.foursoft.gpa.clientfx.model.ArticleModel;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.cache.CacheObject;

public class Arttar extends ConnectionDB{
	
	public static final String DB_ARTTAR = "ARTTAR";
	public static final String DB_ARTICLE_CODE = "ARTICLE_CODE";
	public static final String DB_ACCOUNT_ID = "ACCOUNT_ID";
	public static final String DB_ARTTAR_ORIGIN = "ARTTAR_ORIGIN";
	public static final String DB_ARTTAR_START_DATE = "ARTTAR_START_DATE";
	public static final String DB_TARIFF_CODE = "TARIFF_CODE";
	
	public String getSelectPart(){ 
		String selectPart="SELECT "+DB_ARTICLE_CODE +
				"," +DB_ACCOUNT_ID +
				"," +DB_ARTTAR_ORIGIN +
				"," +DB_ARTTAR_START_DATE +
				"," +DB_TARIFF_CODE +
				" FROM "+getDbName()+DB_ARTTAR;
		return selectPart;
	}

	public TreeMap<String, String> initRecord(){
		
		TreeMap<String, String> arttarDetail= new TreeMap<String, String>();
		
		arttarDetail.put(Arttar.DB_ARTICLE_CODE, "");
		arttarDetail.put(Arttar.DB_ACCOUNT_ID, "");
		arttarDetail.put(Arttar.DB_ARTTAR_ORIGIN, "");
		arttarDetail.put(Arttar.DB_ARTTAR_START_DATE, "");
		arttarDetail.put(Arttar.DB_TARIFF_CODE, "");
	
		return arttarDetail;
	}
	
	public TreeMap<String, String> readArttar(String articleCode, String accountId, String origin, String date){
		
		
		String statement = getSelectPart() + " WHERE "+DB_ARTICLE_CODE+"='"+articleCode+"'";
		statement +=" AND "+DB_ACCOUNT_ID+"='"+accountId+"'";
		statement +=" AND "+DB_ARTTAR_ORIGIN+"='"+origin+"'";
		statement +=" AND "+DB_ARTTAR_START_DATE+"='"+date+"'";
		
		return readTable(statement);
		
	}
	
	
	public ArrayList<ArticleModel> getArticleModelCollection(String accountId , String articleCode){
		
		ArrayList<ArticleModel> modelList=new ArrayList<ArticleModel>();
		
		String whereAnd=" WHERE ";
		String statement = "SELECT * FROM "+getDbName()+DB_ARTTAR;
		
		statement+=" LEFT OUTER JOIN "+Article.DB_ARTICLE +" ON ("+
				Article.DB_ARTICLE+"."+Article.DB_ARTICLE_CODE+"="+DB_ARTTAR+"."+DB_ARTICLE_CODE+" AND "+
				Article.DB_ARTICLE+"."+Article.DB_ACCOUNT_ID+"="+DB_ARTTAR+"."+DB_ACCOUNT_ID + ")";
						
		if(!articleCode.equals("")){
			statement+=whereAnd+DB_ARTTAR+"."+DB_ARTICLE_CODE+" LIKE '%"+articleCode+"%'";
			whereAnd=" AND ";
		}
		
		if(!accountId.equals("")){
			statement +=whereAnd+DB_ARTTAR+"."+DB_ACCOUNT_ID+"='"+accountId+"'";
			whereAnd=" AND ";
		}
		
		ArrayList<TreeMap<String, String>> data =readTableMultiple(statement);
		
		if(data!=null && data.size()>0){
			for(TreeMap<String, String> item: data){
				modelList.add(new ArticleModel(
						item.get(DB_ARTICLE_CODE),
						item.get(Article.DB_ARTICLE_DESCRIPTION),
						item.get(DB_ACCOUNT_ID),
						item.get(DB_TARIFF_CODE), 
						0.00,
						0.00,
						item.get(DB_ARTTAR_START_DATE),
						"",
						"", 
						"",
						item.get(Article.DB_COLLI_CODE))
				);
			}
		}
		
		
		return modelList;
		
	}


	public TreeMap<String, String> getCurrentTariff(String articleCode, String accountId, String origin, String date){
		
		TreeMap<String, String> record=null;
		
		String statement = getSelectPart() + " WHERE " +DB_ARTICLE_CODE+"='"+articleCode+"'";
		statement +=" AND "+DB_ACCOUNT_ID+"='"+accountId+"'";
		statement +=" AND "+DB_ARTTAR_ORIGIN+"='"+origin+"'";
		statement +=" AND "+DB_ARTTAR_START_DATE+"<='"+date+"'";
		statement +=" ORDER BY "+DB_ARTTAR_START_DATE+ " DESC";
		
		ArrayList<TreeMap<String, String>> details = readTableMultiple(statement);
		
		if(details!=null && details.size()>0){
			record=new TreeMap<String, String>();
			record=details.get(0);
		}			
				
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getCurrentTariffCached(String articleCode, String accountId, String origin, String date){
		
		//origin not used
		origin="";
		TreeMap<String, String> record= new TreeMap<String, String>();
		String identifier=DB_ARTTAR+"-"+articleCode+accountId+origin+date;
		
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		if(cob!=null){
			record=(TreeMap<String, String>) cob.getValue();
		}else{
			record=getCurrentTariff(articleCode,accountId,origin,date);
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
		if(record==null){		
			accountId="";
			identifier=DB_ARTTAR+"-"+articleCode+accountId+origin+date;
			cob=Main.cacheInstance.get(identifier);			
			if(cob!=null){	
				record=(TreeMap<String, String>) cob.getValue();
			}else{
				record=getCurrentTariff(articleCode,accountId,origin,date);
				Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
			}
		}
				
		return record;
	}

	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=true;
		
		String accountId=in.get(DB_ACCOUNT_ID);
		String articleCode=in.get(DB_ARTICLE_CODE);
		String origin =in.get(DB_ARTTAR_ORIGIN);
		String date= in.get(DB_ARTTAR_START_DATE);
		TreeMap<String, String> tmpRecord =  readArttar(articleCode,accountId,origin,date);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			retCode=deleteArttarRecord(articleCode,accountId,origin,date);
		}
		
		if(retCode){
			retCode=insertArttar(in);
		}
		
		
		return retCode;
		
	}
	
	public boolean insertArttar(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_ARTTAR+ " VALUES(";
		statement+="'"+formatString(record.get(DB_ARTICLE_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_ID))+"', " ;
		statement+="'"+formatString(record.get(DB_ARTTAR_ORIGIN))+"', ";
		statement+=DateUtils.formatDate(record.get(DB_ARTTAR_START_DATE))+", " ;
		statement+="'"+formatString(record.get(DB_TARIFF_CODE))+"')" ;
	
		return insertUpdateTable(statement);
	}
	
	public boolean deleteArttarRecord(String articleCode, String accountId, String origin, String date) {
		String statement = "DELETE FROM " + DB_ARTTAR + " WHERE " +DB_ARTICLE_CODE+"='"+articleCode+"'";
				statement +=" AND "+DB_ACCOUNT_ID+"='"+accountId+"'";
				statement +=" AND "+DB_ARTTAR_ORIGIN+"='"+origin+"'";
				statement +=" AND "+DB_ARTTAR_START_DATE+"='"+date+"'";

		return insertUpdateTable(statement);
	}
	
	public boolean deleteArticle(String articleCode, String accountId, String origin, String date) {
	String statement = "DELETE "+getDbName()+DB_ARTTAR +","+getDbName()+Article.DB_ARTICLE+" FROM "+getDbName()+DB_ARTTAR;			
			statement+=" JOIN "+Article.DB_ARTICLE +" ON ("+
					Article.DB_ARTICLE+"."+Article.DB_ARTICLE_CODE+"="+DB_ARTTAR+"."+DB_ARTICLE_CODE+" AND "+
					Article.DB_ARTICLE+"."+Article.DB_ACCOUNT_ID+"="+DB_ARTTAR+"."+DB_ACCOUNT_ID + ")";
			
			statement+=" WHERE "+DB_ARTTAR+"."+DB_ARTICLE_CODE+"='"+articleCode+"'";
			statement +=" AND "+DB_ARTTAR+"."+DB_ACCOUNT_ID+"='"+accountId+"'";
			statement +=" AND "+DB_ARTTAR+"."+DB_ARTTAR_ORIGIN+"='"+origin+"'";
			statement +=" AND "+DB_ARTTAR+"."+DB_ARTTAR_START_DATE+"='"+date+"'";
							
		return insertUpdateTable(statement);
	}
}
