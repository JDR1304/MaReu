package com.example.mareu.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.AddMeetingEvent;
import com.example.mareu.event.UpdateMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AddOrMeetingDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static String TAG_AddOrMeetingDetailsFragment = AddOrMeetingDetailsFragment.class.getSimpleName();
    private static final String MEETING_ID = "MEETING_ID";
    private static Meeting meeting;

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    private Spinner roomsSpinner;
    private Button validateBtn;
    
    private EditText editTextDateAndTime;
    private EditText editTextReservationName;
    private EditText editTextParticipants;
    private EditText editTextSubject;
    private Room selectedRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Permet d'avoir la fenetre dans laquelle on écrit tjs visible
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (getArguments() != null && getArguments().containsKey(MEETING_ID) && getArguments().getLong(MEETING_ID) > 0) {
            meeting = DI.getApiService().getMeetingById(getArguments().getLong(MEETING_ID));

        } else {
            meeting = new Meeting(getArguments().getLong(MEETING_ID));

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
        View view = inflater.inflate(R.layout.fragment_add_or_meeting_details, container, false);

        validateBtn = view.findViewById(R.id.validate_button);

        //Récupération de ma liste de rooms
        List<Room> rooms = DI.getApiService().getRooms();
        //Mon adaptateur de rooms
        RoomArrayAdapter roomAdapter = new RoomArrayAdapter(getContext(), rooms);
        // Initialisation des Edits texts
        initEditText(view, roomAdapter);
        //Mon listener sur mon Edit text date & time
        editTextDateListener();
        // Récupération de ma room selectionnée
        roomsSpinnerItemSelectedListener(rooms);
        // Click sur le bouton validation
        validateButtonClickListener();
        //Affichage d'un meeting existant
        displayMeetingFromRecyclerView(rooms);
        //Vérifie si les champs sont correctement remplis
        validateBtn.setEnabled(isABooleanValidateBtn());
        // Mon Listener sur mes edits texts
        textChangedListenerOnEditText();

        return view;
    }

    private void displayMeetingFromRecyclerView(List<Room> rooms) {
        if (meeting != null && meeting.getId() > 0) {
            editTextDateAndTime.setText(meeting.setTime(meeting.getTimeStamp()));
            editTextReservationName.setText(meeting.getName());
            editTextParticipants.setText(meeting.getStringMails());
            editTextSubject.setText(meeting.getTopic());

            //Display the selected room
            int indexRoom = 0;
            for (int i = 0; i < rooms.size(); i++) {
                if (meeting.getRoom().getId() == (rooms.get(i).getId())) {
                    indexRoom = i;
                }
            }
            roomsSpinner.setSelection(indexRoom);
        }
    }

    private boolean isABooleanValidateBtn() {
        return !(editTextDateAndTime.getText().toString()).isEmpty() && !(editTextSubject.getText().toString()).isEmpty() &&
                (!(editTextReservationName.getText().toString()).isEmpty() && (editTextReservationName.getText().toString().length() > 1)) &&
                (!(editTextParticipants.getText().toString()).isEmpty()) && isEmailValid(editTextParticipants.getText().toString());
    }

    private void validateButtonClickListener() {
        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.setRoom(selectedRoom);
                long timestamp = getTimestamp();
                meeting.setTimeStamp(timestamp);
                meeting.setName(editTextReservationName.getText().toString());
                meeting.setParticipantEmails(Collections.singletonList(editTextParticipants.getText().toString()));
                meeting.setTopic(editTextSubject.getText().toString());

                if (meeting.getId() == 0) {
                    meeting.setId(System.currentTimeMillis());
                    EventBus.getDefault().post(new AddMeetingEvent(meeting));
                } else {
                    EventBus.getDefault().post(new UpdateMeetingEvent(meeting));
                }

                getActivity().onBackPressed();
            }
        });
    }

    private void roomsSpinnerItemSelectedListener(List<Room> rooms) {
        roomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoom = rooms.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void textChangedListenerOnEditText() {
        editTextDateAndTime.addTextChangedListener(fieldIsEmpty);
        editTextReservationName.addTextChangedListener(fieldIsEmpty);
        editTextParticipants.addTextChangedListener(fieldIsEmpty);
        editTextSubject.addTextChangedListener(fieldIsEmpty);
    }

    private void initEditText(View view, RoomArrayAdapter roomAdapter) {
        roomsSpinner = (Spinner) view.findViewById(R.id.spinner);
        roomsSpinner.setAdapter(roomAdapter);
        editTextDateAndTime = (EditText) view.findViewById(R.id.edit_text_date);
        editTextReservationName = (EditText) view.findViewById(R.id.edit_text_reservation_name);
        editTextParticipants = (EditText) view.findViewById(R.id.edit_text_participantes);
        editTextSubject = (EditText) view.findViewById(R.id.edit_text_topic);
    }

    private long getTimestamp() {
        // Récupération de la date sur l'edit text au format String
        String str = editTextDateAndTime.getText().toString();
        //Transformation String to long
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
        Date date = null;
        try {
            date = (Date) formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    private TextWatcher fieldIsEmpty = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isABooleanValidateBtn()) {
                validateBtn.setEnabled(true);
            } else {
                validateBtn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void editTextDateListener() {
        editTextDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AddOrMeetingDetailsFragment.this::onDateSet, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month + 1;
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

        if (myMinute < 10) {
            editTextDateAndTime.setText(myYear + "/" + myMonth + "/" + myday + "   " + myHour + ":0" + myMinute);
        } else {
            editTextDateAndTime.setText(myYear + "/" + myMonth + "/" + myday + "   " + myHour + ":" + myMinute);
        }
    }

    boolean isEmailValid(String str) {
        // le + pour au moins un espace ou plus
        String[] mails = str.split(" +");
        int j = 0;
        for (int i = 0; i < mails.length; i++) {
            if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(mails[i]).matches())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG_AddOrMeetingDetailsFragment, "onAttach: ADD Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onStart: ADD Fragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onResume: ADD Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onPause: ADD Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onStop: ADD Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onDestroy: ADD Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG_AddOrMeetingDetailsFragment, "onDetach: ADD Fragment");
    }
}