package com.foursoft.gpa.utils;

import java.math.BigDecimal;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;

public class GpaCalculator {

	private TreeMap<String, String> detail=new TreeMap<String, String>();

	public void setDetail(TreeMap<String, String> detail) {
		this.detail = detail;
	}
	
	
	public double getTaxValue(){
		
		return getTaxValue(detail.get(Disk.BLOCK_E25),
				detail.get(Disk.BLOCK_E27),
				detail.get(Disk.BLOCK_E28),
				detail.get(Disk.BLOCK_E29),
				detail.get(Disk.BLOCK_E30));
				
				
	}
	
	public double getCustomsDutyAmout(){
		return getCustomsDutyAmout(detail.get(Disk.BLOCK_E31),
				detail.get(Disk.BLOCK_E32),
				detail.get(Disk.BLOCK_E33),
			    detail.get(Disk.BLOCK_E34));
	}
	
	public static double getTaxValue(String parmInvoiceValue, String parmRate, String parmFreightAmount, String parmInsuranceAmount,String parmOtherAmount){
		
		double invoiceValue=Double.parseDouble(parmInvoiceValue.trim());
		double rate=Double.parseDouble(parmRate.trim());
		
		double freightAmount=Double.parseDouble(parmFreightAmount.trim());
		double insuranceAmount=Double.parseDouble(parmInsuranceAmount.trim());
		double otherAmount=Double.parseDouble(parmOtherAmount.trim());
		
		return getTaxValue(invoiceValue, rate, freightAmount, insuranceAmount, otherAmount);
	}
	
	public static double getCustomsDutyAmout(String parmCustomsDutyValue, String parmDutyRate, String parmReliefAmount, String parmCustomsSpecialDutyValue ){
	
		
		double customsDutyValue=Double.parseDouble(parmCustomsDutyValue);
		double dutyRate=Double.parseDouble(parmDutyRate);
		double reliefAmount=Double.parseDouble(parmReliefAmount);
		double customsSpecialDutyValue=Double.parseDouble(parmCustomsSpecialDutyValue);
		
		return getCustomsDutyAmout(customsDutyValue,dutyRate,reliefAmount,customsSpecialDutyValue);
	}
	
	private static double getTaxValue(double invoiceValue, double rate, double freightAmount, double insuranceAmount,double otherAmount){
		
		double value=0;
		/*
		 * De formule: (factuurwaarde (in v.v.)) E.25 * (koers) E.27, de uitkomst rekenkundig afgerond, 
		 * en vervolgens vermeerderd met de kosten, vermeld in E.28, E.29 en E.30.
		 */	
		value=round(invoiceValue*rate,2);		
		
		value+=freightAmount+insuranceAmount+otherAmount;
		//value=Math.round(value*100.0)/100.0;
		value=round(value,2);	
		
		return value;
	}
	
	
	
	public static double getCustomsDutyAmout(double customsDutyValue, double dutyRate, double reliefAmount, double customsSpecialDutyValue ){
		
		/*
		 *  De formule: (douanewaarde) E.31 * (percentage) E.32, de uitkomst rekenkundig afgerond, 
		 *  en vervolgens verminderd met het bedrag vrijstelling van E.33 en vermeerderd met het bedrag 
		 *  aan specifiek douanerecht van E.34.
		 */
		double value=0;
		
		
		value=customsDutyValue*dutyRate/100;	
		value=round(value,2);

		value=value-reliefAmount+customsSpecialDutyValue;		
		value=round(value,2);
			
		return value;
	}
	
	public static double round(double value, int scale){
		BigDecimal bdin, bdout;
		
		bdin=new BigDecimal(Double.toString(value));
		bdout=bdin.setScale(scale,BigDecimal.ROUND_HALF_UP);
		value=bdout.doubleValue();
		
		return value;
	}
	
	public static double round(String value, int scale){
		
		double doubleValue=0;
		try{
			doubleValue=Double.parseDouble(value);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return round(doubleValue, scale);
	}
}

