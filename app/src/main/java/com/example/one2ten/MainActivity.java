package com.example.one2ten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public SharedPreferences highScoreInfSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////

        highScoreInfSharedPref = getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
        fragmentTransaction.commit();

    }

    public SharedPreferences getInfHighScoreSharedPref() {
        return highScoreInfSharedPref;
    }

}
