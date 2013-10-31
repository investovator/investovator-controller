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

package org.investovator.controller.agentgaming;

import org.investovator.controller.utils.events.GameEventListener;
import org.investovator.core.data.api.CompanyData;
import org.investovator.core.data.api.CompanyDataImpl;
import org.investovator.core.data.exeptions.DataAccessException;
import org.investovator.agentsimulation.api.JASAFacade;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class AgentGameFacade {

    PortfolioUpdater updater;
    CompanyData companyData;

    public AgentGameFacade() {
        try {
            companyData = new CompanyDataImpl();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void setupAgentGame(){

        updater = new PortfolioUpdater();

        try {
            for(String stock: companyData.getAvailableStockIds()){
                JASAFacade.getMarketFacade().addListener(stock,updater);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    public void registerListener(GameEventListener listener){
        updater.addListener(listener);
    }

}
