package org.investovator.controller.config;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ReportGenerator {

    Document templateDoc;
    XMLParser parser;
    String fileToBeSaved;
    String outputTemplateDoc;
    Document outputDoc;

    /**
     * Creates a report generator with given template.
     * @param templateFile path to the template file.
     */
    public ReportGenerator(String templateFile){
        parser = new XMLParser(templateFile);
        this.templateDoc = parser.getXMLDocumentModel();

    }

    /**
     * Sets the template for output document.
     * @param outputTemplateDoc Path to template file.
     */
    public void setOutputTemplateDoc(String outputTemplateDoc) {
        this.outputTemplateDoc = outputTemplateDoc;
    }


    /**
     * Set output path.
     * @param path path to create output file.
     */
    public void setOutputPath(String path){
        fileToBeSaved = path;
    }

    /**
     * Reads the template file and returns the supported report types
     * @return Supported report types.
     */
    public String[] getSupportedReports(){

        String[] agentNames;

        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = "//reports/report/@name";

        NodeList nodesAttr = null;
        try {
            nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, templateDoc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        agentNames = new String[nodesAttr.getLength()];

        for(int i=0; i<agentNames.length; i++) {
            agentNames[i] = nodesAttr.item(i).getTextContent();
        }

        return agentNames;
    }

    /**
     * Returns the dependency beans of a given report type.
     * @param reportType Type of the report given by getSupportedReports() method.
     * @return
     */
    public String[] getDependencyReportBeans(String reportType){

        String[] beanNames;

        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = String.format("//reports/report[@name=\"%s\"]/*", reportType);

        NodeList nodesAttr = null;

        try {
            nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, templateDoc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        beanNames = new String[nodesAttr.getLength()];

        for(int i=0; i<beanNames.length; i++) {
            beanNames[i] = nodesAttr.item(i).getTextContent();
        }

        return beanNames;
    }


    /**
     * Creates a report bean file with current settings for the given stock symbol.
     * @param stockID name of the stock.
     */
    public void generateXML(String stockID){

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {

            docBuilder = docFactory.newDocumentBuilder();

            XMLParser outputParser = new XMLParser(outputTemplateDoc);
            outputDoc = outputParser.getXMLDocumentModel();
            Element rootElement = outputDoc.getDocumentElement();

            XPath xpath = XPathFactory.newInstance().newXPath();


            // change ATTRIBUTES
            String xPathExpressionElements = "/investovator-config//dependencies/*";
            NodeList beanElements = (NodeList) xpath.evaluate(xPathExpressionElements, templateDoc, XPathConstants.NODESET);

            HashMap<String,String> replacements = new HashMap<String, String>();
            replacements.put("$stockID",stockID);

            for (int i = 0; i < beanElements.getLength(); i++) {

                Element importedElement =  (Element)  outputDoc.importNode((Element)beanElements.item(i),true);
                rootElement.appendChild(importedElement);

                XMLEditor.replacePlaceHolder(importedElement,"$stock", stockID);

            }
            // save xml file back
            createXML();

        } catch (XPathExpressionException e) {
              e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    private void createXML(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(outputDoc);
            StreamResult result = new StreamResult(new File(fileToBeSaved));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
