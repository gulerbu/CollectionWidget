/*
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.burcu.android.collectionwidget.stockwidget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.burcu.android.collectionwidget.R;
import com.burcu.android.collectionwidget.StockDetailActivity;

public class StockWidgetProvider extends AppWidgetProvider {
    public static final String KEY_EXTRA_ITEM = "com.burcu.android.collectionwidget.EXTRA_ITEM";
    private static final String REFRESH = "com.burcu.android.collectionwidget.REFRESH";
    private static final String LIST_ITEM_CLICK = "com.burcu.android.collectionwidget.LIST_ITEM_CLICK";

    public static Intent newIntent(Context context, int appWidgetId, String action) {
        final Intent intent = new Intent(context, StockWidgetProvider.class);
        intent.setAction(action);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return intent;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            final Intent intent = new Intent(context, StockWidgetService.class);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_stock_widget);
            remoteViews.setRemoteAdapter(R.id.layout_stock_widget_list_view, intent);

            remoteViews.setEmptyView(R.id.layout_stock_widget_list_view, R.id.layout_stock_widget_text_view_empty_state);

            // Handle user interactions
            setRefreshButtonClick(context, appWidgetIds[i], remoteViews);
            setListItemClick(context, appWidgetIds[i], remoteViews);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        switch (intent.getAction()) {
            case REFRESH:
                final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);

                if (appWidgetId != 0) {
                    AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId,
                            R.id.layout_stock_widget_list_view);
                }
                break;

            case LIST_ITEM_CLICK:
                final String stockCode = intent.getStringExtra(KEY_EXTRA_ITEM);
                context.startActivity(
                        StockDetailActivity.newIntent(context, stockCode).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
                break;
        }

    }

    private void setRefreshButtonClick(Context context, int appWidgetId, RemoteViews remoteViews) {

        final PendingIntent refreshIntent = PendingIntent.getBroadcast(
                context, 0, newIntent(context, appWidgetId, REFRESH),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R
                .id.layout_stock_widget_image_view_refresh, refreshIntent);

    }

    // This section makes it possible for items to have individualized behavior.
    // It does this by setting up a pending intent template. Individuals items of a collection
    // cannot set up their own pending intents. Instead, the collection as a whole sets
    // up a pending intent template, and the individual items set a fillInIntent
    // to create unique behavior on an item-by-item basis
    private void setListItemClick(Context context, int appWidgetId, RemoteViews remoteViews) {
        final PendingIntent itemClickIntent = PendingIntent.getBroadcast(
                context, 0, newIntent(context, appWidgetId, LIST_ITEM_CLICK),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setPendingIntentTemplate(R.id.layout_stock_widget_list_view, itemClickIntent);

    }
}
