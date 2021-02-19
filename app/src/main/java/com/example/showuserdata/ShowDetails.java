package com.example.showuserdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Post;
import com.example.adapter.UserAdapter;
import com.example.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ShowDetails extends Activity {

    private UserViewModel userViewModel;
    ImageView showimg;
    TextView tvtitle,tvdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdetails);

        showimg = findViewById(R.id.tvname);
        tvtitle = findViewById(R.id.tvtitle);
        tvdetails = findViewById(R.id.tvdetails);

        getData();

    }




    private void getData() {
        Intent i = getIntent();
        Post dene = (Post)i.getSerializableExtra("sampleObject");
        Picasso.get()
                .load(dene.getUrl())
//                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(showimg);
        tvtitle.setText(dene.getTitle());
        tvdetails.setText(dene.getExplanation());

        findViewById(R.id.ivback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
