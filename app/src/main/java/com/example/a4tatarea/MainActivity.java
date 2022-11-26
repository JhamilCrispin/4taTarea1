package com.example.a4tatarea;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.a4tatarea.adapter.PeliculaAdapter;
import com.example.a4tatarea.model.Pelicula;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    RecyclerView mRecyler;
    PeliculaAdapter mAdapter;
    FirebaseFirestore mFirestore;
    SearchView search_view;
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        search_view = findViewById(R.id.search);

        mFirestore = FirebaseFirestore.getInstance();

        mRecyler = findViewById(R.id.recyclerpeliculas);
        mRecyler.setLayoutManager(new LinearLayoutManager(this));

        setUpRecyclerView();
        search_view();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView() {
        mRecyler = findViewById(R.id.recyclerpeliculas);
        mRecyler.setLayoutManager(new LinearLayoutManager(this));
//      Query query = mFirestore.collection("peli").whereEqualTo("id_user", mAuth.getCurrentUser().getUid());
        query = mFirestore.collection("Pelicula");

        FirestoreRecyclerOptions<Pelicula> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Pelicula>().setQuery(query, Pelicula.class).build();

        mAdapter = new PeliculaAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();
        mRecyler.setAdapter(mAdapter);
    }

    private void search_view() {
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }
    public void textSearch(String s){
        FirestoreRecyclerOptions<Pelicula> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Pelicula>()
                        .setQuery(query.orderBy("titulo")
                                .startAt(s).endAt(s+"~"), Pelicula.class).build();
        mAdapter = new PeliculaAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.startListening();
        mRecyler.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(MainActivity.this, DashBoard.class));
        }
    }

}

