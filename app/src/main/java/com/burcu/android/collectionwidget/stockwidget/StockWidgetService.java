/*
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.burcu.android.collectionwidget.stockwidget;


import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.burcu.android.collectionwidget.R;

import java.util.ArrayList;

public class StockWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetRemoteViewsFactory();
    }

    class StockWidgetRemoteViewsFactory implements RemoteViewsFactory {
        private ArrayList<Stock> stocks;


        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        @Override
        public void onCreate() {
            // Initialize stock list
            stocks = new ArrayList<>();
            stocks.addAll(StockUtil.getInitialStockList());

        }

        // You can handle downloading or creating content operations in this method. Requesting update stocks is handle
        // here by imitating.
        @Override
        public void onDataSetChanged() {
            StockUtil.refreshStockList(stocks);

        }

        @Override
        public void onDestroy() {
            stocks.clear();
        }

        @Override
        public int getCount() {
            return stocks.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            final RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layout_stock_widget_item);

            if (position < getCount()) {
                remoteView.setTextViewText(R.id.stock_item_text_view_code, stocks.get(position).getStockCode());
                remoteView.setTextViewText(R.id.stock_item_text_view_price,
                        String.valueOf(stocks.get(position).getStockPrice())
                );
                remoteView.setImageViewResource(R.id.stock_item_image_view_icon,
                        stocks.get(position).isIncreasing() ? R.drawable.ic_raise : R.drawable.ic_fall
                );

                final Intent fillInIntent = new Intent();
                final Bundle extras = new Bundle();
                extras.putString(StockWidgetProvider.KEY_EXTRA_ITEM, stocks.get(position).getStockCode());

                fillInIntent.putExtras(extras);
                // Make it possible to distinguish the individual on-click
                // action of a given item
                remoteView.setOnClickFillInIntent(R.id.stock_item_linear_layout_container, fillInIntent);
            }

            return remoteView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
