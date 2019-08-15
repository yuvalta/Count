package com.example.one2ten;

import android.os.SystemClock;
import android.widget.Chronometer;

public class StopWatch {

    boolean isFirstClick;
    Chronometer stopWatch;

    public StopWatch(boolean isFirstClick, Chronometer stopWatch) {
        this.isFirstClick = isFirstClick;
        this.stopWatch = stopWatch;
    }

    public void startStopWatch() {
        if (isFirstClick) { // if it's the first click start stop watch
            stopWatch.setBase(SystemClock.elapsedRealtime());
            stopWatch.start();
            isFirstClick = false;
        }
    }

    public void stopStopWatch() {
        stopWatch.stop();
        stopWatch.setBase(SystemClock.elapsedRealtime());
    }
}
