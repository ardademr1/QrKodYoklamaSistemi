package com.example.ogrenciqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DosyaActivity extends AppCompatActivity {
    Button button11;
    ImageView ımageView;
    TextView textView3;
    TextView textView4;
    ProgressBar progressBar2;
    CountDownTimer countDownTimer;
    private long timeLeftMilliSeconds=10000;
    private boolean timerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosya);
        button11 = findViewById(R.id.button11);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        progressBar2 = findViewById(R.id.progressBar2);
        ımageView=findViewById(R.id.imageView3);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button11.getText()=="Start"){
                    button11.setText("Stop");
                    textView3.setText("Bağlantı İçin Cihazı Sallayın");
                    textView4.setText("Cihaz Aranıyor....");
                    textView4.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    ımageView.setVisibility(View.INVISIBLE);
                    startTimer();
                }
                else{
                    button11.setText("Start");
                    textView3.setText("Başlatmak İçin Start a Basın!");
                    textView4.setText("Cihaz Aranıyor....");
                    textView4.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.INVISIBLE);
                    ımageView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void startStop(){
        if(timerRunning){
            startTimer();
        }
        else{
            stopTimer();
        }
    }
    public void startTimer(){
        countDownTimer= new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                timeLeftMilliSeconds=l;
            }

            @Override
            public void onFinish() {
                textView4.setText("Bağlantı Başarılı.");
                textView4.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.INVISIBLE);
                ımageView.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning=true;
    }
    public void stopTimer(){
        countDownTimer.cancel();
        timerRunning=false;
    }
}
