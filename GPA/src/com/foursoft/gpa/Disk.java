package com.foursoft.gpa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.foursoft.gpa.db.Mapin;
import com.foursoft.gpa.db.Transcodes;
import com.foursoft.gpa.reporting.beans.ErrorOverviewBean;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.FileUtils;
import com.foursoft.gpa.utils.GpaCalculator;
import com.foursoft.gpa.utils.MapinPersist;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;

public class Disk {

	//Blocks
	public static final String BLOCK_A="A";
	public static final String BLOCK_B="B";
	public static final String BLOCK_D="D";
	public static final String BLOCK_E="E";
	public static final String BLOCK_F="F";
	public static final String BLOCK_G="G";
	public static final String BLOCK_H="H";
	public static final String BLOCK_J="J";
	public static final String BLOCK_K="K";
	public static final String BLOCK_M="M";
	public static final String[]BLOCKS_ALL=new String[]{BLOCK_A,BLOCK_B,BLOCK_D,BLOCK_E,BLOCK_F,BLOCK_G,BLOCK_J,BLOCK_K,BLOCK_M};
	//Fields
	
	public static final String BLOCK_DIRECTION="DIRECTION";
	public static final String BLOCK_A0="A.0";
	public static final String BLOCK_A1="A.1";
	public static final String BLOCK_A2="A.2";
	public static final String BLOCK_A3="A.3";
	public static final String BLOCK_B0="B.0";
	public static final String BLOCK_B1="B.1";
	public static final String BLOCK_B2="B.2";
	public static final String BLOCK_B3="B.3";
	public static final String BLOCK_B4="B.4";
	public static final String BLOCK_B5="B.5";
	public static final String BLOCK_D0="D.0";
	public static final String BLOCK_D1="D.1";
	public static final String BLOCK_D2="D.2";
	public static final String BLOCK_D3="D.3";
	public static final String BLOCK_D4="D.4";
	public static final String BLOCK_D5="D.5";
	public static final String BLOCK_D6="D.6";
	public static final String BLOCK_D7="D.7";
	public static final String BLOCK_D8="D.8";
	public static final String BLOCK_D9="D.9";
	public static final String BLOCK_D10="D.10";
	public static final String BLOCK_D11="D.11";
	public static final String BLOCK_D12="D.12";
	public static final String BLOCK_D13="D.13";
	public static final String BLOCK_D14="D.14";
	public static final String BLOCK_D15="D.15";
	public static final String BLOCK_E0="E.0";
	public static final String BLOCK_E1="E.1";
	public static final String BLOCK_E2="E.2";
	public static final String BLOCK_E3="E.3";
	public static final String BLOCK_E4="E.4";
	public static final String BLOCK_E5="E.5";
	public static final String BLOCK_E6="E.6";
	public static final String BLOCK_E7="E.7";
	public static final String BLOCK_E8="E.8";
	public static final String BLOCK_E9="E.9";
	public static final String BLOCK_E10="E.10";
	public static final String BLOCK_E11="E.11";
	public static final String BLOCK_E12="E.12";
	public static final String BLOCK_E13="E.13";
	public static final String BLOCK_E14="E.14";
	public static final String BLOCK_E15="E.15";
	public static final String BLOCK_E16="E.16";
	public static final String BLOCK_E17="E.17";
	public static final String BLOCK_E18="E.18";
	public static final String BLOCK_E19="E.19";
	public static final String BLOCK_E20="E.20";
	public static final String BLOCK_E21="E.21";
	public static final String BLOCK_E22="E.22";
	public static final String BLOCK_E23="E.23";
	public static final String BLOCK_E24="E.24";
	public static final String BLOCK_E25="E.25";
	public static final String BLOCK_E26="E.26";
	public static final String BLOCK_E27="E.27";
	public static final String BLOCK_E28="E.28";
	public static final String BLOCK_E29="E.29";
	public static final String BLOCK_E30="E.30";
	public static final String BLOCK_E31="E.31";
	public static final String BLOCK_E32="E.32";
	public static final String BLOCK_E33="E.33";
	public static final String BLOCK_E34="E.34";
	public static final String BLOCK_E35="E.35";
	public static final String BLOCK_E36="E.36";
	public static final String BLOCK_E37="E.37";
	public static final String BLOCK_E38="E.38";
	public static final String BLOCK_E39="E.39";
	public static final String BLOCK_E40="E.40";
	public static final String BLOCK_E41="E.41";
	public static final String BLOCK_E42="E.42";
	public static final String BLOCK_E43="E.43";
	public static final String BLOCK_E44="E.44";
	public static final String BLOCK_E45="E.45";
	public static final String BLOCK_E46="E.46";
	public static final String BLOCK_E47="E.47";
	public static final String BLOCK_E48="E.48";
	public static final String BLOCK_E49="E.49";
	public static final String BLOCK_F0="F.0";
	public static final String BLOCK_F1="F.1";
	public static final String BLOCK_F2="F.2";
	public static final String BLOCK_F3="F.3";
	public static final String BLOCK_F4="F.4";
	public static final String BLOCK_F5="F.5";
	public static final String BLOCK_F6="F.6";
	public static final String BLOCK_F7="F.7";
	public static final String BLOCK_G0="G.0";
	public static final String BLOCK_G1="G.1";
	public static final String BLOCK_G2="G.2";
	public static final String BLOCK_G3="G.3";
	public static final String BLOCK_G4="G.4";
	public static final String BLOCK_G5="G.5";
	public static final String BLOCK_G6="G.6";
	public static final String BLOCK_G7="G.7";
	public static final String BLOCK_G8="G.8";
	public static final String BLOCK_G9="G.9";
	public static final String BLOCK_G10="G.10";
	public static final String BLOCK_G11="G.11";
	public static final String BLOCK_G12="G.12";
	public static final String BLOCK_G13="G.13";
	public static final String BLOCK_G14="G.14";
	public static final String BLOCK_G15="G.15";
	public static final String BLOCK_H0="H.0";
	public static final String BLOCK_H1="H.1";
	public static final String BLOCK_H2="H.2";
	public static final String BLOCK_H3="H.3";
	public static final String BLOCK_H4="H.4";
	public static final String BLOCK_H5="H.5";
	public static final String BLOCK_H6="H.6";
	public static final String BLOCK_H7="H.7";
	public static final String BLOCK_H8="H.8";
	public static final String BLOCK_H9="H.9";
	public static final String BLOCK_H10="H.10";
	public static final String BLOCK_H11="H.11";
	public static final String BLOCK_H12="H.12";
	public static final String BLOCK_H13="H.13";
	public static final String BLOCK_H14="H.14";
	public static final String BLOCK_H15="H.15";
	public static final String BLOCK_H16="H.16";
	public static final String BLOCK_H17="H.17";
	public static final String BLOCK_H18="H.18";
	public static final String BLOCK_J0="J.0";
	public static final String BLOCK_J1="J.1";
	public static final String BLOCK_J2="J.2";
	public static final String BLOCK_J3="J.3";
	public static final String BLOCK_J4="J.4";
	public static final String BLOCK_J5="J.5";
	public static final String BLOCK_K0="K.0";
	public static final String BLOCK_K1="K.1";
	public static final String BLOCK_K2="K.2";
	public static final String BLOCK_K3="K.3";
	public static final String BLOCK_K4="K.4";
	public static final String BLOCK_K5="K.5";
	public static final String BLOCK_K6="K.6";
	public static final String BLOCK_K7="K.7";
	public static final String BLOCK_K8="K.8";
	public static final String BLOCK_K9="K.9";
	public static final String BLOCK_K10="K.10";
	public static final String BLOCK_K11="K.11";
	public static final String BLOCK_K12="K.12";
	public static final String BLOCK_K13="K.13";
	public static final String BLOCK_K14="K.14";
	public static final String BLOCK_K15="K.15";
	public static final String BLOCK_K16="K.16";
	public static final String BLOCK_K17="K.17";
	public static final String BLOCK_K18="K.18";
	public static final String BLOCK_K19="K.19";
	public static final String BLOCK_K20="K.20";
	public static final String BLOCK_K21="K.21";
	public static final String BLOCK_K22="K.22";
	public static final String BLOCK_K23="K.23";
	public static final String BLOCK_M0="M.0";
	public static final String BLOCK_M1="M.1";
	public static final String BLOCK_M2="M.2";
	public static final String BLOCK_M3="M.3";
	public static final String BLOCK_M4="M.4";
	public static final String BLOCK_M5="M.5";
	public static final String BLOCK_M6="M.6";
	public static final String BLOCK_M7="M.7";
	public static final String BLOCK_M8="M.8";
	public static final String BLOCK_M9="M.9";
	public static final String BLOCK_M10="M.10";
	public static final String BLOCK_M11="M.11";
	public static final String BLOCK_M12="M.12";
	public static final String BLOCK_M13="M.13";
	public static final String BLOCK_M14="M.14";
	public static final String BLOCK_M15="M.15";
	public static final String BLOCK_M16="M.16";
	
	public static final String END_OF_LINE="\r\n";
	
	public static final String ENTREPOT="ENT";
	public static final String INVOER="IMP";
	
	private String customsFileName=ENTREPOT;
	
	private ArrayList<TreeMap<String, String>> details;
	
	private ArrayList<TreeMap<String, String>> records;
	
	private ArrayList<ErrorOverviewBean> errorCollection;
	
	private Transcodes transcodes=new Transcodes();
	
	private int method;
	
	private String diskFileName="";
	
	private List<DiskErrorsModel> errorsList= new ArrayList<DiskErrorsModel>();
	
	private int numberOfRecords=0;
	//Totals
	private double totalImportDuties=0;
	
	private double totalAmountRelief=0;
	
	private double totalSpecificCustomsDuties=0;
	
	private double totalCustomsDuties=0;
	
	private double totalChargesPreliminary=0;
	
	private double totalChargesFinal=0;
	
	private double totalAgricultureLevies=0;
	
	private double totalAmountVat=0;
	
	private double totalCustomsValue=0;
	
	private int totalTransactionCodes=0;
	
	private MapinPersist mp;

	public Disk(int method) {
		details=new ArrayList<TreeMap<String, String>>();
		records = new ArrayList<TreeMap<String, String>>();
		this.method=method;
		
	}
	
	public void loadPersistData(){
		transcodes.loadTranscodesTable();
		mp= new MapinPersist(customsFileName);
	}
	
	public void addDetails(TreeMap<String, String> detail){
		details.add(detail);
	}
	
	public ArrayList<ErrorOverviewBean> getErrorOverview() {
		return errorCollection;
	}
	
	public double getTotalAmountRelief() {
		return totalAmountRelief;
	}

	public double getTotalSpecificCustomsDuties() {
		return totalSpecificCustomsDuties;
	}

	public double getTotalCustomsDuties() {
		return totalCustomsDuties;
	}

	public double getTotalChargesPreliminary() {
		return totalChargesPreliminary;
	}

	public double getTotalChargesFinal() {
		return totalChargesFinal;
	}

	public double getTotalAgricultureLevies() {
		return totalAgricultureLevies;
	}

	public double getTotalAmountVat() {
		return totalAmountVat;
	}

	public double getTotalCustomsValue() {
		return totalCustomsValue;
	}

	public int getTotalTransactionCodes() {
		return totalTransactionCodes;
	}
	
	public int getNumberOfDiskRecords(){
		
		return this.numberOfRecords;
	}
		
	public int getNumberOfDeclaration(){

		return records.size();
	}
		
	public void setCustomsFileName(String customsFileName) {
		this.customsFileName = customsFileName;
	}
	
	public List<DiskErrorsModel> getErrorsList() {
		return errorsList;
	}

	public boolean isValid(String type){
		TreeMap<String, String> detail=new TreeMap<String, String>();
		boolean retCode= true;
		boolean retCodeConsist=true;
		ControleBrochure cb= new ControleBrochure(type);
		
		if (details != null && details.size() > 0) {
			Protocol.globalStatus=Protocol.STATUS_VALIDATING;
			try {
				for (int i = 0; i < details.size(); i++) {										
					sleep(100);
									
					detail=details.get(i);					
					cb.setDetail(detail);
					cb.setLine(i+1);										
					cb.setDirection(detail.get(Disk.BLOCK_DIRECTION));
					
					if(cb.isValidDetail()){
						if(formatBlock(detail)){
							//DO nothing
						}else{
							retCode= true;
						}
					}else{				
						retCode=false;
					}
					
					if(cb.getErrorsList()!=null && cb.getErrorsList().size()>0){
						errorsList.addAll(cb.getErrorsList());
					}
					
					Protocol.globalTotalProcessedRecords=i;
	
				}
				
					
				if(!cb.isConsistent(details)){
					retCodeConsist=false;
				}
				if(cb.getErrorsList()!=null && cb.getErrorsList().size()>0){
					errorsList.addAll(cb.getErrorsList());
				}
			}catch (Exception e){
				e.printStackTrace();
				errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","",e.getMessage()));
				retCode=false;
			}			
			
		}
		
		return retCode && retCodeConsist;
	}
	
	/**
	 * This method will creates records ArrayList containing all data for DISK
	 */
	private boolean formatBlock(TreeMap<String, String> detail){
		
		String blockA2=detail.get(Disk.BLOCK_A2);		
		if(customsFileName.equals(Disk.INVOER)){	
			blockA2=transcodes.getProcessCode(detail.get(Disk.BLOCK_A2));
		}	
		List<String> blocks=transcodes.getTransBlocksList(blockA2);
		
		StringBuffer blockBuffer=new StringBuffer("");
		TreeMap<String, String> diskRecord=initRecord(blocks);
		if(blocks==null){
			//TODO write error message
			return false;
		}else{
			String prevBlock="";
			boolean firstTime=true;
			TreeMap<String, String> mapingDetail=new TreeMap<String, String>();	
			//ArrayList<TreeMap<String, String>> mappingDetails=mapin.getMappingDetails(customsFileName,blocks.get(Transcodes.DB_TRANS_BLCKS));
			ArrayList<TreeMap<String, String>> mappingDetails=mp.getMappingDetails(blocks);
			for (int i = 0; i < mappingDetails.size(); i++) {	
				mapingDetail=mappingDetails.get(i);				
				if(!firstTime){
					if(!prevBlock.equals(mapingDetail.get(Mapin.DB_MAPIN_BLCK))){						
						diskRecord.put(prevBlock,blockBuffer.toString());
						blockBuffer=new StringBuffer("");
					}
				}else{
					firstTime=false;
				}
				
				blockBuffer.append(getFormatted(detail.get(mapingDetail.get(Mapin.DB_MAPIN_FLD_NAME)),mapingDetail,detail.get(Disk.BLOCK_A3)));
				
				prevBlock=mapingDetail.get(Mapin.DB_MAPIN_BLCK);
				
			}			
			diskRecord.put(prevBlock,blockBuffer.toString());
			//System.out.println(blockBuffer.toString());
			
			records.add(diskRecord);
			//diskRecord=new TreeMap<String, String>();
		}
		
		return true;
	}
	
	/**
	 * This method initializes one record for DISK
	 */
	private TreeMap<String, String> initRecord(List<String> blocks){
		TreeMap<String, String> diskRecord=new TreeMap<String, String>();
		TreeMap<String, String> detail=new TreeMap<String, String>();	
		
		ArrayList<TreeMap<String, String>> mappingDetails =null;
		
		if(method==Processors.DISK_METHOD_1){
			//Only for method 1 all block required.
			mappingDetails=mp.getMappingDetails();
		}else{
			mappingDetails=mp.getMappingDetails(blocks);
		}
		//String [] mandatoryBlocks=transcodes.getBlocksArray(transcode);
		
		String prevBlock="";
		boolean firstTime=true;
		int blockLength=0;
		for (int i = 0; i < mappingDetails.size(); i++) {
			detail=mappingDetails.get(i);
			
			if(!firstTime){
				if(!prevBlock.equals(detail.get(Mapin.DB_MAPIN_BLCK))){
					String empty=String.format("%-"+blockLength+"s", "");
					diskRecord.put(prevBlock,empty);	
					blockLength=0;
				}
			}else{
				firstTime=false;
			}
			
			int fieldLength=Integer.parseInt(detail.get(Mapin.DB_MAPIN_FLD_LN));
			int decimals=Integer.parseInt(detail.get(Mapin.DB_MAPIN_DEC));
			int allowNegative=Integer.parseInt(detail.get(Mapin.DB_MAPIN_ALLOW_NEG));
			blockLength+=fieldLength+decimals+allowNegative;
			
			prevBlock=detail.get(Mapin.DB_MAPIN_BLCK);
			
			
		}
		String empty=String.format("%-"+blockLength+"s", "");
		diskRecord.put(prevBlock,empty);
				
		return diskRecord;
	}
	
	/**
	 * Returns field formatted according to the setup in Mapin table.
	 */
	private String getFormatted(String field,TreeMap<String, String> detail, String a3){
				
		int fieldLength=Integer.parseInt(detail.get(Mapin.DB_MAPIN_FLD_LN));
		int decimals=Integer.parseInt(detail.get(Mapin.DB_MAPIN_DEC));
		int allowNegative=Integer.parseInt(detail.get(Mapin.DB_MAPIN_ALLOW_NEG));
		field=field.trim();
		
		//Format String field
		if(detail.get(Mapin.DB_MAPIN_FLD_TYPE).equals("ALFA")){
			
			
			if(field.length()<fieldLength){
				field=String.format("%-"+fieldLength+"s", field);
			}else{
				try{
					field=field.substring(0,fieldLength);
				}catch(Exception ex){
					System.out.println("ERROR IN FIELD!!!  "+detail.get(Mapin.DB_MAPIN_FLD_NAME) +" --> "+field);
				}
			}
		}
		//Format int field
		if(detail.get(Mapin.DB_MAPIN_FLD_TYPE).equals("DIGIT")){
			String sign="";
			int fieldNum=0;
			//System.out.println(detail.get(Mapin.DB_MAPIN_FLD_NAME));
			
			//Set field to zero when empty to avoid error during parseInt.
			if(field.trim().equals("")){
				field="0";
			}
			
			//When decimal (decimal point detected in the string) then round-up to the closest int.
			if(field.contains(".")){
				StringTokenizer st = new StringTokenizer(field,".");
				int intPart=Integer.parseInt(st.nextToken());
				int decPart=Integer.parseInt(st.nextToken());
				fieldNum=decPart>0?intPart+1:intPart;
			}else{
				try{
					fieldNum=Integer.parseInt(field);
				}
				catch(Exception ex){				
					System.out.println("Error in: "+detail.get(Mapin.DB_MAPIN_FLD_NAME));
					System.out.println(a3);
					ex.printStackTrace();
					
				}
			}
			//If allowed use always the sign in front of number(+ or -)		
			if(allowNegative==1){
				sign="+";
				if(fieldNum<0){
					 sign="-";
					 fieldNum=fieldNum*-1;
				}
			}
			field=""+fieldNum;
			
			field=sign+String.format("%"+fieldLength+"s", field).replace(' ', '0');
		}
		//Format double field
		if(detail.get(Mapin.DB_MAPIN_FLD_TYPE).equals("DECIMAL")){
			String sign="";
			if(field.trim().equals("")){
				field="0.0";
			}
			//round to number of decimals
			double fieldNum=GpaCalculator.round(Double.parseDouble(field),decimals);
			
			if(allowNegative==1){
				sign="+";
				if(fieldNum<0){
					 sign="-";
					 fieldNum=fieldNum*-1; 
				}
			}
			field=Double.toString(fieldNum);
			
			String intPart=field;
			String decPart="";
			if(field.contains(".")){
				StringTokenizer st = new StringTokenizer(field,".");
				intPart=st.nextToken();
				decPart=st.nextToken();
			}
			
			intPart=String.format("%"+fieldLength+"s", intPart).replace(' ', '0');
			decPart=String.format("%-"+decimals+"s", decPart).replace(' ', '0');
			
			field=sign+intPart+decPart;			
		}			
		return field;
	}
	
	/**
	 * This method will creates and zip DISK file.
	 */
	public boolean create(){
		
		TreeMap<String, String> recordDetail=new TreeMap<String, String>();
		String blockSeparator="";
		String tmpLine="";
		boolean created=false;		
		switch (method) {
		case 1:
			break;
		case 2:
			blockSeparator=END_OF_LINE;
			break;
		case 3:
			break;
		default:
			break;
		}
		
		StringBuffer line=new StringBuffer("");
		//Create txt file
		String fileName=FileUtils.getFileName(FileUtils.DISK);
		
		BufferedWriter output =null;
		File file = new File(fileName+".txt");
		try {
			output = new BufferedWriter(new FileWriter(file));	
			
			Protocol.globalTotalRecords=records.size();
			Protocol.globalStatus=Protocol.STATUS_CREATING;
			
			for (int i = 0; i < records.size(); i++) {
				sleep(100);
				//In the record format already required blocks
				recordDetail=records.get(i);
				//Retrive blocks in sorted order
				
				ArrayList<String> list=new ArrayList<String>(recordDetail.keySet());
				Collections.sort(list);
				
				for(String tmp:list){
					tmpLine=recordDetail.get(tmp);
					
					line.append(tmpLine);
					line.append(blockSeparator);
					if(blockSeparator.equals(END_OF_LINE)){
						numberOfRecords++;
					}
				}
				
				/*
				Vector<String> v = new Vector<String>(recordDetail.keySet());
				
			    Collections.sort(v);
			    Iterator<String>  it = v.iterator();
			    while (it.hasNext()) {
			    	
			    	tmpLine=recordDetail.get((String)it.next());

					line.append(tmpLine);
					line.append(blockSeparator);
					if(blockSeparator.equals(END_OF_LINE)){
						numberOfRecords++;
					}
			    }
			    
			    */
			    if(!blockSeparator.equals(END_OF_LINE)){
			    	line.append(END_OF_LINE);
			    }
				output.write(line.toString());
				line=new StringBuffer("");
				numberOfRecords++;
				
				Protocol.globalTotalProcessedRecords=i;				
			}
			
			created=true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		
		//ZIP file created in previous step
		if(created){
			FileInputStream in =null;
			ZipOutputStream out = null;		
			 try {
				// input file 
				in = new FileInputStream(fileName+".txt");
				// out put file 
				String zipFileName=fileName+".zip";			
		        out = new ZipOutputStream(new FileOutputStream(zipFileName));
		        out.setMethod(ZipOutputStream.DEFLATED );
		        out.setLevel(5);
		        // name the file inside the zip  file 
		        out.putNextEntry(new ZipEntry(this.customsFileName)); 
		        // buffer size
		        byte[] b = new byte[1024];
		        int count;

		        while ((count = in.read(b)) > 0) {
		            out.write(b, 0, count);
		        }
		        
		        this.diskFileName=new File(zipFileName).getName();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					out.close();
				} catch (Exception e) {
				}
				
				try {
					in.close();
				} catch (Exception e) {

				}
			}

		}		
		return created;
	}

	/**
	 * This method will creates DISK error file file.
	 */
	public String createErrorLog(){
		
		//Create txt file
		String errorLogFileName=FileUtils.getFileName(FileUtils.LOG)+".txt";
		
		BufferedWriter output =null;
		File file = new File(errorLogFileName);
		
		try {
			output = new BufferedWriter(new FileWriter(file));
			Iterator<DiskErrorsModel> iterator = errorsList.iterator();
			while (iterator.hasNext()) {
				output.write(iterator.next().toString()+"\r\n");
			}
		} catch (IOException e) {
			
		}finally{
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		
		return file.getName();
	}
	
	public void calculateTotals(){
		
		totalImportDuties=0;
		totalAmountRelief=0;
		totalSpecificCustomsDuties=0;
		totalCustomsDuties=0;
		totalChargesPreliminary=0;		
		totalChargesFinal=0;
		totalAgricultureLevies=0;
		totalAmountVat=0;
		totalCustomsValue=0;
		totalTransactionCodes=0;
		
		TreeMap<String, String> detail=new TreeMap<String, String>();
		if (details != null && details.size() > 0) {
			for (int i = 0; i < details.size(); i++) {
				detail=details.get(i);
				totalImportDuties+=getDouble(detail.get(Disk.BLOCK_E35))-getDouble(detail.get(Disk.BLOCK_E33));
				totalAmountRelief+=getDouble(detail.get(Disk.BLOCK_E33));
				totalSpecificCustomsDuties+=getDouble(detail.get(Disk.BLOCK_E34));
				totalCustomsDuties+=getDouble(detail.get(Disk.BLOCK_E35));
				totalAgricultureLevies+=getDouble(detail.get(Disk.BLOCK_F4));
				totalAmountVat+=getDouble(detail.get(Disk.BLOCK_F5));
				totalCustomsValue+=getDouble(detail.get(Disk.BLOCK_E31));
				totalTransactionCodes+=getDouble(detail.get(Disk.BLOCK_A2));
				if(detail.get(Disk.BLOCK_F1).equals(Processors.CHARGES_PRELIMINARY)){
					totalChargesPreliminary+=getDouble(detail.get(Disk.BLOCK_F3));
				}
				if(detail.get(Disk.BLOCK_F1).equals(Processors.CHARGES_FINAL)){
					totalChargesFinal+=getDouble(detail.get(Disk.BLOCK_F3));
				}
				
			}
		}
	}
		
	public double getTotalImportDuties(){
		return this.totalImportDuties;
	}
	
	public TreeMap<String, String> getEmptyDetail(){
		TreeMap<String, String> detail= new TreeMap<String, String>();
		detail.put(Disk.BLOCK_A0, "");
		detail.put(Disk.BLOCK_A1, "");
		detail.put(Disk.BLOCK_A2, "");
		detail.put(Disk.BLOCK_A3, "");
		detail.put(Disk.BLOCK_B0, "");
		detail.put(Disk.BLOCK_B1, "");
		detail.put(Disk.BLOCK_B2, "");
		detail.put(Disk.BLOCK_B3, "");
		detail.put(Disk.BLOCK_B4, "");
		detail.put(Disk.BLOCK_B5, "");
		detail.put(Disk.BLOCK_D0, "");
		detail.put(Disk.BLOCK_D1, "");
		detail.put(Disk.BLOCK_D2, "");
		detail.put(Disk.BLOCK_D3, "");
		detail.put(Disk.BLOCK_D4, "");
		detail.put(Disk.BLOCK_D5, "");
		detail.put(Disk.BLOCK_D6, "");
		detail.put(Disk.BLOCK_D7, "");
		detail.put(Disk.BLOCK_D8, "");
		detail.put(Disk.BLOCK_D9, "");
		detail.put(Disk.BLOCK_D10, "");
		detail.put(Disk.BLOCK_D11, "");
		detail.put(Disk.BLOCK_D12, "");
		detail.put(Disk.BLOCK_D13, "");
		detail.put(Disk.BLOCK_D14, "");
		detail.put(Disk.BLOCK_D15, "");
		detail.put(Disk.BLOCK_E0, "");
		detail.put(Disk.BLOCK_E1, "");
		detail.put(Disk.BLOCK_E2, "");
		detail.put(Disk.BLOCK_E3, "");
		detail.put(Disk.BLOCK_E4, "");
		detail.put(Disk.BLOCK_E5, "");
		detail.put(Disk.BLOCK_E6, "");
		detail.put(Disk.BLOCK_E7, "");
		detail.put(Disk.BLOCK_E8, "");
		detail.put(Disk.BLOCK_E9, "");
		detail.put(Disk.BLOCK_E10, "");
		detail.put(Disk.BLOCK_E11, "");
		detail.put(Disk.BLOCK_E12, "");
		detail.put(Disk.BLOCK_E13, "");
		detail.put(Disk.BLOCK_E14, "");
		detail.put(Disk.BLOCK_E15, "");
		detail.put(Disk.BLOCK_E16, "");
		detail.put(Disk.BLOCK_E17, "");
		detail.put(Disk.BLOCK_E18, "");
		detail.put(Disk.BLOCK_E19, "");
		detail.put(Disk.BLOCK_E20, "");
		detail.put(Disk.BLOCK_E21, "");
		detail.put(Disk.BLOCK_E22, "");
		detail.put(Disk.BLOCK_E23, "");
		detail.put(Disk.BLOCK_E24, "");
		detail.put(Disk.BLOCK_E25, "");
		detail.put(Disk.BLOCK_E26, "");
		detail.put(Disk.BLOCK_E27, "");
		detail.put(Disk.BLOCK_E28, "");
		detail.put(Disk.BLOCK_E29, "");
		detail.put(Disk.BLOCK_E30, "");
		detail.put(Disk.BLOCK_E31, "");
		detail.put(Disk.BLOCK_E32, "");
		detail.put(Disk.BLOCK_E33, "");
		detail.put(Disk.BLOCK_E34, "");
		detail.put(Disk.BLOCK_E35, "");
		detail.put(Disk.BLOCK_E36, "");
		detail.put(Disk.BLOCK_E37, "");
		detail.put(Disk.BLOCK_E38, "");
		detail.put(Disk.BLOCK_E39, "");
		detail.put(Disk.BLOCK_E40, "");
		detail.put(Disk.BLOCK_E41, "");
		detail.put(Disk.BLOCK_E42, "");
		detail.put(Disk.BLOCK_E43, "");
		detail.put(Disk.BLOCK_E44, "");
		detail.put(Disk.BLOCK_E45, "");
		detail.put(Disk.BLOCK_E46, "");
		detail.put(Disk.BLOCK_E47, "");
		detail.put(Disk.BLOCK_E48, "");
		detail.put(Disk.BLOCK_E49, "");
		detail.put(Disk.BLOCK_F0, "");
		detail.put(Disk.BLOCK_F1, "");
		detail.put(Disk.BLOCK_F2, "");
		detail.put(Disk.BLOCK_F3, "");
		detail.put(Disk.BLOCK_F4, "");
		detail.put(Disk.BLOCK_F5, "");
		detail.put(Disk.BLOCK_F6, "");
		detail.put(Disk.BLOCK_F7, "");
		detail.put(Disk.BLOCK_G0, "");
		detail.put(Disk.BLOCK_G1, "");
		detail.put(Disk.BLOCK_G2, "");
		detail.put(Disk.BLOCK_G3, "");
		detail.put(Disk.BLOCK_G4, "");
		detail.put(Disk.BLOCK_G5, "");
		detail.put(Disk.BLOCK_G6, "");
		detail.put(Disk.BLOCK_G7, "");
		detail.put(Disk.BLOCK_G8, "");
		detail.put(Disk.BLOCK_G9, "");
		detail.put(Disk.BLOCK_G10, "");
		detail.put(Disk.BLOCK_G11, "");
		detail.put(Disk.BLOCK_G12, "");
		detail.put(Disk.BLOCK_G13, "");
		detail.put(Disk.BLOCK_G14, "");
		detail.put(Disk.BLOCK_G15, "");
		detail.put(Disk.BLOCK_H0, "");
		detail.put(Disk.BLOCK_H1, "");
		detail.put(Disk.BLOCK_H2, "");
		detail.put(Disk.BLOCK_H3, "");
		detail.put(Disk.BLOCK_H4, "");
		detail.put(Disk.BLOCK_H5, "");
		detail.put(Disk.BLOCK_H6, "");
		detail.put(Disk.BLOCK_H7, "");
		detail.put(Disk.BLOCK_H8, "");
		detail.put(Disk.BLOCK_H9, "");
		detail.put(Disk.BLOCK_H10, "");
		detail.put(Disk.BLOCK_H11, "");
		detail.put(Disk.BLOCK_H12, "");
		detail.put(Disk.BLOCK_H13, "");
		detail.put(Disk.BLOCK_H14, "");
		detail.put(Disk.BLOCK_H15, "");
		detail.put(Disk.BLOCK_H16, "");
		detail.put(Disk.BLOCK_H17, "");
		detail.put(Disk.BLOCK_H18, "");
		detail.put(Disk.BLOCK_J0, "");
		detail.put(Disk.BLOCK_J1, "");
		detail.put(Disk.BLOCK_J2, "");
		detail.put(Disk.BLOCK_J3, "");
		detail.put(Disk.BLOCK_J4, "");
		detail.put(Disk.BLOCK_J5, "");
		detail.put(Disk.BLOCK_K0, "");
		detail.put(Disk.BLOCK_K1, "");
		detail.put(Disk.BLOCK_K2, "");
		detail.put(Disk.BLOCK_K3, "");
		detail.put(Disk.BLOCK_K4, "");
		detail.put(Disk.BLOCK_K5, "");
		detail.put(Disk.BLOCK_K6, "");
		detail.put(Disk.BLOCK_K7, "");
		detail.put(Disk.BLOCK_K8, "");
		detail.put(Disk.BLOCK_K9, "");
		detail.put(Disk.BLOCK_K10, "");
		detail.put(Disk.BLOCK_K11, "");
		detail.put(Disk.BLOCK_K12, "");
		detail.put(Disk.BLOCK_K13, "");
		detail.put(Disk.BLOCK_K14, "");
		detail.put(Disk.BLOCK_K15, "");
		detail.put(Disk.BLOCK_K16, "");
		detail.put(Disk.BLOCK_K17, "");
		detail.put(Disk.BLOCK_K18, "");
		detail.put(Disk.BLOCK_K19, "");
		detail.put(Disk.BLOCK_K20, "");
		detail.put(Disk.BLOCK_K21, "");
		detail.put(Disk.BLOCK_K22, "");
		detail.put(Disk.BLOCK_K23, "");
		detail.put(Disk.BLOCK_M0, "");
		detail.put(Disk.BLOCK_M1, "");
		detail.put(Disk.BLOCK_M2, "");
		detail.put(Disk.BLOCK_M3, "");
		detail.put(Disk.BLOCK_M4, "");
		detail.put(Disk.BLOCK_M5, "");
		detail.put(Disk.BLOCK_M6, "");
		detail.put(Disk.BLOCK_M7, "");
		detail.put(Disk.BLOCK_M8, "");
		detail.put(Disk.BLOCK_M9, "");
		detail.put(Disk.BLOCK_M10, "");
		detail.put(Disk.BLOCK_M11, "");
		detail.put(Disk.BLOCK_M12, "");
		detail.put(Disk.BLOCK_M13, "");
		detail.put(Disk.BLOCK_M14, "");
		detail.put(Disk.BLOCK_M15, "");
		detail.put(Disk.BLOCK_M16, "");
		return detail;
	}
	
	private double getDouble(String val){
		double nbr=0;
		
		try{
			nbr =Double.parseDouble(val);	

		}catch(Exception ex){
			
		}
		return nbr;
	}

	public String getDiskFileName() {
		return diskFileName;
	}
	
	
	public static Comparator<TreeMap<String,String>> comparatorArt = new Comparator<TreeMap<String,String>>() {

		@Override
		public int compare(TreeMap<String, String> det1,	TreeMap<String, String> det2) {
			
			//order by article name
			return det1.get(BLOCK_E16).compareTo(det2.get(BLOCK_E16));		
		}
		
	};
	
	public static Comparator<TreeMap<String,String>> comparatorDocNo = new Comparator<TreeMap<String,String>>() {

		@Override
		public int compare(TreeMap<String, String> det1,TreeMap<String, String> det2) {
			
			//order by doc number
			return (det1.get(BLOCK_B1)+det1.get(BLOCK_B2)).compareTo(det2.get(BLOCK_B1)+det2.get(BLOCK_B2));		
		}
		
	};

		

	public static synchronized void sleep(long delay){
		try
	    {
	        //Thread.sleep(msec);
			TimeUnit.MICROSECONDS.sleep(delay);
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }

	}
	
	public static synchronized void sleepNano(long delay){
		try
	    {
	        //Thread.sleep(msec);
			TimeUnit.NANOSECONDS.sleep(10);
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }

	}
	
}	
