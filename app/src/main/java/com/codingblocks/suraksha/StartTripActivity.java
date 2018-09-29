package com.codingblocks.suraksha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.codingblocks.suraksha.Models.LatLongs;
import com.codingblocks.suraksha.Models.RouteDetails;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartTripActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView tvTime;
    EditText etVehicleNo;
    Spinner transportDropdown;
    ProgressBar p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);

        etVehicleNo=findViewById(R.id.etVehicleNo);
        final Spinner startDropdown = findViewById(R.id.spinnerStart);
        final Spinner endDropdown = findViewById(R.id.spinnerDestination);
        transportDropdown = findViewById(R.id.spinnerTransportMode);

        p=findViewById(R.id.progressbar);

        tvTime = findViewById(R.id.tvTime);
        Button btnEstimate = findViewById(R.id.btnEstimate);
        final LatLongs[] items = new LatLongs[]{
                new LatLongs("Paschim Vihar", "28.677050", "77.112091"),
                new LatLongs("Karol Bagh", "28.654850", "77.187111"),
                new LatLongs("Punjabi Bagh", "28.668489", "77.128632"),
                new LatLongs("Rohini", "28.712700", "77.076668"),
                new LatLongs("Preet Vihar", "28.631410", "77.288230"),
                new LatLongs("Vaishali", "28.702420", "77.137380")

        };

        String[] locations = new String[]{
                "Paschim Vihar", "Karol Bagh", "Punjabi Bagh", "Rohini", "Preet Vihar", "Vaishali"
        };

        final String[] transportationModes = new String[]{
                "Car/Auto", "Bus", "Rickshaw", "Walking"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        startDropdown.setAdapter(adapter);
        endDropdown.setAdapter(adapter);

        ArrayAdapter<String> adapterTransport = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, transportationModes);
        transportDropdown.setAdapter(adapterTransport);

        btnEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRouteDetails(transportDropdown.getSelectedItemPosition(), getApplicationContext(), items[startDropdown.getSelectedItemPosition()].getLatitude()+","+items[startDropdown.getSelectedItemPosition()].getLongitude(), items[endDropdown.getSelectedItemPosition()].getLatitude()+","+items[endDropdown.getSelectedItemPosition()].getLongitude());
            }
        });
    }


    public void fetchRouteDetails(final int transportMode, Context context, String start, String end) {
        String baseUrl = "https://api.tomtom.com/routing/1/calculateRoute/";
//        String start="28.677050,77.112090:";
//        String longitudes = "28.645380,77.203160";
        String json = "/jsonp?key=";
        String apiKey = context.getString(R.string.api_key);
        String url = baseUrl+start+":"+end+json+apiKey;

        p.setVisibility(View.VISIBLE);
        Log.d(TAG, "fetchRouteDetails: "+url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        StartTripActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        String result1 = response.body().string();
                        String result = result1.replace("callback(", "");
                        StartTripActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p.setVisibility(View.INVISIBLE);
                            }
                        });

                        result = result.substring(0, result.length()-1);
                        final RouteDetails routeDetails = gson.fromJson(result, RouteDetails.class);
                        Log.d(TAG, "onResponse: "+routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds());

                        StartTripActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                tvTime.setText("Estimated time is : "+ routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds()+" seconds");
                                Log.d(TAG, "run: "+routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds()+" seconds");

                                Intent ongoingIntent = new Intent(StartTripActivity.this, OngoingTripActivity.class);
                                String finalEstimationTime = getEstimationTime(transportMode, routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds());
                                ongoingIntent.putExtra(getString(R.string.estimated_time), finalEstimationTime);


                                ongoingIntent.putExtra(getString(R.string.vehicle_number),etVehicleNo.getText().toString());
                                ongoingIntent.putExtra(getString(R.string.transport_mode), transportDropdown.getSelectedItem().toString());
                                startActivity(ongoingIntent);
                            }
                        });
                    }
                });
    }

    private String getEstimationTime(int transportMode, String travelTimeInSeconds) {
        if(transportMode == 0){
            return travelTimeInSeconds;
        } else if(transportMode == 1){
            return String.valueOf(Integer.parseInt(travelTimeInSeconds)+1200);
        } else if (transportMode==2){
            return String.valueOf(Integer.parseInt(travelTimeInSeconds)+2400);
        } else{
            return String.valueOf(Integer.parseInt(travelTimeInSeconds)+6000);
        }
    }
}