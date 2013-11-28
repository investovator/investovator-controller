/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.controller;

import junit.framework.Assert;
import org.investovator.controller.agentgaming.AgentGameFacade;
import org.investovator.controller.config.ConfigGenerator;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.controller.utils.enums.GameStates;
import org.investovator.controller.utils.exceptions.GameCreationException;
import org.investovator.controller.utils.exceptions.GameProgressingException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class GameControllerImplTest {

    GameController controller;
    AgentGameFacade facade;

    String[] stocks = {"IBM"};
    private static String RESOURCE_DIR_PATH = "src" + File.separator + "test" + File.separator + "java"
            + File.separator + "resources" + File.separator;
    String outputPath =  System.getProperty("java.io.tmpdir")+"/";
    ConfigGenerator generator;

    @Before
    public void setUp() throws Exception {
        createConfig();
        System.setProperty("jabm.config", outputPath + "main.xml");
        facade = new AgentGameFacade();

        controller = GameControllerImpl.getInstance();

    }

    @Test
    public void testCreateAndRemoveGameInstance() throws Exception {

        ArrayList<String> gameInstances = new ArrayList<>();
        gameInstances.add(controller.createGameInstance(GameModes.AGENT_GAME));
        Assert.assertTrue(gameInstances.contains(GameModes.AGENT_GAME.toString()));

        for(String instance : gameInstances){
            controller.removeGameInstance(instance);
        }

        Assert.assertTrue(!controller.getGameInstances().contains(GameModes.AGENT_GAME.toString()));
    }

    @Test
    public void  testMultiAgentCreation(){

        String agentGame = null;

        try {
            agentGame = controller.createGameInstance(GameModes.AGENT_GAME);
        } catch (GameCreationException e) {
            Assert.assertTrue(false);
        }
        try {
            agentGame = controller.createGameInstance(GameModes.AGENT_GAME);
        } catch (GameCreationException e) {
            if(e.getMessage().equals("Game Mode Already Exists")) Assert.assertTrue(true);
        }

        controller.removeGameInstance(agentGame);

    }


    @Test
    public void testGameStates() throws GameCreationException, GameProgressingException, InterruptedException {

        String instance = controller.createGameInstance(GameModes.AGENT_GAME);
        Assert.assertEquals(controller.getCurrentGameState(instance), GameStates.NEW);
        controller.setupGame(instance, new Object[]{});
        Assert.assertEquals(controller.getCurrentGameState(instance), GameStates.CONFIGURED);
        controller.startGame(instance);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("starting ....");

                for (int i = 0; i < 5; i++) {

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

        Assert.assertEquals(controller.getCurrentGameState(instance), GameStates.RUNNING);
        controller.removeGameInstance(instance);

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
