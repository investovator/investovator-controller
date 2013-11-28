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

package org.investovator.controller.nngaming;

import org.investovator.ann.neuralnet.NNManager;
import org.investovator.ann.nngaming.NNGamingFacade;
import org.investovator.controller.GameFacade;
import org.investovator.controller.command.GameCommand;
import org.investovator.controller.command.ann.ANNGameCommand;
import org.investovator.controller.command.exception.CommandExecutionException;
import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.core.commons.events.GameEventListener;
import org.investovator.core.data.api.utils.TradingDataAttribute;

import java.util.ArrayList;

/**
 * @author Hasala Surasinghe
 * @author Amila Surendra
 * @version $Revision
 */
public class NNGameFacade implements GameFacade {

    private NNGamingFacade nnGamingFacade;

    public NNGameFacade(){

        nnGamingFacade = NNGamingFacade.getInstance();

    }

    @Override
    public void registerListener(GameEventListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeListener(GameEventListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean startGame() {
        NNGamingFacade nnGamingFacade = NNGamingFacade.getInstance();
        nnGamingFacade.startGame();
        return true;
    }

    @Override
    public void stopGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setupGame(Object[] configurations) {

        ArrayList<TradingDataAttribute> attributes = (ArrayList<TradingDataAttribute>) configurations[0];
        ArrayList<String> stockIDs = (ArrayList<String>) configurations[1];
        ArrayList<String> analysisStockIDs = (ArrayList<String>) configurations[2];

        NNManager nnManager = new NNManager(attributes,stockIDs,analysisStockIDs);
        nnManager.createGamingNeuralNetworks();
        nnManager.createAnalysisNeuralNetworks();
    }

    @Override
    public GameModes getGameMode() {
        return GameModes.NN_GAME;
    }

    @Override
    public void runCommand(GameCommand command) throws CommandSettingsException, CommandExecutionException {
        if(command instanceof ANNGameCommand){
            ANNGameCommand nnCommand=(ANNGameCommand)command;
            nnCommand.setFacade(this.nnGamingFacade);
            nnCommand.execute();
        }
        else{
            throw new CommandSettingsException("Invalid command for ANN Gaming engine");
        }
    }
}
