package com.foursoft.gpa.utils;

public class TariffCode {

	private String tariffCode;
	private String fullTariffCode;
	private int chapter=0;
	private int hsHeading=0;
	private int hsSubHeading=0;
	private int cnSubHeading=0;
	private int taricSubHeading=0;
	
	private String additionalTaricCode1="0000";
	private String additionalTaricCode2="0000";
	private String additionalTaricCode3="00";
	private String exciseCode="00";
	private boolean valid=true;
	
	public TariffCode(String tariffCode) {
		this.tariffCode=tariffCode.trim();
		setChapter();
		setHsHeading();
		setHsSubHeading();
		setCnSubHeading();
		setTaricSubHeading();
		setAdditionalTaricCode1();
		setAdditionalTaricCode2();
		setAdditionalTaricCode3();
		setExciseCode();
		
		if(valid){
			setFullTariffCode();
			valid=isValidCode();
		}
			
	}
	
	
	public boolean isValid(){
		return valid;
	}
	
	private boolean isValidCode(){
		String regexp="^[0-9]{10}[a-zA-Z0-9]{12}";
		
		if(!fullTariffCode.matches(regexp)){
			return false;
		}
		
		return true;
	}
	
	private void setChapter(){
		
		if(tariffCode!=null && tariffCode.length()>=2){
			try{
				chapter=Integer.parseInt(tariffCode.substring(0, 2));
			}catch(NumberFormatException ex){
				valid=false;
			}
		}
	}
	
	private void setHsHeading(){
		if(tariffCode!=null && tariffCode.length()>=4){
			try{
				hsHeading=Integer.parseInt(tariffCode.substring(2, 4));
			}catch(NumberFormatException ex){
				valid=false;
			}
		}
	}
	
	private void setHsSubHeading(){
		if(tariffCode!=null && tariffCode.length()>=6){
			try{
				hsSubHeading=Integer.parseInt(tariffCode.substring(4, 6));
			}catch(NumberFormatException ex){
				valid=false;
			}
		}
	}
	
	private void setCnSubHeading(){
		if(tariffCode!=null && tariffCode.length()>=8){
			try{
				cnSubHeading=Integer.parseInt(tariffCode.substring(6, 8));
			}catch(NumberFormatException ex){
				valid=false;
			}
		}
	}
	
	private void setTaricSubHeading(){
		if(tariffCode!=null && tariffCode.length()>=10){
			try{
				taricSubHeading=Integer.parseInt(tariffCode.substring(8, 10));
			}catch(NumberFormatException ex){
				valid=false;
			}
		}
	}
	
	private void setAdditionalTaricCode1(){
		if(tariffCode!=null && tariffCode.length()>=14){
			try{
				additionalTaricCode1=tariffCode.substring(10, 14);
			}catch(Exception ex){
				
			}
		}
	}
	
	private void setAdditionalTaricCode2(){
		if(tariffCode!=null && tariffCode.length()>=18){
			try{
				additionalTaricCode2=tariffCode.substring(14, 18);
			}catch(Exception ex){
				
			}
		}
	}
	
	private void setAdditionalTaricCode3(){
		if(tariffCode!=null && tariffCode.length()>=20){
			try{
				additionalTaricCode3=tariffCode.substring(18, 20);
			}catch(Exception ex){
				
			}
		}
	}
	
	private void setExciseCode(){
		if(tariffCode!=null && tariffCode.length()>=22){
			try{
				exciseCode=tariffCode.substring(20, 22);
			}catch(Exception ex){
				
			}
		}
	}
	
	private void setFullTariffCode(){
		fullTariffCode=constructTariffCode(false);
	}
	
	private String constructTariffCode(boolean friendly){
		String separator="";
		if(friendly){
			separator=" ";
		}
		String code=String.format("%02d",chapter)+
				String.format("%02d",hsHeading)+
				String.format("%02d",hsSubHeading)+
				separator+
				String.format("%02d",cnSubHeading)+
				separator+
				String.format("%02d",taricSubHeading)+
				separator+
				additionalTaricCode1+
				separator+
				additionalTaricCode2+
				separator+
				additionalTaricCode3+
				separator+
				exciseCode;
		
		return code;
	}
	
	public String getFullTariffCode() {
		return fullTariffCode;
	}
	
	public String getFriendlyTariffCode() {
		return constructTariffCode(true);
	}

	public int getChapter() {
		return chapter;
	}

	
	public String getTariffCode() {
		return tariffCode;
	}
	
	public String getExciseCode(){
		return exciseCode;
	}

	
	
}
