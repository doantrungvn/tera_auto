package org.terasoluna.qp.app.common.ultils;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XmlDocumentHelper {
	
	private Document document;
	
	private File file;
	
	private XmlDocumentHelper(String path){
		file = new File(path);
		try {
			file.createNewFile();
			document  = DocumentBuilderFactory.newInstance()
												.newDocumentBuilder()
												.parse(file);
		} catch (Exception e) {
			
		} 
	}

	public Document getDocument() {
		return document;
	}
	
	public void persistDocument(){
		if(document!=null && file !=null){
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			try {
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				transformer.transform(source, result);
			} catch (Exception ex) {
			}
		}
	}
	
	
	public static XmlDocumentHelper createNew(String path) {
		return new XmlDocumentHelper(path);
	}
}