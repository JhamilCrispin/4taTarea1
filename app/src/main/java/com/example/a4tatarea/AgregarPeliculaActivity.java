package com.example.a4tatarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AgregarPeliculaActivity extends AppCompatActivity {
    Button btn_save;
    EditText titulo,resolucion,genero,duracion,sinopsis,director,año;
    FirebaseFirestore firestore;
    FirebaseAuth Auth;

    StorageReference storageReference;
    String storage_path = "Pelicula/*";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        String id = getIntent().getStringExtra("id_peli");
        firestore = FirebaseFirestore.getInstance();
        Auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        titulo = findViewById(R.id.titulo);
        resolucion = findViewById(R.id.resolucion);
        genero = findViewById(R.id.genero);
        duracion = findViewById(R.id.duracion);
        sinopsis = findViewById(R.id.sinopsis);
        director = findViewById(R.id.director);
        año = findViewById(R.id.año);
        btn_save = findViewById(R.id.btn_guardar);

        if (id == null || id == ""){
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String savetitulo = titulo.getText().toString().trim();
                    String saveresolucion = resolucion.getText().toString().trim();
                    String savegenero = genero.getText().toString().trim();
                    String saveduracion = duracion.getText().toString().trim();
                    String savesinopsis = sinopsis.getText().toString().trim();
                    String savedirector = director.getText().toString().trim();
                    String saveaño = año.getText().toString().trim();

                    if (savetitulo.isEmpty() && saveresolucion.isEmpty() && savegenero.isEmpty() && saveduracion.isEmpty() && savesinopsis.isEmpty() && savedirector.isEmpty() && saveaño.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingrese sus Datos", Toast.LENGTH_SHORT).show();
                    }else {
                        postPelicula(savetitulo, saveresolucion, savegenero, saveduracion, savesinopsis, savedirector, saveaño);
                    }
                }
            });
        }else {
            btn_save.setText("Update");
            getPelicula(id);
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String savetitulo = titulo.getText().toString().trim();
                    String saveresolucion = resolucion.getText().toString().trim();
                    String savegenero = genero.getText().toString().trim();
                    String saveduracion = duracion.getText().toString().trim();
                    String savesinopsis = sinopsis.getText().toString().trim();
                    String savedirector = director.getText().toString().trim();
                    String saveaño = año.getText().toString().trim();

                    if (savetitulo.isEmpty() && saveresolucion.isEmpty() && savegenero.isEmpty() && saveduracion.isEmpty() && savesinopsis.isEmpty() && savedirector.isEmpty() && saveaño.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingrese sus Datos", Toast.LENGTH_SHORT).show();
                    }else {
                        updatePelicula(savetitulo, saveresolucion, savegenero, saveduracion, savesinopsis, savedirector, saveaño, id);
                    }
                }
            });
        }
    }

    private void updatePelicula(String savetitulo, String saveresolucion, String savegenero, String saveduracion, String savesinopsis, String savedirector,String saveaño, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("guardar_titulo", savetitulo);
        map.put("guardar_resolucion", saveresolucion);
        map.put("guardar_genero", savegenero);
        map.put("guardar_duracion", saveduracion);
        map.put("guardar_sinopsis", savesinopsis);
        map.put("guardar_director", savedirector);
        map.put("guardar_año", saveaño);

        firestore.collection("Pelicula").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Se Guardo los Datos Correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Guardar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postPelicula(String savetitulo, String saveresolucion, String savegenero, String saveduracion, String savesinopsis, String savedirector,String saveaño) {
        String idUser = Auth.getCurrentUser().getUid();
        DocumentReference id = firestore.collection("Pelicula").document();

        Map<String, Object> map = new HashMap<>();
        map.put("id_user", idUser);
        map.put("titulo", savetitulo);
        map.put("resolucion", saveresolucion);
        map.put("genero", savegenero);
        map.put("duracion", saveduracion);
        map.put("sinopsis", savesinopsis);
        map.put("director", savedirector);
        map.put("año", saveaño);

        firestore.collection("Pelicula").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Se Guardo los Datos Correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Guardar los Datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPelicula(String id){
        firestore.collection("Pelicula").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String tiPeli = documentSnapshot.getString("titulo");
                String rePeli = documentSnapshot.getString("resolucion");
                String gePeli = documentSnapshot.getString("genero");
                String duPeli = documentSnapshot.getString("duracion");
                String siPeli = documentSnapshot.getString("sinopsis");
                String diPeli = documentSnapshot.getString("director");
                String añoPeli = documentSnapshot.getString("año");

               titulo.setText(tiPeli);
               resolucion.setText(rePeli);
               genero.setText(gePeli);
               duracion.setText(duPeli);
               sinopsis.setText(siPeli);
               director.setText(diPeli);
               año.setText(añoPeli);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Guardar los Datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}