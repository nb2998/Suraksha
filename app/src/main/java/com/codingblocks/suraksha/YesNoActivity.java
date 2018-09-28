package com.codingblocks.suraksha;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class YesNoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_no);

        Button yesbtn=findViewById(R.id.yes_button);
        Button nobtn=findViewById(R.id.no_button);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.yes_button:
                //TODO :- BYE AND FINSIH ACTIVITY

                break;


            case R.id.no_button:





                break;
        }
    }
}
