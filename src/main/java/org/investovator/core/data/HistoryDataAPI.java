package org.investovator.core.org.investovator.data;

import org.investovator.core.org.investovator.data.types.HistoryOrderData;

import java.util.Date;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public interface HistoryDataAPI {

    public HistoryOrderData[] getTradingData(Date startTime, Date endTime, String stockId );

    public com.investovator.data.HistoryData getOHLCPData(Date day,String stockId);
}
