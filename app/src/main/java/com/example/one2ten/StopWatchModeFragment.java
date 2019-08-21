package com.example.one2ten;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Random;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class StopWatchModeFragment extends Fragment {

    private int MAX_BUTTON = 31;
    private int MIN_BUTTON = 0;
    int STOP_WATCH = 0;

    private int currentTileRandomPlace;
    private int randomColor;
    private int numberToShowOnButton = 1;
    private long highScoreMilli;

    boolean isMute;

    private SharedPreferences bestTimeSharedPref;

    private TextView currentScoreTV;
    private TextView timeInfo;
    private TextView highScoreInfo;

    private ImageButton backToMenu;

    private Chronometer stopWatch;

    private StopWatch stopper;

    MediaPlayer clickMp, levelUpMp;

//    private RewardedAd rewardedAd;

    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31};

    int[] colorsArray = new int[]{R.color.menuRed, R.color.menuWhite, R.color.menuYellow, R.color.OliveDrab, R.color.SteelBlue,
            R.color.MediumPurple, R.color.MediumOrchid, R.color.MediumSpringGreen, R.color.MediumVioletRed, R.color.MediumSeaGreen};

    ArrayList<TextView> textViewArrayList = new ArrayList<TextView>(ids.length);

    public StopWatchModeFragment(boolean isMute) {
        this.isMute = isMute;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        rewardedAd = new RewardedAd(getContext(), "ca-app-pub-3940256099942544/5224354917");
//
//        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
//            @Override
//            public void onRewardedAdLoaded() {
//                // Ad successfully loaded.
//            }
//
//            @Override
//            public void onRewardedAdFailedToLoad(int errorCode) {
//                // Ad failed to load. look for return values on https://developers.google.com/admob/android/rewarded-ads
//            }
//        };
//        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1to100_mode, container, false);

        clickMp = MediaPlayer.create(getContext(), R.raw.level_up);
        levelUpMp = MediaPlayer.create(getContext(), R.raw.click);

        bestTimeSharedPref = this.getActivity().getSharedPreferences("bestTimeStopWatch", Context.MODE_PRIVATE);
        highScoreMilli = (Long) bestTimeSharedPref.getLong("bestTimeStopWatch", 1);

        bindGridViews(view);

        setTopBar(View.VISIBLE);

        stopper = new StopWatch(true, stopWatch); // class that it's a stop watch

        currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

        backToMenu.setOnClickListener(ReturnHomeListener);

        createTile(currentTileRandomPlace, 1, numberToShowOnButton++);

//        SharedPreferences.Editor editor = bestTimeSharedPref.edit();
//        editor.putInt("bestTimeStopWatch", 1000000);
//        editor.commit();

        return view;
    }

    private void initTextViews(View view) { // find all buttons
        for (int i = 0; i < ids.length; i++) {
            TextView currentTextView = view.findViewById(ids[i]);
            textViewArrayList.add(currentTextView);
            textViewArrayList.get(i).getBackground().setTint(getResources().getColor(R.color.transparent));
        }

        for (int i = 0; i < ids.length; i++) { // set listeners to all buttons

            textViewArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tileCleanup(currentTileRandomPlace);

                    if (numberToShowOnButton <= 10) {

                        Object tapOfButtonPressed = view.getTag();

                        if (tapOfButtonPressed.equals(String.valueOf(currentTileRandomPlace))) {

                            if (!isMute) {
                                makeSounds();
                            }

                            stopper.startStopWatch();

                            currentScoreTV.setText(String.valueOf(numberToShowOnButton - 1)); // set current highScoreInfinity

                            if (stopper.getTime() < highScoreMilli) {
                                // new high highScoreInfinity!!!
                            }

                            currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                            randomColor = generateRandomNumber(colorsArray.length - 1, 0);

                            createTile(currentTileRandomPlace, randomColor, numberToShowOnButton++);
                        } else {
                            createTile(currentTileRandomPlace, randomColor, numberToShowOnButton - 1);
                        }

                    } else {
                        winGame(true);
                    }
                }
            });
        }
    }

    public void winGame(boolean isPressedReturn) {

        Log.d("aaa", String.valueOf(stopper.getTime()));
        Log.d("bbb", String.valueOf(highScoreMilli));

        stopper.stopStopWatch();

        currentScoreTV.setText("0");

        clickMp.release();
        levelUpMp.release();

        if (isPressedReturn) {
            GameOverDialog gameOverDialog = new GameOverDialog(getContext(), StopWatchModeFragment.this, stopper.getTime(), STOP_WATCH, stopper);
            gameOverDialog.setCancelable(false);
            gameOverDialog.setCanceledOnTouchOutside(false);
            gameOverDialog.show();
            gameOverDialog.resumeButton.setVisibility(View.INVISIBLE);

            if (stopper.getTime() < highScoreMilli) { // new high highScoreInfinity!!!
                SharedPreferences.Editor editor = bestTimeSharedPref.edit();
                editor.putLong("bestTimeStopWatch", stopper.getTime());
                editor.commit();
            }
        } else {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    public void tileCleanup(int position) {
        textViewArrayList.get(position).getBackground().setTint(getResources().getColor(R.color.transparent));
        textViewArrayList.get(position).setText("");
    }

    public void createTile(int position, int randomColor, int numberToShowOnButton) {
        textViewArrayList.get(position).getBackground().setTint(getResources().getColor(colorsArray[randomColor]));
        textViewArrayList.get(position).setText(String.valueOf(numberToShowOnButton++));
        textViewArrayList.get(position).setTextSize(30);
    }

    public void makeSounds() {

        if (numberToShowOnButton % 10 == 0) {
            clickMp.start();
        } else {
            levelUpMp.start();
        }
    }

    public void bindGridViews(View view) {

        // topBar binding
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        stopWatch = getActivity().findViewById(R.id.timerTV);

        currentScoreTV.setText("0"); // set current highScoreInfinity

        initTextViews(view);
    }

    public int generateRandomNumber(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


    private void setTopBar(int showTopBar) {

        stopWatch.setVisibility(showTopBar);
        currentScoreTV.setVisibility(showTopBar);
        backToMenu.setVisibility(showTopBar);
        highScoreInfo.setVisibility(showTopBar);
    }

    @Override
    public void onDestroy() {
        setTopBar(View.INVISIBLE);
        super.onDestroy();
    }

    private View.OnClickListener ReturnHomeListener = new View.OnClickListener() {
        public void onClick(View v) {

            stopper.pauseStopWatch();

            GameOverDialog gameOverDialog = new GameOverDialog(getContext(), StopWatchModeFragment.this, numberToShowOnButton - 1, STOP_WATCH, stopper);
            gameOverDialog.setCancelable(false);
            gameOverDialog.setCanceledOnTouchOutside(false);
            gameOverDialog.show();

        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
