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
import org.investovator.core.commons.events.GameEventListener;

/**
 * @author Amila Surendra
 * @author Ishan
 * @version $Revision
 */
public interface GameFacade {

    /**
     * Register a listener with game facade.
     * @param listener GameEventListener
     */
    public void registerListener(GameEventListener listener);

    /**
     * Remove a listener with game facade.
     * @param listener GameEventListener
     */
    public void removeListener(GameEventListener listener);

    /**
     * Starts the game represented by the facade.
     */
    public boolean startGame();

    /**
     * Stop the represented by the facade.
     */
    public void stopGame();

    /**
     * Setup Game configuration
     * @param configurations
     */
    public void setupGame(Object[] configurations);

    /**
     * Returns the game mode controlled by the facade.
     * @return GameModes instance representing current game mode.
     */
    public GameModes getGameMode();

    /**
     * Returns the name of the game controlled by the facade.
     * @return String representing game name.
     */
    public String getName();


    /**
     * Returns the description of the game controlled by the facade.
     * @return
     */
    public String getDescription();

    /**
     * Run the given GameCommand.
     * @param command command to run.
     * @throws CommandSettingsException
     * @throws CommandExecutionException
     */
    public void runCommand(GameCommand command) throws CommandSettingsException, CommandExecutionException;

}
