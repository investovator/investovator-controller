package org.investovator.controller;

import net.sourceforge.jasa.event.MarketEvent;
import org.investovator.controller.utils.events.GameEvent;
import org.investovator.controller.utils.events.GameEventListener;
import org.investovator.controller.utils.events.PortfolioChangedEvent;
import org.investovator.jasa.api.JASAFacade;
import org.investovator.jasa.api.MarketFacade;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class GameControllerFacadeTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFacade() throws Exception{

        System.setProperty("jabm.config","/home/amila/config/main.xml");

        JASAFacade.getMarketFacade().startSimulation();

        GameControllerFacade.getInstance().registerListener(new GameEventListener() {
            @Override
            public void eventOccurred(GameEvent event) {
                if(event instanceof PortfolioChangedEvent){
                    System.out.println(((PortfolioChangedEvent) event).getPortfolio().getCashBalance());
                }
            }
        });

    }
}
