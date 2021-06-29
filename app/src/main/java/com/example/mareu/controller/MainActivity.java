package com.example.mareu.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Room;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mareu.controller.MeetingFragment.TAG_MEETING_FRAGMENT;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeetingFragment meetingFragment = MeetingFragment.newInstance();

        addFragment(meetingFragment);

    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_layout_main, fragment, MeetingFragment.TAG_MEETING_FRAGMENT);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(AddOrMeetingDetailsFragment addOrMeetingDetailsFragment) {

        Toast.makeText(this, "Display add or meeting details fragment", Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout_main, addOrMeetingDetailsFragment, AddOrMeetingDetailsFragment.TAG_AddOrMeetingDetailsFragment);
        ft.addToBackStack(AddOrMeetingDetailsFragment.TAG_AddOrMeetingDetailsFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MeetingFragment meetingF = (MeetingFragment) getSupportFragmentManager().findFragmentByTag(TAG_MEETING_FRAGMENT);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort_by_meeting_room:

                List<Room> rooms = new ArrayList<>();
                rooms.addAll(DI.getApiService().getRooms());
                rooms.add(0, new Room(-1, getString(R.string.select_a_room), R.color.black));
                Spinner spinner = new Spinner(this);
                RoomArrayAdapter roomAdapter = new RoomArrayAdapter(this, rooms);
                spinner.setAdapter(roomAdapter);
                spinner.setSelection(0, false);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.select_a_room))
                        .setView(spinner)
                        .create();
                dialog.show();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            Room meetingRoom = rooms.get(position);
                            meetingF.filterByRoom(meetingRoom);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                return true;

            case R.id.sort_by_date:
                Toast.makeText(this, "tri par date", Toast.LENGTH_SHORT).show();
                int myYear, myday, myMonth;
                Calendar calendar = Calendar.getInstance();
                myYear = calendar.get(Calendar.YEAR);
                myMonth = calendar.get(Calendar.MONTH);
                myday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, MainActivity.this::onDateSet, myYear, myMonth, myday);
                datePickerDialog.show();

                return true;
            case R.id.all_meetings:

                meetingF.allMeeting();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month + 1, dayOfMonth);
        String zero = "0";
        String strMonth = null;
        String strDayOfMonth = null;
        if (month < 10) {
            strMonth = zero + String.valueOf(month + 1);
        } else {
            strMonth = String.valueOf(month + 1);
        }
        if (dayOfMonth < 10) {
            strDayOfMonth = zero + String.valueOf(dayOfMonth);
        } else {
            strDayOfMonth = String.valueOf(dayOfMonth);
        }

        String str = (String.valueOf(year) + "/" + strMonth + "/" + strDayOfMonth);
        Log.d("valeur de str", str);

        MeetingFragment meetingF = (MeetingFragment) getSupportFragmentManager().findFragmentByTag(TAG_MEETING_FRAGMENT);
        meetingF.filterByDate(str);
    }


}