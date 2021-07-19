package com.example.mareu.event;

import com.example.mareu.model.Meeting;

public class AddMeetingEvent {

    /**
     * Meeting to add
     */
    public Meeting meeting;

    /**
     * Constructor.
     *
     * @param meeting
     */
    public AddMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
