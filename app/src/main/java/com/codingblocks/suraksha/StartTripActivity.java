package com.codingblocks.suraksha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.codingblocks.suraksha.Models.LatLongs;

public class StartTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);

        Spinner startDropdown = findViewById(R.id.spinnerStart);
        Spinner endDropdown = findViewById(R.id.spinnerDestination);
        Button btnEstimate = findViewById(R.id.btnEstimate);
        LatLongs[] items = new LatLongs[]{
                new LatLongs("Block C",	"28.674388", "77.087626"),
                new LatLongs("B3 Block", "28.664372", "77.101573"),
                new LatLongs("A1 Block", "28.676083", "77.099084"),
                new LatLongs("Block 12", "28.662433", "77.090228"),
                new LatLongs("Block 4", "28.665031", "77.010524"),
                new LatLongs("A4 Block", "28.673127", "77.111944"),
                new LatLongs("Guru Harkishan Nagar", "28.673767", "77.084005"),
                new LatLongs("B4 Block", "28.666161", "77.087861"),
                new LatLongs("State Bank Nagar", "28.661416", "77.095807")

        };
        ArrayAdapter<LatLongs> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        startDropdown.setAdapter(adapter);
        endDropdown.setAdapter(adapter);

        btnEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}