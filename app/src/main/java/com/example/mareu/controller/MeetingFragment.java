package com.example.mareu.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;


public class MeetingFragment extends Fragment {

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
        /*
        Bundle bundle = new Bundle();
        bundle.putBoolean(FAVORITE_KEY, isFavorite);
        fragment.setArguments(bundle);
        */
        return fragment;
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

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(AddOrMeetingDetailsFragment.newInstance(-1));
            }
        });
        return view;
    }

}
