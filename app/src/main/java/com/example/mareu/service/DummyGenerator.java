package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyGenerator {

    //Dummy Meetings
    public static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1,1, 14, "Jerome",  Arrays.asList("jerome@lamzone.com","sophie@lamzone.com"), "Projet suez"),
            new Meeting(2,2, 9, "Bastien", Arrays.asList("bastien@lamzone.com","fabien@lamzone.com"), "recyclage dechet"),
            new Meeting(3,3, 15, "Paul", Arrays.asList("paul@lamzone.com","rachel@lamzone.com"), "Nouvelles Technologies"),
            new Meeting(4,4, 16, "Vincent", Arrays.asList("vincent@lamzone.com","jerome@lamzone.com","rachel.d@gmail.com"), "Projet Turf"),
            new Meeting(5,5, 8, "Fabien", Arrays.asList("fabien@lamzone.com","jerome@lamzone.com"), "Appel d'offre "),
            new Meeting(6,6, 10, "Rachel", Arrays.asList("rachel@lamzone.com","philippe@lamzone.com"), "Organisation Team building"),
            new Meeting(7,7, 17, "Sophie", Arrays.asList("sophie@lamzone.com","fabien@lamzone.com"), "Projet suez")
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(dummyMeetings);
    }

    // Dummy Rooms
    public static List <Room> dummyRooms = Arrays.asList(
            new Room (1, "Réunion A "),
            new Room (2, "Réunion B "),
            new Room (3, "Réunion C "),
            new Room (4, "Réunion D "),
            new Room (5, "Réunion E "),
            new Room (6, "Réunion F "),
            new Room (7, "Réunion G "),
            new Room (8, "Réunion H "),
            new Room (9, "Réunion I "),
            new Room (10, "Réunion J ")
    );
    static List<Room> generateRooms() {
        return new ArrayList<>(dummyRooms);
    }
}


