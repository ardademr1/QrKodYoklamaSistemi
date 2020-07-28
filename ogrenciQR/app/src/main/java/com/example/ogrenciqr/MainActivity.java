package com.example.ogrenciqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;
    Button button;
    Button button2;
    Button button8;
    String uid;
    FirebaseAuth mfirebaseauth;
    FirebaseAuth.AuthStateListener mauthstatelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfirebaseauth=FirebaseAuth.getInstance();

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button8 = findViewById(R.id.button8);
        mauthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser = mfirebaseauth.getCurrentUser();
                if(mfirebaseuser != null){
                    uid=mfirebaseuser.getUid();
                    Toast.makeText(MainActivity.this,"Giriş Yapıldı",Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(MainActivity.this, QrOkuActivity.class);
                    intent2.putExtra("uid",uid);
                    startActivity(intent2);
                }
                else{
                    Toast.makeText(MainActivity.this,"Lütfen Giriş Yapın",Toast.LENGTH_LONG).show();
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editText.getText().toString();
                String pwd=editText2.getText().toString();
                    if (!(email.isEmpty() && pwd.isEmpty())) {
                        mfirebaseauth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser mfirebaseuser2 = mfirebaseauth.getCurrentUser();
                                if(!task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Giriş Yapılamadı",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    uid=mfirebaseuser2.getUid();
                                    Intent intent1 = new Intent(MainActivity.this, QrOkuActivity.class);
                                    intent1.putExtra("uid",uid);
                                    startActivity(intent1);
                                }
                            }
                        });
                    }
                }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KayitOlActivity.class);
                startActivity(intent);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YoklamaActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseauth.addAuthStateListener(mauthstatelistener);
    }
}
