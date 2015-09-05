package com.juanlabrador.cambiaridioma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    Toolbar mToolbar;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    String[] mLanguages;
    TextView mLanguage;
    LinearLayout mSettingLang;
    String[] mLocales = new String[] {
        "es", "en", "fr", "de", "pt"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.action_setting);
        setSupportActionBar(mToolbar);
        mLanguages = getResources().getStringArray(R.array.languages);
        mPreferences = getSharedPreferences("App", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        mLanguage = (TextView) findViewById(R.id.language);
        mLanguage.setText(mLanguages[mPreferences.getInt("lang", 0)]);

        mSettingLang = (LinearLayout) findViewById(R.id.setting_lang);
        mSettingLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setSingleChoiceItems(mLanguages, mPreferences.getInt("lang", 0), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setLocale(mLocales[i]);
                mEditor.putInt("lang", i);
                mEditor.commit();
                mLanguage.setText(mLanguages[i]);
                //dialogInterface.dismiss();
            }
        });
        mDialog.create();
        mDialog.show();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
    }

}
