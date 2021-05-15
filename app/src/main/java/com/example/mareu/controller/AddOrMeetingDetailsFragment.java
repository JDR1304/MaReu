package com.example.mareu.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.service.ApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;


public class AddOrMeetingDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String MEETING_ID = "MEETING_ID";
    private static Meeting meeting;

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    private Spinner roomsSpinner;

    private TextView meetingRoom;
    private TextView meetingTime;
    private TextView reservationName;
    private TextView participants;
    private TextView topic;
    private Button validateBtn;

    //private EditText editTextRoom;
    private EditText editTextDateAndTime;
    private EditText editTextReservationName;
    private EditText editTextParticipants;
    private EditText editTextSubject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null && getArguments().containsKey(MEETING_ID) && getArguments().getLong(MEETING_ID)>0) {
            meeting = DI.getApiService().getMeetingById(getArguments().getLong(MEETING_ID));

            }
        else {
            meeting = new Meeting (getArguments().getLong(MEETING_ID));

        }
    }

    public static AddOrMeetingDetailsFragment newInstance(long id) {
        AddOrMeetingDetailsFragment fragment = new AddOrMeetingDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(MEETING_ID, id);
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_or_meeting_details, container, false);

        meetingRoom = view.findViewById(R.id.meeting_room);
        meetingTime = view.findViewById(R.id.meeting_time);
        reservationName = view.findViewById(R.id.reservationName);
        participants = view.findViewById(R.id.participants);
        topic = view.findViewById(R.id.topic);
        validateBtn = view.findViewById(R.id.validate_button);

        RoomArrayAdapter roomAdapter = new RoomArrayAdapter(getContext(), DI.getApiService().getRooms());


        if (meeting.getId()>0) {
            ((EditText) view.findViewById(R.id.edit_text_date)).setText(meeting.setTime(meeting.getTimeStamp()));
            ((EditText) view.findViewById(R.id.edit_text_topic)).setText(meeting.getTopic());
            ((EditText) view.findViewById(R.id.edit_text_reservation_name)).setText(meeting.getName());
            ((EditText) view.findViewById(R.id.edit_text_participantes)).setText(meeting.getStringMails());

           ///Getting the instance of Spinner and applying OnItemSelectedListener on it
            roomsSpinner = (Spinner) view.findViewById(R.id.spinner);
            roomsSpinner.setAdapter(roomAdapter);


        }
        else{
            roomsSpinner = (Spinner) view.findViewById(R.id.spinner);
            roomsSpinner.setAdapter(roomAdapter);
            editTextDateAndTime = (EditText) view.findViewById(R.id.edit_text_date); ;
            editTextReservationName = (EditText) view.findViewById(R.id.edit_text_reservation_name);
            editTextParticipants = (EditText) view.findViewById(R.id.edit_text_participantes);
            editTextSubject = (EditText) view.findViewById(R.id.edit_text_topic);
            editTextReservationName.getText();
            editTextParticipants.getText();
            editTextSubject.getText();

            editTextDateAndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AddOrMeetingDetailsFragment.this::onDateSet,year, month,day);
                    datePickerDialog.show();
                }
            });
        }
                    
        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(MeetingFragment.newInstance());
            }
        });
        return view;

        }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myday = day;
            myMonth = month;
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR);
            minute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AddOrMeetingDetailsFragment.this::onTimeSet, hour, minute, DateFormat.is24HourFormat(getContext()));
            timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;

        if (myMinute<10){
            editTextDateAndTime.setText(myYear + "/" + myMonth + "/" + myday + "   " + myHour + ":0" + myMinute);
        }
        else {
            editTextDateAndTime.setText(myYear + "/" + myMonth + "/" + myday + "   " + myHour + ":" + myMinute);
        }
    }

}