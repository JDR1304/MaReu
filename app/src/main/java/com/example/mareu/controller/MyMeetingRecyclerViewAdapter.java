package com.example.mareu.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import java.util.List;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private ImageView roundView;
    private ImageView binView;
    private TextView meetingRoom;
    private TextView time;
    private TextView reservationName;
    private TextView mailAddress;

    private final List<Meeting> mMeetings;

    public MyMeetingRecyclerViewAdapter (List<Meeting> items){ mMeetings = items;}


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = = mMeetings.get
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView binView;
        private TextView meetingRoom;
        private TextView time;
        private TextView reservationName;
        private TextView mailAddress;


        public ViewHolder(View view) {
            super(view);
            roundView = view.fin
        }
    }
}
