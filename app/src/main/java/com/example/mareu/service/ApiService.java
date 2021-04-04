package com.example.mareu.service;

import com.example.mareu.model.Meeting;

public interface ApiService {

    void addMeeting(Meeting meeting);

    void removeMeeting(Meeting meeting);
}
