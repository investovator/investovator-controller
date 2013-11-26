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

package org.investovator.controller.command.dataplayback;

import org.investovator.controller.command.GameCommand;
import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.dataplaybackengine.player.DataPlayer;

/**
 * This is the beginning for the command hierarchy for Data playbacks
 *
 * @author: ishan
 * @version: ${Revision}
 */
public interface DataPlaybackGameCommand extends GameCommand {

    /**
     * Used to set the necessary data player in the command object
     * @param player
     */
    public void setDataPlayer(DataPlayer player) throws CommandSettingsException;
}
