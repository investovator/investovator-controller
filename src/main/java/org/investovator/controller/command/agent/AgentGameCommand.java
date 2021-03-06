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

package org.investovator.controller.command.agent;

import org.investovator.agentsimulation.api.JASAFacade;
import org.investovator.controller.command.GameCommand;

/**
 * This is the beginning for the command hierarchy for Agent based games
 *
 * @author: ishan
 * @version: ${Revision}
 */
public abstract class AgentGameCommand implements GameCommand {

    protected JASAFacade facade;

    /**
     * Used to set the Facade for the agent game
     * @param facade
     */
    public void setFacade(JASAFacade facade){
        this.facade = facade;
    }
}
