package com.example.mareu.controller;


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
import com.example.mareu.model.Room;
import com.example.mareu.service.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MeetingFragment extends Fragment {

    public static String TAG_MEETING_FRAGMENT = MeetingFragment.class.getSimpleName();
    private ApiService mApiService;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter adapter;
    private FloatingActionButton fab;
    private List<Meeting> mMeetingList;

    private List <Meeting> currentFilterList;
    int firstConnection =0;

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
        currentFilterList = new ArrayList<>();
        Log.d(TAG_MEETING_FRAGMENT, "onCreate: Meeting Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        Log.d(TAG_MEETING_FRAGMENT, "onCreateView: Meeting Fragment");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_meeting_recyclerview);

        if (firstConnection == 0) {
            listPublisher(mApiService.getMeetings());
            firstConnection++;
        }else {
            listPublisher(currentFilterList);
        }
        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(AddOrMeetingDetailsFragment.newInstance(0));
            }
        });
        return view;
    }

    @Subscribe
    public void onEventDelete(DeleteMeetingEvent deleteMeeting) {
        mApiService.removeMeeting(deleteMeeting.meeting);
        mMeetingList.remove(deleteMeeting.meeting);
        updateAndNotifyAdapter();
    }

    private void updateAndNotifyAdapter() {
        currentFilterList.clear();
        currentFilterList.addAll(mMeetingList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventAdd(AddMeetingEvent addMeeting) {
        mApiService.addMeeting(addMeeting.meeting);
        mMeetingList.add(addMeeting.meeting);
        updateAndNotifyAdapter();
    }

    @Subscribe
    public void updateMeetingEvent(UpdateMeetingEvent updateMeeting) {
        mApiService.updateMeeting(updateMeeting.meeting);
        updateAndNotifyAdapter();
    }

    public void filterByRoom(Room room) {
        Log.d("TAG", "filterByRoom: ");
        List<Meeting> meetingbyroom = new ArrayList<>();
        meetingbyroom = mApiService.getMeetingByRoom(room);
        listPublisher(meetingbyroom);

    }

    public void filterByDate(String str) {
        List<Meeting> meetingbydate = new ArrayList<>();
        meetingbydate = mApiService.getMeetingByDate(str);
        listPublisher(meetingbydate);
    }

    public void allMeeting() {
        listPublisher(mApiService.getMeetings());
    }

    public void listPublisher(List<Meeting> meetings) {
        if (mMeetingList == null) {
            mMeetingList = new ArrayList<>();
        }
        mMeetingList.clear();
        mMeetingList.addAll(meetings);
        if (adapter == null) {
            adapter = new MyMeetingRecyclerViewAdapter(mMeetingList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(adapter);
        } else {
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG_MEETING_FRAGMENT, "onStart: Meeting Fragment");
        //initList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG_MEETING_FRAGMENT, "onResume: Meeting Fragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG_MEETING_FRAGMENT, "onPause: Meeting Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG_MEETING_FRAGMENT, "onStop: Meeting Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG_MEETING_FRAGMENT, "onDestroy: Meeting Fragment");
    }
}
