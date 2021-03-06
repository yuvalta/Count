package com.yeti.count;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {


    public SharedPreferences highScoreInfSharedPref;
    public SharedPreferences bestTimeSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////

        highScoreInfSharedPref = getSharedPreferences("yourHighScoreInfinity", Context.MODE_PRIVATE);
        bestTimeSharedPref = getSharedPreferences("bestTimeStopWatch", Context.MODE_PRIVATE);

//        SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
//        editor.clear();
//        editor.commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
        fragmentTransaction.commit();

    }
}
