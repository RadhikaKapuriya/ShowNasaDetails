package com.example.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Post;
import com.example.showuserdata.MainActivity;
import com.example.showuserdata.R;
import com.example.showuserdata.ShowDetails;
import com.example.viewmodel.MyApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<Post> Posts;
    MainActivity mainActivity;
    public UserAdapter(MainActivity mainActivity1) {
        mainActivity = mainActivity1;
    }


    public void setPostList(ArrayList<Post> users1) {
        Posts = users1;
        Log.d("setUserList123","setPostList = " + Posts.size());
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(Posts.get(position).getUrl())
                .into(holder.tvname);
        Log.d("setUserList123","getTitle = " + Posts.get(position).getDate());

        holder.tvname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainActivity, ShowDetails.class);
                i.putExtra("sampleObject", (Serializable) Posts.get(position));
                mainActivity.startActivity(i);
            }
        });


    }



    @Override
    public int getItemCount() {
        if (Posts != null) {
            return Posts.size();
        } else {
            return 0;
        }
    }








    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tvname;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvname = (ImageView) itemView.findViewById(R.id.tvname);
        }
    }
}
