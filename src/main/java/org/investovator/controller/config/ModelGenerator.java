package org.investovator.controller.config;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGenerator {

    XMLParser parser;
    Document templateDoc;
    Document outputDoc;

    /*Variables for storing external properties*/
    HashMap<String,AgentProperties> agentProperties;



    public ModelGenerator(String templateFile){
        parser = new XMLParser(templateFile);
        templateDoc = parser.getXMLDocumentModel();

        /*Initialization*/
        agentProperties = new HashMap<String, AgentProperties>();
    }

    /**
     * Returns the Types of Agents supported by the System
     * @return
     */
    public String[] getSupportedAgentTypes(){

        String[] agentNames;

        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = "//agents/agent/@name";

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
     * Adds the AgentType with given population size.
     * @param agentType
     * @param agentPopulationSize
     */
    public void addAgent(String agentType, int agentPopulationSize){
        AgentProperties properties = new AgentProperties();
        properties.size = agentPopulationSize;
        agentProperties.put(agentType,properties);
    }


    public void createModelConfig(){

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // root elements
        outputDoc = docBuilder.newDocument();
        Element rootElement = outputDoc.createElement("beans");
        outputDoc.appendChild(rootElement);


        addGlobalDependencies(rootElement);

        addAgents(rootElement);

        createXML();

    }


    private void addAgents(Element rootElement){

        Iterator agentIterator = agentProperties.keySet().iterator();

        while (agentIterator.hasNext()) {
            String next = agentIterator.next().toString();
            AgentProperties properties = agentProperties.get(next);

            addAgent(next,properties.size,rootElement);
        }
    }


    private void addGlobalDependencies(Element rootElement){
        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = "/investovator-config/global-dependencies/*";

        NodeList nodesAttr = null;
        try {
            nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, templateDoc, XPathConstants.NODESET);
            HashMap<String,String> replacements = new HashMap<String, String>();

            for (int i = 0; i < nodesAttr.getLength(); i++) {

                Node result = outputDoc.importNode(nodesAttr.item(i), true);
                rootElement.appendChild( result );

                replacePlaceHolder(result, "$stockID","GOOG");

            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }



    private void replacePlaceHolder(Node source, String placeholder, String replacement){

        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = "//@*";

        NodeList nodesAttr = null;
        try {
            nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, source, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        String resultAtt ;

        for(int i=0; i<nodesAttr.getLength(); i++) {
            resultAtt = nodesAttr.item(i).getTextContent();

            if(resultAtt.contains(placeholder)){
                resultAtt = resultAtt.replace("$stockID",replacement);
                nodesAttr.item(i).setTextContent(resultAtt);
            }
        }

    }




    private void createXML(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(outputDoc);
            StreamResult result = new StreamResult(new File("out.xml"));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    private void addAgent(String agent, int size ,Element rootElement) {

            XPath xpath = XPathFactory.newInstance().newXPath();
            String agentExpr = String.format("//agents//agent[@name=\"%s\"]/agent-bean/*", agent);

        try {
            Element agentBean = (Element) xpath.evaluate(agentExpr, templateDoc, XPathConstants.NODE);

            HashMap<String,String> replacements = new HashMap<String, String>();
            replacements.put("$population_size",Integer.toString(size));

            addElementReplacingAttributes(agentBean, replacements, rootElement);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add Elements to document replacing attributes which has variable value given in replacements keys and replaces with its value.
     * @param original
     * @param replacements
     * @param parent
     */
    private void addElementReplacingAttributes(Element original, HashMap<String, String> replacements, Element parent){

        XPath xpath = XPathFactory.newInstance().newXPath();
        Element importedElement =  (Element) outputDoc.importNode(original,true);
        parent.appendChild(importedElement);

        Iterator test =  replacements.keySet().iterator();
        while (test.hasNext()) {
            String next = test.next().toString();

            String agentExpr = String.format("//@*[.=\"%s\"]",next);

            try {
                Attr attribute = (Attr) xpath.evaluate(agentExpr,importedElement,XPathConstants.NODE);

                attribute.setValue(replacements.get(next));



            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
    }




    class AgentProperties{
        int size;
    }

}

