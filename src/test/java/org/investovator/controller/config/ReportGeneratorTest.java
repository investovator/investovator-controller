package org.investovator.controller.config;

import junit.framework.Assert;
import org.apache.directory.api.ldap.model.filter.AssertionType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
    public void testGenerateXML() throws Exception {

        ReportGenerator gen = new ReportGenerator(resourcePath + "report_template.xml");

        gen.setOutputPath("out.xml");
        gen.setOutputTemplateDoc(resourcePath + "bean-config-template.xml");

        gen.generateXML("IBM");


    }


    @Test
    public void Test() throws Exception{
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

        ReportGenerator gen = new ReportGenerator(getClass().getResource("report_template.xml").getPath());

        String[] types = gen.getSupportedReports();

        String[] result = gen.getDependencyReportBeans(types[0]);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
