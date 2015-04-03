package com.foursoft.gpa.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.foursoft.gpa.db.Femart;
import com.foursoft.gpa.db.Femimp;
import com.foursoft.gpa.db.Feminb;
import com.foursoft.gpa.db.Femrel;
import com.foursoft.gpa.db.Femtra;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.FileProcessors;
import com.foursoft.gpa.utils.XmlDataProcessor;
import com.foursoft.gpa.utils.XmlHandler;
import com.foursoft.gpa.utils.XmlValidator;


public class XmlFilesProcessor extends FileProcessors {

	private XmlDataProcessor proc;
	private static final String EXTENSION = "xml";
	
	@Override
	public void init() {
		setExtention(EXTENSION);		
	}

	@Override
	public boolean loadFile(String fileName) {

		setLocked(false);
		//parse xml file 
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();			
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XmlHandler handler = new XmlHandler();
			saxParser.parse(new File(fileName), handler);
			
			if(handler.getStartTag()==null){
				this.errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","","Root tag is not found"));
				return false;
			}
			String xsdFileName=handler.getStartTag().toLowerCase();
							
			File currentFile = new File(".");
			XmlValidator xml=new XmlValidator();
			
			try {
				String xsdFile=currentFile.getCanonicalPath()+"\\xsd\\"+xsdFileName+".xsd";
				
				xml.setXmlFile(fileName);
				xml.setXsdFile(xsdFile);
				
				//validate xml against xsd
				if(!xml.isValidXml()){
					System.out.println(xml.getErrorMessage());
					this.errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","",xml.getErrorMessage()));
					return false;
				}					
			}catch (Exception e) {
				e.printStackTrace();
				this.errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","",e.toString()));
				return false;
			}finally{
				
			}
			
			ArrayList<TreeMap<String, String>> records=handler.getRecords();			
			if(handler.getStartTag().toUpperCase().equals(Feminb.DB_FEMINB)){
				proc= new XmlToInboundProcessor();
			}else if(handler.getStartTag().toUpperCase().equals(Femimp.DB_FEMIMP)){
				proc= new XmlToImportProcessor();
			}else if(handler.getStartTag().toUpperCase().equals(Femtra.DB_FEMTRA)){
				proc= new XmlToTransitProcessor();
			}else if(handler.getStartTag().toUpperCase().equals(Femart.DB_FEMART)){
				proc=new XmlArticleProcessor();
			}else if(handler.getStartTag().toUpperCase().equals(Femrel.DB_FEMREL)){
				proc=new XmlAccountProcessor();
			}else{
				this.errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","","Interface file is not defined."));
				return false;
			}
			proc.setRecord(records);
			proc.setFileName(fileName);
			proc.Process();
			
			if(proc.isLocked()){
				setLocked(true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.errorsList.add(new DiskErrorsModel(0,"",ControleBrochure.TYPE_ERROR,"","",e.toString()));
			return false;
		}
		
		return true;
		
	}

}
