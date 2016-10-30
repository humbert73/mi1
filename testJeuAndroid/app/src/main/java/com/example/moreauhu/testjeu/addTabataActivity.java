package com.example.moreauhu.testjeu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moreauhu.testjeu.entity.RepeatListener;
import com.example.moreauhu.testjeu.entity.Tabata;

public class addTabataActivity extends AppCompatActivity {

    //  VALUES
    static final String STATE_PREPARE = "prepare";
    static final String STATE_WORK = "work";
    static final String STATE_REST = "rest";
    static final String STATE_CYCLES = "cycles";
    private Integer prepare = 10;
    private Integer work = 20;
    private Integer rest = 10;
    private Integer cycles = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tabata);
        initRepeatImageButtons();
    }

    public void initRepeatImageButtons() {
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_prepare).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_work).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_rest).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_cycles).getParent());
    }

    private void initImageButtonOfLayout(RelativeLayout layout) {
        ImageButton buttonMinus = (ImageButton) layout.getChildAt(2);
        ImageButton buttonPlus = (ImageButton) layout.getChildAt(3);
        buttonMinus.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnMinus(v);
            }
        }));
        buttonPlus.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnPlus(v);
            }
        }));
    }

    public void onClickOnMinus(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        TextView textView = (TextView) layout.getChildAt(1);
        Integer number = Integer.valueOf((String) textView.getText());
        number = number - 1;
        textView.setText(number.toString());
    }

    public void onClickOnPlus(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        TextView textView = (TextView) layout.getChildAt(1);
        Integer number = Integer.valueOf((String) textView.getText());
        number = number + 1;
        textView.setText(number.toString());
        //TODO: update()
    }

    public void onClickAdd(View v) {
        this.createTabata();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void createTabata() {
        EditText nameInput       = (EditText) findViewById(R.id.input_name);
        TextView prepareTextView = (TextView) findViewById(R.id.number_prepare);
        TextView workTextView    = (TextView) findViewById(R.id.number_work);
        TextView restTextView    = (TextView) findViewById(R.id.number_rest);
        TextView cyclesTextView  = (TextView) findViewById(R.id.number_cycles);

        String  name             = String.valueOf(nameInput.getText());
        Integer prepareTime      = Integer.valueOf((String)prepareTextView.getText());
        Integer workTime         = Integer.valueOf((String)workTextView.getText());
        Integer restTime         = Integer.valueOf((String)restTextView.getText());
        Integer cyclesNumber     = Integer.valueOf((String)cyclesTextView.getText());

        //TODO: implements Parceble
        Tabata tabata = new Tabata(name, prepareTime, workTime, restTime, cyclesNumber);
        tabata.save();
    }
}
