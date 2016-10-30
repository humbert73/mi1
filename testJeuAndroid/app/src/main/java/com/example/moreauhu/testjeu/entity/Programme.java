package com.example.moreauhu.testjeu.entity;

import com.example.moreauhu.testjeu.entity.Tabata.Tabata;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by moreauhu on 04/10/16.
 */

public class Programme implements Serializable {
    String name;
    ArrayList<Tabata> tabatas;

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
