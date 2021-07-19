package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyApiService implements ApiService {

    private List<Meeting> meetings = DummyGenerator.generateMeetings();
    private List<Room> rooms = DummyGenerator.generateRooms();

    @Override
    public List<Meeting> getMeetings() {
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
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getId() == id) {
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

    @Override
    public List<Meeting> getMeetingByRoom(Room room) {
        List<Meeting> liste = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getRoom().getName() == room.getName()) {
                liste.add(meetings.get(i));
            }
        }
        return liste;
    }

    @Override
    public List<Meeting> getMeetingByDate(String str) {
        List<Meeting> liste = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = formatter.format(new Date(Long.parseLong(Long.toString(meetings.get(i).getTimeStamp()))));
            if (dateString.equals(str)) {
                liste.add(meetings.get(i));
            }
        }
        return liste;
    }


}
