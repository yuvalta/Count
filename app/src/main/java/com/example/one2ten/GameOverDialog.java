package com.example.one2ten;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class GameOverDialog extends Dialog {
    Button tryAgainButton;
    Button returnButton;
    Context context;
    Activity activity;

    TextView yourScore;
    TextView bestScore;

    int score;

    public GameOverDialog(Context context, Activity activity, int score) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.score = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_game_summary_dialog_layout);

        yourScore = findViewById(R.id.your_game_score_TV);
        bestScore = findViewById(R.id.high_score_game_over_TV);

        yourScore.setText(String.valueOf(score - 1));

        SharedPreferences highScoreInfSharedPref = activity.getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);
        bestScore.setText((String.valueOf(highScoreInfSharedPref.getInt("highScoreInfinity", 0000))));

        tryAgainButton = findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
