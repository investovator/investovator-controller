package org.investovator.core.config;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
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
public class ReportBeanGenerator {

    File templateXML;

    public ReportBeanGenerator(File templateFile){
        this.templateXML = templateFile;
    }


    public File generateXML(String stockID){

        String textToFind = "transactionPriceTimeSeriesReport$Stock";
        String textToReplace = stockID;

        String filepath = getClass().getResource("input.xml").getPath();
        String fileToBeSaved = "out.xml";

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            XPath xpath = XPathFactory.newInstance().newXPath();


            // change ATTRIBUTES
            String xPathExpressionAttr = "//@id|//@ref";
            NodeList nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODESET);

            String resultAtt ;

            for(int i=0; i<nodesAttr.getLength(); i++) {
                resultAtt = nodesAttr.item(i).getTextContent();
                resultAtt = resultAtt + stockID;
                nodesAttr.item(i).setTextContent(resultAtt);
            }

            System.out.println("Everything replaced.");

            // save xml file back
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileToBeSaved));
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

}
