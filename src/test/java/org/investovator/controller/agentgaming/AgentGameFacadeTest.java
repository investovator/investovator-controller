package org.investovator.controller.agentgaming;

import junit.framework.Assert;
import org.investovator.agentsimulation.api.JASAFacade;
import org.investovator.controller.command.agent.UnmatchedOrdersCommand;
import org.investovator.controller.config.ConfigGenerator;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.core.commons.simulationengine.MarketOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class AgentGameFacadeTest {

    AgentGameFacade facade;

    String[] stocks = {"SAMP"};
    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test" + File.separator + "java"
            + File.separator + "resources" + File.separator;
    String outputPath =  System.getProperty("java.io.tmpdir")+"/";
    ConfigGenerator generator;


    @Before
    public void setUp() throws Exception {
        createConfig();
        System.setProperty("jabm.config", outputPath + "main.xml");
        facade = new AgentGameFacade();
    }

    @Test
    public void testUnmatchedOrdersCommand() throws Exception{


        facade.startGame();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("starting ....");

                for (int i = 0; i < 6; i++) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
        t.join();



        String userName =  "testUser1";

        JASAFacade.getMarketFacade().AddUserAgent(userName, 100000);
        JASAFacade.getMarketFacade().putLimitOrder(userName, "SAMP", 1, 10, true);

        UnmatchedOrdersCommand command = new UnmatchedOrdersCommand(userName);
        facade.runCommand(command);

        HashMap<String, ArrayList<MarketOrder>> orders = command.getOrders();

        Assert.assertTrue(orders.get("SAMP").size()>0);

        facade.stopGame();


    }


    private void createConfig(){

        generator = new ConfigGenerator(stocks, outputPath);

        generator.setModelTemlpateFile(RESOURCE_DIR_PATH + "model_template.xml");
        generator.setReportTemlpateFile(RESOURCE_DIR_PATH + "report_template.xml");
        generator.setMainTemplateFile(RESOURCE_DIR_PATH + "main_template.xml");
        generator.setSpringBeanConfigTemplate(RESOURCE_DIR_PATH + "bean-config-template.xml");

        generator.addAgent("Linear Combination Traders",100);
        generator.setInitialPrice(100);
        generator.setNoOfDays(1);
        generator.setSpeedFactor(1);

        for(int i = 0; i < stocks.length; i++){
            File file = new File(RESOURCE_DIR_PATH + stocks[i]+".properties");
            generator.addProperties(stocks[i], "file:"+file.getAbsolutePath() );
        }

        String[] types = generator.getSupportedReports();
        String[] result = generator.getDependencyReportBeans(types[0]);
        generator.addDependencyReportBean(result);

        generator.createConfigs();

    }
}
