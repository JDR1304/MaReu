package com.example.mareu.model;


public class Room {

    private int id;
    private String name;
    private int colorDrawable;
    /**
     * Key for Room
     **/
    public static final String ROOM_KEY = "ROOM_KEY";

    public Room(int id, String name, int colorDrawable) {
        this.id = id;
        this.name = name;
        this.colorDrawable = colorDrawable;
    }


    /**
     * getters and setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(int colorDrawable) {
        this.colorDrawable = colorDrawable;
    }


}
