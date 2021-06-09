package com.example.mareu.event;

import com.example.mareu.model.Meeting;

public class UpdateMeetingEvent {

    public Meeting meeting;

    public UpdateMeetingEvent(Meeting meeting){
        this.meeting = meeting;
    }

}
