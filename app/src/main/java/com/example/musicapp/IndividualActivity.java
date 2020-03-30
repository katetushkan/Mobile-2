package com.example.musicapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class IndividualActivity extends AppCompatActivity {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference videoRef = storageRef.child("video/");

    private static final String TAG = "IndividualActivity";
    private Integer textSize;
    private Integer language;
    private Integer fontId;
    private Typeface typeface;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        Log.d(TAG, "onCreate: started");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: waiting for intents");
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("albumName")){
            Log.d(TAG, "getIncomingIntent: found extras");

            String imageUrl = getIntent().getStringExtra("image_url");
            String albumName = getIntent().getStringExtra("albumName");
            String artistName = getIntent().getStringExtra("artistName");
            String year = getIntent().getStringExtra("year");
            String descrip = getIntent().getStringExtra("description");
            String trailer = getIntent().getStringExtra("trailer");
            textSize = getIntent().getIntExtra("textSize", 16);
            language = getIntent().getIntExtra("lan", 0);
            fontId = getIntent().getIntExtra("fontId", 1);

            typeface = ResourcesCompat.getFont(this, fontId);

            setImage(imageUrl, albumName, artistName, year, descrip, trailer);
        }
    }





    private void setImage(String imageUrl, String imageName, String artistName, String year, String descrip, String trailer){
        Log.d(TAG, "setImage: setting exstras");

        TextView name = findViewById(R.id.albumNameIndivid);
        name.setText(imageName);
        name.setTextSize(textSize.floatValue());
        name.setTypeface(typeface);

        TextView artist = findViewById(R.id.artistNameIndivid);
        artist.setText(artistName);
        artist.setTextSize(textSize.floatValue());
        artist.setTypeface(typeface);

        TextView yearText = findViewById(R.id.yearIndivid);
        yearText.setText(year);
        yearText.setTextSize(textSize.floatValue());
        yearText.setTypeface(typeface);

        TextView description = findViewById(R.id.image_description);
        description.setText(descrip);
        description.setTextSize(textSize.floatValue());
        description.setTypeface(typeface);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap().load(imageUrl).into(image);

//        String path = "https://firebasestorage.googleapis.com/v0/b/musicapp-b53aa.appspot.com/o/video%2Fbeyonce.mp4?alt=media&token=2beb9cf6-278c-47e9-b6a8-90eb4fe1a71d";
        VideoView video = findViewById(R.id.video);
        Uri uri = Uri.parse(trailer);
        video.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
