package com.example.moreauhu.testjeu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.moreauhu.testjeu.entity.State;
import com.example.moreauhu.testjeu.entity.Tabata.Tabata;
import com.example.moreauhu.testjeu.entity.Tabata.TabataFactory;

import java.util.ArrayList;

public class tabataManagerActivity extends AppCompatActivity {

    private TabataFactory tabataFactory = new TabataFactory();
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Button> buttonsSelected = new ArrayList<>();
    private ArrayList<Tabata> tabatas;
    private ArrayList<Tabata> tabatasToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.init();
    }

    private void init() {
        this.initFabIcone();
        this.initTabatas();
    }

    private void initFabIcone() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tabataManagerActivity.this, addTabataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTabatas() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.tabatas);
        this.tabatas = new ArrayList<>(Tabata.listAll(Tabata.class));

        for (Tabata tabata : this.tabatas) {
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onClickOnTabata(v);
                }
            });
            button.setText(tabata.getName());
            layout.addView(button);
            this.buttons.add(button);
        }
    }

    private void updateDisplay() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.tabatas);
        layout.removeAllViewsInLayout();
        init();
    }

    public void onClickOnTabata(View v) {
        Button button = (Button) v;
        if (! buttonsSelected.contains(button)) {
            buttonsSelected.add(button);
            button.setBackgroundColor(State.WORK.getColor());
        } else {
            buttonsSelected.remove(button);
            button.setBackgroundColor(State.REST.getColor());
        }
    }

    public void onClickOnDelete(View v) {
        ArrayList<String> tabataNames = new ArrayList<>();
        for (Button button : buttonsSelected) {
            tabataNames.add((String)button.getText());
        }
        this.tabataFactory.deleteTabatasByNames(tabataNames);
        this.updateDisplay();
    }
}
