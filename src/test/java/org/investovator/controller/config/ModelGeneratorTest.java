package org.investovator.controller.config;

import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGeneratorTest {
    @Test
    public void testGetSupportedAgentTypes() throws Exception {
        ModelGenerator generator = new ModelGenerator(ModelGenerator.class.getResource("new_template.xml").getPath());
        String[] result = generator.getSupportedAgentTypes();

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
