package org.investovator.controller.config;

/**
 * @author Amila Surendra
 * @version $Revision
 */

import org.w3c.dom.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Utility class for manipulating XML
 */
public class XMLEditor {

    /**
     * Add Elements to document replacing attributes which has variable value given in replacements keys and replaces with its value.
     * @param original original element
     * @param replacements replacement elements
     * @param parent parent element
     */
    public static void addElementReplacingAttributes(Element original, HashMap<String, String> replacements, Document outputDoc ,Element parent){

        XPath xpath = XPathFactory.newInstance().newXPath();
        Element importedElement =  (Element) outputDoc.importNode(original,true);
        parent.appendChild(importedElement);


        Iterator test =  replacements.keySet().iterator();
        while (test.hasNext()) {
            String next = test.next().toString();

            String agentExpr = String.format("//@*[.=\"%s\"]",next);

            try {
                Attr attribute = (Attr) xpath.evaluate(agentExpr,importedElement, XPathConstants.NODE);

                attribute.setValue(replacements.get(next));



            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Replace a given placeholder text with replacement text.
     * @param source Node containing the placeholder.
     * @param placeholder Placeholder name.
     * @param replacement Replacement text.
     */
    public static void replacePlaceHolder(Node source, String placeholder, String replacement){

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

    /**
     * Replaces a given placeholder with given elements.
     * @param placeholderName Name of the placeholder.
     * @param doc Reference to the XML document
     * @param replacements Replacement elements.
     */
    public static void replacePlaceholderElement(String placeholderName, Document doc, Element[] replacements){

        XPath xpath = XPathFactory.newInstance().newXPath();
                                   String xPathExpressionAttr = "//" + placeholderName;

        try {
            Node reportListElement = (Node) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODE);

            Element parent = (Element) reportListElement.getParentNode();

            for (Element element : replacements) {
                parent.appendChild(element);
            }

            parent.removeChild(reportListElement);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){

        }

    }

    /**
     * Replaces a given placeholder of an source element with given elements.
     * @param placeholderName Name of the placeholder.
     * @param doc Reference to the XML document
     * @param replacements Replacement elements.
     */
    public static void replacePlaceholderElement(String placeholderName, Element doc, Element[] replacements){

        XPath xpath = XPathFactory.newInstance().newXPath();
        String xPathExpressionAttr = "//" + placeholderName;

        try {
            Node reportListElement = (Node) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODE);

            Element parent = (Element) reportListElement.getParentNode();

            for (Element element : replacements) {
                parent.appendChild(element);
            }

            parent.removeChild(reportListElement);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }


    /**
     * Replaces a given placeholder with given elements.
     * @param placeholderName Name of the placeholder.
     * @param doc Reference to the XML document
     * @param replacement Replacement text.
     */
    public static void replacePlaceholderElement(String placeholderName, Element doc, String replacement){

        XPath xpath = XPathFactory.newInstance().newXPath();
        String xPathExpressionAttr = "//" + placeholderName;

        try {
            Node reportListElement = (Node) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODE);

            reportListElement.getParentNode().setTextContent(replacement);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates element for filling imports placeholder.
     * @param sourceDoc Source XML Document.
     * @param fileName  name of the file.
     * @return element containing file import tag.
     */
    public static Element createImportElement(Document sourceDoc, String fileName){

        Element importElement = sourceDoc.createElement("import");
        NamedNodeMap attribs = importElement.getAttributes();
        Attr bean = sourceDoc.createAttribute("resource");
        bean.setValue(fileName);
        attribs.setNamedItem(bean);
        return importElement;
    }

    /**
     * Creates element for filling controller placeholder.
     * @param sourceDoc Source XML Document.
     * @param beanName name of the bean.
     * @return Element containing reference to given bean.
     */
    public static Element createControllerElement(Document sourceDoc, String beanName){

        Element importElement = sourceDoc.createElement("ref");
        NamedNodeMap attribs = importElement.getAttributes();
        Attr bean = sourceDoc.createAttribute("bean");
        bean.setValue(beanName);
        attribs.setNamedItem(bean);
        return importElement;
    }


    /**
     * Creates element for filling controller placeholder.
     * @param sourceDoc Source XML Document.
     * @param agentBeanName name of the agent bean.
     * @return Element containing reference to given bean.
     */
    public static Element createAgentElement(Document sourceDoc, String agentBeanName){

        Element importElement = sourceDoc.createElement("ref");
        NamedNodeMap attribs = importElement.getAttributes();
        Attr bean = sourceDoc.createAttribute("bean");
        bean.setValue(agentBeanName);
        attribs.setNamedItem(bean);
        return importElement;
    }


    /**
     * Creates element for filling properties placeholder.
     * @param sourceDoc Source XML Document.
     * @param propFileName name of the property file.
     * @return Element containing reference to given bean.
     */
    public static Element createPropertyFileElement(Document sourceDoc, String propFileName){

        Element importElement = sourceDoc.createElement("value");
        importElement.setTextContent(propFileName);
        return importElement;
    }

}
