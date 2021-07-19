package com.example.mareu.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Meeting {

    /**
     * Identifier
     */
    private long id;

    /**
     * Meeting room
     */
    private Room meetingRoom;

    /**
     * reservation Time
     */
    private long time;

    /**
     * name who reserved the room
     */
    private String name;

    /**
     * email address
     */
    private List<String> participantEmails;

    /**
     * topic
     */
    private String topic;

    /**
     * Key for Room
     **/
    public static final String MEETING_KEY = "MEETING_KEY";

    public Meeting(long id) {
    }

    /**
     * Constructor
     */
    public Meeting(long id, Room meetingRoom, long time, String name, List<String> participantEmails, String topic) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.time = time;
        this.name = name;
        this.participantEmails = participantEmails;
        this.topic = topic;
    }

    /**
     * Getters and setters
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return meetingRoom;
    }

    public void setRoom(Room meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public long getTimeStamp() {
        return time;
    }

    public void setTimeStamp(Long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParticipantEmails() {
        return participantEmails;
    }

    public void setParticipantEmails(List<String> participantEmails) {
        this.participantEmails = participantEmails;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStringMails() {
        String emails = "";
        String space = "  ";
        for (int i = 0; i < participantEmails.size(); i++) {
            emails = participantEmails.get(i) + space + emails;
        }
        return emails;
    }

    public String setTime(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
        String dateString = formatter.format(new Date(Long.parseLong(Long.toString(timeStamp))));
        return dateString;
    }
}


