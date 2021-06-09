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
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

    public static String TAG = AddOrMeetingDetailsFragment.class.getSimpleName();
    private static final String MEETING_ID = "MEETING_ID";
    private static Meeting meeting;

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    private Spinner roomsSpinner;
    private Button validateBtn;

    private EditText editTextRoom;
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
        // Mon Listener sur mes edits texts
        textChangedListenerOnEditText();
        //Mon listener sur mon Edit text date & time
        editTextDateListener();
        // Récupération de ma room selectionnée
        roomsSpinnerItemSelectedListener(rooms);

        // Click sur le bouton validation
        validateButtonClickListener();


        if (meeting != null && meeting.getId() > 0) {
           /* ((EditText) view.findViewById(R.id.edit_text_date)).setText(meeting.setTime(meeting.getTimeStamp()));
            ((EditText) view.findViewById(R.id.edit_text_topic)).setText(meeting.getTopic());
            ((EditText) view.findViewById(R.id.edit_text_reservation_name)).setText(meeting.getName());
            ((EditText) view.findViewById(R.id.edit_text_participantes)).setText(meeting.getStringMails());

            ///Getting the instance of Spinner and applying OnItemSelectedListener on it
            roomsSpinner = (Spinner) view.findViewById(R.id.spinner);

            roomsSpinner.setAdapter(roomAdapter);*/
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

        } else {
            //validateBtn.setEnabled(false);
        }
        validateBtn.setEnabled(false);
        return view;
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
                }
                else {
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
            /*validateBtn.setEnabled(!(editTextDateAndTime.getText().toString()).isEmpty() &&
                    !(editTextReservationName.getText().toString()).isEmpty() &&
                    !(editTextParticipants.getText().toString()).isEmpty() && (Patterns.EMAIL_ADDRESS.matcher(editTextParticipants.getText().toString()).matches()) &&
                    !(editTextSubject.getText().toString()).isEmpty());*/
            if (!(editTextDateAndTime.getText().toString()).isEmpty() && !(editTextSubject.getText().toString()).isEmpty() &&
                    (!(editTextReservationName.getText().toString()).isEmpty() && (editTextReservationName.getText().toString().length() > 1)) &&
                    (!(editTextParticipants.getText().toString()).isEmpty() && (Patterns.EMAIL_ADDRESS.matcher(editTextParticipants.getText().toString()).matches()))) {
                validateBtn.setEnabled(true);
            } else {
                if (!(editTextReservationName.getText().toString()).isEmpty() && (editTextReservationName.getText().toString().length() < 1)) {
                    Toast.makeText(getContext(), "Veuillez indiquer le nom de qui à reservé (minimum 2 caracteres)", Toast.LENGTH_LONG).show();

                    if (!(Patterns.EMAIL_ADDRESS.matcher(editTextParticipants.getText().toString()).matches())) {
                        Toast.makeText(getContext(), "Veuillez entrer un adresse mail valide", Toast.LENGTH_LONG).show();
                    }
                }
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ADD Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ADD Fragment");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ADD Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ADD Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ADD Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ADD Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ADD Fragment");
    }
}