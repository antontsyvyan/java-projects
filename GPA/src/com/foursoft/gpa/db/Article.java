package com.foursoft.gpa.db;

import java.util.TreeMap;

import com.foursoft.gpa.Main;
import com.foursoft.gpa.utils.cache.CacheObject;

public class Article extends ConnectionDB {
	
	public static final String DB_ARTICLE = "ARTICLE";
	public static final String DB_ARTICLE_CODE = "ARTICLE_CODE";
	public static final String DB_ACCOUNT_ID = "ACCOUNT_ID";
	public static final String DB_ARTICLE_DESCRIPTION = "ARTICLE_DESCRIPTION";
	public static final String DB_ARTICLE_SEARCH_NAME = "ARTICLE_SEARCH_NAME";
	public static final String DB_ARTICLE_NET_WEIGHT = "ARTICLE_NET_WEIGHT";
	public static final String DB_COLLI_CODE = "COLLI_CODE";
	
	public String getSelectPart(){ 
		
		 String selectPart="SELECT "+DB_ARTICLE_CODE +
		"," +DB_ACCOUNT_ID +
		"," +DB_ARTICLE_DESCRIPTION +
		"," +DB_ARTICLE_SEARCH_NAME +
		"," +DB_ARTICLE_NET_WEIGHT +
		"," +DB_COLLI_CODE +
		" FROM "+getDbName()+DB_ARTICLE;
		 
		return selectPart;
	}

	public TreeMap<String, String> initRecord(){
		
		TreeMap<String, String> articleDetail= new TreeMap<String, String>();
		
		articleDetail.put(Article.DB_ARTICLE_CODE, "");
		articleDetail.put(Article.DB_ACCOUNT_ID, "");
		articleDetail.put(Article.DB_ARTICLE_DESCRIPTION, "");
		articleDetail.put(Article.DB_ARTICLE_SEARCH_NAME, "");
		articleDetail.put(Article.DB_ARTICLE_NET_WEIGHT, "");
		articleDetail.put(Article.DB_COLLI_CODE, "");
	
		return articleDetail;
	}
	
	
	public TreeMap<String, String> readArticle(String articleCode, String accountId){
		
		
		String statement = getSelectPart() + " WHERE "+DB_ARTICLE_CODE+"='"+articleCode+"'";
		statement+= " AND "+DB_ACCOUNT_ID+"='"+accountId+"'";
		
		return readTable(statement);
		
	}
	
	public TreeMap<String, String> getArticleData(String articleCode, String accountId){
		
		TreeMap<String, String> articleRecord=readArticle(articleCode, accountId);
		
		if(articleRecord==null){
			articleRecord=readArticle(articleCode,"");
		}		
		return articleRecord;		
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, String> getArticleDataCached(String articleCode, String accountId){
		
		TreeMap<String, String> record= new TreeMap<String, String>();
		String identifier=DB_ARTICLE+"-"+articleCode+accountId;
		
		CacheObject<Object> cob=Main.cacheInstance.get(identifier);
		if(cob!=null){
			record=(TreeMap<String, String>) cob.getValue();
		}else{
			record=readArticle(articleCode, accountId);
			Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
		}
		
		if(record==null){
			identifier=DB_ARTICLE+"-"+articleCode;
			cob=Main.cacheInstance.get(identifier);
			if(cob!=null){
				record=(TreeMap<String, String>) cob.getValue();
			}else{
				record=readArticle(articleCode,"");
				Main.cacheInstance.put(identifier, new CacheObject<Object>(record));
			}
		}		
		return record;		
	}
	
	public boolean insertUpdate(TreeMap<String, String> in){
		boolean retCode=true;
		
		String accountId=in.get(DB_ACCOUNT_ID);
		String articleCode=in.get(DB_ARTICLE_CODE);
		TreeMap<String, String> tmpRecord =  readArticle(articleCode,accountId);
		if(tmpRecord!=null && tmpRecord.size()>0){
			//delete record
			retCode=deleteArticleRecord(articleCode, accountId);
		}
		
		if(retCode){
			retCode=insertArticle(in);
		}
		
		
		return retCode;
		
	}
	

	public boolean insertArticle(TreeMap<String, String> record){

		String statement="INSERT INTO "+getDbName()+DB_ARTICLE+ " VALUES(";
		statement+="'"+formatString(record.get(DB_ARTICLE_CODE))+"', " ;
		statement+="'"+formatString(record.get(DB_ACCOUNT_ID))+"', " ;
		statement+="'"+formatString(record.get(DB_ARTICLE_DESCRIPTION))+"', " ;
		statement+="'"+formatString(record.get(DB_ARTICLE_SEARCH_NAME))+"', " ;
		statement+=formatDecimal(record.get(DB_ARTICLE_NET_WEIGHT))+"," ;
		statement+="'"+formatString(record.get(DB_COLLI_CODE))+"')" ;
	
		return insertUpdateTable(statement);
	}
	
	public boolean deleteArticleRecord(String articleCode, String accountId ) {
		String statement = "DELETE FROM " + DB_ARTICLE + " WHERE  "
				+ DB_ACCOUNT_ID + "= '" + accountId + "' AND "
				+ DB_ARTICLE_CODE + "='" + articleCode + "'";

		return insertUpdateTable(statement);
	}

}
