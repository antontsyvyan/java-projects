package com.foursoft.gpa.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.db.Transcodes;

import static com.foursoft.gpa.utils.GpaCalculator.round;


public class ControleBrochure {
	
	//Messages
	public static final String ERROR_TRANSACTION_CODE="Moet zijn gevuld met een transactiecode die voorkomt in de tabel met transactiebeschrijvingen uit onderdeel 4.11 van de informatiebrochure";
	public static final String ERROR_DOCUMENT_SRT="Documentsoort moet voorkomen in het domein van tabel A28, onderdeel Algemeen, van het Codeboek Sagitta of die is vermeld bij B.1 van de informatiebrochure.";
	public static final String ERROR_AANGIFTE_SRT="Aangiftesoort moet voorkomen in het domein van tabel T03, onderdeel Uitvoer, van het Codeboek Sagitta of die is vermeld bij J.1 van de informatiebrochure.";
	public static final String ERROR_DOCUMENT_SRT2="Toegestane aangifte- of documentsoort: REFNR";
	public static final String ERROR_NIET_TOGESTAAN="is niet toegestaan";	
	public static final String ERROR_VERPLICHT="Waarde is verplicht";
	public static final String ERROR_INVOER_DATUM_VERPLICHT="Datum van invoer is verplicht";
	public static final String ERROR_DATUM_NOT_IN_PERIOD="%s moet in het tijdvak van A.1 liggen";
	public static final String ERROR_AFGIFTE_DATUM="Afgiftedatum moet gelijk of ouder zijn dan de datum van invoer ";
	public static final String ERROR_AFGIFTE_DATUM_VERPLICHT="Afgiftedatum is verplicht";
	public static final String ERROR_MRN_VERPLICHT="B2,B3 en B4 stellen samen een MRN nummer op en moeten alle drie gevuld zijn.";
	public static final String ERROR_TG_INDICATOR="De waarde van TG indicator moet gelijk zijn aan %s";
	public static final String ERROR_COUNTRY_NOT_VALID="Het land van %s niet correct. Land moet voorkomen in het domein van tabel S01, onderdeel Algemeen, van het Codeboek Sagitta.";
	public static final String ERROR_COUNTRY_EMPTY="Het land van %s is leeg";
	public static final String ERROR_EU_CONTRY_NOT_ALLOWED="In Invoer mag landcode %s niet als land van %s worden gebruikt, daarvoor is landcode EU beschikbaar.";
	public static final String ERROR_COMM_PREF="Communautaire preferentie moet voorkomen in het domein van tabel T17, onderdeel Algemeen, van het Codeboek Sagitta";
	public static final String ERROR_COMM_PREF_NOT_ALLOWED="Communautaire preferentie mag niet zijn gevuld";
	public static final String ERROR_NATION_PREF_MANDATORY="Nationale preferentie moet zijn gevuld";
	public static final String ERROR_NATION_PREF_NOT_ALLOWED="Nationale preferentie mag niet zijn gevuld";
	public static final String ERROR_NATION_PREF="Nationale preferentie moet voorkomen in het domein van tabel T12, onderdeel Algemeen, van het Codeboek Sagitta";
	public static final String ERROR_MACHTIGINGSNUMMER_NOT_ALLOWED ="Machtigingsnummer contingent mag niet zijn gevuld bij transactiecode %s";
	public static final String ERROR_CERTIFICAATNUMMER_NOT_ALLOWED ="Certificaatnummer mag niet zijn gevuld als de transactiecode gelijk is %s";
	public static final String ERROR_INVOERVERGUNNING_DATUM_MANDATORY="Invoervergunning datum moet zijn gevuld";
	public static final String ERROR_INVOERVERGUNNING_DATUM="De datum van afgifte van de invoervergunning of het invoercertificaat moet liggen voor of op de %s";
	public static final String ERROR_ACHTERAF_OVERLEGGEN_INDICATOR="Indicator Achteraf overleggen mag niet zijn gevuld als de transactiecode gelijk is aan %s";
	public static final String ERROR_ONVOLLEDIGE_AANGIFTE_INDICATOR="Onvolledige aangifte indicator moet gelijk zijn aan spatie of *. ";
	public static final String ERROR_NL_NOT_ALLOWED="Mag niet zijn gevuld met de code NL";
	public static final String ERROR_TARIFF_CODE_NOT_VALID="Goederencode %s is niet geldig";
	public static final String ERROR_TARIFF_CODE_NOT_EXIST="Goederencode is leeg. Artikel nummer %s";
	public static final String ERROR_STATUS_INDICATOR="Status indicator moet zijn gevuld met J, N of B";
	public static final String ERROR_ADD_UNIT_NOT_VALID="Code %s is ongeldig. Aanvullende eenheden moeten zijn gevuld met een code die is opgenomen in paragraaf 5.2.1 van de informatiebrochure";
	public static final String ERROR_CURRENCY_NOT_VALID="Valuta %s is ongeldig. Moet zijn gevuld met een van de drieletterige codes uit het domein van tabel S10, onderdeel Algemeen, van het Codeboek Sagitta";
	public static final String ERROR_PERCENTAGE_DOUANE_RECHT="Het percentage douanerecht moet 0 zijn";
	public static final String ERROR_BEDRAG_VRIJSTELLING="Bedrag vrijstelling moet 0 zijn";
	public static final String ERROR_E38_NOT_E39="Moet zijn gevuld als E.38 is gevuld";
	public static final String ERROR_TRANS_TYPE_NOT_VALID="Aard van de transactie is niet geldig. Moet zijn gevuld met de code die van toepassing is uit het domein van tabel A22, onderdeel algemeen, van het Codeboek Sagitta.";
	public static final String ERROR_RELATION_NOT_VALID="Verbondenheid is niet geldig. Toegestane codes zijn: A , B , C en N";
	public static final String ERROR_PRICE_INFLUENCE_NOT_VALID="Prijs invloed is niet geldig. Toegestane codes zijn: A , B , C en N";
	public static final String ERROR_ROYALTIES_NOT_VALID="Royalties is niet geldig. Toegestane codes zijn: A , B , C en N";
	public static final String ERROR_INDICATOR_CUSTOMS_DEBT_NOT_VALID="Indicator wijze van berekening douaneschuld niet geldig. Toegestane codes zijn: B, H, I, J, S, V, T of O";
	public static final String ERROR_PRIOR_REG_NOT_VALID="Voorafgaande regeling. Moet zijn gevuld met de code die van toepassing is uit het domein van tabel A35, onderdeel invoer, van het Codeboek Sagitta.";
	public static final String ERROR_METHOD_OF_VALUATION_NOT_VALID="Methode van waardebepaling is not geldig. Toegestane codes zijn: 1, 2, 3, 4, 5, 6, 7, 8.";
	public static final String ERROR_DIFF_SCHEME_NOT_VALID=" Als in E.46 een 7 is ingevuld, dan dient in veld E.36 (Code Vak 37 Enig Document), derde deelvak %s te zijn vermeld.";
	public static final String ERROR_MOT="Moet altijd zijn gevuld met 0 als de transactiecode van A.2 gelijk is aan 11nnnn.";
	public static final String ERROR_ONLY_EMPTY="Mag niet zijn gevuld";
	public static final String ERROR_ADMIN_UNIT="Administratieve eenheid %s is ongeldig";
	public static final String ERROR_H3_H5="Moet zijn gevuld als %s is gevuld";
	public static final String ERROR_ZERO_VALUE="Moet zijn gevuld met een waarde die groter is dan 0";
	public static final String ERROR_CONTAINER="Moet zijn gevuld met een van de codes 0 of 1";
	public static final String ERROR_MEASURE_CODES="Moet zijn gevuld met een maatstafcode die voorkomt in het domein van tabel T08, onderdeel Invoer, van het Codeboek Sagitta";
	public static final String ERROR_DOCUMENT_CODES="Bescheidcode moet voorkomen in het domein van tabel T03, onderdeel Invoer, van het Codeboek Sagitta en niet in de kolom 'Bijzonderheden' een verwijzing hebben naar GPA veld E.6 of E.7";
	public static final String ERROR_CUSTOMS_AMOUNT_NEGATIVE="Douanewaarde (%s) mag niet negatief zijn.";
	public static final String ERROR_RATE_VALUE_EMPTY="Koers moet zijn gevuld. Valuta %s , period %s";
	public static final String ERROR_ANTIDUMP_TYPE_NOT_VALID="Antidumpingheffing type %s is niet toegestaan. Moet zijn gevuld met V of D";
	public static final String ERROR_E19_NOT_E20="Moet zijn gevuld als E.19 is gevuld";		
	public static final String ERROR_NOT_A_NUMBER="Value %s is not a number";	
	public static final String ERROR_MUTATIE_NIET_TOEGESTAAN="Code %s is niet a toegestaan. Toegestane mutatiesoorten zijn: BV, EV, VM, MB, BZ, BM, CO, PR, PV, VN, VD, VT, OE, BI, AF of HW.";		
	public static final String ERROR_NOT_POSITIVE="Waarde moet negatief zijn";	
	public static final String ERROR_NOT_NEGATIVE="Waarde moet positief zijn";	
	public static final String ERROR_GROSS_LESS_NET="het brutogewicht van D.2 lager is dan het nettogewicht van D.13.";	
	
	public static final String WARNING_DOCUMENT_SRT="is gebruikt bij transactiecode";
	public static final String WARNING_DOCUMENT_SRT2="Een * staat op pos. 5 en de documentsoort * ongelijk is aan 820, 821, 822 of 705.";
	public static final String WARNING_AANGIFTE_SRT1="Een * staat op pos. 5 en de documentsoort * ongelijk is aan N820, N821 of N822.";
	public static final String WARNING_INVOERVERGUNNING="Invoervergunning is gevuld en de transactiecode gelijk is aan %s";
	public static final String WARNING_E8_NOT_E7="Invoervergunning datum is ingevuld maar Invoervergunning(E.7) niet.";
	public static final String WARNING_ACHTERAF_OVERLEGGEN_INDICATOR="Preferentiecode in %s is gelijk aan %s";
	public static final String WARNING_TARIFF_CODE1="de goederencode %s valt onder hoofdstuk %d van het douanetarief en de transactiecode gelijk is aan %s en E.7 of E.5 gevuld is.";
	public static final String WARNING_E20_NOT_E19="E.19 niet is gevuld en E.20 wel is gevuld";	
	public static final String WARNING_INCO_TERM="Incoterm %s is gebruikt";
	public static final String WARNING_INCO_TERM_NOT_EXIST="Incoterm %s komt niet in het domein van tabel A14, onderdeel Algemeen, van het Codeboek Sagitta.";
	public static final String WARNING_INCO_TERM_EXPIRED="Incoterm %s is per 01/01/2011 vervallen. Voor koopcontracten afgesloten voor 01/01/2011 kan deze leveringsvoorwaarde nog wel worden gebruikt.";
	public static final String WARNING_EXCHANGE_RATE="Valutacode factuur is EUR maar koers is niet gelijk aan 1.";
	public static final String WARNING_KOSTEN="%s zijn %s en leveringsvoorwaarden gevuld met %s";
	public static final String WARNING_CUSTOMS_AMOUNT="Douanewaarde (%s) in E.31 wijkt af van de berekening (factuurwaarde)E.25 *(koers)E.27 + E.28 + E.29 + E.30 (%s)";
	public static final String WARNING_OTHER_COSTS="Als E.23(Leveringsvoorwaarden) is gevuld met DDP overige kosten in E.30 moeten negatief zijn";
	public static final String WARNING_REQ_SCHEME="Eerste twee posities van de code ongelijk zijn aan: 01, 40, 41, 42,43, 45, 61, 63 of 67";
	public static final String WARNING_REQ_SCHEME2="Eerste twee posities van de code ongelijk zijn aan: 71";
	public static final String WARNING_PRIOR_SCHEME="E.33 groter is dan 0 en de derde en vierde positie van E.36 ongelijk is aan 21 of 22";
	public static final String WARNING_DIFF_SCHEME="De laatste drie posities van E.36 gelijk zijn aan B05 is E.33 gelijk aan nihil";
	public static final String WARNING_CUSTOMS_DUTY_AMOUNT="Bedrag invoerrechten(%s) in E.35 wijkt af van de berekening (douanewaarde) E.31* (percentage) E.32 - E33 + E34 (calculated value of duty amount is:%s)";
	public static final String WARNING_E39_NOT_E38="E.38 niet is gevuld en E.39 is gevuld";	
	public static final String WARNING_INDICATOR_QUOTA="E.5 is gevuld en in E.47 een J is ingevuld";
	public static final String WARNING_INDICATOR_QUOTA_NOT_J="Moet worden gevuld met J of leeg";
	public static final String WARNING_CONTAINER="De transactiecode van A.2 gelijk is aan %s en E.49 ongelijk is aan 0";	
	public static final String WARNING_DOCUMENT_CODE="Een andere code wordt ingevuld dan: IF1 tot en met IF9, CVO of FP2";
	public static final String WARNING_COMM_PREF="In veld E40 is preferentiecode %s ingevuld en dan moet veld E6 gevuld zijn met de voorschreven aanduiding voor het preferentieel bescheid.";
	public static final String WARNING_INCONSISTENT_TARIFF="Er is gesignaleerd dat voor %s gebruik is gemaakt van meerdere goederencodes %s";
	public static final String WARNING_ANTIDUMP_AMOUNT="De waarde van F.3 gelijk is aan 0 en F.1 is gevuld of F.2 is gevuld.";
	public static final String WARNING_INCONSISTENT_WEIGHT="Er is gesignaleerd dat het nettogewicht van artikel %s afwijkt.";
	public static final String WARNING_INCONSISTENT_PRICE="De prijs per eenheid %s meer dan 10 procent afwijkt.";
	public static final String WARNING_DOCUMENT_DATE="Datum van afgifte %s wijkt af. Documentnummer %s.";
	public static final String WARNING_AFGIFTE_DATUM="Afgiftedatum mag niet afwijken van de transactiedatum";
	public static final String WARNING_WET_VERMELDINGEN="Code %s is niet toegestaan. Toegestane codes zijn: A, B, C, D, E, F, G, H, I of J.";
	//
	public static final String[] DOC_SRT_1 = new String[] {"820", "821", "822"};
	public static final String[] DOC_SRT_2 = new String[] {"820", "821", "822","705"};
	public static final String[] DOC_SRT_3 = new String[] {"822", "IF3", "IF8", "T2F", "T2M", "ZZZ"};	
	public static final String[] DOC_SRT_4 = new String[] {"720", "820", "821", "822"};	
	
	public static final String[] INCO_TERMS_1 = new String[] {"EXW", "FAS", "FOB" ,"FCA"};
	public static final String[] INCO_TERMS_2 = new String[] {"CFR", "CIF", "CPT", "CIP", "DAF", "DES", "DEQ", "DDU", "DDP", "DAP"};//DAP is not 100% sure
	public static final String[] INCO_TERMS_3 = new String[] {"EXW", "FAS", "FOB", "FCA", "CFR", "CPT"};	
	public static final String[] INCO_TERMS_4 = new String[] {"CIF", "CIP", "DAF", "DES", "DEQ","DDU" ,"DDP"};
	public static final String[] INCO_TERMS_5 = new String[] {"DDP"};
	public static final String[] INCO_TERMS_6 = new String[] {"DAP","DAT"}; 
	public static final String[] INCO_TERMS_EXPIRED = new String[] {"DDU", "DAF", "DES", "DEQ"};
	
	public static final String[] CALC_METHODS_1 = new String[]{"B", "H", "I", "S", "V", "T", "O"};
	public static final String[] CALC_METHODS_2 = new String[]{"B", "H", "I", "S", "T", "O"};
	
	public static final String[] REQ_SCHEMES = new String[]{"01", "40", "41", "42", "43", "45", "61", "63", "67"};
	
	public static final String[] DV1_VALUES = new String[]{" ", "A", "B", "C", "N"};
	public static final String[] DV1_VALUES2 = new String[]{" ", "B", "H", "I", "J", "S", "V", "T", "O"};
	public static final String[] DV1_VALUES3 = new String[]{"B", "I", "J", "S", "V", "T", "O"};
	
	public static final String[] E46_CODES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
	
	public static final String[] DOC_CODES = new String[]{"IF1","IF2","IF3","IF4","IF5","IF6","IF7","IF8","IF9","CVO","FP2"};
	
	public static final String[] COMM_PREF = new String[]{"100"};
	
	public static final String[] TRANS_CODES_ENT = new String[]{"101100","109900"};
		
	public static final String[] ANTIDUMP_TYPES = new String[]{"V","D"};
	
	public static final String[] WET_VERMELDINGEN = new String[]{"A","B","C","D","E","F","G","H","I","J"};
	
	public static final String[] MUTATIE_SOORT = new String[]{"BV","EV","VM","MB","BZ","BM","CO","PR","PV","VN","VD","VT","OE","BI","AF","HW"};
	
	public static final String[] MUTATIE_SOORT_WITHOUT_OUTBOUND = new String[]{"VM","VN","HW","BM","BZ","CO","VD","VT","PV","OE"};
	//For validation matrix
	public static final String[] K7_ARRAY = new String[]{"BV","EV","VM","VM","MB","BZ","BM","CO","PR","PV","VN","VD","VT","OE","OE","OE","BI","AF","HW"};	
	public static final String[] A2_ARRAY = new String[]{
		"^101000$",
		"^101000$",
		"^101000",
		"^1009[0-9]{2}$",
		"^101000$",
		"^101000$",
		"^101000$",
		"^101000$",
		"^091000$",
		"^101000$",
		"^101000$",
		"^101000$",
		"^101000$",
		"^101000$",
		"^0110[0-9]{2}$",
		"^1001[0-9]{2}$",
		"^[0-9&&[^1]][0-9&&[^0]]10[0-9]{2}$",
		"^10[0-9&&[^1]][0-9&&[^0]][0-9]{2}$",
		"^101000$"
		};
	public static final boolean[] K8_REQUIRED = new boolean[]{
		false,
		false,
		false,
		false,
		false,
		true,
		true,
		false,
		false,
		true,
		false,
		false,
		false,
		true,
		true,
		true,
		false,
		false,
		false
		};
	public static final boolean[] K9_K10_ALLOW_PLUS = new boolean[]{
		true,
		false,
		false,
		false,
		true,
		true,
		true,
		true,
		true,
		true,
		false,
		false,
		false,
		true,
		true,
		false,
		true,
		false,
		false
		};	
	public static final boolean[] K9_K10_ALLOW_MIN = new boolean[]{
		false,
		true,
		true,
		true,
		false,
		true,
		true,
		true,
		false,
		true,
		true,
		true,
		true,
		true,
		false,
		true,
		false,
		true,
		false
		};
	//
	public static final String TYPE_ERROR="ERROR";
	public static final String TYPE_WARNING="WARNING";
	
	private TreeMap<String, String> detail=new TreeMap<String, String>();
	private String type;
	private Integer line;
	private List<DiskErrorsModel> errorsList;
	
	private boolean valid;
	
	private String currentBlock="";
	
	private int transCode;
	
	private DateUtils du= new  DateUtils();	
	private Transcodes tc=new Transcodes();
	
	private GpaCalculator calc= new GpaCalculator();
	
	private TreeMap<String, String> documentSoorten;
	private TreeMap<String, String> aangifteSoorten;
	private TreeMap<String, String> countries;
	private TreeMap<String, String> euCountries;
	private TreeMap<String, String> communautairePreferenties; 
	//private TreeMap<String, String> nationalePreferenties; 
	private TreeMap<String, String> nationalePreferentiesRange1;
	private TreeMap<String, String> incoTerms;
	private TreeMap<String, String> currencyCodes;
	private TreeMap<String, String> transactionTypes;
	private TreeMap<String, String> regulation;
	private TreeMap<String, String> adminUnits;
	private TreeMap<String, String> measureCodes;
	private TreeMap<String, String> documentCodesNoLink;
	private String searchField="";
	private String searchFieldCur;
	
	private String direction="";
	
	public ControleBrochure(){
		initBrochure();
	}
	
	public ControleBrochure(String type){
		this.type=type;
		initBrochure();
	}
	
	
	
	public void setDirection(String direction) {
		this.direction = direction;
	}

	private void initBrochure(){
		errorsList=new ArrayList<DiskErrorsModel>();
		valid=true;
		//load tables
		Codetable ct=new Codetable();
		documentSoorten=ct.getDocumentSoorten();
		aangifteSoorten=ct.getAngifteSoorten();
		countries=ct.getCountriesGeneral();
		euCountries=ct.getEuCountries();
		communautairePreferenties=ct.getCommunautairePreferenties();
		//nationalePreferenties=ct.getNationalePreferenties();
		nationalePreferentiesRange1=ct.getNationalePreferentiesRange("060", "899");
		incoTerms=ct.getIncotermsGeneral();
		currencyCodes=ct.getCurrenciesGeneral();
		transactionTypes=ct.getTransactionTypes();
		regulation=ct.getRegulations();		
		adminUnits=ct.getAdministrativeUnits();
		measureCodes=ct.getMeasureCodes();
		documentCodesNoLink=ct.getDocumentCodesInvoerNoReference();	
					
		tc.loadTranscodesTable();
	}
	
	public boolean isValidDetail() throws Exception{
		
		this.valid=true;
		errorsList.clear();
		if(type.equals(Disk.INVOER)){			
			return isValidForImp();
		}else{
			return isValidForImp();
		}

	}
	
	/*
	private boolean isValidForEnt(){
		boolean valid=true;
		
		return valid;
	}
	*/
	
	private boolean isValidForImp() throws Exception{
			
		//get blocks
		//List<String> blocks=getBlocks(detail.get(Disk.BLOCK_A2));	
		searchFieldCur=searchField;
		if(this.searchField==null || this.searchField.trim().equals("") ){
			searchFieldCur=detail.get(Disk.BLOCK_A3);
		}
		
		String blockA2=detail.get(Disk.BLOCK_A2);		
		if(type.equals(Disk.INVOER)){	
			blockA2=tc.getProcessCode(detail.get(Disk.BLOCK_A2));
		}		
		List<String> blocks=tc.getTransBlocksList(blockA2);
		
		if(blocks==null || blocks.size()==0){
			//Leave validation		
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_A,Disk.BLOCK_A2,ERROR_TRANSACTION_CODE));
			return false;
		}
		try{
			transCode=Integer.parseInt(detail.get(Disk.BLOCK_A2).trim());
		}catch(Exception ex){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_A,Disk.BLOCK_A2,ERROR_TRANSACTION_CODE));
			return false;
		}
		
		Collections.sort(blocks);
				
		//
		if(blocks.contains(Disk.BLOCK_A)){
			//validate A-block	
			//A-block is required for all transaction codes
		}
		
		if(blocks.contains(Disk.BLOCK_B)){
			//validate B-block					
			validateB();
	
		}
		if(blocks.contains(Disk.BLOCK_D)){
			//validate D-block	
			validateD();
		}
		if(blocks.contains(Disk.BLOCK_E)){
			//validate E-block		
			validateE();
		}
		if(blocks.contains(Disk.BLOCK_F)){
			//validate F-block
			validateF();
		}
		if(blocks.contains(Disk.BLOCK_G)){
			//validate G-block
		}
		if(blocks.contains(Disk.BLOCK_H)){
			//validate H-block			
			validateH();
		}
		if(blocks.contains(Disk.BLOCK_J)){
			//validate J-block
			validateJ();
		}
		if(blocks.contains(Disk.BLOCK_K)){
			//validate K-block
			validateK();
		}
		if(blocks.contains(Disk.BLOCK_M)){
			//validate M-block			
			validateM();
		}
		
		
		return this.valid;
	}
		
	private void validateB(){
		//validate B1
		currentBlock=Disk.BLOCK_B;
		
		//B1-block	
		String field=Disk.BLOCK_B1;
		String blockValue=detail.get(field);
		boolean starOn5=false;
		//Is * op de positie 5 aanwezig?
		if(blockValue.length()==5){
			if(blockValue.charAt(4)=='*'){
				blockValue=blockValue.substring(0,4).trim();
				starOn5=true;				
			}
		}
				
		blockValue=blockValue.trim();
		/* 
		 * Moet zijn gevuld met de code voor een aangifte- of documentsoort die voorkomt in
		 * het domein van tabel A28, onderdeel Algemeen, van het Codeboek Sagitta of die is
		 * vermeld bij B.1 van de informatiebrochure.
		 */
		if(!documentSoorten.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_DOCUMENT_SRT));
			valid=false;
		}else{	
			if((transCode >=100900 && transCode<=101506) || (transCode >=110900 && transCode<=111506)){
				/*
				 * Transactiecode: 100900 t/m 101506 en 110900 t/m 111506
				 * Toegestane aangifte- of documentsoorten: REFNR
				 */
				if(!blockValue.equals("REFNR")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_DOCUMENT_SRT2));
					valid=false;
				}
			}
			
			if(transCode >=10900 && transCode<=11506){
				/*
				 * Met uitzondering van EXn en
				 * COn zijn alle codes toegestaan die voorkomen in het domein van tabel A28, onderdeel
				 * Algemeen, van het Codeboek Sagitta of vermeld zijn bij B.1 van de informatiebrochure
				 */
				if(blockValue.length()==3){
					if(blockValue.startsWith("EX") || blockValue.startsWith("CO")){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,blockValue+" "+ERROR_NIET_TOGESTAAN));
						valid=false;
					}else if(Arrays.asList(DOC_SRT_3).contains(blockValue)){
						/*
						 * Signalering volgt als een van de codes 822, IF3, IF8, T2F, T2M, ZZZ is
						 * gebruikt.
						 */
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,blockValue+" "+WARNING_DOCUMENT_SRT+" "+Disk.BLOCK_A2));
					}
				}
				
			}
			
			/*
			 * Signalering vindt plaats als op positie 5 van B.1 een * staat en de documentsoort
			 * ongelijk is aan 820, 821, 822 of 705.
			 */
			if(starOn5){
				if(!Arrays.asList(DOC_SRT_2).contains(blockValue)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_DOCUMENT_SRT2));
				}
			}
			
		}
		
		//B2,B3 en B4 blocken		
		/*
		 * Moeten zijn gevuld. Er vindt controle op de layout (MRN formaat) plaats als B.1 is gevuld met 820, 821 of 822.
		 */
		
		if(Arrays.asList(DOC_SRT_1).contains(detail.get(Disk.BLOCK_B1).trim())){
			
			if(detail.get(Disk.BLOCK_B2).trim().equals("") || detail.get(Disk.BLOCK_B3).trim().equals("") || detail.get(Disk.BLOCK_B4).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_MRN_VERPLICHT));
				valid=false;
			}else{
				//MovementReferenceNumber mrn= new MovementReferenceNumber();
				//mrn.setDeclarationDate(detail.get(Disk.BLOCK_B4).trim());
				//mrn.setOfficeCode(detail.get(Disk.BLOCK_B3).trim());
				//mrn.setNumber(detail.get(Disk.BLOCK_B2).trim());
				//if(!mrn.isValid()){
					//this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,mrn.getError()));
					//valid=false;
				//}
			}
		}
		
		
		//B4-block 
		/*
		 * Afgiftedatum
		 * 
		 * IMP
		 * Moet zijn gevuld met een geldige datum die gelijk is aan of ouder is dan de dag van invoer in M.6
		 * 
		 * ENT
		 * Moet zijn gevuld met een geldige datum die gelijk is aan of ouder is dan de mutatiedatum van K.13.
		 */
		
		field=Disk.BLOCK_B4;
		blockValue=detail.get(field).trim();
		
		String mutationDate="";
		if(type.equals(Disk.INVOER)){
			mutationDate=detail.get(Disk.BLOCK_M6).trim();
		}else{
			mutationDate=detail.get(Disk.BLOCK_K13).trim();
		}
		
		if(!mutationDate.equals("")){
			//De dag van invoer moet in het tijdvak van A.1 liggen.
			if(du.isInPeriod(mutationDate, detail.get(Disk.BLOCK_A1).trim())){
				if(!blockValue.equals("")){
					if(!isValidDates(mutationDate,blockValue)){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_AFGIFTE_DATUM));
						valid=false;
					}else{
						// Warning. De documentdatum mag niet afwijken van de transactiedatum
						if(!mutationDate.equals(blockValue)){
							this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_AFGIFTE_DATUM));
						}
					}
				}else{
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_AFGIFTE_DATUM_VERPLICHT));
					valid=false;
				}
			}else{
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_M,Disk.BLOCK_M6,String.format(ERROR_DATUM_NOT_IN_PERIOD,"De dag van invoer ")));				
				valid=false;
			}
					
			
		}else{
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_M,Disk.BLOCK_M6,ERROR_INVOER_DATUM_VERPLICHT));
			valid=false;
		}
		
		//B5-block		
		/*
		 * Indicator TG 
		 * 
		 */
		
		field=Disk.BLOCK_B5;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			blockValue=" ";
		}
		
		String regexp="^01[0-9]{4}";
		/*
		 * Het teken * mag alleen worden
		   gebruikt als de transactiecode van A.2 gelijk is aan 01nnnn en de waarde van B.1
		   gelijk is aan 720, 820, 821 of 822.
		 */
		String characterToBeUsed=" ";
		if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
			if(Arrays.asList(DOC_SRT_4).contains(detail.get(Disk.BLOCK_B1))){
				characterToBeUsed="*";
			}
		}
		
		if(!blockValue.equals(characterToBeUsed)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_TG_INDICATOR,characterToBeUsed)));
			valid=false;
		}		
	}
	
	
	private void validateD(){
		currentBlock=Disk.BLOCK_D;
		//D1-block		
		//Moet zijn gevuld.
		String field=Disk.BLOCK_D1;
		String blockValue=detail.get(field);
		
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//D2-block
		//Moet zijn gevuld. 
		field=Disk.BLOCK_D2;
		blockValue=detail.get(field);
		int grossWeight=0;
		try{
			grossWeight=Integer.parseInt(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.		
			if(grossWeight<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}else{

			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//D2-block
		//Moet zijn gevuld. 
		field=Disk.BLOCK_D13;
		blockValue=detail.get(field);
		int netWeight=0;
		try{
			netWeight=Integer.parseInt(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.		
			if(netWeight<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}else{

			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}	
		//Signalering volgt als het brutogewicht van D.2 lager is dan het nettogewicht
		//van D.13		
		if(grossWeight>0 && netWeight>0){			
			if(grossWeight<netWeight){				
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,ERROR_GROSS_LESS_NET));
			}
		}
		
		//D3-block	
		field=Disk.BLOCK_D3;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met een code die voorkomt in het domein van tabel A25, onderdeel Algemeen, 
		 */
		if(!adminUnits.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, String.format(ERROR_ADMIN_UNIT,blockValue)));
			valid=false;
		}
		
		//D4-block	
		field=Disk.BLOCK_D4;
		blockValue=detail.get(field).trim();
		int intValue=0;
		try{
			intValue=Integer.parseInt(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.
			
			if(intValue<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//D5-block
		//Moet zijn gevuld.
		field=Disk.BLOCK_D5;
		blockValue=detail.get(field).trim();
		//Mandatory
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//D6-block
		//Moet zijn gevuld
		field=Disk.BLOCK_D6;
		blockValue=detail.get(field).trim();
		//Mandatory
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_TARIFF_CODE_NOT_EXIST,detail.get(Disk.BLOCK_E16))));
			valid=false;
			//Already checked in the mandatory fields section
		}else{
			TariffCode tariffCode=new TariffCode(blockValue);
			if(!tariffCode.isValid()){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_TARIFF_CODE_NOT_VALID,tariffCode.getTariffCode())));
				valid=false;
			}
		}
		
		//D7-block
		field=Disk.BLOCK_D7;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			//Moet zijn gevuld als K.2 gevuld is met de indicator H.
			if(detail.get(Disk.BLOCK_K2).equals("H")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
				valid=false;
			}
		}
		
		//D8-block
		field=Disk.BLOCK_D8;
		blockValue=detail.get(field).trim();
		//Moet zijn gevuld
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
			
		//D10 -block Already checked in Inbound interface
		
		//D11-block
		field=Disk.BLOCK_D11;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met een code die voorkomt in het domein van tabel A25, onderdeel Algemeen, 
		 * van het Codeboek Sagitta of met een code die is opgenomen in de tabellen in de paragrafen 5.2.1
		 * en 5.2.2 van de informatiebrochure
		 */
		if(!adminUnits.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, String.format(ERROR_ADMIN_UNIT,blockValue)));
			valid=false;
		}
		
		//D12-block
		field=Disk.BLOCK_D12;
		blockValue=detail.get(field).trim();
		intValue=0;
		try{
			intValue=Integer.parseInt(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.
			
			if(intValue<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		
		//D14-block
		field=Disk.BLOCK_D14;
		blockValue=detail.get(field).trim();
		//Moet zijn gevuld
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//D15-block
		
	}
	
	
	
	
	private void validateE(){
		currentBlock=Disk.BLOCK_E;
			
		//E1-block		
		/*
		 * Moet zijn gevuld. De tweeletterige alfacode voor het land van verzending/uitvoer
		   moet voorkomen in het domein van tabel S01, onderdeel Algemeen, van het
		   Codeboek Sagitta
		 */
		String field=Disk.BLOCK_E1;
		String blockValue=detail.get(field).trim().toUpperCase();
		String landType="verzending/uitvoer";
		if(!blockValue.equals("")){
			if(!countries.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_NOT_VALID,landType)));
				valid=false;
			}
		}else{
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_EMPTY,landType)));
			valid=false;
		}

		//E2-block		
		/*
		 * Moet zijn gevuld. De tweeletterige alfacode voor het land van oorsprong moet
		   voorkomen in het domein van tabel S01, onderdeel Algemeen, van het Codeboek Sagitta.
		   De codes voor de landen, die behoren tot de EU mogen hier niet gebruikt worden,
		   indien er sprake is van brengen in het vrije verkeer.
		 */

		field=Disk.BLOCK_E2;
		blockValue=detail.get(field).trim().toUpperCase();
		landType="oorsprong ";
		if(!blockValue.equals("")){
			if(!countries.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_NOT_VALID,landType)));
				valid=false;
			}else{
				if(!direction.equals(Processors.INBOUND)){
					if(euCountries.containsKey(blockValue)){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_EU_CONTRY_NOT_ALLOWED,blockValue,landType)));
						valid=false;
					}
				}
			}
		}else{
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_EMPTY,landType)));
			valid=false;
		}
		
		/*
		 * De velden E.3 en E.4 (Nationale preferentiecodes) zijn vanaf 1 januari 2014 vervangen door het 
		 * volgnummer van het tariefcontingent.
		 * Geen validatie zo ver.
		 * 
		
		//E40,E3 -block		
		field=Disk.BLOCK_E40;
		blockValue=detail.get(field).trim();
		boolean isCommPref=false;
		boolean isNationPref=false;
		if(!blockValue.equals("")){
			if(!communautairePreferenties.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_COMM_PREF));
				valid=false;
			}
			isCommPref=true;
		}
		


		field=Disk.BLOCK_E3;
		blockValue=detail.get(field).trim();
		
		if(!blockValue.equals("")){
			//E.3 mag niet zijn gevuld als de transactiecode gelijk is aan mm13nn.
			String regexp="^[0-1]{2}13[0-9]{2}";
			if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
				if(isCommPref){
					//E.40 mag niet zijn gevuld
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_E40,ERROR_COMM_PREF_NOT_ALLOWED));
					valid=false;
					
				}else{
					//E3 mag niet zijn gevuld
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NATION_PREF_NOT_ALLOWED));
					valid=false;
				}
			}else{
				//Check code in E3
				if(!nationalePreferenties.containsKey(blockValue)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NATION_PREF));
					valid=false;
				}else{
					isNationPref=true;
				}
			}
		}else{
			if(isCommPref){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NATION_PREF_MANDATORY));
				valid=false;
			}
		}
		
		//E4 -block
		field=Disk.BLOCK_E4;
		blockValue=detail.get(field).trim();
		
		if(isNationPref){
			if(!blockValue.equals("")){
				if(!nationalePreferenties.containsKey(blockValue)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NATION_PREF));
					valid=false;
				}
			}
		}else{
			if(!blockValue.equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NATION_PREF_NOT_ALLOWED));
				valid=false;
			}
		}
		
		*/
		
		//E5 -block	
		field=Disk.BLOCK_E5;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//IMP
			//E.5 mag niet zijn gevuld bij transactiecodes mm12nn, mm13nn en mm15nn
			//ENT
			//E.5 mag niet zijn gevuld bij transactiecodes 101100, 1012nn, 1013nn, 1015nn en 109900.			
			String regexp="";
			
			if(type.equals(Disk.ENTREPOT)){
				regexp="^(10)(12|13|15)[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp) || Arrays.asList(TRANS_CODES_ENT).contains(detail.get(Disk.BLOCK_A2))){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_MACHTIGINGSNUMMER_NOT_ALLOWED,detail.get(Disk.BLOCK_A2))));
					valid=false;
				}
			}else{
				regexp="^[0-1]{2}(12|13|15)[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_MACHTIGINGSNUMMER_NOT_ALLOWED,detail.get(Disk.BLOCK_A2))));
					valid=false;
				}		
			}
			
						
		}
		
		//E6 -block	
		field=Disk.BLOCK_E6;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//IMP
			//E.6 mag niet zijn gevuld als de transactiecode gelijk is aan mm13nn.
			//ENT
			//E.6 mag niet zijn gevuld als de transactiecode gelijk is aan 101100, 1013nn, 109900
			String regexp="";
			if(type.equals(Disk.ENTREPOT)){
				regexp="^1013[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp) || Arrays.asList(TRANS_CODES_ENT).contains(detail.get(Disk.BLOCK_A2))){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CERTIFICAATNUMMER_NOT_ALLOWED,detail.get(Disk.BLOCK_A2))));
					valid=false;
				}
			}else{
				regexp="^[0-1]{2}13[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CERTIFICAATNUMMER_NOT_ALLOWED,detail.get(Disk.BLOCK_A2))));
					valid=false;
				}	
			}						
		}
		
		
		//E7 -block	
		field=Disk.BLOCK_E7;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//IMP
			//Signalering vindt plaats als E.7 is gevuld en de transactiecode gelijk is aan mm12nn,mm13nn of mm15nn
			//ENT
			//Signalering vindt plaats als E.7 is gevuld en de transactiecode gelijk is aan 101100, 1012nn,	1013nn, 1015nn of 109900.

			String regexp="";
			if(type.equals(Disk.ENTREPOT)){
				regexp="^(10)(12|13|15)[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp) || Arrays.asList(TRANS_CODES_ENT).contains(detail.get(Disk.BLOCK_A2))){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_INVOERVERGUNNING,detail.get(Disk.BLOCK_A2))));
				}
			}else{
				regexp="^[0-1]{2}(12|13|15)[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_INVOERVERGUNNING,detail.get(Disk.BLOCK_A2))));
				}	
			}
							
		}

		//E8 -block	
		field=Disk.BLOCK_E8;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){	
			//Signalering vindt ook plaats als E.7 niet is ingevuld en in E.8 wel een datum is ingevuld
			if(detail.get(Disk.BLOCK_E7).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_E8_NOT_E7));
			}
			//De datum van afgifte van de invoervergunning of het invoercertificaat moet liggen voor of op de 
			//IMP - dag van invoer M.6.
			//ENT - mutatiedatum K.13.
			String tmpDatum=detail.get(Disk.BLOCK_M6);
			String datumText="dag van invoer M.6";
			if(type.equals(Disk.ENTREPOT)){
				tmpDatum=detail.get(Disk.BLOCK_K13);
				datumText="mutatiedatum K.13";
			}
			
			if(!isValidDates(tmpDatum,blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_INVOERVERGUNNING_DATUM,datumText)));
				valid=false;
			}
		}else{
			//E8 moet zijn gevuld als E.7 is gevuld en niet gelijk is aan MEER
			if(!detail.get(Disk.BLOCK_E7).trim().equals("") && !detail.get(Disk.BLOCK_E7).trim().toUpperCase().equals("MEER")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_INVOERVERGUNNING_DATUM_MANDATORY));
				valid=false;
			}
		}
		
		// E9 -block	
		field=Disk.BLOCK_E9;
		blockValue=detail.get(field).trim();
		//E9 mag niet zijn gevuld als de transactiecode gelijk is aan mm13nn
		String regexp="";
		boolean goNext=true;		
		if(!blockValue.equals("")){
			if(type.equals(Disk.ENTREPOT)){
				regexp="^1013[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp) || Arrays.asList(TRANS_CODES_ENT).contains(detail.get(Disk.BLOCK_A2))){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_ACHTERAF_OVERLEGGEN_INDICATOR,detail.get(Disk.BLOCK_A2))));
					valid=false;
					goNext=false;
				}
			}else{
				regexp="^[0-1]{2}(13)[0-9]{2}$";
				if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_ACHTERAF_OVERLEGGEN_INDICATOR,detail.get(Disk.BLOCK_A2))));
					valid=false;
					goNext=false;
				}
			}
			
			if(goNext){
				if(!blockValue.equals("*")){
					//E9 Moet gelijk zijn aan spatie of *
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_INVOERVERGUNNING_DATUM_MANDATORY));
					valid=false;
				}else{
					/*
					 * Signalering vindt plaats als E.9 is gevuld met een * en de
					   preferentiecode in E.3 en/of E.4 voorkomt in de reeks van 060 tot en met 899 van
                       tabel T12, onderdeel Algemeen, van het Codeboek Sagitta.
					 */
					if(!detail.get(Disk.BLOCK_E3).trim().equals("")){
						if(nationalePreferentiesRange1.containsKey(detail.get(Disk.BLOCK_E3).trim())){
							this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_ACHTERAF_OVERLEGGEN_INDICATOR,Disk.BLOCK_E3,detail.get(Disk.BLOCK_E3))));
						}
					}
					
					if(!detail.get(Disk.BLOCK_E4).trim().equals("")){
						if(nationalePreferentiesRange1.containsKey(detail.get(Disk.BLOCK_E4).trim())){
							this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_ACHTERAF_OVERLEGGEN_INDICATOR,Disk.BLOCK_E4,detail.get(Disk.BLOCK_E4))));
						}
					}
				}
			}
		}
		
		//E10 -block
		field=Disk.BLOCK_E10;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!blockValue.equals("*")){
				//E10 Moet gelijk zijn aan spatie of *
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ONVOLLEDIGE_AANGIFTE_INDICATOR));
				valid=false;
			}
		}
		
		//E11 -block  Dit veld is per 1 januari 2004 niet in gebruik.
		
		//Mandatory blocks section
		
		//E12,E13,E14-blocks
		//These block are mandatory
		
		boolean e12=true;
		field=Disk.BLOCK_E12;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			if(!direction.equals(Processors.INBOUND)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
				valid=false;
			}
			e12=false;
		}
		
		
		if(e12){
			String [] blocks=new String[]{Disk.BLOCK_E13,Disk.BLOCK_E14};
			for(int i=0;i<blocks.length;i++){
				field=blocks[i];
				blockValue=detail.get(field).trim();
				if(blockValue.equals("")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
					valid=false;
				}
			}
		}
		
		//H18 -block
		//This block should be checked before E15
		boolean eu=false;
		boolean validateE15=true;
		
		field=Disk.BLOCK_H18;
		blockValue=detail.get(field).trim();
		landType="andere lidstaat dan NL ";
		if(!blockValue.equals("")){
			if(!countries.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_NOT_VALID,landType)));
				validateE15=false;
				valid=false;
			}else{
				if(blockValue.equals("NL")){
					//Mag niet zijn gevuld met de code NL
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_NL_NOT_ALLOWED));
					validateE15=false;
					valid=false;				
				}else{
					//Check if lidstaat
					if(euCountries.containsKey(blockValue)){
						 eu=true;
					}
				}
								
			}
		}
		
		//E15 -block
		if(validateE15){
			field=Disk.BLOCK_E15;
			blockValue=detail.get(field).trim();
			if(!blockValue.equals("")){
				/*
				 * Als H.18 niet is gevuld met de code van een lidstaat en E.15 is wel gevuld dan moet
				   de Nederlandse Belastingdienst het BTW-identificatienummer voor de verlegging
				   van de omzetbelasting hebben toegekend.
				   Als H.18 is gevuld met de code van een lidstaat, moet E.15 zijn gevuld met een BTWidentifi
				   catienummer voor de (verlegging van) de omzetbelasting, dat is toegekend
				   door de betreffende lidstaat waar de goederen zich bevinden op het moment van
				   het tot verbruik aangeven en het in het vrije verkeer brengen			  
				 */
				
				VatNumber vat=new VatNumber(blockValue);
				if(eu){					
					vat.setCountyCode(detail.get(Disk.BLOCK_H18).trim());
				}else{
					vat.setCountyCode("NL");
				}
				
				if(!vat.isValid()){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,vat.getErrorMessage()));
					valid=false;
				}
				
			}else{
				// E.15 hoeft niet te zijn gevuld bij plaatsing onder de douaneregeling douaneentrepot.
				if(!direction.equals(Processors.INBOUND)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
					valid=false;
				}
			}
		}
		
		//E16 -block
		field=Disk.BLOCK_E16;
		blockValue=detail.get(field).trim();
		//Mandatory
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//E17 -block
		field=Disk.BLOCK_E17;
		blockValue=detail.get(field).trim();
		//Mandatory
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_TARIFF_CODE_NOT_EXIST,detail.get(Disk.BLOCK_E16))));
			valid=false;
			//Already checked in the mandatory fields section
		}else{
			TariffCode tariffCode=new TariffCode(blockValue);
			if(tariffCode.isValid()){
				/*
				 * IMP
				 * Signalering vindt plaats als de goederencode valt onder hoofdstuk 1 tot en met
				   24 van het douanetarief en de transactiecode gelijk is aan mm12nn of mm15nn
				   en E.7 of E.5 gevuld is.
				   
				   ENT
				   Signalering vindt plaats als de goederencode valt onder hoofdstuk 1 tot en met 
				   24 van het douanetarief en de transactiecode gelijk is aan 1012nn of 1015nn 
				   en E.7 of E.5 gevuld is.
				 */
				
				regexp="^[0-1]{2}12|15[0-9]{2}$";
				if(!direction.equals(Processors.INBOUND)){
					regexp="^10(12|15)[0-9]{2}$";
				}
				
				if(tariffCode.getChapter()>=1 && tariffCode.getChapter()<=24){
					
					if(detail.get(Disk.BLOCK_A2).trim().matches(regexp)){
						if(detail.get(Disk.BLOCK_E5).trim().equals("") || detail.get(Disk.BLOCK_E7).trim().equals("")){
							this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_TARIFF_CODE1,tariffCode.getTariffCode(),tariffCode.getChapter(),detail.get(Disk.BLOCK_A2).trim())));
						}
					}
				}
			}else{
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_TARIFF_CODE_NOT_VALID,tariffCode.getTariffCode())));
				valid=false;
			}
		}
		
		
		//E18 -block
		field=Disk.BLOCK_E18;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met J, N of B.
		 */
		if(!blockValue.equals("")){
			if(!blockValue.matches("^[jnbJNB]$")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_STATUS_INDICATOR));
				valid=false;
			}
		}else{
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_STATUS_INDICATOR));
			valid=false;
		}
		
		//E19 -block
		field=Disk.BLOCK_E19;
		blockValue=detail.get(field).trim().toUpperCase();
		if(!blockValue.equals("")){
			/*
			 * Moet zijn gevuld met een code die is opgenomen in paragraaf 5.2.1 van de
			   informatiebrochure
			 */
			if(!StaticUnits.UNITS.containsKey(blockValue)){			
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_ADD_UNIT_NOT_VALID,blockValue)));
				valid=false;				
			}
		}else{
			if(!detail.get(Disk.BLOCK_E20).trim().equals("") && !detail.get(Disk.BLOCK_E20).trim().equals("0")){
				/*
				 * Signalering vindt plaats als E.19 niet is gevuld en E.20 wel is gevuld.
				 */
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_E20_NOT_E19));
			}
		}
		
		
		//E20 -block
		field=Disk.BLOCK_E20;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("") || blockValue.equals("0")){
			/*
			 * Moet zijn gevuld als E.19 is gevuld
			 */
			if(!detail.get(Disk.BLOCK_E19).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_E19_NOT_E20));
				valid=false;
			}
		}

		//E21 -block
		field=Disk.BLOCK_E21;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld
		 */
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//E23 -block
		field=Disk.BLOCK_E23;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			/*
			 * Moet zijn gevuld met een van de codes uit het domein van tabel A14, onderdeel
			   Algemeen, van het Codeboek Sagitta. Signalering vindt plaats als van een andere
			   code of XXX gebruik is gemaakt
			   Signalering vindt plaats als gevuld met de waarde: DDU, DAF, DES of DEQ
			   (Deze codes zijn per 01/01/2011 vervallen)
			 */
			if(incoTerms.containsKey(blockValue)){
				if(blockValue.toUpperCase().equals("XXX")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_INCO_TERM,blockValue)));
				}
				if(Arrays.asList(INCO_TERMS_EXPIRED).contains(blockValue)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_INCO_TERM_EXPIRED,blockValue)));
				}
			}else{
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_INCO_TERM_NOT_EXIST,blockValue)));
			}
		}
		
		//E24 -block
		field=Disk.BLOCK_E24;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			if(!direction.equals(Processors.INBOUND)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
				valid=false;
			}
		}
		
		//E25 -block
		field=Disk.BLOCK_E25;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("") || blockValue.equals("0")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
		}
		
		//E26 -block
		field=Disk.BLOCK_E26;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			/*
			 * Moet zijn gevuld met een van de drieletterige codes uit het domein van tabel S10, onderdeel Algemeen, 
			 * van het Codeboek Sagitta";
			 */
			if(!currencyCodes.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CURRENCY_NOT_VALID,blockValue)));
				valid=false;
			}
		}
		
		//E27 -block
		field=Disk.BLOCK_E27;
		blockValue=detail.get(field).trim();
		double decValue=0;
		try{
			decValue=Double.parseDouble(blockValue);		
			if(decValue>0){
				/*
				 * Signalering volgt als E.26 is gevuld met EUR en E.27 niet is gevuld met 1.
				 */
				if(detail.get(Disk.BLOCK_E26).trim().toUpperCase().equals("EUR")){
					if(decValue!=1.0){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_EXCHANGE_RATE));
					
					}
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//E28 -block
		field=Disk.BLOCK_E28;
		blockValue=detail.get(field).trim();
		//Vrachtkosten
		try{
			decValue=Double.parseDouble(blockValue);
			/*
			boolean warningE28=false;		
			if(decValue<=0){
	
				 //Signalering vindt plaats als E.23 is gevuld met EXW, FAS, FOB of FCA en E.28 is kleiner of gelijk aan 0.
	
				if(Arrays.asList(INCO_TERMS_1).contains(detail.get(Disk.BLOCK_E23).trim())){
					warningE28=true;
				}
			}else{
	
				//Signalering vindt ook plaats als E.23 is gevuld met CFR, CIF, CPT, CIP, DAF, DES, DEQ, DDU of DDP en E.28 > 0.
	
				if(Arrays.asList(INCO_TERMS_2).contains(detail.get(Disk.BLOCK_E23).trim())){
					warningE28=true;
				}
			}
			if(warningE28){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_KOSTEN,"Vrachtkosten",decValue,detail.get(Disk.BLOCK_E23).trim())));
			}
			
			*/
			
			if(decValue>0){
				if(Arrays.asList(INCO_TERMS_6).contains(detail.get(Disk.BLOCK_E23).trim())){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_KOSTEN,"Vrachtkosten",decValue,detail.get(Disk.BLOCK_E23).trim())));
	
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		
		//E29 -block
		field=Disk.BLOCK_E29;
		blockValue=detail.get(field).trim();
		//Verzekeringskosten 
		try{
			decValue=Double.parseDouble(blockValue);
			boolean warningE29=false;
			if(decValue<0){
				/*
				 * Signalering vindt plaats als E.29 < 0.
				 */
				warningE29=true;
			}else if(decValue==0){
				/*
				 * Signalering vindt plaats als E.23 is gevuld met EXW, FAS, FOB, FCA, CFR of CPT en E.29 = 0
				 */
				if(Arrays.asList(INCO_TERMS_3).contains(detail.get(Disk.BLOCK_E23).trim())){
					warningE29=true;
				}
			}else{
				/*
				 * Signalering vindt plaats als E.23 is ingevuld met CIF, CIP, DES, DEQ, DAF, DDU of DDP en E.29 > 0
				 */
				if(Arrays.asList(INCO_TERMS_4).contains(detail.get(Disk.BLOCK_E23).trim())){
					warningE29=true;
				}
			}
				
			if(warningE29){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_KOSTEN,"Verzekeringskosten",decValue,detail.get(Disk.BLOCK_E23).trim())));
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		//E30 -block
		field=Disk.BLOCK_E30;
		blockValue=detail.get(field).trim();
		//Overige kosten 
		try{
			decValue=Double.parseDouble(blockValue);
			if(decValue>0){
				if(detail.get(Disk.BLOCK_E23).toUpperCase().trim().equals("DDP")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_OTHER_COSTS ));
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
				
		//E31 -block
		field=Disk.BLOCK_E31;
		blockValue=detail.get(field).trim();
		//Douanewaarde
		try{
			decValue=Double.parseDouble(blockValue);
			if(decValue<0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CUSTOMS_AMOUNT_NEGATIVE,decValue)));
				valid=false;
			}else{		
				if(decValue!=0){
					double taxValue=calc.getTaxValue();
					if(decValue!=taxValue){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_CUSTOMS_AMOUNT,decValue,taxValue)));
					}
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//E32 -block
		field=Disk.BLOCK_E32;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);
			if(Arrays.asList(CALC_METHODS_1).contains(detail.get(Disk.BLOCK_E45).trim())){
				//Als in E.45 is ingevuld een B, H, I, S, V, T of O, dan mag in E.32 niets anders dan 0 zijn ingevuld
				if(decValue!=0){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_PERCENTAGE_DOUANE_RECHT));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		
		//E36 -block
		field=Disk.BLOCK_E36;
		blockValue=detail.get(field).trim();
		boolean e36Error=false;
		CodeVak37 codeVak37=new CodeVak37();
		//Moet zijn gevuld.
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			valid=false;
			e36Error=true;
		}else{
			/*
			 * IMP
			 * Signalering vindt plaats als de eerste twee posities van de code ongelijk zijn aan: 
			 * 01, 40, 41, 42,43, 45, 61, 63 of 67.
			 * 
			 * ENT
			 * Signalering vindt plaats als de eerste twee posities van de code ongelijk zijn aan 71.
			 */
			codeVak37=new CodeVak37(detail.get(field));
			
			if(!direction.equals(Processors.INBOUND)){
				if(!regulation.containsKey(codeVak37.getVoorafgaandeRegeling())){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_PRIOR_REG_NOT_VALID));
					valid=false;
				}
				
				if(!Arrays.asList(REQ_SCHEMES).contains(codeVak37.getGevraagdeRegeling())){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_REQ_SCHEME));
				}
			}else{
				if(!codeVak37.getGevraagdeRegeling().equals("71")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_REQ_SCHEME2));				
				}
			}
		}
		
		//E33 -block
		field=Disk.BLOCK_E33;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);
			boolean e33Error=false;
			if(Arrays.asList(CALC_METHODS_2).contains(detail.get(Disk.BLOCK_E45).trim())){
				//Als in E.45 is ingevuld een B, H, I, S, T of O, dan mag in E.33 niets anders dan 0 zijn ingevuld.
				if(decValue!=0){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_BEDRAG_VRIJSTELLING));
					valid=false;
					e33Error=true;
				}
			}
			
			if(!e33Error && e36Error){
				/*
				 * Signalering volgt als E.33 groter is dan 0 en de derde en vierde positie van E.36 ongelijk is aan 21 of 22. 
				 * Als de laatste drie posities van E.36 gelijk zijn aan B05 is E.33 gelijk aan nihil.
				 */
				
				if(decValue>0){
					if(!codeVak37.getVoorafgaandeRegeling().equals("21")||!codeVak37.getVoorafgaandeRegeling().equals("21")){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_PRIOR_SCHEME));					
					}
					
					if(codeVak37.getVerbijzonderingRegeling().equals("B05")){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_DIFF_SCHEME));							
					}
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
				
		//E35 -block
		field=Disk.BLOCK_E35;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);		
			//decValue=Math.round(decValue*100.0)/100.0;	
			decValue=GpaCalculator.round(decValue, 2);
			
			if(decValue!=0){
				double custDutyAmt=calc.getCustomsDutyAmout();
				if(decValue!=custDutyAmt){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_CUSTOMS_DUTY_AMOUNT,decValue,custDutyAmt)));
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//E37 -block Already checked in Domproc interface
		
		
		//E38 -block
		field=Disk.BLOCK_E38;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			/*
			 * Signalering vindt plaats als: 
			 * 
			 * Een andere code wordt ingevuld dan:
			 * 		- de codes IF1 tot en met IF9 bij gebruik van inlichtingenbladen, of
			 * 		- de code CVO voor een certificaat van oorsprong voor textielproducten, of
			 * 		- de code FP2 voor een erkenningsnummer, de z.g. P2-code, in het kader vande fytosanitaire richtlijn.		
			 */
			if(!Arrays.asList(DOC_CODES).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_DOCUMENT_CODE));
			}
					
		}else{
			// E.38 niet is gevuld en E.39 is gevuld.
			if(!detail.get(Disk.BLOCK_E39).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_E39_NOT_E38));
			}
		}
		
		//E39 -block
		field=Disk.BLOCK_E39;
		blockValue=detail.get(field).trim();
		
		if(blockValue.equals("")){
			//Moet zijn gevuld als E.38 is gevuld
			if(!detail.get(Disk.BLOCK_E38).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,ERROR_E38_NOT_E39));	
				valid=false;
			}
		}
		
			
		//E40 -block		
		field=Disk.BLOCK_E40;
		blockValue=detail.get(field).trim();

		if(!blockValue.equals("")){
			if(!communautairePreferenties.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_COMM_PREF));
				valid=false;
			}else{
				if(!Arrays.asList(COMM_PREF).contains(blockValue)){
					if(detail.get(Disk.BLOCK_E6).trim().equals("") && !detail.get(Disk.BLOCK_E9).trim().equals("*")){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_COMM_PREF,blockValue)));
					}
				}
			}
		}
		
		//E41 -block		
		field=Disk.BLOCK_E41;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!transactionTypes.containsKey(blockValue)){
				/*
				 *  Moet zijn gevuld met de code die van toepassing is uit het domein van tabel A22, 
				 *  onderdeel lgemeen, van het Codeboek Sagitta.
				 */
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_TRANS_TYPE_NOT_VALID));
				valid=false;			
			}
		}else{
			if(!direction.equals(Processors.INBOUND)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
				valid=false;
			}
		}
		
		//E42 -block		
		field=Disk.BLOCK_E42;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!Arrays.asList(DV1_VALUES).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_RELATION_NOT_VALID));
				valid=false;			
			}
		}
		
		//E43 -block		
		field=Disk.BLOCK_E43;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!Arrays.asList(DV1_VALUES).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_PRICE_INFLUENCE_NOT_VALID));
				valid=false;			
			}
		}
		
		//E44 -block		
		field=Disk.BLOCK_E44;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!Arrays.asList(DV1_VALUES).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ROYALTIES_NOT_VALID));
				valid=false;			
			}
		}
		
		//E45 -block		
		field=Disk.BLOCK_E45;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!Arrays.asList(DV1_VALUES2).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_INDICATOR_CUSTOMS_DEBT_NOT_VALID));
				valid=false;			
			}
		}
		
		//E46 -block		
		field=Disk.BLOCK_E46;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(!Arrays.asList(E46_CODES).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_METHOD_OF_VALUATION_NOT_VALID));
				valid=false;			
			}

			//Als in E.46 een 8 is ingevuld, dan dient in veld E.36 (Code Vak 37 Enig Document), derde deelvak E02 te zijn vermeld.			
			if(blockValue.equals("7") && !codeVak37.getVerbijzonderingRegeling().equals("E01")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_DIFF_SCHEME_NOT_VALID,"E01")));
				valid=false;
				
			}

			//Als in E.46 een 8 is ingevuld, dan dient in veld E.36 (Code Vak 37 Enig Document), derde deelvak E02 te zijn vermeld.
			if(blockValue.equals("8") && !codeVak37.getVerbijzonderingRegeling().equals("E02")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_DIFF_SCHEME_NOT_VALID,"E02")));
				valid=false;
			}
		}
		
		//E47 -block
		field=Disk.BLOCK_E47;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			if(blockValue.toUpperCase().equals("J")){
				//Signalering volgt als E.5 is gevuld en in E.47 een J is ingevuld
				if(!detail.get(Disk.BLOCK_E5).trim().equals("")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_INDICATOR_QUOTA));
				}
				
			}else{
				//E.47 niet is gevuld met een een J.
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_INDICATOR_QUOTA_NOT_J));			
			}
		}
		
		//E48 -block
		field=Disk.BLOCK_E48;
		blockValue=detail.get(field).trim();
		int intValue=0;
		try{	
			intValue=Integer.parseInt(blockValue);
			if(intValue>=0 && intValue<=9){			
				//Moet altijd zijn gevuld met 0 als de transactiecode van A.2 gelijk is aan 11nnnn.
				if(intValue!=0 && detail.get(Disk.BLOCK_A2).trim().matches("^11[0-9]{4}")){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_MOT));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//E49 -block
		field=Disk.BLOCK_E49;
		blockValue=detail.get(field).trim();
		try{
		    intValue=Integer.parseInt(blockValue);
		    
		    if(intValue<0 && intValue>1){
		    	//Moet zijn gevuld met een van de codes 0 of 1
		    	this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_CONTAINER));
				valid=false;
		    	
		    }else{
			    //Signalering vindt plaats als de transactiecode van A.2 gelijk is aan 10nnnn of 11nnnn en E.49 ongelijk is aan 0.
			    if(intValue>0 && detail.get(Disk.BLOCK_A2).trim().matches("^10|11[0-9]{4}")){	    	
			    	this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,String.format(WARNING_CONTAINER,detail.get(Disk.BLOCK_A2))));	    	
			    }
		    }		
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
							
	}
	
	private void validateF(){
		currentBlock=Disk.BLOCK_F;
		
		//F1-block	
		String field=Disk.BLOCK_F1;
		String blockValue=detail.get(field).trim();
		
		double decValue=0;
		
		try{
			decValue=Double.parseDouble(detail.get(Disk.BLOCK_F3));
			
			if(!blockValue.equals("")){
				if(!Arrays.asList(ANTIDUMP_TYPES).contains(blockValue)){			
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_ANTIDUMP_TYPE_NOT_VALID,blockValue)));
					valid=false;
				}
			}else{
				if(!detail.get(Disk.BLOCK_F2).equals("")|| decValue>0){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//F2-block	
		field=Disk.BLOCK_F2;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			if(!detail.get(Disk.BLOCK_F1).equals("")|| decValue>0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
			}
		}
		
		//F3-block	
		if(decValue==0.0){
			if(!detail.get(Disk.BLOCK_F1).equals("")|| !detail.get(Disk.BLOCK_F2).equals("")){				
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_ANTIDUMP_AMOUNT));	    	

			}
		}
		
		
	}
	private void validateH(){
		currentBlock=Disk.BLOCK_H;
		
		//H1-block	
		String field=Disk.BLOCK_H1;
		String blockValue=detail.get(field).trim();
		//Mag niet zijn gevuld
		if(!blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ONLY_EMPTY));
			valid=false;
		}
		
		//H2-block	
		field=Disk.BLOCK_H2;
		blockValue=detail.get(field).trim();
		int intValue=Integer.parseInt(blockValue);
		//Mag niet zijn gevuld
		if(intValue!=0){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ONLY_EMPTY));
			valid=false;
		}
		
		// H3/H5 - blocks
		field=Disk.BLOCK_H3;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//Moet zijn gevuld met een geldige datum als H.3 is gevuld.
			if(detail.get(Disk.BLOCK_H5).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_H3_H5,field)));
				valid=false;
			}
		}else{
			//Moet zijn gevuld als H.5 is gevuld.
			if(!detail.get(Disk.BLOCK_H5).trim().equals("")){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_H3_H5,Disk.BLOCK_H5)));
				valid=false;
			}
		}
		
		//H6 and H11-blocks	
		field=Disk.BLOCK_H6;
		blockValue=detail.get(field).trim();
		double decValue=0;
		decValue=Double.parseDouble(blockValue);
		try{
			if(decValue>0.0){
				if(!detail.get(Disk.BLOCK_H11).trim().equals("")){
					/*
					 * Moet zijn gevuld met een maatstafcode die voorkomt in het domein van tabel T08, onderdeel
					 * Invoer, van het Codeboek Sagitta
					 */
					if(!measureCodes.containsKey(detail.get(Disk.BLOCK_H11).trim().equals(""))){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_H,Disk.BLOCK_H11, ERROR_MEASURE_CODES));
						valid=false;
					}
				}else{			
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_H,Disk.BLOCK_H11,ERROR_VERPLICHT));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		//H7 and H12-blocks	
		field=Disk.BLOCK_H7;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);
			if(decValue>0.0){
				if(!detail.get(Disk.BLOCK_H12).trim().equals("")){
					/*
					 * Moet zijn gevuld met een maatstafcode die voorkomt in het domein van tabel T08, onderdeel
					 * Invoer, van het Codeboek Sagitta
					 */
					if(!measureCodes.containsKey(detail.get(Disk.BLOCK_H12).trim().equals(""))){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_H,Disk.BLOCK_H12, ERROR_MEASURE_CODES));
						valid=false;
					}
				}else{			
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,Disk.BLOCK_H,Disk.BLOCK_H12,ERROR_VERPLICHT));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		/*
		 * H8 abd H8 are not required anymore
		 *
		//H8-block	
		field=Disk.BLOCK_H8;
		blockValue=detail.get(field).trim();
		//Mag niet zijn gevuld
		if(!blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ONLY_EMPTY));
			valid=false;
		}
		
		//H9-block	
		field=Disk.BLOCK_H9;
		blockValue=detail.get(field).trim();
		if(blockValue.equals("")){
			 //Als dit veld is gebruikt als afwijkende datum heffingsgrondslagen, moet dit veld zijn gevuld als
			 //E.45 gelijk is aan B, I, J, S, V, T of O.
			if(!Arrays.asList(DV1_VALUES3).contains(detail.get(Disk.BLOCK_E45))){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_VERPLICHT));
				valid=false;			
			}
		}
		*/
		
		//H10-block	
		field=Disk.BLOCK_H10;
		blockValue=detail.get(field).trim();
		CodeVak37 codeVak37=new CodeVak37(detail.get(Disk.BLOCK_E36));
		String landType="bestemming";
		/*
		 * Moet zijn gevuld met een tweeletterige code die voorkomt in het domein van tabel S01, onderdeel
		 * Algemeen, van het Codeboek Sagitta als de eerste twee posities van de code in E.36 bestaan
		 * uit 01.
		 */
		if(codeVak37.getGevraagdeRegeling().equals("01")){
			if(!countries.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_COUNTRY_NOT_VALID,landType)));
				valid=false;
			}
		}
		
		
		//H13 - H16 blocks
		String[] BLOCKS = new String[]{Disk.BLOCK_H13,Disk.BLOCK_H14,Disk.BLOCK_H15,Disk.BLOCK_H16};
		for (int i=0;i<BLOCKS.length;i++){
			field=BLOCKS[i];
			blockValue=detail.get(field).trim();
			/*
			 * Als een code is ingevuld, moet die voorkomen in het domein van tabel T03, onderdeel Invoer,
			 * van het Codeboek Sagitta en niet in
			 * de kolom 'Bijzonderheden' een verwijzing hebben naar GPA veld E.6 of E.7.
			 */
			if(!blockValue.equals("")){
				if(!documentCodesNoLink.containsKey(blockValue)){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_DOCUMENT_CODES));
					valid=false;
				}						
			}		
		}
		
	    //H17-block (not required anymore)
		
		//H18-blocks (already validated before E15 block)			
		
	}
	
	private void validateJ(){
		String currentBlock=Disk.BLOCK_J;
		//J1-block	
		String field=Disk.BLOCK_J1;
		String blockValue=detail.get(field).trim();
		boolean starOn5=false;
		//Is * op de positie 5 aanwezig?
		if(blockValue.length()==5){
			if(blockValue.charAt(4)=='*'){
				blockValue=blockValue.substring(0,4).trim();
				starOn5=true;				
			}
		}		
		if(!aangifteSoorten.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_AANGIFTE_SRT));
			valid=false;
		}
		
		/*
		 * Signalering vindt plaats als op positie 5 van J.1 een * staat en de documentsoort 
		 * ongelijk is aan	N820, N821 of N822.
		 */
		if(starOn5){
			if(!Arrays.asList(DOC_SRT_1).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_AANGIFTE_SRT1));
			}
		}
		
		//J4-block
		field=Disk.BLOCK_J4;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//De mutatiedatum moet in het tijdvak van A.1 liggen.
			if(du.isInPeriod(blockValue, detail.get(Disk.BLOCK_A1).trim())){
				if(!blockValue.equals(detail.get(Disk.BLOCK_K13))){
					//Signalering vindt plaats als de datum ongelijk is aan de mutatiedatum van K.13.
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_AFGIFTE_DATUM));
				}
			}else{
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_DATUM_NOT_IN_PERIOD,"De mutatiedatum")));				
				valid=false;
			}
		}
		
		
	}
	
	private void validateK(){
		String currentBlock=Disk.BLOCK_K;
		//K1-block	
		String field=Disk.BLOCK_K2;
		String blockValue=detail.get(field).trim();		
		if(!blockValue.equals("")){
			if(!Arrays.asList(WET_VERMELDINGEN).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field, String.format(WARNING_WET_VERMELDINGEN,blockValue)));
			}
		}
		
		currentBlock=Disk.BLOCK_K;
		//K3-block	
		field=Disk.BLOCK_K3;
		blockValue=detail.get(field).trim();		
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, ERROR_VERPLICHT));
			valid=false;
		}
		//K4-block	
		field=Disk.BLOCK_K4;
		blockValue=detail.get(field).trim();		
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, ERROR_VERPLICHT));
			valid=false;
		}
		//K4-block	
		field=Disk.BLOCK_K5;
		blockValue=detail.get(field).trim();		
		if(blockValue.equals("")){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, ERROR_VERPLICHT));
			valid=false;
		}

		//K2-block	
		field=Disk.BLOCK_K6;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met een code die voorkomt in het domein van tabel A25, onderdeel Algemeen, 
		 * van het Codeboek Sagitta of met een code die is opgenomen in de tabellen in de paragrafen 5.2.1
		 * en 5.2.2 van de informatiebrochure
		 */
		if(!adminUnits.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, String.format(ERROR_ADMIN_UNIT,blockValue)));
			valid=false;
		}
		
		//K7-block	
		field=Disk.BLOCK_K7;
		blockValue=detail.get(field).trim();
		/*
		 * De toegestane codes zijn: BV, EV, VM, MB, BZ, BM, CO, PR, PV, VN, VD, VT, OE, BI, AF of HW.
		 */
		if(!blockValue.equals("")){
			if(!Arrays.asList(MUTATIE_SOORT).contains(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, ERROR_MUTATIE_NIET_TOEGESTAAN));
				valid=false;				
			}
		}else{
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, ERROR_VERPLICHT));
			valid=false;
		}
		
		//K8, K9, K10-blocks
		validateMatrix();
		
		//K11-block	
		field=Disk.BLOCK_K11;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			/*
			 * Moet zijn gevuld met een van de drieletterige codes uit het domein van tabel S10, onderdeel Algemeen, 
			 * van het Codeboek Sagitta";
			 */
			if(!currencyCodes.containsKey(blockValue)){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CURRENCY_NOT_VALID,blockValue)));
				valid=false;
			}
		}
		
		//K12-block	
		field=Disk.BLOCK_K12;
		blockValue=detail.get(field).trim();
		double decValue=0;
		try{
			decValue=Double.parseDouble(blockValue);		
			if(decValue>0){
				/*
				 * Signalering volgt als K.11 is gevuld met EUR en K.12 niet is gevuld met 1.
				 */
				if(detail.get(Disk.BLOCK_E26).trim().toUpperCase().equals("EUR")){
					if(decValue!=1.0){
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_EXCHANGE_RATE));
					
					}
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//K13-block	
		field=Disk.BLOCK_K13;
		blockValue=detail.get(field).trim();
		if(!blockValue.equals("")){
			//De mutatiedatum moet in het tijdvak van A.1 liggen.
			if(du.isInPeriod(blockValue, detail.get(Disk.BLOCK_A1).trim())){
			}else{
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_DATUM_NOT_IN_PERIOD,"De mutatiedatum")));				
				valid=false;
			}
		}
		
		//K14-block	
		field=Disk.BLOCK_K14;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.
			if(!detail.get(Disk.BLOCK_K7).equals("EV") && !detail.get(Disk.BLOCK_K7).equals("BV")){
				if(decValue<=0){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,""+decValue+"  "+ERROR_ZERO_VALUE));
					valid=false;
				}
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
			
	}
	
 	private void validateM(){
		
		String currentBlock=Disk.BLOCK_M;
		
		//M1-block	
		String field=Disk.BLOCK_M1;
		String blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met een code die voorkomt in het domein van tabel A25, onderdeel Algemeen, 
		 * van het Codeboek Sagitta of met een code die is opgenomen in de tabellen in de paragrafen 5.2.1
		 * en 5.2.2 van de informatiebrochure
		 */
		if(!adminUnits.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field, String.format(ERROR_ADMIN_UNIT,blockValue)));
			valid=false;
		}
		
		//M2-block	
		field=Disk.BLOCK_M2;
		blockValue=detail.get(field).trim();
		int intValue=0;
		try{
			intValue=Integer.parseInt(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.
			
			if(intValue<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		//M3-block	
		field=Disk.BLOCK_M3;
		blockValue=detail.get(field).trim();
		double decValue=0;
		try{
			decValue=Double.parseDouble(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.			
			if(decValue<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		//M4-block	
		field=Disk.BLOCK_M4;
		blockValue=detail.get(field).trim();
		/*
		 * Moet zijn gevuld met een van de drieletterige codes uit het domein van tabel S10, onderdeel
		 * Algemeen, van het Codeboek Sagitta.
		 */
		if(!currencyCodes.containsKey(blockValue)){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_CURRENCY_NOT_VALID,blockValue)));
			valid=false;
		}
		
		//M5-block	
		field=Disk.BLOCK_M5;
		blockValue=detail.get(field).trim();
		try{
		decValue=Double.parseDouble(blockValue);
		if(decValue<=0){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_RATE_VALUE_EMPTY,detail.get(Disk.BLOCK_M4),detail.get(Disk.BLOCK_A1))));
			valid=false;
		}else{
			if(detail.get(Disk.BLOCK_M4).trim().toUpperCase().equals("EUR")){
				if(decValue!=1.0){
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_WARNING,currentBlock,field,WARNING_EXCHANGE_RATE));				
				}
			}
		}
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
		//M7-block	
		field=Disk.BLOCK_M7;
		blockValue=detail.get(field).trim();
		try{
			decValue=Double.parseDouble(blockValue);
			//Moet zijn gevuld met een waarde die groter is dan 0.		
			if(decValue<=0){
				this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,ERROR_ZERO_VALUE));
				valid=false;
			}	
		}catch(NumberFormatException e){
			this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,field,String.format(ERROR_NOT_A_NUMBER,blockValue)));
			valid=false;
			
		}
		
	}

 	private void validateMatrix(){

 	String transactionCode=detail.get(Disk.BLOCK_A2);
 	String k7=detail.get(Disk.BLOCK_K7);
	String k8=detail.get(Disk.BLOCK_K8);
	double k9=Double.parseDouble(detail.get(Disk.BLOCK_K9));
	double k10=Double.parseDouble(detail.get(Disk.BLOCK_K10));
	
	if(!Arrays.asList(K7_ARRAY).contains(k7)){
		int i=Arrays.asList(K7_ARRAY).indexOf(k7);
		if(transactionCode.matches(A2_ARRAY[i])){
			if(K8_REQUIRED[i]){
				if(k8.equals("")){
					//Error K8 required
					this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_K8,ERROR_VERPLICHT));
					valid=false;
				}
			}
			
			if(!K9_K10_ALLOW_PLUS[i] || !K9_K10_ALLOW_MIN[i]){
				
				if(!K9_K10_ALLOW_PLUS[i]){
					if(k9>0){
						//Error K.9 should be negative
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_K9,ERROR_NOT_POSITIVE));
						valid=false;					
					}
					if(k10>0){
						//Error K.10 should be negative
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_K10,ERROR_NOT_POSITIVE));
					}
				}
				
				if(!K9_K10_ALLOW_MIN[i]){
					if(k9<0){
						//Error K.9 should be positive
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_K9,ERROR_NOT_NEGATIVE));
						valid=false;	
					}
					if(k10<0){
						//Error K.10 should be positive
						this.errorsList.add(new DiskErrorsModel(line,searchFieldCur,TYPE_ERROR,currentBlock,Disk.BLOCK_K10,ERROR_NOT_NEGATIVE));
						valid=false;
					}
				}
			}
			
		}
	}
	
 	}
 	
 	
 	
	public boolean isConsistent(ArrayList<TreeMap<String, String>> details) {
		
		if(type.equals(Disk.INVOER)){
			return isConsistentImp(details);
		}else{
			return isConsistentEnt(details);
		}
	}
	
	public boolean isConsistentEnt(ArrayList<TreeMap<String, String>> details) {
		return true;
	}
	
	public boolean isConsistentImp(ArrayList<TreeMap<String, String>> details) {
		
		this.valid=true;
		errorsList.clear();
		String lastArticle = "";
		boolean firstTime=true;
		ArrayList<TreeMap<String,String>> tmp= new ArrayList<TreeMap<String,String>>();
		Protocol.globalStatus=Protocol.STATUS_CHECK_CONSISTENCY;
		int teller=0;
		
		Collections.sort(details,Disk.comparatorArt);
		
		//Article consisyency
		for (TreeMap<String,String> detail : details){ 
			if(!firstTime){				
				if(!lastArticle.equals(detail.get(Disk.BLOCK_E16))){					
					checkConsistentArticle(tmp);						
					tmp.clear();
				}						
			}
			
			firstTime=false;
						
			tmp.add(detail);				
			lastArticle=detail.get(Disk.BLOCK_E16);
			
			Protocol.globalTotalProcessedRecords=teller++;
		}
		
		if(tmp!=null && tmp.size()>0){
			checkConsistentArticle(tmp);
		}
		
		
		//Document type consistency
		Collections.sort(details,Disk.comparatorDocNo);
		tmp.clear();
		String lastDocType = "";
		firstTime=true;
		for (TreeMap<String,String> detail : details){ 
			if(!lastDocType.equals(detail.get(Disk.BLOCK_B1))){				
				checkConsistentDocument(tmp,lastDocType);
				tmp.clear();
			}
			
			firstTime=false;			
			tmp.add(detail);
			lastDocType = detail.get(Disk.BLOCK_B1);
		}
		
		if(tmp!=null && tmp.size()>0){
			checkConsistentDocument(tmp,lastDocType);
		}
				
		return this.valid;

	}
		
	private void checkConsistentArticle(ArrayList<TreeMap<String,String>> records){
		boolean firstTime=true;
		String tariffCode="";
		int refWeight=0;
		double refPrice=0;
		int curWeight=0;
		double curPrice=0;
		for (TreeMap<String,String> temp : records) {
			if(!firstTime){
				if(!tariffCode.equals(temp.get(Disk.BLOCK_E17))){								
					this.errorsList.add(new DiskErrorsModel(0,temp.get(Disk.BLOCK_A3),TYPE_WARNING,Disk.BLOCK_E,Disk.BLOCK_E17,String.format(WARNING_INCONSISTENT_TARIFF,temp.get(Disk.BLOCK_E16),temp.get(Disk.BLOCK_E17))));
				}
				curWeight=getArticleWeight(temp.get(Disk.BLOCK_M7),temp.get(Disk.BLOCK_M2));
				if(refWeight!=curWeight){
					this.errorsList.add(new DiskErrorsModel(0,temp.get(Disk.BLOCK_A3),TYPE_WARNING,Disk.BLOCK_M,Disk.BLOCK_M7,String.format(WARNING_INCONSISTENT_WEIGHT,temp.get(Disk.BLOCK_E16))));
				}

				curPrice=getArticlePrice(temp.get(Disk.BLOCK_E25),temp.get(Disk.BLOCK_E27),temp.get(Disk.BLOCK_M2));				
				if(getDiffPercentage(refPrice,curPrice)>10.00){
					this.errorsList.add(new DiskErrorsModel(0,temp.get(Disk.BLOCK_A3),TYPE_WARNING,Disk.BLOCK_M,Disk.BLOCK_M3,String.format(WARNING_INCONSISTENT_PRICE,temp.get(Disk.BLOCK_E16))));					
				}
			
			}else{
				//Assuming the first occurence will determine tariff code,netweight and price of this article
				tariffCode=temp.get(Disk.BLOCK_E17);
				refWeight=getArticleWeight(temp.get(Disk.BLOCK_M7),temp.get(Disk.BLOCK_M2));
				refPrice=getArticlePrice(temp.get(Disk.BLOCK_E25),temp.get(Disk.BLOCK_E27),temp.get(Disk.BLOCK_M2));
			}
			
			firstTime=false;
			
		}
	}
	
	
	private void checkConsistentDocument(ArrayList<TreeMap<String,String>> details,String type){
		
		ArrayList<TreeMap<String,String>> tmp= new ArrayList<TreeMap<String,String>>();
		boolean firstTime=true;
		String lastDocNo = "";
		int teller=0;
		
		if(type.toUpperCase().equals("ADMIN")){
			for (TreeMap<String,String> detail : details){ 
				if(!firstTime){	
					if(!lastDocNo.equals(detail.get(Disk.BLOCK_B2))){	
						checkConsistentDocumentType(tmp);						
						tmp.clear();
					}
				}
				firstTime=false;			
				tmp.add(detail);
				lastDocNo = detail.get(Disk.BLOCK_B2);
				
				Protocol.globalTotalProcessedRecords=teller++;
			}
			
			if(tmp!=null && tmp.size()>0){
				checkConsistentDocumentType(tmp);
			}
		}	
	}
	
	private void checkConsistentDocumentType(ArrayList<TreeMap<String,String>> records){
		boolean firstTime=true;
		String docDate="";
		
		for (TreeMap<String,String> temp : records) {			
			if(!firstTime){				
				if(!docDate.equals(temp.get(Disk.BLOCK_B4))){
					this.errorsList.add(new DiskErrorsModel(0,temp.get(Disk.BLOCK_A3),TYPE_WARNING,Disk.BLOCK_B,Disk.BLOCK_B4,String.format(WARNING_DOCUMENT_DATE,temp.get(Disk.BLOCK_B4),temp.get(Disk.BLOCK_B2))));				
				}
				
			}else{
				//Assuming the first occurence will determine the document date
				docDate=temp.get(Disk.BLOCK_B4);
			}
			
			firstTime=false;
		}
		
	}
	
	
	private int getArticleWeight(String totalWeight, String quantity){
		if(totalWeight.trim().equals("")){
			totalWeight="0";
		}
		
		if(quantity.trim().equals("")){
			quantity="0";
		}
		
		int retWeight=0;
		try{
			int weight=Integer.parseInt(totalWeight);
			int qty=Integer.parseInt(quantity);
			
			if(qty>0){
				retWeight=weight/qty;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retWeight;
	}
	
	private double getArticlePrice(String totalPrice, String exchangeRate, String quantity){
		double retPrice=0.00;
		
		double price=Double.parseDouble(totalPrice);
		int qty=Integer.parseInt(quantity);
		double rate=Double.parseDouble(exchangeRate);
				
		if(qty>0){
			
			retPrice=price/qty*rate;
		}	
		if(retPrice>0){
			retPrice=round(retPrice,2);
		}
		return retPrice;
	}

	private double getDiffPercentage(double ref, double cur ){		
		
		return ref*cur/100;
	}

	private boolean isValidDates(String transactionDate, String argDate){
			
			boolean valid=true;
			
			switch (du.getDateFromCustoms(transactionDate).compareTo(du.getDateFromCustoms(argDate))) {
			 case -1:  
				 valid = false;
				 //errorCollector.append("Shipment date is before arrival date");	
				 break;
			 case 0:	break;
			 case 1:	break;
			 default:
				valid = false;
				
			}
			
			return valid;
		}
	
	public List<DiskErrorsModel> getErrorsList() {
		return errorsList;
	}
	
	public void setDetail(TreeMap<String, String> detail){
		this.detail=detail;
		calc.setDetail(detail);
	}

	public void setLine(Integer line) {
		this.line = line;
	}


	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	
}
