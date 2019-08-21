package com.example.one2ten;

import android.os.SystemClock;
import android.widget.Chronometer;

public class StopWatch {

    boolean isFirstClick;
    Chronometer stopWatch;
    long timeSinceStart = 0;

    public StopWatch(boolean isFirstClick, Chronometer stopWatch) {
        this.isFirstClick = isFirstClick;
        this.stopWatch = stopWatch;
    }

    public void startStopWatch() {
        if (isFirstClick) { // if it's the first click start stop watch
            timeSinceStart = 0;
            stopWatch.setBase(SystemClock.elapsedRealtime());
            stopWatch.start();
            isFirstClick = false;
        }
    }

    public void stopStopWatch() {
        stopWatch.stop();
        stopWatch.setBase(SystemClock.elapsedRealtime());
    }

    public void pauseStopWatch() {
        timeSinceStart = stopWatch.getBase() - SystemClock.elapsedRealtime();
        stopWatch.stop();
    }

    public void resumeStopWatch() {
        stopWatch.setBase(SystemClock.elapsedRealtime() + timeSinceStart);
        stopWatch.start();
    }

    public long getTime() {
        return (int) (SystemClock.elapsedRealtime() - stopWatch.getBase());
    }
}
