package com.example.one2ten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;


import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{


    public SharedPreferences highScoreInfSharedPref;
    public SharedPreferences bestTimeSharedPref;

    private RewardedVideoAd rewardedVideoAd;

    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////

        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
//
//        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        rewardedVideoAd.setRewardedVideoAdListener(this);
//        loadRewardedVideoAd();

        highScoreInfSharedPref = getSharedPreferences("yourHighScoreInfinity", Context.MODE_PRIVATE);
        bestTimeSharedPref = getSharedPreferences("bestTimeStopWatch", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
        editor.clear();
        editor.commit();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
        fragmentTransaction.commit();

    }

//    private void loadRewardedVideoAd() {
//        if (!rewardedVideoAd.isLoaded()) {
//            Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show();
//
//            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
//        }
//    }
//
//    public void showRewardedVideo() {
//        if (rewardedVideoAd.isLoaded()) {
//            Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
//
//            rewardedVideoAd.show();
//        }
//    }

    public SharedPreferences getInfHighScoreSharedPref() {
        return highScoreInfSharedPref;
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "loaded main", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "finished main", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
