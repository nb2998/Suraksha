package com.codingblocks.suraksha;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class OngoingTripActivity extends AppCompatActivity {

    Button btnEndTrip;
    TextView tvTime, tvExactTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_trip);

        btnEndTrip = findViewById(R.id.btnEndTrip);
        tvTime = findViewById(R.id.tvTime);
        tvExactTime = findViewById(R.id.tvExactTime);

        Intent intent = getIntent();
        int timeInSecs = Integer.valueOf(intent.getStringExtra(getString(R.string.estimated_time)));
        tvTime.setText("Estimated time: \n"+Integer.valueOf(timeInSecs)/60+" minutes.");

        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        int totalSeconds = ((hours * 60) + minutes) * 60 + seconds;

        Log.d("TAG", "onCreate: totalsecs "+totalSeconds);

        final int estimatedTimeInSecs = totalSeconds+ timeInSecs;
        Log.d("TAG", "onCreate: estimated  "+estimatedTimeInSecs);

        int finalHours = estimatedTimeInSecs / 3600;  // Be sure to use integer arithmetic
        int finalMinutes = ((estimatedTimeInSecs) / 60) % 60;
        int finalSecs = estimatedTimeInSecs % 60;

        Log.d("TAG", "onCreate: "+finalHours+" "+finalMinutes+" "+finalSecs);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, finalHours);
        cal.set(Calendar.MINUTE, finalMinutes);
        cal.set(Calendar.SECOND, finalSecs);

        Date estimatedArrivalTime = cal.getTime();
        tvExactTime.setText(getString(R.string.estimated_time)+"\n"+estimatedArrivalTime.getHours()+" : "+estimatedArrivalTime.getMinutes());

        Log.d("TAG", "onCreate: "+currentTime.toString());
        Log.d("TAG", "onCreate: "+estimatedArrivalTime.toString());

        Intent alarmIntent = new Intent(OngoingTripActivity.this, YesNoActivity.class);
        alarmIntent.putExtra(getString(R.string.vehicle_number), getIntent().getStringExtra(getString(R.string.vehicle_number)));
        alarmIntent.putExtra(getString(R.string.transport_mode), getIntent().getStringExtra(getString(R.string.transport_mode)));
        PendingIntent pendingIntent = PendingIntent.getActivity(OngoingTripActivity.this, 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + estimatedTimeInSecs*1000, pendingIntent);

        btnEndTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endIntent = new Intent(OngoingTripActivity.this, YesNoActivity.class);
                endIntent.putExtra(getString(R.string.vehicle_number), getIntent().getStringExtra(getString(R.string.vehicle_number)));
                endIntent.putExtra(getString(R.string.transport_mode), getIntent().getStringExtra(getString(R.string.transport_mode)));
                startActivity(endIntent);
            }
        });
    }
}
