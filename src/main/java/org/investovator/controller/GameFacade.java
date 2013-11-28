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

    public void registerListener(GameEventListener listener);

    public void removeListener(GameEventListener listener);

    public boolean startGame();

    public void stopGame();

    public void setupGame(Object[] configurations);

    public GameModes getGameMode();

    public String getName();

    public String getDescription();

    public void runCommand(GameCommand command) throws CommandSettingsException, CommandExecutionException;

}
