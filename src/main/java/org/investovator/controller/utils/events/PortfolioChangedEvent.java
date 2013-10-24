package org.investovator.controller.utils.events;

import org.investovator.core.commons.utils.Portfolio;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class PortfolioChangedEvent extends GameEvent {

    private Portfolio portfolio;

    public PortfolioChangedEvent(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
