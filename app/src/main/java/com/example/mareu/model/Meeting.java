package com.example.mareu.model;

import java.util.List;

public class Meeting {

    /** Identifier */
    private long id;

    /** Room name */
    private int roomId;

    /** reservation Time */
    private long time;

    /** name who reserved the room */
    private String name;

    /** email address */
    private List<String> email;

    /** topic */
    private String topic;

    /** Constructor */
    public Meeting(long id, int roomId, long time, String name, List<String> email, String topic) {
        this.id = id;
        this.roomId = roomId;
        this.time = time;
        this.name = name;
        this.email = email;
        this.topic = topic;
    }

    /** Getters and setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}
