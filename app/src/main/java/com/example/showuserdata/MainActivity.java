package com.example.showuserdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.Model.Post;
import com.example.adapter.UserAdapter;
import com.example.viewmodel.UserViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    UserAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new UserAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter  );
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        recyclerView.setOnI
        getData();

    }




    private void getData() {
        userViewModel.getAllPost().observe(this, postList -> {
            adapter.setPostList((ArrayList<Post>) postList);
        });
    }


}
