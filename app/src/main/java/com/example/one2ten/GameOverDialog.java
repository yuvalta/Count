package com.example.one2ten;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class GameOverDialog extends Dialog {
    Button tryAgainButton;
    Button returnButton;
    Context context;
    Activity activity;

    public boolean returnOrRetry; // true -> retry, false -> return to menu

    public GameOverDialog(Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.after_game_summary_dialog_layout);


        tryAgainButton = findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnOrRetry = true;
            }
        });

        returnButton = findViewById(R.id.return_to_menu_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnOrRetry = false;
            }
        });


    }
}
