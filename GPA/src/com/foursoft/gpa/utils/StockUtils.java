package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.db.Stock;
import com.foursoft.gpa.reporting.beans.StockOverviewBean;

public class StockUtils {

	private String feedSystem;
	private String clientCode;
	private String period;
	
	private DateUtils du=new DateUtils();
	
	public StockUtils() {
		
	}
	
	public ArrayList<StockOverviewBean> getCollection(){
		ArrayList<StockOverviewBean> collection =new ArrayList<StockOverviewBean>();		
		Protocol.initProgress();
		Protocol.globalStatus=Protocol.STATUS_COLLECTING;
		try {
			//Prepare collection
			Femuti femuti=new Femuti();
			ArrayList<TreeMap<String, String>> details=femuti.readRecordsForPeriod(feedSystem, clientCode, period,Processors.PRELIMINARY);
			TreeMap<String, String> tempDetail=new TreeMap<String, String>();
			StockOverviewBean sob= new StockOverviewBean();
			String previousClientCode="";
			String previousArticle="";
			String previousCurrency="";
			
			if (details != null && details.size() > 0) {
				Protocol.globalTotalRecords=details.size();				
				for (int i = 0; i < details.size(); i++) {
					tempDetail=details.get(i);
					Protocol.globalTotalProcessedRecords=i;
					Disk.sleep(1);
					
					if(!previousClientCode.equals(tempDetail.get(Femuti.DB_NAW_CODE_CM)) ||	
							!previousArticle.equals(tempDetail.get(Femuti.DB_FEMUTI_ART_NO))|| 
							!previousCurrency.equals(tempDetail.get(Femuti.DB_CURR_CODE_FEMUTI))){
						
						sob= getBeginStock(feedSystem, tempDetail.get(Femuti.DB_NAW_CODE_CM), period, tempDetail.get(Femuti.DB_FEMUTI_ART_NO),tempDetail.get(Femuti.DB_CURR_CODE_FEMUTI));
						
						if(sob!=null){
							sob.setUnit(tempDetail.get(Femuti.DB_KIND_CODE_D3));
							collection.add(sob);	
						}
						
						previousClientCode=tempDetail.get(Femuti.DB_NAW_CODE_CM).trim();
						previousArticle=tempDetail.get(Femuti.DB_FEMUTI_ART_NO).trim();
						previousCurrency=tempDetail.get(Femuti.DB_CURR_CODE_FEMUTI).trim();																		
					}
					
					sob= new StockOverviewBean();
					sob.setArticle(tempDetail.get(Femuti.DB_FEMUTI_ART_NO));
					sob.setClient(tempDetail.get(Femuti.DB_NAW_CODE_CM));
					sob.setCurrency(tempDetail.get(Femuti.DB_CURR_CODE_FEMUTI));
					sob.setDateK13(tempDetail.get(Femuti.DB_FEMUTI_TRANS_DATE));
					sob.setDescription(tempDetail.get(Femuti.DB_FEMUTI_ART_DESC));
					sob.setFeedingSystem(tempDetail.get(Femuti.DB_FEED_SYS_FEMUTI));
					sob.setJob(tempDetail.get(Femuti.DB_FEMUTI_JOB_NO)+"/"+tempDetail.get(Femuti.DB_FEMUTI_LINE_NO));
					sob.setPeriod(period);
					sob.setQuantity(Integer.parseInt(tempDetail.get(Femuti.DB_FEMUTI_NO_PCS_D12)));
					sob.setTariff(tempDetail.get(Femuti.DB_FEMUTI_TARIFF_CODE));
					sob.setUnit(tempDetail.get(Femuti.DB_KIND_CODE_D11));
					sob.setValue(Double.parseDouble(tempDetail.get(Femuti.DB_FEMUTI_INV_VALUE)));
					sob.setType(tempDetail.get(Femuti.DB_FEMUTI_TRANS_CODE_K7));
					sob.setDirection(tempDetail.get(Femuti.DB_FEMUTI_IN_OUT_BOUND));
					
					double netWeight=0;					
					try{
						netWeight=Double.parseDouble(tempDetail.get(Femuti.DB_FEMUTI_NT_WGT));
					}catch(Exception e){
						
					}
					sob.setWeight(netWeight);
					
					collection.add(sob);		
				}
			}
						
			previousClientCode="";
			previousArticle="";
			previousCurrency="";
			
			//Read Femutu
			Femutu femutu=new Femutu();
			details=femutu.readRecordsForPeriod(feedSystem, clientCode, period, Processors.OUTBOUND, Processors.PRELIMINARY);
			tempDetail=new TreeMap<String, String>();
			if (details != null && details.size() > 0) {
				Protocol.globalTotalRecords=details.size();		
				for (int i = 0; i < details.size(); i++) {
					tempDetail=details.get(i);
					Protocol.globalTotalProcessedRecords=i;
					Disk.sleep(1);
					if(!previousClientCode.equals(tempDetail.get(Femutu.DB_NAW_CODE_CM)) ||	
							!previousArticle.equals(tempDetail.get(Femutu.DB_FEMUTU_ART_NO))|| 
							!previousCurrency.equals(tempDetail.get(Femutu.DB_CURR_CODE_FEMUTU))){
						
						//check if femuti exists for this article						
						ArrayList<TreeMap<String, String>> femutiRecords=femuti.readArticleRecordsForPeriod(feedSystem, tempDetail.get(Femutu.DB_NAW_CODE_CM), period, tempDetail.get(Femutu.DB_FEMUTU_ART_NO), tempDetail.get(Femutu.DB_CURR_CODE_FEMUTU),Processors.PRELIMINARY);
						if(femutiRecords==null || femutiRecords.size()==0){
							sob= getBeginStock(feedSystem, tempDetail.get(Femutu.DB_NAW_CODE_CM), period, tempDetail.get(Femutu.DB_FEMUTU_ART_NO),tempDetail.get(Femutu.DB_CURR_CODE_FEMUTU));
							
							if(sob!=null){
								collection.add(sob);	
							}						
							previousClientCode=tempDetail.get(Femutu.DB_NAW_CODE_CM);
							previousArticle=tempDetail.get(Femutu.DB_FEMUTU_ART_NO);
							previousCurrency=tempDetail.get(Femutu.DB_CURR_CODE_FEMUTU);																		
						}					
					}
					sob= new StockOverviewBean();
					sob.setArticle(tempDetail.get(Femutu.DB_FEMUTU_ART_NO));
					sob.setClient(tempDetail.get(Femutu.DB_NAW_CODE_CM));
					sob.setCurrency(tempDetail.get(Femutu.DB_CURR_CODE_FEMUTU));
					sob.setDateK13(tempDetail.get(Femutu.DB_FEMUTU_TRANS_DATE));
					sob.setDescription(tempDetail.get(Femutu.DB_FEMUTU_ART_DESC));
					sob.setFeedingSystem(tempDetail.get(Femutu.DB_FEED_SYS_FEMUTU));
					sob.setJob(tempDetail.get(Femutu.DB_FEMUTU_JOB_NO)+"/"+tempDetail.get(Femutu.DB_FEMUTU_LINE_NO));
					sob.setPeriod(period);
					sob.setQuantity(Integer.parseInt(tempDetail.get(Femutu.DB_FEMUTU_NO_OF_PACK))*-1);
					sob.setTariff(tempDetail.get(Femutu.DB_FEMUTU_TARIFF_CODE));
					sob.setUnit(tempDetail.get(Femutu.DB_FEMUTU_PACK_UNIT));
					sob.setValue(Double.parseDouble(tempDetail.get(Femutu.DB_FEMUTU_INV_VAL))*-1);
					sob.setType(tempDetail.get(Femutu.DB_FEMUTU_TRANS_CODE_K7));
					sob.setDirection(tempDetail.get(Femutu.DB_FEMUTU_IN_OUT_BOUND));
					
					double netWeight=0;					
					try{
						netWeight=Double.parseDouble(tempDetail.get(Femutu.DB_FEMUTU_NT_WGT));
					}catch(Exception e){
						
					}
					sob.setWeight(netWeight);
					collection.add(sob);
				}
			}
						

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Protocol.initProgress();
		return collection;
	}
	
	public ArrayList<StockOverviewBean> getSortedCollection(){
		ArrayList<StockOverviewBean> collection =new ArrayList<StockOverviewBean>();
		
		collection=getCollection();
		Collections.sort(collection);  
		
		return collection;
	}
	
	public ArrayList<StockOverviewBean> sortCollection(ArrayList<StockOverviewBean> collection){
		
		Collections.sort(collection);  
		
		return collection;
	}
	
	private StockOverviewBean getBeginStock(String feedSystem, String clientCode, String period, String article,String currency){
		StockOverviewBean sob= null;
		
		String prevPeriod =	du.calculatePreviousPeriod(period);			
		Stock stock=new Stock();
		TreeMap<String, String> stockDetail = new TreeMap<String, String>();
		
		
		stockDetail=stock.getBeginStock(feedSystem, clientCode, prevPeriod, article, currency);
		
		if(stockDetail!=null){
			sob= new StockOverviewBean();
			sob.setArticle(stockDetail.get(Stock.DB_STOCK_ART_NO));
			sob.setClient(stockDetail.get(Stock.DB_NAW_CODE_CM));
			sob.setCurrency(stockDetail.get(Stock.DB_CURR_CODE_STOCK));
			sob.setDateK13("");
			sob.setDescription("");
			sob.setFeedingSystem(stockDetail.get(Stock.DB_FEED_SYS_STOCK));
			sob.setJob("");
			sob.setPeriod(period);
			sob.setQuantity(Integer.parseInt(stockDetail.get(Stock.DB_STOCK_PCS)));
			sob.setTariff(stockDetail.get(Stock.DB_STOCK_TARIFF_CODE));
			sob.setUnit("");
			sob.setValue(Double.parseDouble(stockDetail.get(Stock.DB_STOCK_AMT)));
			sob.setWeight(Double.parseDouble(stockDetail.get(Stock.DB_STOCK_NT_WGT)));
			sob.setType("AA");
			sob.setDirection("");
		}
		
		return sob;
	}

	public String getFeedSystem() {
		return feedSystem;
	}

	public void setFeedSystem(String feedSystem) {
		this.feedSystem = feedSystem;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
