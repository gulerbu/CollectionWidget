/*
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.burcu.android.collectionwidget.stockwidget;


public class Stock {

    private String stockCode;
    private double stockPrice;
    private boolean increasing;

    public Stock(String stockCode, double stockPrice, boolean increasing) {
        this.stockCode = stockCode;
        this.stockPrice = stockPrice;
        this.increasing = increasing;
    }

    public String getStockCode() {
        return stockCode;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public boolean isIncreasing() {
        return increasing;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public void setIncreasing(double newPrice) {
        increasing = stockPrice >= newPrice;
    }
}
