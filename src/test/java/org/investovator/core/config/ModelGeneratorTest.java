package org.investovator.core.config;

import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGeneratorTest {
    @Test
    public void testGetSupportedAgentTypes() throws Exception {
        ModelGenerator generator = new ModelGenerator(ModelGenerator.class.getResource("model_template.xml").getPath());
        generator.getSupportedAgentTypes();
    }
}
