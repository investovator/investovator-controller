package org.investovator.controller.config;

import junit.framework.Assert;
import org.apache.directory.api.ldap.model.filter.AssertionType;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ReportGeneratorTest {

    private String resourcePath =  "src" + File.separator + "test"   + File.separator + "java"
            + File.separator + "resources" + File.separator;

    private String tempDir;

    private ReportGenerator reportGenerator;


    @Before
    public void setUp() throws Exception {

        tempDir =  System.getProperty("java.io.tmpdir") +"/";

        reportGenerator = new ReportGenerator(resourcePath + "report_template.xml");
        reportGenerator.setOutputTemplateDoc(resourcePath + "bean-config-template.xml");
        reportGenerator.setOutputPath(tempDir + "out.xml");
    }

    @Test
    public void testGenerateXMLAndValidation() throws Exception {

        reportGenerator.generateXML("IBM");

        URL schemaFile = new URL("http://www.springframework.org/schema/beans/spring-beans.xsd");
        Source xmlFile = new StreamSource(new File(tempDir +"out.xml"));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            Assert.assertTrue(true);
        } catch (SAXException e) {
            Assert.assertTrue("Generated report bean xml validation fails.",false);
        }
    }


    @Test
    public void testGetSupportedReports() throws Exception{

        ReportGenerator gen = new ReportGenerator(resourcePath + "report_template.xml");

        String[] result = gen.getSupportedReports();

        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            resultList.add(result[i]);
        }

        Assert.assertTrue(resultList.contains("Price Time Series"));
        Assert.assertTrue(resultList.contains("Spread Time Series"));
    }


    @Test
    public void testGetDependencyReportBeans() throws Exception{
        String[] result = reportGenerator.getDependencyReportBeans("Spread Time Series");
        Assert.assertTrue(result[0].equals("spreadTimeSeriesReportVariables$stockID"));
    }
}
