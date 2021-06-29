package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.List;

public interface ApiService {


    List<Meeting> getMeeting();

    void addMeeting(Meeting meeting);

    void removeMeeting(Meeting meeting);

    Meeting getMeetingById(long id);

    void updateMeeting(Meeting meeting);

    //Get List of room

    List <Room> getRooms();

    Room getRoomById(int id);

    List <Meeting> getMeetingByRoom(Room room);

    List <Meeting> getMeetingByDate (String str);

}
