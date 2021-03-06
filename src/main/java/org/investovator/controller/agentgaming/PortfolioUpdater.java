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

import net.sourceforge.jabm.event.EventListener;
import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jasa.agent.TradingAgent;
import net.sourceforge.jasa.event.TransactionExecutedEvent;
import net.sourceforge.jasa.market.Order;
import org.investovator.agentsimulation.api.JASAFacade;
import org.investovator.agentsimulation.api.MarketFacade;
import org.investovator.agentsimulation.api.utils.HollowTradingAgent;
import org.investovator.core.commons.events.GameEvent;
import org.investovator.core.commons.events.GameEventListener;
import org.investovator.core.data.api.UserData;
import org.investovator.core.data.api.UserDataImpl;
import org.investovator.core.data.exeptions.DataAccessException;

import java.util.ArrayList;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class PortfolioUpdater implements EventListener {

    MarketFacade jasaFacade;
    UserData userData;
    private ArrayList<GameEventListener> listeners;

    public PortfolioUpdater() {
        try {
            userData = new UserDataImpl();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        jasaFacade = JASAFacade.getMarketFacade();
    }

    public void addListener(GameEventListener listener){
        this.listeners.add(listener);
    }

    private void notifyListeners(GameEvent event)
    {
        for(GameEventListener listener : listeners){
            listener.eventOccurred(event);
        }
    }

    @Override
    public void eventOccurred(SimEvent simEvent) {

        if (simEvent instanceof TransactionExecutedEvent){

            TransactionExecutedEvent transactionEvent = (TransactionExecutedEvent)simEvent;

            Order ask = ((TransactionExecutedEvent) simEvent).getAsk();
            Order bid =  ((TransactionExecutedEvent) simEvent).getBid();

            TradingAgent buyer = ask.getAgent();
            TradingAgent seller = bid.getAgent();

            if(buyer instanceof HollowTradingAgent){

                String buyingUser = ((HollowTradingAgent) buyer).getUserName();

               // try {
                  //  Portfolio buyerPortfolio = userData.getUserPortfolio(buyingUser);
                    String stockID = ((TransactionExecutedEvent) simEvent).getAuction().getStockID();
                 //   buyerPortfolio.boughtShares(stockID,transactionEvent.getQuantity(),(float)transactionEvent.getPrice());

                   // userData.updateUserPortfolio(buyingUser, buyerPortfolio);
                 //   notifyListeners(new PortfolioChangedEvent(buyerPortfolio));

            //    } catch (DataAccessException e) {
           //         e.printStackTrace();
             //   }


            }

        }

    }
}
