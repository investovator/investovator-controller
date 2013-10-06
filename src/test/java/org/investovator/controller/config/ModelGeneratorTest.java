package org.investovator.controller.config;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class ModelGeneratorTest {

    ModelGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new ModelGenerator(ModelGenerator.class.getResource("new_template.xml").getPath());

    }

    @Test
    public void testGetSupportedAgentTypes() throws Exception {

        String[] result = generator.getSupportedAgentTypes();

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    @Test
    public void testCreateModelConfig() throws Exception{

        generator.addAgent(generator.getSupportedAgentTypes()[0],120);
        generator.addAgent(generator.getSupportedAgentTypes()[1],100);


        generator.createModelConfig();

    }



}
