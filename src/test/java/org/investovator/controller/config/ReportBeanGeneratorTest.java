package org.investovator.controller.config;

import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ReportBeanGeneratorTest {
    @Test
    public void testGenerateXML() throws Exception {

        ReportBeanGenerator gen = new ReportBeanGenerator(null);

        gen.generateXML("IBM");


    }
}