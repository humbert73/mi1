package com.example.moreauhu.testjeu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
            button.setText(this.createTabataTextInformation(tabata));
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
            tabata = this.tabataFactory.searchTabataByName(this.extractNameFromInformation((String) button.getText()));
            if (tabata != null) {
                programme.add(tabata);
            }
        }

        return programme;
    }

    private String extractNameFromInformation(String information) {
        String infos[]  = information.split(" [(]");
        return infos[0];
    }

    private String createTabataTextInformation(Tabata tabata) {
        String separation_state = ", ";
        String separation = ": ";
        return tabata.getName() + " (" +
                State.PREPARE.getShortName() + separation + tabata.getPrepareTime() + separation_state +
                State.WORK.getShortName()    + separation + tabata.getWorkTime()    + separation_state +
                State.REST.getShortName()    + separation + tabata.getRestTime()    +
                ") x" + tabata.getCyclesNumber();
    }
}
