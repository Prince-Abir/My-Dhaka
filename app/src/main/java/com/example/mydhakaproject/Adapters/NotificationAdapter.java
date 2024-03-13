package com.example.mydhakaproject.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydhakaproject.Models.NotificationModel;
import com.example.mydhakaproject.R;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    Context context;
    List<NotificationModel> list;
    Activity activity;


    public NotificationAdapter(Context context, Activity activity, List<NotificationModel> itemList) {
        this.context = context;
        this.activity = activity;
        this.list = itemList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_notification, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.text.setText(list.get(position).getText());
        holder.date.setText(list.get(position).getDate());

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.move_left));


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, text, date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notify_title);
            text = itemView.findViewById(R.id.notify_text);
            date = itemView.findViewById(R.id.notify_date);

        }
    }


}


