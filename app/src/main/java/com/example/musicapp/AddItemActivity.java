package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText artistName;
    private EditText albumName;
    private EditText year;
    private EditText description;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        artistName = findViewById(R.id.artist_new_text);
        albumName = findViewById(R.id.album_new_text);
        year = findViewById(R.id.year_new_text);
        description = findViewById(R.id.description_new_text);
        add = findViewById(R.id.add_button);
        add.setOnClickListener(this);

    }

    private void saveItem(){
        String newArtist = artistName.getText().toString();
        String newAlbum = albumName.getText().toString();
        String newYear = year.getText().toString();
        String newDescription = description.getText().toString();

        if (newAlbum.trim().isEmpty() || newArtist.trim().isEmpty() || newYear.trim().isEmpty()){
            Toast.makeText(this, "Please insert info", Toast.LENGTH_LONG);
            return;
        }

        CollectionReference albumsRef = FirebaseFirestore.getInstance().collection("albums");
        albumsRef.add(new musicItem("", newArtist, newYear, newAlbum, newDescription,""));
        Toast.makeText(this, "Added", Toast.LENGTH_LONG);
    }

    @Override
    public void onClick(View v) {
        saveItem();
    }
}
