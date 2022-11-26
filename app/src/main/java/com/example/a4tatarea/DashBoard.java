package com.example.a4tatarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a4tatarea.adapter.PeliculaAdapter;
import com.example.a4tatarea.model.Pelicula;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class DashBoard extends AppCompatActivity {


    Button btnSalir, btnSave;
    RecyclerView mRecycler;
    PeliculaAdapter mAdapter;
    FirebaseFirestore mFirestore;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mRecycler = findViewById(R.id.recyclerpeliculas);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("Pelicula");

        FirestoreRecyclerOptions<Pelicula> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Pelicula>().setQuery(query, Pelicula.class).build();

        mAdapter = new PeliculaAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);


        btnSalir = findViewById(R.id.btnSalir);
        btnSave = findViewById(R.id.btnAñadir);
        mAuth = FirebaseAuth.getInstance();

        btnSalir.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, AgregarPeliculaActivity.class));
            }
        });

    }//End onCreate
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


}