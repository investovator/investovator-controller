package org.investovator.controller.config;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
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

        ReportGenerator gen = new ReportGenerator(getClass().getResource("report_template.xml").getPath());
        String[] types = gen.getSupportedReports();
        String[] result = gen.getDependencyReportBeans(types[0]);

        generator.addDependencyReportBean(result);

        generator.addSimulationProperty("$slowSleepInterval","100");
        generator.addSimulationProperty("$maximumDays","1");
        generator.addSimulationProperty("$lengthOfDay","200000");
        generator.addSimulationProperty("$initialPrice","500");

        generator.createModelConfig();

    }



}
