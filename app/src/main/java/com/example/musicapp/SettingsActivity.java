package com.example.musicapp;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final Integer[] language = {getIntent().getIntExtra("lan", 0)};



        Integer textsize = getIntent().getIntExtra("text", 16);
        Spinner spinner  = findViewById(R.id.spinner);

        Integer fontId = 2131296257;
        Resources res = getResources();
        String[] fonts = res.getStringArray(R.array.fonts);

        if (spinner != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fonts);
            spinner.setAdapter(adapter);
        }

        final Button changeLanguage = findViewById(R.id.language);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItem settings = findViewById(R.id.action_settings);
                TextView setSize = findViewById(R.id.textView);
                if (language[0] == 0) {
                    language[0] = 1;
                    changeLanguage.setText("русский");
                    setSize.setText("Размер шрифта");
//                    settings.setTitle("настройки");
                } else {
                    language[0] = 0;
                    changeLanguage.setText("English");
                    setSize.setText("Text size:");
//                    settings.setTitle("settings");
                }
            }
        });


    }




}
