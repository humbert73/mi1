package com.example.moreauhu.testjeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moreauhu.testjeu.entity.RepeatListener;
import com.example.moreauhu.testjeu.entity.Tabata.Tabata;
import com.example.moreauhu.testjeu.entity.Tabata.TabataFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //  VALUES
    static final String STATE_PREPARE = "prepare";
    static final String STATE_WORK = "work";
    static final String STATE_REST = "rest";
    static final String STATE_CYCLES = "cycles";
    private Integer prepare = 10;
    private Integer work = 20;
    private Integer rest = 10;
    private Integer cycles = 8;
    private TabataFactory tabataFactory = new TabataFactory();

    static final int REPEAT_LISTENER_INITIAL_INTERVAL = 400;
    static final int REPEAT_LISTENER_NORMAL_INTERVAL = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futur_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            this.restoreSavedValues(savedInstanceState);
        }

        init();
        initValues();
        initRepeatImageButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //this.updateValues();
        savedInstanceState.putInt(STATE_PREPARE, this.prepare);
        savedInstanceState.putInt(STATE_WORK, this.work);
        savedInstanceState.putInt(STATE_REST, this.rest);
        savedInstanceState.putInt(STATE_CYCLES, this.cycles);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValues() {
        //this.prepare = ((TextView)findViewById(R.id.))
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.futur_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_tabata) {
            Intent intent = new Intent(this, addTabataActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_programme) {
            Intent intent = new Intent(this, addProgrammeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gestion_tabata) {

        } else if (id == R.id.nav_gestion_programme) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void onClickStart(View v) {
        Intent intent = new Intent(this, tabataActivity.class);
        Tabata tabata = createTabata();

        intent.putExtra("tabata", tabata);
        startActivity(intent);
    }

    private void restoreSavedValues(Bundle savedInstanceState) {
        this.prepare = savedInstanceState.getInt(STATE_PREPARE);
        this.work = savedInstanceState.getInt(STATE_WORK);
        this.rest = savedInstanceState.getInt(STATE_REST);
        this.cycles = savedInstanceState.getInt(STATE_CYCLES);
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
        buttonMinus.setOnTouchListener(new RepeatListener(
                REPEAT_LISTENER_INITIAL_INTERVAL,
                REPEAT_LISTENER_NORMAL_INTERVAL,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickOnMinus(v);
                    }
                }));
        buttonPlus.setOnTouchListener(new RepeatListener(
                REPEAT_LISTENER_INITIAL_INTERVAL,
                REPEAT_LISTENER_NORMAL_INTERVAL,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickOnPlus(v);
                    }
                }));
    }

    private Tabata createTabata() {
        TextView prepareTextView = (TextView) findViewById(R.id.number_prepare);
        TextView workTextView = (TextView) findViewById(R.id.number_work);
        TextView restTextView = (TextView) findViewById(R.id.number_rest);
        TextView cyclesTextView = (TextView) findViewById(R.id.number_cycles);
        Integer prepareTime = Integer.valueOf((String) prepareTextView.getText());
        Integer workTime = Integer.valueOf((String) workTextView.getText());
        Integer restTime = Integer.valueOf((String) restTextView.getText());
        Integer cyclesNumber = Integer.valueOf((String) cyclesTextView.getText());

        //TODO: implements Parceble
        return this.tabataFactory.addOrUpdateTabata("quick", prepareTime, workTime, restTime, cyclesNumber);
    }
}
