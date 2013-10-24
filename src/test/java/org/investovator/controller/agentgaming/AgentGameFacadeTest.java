package org.investovator.controller.agentgaming;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class AgentGameFacadeTest {

    AgentGameFacade agentGameFacade;

    @Before
    public void setUp() throws Exception {
         agentGameFacade = new AgentGameFacade();
    }

    @Test
    public void testSetupAgentGame() throws Exception {

        agentGameFacade.setupAgentGame();

    }
}
