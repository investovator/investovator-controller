package org.investovator.controller.config;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGeneratorTest {

    private String resourcePath =  "src" + File.separator + "test"   + File.separator + "java"
            + File.separator + "resources" + File.separator;

    private String tempDir;

    private ModelGenerator generator;

    @Before
    public void setUp() throws Exception {
        tempDir =  System.getProperty("java.io.tmpdir") +"/";
        generator = new ModelGenerator(resourcePath+"model_template.xml");
        generator.setOutputTemplateDoc(resourcePath + "bean-config-template.xml");
        generator.setStockID("GOOG");
        generator.setOutputFile(tempDir + "out.xml");
    }

    @Test
    public void testGetSupportedAgentTypes() throws Exception {

        String[] result = generator.getSupportedAgentTypes();
        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            resultList.add(result[i]);
        }
        Assert.assertTrue(resultList.contains("Noise Traders"));
        Assert.assertTrue(resultList.contains("Fundamentalist Traders"));
        Assert.assertTrue(resultList.contains("Chartist Traders"));
    }

    @Test
    public void testCreateModelConfig() throws Exception{

        generator.addAgent(generator.getSupportedAgentTypes()[0],120);
        generator.addAgent(generator.getSupportedAgentTypes()[1],100);

        ReportGenerator reportGenerator = new ReportGenerator(resourcePath + "report_template.xml");
        reportGenerator.setOutputTemplateDoc(resourcePath + "bean-config-template.xml");
        reportGenerator.setOutputPath(tempDir + "out.xml");
        String[] types = reportGenerator.getSupportedReports();
        String[] result = reportGenerator.getDependencyReportBeans(types[0]);

        generator.addDependencyReportBean(result);

        generator.addSimulationProperty("$slowSleepInterval","100");
        generator.addSimulationProperty("$maximumDays","1");
        generator.addSimulationProperty("$lengthOfDay","200000");
        generator.addSimulationProperty("$initialPrice","500");

        generator.createModelConfig();


        //Validation
        URL schemaFile = new URL("http://www.springframework.org/schema/beans/spring-beans.xsd");
        Source xmlFile = new StreamSource(new File(tempDir +"out.xml"));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            Assert.assertTrue(true);
        } catch (SAXException e) {
            Assert.assertTrue("Generated model bean xml validation fails.",false);
        }

        //Property Validation
        XMLParser parser = new XMLParser(tempDir+"out.xml");
        Document doc = parser.getXMLDocumentModel();

        XPath xpath = XPathFactory.newInstance().newXPath();
        Node nodesAttr = null;

        String xPathExpressionAttr = "//property[@name='lengthOfDay']/@value";
        nodesAttr = (Node) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODE);
        Assert.assertEquals("200000", nodesAttr.getNodeValue());

        xPathExpressionAttr = "//property[@name='maximumDays']/@value";
        nodesAttr = (Node) xpath.evaluate(xPathExpressionAttr, doc, XPathConstants.NODE);
        Assert.assertEquals("1", nodesAttr.getNodeValue());

    }



}
