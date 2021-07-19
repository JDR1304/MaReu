package com.example.mareu.controller;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.event.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;


import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {


    private List<Meeting> mMeetings;

    public MyMeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String dash = " - ";
        Meeting meeting = mMeetings.get(position);
        holder.meetingRoom.setText(meeting.getRoom().getName());
        holder.roundView.getDrawable().mutate().setTint(meeting.getRoom().getColorDrawable());
        holder.time.setText(dash + meeting.setTime(meeting.getTimeStamp()) + dash);
        holder.reservationName.setText(meeting.getName());
        holder.mailAddress.setText(meeting.getStringMails());

        // Remove le meeting, send event to the meeting fragment to remove it and set a new adapter
        holder.binView.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));

        holder.itemView.setOnClickListener(v -> {
            long id = meeting.getId();
            Log.d("id de la vue", "onBindViewHolder: ");
            EventBus.getDefault().post(AddOrMeetingDetailsFragment.newInstance(id));

        });

    }

    @Override
    public int getItemCount() {

        return mMeetings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView roundView;
        private ImageView binView;
        private TextView meetingRoom;
        private TextView time;
        private TextView reservationName;
        private TextView mailAddress;


        public ViewHolder(View view) {
            super(view);
            roundView = view.findViewById(R.id.imageRound);
            binView = view.findViewById(R.id.imageDelete);
            meetingRoom = view.findViewById(R.id.roomName);
            time = view.findViewById(R.id.time);
            reservationName = view.findViewById(R.id.reservationName);
            mailAddress = view.findViewById(R.id.mailAddress);
        }

    }
}
