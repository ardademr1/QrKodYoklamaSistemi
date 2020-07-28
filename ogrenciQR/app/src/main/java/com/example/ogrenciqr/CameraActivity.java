package com.example.ogrenciqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    String uid;
    String adSoyad;
    String text;
    public static String sonc;
    public static String getSonc(){
        return sonc;
    }
    DatabaseReference databaseReference;
    DatabaseReference ref;
    DatabaseReference LPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        uid = getIntent().getStringExtra("uid");
        setContentView(scannerView);
        ref = FirebaseDatabase.getInstance().getReference("Student").child(uid).child("adsoyad");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                adSoyad = dataSnapshot.getValue(String.class);
                Log.d(MainActivity.class.getName(), "adsoyad is: " + adSoyad);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(MainActivity.class.getName(), "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public void handleResult(Result result) {
            Toast.makeText(getApplicationContext(), "Yoklama Alındı", Toast.LENGTH_LONG).show();
            text = result.getText();
            String[] separated = text.split("-");
            LPref=FirebaseDatabase.getInstance().getReference(separated[0]);
            LPref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String aa=snapshot.getKey();
                        Log.d(MainActivity.class.getName(), "Key is: " +aa );
                        if(aa.equals(uid)){
                            String[] separated = text.split("-");
                            databaseReference = FirebaseDatabase.getInstance().getReference(separated[0]);
                            databaseReference.child(uid).child("durum").setValue(separated[1]);
                            databaseReference.child(uid).child("adsoyad").setValue(adSoyad);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
