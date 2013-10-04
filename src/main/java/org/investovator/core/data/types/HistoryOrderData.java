package org.investovator.core.org.investovator.data.types;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public class HistoryOrderData {

    private String date;
    private int numOfShares;
    private double price;
    private String stockId;
    private boolean isBid;

    public HistoryOrderData(String date, int numOfShares, double price, String stockId, boolean bid) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.stockId = stockId;
        isBid = bid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public boolean isBid() {
        return isBid;
    }

    public void setBid(boolean bid) {
        isBid = bid;
    }
}
