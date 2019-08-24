package com.example.one2ten;

import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GameOverDialog extends Dialog {

    int INFINITY = 1;
    int STOP_WATCH = 0;

    int currentHighScore = 0;
    int currentBestTime = 0;

    Button resumeButton;
    Button returnButton;
    Context context;

    InfinityModeFragment infinityModeFragment;
    StopWatchModeFragment stopWatchModeFragment;

    TextView yourScore;
    TextView bestScore;
    TextView highScoreMessage;

    AnimatorSet animatorSet;
    StopWatch stopper;

    public int gameMode;

    int yourHighScoreInfinity;
    long stopWatchTime;

    public GameOverDialog(Context context, InfinityModeFragment infinityModeFragment, int score, AnimatorSet animatorSet, int gameMode) {
        super(context);
        this.context = context;
        this.infinityModeFragment = infinityModeFragment;
        this.yourHighScoreInfinity = score;
        this.animatorSet = animatorSet;
        this.gameMode = gameMode;
    }

    public GameOverDialog(Context context, StopWatchModeFragment stopWatchModeFragment, long time, int gameMode, StopWatch stopper) {
        super(context);
        this.context = context;
        this.stopWatchModeFragment = stopWatchModeFragment;
        this.stopWatchTime = time;
        this.gameMode = gameMode;
        this.stopper = stopper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_game_summary_dialog_layout);

        yourScore = findViewById(R.id.your_game_score_TV);
        bestScore = findViewById(R.id.high_score_game_over_TV);
        highScoreMessage = findViewById(R.id.high_score_message);

        SharedPreferences infSharedPref = null;
        SharedPreferences stopWatchSharedPref = null;

        if (gameMode == INFINITY) {
            yourScore.setText(String.valueOf(yourHighScoreInfinity));
            infSharedPref = infinityModeFragment.getActivity().getSharedPreferences("yourHighScoreInfinity", Context.MODE_PRIVATE);

            currentHighScore = (infSharedPref.getInt("yourHighScoreInfinity", 0000));

//            if (infSharedPref.getInt("yourHighScoreInfinity", 0000) == 0) {
//                bestScore.setText((String.valueOf(infSharedPref.getInt("yourHighScoreInfinity", 0000))));
//            } else {
            if (yourHighScoreInfinity > currentHighScore) { // new high yourHighScoreInfinity
                highScoreMessage.setVisibility(View.VISIBLE);
                bestScore.setText(String.valueOf(yourHighScoreInfinity));
            } else {
                bestScore.setText(String.valueOf(currentHighScore));
            }
//            }

        } else if (gameMode == STOP_WATCH) {
            yourScore.setText(String.valueOf(stopWatchTime));
            stopWatchSharedPref = stopWatchModeFragment.getActivity().getSharedPreferences("bestTimeStopWatch", Context.MODE_PRIVATE);
            currentBestTime = (stopWatchSharedPref.getInt("bestTimeStopWatch", 0000));

            if (stopWatchSharedPref.getInt("bestTimeStopWatch", 0000) == 0) {
                bestScore.setText((String.valueOf(stopWatchSharedPref.getInt("bestTimeStopWatch", 0000))));
            } else {
                if (stopWatchTime < currentBestTime) {
                    highScoreMessage.setVisibility(View.VISIBLE);
                    bestScore.setText((String.valueOf(stopWatchSharedPref.getInt("bestTimeStopWatch", 0000) - 1)));
                } else {
                    bestScore.setText(currentBestTime);
                }
            }
        }

        resumeButton = findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameMode == INFINITY) {
                    animatorSet.resume();
                } else {
                    stopper.resumeStopWatch();
                }
                highScoreMessage.setVisibility(View.INVISIBLE);
                dismiss();
            }
        });

        returnButton = findViewById(R.id.quit_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameMode == INFINITY) {
                    infinityModeFragment.closeGameOverDialog();
                } else if (gameMode == STOP_WATCH) {
                    stopWatchModeFragment.winGame(false);
                }
                highScoreMessage.setVisibility(View.INVISIBLE);
                dismiss();
            }
        });

    }
}
