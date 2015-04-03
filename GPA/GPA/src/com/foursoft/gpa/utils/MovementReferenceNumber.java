package com.foursoft.gpa.utils;

import java.util.TreeMap;

public class MovementReferenceNumber {
	
	private String error;
	private String mrn;
	private String declarationDate;
	private String number;
	private String officeCode;
	
	private DateUtils du= new DateUtils();
	
	public MovementReferenceNumber() {
	}
	
	public MovementReferenceNumber(String mrn) {
		this.mrn=mrn;
	}
	
	private static TreeMap<String,Integer> iso6346;
	static {
		iso6346 = new TreeMap<String,Integer>();
		int i=0;
		//Digits
		for(i=0;i<10;i++){
			iso6346.put(""+i, i);
		}
		//Letters
		char startUpper = 'A';   
		int numOfLoops = 26; 
		for (int j = 0; j < numOfLoops; j++) {  
			
			if(i==11 || i==22 || i==33){
				i++;
			}
			iso6346.put(String.valueOf(startUpper), i++);
			startUpper++;
		}    
	}
	
	public boolean isValid(){
		
	
		if(mrn==null || mrn.length()==0){
			mrn=du.getYear(declarationDate.trim())+officeCode.trim()+number.trim();
		}
		
		//Check length
		if(mrn.length()!=18){
			error="The length of MRN number is not equals to 18 positions";
			return false;
		}
		//Check digit should be a number;
		int checkDigit=0;
		
		try {
			checkDigit=Integer.parseInt(mrn.substring(17,18));
		} catch (NumberFormatException e) {
			error="Check digit is not a number";
			return false;
		}
		
		//is control digit correct	
		if(checkDigit!=calculateCheckDigit(mrn.substring(0,17))){
			error="Check digit is not correct";
			return false;
		}
		
		return true;
	}
	
	private int calculateCheckDigit(String rawMrn){	
		
		/*
		 * The algorithm for calculating the check digit character for the MRN is based on the ISO 6346 algorithm. 
		 * Each character of the reference numbers is given a numeric value (1-1,2-2...,9-9,A-10,B-11,C-12...,Z-38). 
		   Each individual number is then multiplied by a different factor. The factors are in the range 2 power 0  to 2 power 16  
		   producing 17 sub-totals for the MRN.
		   These individual sub-totals are totaled and that result is then divided by 11. 
		   The remainder of the calculation is then check digit.(when reminder=10 then check digit=0) 
		 */
		
		int total=0;
		for(int i=0;i<rawMrn.length();i++){			
			total+=Math.pow(2,i)*iso6346.get(String.valueOf(rawMrn.charAt(i)).toUpperCase());
		}		
		int reminder=total%11;
		
		return (reminder==10)?0:reminder;
	}
	

	public String getError() {
		return error;
	}

	public void setDeclarationDate(String declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

}
