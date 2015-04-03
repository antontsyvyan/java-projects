package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.foursoft.gpa.db.Cdbk;


public class Codetable {
	
	public static final String GENERAL="GEN";
	public static final String INVOER="INV";
	public static final String UITVOER="UIT";
	
	//Tables
	public static final String COUNTRY_CODES="S01";
	public static final String CURRENCY_CODES="S10";
	public static final String INCOTERMS_CODES="A14";
	public static final String DOCUMENT_CODES="T03";
	public static final String DOCUMENT_SRT="A28";	
	public static final String DECLARATION_TYPE="A04";
	public static final String COMM_PREF="T17";
	public static final String NATION_PREF="T12";
	public static final String TRANSACTION_TYPE="A22";
	public static final String REGULATIONS="A35";
	public static final String PACK_UNIT="A25";
	public static final String MEASURE_CODE="T08";
	
	public static final String EU_REMARK="In Invoer mag deze code niet als land van oorsprong worden gebruikt, daarvoor is landcode EU beschikbaar.";
	
	public static final String GPA_FIELD_E6="GPA veld E.6";
	public static final String GPA_FIELD_E7="GPA veld E.7";
	public static final String[] GPA_REF= new String[]{GPA_FIELD_E6,GPA_FIELD_E7};
	
	private Cdbk cdbk=new Cdbk();
	
	public Codetable() {
	}
	
	public TreeMap<String, String> getCountriesGeneral(){
							
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, COUNTRY_CODES));
		
	}
	
	public TreeMap<String, String> getEuCountries(){
		
		return fillTable(cdbk.getTablePart(Processors.NL, GENERAL, COUNTRY_CODES,EU_REMARK));
		
	}
	
	public TreeMap<String, String> getCurrenciesGeneral(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, CURRENCY_CODES));
		
	}
	
	public TreeMap<String, String> getIncotermsGeneral(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, INCOTERMS_CODES));
		
	}
	
	public TreeMap<String, String> getCommunautairePreferenties(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, COMM_PREF));
		
	}
	
	public TreeMap<String, String> getNationalePreferenties(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, NATION_PREF));
		
	}
	
	public TreeMap<String, String> getNationalePreferentiesRange(String from, String to){
		
		return fillTable(cdbk.getTableRange(Processors.NL, GENERAL, NATION_PREF, from, to));
		
	}

	public TreeMap<String, String> getDocumentCodesInvoer(){
		
		return fillTable(cdbk.getTable(Processors.NL, INVOER, DOCUMENT_CODES));
		
	}
	
	public TreeMap<String, String> getDocumentCodesUitvoer(){
		
		return fillTable(cdbk.getTable(Processors.NL, UITVOER, DOCUMENT_CODES));
		
	}
	
	public TreeMap<String, String> getDocumentCodesInvoerNoReference(){
		
		return fillTable(cdbk.getTablePart(Processors.NL, INVOER, DOCUMENT_CODES,GPA_REF));
		
	}
	
	public TreeMap<String, String> getDeclarationTypesInvoer(){
		
		return fillTable(cdbk.getTable(Processors.NL, INVOER, DECLARATION_TYPE));
		
	}
	
	
	public TreeMap<String, String> getRegulations(){
		return fillTable(cdbk.getTable(Processors.NL, INVOER, REGULATIONS));
	}
	
	public TreeMap<String, String> getMeasureCodes(){
		return fillTable(cdbk.getTable(Processors.NL, INVOER, MEASURE_CODE));
	}
	
	public TreeMap<String, String> getTransactionTypes(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, TRANSACTION_TYPE));
		
	}
	
	public TreeMap<String, String> getPackinUnits(){
		
		return fillTable(cdbk.getTable(Processors.NL, GENERAL, PACK_UNIT));
		
	}
	
	public TreeMap<String, String> getAdministrativeUnits(){
		
		TreeMap<String, String> units= getPackinUnits();
		/*
		 * Als een code voorkomt in zowel tabel A.25 
		 * als in de tabellen in de paragrafen 5.2.1 en 5.2.2,
		 * dan vervalt de betekenis uit tabel A.25 bij die code
		 */
		
		//static units(5.2.1)
		Set<String> keys = StaticUnits.UNITS.keySet();
		for (String key : keys){			
			if(units.containsKey(key)){
				units.remove(key);				
			}
			units.put(key, StaticUnits.UNITS.get(key));
		}
		
		//admin units(5.2.2)
		keys = StaticUnits.ADMIN_UNITS.keySet();
		for (String key : keys){			
			if(units.containsKey(key)){
				units.remove(key);			
			}
			units.put(key, StaticUnits.ADMIN_UNITS.get(key));
		}	
		return units;
		
	}
	
	public ArrayList <String> getAdministrativeUnitsList(){
		ArrayList<String> units=new ArrayList<String>();
		units.add("");
		
		TreeMap<String, String> treeMap=getAdministrativeUnits();
		for(Map.Entry<String,String> entry : treeMap.entrySet()) {
			units.add(entry.getKey().trim());
		}

		
		return units;
	}
	
	
	public TreeMap<String, String> getDocumentSoorten(){
		
		TreeMap<String, String> tmp= new TreeMap<String, String>();
		
		TreeMap<String, String> srt=fillTable(cdbk.getTable(Processors.NL, GENERAL, DOCUMENT_SRT));			
		//When type is only 2 characters can be extended with 1 character from A04 Invoer		
		TreeMap<String, String> types= getDeclarationTypesInvoer();
		
		for(Map.Entry<String, String> entrySrt : srt.entrySet()){
			String code=entrySrt.getKey().trim();
			if(code.length()==2){
				for(Map.Entry<String, String> entryTyp : types.entrySet()){
					String n=entryTyp.getKey().trim();
					tmp.put(code+n, code+n);
				}
				
			}
		}
	
		srt.putAll(tmp);			
		//Add extra codes
		srt.put("ADMIN","Handels- of administratieve documenten.");
		srt.put("CPD","Carnet de Passage en Douane.");
		srt.put("REFNR","Vereenvoudigde aangifte of domicileringsprocedure.");
				
		return srt;
		
	}
	
	public TreeMap<String, String> getAngifteSoorten(){
				
		TreeMap<String, String> srt=getDocumentCodesUitvoer();			
			
		//Add extra codes
		srt.put("CPD","Carnet de Passage en Douane.");
		srt.put("OVER","Overdracht bijlage 68 TVoCDW.");
		srt.put("SAGIT","Aangifte via Sagitta (DSI)");
		srt.put("ADMIN","Handels- of administratieve documenten.");		
		srt.put("REFNR","Beeindiging van de regeling douane-entrepot met transactiecode	100100");
		
		//Another codes		
		srt.put("IMA","IMA");
		srt.put("IMB","IMB");	
		srt.put("IMC","IMC");	
		srt.put("EUA","EUA");
		srt.put("EUB","EUB");
		srt.put("EUC","EUC");
		srt.put("EXA","EXA");
		srt.put("EXB","EXB");
		srt.put("EXC","EXC");
					
		return srt;
		
	}

	private TreeMap<String, String> fillTable(ArrayList <TreeMap<String, String>> details){
		
		TreeMap<String, String> result= new TreeMap<String, String>();
				
		for (int i = 0; i < details.size(); i++) {
    		TreeMap<String, String> det=details.get(i); 		
    		result.put(det.get(Cdbk.DB_CDBK_CODE).toUpperCase(),det.get(Cdbk.DB_CDBK_DESC1));
		}
					
		return result;		
	}

}


