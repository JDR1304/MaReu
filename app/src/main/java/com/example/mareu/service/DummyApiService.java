package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public class DummyApiService implements ApiService {

    private List<Meeting> meetings = DummyGenerator.generateRooms();

    @Override
    public void addMeeting(Meeting meeting) {

    }

    @Override
    public void removeMeeting(Meeting meeting) {

    }
}
