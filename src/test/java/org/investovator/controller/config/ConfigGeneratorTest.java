package org.investovator.controller.config;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ConfigGeneratorTest {

    String[] stocks = {"IBM","GOOG","SAMP"};
    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test" + File.separator + "java"
            + File.separator + "resources" + File.separator;

    ConfigGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new ConfigGenerator(stocks, System.getProperty("user.home") + "/config");

        generator.setModelTemlpateFile(RESOURCE_DIR_PATH + "model_template.xml");
        generator.setReportTemlpateFile(RESOURCE_DIR_PATH +"report_template.xml");
        generator.setMainTemplateFile(RESOURCE_DIR_PATH +"main_template.xml");
        generator.setSpringBeanConfigTemplate(RESOURCE_DIR_PATH +"bean-config-template.xml");
    }

    @Test
    public void testCreateConfigs(){

        generator.addAgent("Linear Combination Traders",100);
        generator.setInitialPrice(100);
        generator.setNoOfDays(1);
        generator.setSpeedFactor(1);

        generator.addProperties(stocks[0],stocks[0] + "url");
        generator.addProperties(stocks[1],stocks[1] + "url");
        generator.addProperties(stocks[2],stocks[2] + "url");

        String[] types = generator.getSupportedReports();
        String[] result = generator.getDependencyReportBeans(types[0]);
        generator.addDependencyReportBean(result);



        generator.createConfigs();
    }
}
