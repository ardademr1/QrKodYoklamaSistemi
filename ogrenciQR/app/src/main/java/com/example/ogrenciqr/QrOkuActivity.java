package com.example.ogrenciqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QrOkuActivity extends AppCompatActivity {
    public static TextView textView;
    Button button5;
    Button button6;
    Button button7;
    Button button9;
    String uid;
    private FirebaseAuth.AuthStateListener mauthstatelistener;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        textView=(TextView)findViewById(R.id.textView);
        uid = getIntent().getStringExtra("uid");
        setContentView(R.layout.activity_qr_oku);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
         button5.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(QrOkuActivity.this, CameraActivity.class);
                 intent.putExtra("uid",uid);
                 startActivity(intent);
             }
         });
         button6.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 Intent intent = new Intent(QrOkuActivity.this, MainActivity.class);
                 startActivity(intent);
             }
         });
         button7.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 databaseReference.child(uid).child("durum").setValue("GELDİ");
             }
         });
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QrOkuActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
