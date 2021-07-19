package com.example.mareu.controller;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mareu.model.Room;

import java.util.List;

public class RoomArrayAdapter extends ArrayAdapter<Room> {

    private List<Room> items;

    public RoomArrayAdapter(Context context, List<Room> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.items = items;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);

        if (v == null) {
            v = new TextView(getContext());
        }
        v.setText(items.get(position).getName());
        return v;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Room getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView lbl = (TextView) view.findViewById(android.R.id.text1);
        lbl.setText(items.get(position).getName());
        return view;
    }
}
