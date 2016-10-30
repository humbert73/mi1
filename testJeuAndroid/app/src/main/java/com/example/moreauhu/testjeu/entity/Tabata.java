package com.example.moreauhu.testjeu.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by moreauhu on 04/10/16.
 */

public class Tabata extends SugarRecord implements Serializable {
    String name;
    int prepareTime;
    int workTime;
    int restTime;
    int cyclesNumber;

    // Default constructor is necessary for SugarRecord
    public Tabata() {
    }

    public Tabata(String name, int prepareTime, int workTime, int restTime, int cyclesNumber) {
        this.name         = name;
        this.prepareTime  = prepareTime;
        this.workTime     = workTime;
        this.restTime     = restTime;
        this.cyclesNumber = cyclesNumber;
    }

    public String getName() {
        return name;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public int getCyclesNumber() {
        return cyclesNumber;
    }
}
