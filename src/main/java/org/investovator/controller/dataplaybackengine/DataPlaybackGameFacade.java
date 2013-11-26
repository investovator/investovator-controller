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
import org.investovator.controller.command.exception.CommandExecutionException;
import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.core.commons.events.GameEventListener;
import org.investovator.core.data.api.utils.TradingDataAttribute;
import org.investovator.dataplaybackengine.DataPlayerFacade;
import org.investovator.dataplaybackengine.configuration.GameConfiguration;
import org.investovator.dataplaybackengine.exceptions.GameAlreadyStartedException;
import org.investovator.dataplaybackengine.player.DataPlayer;
import org.investovator.dataplaybackengine.player.type.PlayerTypes;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public class DataPlaybackGameFacade implements GameFacade {

    private DataPlayer player;
    private DataPlayerFacade facade;

    public DataPlaybackGameFacade() {
        facade=new DataPlayerFacade();
    }

    @Override
    public void registerListener(GameEventListener listener) {
        player.setObserver(listener);
    }

    @Override
    public void removeListener(GameEventListener listener) {
        player.removeObserver(listener);
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

        GameConfiguration config=(GameConfiguration)configurations[0];
        this.player=facade.createPlayer(config);

    }

    @Override
    public GameModes getGameMode() {
        return GameModes.PAYBACK_ENG;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runCommand(GameCommand command) throws CommandSettingsException, CommandExecutionException {
        if(command instanceof DataPlaybackGameCommand){
            DataPlaybackGameCommand dpeCommand=(DataPlaybackGameCommand)command;
            dpeCommand.setDataPlayer(this.player);
            dpeCommand.setFacade(this.facade);
            dpeCommand.execute();

        }
        else{
            throw new CommandSettingsException("Invalid command for Data Playback engine");
        }
    }
}
