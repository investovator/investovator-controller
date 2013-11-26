/*
 * investovator, Stock Market Gaming Framework
 *     Copyright (C) 2013  investovator
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.investovator.controller.dataplaybackengine;

import org.investovator.controller.GameFacade;
import org.investovator.controller.command.GameCommand;
import org.investovator.controller.command.dataplayback.DataPlaybackGameCommand;
import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.controller.utils.events.GameEventListener;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.dataplaybackengine.DataPlayerFacade;
import org.investovator.dataplaybackengine.exceptions.GameAlreadyStartedException;
import org.investovator.dataplaybackengine.player.DailySummaryDataPLayer;
import org.investovator.dataplaybackengine.player.DataPlayer;
import org.investovator.dataplaybackengine.player.type.PlayerTypes;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public class DataPlaybackGameFacade implements GameFacade {

    DataPlayer player;


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
        try {
            this.player.startGame();
        } catch (GameAlreadyStartedException e) {
            return false;
        }
        return true;
    }

    @Override
    public void stopGame() {
        this.player.stopGame();
    }

    @Override
    public void setupGame(Object[] configurations) {
        PlayerTypes type=(PlayerTypes)configurations[0];
        String[] symbols=(String[])configurations[1];
        Date startDate=(Date)configurations[2];
        ArrayList<TradingDataAttribute> attributes=(ArrayList<TradingDataAttribute>)configurations[3];
        TradingDataAttribute attrToMatch=(TradingDataAttribute)configurations[4];
        boolean multiplayer=(boolean)configurations[5];

        DataPlayerFacade.getInstance().createPlayer(type,symbols,startDate,attributes,attrToMatch,multiplayer);

        this.player=DataPlayerFacade.getInstance().getCurrentPlayer();
    }

    @Override
    public GameModes getGameMode() {
        return GameModes.PAYBACK_ENG;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runCommand(GameCommand command) throws CommandSettingsException {
        if(command instanceof DataPlaybackGameCommand){
            DataPlaybackGameCommand dpeCommand=(DataPlaybackGameCommand)command;
            dpeCommand.setDataPlayer(this.player);
            dpeCommand.execute();

        }
        else{
            throw new CommandSettingsException("Invalid command for Data Playback engine");
        }
    }
}
