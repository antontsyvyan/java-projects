package com.foursoft.gpa.utils;

public class CodeVak37 {
	
	private String code="";
	private boolean valid=true;
	private String value;
	
	private String gevraagdeRegeling="  ";
	private String voorafgaandeRegeling="00";
	private String verbijzonderingRegeling="000";

	
	public CodeVak37() {
		
	}
	
	public CodeVak37(String code) {
		this.code=code;
		
		if(code.length()>=2){
			this.gevraagdeRegeling=this.code.substring(0,2);
		}
		
		if(code.length()>=4){
			this.voorafgaandeRegeling=this.code.substring(2,4);
		}
		
		if(code.length()>=6){
			this.verbijzonderingRegeling=this.code.substring(4,7);
		}		
	}
	
	public String getGevraagdeRegeling() {
		return gevraagdeRegeling;
	}



	public void setGevraagdeRegeling(String gevraagdeRegeling) {
		this.gevraagdeRegeling = gevraagdeRegeling;
	}



	public String getVoorafgaandeRegeling() {
		return voorafgaandeRegeling;
	}



	public void setVoorafgaandeRegeling(String voorafgaandeRegeling) {
		this.voorafgaandeRegeling = voorafgaandeRegeling;
	}



	public String getVerbijzonderingRegeling() {
		return verbijzonderingRegeling;
	}



	public void setVerbijzonderingRegeling(String verbijzonderingRegeling) {
		this.verbijzonderingRegeling = verbijzonderingRegeling;
	}



	public String getValue() {
		
		return value;
	}



	public boolean isValid(){
		
		return valid;
	}

}
