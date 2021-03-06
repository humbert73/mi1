package com.example.moreauhu.testjeu.entity.Tabata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moreauhu on 30/10/16.
 */

public class TabataFactory {

    public Tabata addOrReplaceTabata(String name, int prepareTime, int workTime, int restTime, int cyclesNumber) {
        Tabata tabata = this.findTabataByName(name);
        if (tabata != null) {
            this.updateTabata(tabata, prepareTime, workTime, restTime, cyclesNumber);
        } else {
            tabata = new Tabata(name, prepareTime, workTime, restTime, cyclesNumber);
        }
        tabata.save();

        return tabata;
    }

    public void deleteTabatasByNames(ArrayList<String> tabatasNames)
    {
        for (String name : tabatasNames) {
            Tabata tabata = this.findTabataByName(name);
            tabata.delete();
        }
    }

    public Tabata searchTabataByName(String name) {
        ArrayList<Tabata> tabatas = new ArrayList<>(Tabata.listAll(Tabata.class));
        for (Tabata tabata : tabatas) {
            if (tabata.getName().equals(name)) {
                return tabata;
            }
        }

        return null;
    }

    public ArrayList<Tabata> getTabatas() {
        return new ArrayList<>(Tabata.listAll(Tabata.class));
    }

    private void updateTabata(Tabata tabata, int prepareTime, int workTime, int restTime, int cyclesNumber) {
        tabata.setPrepareTime(prepareTime);
        tabata.setWorkTime(workTime);
        tabata.setRestTime(restTime);
        tabata.setCyclesNumber(cyclesNumber);
    }

    public Tabata findTabataByName(String name) {
        List<Tabata> tabatas = Tabata.listAll(Tabata.class);
        for (Tabata tabata : tabatas) {
            if (tabata.getName().compareTo(name) == 0) { // compareTo() return 0 if equals

                return tabata;
            }
        }

        return null;
    }
}
