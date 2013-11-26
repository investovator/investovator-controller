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

package org.investovator.controller.agentgaming;

import org.investovator.agentsimulation.api.MarketFacade;
import org.investovator.controller.GameFacade;
import org.investovator.controller.command.GameCommand;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.controller.utils.events.GameCreationProgressChanged;
import org.investovator.core.commons.events.GameEvent;
import org.investovator.core.commons.events.GameEventListener;
import org.investovator.agentsimulation.api.JASAFacade;

import java.util.ArrayList;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class AgentGameFacade implements GameFacade {

    private ArrayList<GameEventListener> listeners;

    public AgentGameFacade(){
        listeners = new ArrayList<GameEventListener>();
    }


    private void startAgentGame(){

        MarketFacade simulationFacade = JASAFacade.getMarketFacade();
        simulationFacade.startSimulation();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {

                    try {
                        Thread.sleep(1000);
                        notifyListeners(new GameCreationProgressChanged(GameModes.AGENT_GAME, (((float)i)/4) ));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @Override
    public void removeListener(GameEventListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean startGame() {
        startAgentGame();
        return true;
    }

    @Override
    public void stopGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setupGame(Object[] configurations) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GameModes getGameMode() {
        return GameModes.AGENT_GAME;
    }

    @Override
    public void runCommand(GameCommand command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private void notifyListeners(GameEvent event){
        for(GameEventListener listener : listeners){
            listener.eventOccurred(event);
        }
    }

    public void registerListener(GameEventListener listener){
        //agentGameFacade.registerListener(listener);
        listeners.add(listener);
    }
}
