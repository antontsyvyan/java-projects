package com.foursoft.gpa.utils;

import java.util.TreeMap;

public class VatNumber {

	private String number;
	private String countryCode;
	private String errorMessage;
	
	private static TreeMap<String,String[]> VAT_PATTERNS;
	static { //VAT identification number structure 
		/*
		 *  http://ec.europa.eu/taxation_customs/vies/faqvies.do#item11
		 *  
			Member 			State 				Structure Format* 
			AT-Austria 		ATU99999999 		1 block of 9 characters 
			BE-Belgium 		BE0999999999 		1 block of 10 digits 
			BG-Bulgaria 	BG999999999 or
							BG9999999999 		1 block of 9 digits or1 block of 10 digits 
			CY-Cyprus 		CY99999999L 		1 block of 9 characters 
			CZ-Czech Rep.	CZ99999999 or
							CZ999999999 or
							CZ9999999999		1 block of either 8, 9 or 10 digits 
			DE-Germany 		DE999999999 		1 block of 9 digits 
			DK-Denmark 		DK99 99 99 99 		4 blocks of 2 digits 
			EE-Estonia 		EE999999999 		1 block of 9 digits 
			EL-Greece 		EL999999999 		1 block of 9 digits 
			ES-Spain 		ESX9999999X(4) 		1 block of 9 characters 
			FI-Finland 		FI99999999 			1 block of 8 digits 
			FR-France  		FRXX 999999999 		1 block of 2 characters, 1 block of 9 digits 
			GB-Unied K.		GB999 9999 99 or
							GB999 9999 99 999(5) or
							GBGD999(6) or
							GBHA999(7) 			1 block of 3 digits, 1 block of 4 digits and 1 block of 2 digits; or the above followed by a block of 3 digits; or 1 block of 5 characters 
			HR-Croatia 		HR99999999999 		1 block of 11 digits 
			HU-Hungary 		HU99999999 			1 block of 8 digits 
			IE-Ireland 		IE9S99999L
							IE9999999WI 		1 block of 8 characters or 1 block of 9 characters 
			IT-Italy 		IT99999999999 		1 block of 11 digits 
			LT-Lithuania 	LT999999999 or
							LT999999999999 		1 block of 9 digits, or 1 block of 12 digits 
			LU-Luxembourg 	LU99999999 			1 block of 8 digits 
			LV-Latvia 		LV99999999999 		1 block of 11 digits 
			MT-Malta 		MT99999999 			1 block of 8 digits 
			NL-Netherlands 	NL999999999B99(8)   1 block of 12 characters 
			PL-Poland 		PL9999999999 		1 block of 10 digits 
			PT-Portugal 	PT999999999 		1 block of 9 digits 
			RO-Romania 		RO999999999 		1 block of minimum 2 digits and maximum 10 digits 
			SE-Sweden 		SE999999999999 		1 block of 12 digits 
			SI-Slovenia 	SI99999999 			1 block of 8 digits 
			SK-Slovakia 	SK9999999999 		1 block of 10 digits 
			
			
			Remarks:
			*: Format excludes 2 letter alpha prefix
			9: A digit
			X: A letter or a digit
			S: A letter; a digit; "+" or "*"
			L: A letter 
			
			
			Notes:
			1: The 1st position following the prefix is always "U".
			2: The first digit following the prefix is always zero ('0').
			3: The (new) 10-digit format is the result of adding a leading zero to the (old) 9-digit format.
			4: The first and last characters may be alpha or numeric; but they may not both be numeric.
			5: Identifies branch traders.
			6: Identifies Government Departments.
			7: Identifies Health Authorities.
			8: The 10th position following the prefix is always "B". 
		 */
		VAT_PATTERNS=new TreeMap<String,String[]>();
		
		VAT_PATTERNS.put("AT", new String[]{"^ATU[0-9]{8}$"});
		VAT_PATTERNS.put("BE", new String[]{"^BE0[0-9]{9}$"});
		VAT_PATTERNS.put("BG", new String[]{"^BG[0-9]{9,10}$"});
		VAT_PATTERNS.put("CY", new String[]{"^CY[0-9]{8}[a-zA-Z]{1}$"});
		VAT_PATTERNS.put("CZ", new String[]{"^CZ[0-9]{8,10}$"});
		VAT_PATTERNS.put("DE", new String[]{"^DE[0-9]{9}$"});
		VAT_PATTERNS.put("DK", new String[]{"^DK[0-9]{8}$"});
		VAT_PATTERNS.put("EE", new String[]{"^EE[0-9]{9}$"});
		VAT_PATTERNS.put("EL", new String[]{"^EL[0-9]{9}$"});
		VAT_PATTERNS.put("ES", new String[]{"^ES[a-zA-Z0-9]{1}[0-9]{7}[a-zA-Z0-9]{1}$"});
		VAT_PATTERNS.put("FI", new String[]{"^FI[0-9]{8}$"});
		VAT_PATTERNS.put("FR", new String[]{"^FR[a-zA-Z0-9]{2}[0-9]{9}$"});
		VAT_PATTERNS.put("GB", new String[]{"^GB[0-9]{9}$","^GB[0-9]{12}$","^GBGD[0-9]{3}$","^GBHA[0-9]{3}$"});
		VAT_PATTERNS.put("HR", new String[]{"^HR[0-9]{11}$"});
		VAT_PATTERNS.put("HU", new String[]{"^HU[0-9]{8}$"});
		VAT_PATTERNS.put("IE", new String[]{"^IE[0-9]{1}S[0-9]{5}[a-zA-Z]{1}$","^IE[0-9]{7}WI$"});
		VAT_PATTERNS.put("IT", new String[]{"^IT[0-9]{11}$"});
		VAT_PATTERNS.put("LT", new String[]{"^LT[0-9]{9}$","^LT[0-9]{12}$"});
		VAT_PATTERNS.put("LU", new String[]{"^LU[0-9]{8}$"});
		VAT_PATTERNS.put("LV", new String[]{"^LV[0-9]{11}$"});
		VAT_PATTERNS.put("MT", new String[]{"^MT[0-9]{8}$"});
		VAT_PATTERNS.put("NL", new String[]{"^NL[0-9]{11}$"}); //Bij een Nederlands BTW- identificatienummer de letter B weglaten.
		VAT_PATTERNS.put("PL", new String[]{"^PL[0-9]{10}$"});
		VAT_PATTERNS.put("PT", new String[]{"^PL[0-9]{9}$"});
		VAT_PATTERNS.put("RO", new String[]{"^RO[0-9]{2,10}$"});
		VAT_PATTERNS.put("SE", new String[]{"^SE[0-9]{12}$"});		
		VAT_PATTERNS.put("SI", new String[]{"^SI[0-9]{8}$"});
		VAT_PATTERNS.put("SK", new String[]{"^SK[0-9]{10}$"});
	
	}
	
	public VatNumber(String number) {
		this.number=number;
	}
	
	
	public boolean isValid(){
		boolean retCode=false;
		
		if(this.number==null || this.number.length()==0){
			errorMessage="VAT is empty";
			return false;
		}
				
		if(countryCode==null || countryCode.length()==0){
			if(!setCountyCode()){
				errorMessage="Can't detemine country code";
				return false;
			}
		}
		
		String [] patterns=VAT_PATTERNS.get(countryCode);
		
		if(patterns!=null && patterns.length>0){
			this.number=number.trim().toUpperCase().replace(" ", "");
			String patternsString="";
			for(int i=0;i<patterns.length;i++){
				patternsString+=" "+patterns[i];
				if(this.number.matches(patterns[i])){					
					//If at least one match found - exit loop and return true
					retCode=true;
					break;
				}				
			}						
			errorMessage="VAT number "+this.number+" doesn't match one of the patterns \""+patternsString+"\" for country "+this.countryCode;
		}else{
			errorMessage="Can't validate VAT number. Validation rules are not available for country "+this.countryCode+".\r\n http://ec.europa.eu/taxation_customs/vies/faq.html#item_11";
			return false;
		}
						
		return retCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setCountyCode(String countryCode){
		this.countryCode=countryCode;
	}
	
	private boolean setCountyCode(){
		
		if(this.number.length()<3){
			return false;
		}else{
			this.countryCode=this.number.substring(0, 2);
		}
		
		return true;
		
			
	}

}
