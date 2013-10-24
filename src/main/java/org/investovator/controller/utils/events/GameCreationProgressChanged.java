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

package org.investovator.controller.utils.events;

import org.investovator.controller.utils.enums.GameModes;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class GameCreationProgressChanged extends GameEvent {

    private GameModes gameMode;
    private float progress;

    public GameCreationProgressChanged(GameModes gameMode, float progress) {
        this.gameMode = gameMode;
        this.progress = progress;
    }

    public GameModes getGameMode(){
       return gameMode;
    }
    public void setGameMode(GameModes gameMode) {
        this.gameMode = gameMode;
    }

    public float getProgress() {
        return progress;
    }
}
