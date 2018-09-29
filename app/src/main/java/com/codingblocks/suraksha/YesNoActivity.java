package com.codingblocks.suraksha;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

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

//                Intent i=new Intent(getApplicationContext(),SensorActivity.class);
//                startActivity(i);

//                        Handler handler = new Handler();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    gameOver();
//                                }
//                            },10000);
//                        }

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        gameOver();
                    }
                }, 10000);


                    }


        }



    private void gameOver() {

        String smsNumber = String.format("smsto: %s",
                "8851735067");


//        String smsNumber = String.format("smsto: %s",
//                );


        String sms = "I am in trouble. Please help me out! My current location is :- \n\n https://www.google.com/maps/@28.7298838,76.7325634,11z";
        String vehicleNo = getIntent().getStringExtra(getString(R.string.vehicle_number));
        String transportMode = getIntent().getStringExtra(getString(R.string.transport_mode));

        if(transportMode.length()!=0){
            sms+=" I last boarded in a "+transportMode+". ";
        }

        if(vehicleNo.length()>4){
            sms+="The vehicle number was "+vehicleNo;
        }

        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(smsNumber));
        // Add the message (sms) with the key ("sms_body").
        smsIntent.putExtra("sms_body", sms);
        // If package resolves (target app installed), send intent.
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            Log.d("TAG", "Can't resolve app for ACTION_SENDTO Intent");
        }

    }
}
