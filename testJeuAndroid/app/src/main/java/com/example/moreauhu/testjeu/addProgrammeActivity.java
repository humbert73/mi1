package com.example.moreauhu.testjeu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moreauhu.testjeu.entity.State;
import com.example.moreauhu.testjeu.entity.Tabata.Tabata;
import com.example.moreauhu.testjeu.entity.Tabata.TabataFactory;

import java.util.ArrayList;

public class addProgrammeActivity extends AppCompatActivity {

    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Button> buttons_clicked = new ArrayList<>();
    private ArrayList<Tabata> tabatas;
    private TabataFactory tabataFactory = new TabataFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_programme);

        initTabatas();
    }

    public void initTabatas() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.tabatas);
        this.tabatas = this.tabataFactory.getTabatas();

        for (Tabata tabata : this.tabatas) {
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onClickOnTabata(v);
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onLongClickOnTabata(v);
                }
            });
            button.setText(tabata.getName());
            layout.addView(button);
            this.buttons.add(button);
        }
    }

    public void onClickOnTabata(View v) {
        Button button = (Button)v;
        Button buttonClicked = new Button(this);
        buttonClicked.setText(button.getText());
        buttonClicked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickOnTabataSelected(v);
            }
        });
        buttons_clicked.add(buttonClicked);
        addSelectedTabataButton(buttonClicked);
    }

    public boolean onLongClickOnTabata(View v) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        String tabataName = ((TextView)v).getText().toString();
        Tabata tabata = this.tabataFactory.findTabataByName(tabataName);

        String tabataInformations = this.createTabataTextInformation(tabata);
        dlgAlert.setMessage(tabataInformations);
        dlgAlert.create().show();

        return true;
    }

    public void onClickOnTabataSelected(View v) {
        Button button = (Button)v;
        buttons_clicked.remove(button);
        removeSelectedTabataButton(button);
    }

//     Display
    public void removeSelectedTabataButton(Button button) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.tabatas_selected);
        layout.removeView(button);
    }

    public void addSelectedTabataButton(Button button) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.tabatas_selected);
        layout.addView(button);
    }

    public void onClickStart(View v) {
        ArrayList<Tabata> programme = this.getProgramme();
        if (programme.isEmpty()) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage(R.string.dialogAlert);
            dlgAlert.create().show();
        } else {
            Intent intent = new Intent(this, tabataActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("programme", programme);
            startActivity(intent);
        }

    }

    private ArrayList<Tabata> getProgramme() {
        Tabata tabata;
        ArrayList<Tabata> programme = new ArrayList<>();

        for (Button button : buttons_clicked) {
            tabata = this.tabataFactory.searchTabataByName((String) button.getText());
            if (tabata != null) {
                programme.add(tabata);
            }
        }

        return programme;
    }

    private String createTabataTextInformation(Tabata tabata) {
        String separation = " : ";
        String tab = "\t";
        String eol = "\n";
        String tabata_information = tab + tabata.getName() + eol + eol +
                State.PREPARE.getShortName() + separation + tab + tab + tabata.getPrepareTime() + eol +
                State.WORK.getShortName()    + separation + tab + tab + tabata.getWorkTime()    + eol +
                State.REST.getShortName()    + separation + tab + tab + tabata.getRestTime()    + eol +
                "Cycles" + separation + tab + tabata.getCyclesNumber();

        return tabata_information;
    }
}
