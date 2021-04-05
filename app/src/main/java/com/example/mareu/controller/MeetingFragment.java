package com.example.mareu.controller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.ApiService;

import java.util.List;


public class MeetingFragment extends Fragment {

    private ApiService mApiService;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter adapter;

    public MeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getApiService();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        //
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_meeting_recyclerview);
        adapter = new MyMeetingRecyclerViewAdapter(mApiService.getMeeting());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}