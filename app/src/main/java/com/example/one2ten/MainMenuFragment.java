package com.example.one2ten;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainMenuFragment extends Fragment {

    private InfinityModeFragment.OnFragmentInteractionListener mListener ;

    LinearLayout topBar;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;
    TextView appName;

    Button timerModeButton;
    Button infinityModeButton;
    ImageButton backToMenu;

    ImageButton muteSounds;

    boolean isMute = false;

    boolean isPressedBackTwice;

//    private RewardedVideoAd rewardedVideoAd;
//
//    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544~3347511713");
//
//        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
//        rewardedVideoAd.setRewardedVideoAdListener();
//        loadRewardedVideoAd();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        isPressedBackTwice = false;

        topBar = getActivity().findViewById(R.id.top_bar);

        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);

        timerModeButton = view.findViewById(R.id.timer_mode_button);
        infinityModeButton = view.findViewById(R.id.infinity_mode_button);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);

        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);
        appName = view.findViewById(R.id.appNameText);

        muteSounds = view.findViewById(R.id.mute_sounds);
        muteSounds.setOnClickListener(muteListener);

        timerModeButton.setOnClickListener(modeSelectionListener);
        infinityModeButton.setOnClickListener(modeSelectionListener);

        Animation appNameAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein_fadeout);
        appName.startAnimation(appNameAnimation);

        setTopBar(View.INVISIBLE);

        return view;

    }

    private View.OnClickListener muteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (isMute) {
                muteSounds.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
                isMute = false;
            }
            else {
                muteSounds.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
                isMute = true;
            }

        }
    };

    private View.OnClickListener modeSelectionListener = new View.OnClickListener() {
        public void onClick(View v) {


            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (v.getId()) {
                case R.id.timer_mode_button:

                    StopWatchModeFragment stopWatchModeFragment = new StopWatchModeFragment(isMute);
                    fragmentTransaction.add(R.id.frame_layout_for_fragments, stopWatchModeFragment).addToBackStack("TimerFragment");
                    fragmentTransaction.commit();

                    break;

                case R.id.infinity_mode_button:

                    InfinityModeFragment infinityModeFragment = new InfinityModeFragment(isMute);
                    fragmentTransaction.add(R.id.frame_layout_for_fragments, infinityModeFragment).addToBackStack("TimerFragment");
                    fragmentTransaction.commit();

                    break;
            }

        }
    };

    private void setTopBar(int showTopBar) {

        timerTV.setVisibility(showTopBar);
        pointsTV.setVisibility(showTopBar);
        currentScoreTV.setVisibility(showTopBar);
        backToMenu.setVisibility(showTopBar);
        timeInfo.setVisibility(showTopBar);
        highScoreInfo.setVisibility(showTopBar);

    }

    private View.OnClickListener ReturnHomeListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {

//        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544~3347511713");
//
//        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
//        rewardedVideoAd.setRewardedVideoAdListener(this);
//        loadRewardedVideoAd();

        super.onResume();
    }

//    private void loadRewardedVideoAd() {
//        if (!rewardedVideoAd.isLoaded()) {
//            Toast.makeText(getContext(), "bbb", Toast.LENGTH_SHORT).show();
//
//            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
//        }
//    }
//
//    public void showRewardedVideo() {
//        if (rewardedVideoAd.isLoaded()) {
//            Toast.makeText(getContext(), "aaa", Toast.LENGTH_SHORT).show();
//
//            rewardedVideoAd.show();
//        }
//    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
