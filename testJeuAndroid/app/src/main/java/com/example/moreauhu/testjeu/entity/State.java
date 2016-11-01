package com.example.moreauhu.testjeu.entity;

import android.graphics.Color;

/**
 * Created by moreauhu on 07/10/16.
 */

public enum State {
    PREPARE (Color.rgb(21 , 147, 196), "Prepare", "P", "@drawable/ic_rest"),
    WORK    (Color.rgb(189,  38,  38), "Work"   , "W", "@drawable/ic_work"),
    REST    (Color.rgb(103, 175,  69), "Rest"   , "R", "@drawable/ic_rest");

    private int color;
    private String title;
    private String shortName;
    private String icone;

    State(int color, String title, String shortName, String icone){
        this.color     = color;
        this.title     = title;
        this.shortName = shortName;
        this.icone     = icone;
    }

    public int getColor() {
        return this.color;
    }

    public String getTitle() {
        return this.title;
    }

    public String getShortName() {
        return this.shortName;
    }
}