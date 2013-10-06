package org.investovator.controller.config;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGenerator {

    XMLParser parser;
    Document templateDoc;

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
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("beans");
        doc.appendChild(rootElement);


        //addGlobalDependencies(doc,rootElement);

        addAgents(rootElement, doc);

        createXML(doc);

    }


    private void addAgents(Element rootElement, Document doc){

        Iterator agentIterator = agentProperties.keySet().iterator();

        while (agentIterator.hasNext()) {
            String next = agentIterator.next().toString();
            AgentProperties properties = agentProperties.get(next);

            addAgent(next,properties.size,rootElement,doc);
        }
    }


    private void addGlobalDependencies(Document doc, Element rootElement){
        XPath xpath = XPathFactory.newInstance().newXPath();

        String xPathExpressionAttr = "/investovator-config/global-dependencies/*";

        NodeList nodesAttr = null;
        try {
            nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, templateDoc, XPathConstants.NODESET);

            for (int i = 0; i < nodesAttr.getLength(); i++) {
                rootElement.appendChild( doc.importNode(nodesAttr.item(i),true) );
            }


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }


    private void createXML(Document doc){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("out.xml"));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    private void addAgent(String agent, int size ,Element rootElement, Document doc) {

            XPath xpath = XPathFactory.newInstance().newXPath();
            String agentExpr = String.format("//agents//agent[@name=\"%s\"]/agent-bean/*", agent);

        try {
            Element agentBean = (Element) xpath.evaluate(agentExpr, templateDoc, XPathConstants.NODE);

            HashMap<String,String> replacements = new HashMap<String, String>();
            replacements.put("$population_size",Integer.toString(size));

            rootElement.appendChild(getElementReplacing(agentBean, replacements,doc));

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }




    private Element getElementReplacing(Element original, HashMap<String,String> replacements, Document doc){

        XPath xpath = XPathFactory.newInstance().newXPath();

        Iterator test =  replacements.keySet().iterator();
        while (test.hasNext()) {
            String next = test.next().toString();


            String agentExpr = String.format("//@*[.=\"%s\"]",next);

            try {
                Attr attribute = (Attr) xpath.evaluate(agentExpr,original,XPathConstants.NODE);

                attribute.setValue(replacements.get(next));


            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
        Element importedElement = (Element) doc.importNode(original,true);
        return  importedElement;
    }


    class AgentProperties{
        int size;
    }

}

