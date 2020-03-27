package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.Permission;

public class AddItemActivity extends AppCompatActivity {


    private EditText artistName;
    private EditText albumName;
    private EditText year;
    private EditText description;
    private Button add;
    Uri videoUri;
    private String newArtist;
    private String newAlbum;
    private String newYear ;
    private String newDescription;
    private ProgressDialog progressDialog;
    private TextView notification;
    private Button choose;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        artistName = findViewById(R.id.artist_new_text);
        albumName = findViewById(R.id.album_new_text);
        year = findViewById(R.id.year_new_text);
        description = findViewById(R.id.description_new_text);
        add = findViewById(R.id.add_button);
        choose = findViewById(R.id.choose);
        notification = findViewById(R.id.notification);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectVideo();
                }
                else
                    ActivityCompat.requestPermissions(AddItemActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoUri != null){
                    saveItem(videoUri);
                }
                else
                    Toast.makeText(AddItemActivity.this, "Please6 select a file", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            selectVideo();
        }
        else
            Toast.makeText(AddItemActivity.this, "check permission", Toast.LENGTH_LONG).show();
    }

    private void selectVideo(){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
            notification.setText("A file is selected: " + data.getData().getLastPathSegment() );
        } else
            Toast.makeText(AddItemActivity.this, "can't find file", Toast.LENGTH_SHORT).show();
    }

    private void saveItem(Uri videoUri){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();


         newArtist = artistName.getText().toString();
         newAlbum = albumName.getText().toString();
         newYear = year.getText().toString();
         newDescription = description.getText().toString();


        String fileName = System.currentTimeMillis()+"";


        // returns root path
        storageRef.child("video/").child(fileName).putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CollectionReference albumsRef = FirebaseFirestore.getInstance().collection("albums");
                        String trailer = uri.toString();
                        albumsRef.add(new musicItem("", newArtist, newYear, newAlbum, newDescription,trailer));
                        Toast.makeText(AddItemActivity.this, "Added", Toast.LENGTH_LONG);
                        AddItemActivity.this.finish();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddItemActivity.this, "Not uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

        if (newAlbum.trim().isEmpty() || newArtist.trim().isEmpty() || newYear.trim().isEmpty()){
            Toast.makeText(this, "Please insert info", Toast.LENGTH_LONG);
            return;
        }


    }


}
