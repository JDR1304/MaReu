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
        meetings.add(meeting);
    }

    @Override
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public Meeting getMeetingById(long id) {
        for (int i = 0; i<meetings.size(); i++){
            if (meetings.get(i).getId()== id){
                return meetings.get(i);
            }
        }
        return null;
    }


    @Override
    public List<Room> getRooms() {
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

    @Override
    public void updateMeeting(Meeting meeting) {
        Meeting originalMeeting = getMeetingById(meeting.getId());
        originalMeeting = meeting;
    }
}
