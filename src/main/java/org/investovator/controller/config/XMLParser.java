package org.investovator.controller.config;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * @author Amila Surendra
 * @version $Revision
 */


/*
Class for common XML DOM manipulation tasks
 */
public class XMLParser {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder = null;
    Document doc = null;
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer;

    private String inputFile;

    /**
     * Creates a XMLParser using given xml document path.
     * @param file
     */
    public XMLParser(String file){
        inputFile = file;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        try {
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(inputFile);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Return the XML document model of the given document.
     * @return Document model.
     */
    public Document getXMLDocumentModel(){
         return  doc;
    }


    /**
     * Save the source file with a new name.
     * @param fileName
     */
    public void saveNewXML(String fileName){

       try {

            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
