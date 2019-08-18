package com.example.one2ten;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


public class GameOverDialog extends Dialog {
    Button resumeButton;
    Button returnButton;
    Context context;
    InfinityModeFragment activity;

    TextView yourScore;
    TextView bestScore;
    TextView highScoreMessage;

    AnimatorSet animatorSet;

    public boolean returnOrResume; // return -> true, resume -> false

    int score;

    public GameOverDialog(Context context, InfinityModeFragment activity, int score, AnimatorSet animatorSet) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.score = score;
        this.animatorSet = animatorSet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_game_summary_dialog_layout);

        yourScore = findViewById(R.id.your_game_score_TV);
        bestScore = findViewById(R.id.high_score_game_over_TV);
        highScoreMessage = findViewById(R.id.high_score_message);

        yourScore.setText(String.valueOf(score - 1));

        SharedPreferences highScoreInfSharedPref = activity.getActivity().getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);

        if (highScoreInfSharedPref.getInt("highScoreInfinity", 0000) == 0) {
            bestScore.setText((String.valueOf(highScoreInfSharedPref.getInt("highScoreInfinity", 0000))));
        }
        else {
            bestScore.setText((String.valueOf(highScoreInfSharedPref.getInt("highScoreInfinity", 0000) - 1)));
        }

        resumeButton = findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSet.resume();
                dismiss();
            }
        });

        returnButton = findViewById(R.id.quit_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.loseGame(false);

                dismiss();
            }
        });

    }
}
