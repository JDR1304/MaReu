package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.List;

public class DummyApiService implements ApiService {

    private List<Meeting> meetings = DummyGenerator.generateMeetings();
    private List<Room> rooms = DummyGenerator.generateRooms();

    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {

    }

    @Override
    public void removeMeeting(Meeting meeting) {

    }


    @Override
    public List<Room> getRoom() {
        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if (id == rooms.get(i).getId()) {
                return rooms.get(i);
            }
        }
        return null;
    }
}
