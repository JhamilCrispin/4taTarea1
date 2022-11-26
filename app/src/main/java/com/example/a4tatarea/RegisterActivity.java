package com.example.a4tatarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Creamos variables para asignarles un objeto

    private EditText txtUser;
    private EditText txtMail;
    private EditText txtPhone;
    private TextInputLayout txtPassword;
    private Button btnRegister;
    private TextView lblLogin;

    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //asignamos el objeto a la variable
        txtUser = findViewById(R.id.txtUser);
        txtMail = findViewById(R.id.txtMail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        lblLogin = findViewById(R.id.lblLogin);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(view->
                createuser()
                );

        //Ejecutamos el metodo para el click
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    //metodo para abrir la ventana del login
    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void createuser(){
        String name = txtUser.getText().toString();
        String mail = txtMail.getText().toString();
        String phone = txtPhone.getText().toString();
        String password = txtPassword.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)){
            txtMail.setError("Ingrese un nombre");
            txtMail.requestFocus();
        }else if (TextUtils.isEmpty(mail)){
            txtMail.setError("Ingrese un Corero");
            txtMail.requestFocus();
        }else if (TextUtils.isEmpty(phone)){
            txtMail.setError("Ingrese un telefono");
            txtMail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            txtMail.setError("Ingrese una contraseña");
            txtMail.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String,Object> user = new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Correo", mail);
                        user.put("Telefono", phone);
                        user.put("Contraseña", password);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario registrado",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }else{
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    }
            });
        }

    }

}
