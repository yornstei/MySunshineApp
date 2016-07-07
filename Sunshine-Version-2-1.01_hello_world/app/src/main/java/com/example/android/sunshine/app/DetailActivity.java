package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        String detailForecast = intent.getStringExtra(intent.EXTRA_TEXT);

        TextView tv = (TextView) findViewById(R.id.detail_forecast_text_view);

        tv.setText(detailForecast);
    }
}
