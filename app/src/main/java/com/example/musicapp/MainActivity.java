package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.SearchView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference albumRef = db.collection("albums");
    private static final String TAG = "MainActivity";
    private MenuItem settingItem;
    private Typeface typeface;



    int language = 0;
    int textSize = 16;
    int fontId = 2131296257;

    //Variables
    private List<musicItem> exampleList = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initImageBitmaps();
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_item);
        typeface = ResourcesCompat.getFont(this, fontId);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                intent.putExtra("lan", language);
                intent.putExtra("textSize", textSize);
                intent.putExtra("fontId", fontId);
                startActivity(intent);
            }
        });


    }


    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        exampleList.clear();

            albumRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                        musicItem musicItem = documentSnapshots.toObject(musicItem.class);
                        musicItem.setDocumentId(documentSnapshots.getId());
                        exampleList.add(musicItem);
                    }
                    initRecyclerview();
                }

            });




    }


    private void initRecyclerview(){
        Log.d(TAG, "initRecyclerview: init recuclerview");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);

        adapter = new RecyclerViewAdapter( this, exampleList, textSize, language, fontId, typeface);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        settingItem = menu.findItem(R.id.action_settings);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("lan", language);
                intent.putExtra("textSize", textSize);
                intent.putExtra("fontId", fontId);
                startActivityForResult(intent, 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            textSize = data.getIntExtra("textSize", 16);
            language = data.getIntExtra("lan", 0);
            fontId = data.getIntExtra("fontId", 0);
        }


        if(language == 0){
            settingItem.setTitle("Settings");

        }
        else{
            settingItem.setTitle("Настройки");
        }

    }
}
