/*
 * Copyright 2003-2017 Monitise Group Limited. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Monitise Group Limited.
 * Any reproduction of this material must contain this notice.
 */

package com.burcu.android.collectionwidget;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StockDetailActivity extends AppCompatActivity {

    private static final String KEY_STOCK_CODE = "stockCode";

    public static Intent newIntent(Context context, String stockCode) {
        final Intent intent = new Intent(context, StockDetailActivity.class);
        intent.putExtra(KEY_STOCK_CODE, stockCode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        ((TextView) findViewById(R.id.stock_detail_text_view_message)).setText(String.format(
                getString(R.string.detailed_view_text), getIntent().getStringExtra(KEY_STOCK_CODE))
        );

    }
}
