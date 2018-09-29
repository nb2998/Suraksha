package com.codingblocks.suraksha;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.suraksha.Models.Contact;
import com.codingblocks.suraksha.Models.PoliceDetail;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class Police extends RecyclerView.Adapter<Police.Viewholder> {

    ArrayList<PoliceDetail> policeDetails;
    Context context;

    public Police(ArrayList<PoliceDetail> policeDetails, Context context) {
        this.policeDetails = policeDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater l=LayoutInflater.from(context);
        View view=l.inflate(R.layout.content_police_mail,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, int i) {

        PoliceDetail p=policeDetails.get(i);
        viewholder.number.setText(p.getNumber());
        viewholder.name.setText(p.getPoliceStation());
        viewholder.imageView.setImageResource(R.drawable.police);

        viewholder.name.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick12345: "+viewholder.number.toString());
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel://"+viewholder.number.getText().toString()));//change the number
                context.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return policeDetails.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name , number;
        ImageView imageView;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
            imageView=itemView.findViewById(R.id.imageView);



        }
    }


}