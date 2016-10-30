package com.example.moreauhu.testjeu.entity;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by moreauhu on 04/10/16.
 */

public class Programme extends SugarRecord implements Serializable {
    String name;
    ArrayList<Tabata> tabatas;

    // Default constructor is necessary for SugarRecord
    public Programme() {
    }

    public Programme(String name, ArrayList<Tabata> tabatas) {
        this.name         = name;
        this.tabatas  = tabatas;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tabata> getTabatas() {
        return tabatas;
    }
}
