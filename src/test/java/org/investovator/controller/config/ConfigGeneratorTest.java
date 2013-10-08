package org.investovator.controller.config;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ConfigGeneratorTest {
    String[] stocks = {"IBM"};

    ConfigGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new ConfigGenerator(stocks, System.getProperty("user.home") + "/config");
    }

    @Test
    public void testCreateConfigs(){

        generator.addAgent("Linear Combination Traders",100);
        generator.setInitialPrice(100);
        generator.setNoOfDays(1);
        generator.setSpeedFactor(1);

        String[] types = generator.getSupportedReports();
        String[] result = generator.getDependencyReportBeans(types[0]);
        generator.addDependencyReportBean(result);



        generator.createConfigs();
    }
}