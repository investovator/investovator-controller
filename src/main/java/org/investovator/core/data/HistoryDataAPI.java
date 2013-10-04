package org.investovator.core.org.investovator.data;

import org.investovator.core.org.investovator.data.types.HistoryOrderData;
import org.investovator.data.excel.HistoryData;

import java.util.Date;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public interface HistoryDataAPI {

    public HistoryOrderData[] getTradingData(Date startTime, Date endTime, String stockId );

    public HistoryData getOHLCPData(Date day,String stockId);
}
