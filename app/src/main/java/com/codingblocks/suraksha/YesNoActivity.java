package com.codingblocks.suraksha;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class YesNoActivity extends AppCompatActivity implements View.OnClickListener {

    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_no);

        Button yesbtn=findViewById(R.id.yes_button);
        Button nobtn=findViewById(R.id.no_button);

        t1 = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }

            }
        });

        yesbtn.setOnClickListener(this);
        nobtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.yes_button:
                //TODO :- BYE AND FINSIH ACTIVITY
                String toSpeak = "Bye . Have a good day !";
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
               //android.os.Process.killProcess(android.os.Process.myPid());

                Log.e("TAG", "onClick: ---------");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                Log.e("TAG", "onClick: ---------");
                finishAffinity();



                Log.e("TAG", "onClick: ---------");
                break;


            case R.id.no_button:

                Intent i=new Intent(getApplicationContext(),SensorActivity.class);
                startActivity(i);

                break;
        }
    }
}
