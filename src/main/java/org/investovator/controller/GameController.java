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

import org.investovator.controller.command.GameCommand;
import org.investovator.controller.command.exception.CommandExecutionException;
import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.controller.utils.enums.GameModes;
import org.investovator.controller.utils.enums.GameStates;
import org.investovator.core.commons.events.GameEventListener;
import org.investovator.controller.utils.exceptions.GameCreationException;
import org.investovator.controller.utils.exceptions.GameProgressingException;

import java.util.List;

/**
 * @author Amila Surendra
 * @author Ishan
 * @version $Revision
 */
public interface GameController {

    /**
     * Creates a game instance of game of the given type and returns the instance name.
     * @param type Game type.
     * @return Game instance.
     * @throws GameCreationException
     */
    public String createGameInstance(GameModes type) throws GameCreationException;


    /**
     * Removes the game specified game instance. If the game is running it is first stopped and then removed.
     * @param instance
     */
    public void removeGameInstance(String instance);


    /**
     * Return all the game instances managed by game controller.
     * @return List of String representing instances,
     */
    public List<String> getGameInstances();


    /**
     * Register GameEventListener with the backend game facade representing the given instance.
     * @param instance Instance Id of the game.
     * @param listener Listener to register,
     */
    public void registerListener(String instance, GameEventListener listener);


    /**
     * Remove given GameEventListener from the backend game facade representing the given instance.
     * @param instance Instance Id of the game.
     * @param listener Listener to remove,
     */
    public void removeListener(String instance, GameEventListener listener);


    /**
     * Starts the game specified by the instance id.
     * @param instance Game instance.
     * @return boolean stating the game started or not.
     * @throws GameProgressingException
     */
    public boolean startGame(String instance) throws GameProgressingException;


    /**
     * Stops the game specified by the instance id.
     * @param instance Game instance.
     */
    public void stopGame(String instance);


    /**
     * Setup the game specified by the instance id.
     * @param instance Game instance.
     * @param configurations set of configurations,
     */
    public void setupGame(String instance, Object[] configurations);


    /**
     * Get the state of the game specified by the instance.
     * @param instance Game instance.
     * @return State of the game.
     */
    public GameStates getCurrentGameState(String instance);


    /**
     * Runs a given command on the specified game instance.
     * @param instance  Game instance.
     * @param command  Command to run.
     * @throws CommandSettingsException
     * @throws CommandExecutionException
     */
    public void runCommand(String instance, GameCommand command) throws CommandSettingsException, CommandExecutionException;


    /**
     * Returns the name of the game specified by given instance.
     * @param instance Game instance.
     * @return Name of the game.
     */
    public String getName(String instance);


    /**
     * Returns the description of the game specified by given instance.
     * @param instance Game instance.
     * @return Description of the game.
     */
    public String getDescription(String instance);


    /**
     * Return the game mode of the given game instance.
     * @param instance Game Instance
     * @return Game mode
     */
    public GameModes getGameMode(String instance);

}
