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
import com.example.moreauhu.testjeu.entity.Tabata.Tabata;
import com.example.moreauhu.testjeu.entity.Tabata.TabataFactory;

public class addTabataActivity extends AppCompatActivity {

    //  VALUES
    static final String  STATE_NAME      = "name";
    static final String  STATE_PREPARE   = "prepare";
    static final String  STATE_WORK      = "work";
    static final String  STATE_REST      = "rest";
    static final String  STATE_CYCLES    = "cycles";

    private String  name    = "";
    private Integer prepare = MainActivity.DEFAULT_PREPARE;
    private Integer work    = MainActivity.DEFAULT_WORK;
    private Integer rest    = MainActivity.DEFAULT_REST;
    private Integer cycles  = MainActivity.DEFAULT_CYCLES;

    private TabataFactory tabataFactory = new TabataFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tabata);

        if (savedInstanceState != null) {
            this.restoreSavedValues(savedInstanceState);
        }

        this.init();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_NAME, this.name);
        savedInstanceState.putInt(STATE_PREPARE, this.prepare);
        savedInstanceState.putInt(STATE_WORK   , this.work);
        savedInstanceState.putInt(STATE_REST   , this.rest);
        savedInstanceState.putInt(STATE_CYCLES , this.cycles);

        super.onSaveInstanceState(savedInstanceState);
    }


    private void restoreSavedValues(Bundle savedInstanceState) {
        this.name    = savedInstanceState.getString(STATE_NAME);
        this.prepare = savedInstanceState.getInt(STATE_PREPARE);
        this.work    = savedInstanceState.getInt(STATE_WORK);
        this.rest    = savedInstanceState.getInt(STATE_REST);
        this.cycles  = savedInstanceState.getInt(STATE_CYCLES);
    }

    private void init() {
        this.initValues();
        this.initRepeatImageButtons();
    }

    private void initValues() {
        ((TextView) findViewById(R.id.number_prepare)).setText(this.prepare.toString());
        ((TextView) findViewById(R.id.number_work)).setText(this.work.toString());
        ((TextView) findViewById(R.id.number_rest)).setText(this.rest.toString());
        ((TextView) findViewById(R.id.number_cycles)).setText(this.cycles.toString());
    }

    private void initRepeatImageButtons() {
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_prepare).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_work).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_rest).getParent());
        initImageButtonOfLayout((RelativeLayout) findViewById(R.id.label_cycles).getParent());
    }

    private void initImageButtonOfLayout(RelativeLayout layout) {
        ImageButton buttonMinus = (ImageButton) layout.getChildAt(2);
        ImageButton buttonPlus = (ImageButton) layout.getChildAt(3);
        buttonMinus.setOnTouchListener(
                new RepeatListener(
                        MainActivity.REPEAT_LISTENER_INITIAL_INTERVAL,
                        MainActivity.REPEAT_LISTENER_NORMAL_INTERVAL,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickOnMinus(v);
                            }
                        }));
        buttonPlus.setOnTouchListener(
                new RepeatListener(
                        MainActivity.REPEAT_LISTENER_INITIAL_INTERVAL,
                        MainActivity.REPEAT_LISTENER_NORMAL_INTERVAL,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickOnPlus(v);
                            }
                        }));
    }

    public void onClickOnMinus(View v) {
        TextView textView = this.getTextViewLinkedOfTheButton(v);
        Integer newValue = this.appliedActionOnValueByTextViewId(textView.getId(), "minus");
        textView.setText(newValue.toString());
    }

    public void onClickOnPlus(View v) {
        TextView textView = this.getTextViewLinkedOfTheButton(v);
        Integer newValue = this.appliedActionOnValueByTextViewId(textView.getId(), "plus");
        textView.setText(newValue.toString());
    }

    public void onClickAdd(View v) {
        this.createTabata();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private TextView getTextViewLinkedOfTheButton(View v) {
        RelativeLayout layout = (RelativeLayout) v.getParent();
        return (TextView) layout.getChildAt(1);
    }

    private int appliedActionOnValueByTextViewId(int id, String action) {
        int value = 0;
        if (id == R.id.number_prepare) {
            value = this.takeActionOnValue(this.prepare, action);
            this.prepare = value;
        } else if (id == R.id.number_work) {
            value = this.takeActionOnValue(this.work, action);
            this.work = value;
        } else if (id == R.id.number_rest) {
            value = this.takeActionOnValue(this.rest, action);
            this.rest = value;
        } else if (id == R.id.number_cycles) {
            value = this.takeActionOnValue(this.cycles, action);
            this.cycles = value;
        }

        return value;
    }

    private int takeActionOnValue(int value, String action) {
        if (action.equals("minus")) {
            value--;
        } else if (action.equals("plus")) {
            value++;
        }

        return value;
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

        this.tabataFactory.addOrReplaceTabata(name, prepareTime, workTime, restTime, cyclesNumber);
    }
}
