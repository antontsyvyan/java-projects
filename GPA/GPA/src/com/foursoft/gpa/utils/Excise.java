package com.foursoft.gpa.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import com.foursoft.gpa.db.Exc;
import com.foursoft.gpa.db.Excrel;
import com.foursoft.gpa.db.Excsrt;
import com.foursoft.gpa.db.Exctar;

public class Excise {
	
	public final static String EXCISE_MIDDEL_CODE="EXCISE_MIDDEL_CODE";
	public final static String EXCISE_AMOUNT="EXCISE_AMOUNT";
	//Excise groups
	public final static String ALC_FREE="ALC_FREE";
	public final static String BEER="BEER";
	public final static String WINE="WINE";
	public final static String ALC_INTERMED="ALC_INTERMED";
	public final static String ALC_OTHER="ALC_OTHER";
	
	public final static String[] BEVERAGES=new String[]{ALC_FREE,BEER,WINE,ALC_INTERMED,ALC_OTHER};
	
	private String articleNumber;
	private String tariffCode;
	private int quantity;
	private String shipmentDate;
	private boolean excise=false;
	private boolean moreExcises=false;
	
	private String groupId;
	private String exciseId;
	
	private Excrel excrel = new Excrel();
	private String[] middelCodes=new String[]{};
	private Exc exc = new Exc();
	private TreeMap<String, String> excDetail;
	
	private ArrayList<TreeMap<String, String>> exciseAmounts=new ArrayList<TreeMap<String, String>>();
	
	private int current=0;
	
	

	public Excise() {

	}
	
	public Excise(String articleNumber,String tariffCode,int quantity,String shipmentDate){
		
		this.articleNumber=articleNumber;
		this.tariffCode=tariffCode;
		this.quantity=quantity;
		this.shipmentDate=shipmentDate;
		excDetail=exc.getExc(this.articleNumber);		
		
		if(excDetail!=null){
			excise=true;			
			String code= getExciseCode();
			setGroupAndExciseId(code);
			setMiddelCodes(code);
			if(middelCodes!=null && middelCodes.length>0){
				setExcises();
			}
		}
		
	}
	
	public boolean isExcise() {
		return excise;
	}
	
	public boolean hasMoreExcises() {
		return moreExcises;
	}
	
	
	public TreeMap<String, String> nextExcise(){
		TreeMap<String, String> exciseDetail=new TreeMap<String, String>();
		
		if(moreExcises){
			if(current<=exciseAmounts.size()-1){
				exciseDetail=exciseAmounts.get(current);
				current++;
				
				if(current==exciseAmounts.size()){
					moreExcises=false;
				}
			}
		}else{
			exciseDetail=null;
		}
		
		return exciseDetail;
	}
	public String getExtractPercentage(){
		String value="";
		
		if(groupId.equals(BEER)){
			if(excDetail!=null){
				value=excDetail.get(Exc.DB_EXC_EXT_LVL);
			}
		}
		
		return value;
	}
	
	public String getAlcogolStrength(){
		String value="";
		
		if(groupId.equals(WINE) || groupId.equals(ALC_INTERMED) || groupId.equals(ALC_OTHER)){
			if(excDetail!=null){
				value=excDetail.get(Exc.DB_EXC_ALC_PERC);
			}
		}
		return value;
	}
	
	public String getLitersAlcFree(){
		String value="";
		
		if(groupId.equals(ALC_FREE) || groupId.equals(BEER)){
			value=""+getDouble(excDetail.get(Exc.DB_EXC_SKU_HECTO_LT)) * quantity;
		}
		
		
		return value;
	}
	
	public String getLitersOil(){
		String value="";
		
		return value;
	}
	
	public String getLitersPureAlcohol(){
		String value="";
		
		double numberOfHL=getDouble(excDetail.get(Exc.DB_EXC_SKU_HECTO_LT)) * quantity;
		
		if(groupId.equals(ALC_OTHER)){
			double alcPerc=getDouble(excDetail.get(Exc.DB_EXC_ALC_PERC));
			double result=numberOfHL/100*alcPerc;
			BigDecimal bd = new BigDecimal(result);
		    bd = bd.setScale(2, BigDecimal.ROUND_UP);
		    value=bd.toString();
		}
		
		
		return value;
	}
	
	private String getExciseCode(){
		
		String code="00";
		//Retrieve position 21 and 22 of the goods code
		try{
			code= this.tariffCode.substring(20,22);
		}catch(Exception ex){
			
		}
		
		
		return code;
	}
	
	private void setMiddelCodes(String code){
		ArrayList<TreeMap<String, String>> details=excrel.getExcrelRecords(code);
		if(details !=null && details.size()>0){
			middelCodes=new String[details.size()];		
			for (int i = 0; i < details.size(); i++) {
				middelCodes[i]=details.get(i).get(Excrel.DB_EXCREL_MID_CODE);
			}
		}
	}
	
	private void setExcises(){
		
		TreeMap<String, String> detail=new TreeMap<String, String>();
		Exctar exctar=new Exctar();
		for(int i=0; i<middelCodes.length;i++){
			detail=exctar.getExcsrtRecord(exciseId, middelCodes[i], this.shipmentDate);
			if(detail !=null){
				exciseAmounts.add(calculateExcise(detail));
				moreExcises=true;
			}
		}
	}
	
	private void setGroupAndExciseId(String code){
		Excsrt excsrt= new Excsrt();
		TreeMap<String, String> detail=excsrt.getExcsrtRecordsByExciseCode(code);
		if(detail !=null){
			groupId=detail.get(Excsrt.DB_EXCSRT_GRP_ID);
			exciseId=detail.get(Excsrt.DB_EXCSRT_ID);
		}
	}
	
	private TreeMap<String, String> calculateExcise(TreeMap<String, String> exctarDetail){
		
		TreeMap<String, String> retDetails=new TreeMap<String, String>();
		double exciseAmount=0;
		//Excise for beverages
		if(Arrays.asList(BEVERAGES).contains(groupId)){
			//Determine total Hectoliters(Quantity X Hectoliters/SKU)
			double numberOfHL=getDouble(excDetail.get(Exc.DB_EXC_SKU_HECTO_LT)) * quantity;
			double tariffPerHL=getDouble(exctarDetail.get(Exctar.DB_EXCTAR_UNIT_TAR));
			
			if(groupId.equals(ALC_OTHER)){
				double aclPercentage=getDouble(excDetail.get(Exc.DB_EXC_ALC_PERC));
				tariffPerHL=tariffPerHL * aclPercentage;
			}
			exciseAmount=numberOfHL * tariffPerHL;
		}
		
		retDetails.put(EXCISE_MIDDEL_CODE,exctarDetail.get(Exctar.DB_EXCTAR_MID_CODE));
		retDetails.put(EXCISE_AMOUNT,""+exciseAmount);
		
		return retDetails;
	}
	
	private double getDouble(String val){
		double nbr=0;
		
		try{
			nbr =Double.parseDouble(val);	

		}catch(Exception ex){
			
		}
		return nbr;
	}

}
