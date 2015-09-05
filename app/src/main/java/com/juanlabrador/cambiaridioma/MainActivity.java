package com.juanlabrador.cambiaridioma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by juanlabrador on 05/09/15.
 */
public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    SharedPreferences mPreferences;
    Calendar mCalendar;
    SimpleDateFormat mFormatDate;
    SimpleDateFormat mFormatDay;
    String[] mLocales = new String[] {
            "es", "en", "fr", "de", "pt"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        init();

    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);

        mCalendar = Calendar.getInstance();
        mFormatDay = new SimpleDateFormat(getString(R.string.format_day),
                new Locale(mLocales[mPreferences.getInt("lang", 0)]));
        mFormatDate = new SimpleDateFormat(getString(R.string.format_date),
                new Locale(mLocales[mPreferences.getInt("lang", 0)]));

        TextView mHello = (TextView) findViewById(R.id.hello);
        mHello.setText(R.string.hello);
        TextView mDate = (TextView) findViewById(R.id.date);
        mDate.setText(String.format(getString(R.string.date),
                mFormatDate.format(mCalendar.getTime()), mFormatDay.format(mCalendar.getTime())));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting)
            startActivity(new Intent(this, SettingsActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
