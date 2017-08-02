/*
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.burcu.android.collectionwidget.stockwidget;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class StockUtil {

    private static final String DECIMAL_FORMAT = "#0.000";

    public static ArrayList<Stock> getInitialStockList() {
        final ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("AFYON", 8.75, false));
        stocks.add(new Stock("AGYO", 2.97, true));
        stocks.add(new Stock("AKBNK", 9.75, false));
        stocks.add(new Stock("BMEKS", 0.64, false));
        stocks.add(new Stock("ECILC", 4.34, false));
        stocks.add(new Stock("GOLDP", 9.90, true));
        stocks.add(new Stock("HATEK", 4.64, true));
        stocks.add(new Stock("KUYAS", 4.60, false));
        stocks.add(new Stock("SANKO", 4.41, true));

        return stocks;

    }

    public static void refreshStockList(ArrayList<Stock> stocks) {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        for (Stock stock : stocks) {
            final double price = Double.parseDouble(format.format(new Random().nextDouble() * 10)) ;
            stock.setIncreasing(price);
            stock.setStockPrice(price);
        }

    }

}
