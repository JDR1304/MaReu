package com.example.mareu.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.AddMeetingEvent;
import com.example.mareu.event.DeleteMeetingEvent;
import com.example.mareu.event.UpdateMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MeetingFragment extends Fragment {

    public static String TAG= MeetingFragment.class.getSimpleName();
    private List <Meeting> meetings;
    private ApiService mApiService;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter adapter;
    private FloatingActionButton fab;

    /**
     * Create and return a new instance
     *
     * @return @{@link MeetingFragment}
     */
    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getApiService();
        Log.d(TAG, "onCreate: Meeting Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        Log.d(TAG, "onCreateView: Meeting Fragment");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_meeting_recyclerview);
        adapter = new MyMeetingRecyclerViewAdapter(mApiService.getMeeting());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(AddOrMeetingDetailsFragment.newInstance(0));
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: Meeting Fragment");
        //initList();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Meeting Fragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Meeting Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Meeting Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onDestroy: Meeting Fragment");
    }

    @Subscribe
    public void onEventDelete(DeleteMeetingEvent removeMeeting){
        mApiService.removeMeeting(removeMeeting.meeting);
        adapter.notifyDataSetChanged();

    }

    @Subscribe
    public void onEventAdd(AddMeetingEvent addMeeting){
        mApiService.addMeeting(addMeeting.meeting);
        adapter.notifyDataSetChanged();
    }
    @Subscribe
    public void updateMeetingevent(UpdateMeetingEvent updateMeeting){
        mApiService.updateMeeting(updateMeeting.meeting);
        adapter.notifyDataSetChanged();
    }

}
