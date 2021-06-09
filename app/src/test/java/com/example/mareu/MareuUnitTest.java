package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.service.ApiService;
import com.example.mareu.service.DummyGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MareuUnitTest {

    private ApiService service;

    @Before
    public void setup() {service = DI.getNewInstanceApiService();}

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeeting();
        List<Meeting> expectedMeetings = DummyGenerator.dummyMeetings;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));

    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.removeMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));

    }

    @Test
    public void getMeetingByIdWithSuccess() {
        List<Meeting> meetings = service.getMeeting();
        Meeting meeting = meetings.get(1);
        long id = meeting.getId();
        assertEquals(meeting, service.getMeetingById(id));

    }

    @Test
    public void createMeetingWithSuccess() {
        Meeting meetingCreated = new Meeting(100,new Room(10, "Réunion J", 0xFF6AC34A),
                1618596000000L, "Alexandre", Arrays.asList("alexandre@lamzone.com","sebastien@lamzone.com"), "Prospection");
        service.addMeeting(meetingCreated);
        assertTrue(service.getMeeting().contains(meetingCreated));

    }

    @Test
    public void getRoomByIdWithSuccess() {
        List<Room> rooms = service.getRooms();
        Room room = rooms.get(1);
        int id = room.getId();
        assertEquals(room, service.getRoomById(id));

    }

}