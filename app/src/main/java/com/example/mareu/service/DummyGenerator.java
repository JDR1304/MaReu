package com.example.mareu.service;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyGenerator {

    //Dummy Meetings
    public static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1,new Room (1, "Réunion A", 0xFFBB86FC) , 1618567200000L, "Jerome",  Arrays.asList("jerome@lamzone.com","sophie@lamzone.com"), "Projet suez"),
            new Meeting(2,new Room (2, "Réunion B", 0xFF6200EE), 1623157200000L, "Bastien", Arrays.asList("bastien@lamzone.com","fabien@lamzone.com"), "recyclage dechet"),
            new Meeting(3,new Room (3, "Réunion C", 0xFF3700B3), 1618563600000L, "Paul", Arrays.asList("paul@lamzone.com","rachel@lamzone.com"), "Nouvelles Technologies"),
            new Meeting(4,new Room (4, "Réunion D", 0xFF03DAC5), 1624341600000L, "Vincent", Arrays.asList("vincent@lamzone.com","jerome@lamzone.com","rachel.d@gmail.com"), "Projet Turf"),
            new Meeting(5, new Room (5, "Réunion E", 0xFF018786), 1618592400000L, "Fabien", Arrays.asList("fabien@lamzone.com","jerome@lamzone.com"), "Appel d'offre "),
            new Meeting(6,new Room (6, "Réunion F", 0xFF000000), 1622212200000L, "Rachel", Arrays.asList("rachel@lamzone.com","philippe@lamzone.com"), "Organisation Team building"),
            new Meeting(7,new Room (7, "Réunion G", 0xFFE91EDF), 1618581600000L, "Sophie", Arrays.asList("sophie@lamzone.com","fabien@lamzone.com"), "Projet suez"),
            new Meeting(8, new Room (8, "Réunion H", 0xFFFFEB3B), 1620822600000L, "Anne-Denise", Arrays.asList("anne@lamzone.com","victor@lamzone.com"), "Appel d'offre "),
            new Meeting(9,new Room (9, "Réunion I", 0xFFFF5722), 1618579800000L, "Bob", Arrays.asList("bob@lamzone.com","philippe@lamzone.com"), "Organisation Team building"),
            new Meeting(10,new Room (10, "Réunion J", 0xFF6AC34A), 1624521600000L, "Victor", Arrays.asList("victor@lamzone.com","bob@lamzone.com"), "Nouveaux clients")
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(dummyMeetings);
    }

    // Dummy Rooms
    public static List <Room> dummyRooms = Arrays.asList(
            new Room (1, "Réunion A", 0xFFBB86FC),
            new Room (2, "Réunion B", 0xFF6200EE),
            new Room (3, "Réunion C", 0xFF3700B3),
            new Room (4, "Réunion D", 0xFF03DAC5),
            new Room (5, "Réunion E", 0xFF018786),
            new Room (6, "Réunion F", 0xFF000000),
            new Room (7, "Réunion G", 0xFFE91EDF),
            new Room (8, "Réunion H", 0xFFFFEB3B),
            new Room (9, "Réunion I", 0xFFFF5722),
            new Room (10, "Réunion J", 0xFF6AC34A)
    );
    static List<Room> generateRooms() {
        return new ArrayList<>(dummyRooms);
    }
}


