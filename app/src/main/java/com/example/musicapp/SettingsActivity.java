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
import java.util.HashMap;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private HashMap<String, Integer> SelectedFont;
    private Typeface typeface;
    private Integer fontId;
    private TextView setSize;
    private Button changeLanguage;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final Integer[] language = {getIntent().getIntExtra("lan", 0)};

        final Integer[] textsize = {getIntent().getIntExtra("textSize", 16)};

        fontId = getIntent().getIntExtra("fontId", R.font.lobster);

        typeface = ResourcesCompat.getFont(this, fontId);
        SelectedFont = new HashMap<>();
        SelectedFont.put("beer_money", R.font.beer_money);
        SelectedFont.put("normal", R.font.normal);
        SelectedFont.put("boomboom", R.font.boomboom);
        SelectedFont.put("christmas_script", R.font.christmas_script);
        SelectedFont.put("gnyrwn", R.font.gnyrwn);
        SelectedFont.put("lobster", R.font.lobster);
        HashMap<Integer, String> fontsstr = new HashMap<>();
        fontsstr.put(R.font.beer_money, "beer_money");
        fontsstr.put(R.font.boomboom, "boomboom");
        fontsstr.put(R.font.lobster, "lobster");
        fontsstr.put(R.font.christmas_script, "christmas_script");
        fontsstr.put(R.font.gnyrwn, "gnyrwn");
        fontsstr.put(R.font.normal, "normal");
        Resources res = getResources();
        String[] fonts = res.getStringArray(R.array.fonts);
        intent = new Intent();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fonts);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int spinnerPosition = adapter.getPosition(fontsstr.get(fontId));
        spinner.setSelection(spinnerPosition);




        setSize = findViewById(R.id.textView);
        changeLanguage = findViewById(R.id.language);
        if (language[0] == 0) {

            changeLanguage.setText("English");
            setSize.setText("Text size:");
        } else {
            changeLanguage.setText("русский");
            setSize.setText("Размер шрифта");

        }
        changeLanguage.setTextSize(textsize[0].floatValue());
        setSize.setTextSize(textsize[0].floatValue());
        changeLanguage.setTypeface(typeface);
        setSize.setTypeface(typeface);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        fontId = SelectedFont.get(text);
        typeface = ResourcesCompat.getFont(this, fontId);
        changeLanguage.setTypeface(typeface);
        setSize.setTypeface(typeface);
        intent.putExtra("fontId", fontId);
        setResult(RESULT_OK, intent);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
