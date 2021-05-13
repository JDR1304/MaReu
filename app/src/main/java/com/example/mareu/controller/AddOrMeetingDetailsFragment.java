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
    private TextView topic;
    private Button validateBtn;
    private Button btnTimeDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null && getArguments().containsKey(MEETING_ID)) {
            meeting = DI.getApiService().getMeetingById(getArguments().getLong(MEETING_ID));
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
        topic = view.findViewById(R.id.topic);
        validateBtn = view.findViewById(R.id.validate_button);
        btnTimeDate =view.findViewById(R.id.btnTimeDate);

        if (meeting.getId()>=0) {
            // ((EditText)view.findViewById(R.id.edit_text_date)).setText((int)meeting.getTimeStamp());
            ((EditText) view.findViewById(R.id.edit_text_topic)).setText(meeting.getTopic());
            ((EditText) view.findViewById(R.id.edit_text_reservation_name)).setText(meeting.getName());
            //((EditText) view.findViewById(R.id.edit_text_participantes)).setText((List)meeting.getParticipantEmails());

           ///Getting the instance of Spinner and applying OnItemSelectedListener on it
            roomsSpinner = (Spinner) view.findViewById(R.id.spinner);
            RoomArrayAdapter roomAdapter = new RoomArrayAdapter(getContext(), DI.getApiService().getRooms());
            roomsSpinner.setAdapter(roomAdapter);



        }else{
            ((EditText) view.findViewById(R.id.edit_text_topic)).setText(meeting.getTopic());
            ((EditText) view.findViewById(R.id.edit_text_reservation_name)).setText(meeting.getName());
            btnTimeDate.setOnClickListener(new View.OnClickListener() {
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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        meetingTime.setText("Year: " + myYear + "\n" +
                "Month: " + myMonth + "\n" +
                "Day: " + myday + "\n" +
                "Hour: " + myHour + "\n" +
                "Minute: " + myMinute);
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
}