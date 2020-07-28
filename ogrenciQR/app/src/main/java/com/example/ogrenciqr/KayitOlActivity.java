package com.example.ogrenciqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KayitOlActivity extends AppCompatActivity {
    TextView textView6;
    EditText editText3;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    Button button3;
    Button button4;
    FirebaseAuth mfirebase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        mfirebase =FirebaseAuth.getInstance();
        textView6 = findViewById(R.id.textView6);
        editText3 = findViewById(R.id.editText3);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        editText7 = findViewById(R.id.editText7);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KayitOlActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String adsoyad= editText3.getText().toString();
                final String email=editText5.getText().toString();
                final String pwd=editText6.getText().toString();
                final String durum="GELMEDİ";
                if(editText6.getText().toString().equals(editText7.getText().toString())) {
                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(KayitOlActivity.this,"Hata",Toast.LENGTH_LONG).show();
                    }
                    if(!(email.isEmpty()&&pwd.isEmpty())){
                        mfirebase.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(KayitOlActivity.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    ogrenci ogr = new ogrenci(
                                            adsoyad,
                                            email,
                                            pwd,
                                            durum
                                    );
                                    FirebaseDatabase.getInstance().getReference("Student").child(FirebaseAuth.getInstance()
                                            .getCurrentUser().getUid()).setValue(ogr)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(KayitOlActivity.this, "Kayıt Olma Başarılı!",
                                                    Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(KayitOlActivity.this, "Kayıt Olma Başarısız!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    /*Intent intent = new Intent(KayitOlActivity.this, MainActivity.class);
                    startActivity(intent);*/
                }
                else{
                    textView6.setText("Şifreler Farklı!");
                    textView6.setTextColor(Color.rgb(255,0,0));
                }
            }
        });
    }

}
