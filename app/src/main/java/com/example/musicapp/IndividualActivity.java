package com.example.musicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class IndividualActivity extends AppCompatActivity {

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference videoRef = storageRef.child("video/");

    private static final String TAG = "IndividualActivity";


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


            setImage(imageUrl, albumName, artistName, year, descrip, trailer);
        }
    }





    private void setImage(String imageUrl, String imageName, String artistName, String year, String descrip, String trailer){
        Log.d(TAG, "setImage: setting exstras");

        TextView name = findViewById(R.id.albumNameIndivid);
        name.setText(imageName);

        TextView artist = findViewById(R.id.artistNameIndivid);
        artist.setText(artistName);

        TextView yearText = findViewById(R.id.yearIndivid);
        yearText.setText(year);

        TextView description = findViewById(R.id.image_description);
        description.setText(descrip);

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
