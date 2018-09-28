package com.codingblocks.suraksha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);

        final Spinner startDropdown = findViewById(R.id.spinnerStart);
        final Spinner endDropdown = findViewById(R.id.spinnerDestination);
        tvTime = findViewById(R.id.tvTime);
        Button btnEstimate = findViewById(R.id.btnEstimate);
        final LatLongs[] items = new LatLongs[]{
                new LatLongs("Block C", "28.674388", "77.087626"),
                new LatLongs("B3 Block", "28.664372", "77.101573"),
                new LatLongs("A1 Block", "28.676083", "77.099084"),
                new LatLongs("Block 12", "28.662433", "77.090228"),
                new LatLongs("Block 4", "28.665031", "77.010524"),
                new LatLongs("A4 Block", "28.673127", "77.111944"),
                new LatLongs("Guru Harkishan Nagar", "28.673767", "77.084005"),
                new LatLongs("B4 Block", "28.666161", "77.087861"),
                new LatLongs("State Bank Nagar", "28.661416", "77.095807")

        };

        String[] locations = new String[]{
                "Block C", "B3 Block", "A1 Block", "Block 12", "Block 4", "A4 Block", "Guru Harkishan Nagar", "B4 Block", "State Bank Nagar"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        startDropdown.setAdapter(adapter);
        endDropdown.setAdapter(adapter);

        btnEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchRouteDetails(getApplicationContext(), items[startDropdown.getSelectedItemPosition()].getLatitude()+","+items[startDropdown.getSelectedItemPosition()].getLongitude(), items[endDropdown.getSelectedItemPosition()].getLatitude()+","+items[endDropdown.getSelectedItemPosition()].getLongitude());
            }
        });
    }


    public void fetchRouteDetails(Context context, String start, String end) {
        String baseUrl = "https://api.tomtom.com/routing/1/calculateRoute/";
//        String start="28.677050,77.112090:";
//        String longitudes = "28.645380,77.203160";
        String json = "/jsonp?key=";
        String apiKey = context.getString(R.string.api_key);
        String url = baseUrl+start+":"+end+json+apiKey;

        Log.d(TAG, "fetchRouteDetails: "+url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        String result1 = response.body().string();
                        String result = result1.replace("callback(", "");
                        result = result.substring(0, result.length()-1);
                        final RouteDetails routeDetails = gson.fromJson(result, RouteDetails.class);
                        Log.d(TAG, "onResponse: "+routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds());

                        StartTripActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                tvTime.setText("Estimated time is : "+ routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds()+" seconds");
                                Log.d(TAG, "run: "+routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds()+" seconds");

                                Intent ongoingIntent = new Intent(StartTripActivity.this, OngoingTripActivity.class);
                                ongoingIntent.putExtra(getString(R.string.estimated_time), routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds());
                                startActivity(ongoingIntent);
                            }
                        });
                    }
                });
    }
}