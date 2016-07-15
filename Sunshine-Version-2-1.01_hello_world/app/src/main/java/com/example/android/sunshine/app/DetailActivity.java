package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final String SHARE_HASH_TAG =" #SunshineApp";
    private String mForecast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //setHasOptionMenu(true);


        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(intent.EXTRA_TEXT)){
            mForecast = intent.getStringExtra(intent.EXTRA_TEXT);
        }

        TextView tv = (TextView) findViewById(R.id.detail_forecast_text_view);

        tv.setText(mForecast);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        getMenuInflater().inflate(R.menu.detailfragment,menu);

        MenuItem menuShare = menu.findItem(R.id.action_share);

        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuShare);

        if(mShareActionProvider != null){
            mShareActionProvider.setShareIntent(CreatShareForecastIntent());
        }else{
            Log.v(LOG_TAG, "No share provider");
        }
        return true;
    }
    // From here on code is From main activity class for menu function

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        if(id == R.id.open_map){
            OpenPreferredLocationInMap();
        }

        return super.onOptionsItemSelected(item);
    }

    private void OpenPreferredLocationInMap() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String location = pref.getString(getString(R.string.pref_key), getString(R.string.pref_defaultValue));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }else{
            Log.d("Intent Issue", "couldn't open map");
        }
    }
    //Up to here From main activity class for menu function

    private Intent CreatShareForecastIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecast + SHARE_HASH_TAG);
        return shareIntent;
    }



}
