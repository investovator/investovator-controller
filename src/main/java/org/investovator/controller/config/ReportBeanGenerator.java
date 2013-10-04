package org.investovator.controller.config;

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
    String fileToBeSaved = "out.xml";

    public ReportBeanGenerator(File templateFile){
        this.templateXML = templateFile;
    }


    public void setOutputPath(String path){
        fileToBeSaved = path;
    }


    public File generateXML(String stockID){

        String filePath = getClass().getResource("report_template.xml").getPath();


        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);

            XPath xpath = XPathFactory.newInstance().newXPath();


            // change ATTRIBUTES
            String xPathExpressionAttr = "//@id|//@ref|//@bean";
            NodeList nodesAttr = (NodeList) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODESET);

            String resultAtt ;

            for(int i=0; i<nodesAttr.getLength(); i++) {
                resultAtt = nodesAttr.item(i).getTextContent();
                resultAtt = resultAtt + stockID;
                nodesAttr.item(i).setTextContent(resultAtt);
            }

            // save xml file back
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileToBeSaved));
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null;
    }

}
