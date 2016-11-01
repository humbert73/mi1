package com.example.moreauhu.testjeu;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.moreauhu.testjeu.entity.State;
import com.example.moreauhu.testjeu.entity.Tabata.Tabata;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class tabataActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    /*------------------------TABATA PART------------------------*/
    // VIEW
    private TextView timerValue;

    // DATA
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    private int timeElapsed;

    private  ArrayList<Tabata> programme;
    private int etape_restante = 1;

    private Tabata tabata;
    private int cyclesNumber;
    private int time;

    private State state;
    private static final String PLAY ="play";
    private static final String PAUSE="pause";

    // HANDLER
    private Handler customHandler = new Handler();
    /*------------------------TABATA PART------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabata);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        /*------------------------TABATA PART------------------------*/
        setContentView(R.layout.activity_tabata);

        this.init();

        // Lancement de updateTimerThread
        customHandler.postDelayed(updateTimerThread, 0);
        /*------------------------TABATA PART------------------------*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /*------------------------TABATA PART------------------------*/
    private void init()
    {
        this.programme = new ArrayList<>();

        // Initialisation en fonction du lancement en mode quick start ou d'un programme
        if ((ArrayList<Tabata>) getIntent().getSerializableExtra("programme") != null) {
            this.programme = (ArrayList<Tabata>) getIntent().getSerializableExtra("programme");
            this.etape_restante = programme.size();
            this.tabata = this.getCurrentTabata();
        } else {
            this.tabata = (Tabata)this.getIntent().getSerializableExtra("tabata");
        }
        this.initValues();
    }

    private void initValuesForNextStage() {
        this.tabata = getNextTabata();
        this.initValues();
        this.updateDisplay(this.state);
    }

    private void initValues() {
        this.cyclesNumber = this.tabata.getCyclesNumber();
        this.time  = tabata.getPrepareTime();
        this.state = State.PREPARE;
        this.initTimerValues();
    }

    private void initTimerValues() {
        this.startTime = SystemClock.uptimeMillis();
        this.timerValue  = (TextView) findViewById(R.id.timerValue);
    }

    private Tabata getNextTabata() {
        this.etape_restante--;

        return getCurrentTabata();
    }

    private Tabata getCurrentTabata() {
        return this.programme.get(this.programme.size() - this.etape_restante);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;// Calcul du temps écoulé
            updatedTime = timeSwapBuff + timeInMilliseconds;// Calcul du "timer" à afficher
            int secs = (int) (updatedTime / 1000); // Décompositions en secondes
            secs = secs % 60;
            timeElapsed = time - secs;

            updateTimerDisplay();
            restartOrStopTimer(this);
        }
    };

    private void updateTimerDisplay() {
        timerValue.setText(String.format("%02d", timeElapsed));
    }

    private void restartOrStopTimer(Runnable run) {
        if (this.isTheEnd()) {
            // Arrêt de updateTimerThread
            customHandler.removeCallbacks(updateTimerThread);
        } else {
            if (this.isTheCyclesEnd()) {
                initValuesForNextStage();
            } else if (this.isTheTimerEnd()) {
                this.changeState();
            }
            customHandler.postDelayed(run, 0);
        }
    }

    private boolean isTheEnd() {
        return this.isTheCyclesEnd() && etape_restante == 1;
    }

    private boolean isTheCyclesEnd() {
        return this.isTheTimerEnd() && cyclesNumber == 0;
    }

    private boolean isTheTimerEnd() {
        return timeElapsed <= 0;
    }

    //return time value of the new state;
    private void changeState() {
        if (state == State.WORK) {
            time = this.tabata.getRestTime();
            state = State.REST;
        } else {
            time = this.tabata.getWorkTime();
            state = State.WORK;
            cyclesNumber--;
        }
        this.initTimerValues();
        this.updateDisplay(state);
    }

    private void updateDisplay(State state) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.fullscreen_content_controls);
        TextView title = (TextView) findViewById(R.id.title_state);

        viewGroup.setBackgroundColor(state.getColor());
        title.setText(state.getTitle());
    }

    public void onClickPlayPause(View v) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.playPause);
        String tag = (String)imageButton.getTag();
        if (PAUSE.compareTo(tag) == 0) {
            imageButton.setBackgroundResource(R.drawable.ic_play_button);
            imageButton.setTag(PLAY);
            this.onClickPause();
        } else {
            imageButton.setBackgroundResource(R.drawable.ic_pause_button);
            imageButton.setTag(PAUSE);
            this.onClickPlay();
        }
    }

    private void onClickPause() {
        TextView textView = (TextView) findViewById(R.id.timerValue);
        time = Integer.valueOf((String) textView.getText());

        customHandler.removeCallbacks(updateTimerThread);
    }

    private void onClickPlay() {
        // Lancement de updateTimerThread
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }
    /*------------------------TABATA PART------------------------*/
}
