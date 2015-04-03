package com.foursoft.gpa.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XmlValidator {

	private String xmlFile;
	private String xsdFile;
	
	private String errorMessage;
	
	
	public XmlValidator(){
		
	}
	
	public boolean isValidXml() {

		boolean retCode = true;
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		InputStream xsdIn = null;
		InputStream xmlIn = null;
		try {
			xsdIn = new URI("file:/"+xsdFile.replace(File.separatorChar, '/')).toURL().openStream();
			Source schemaSource = new StreamSource(xsdIn, xsdFile.toString());
			Schema schema = schemaFactory.newSchema(schemaSource);
			Validator schemaValidator = schema.newValidator();
			xmlIn = new URI("file:/"+xmlFile.replace(File.separatorChar, '/')).toURL().openStream();
			try {
				StreamSource source = new StreamSource(xmlIn,xmlFile.toString());
				schemaValidator.validate(source);
			} catch (Exception ex) {
				errorMessage = ex.getMessage();
				retCode = false;
			} finally {
				xmlIn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				xsdIn.close();
			} catch (IOException e) {

			}
		}
		return retCode;

	}
	
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}


	public void setXsdFile(String xsdFile) {
		this.xsdFile = xsdFile;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
	
}
