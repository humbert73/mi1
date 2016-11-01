package com.example.moreauhu.testjeu.entity.Tabata;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by moreauhu on 04/10/16.
 */

public class Tabata extends SugarRecord implements Parcelable {
    private String name;
    private int prepareTime;
    private int workTime;
    private int restTime;
    private int cyclesNumber;

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

    public static final Parcelable.Creator<Tabata> CREATOR
            = new Parcelable.Creator<Tabata>() {
        public Tabata createFromParcel(Parcel in) {
            return new Tabata(
                    in.readString(),
                    in.readInt(),
                    in.readInt(),
                    in.readInt(),
                    in.readInt()
            );
        }

        public Tabata[] newArray(int size) {
            return new Tabata[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(prepareTime);
        out.writeInt(workTime);
        out.writeInt(restTime);
        out.writeInt(cyclesNumber);
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

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public void setCyclesNumber(int cyclesNumber) {
        this.cyclesNumber = cyclesNumber;
    }
}
