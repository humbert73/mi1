package com.example.moreauhu.testjeu.entity;

import android.graphics.Color;

/**
 * Created by moreauhu on 07/10/16.
 */

public enum State {
    PREPARE (Color.rgb(21, 88, 232)  , "Prepare", "@drawable/ic_rest"),
    WORK    (Color.rgb(255, 100, 100), "Work", "@drawable/ic_work"),
    REST    (Color.rgb(0, 195, 100)  , "Rest", "@drawable/ic_rest");

    private int color;
    private String title;
    private String icone;

    State(int color, String title, String icone){
        this.color = color;
        this.title = title;
        this.icone = icone;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}