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

import org.investovator.controller.command.exception.CommandSettingsException;
import org.investovator.dataplaybackengine.player.DailySummaryDataPLayer;
import org.investovator.dataplaybackengine.player.DataPlayer;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public class StepToNextDayCommand extends DataPlaybackGameCommand{

    DailySummaryDataPLayer pLayer;
    @Override
    public void setDataPlayer(DataPlayer player) throws CommandSettingsException {
        if(player instanceof DailySummaryDataPLayer){

            this.pLayer=(DailySummaryDataPLayer)player;
        }
        else{
            throw new CommandSettingsException("Invalid data player");
        }
    }

    @Override
    public void execute() {
        this.pLayer.playNextDay();
    }
}
