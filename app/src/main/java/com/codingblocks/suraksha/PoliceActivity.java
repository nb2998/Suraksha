package com.codingblocks.suraksha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codingblocks.suraksha.Models.PoliceDetail;

import java.util.ArrayList;

public class PoliceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);

        ArrayList<PoliceDetail> policeDetails = new ArrayList<>();
        policeDetails.add(new PoliceDetail("Deputy Comissioner Of Police", "01127034873"));
        policeDetails.add(new PoliceDetail("Police station Karol Bagh", "01128720482"));
        policeDetails.add(new PoliceDetail("Police station Rajendra Nagar", "0112874012"));
        policeDetails.add(new PoliceDetail("Police station Paharganj", "01123524746"));
        policeDetails.add(new PoliceDetail("Police station Connought Place", "01123747100"));
        policeDetails.add(new PoliceDetail("DCP Office, Delhi Police", "100"));
        policeDetails.add(new PoliceDetail("Anti crime cell @CCPM West and Outer District", "08882111255"));
        policeDetails.add(new PoliceDetail("Khyala Police", "08750871123"));
        policeDetails.add(new PoliceDetail("Police Station Punjabi Bagh", "0112554162"));


        Police adapter = new Police(policeDetails, getApplicationContext());
        RecyclerView recView = findViewById(R.id.recViewPolice);

        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);



    }
}
