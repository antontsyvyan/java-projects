package com.foursoft.gpa.reporting.beans;

public class StockOverviewBean implements Comparable<Object> {

	private String feedingSystem="";
	private String period="";
	private String client="";
	private String currency="";
	private String article="";
	private String type="";
	private int quantity=0;
	private String job="";
	private String tariff="";
	private String dateK13="";
	private String unit="";
	private double value=0;
	private String description="";
	private String direction="";
	private double weight=0;
	
	public StockOverviewBean() {

	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client.trim();
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency.trim();
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job.trim();
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff.trim();
	}
	public String getDateK13() {
		return dateK13;
	}
	public void setDateK13(String dateK13) {
		this.dateK13 = dateK13.trim();
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit.trim();
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description.trim();
	}
	public String getFeedingSystem() {
		return feedingSystem;
	}
	public void setFeedingSystem(String feedingSystem) {
		this.feedingSystem = feedingSystem.trim();
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period.trim();
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article.trim();
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction.trim();
	}

	public String getType() {
		return type;
	}
		
	public void setType(String type) {
		this.type = type.trim();
	}
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Object obj) {

		StockOverviewBean entry = (StockOverviewBean) obj;
		//sort by feed system
		int result = getFeedingSystem().compareTo(entry.getFeedingSystem());
        if(result != 0) {
               return result;
        }
        //sort by client
        result = getClient().compareTo(entry.getClient());
        if(result != 0) {
               return result;
        }		 
		//sort by article
        result = getArticle().compareTo(entry.getArticle());
        if(result != 0) {
               return result;
        }
        //sort by currency
        result = getCurrency().compareTo(entry.getCurrency());
        if(result != 0) {
               return result;
        }
        
        
        return 0;
	}
}
