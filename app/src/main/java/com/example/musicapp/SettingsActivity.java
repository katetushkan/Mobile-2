package com.example.musicapp;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {

    private String SelectedFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final Integer[] language = {getIntent().getIntExtra("lan", 0)};

        final Integer[] textsize = {getIntent().getIntExtra("textSize", 16)};

        final Integer[] fontId = {getIntent().getIntExtra("fontId", 1)};


        Resources res = getResources();
        String[] fonts = res.getStringArray(R.array.fonts);
        final Intent intent = new Intent();

        final Spinner spinner  = findViewById(R.id.spinner);
        if (spinner != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fonts);
            spinner.setAdapter(adapter);

        }


        final TextView setSize = findViewById(R.id.textView);
        final Button changeLanguage = findViewById(R.id.language);
        if (language[0] == 0) {

            changeLanguage.setText("English");
            setSize.setText("Text size:");
        } else {
            changeLanguage.setText("русский");
            setSize.setText("Размер шрифта");

        }
        changeLanguage.setTextSize(textsize[0].floatValue());
        setSize.setTextSize(textsize[0].floatValue());

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(textsize[0]-16);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setMax(10);
                textsize[0] = textsize[0] + progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                intent.putExtra("textSize", textsize[0]);
                changeLanguage.setTextSize(textsize[0].floatValue());
                setSize.setTextSize(textsize[0].floatValue());
                setResult(RESULT_OK, intent);
                textsize[0] = 16;

            }
        });
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (language[0] == 0) {
                    language[0] = 1;
                    changeLanguage.setText("русский");
                    setSize.setText("Размер шрифта");
                    intent.putExtra("lan", language[0]);
                    setResult(RESULT_OK, intent);

                } else {
                    language[0] = 0;
                    changeLanguage.setText("English");
                    setSize.setText("Text size:");
                    intent.putExtra("lan", language);
                    setResult(RESULT_OK, intent);

                }
            }
        });


    }




}
