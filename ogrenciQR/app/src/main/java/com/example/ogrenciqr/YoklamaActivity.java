package com.example.ogrenciqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YoklamaActivity extends AppCompatActivity {
    ListView listview;
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference refkey;
    ArrayList<String> list;
    String value;
    ArrayAdapter<String> adapter;
    ogrenci ogrenci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoklama);
        ogrenci = new ogrenci();
        listview = (ListView) findViewById(R.id.listview);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Student");
        refkey = FirebaseDatabase.getInstance().getReference("Key");
        // Read from the database
        refkey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(String.class);
                Log.d(MainActivity.class.getName(), "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(MainActivity.class.getName(), "Failed to read value.", error.toException());
            }
        });
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.user_info,R.id.userInfo,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ogrenci = ds.getValue(ogrenci.class);
                    if(value.equals(ogrenci.getDurum())) {
                        list.add(ogrenci.getAdsoyad().toString() + "  " + "Geldi");
                    }
                }
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
