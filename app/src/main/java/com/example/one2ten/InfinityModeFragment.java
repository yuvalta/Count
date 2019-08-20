package com.example.one2ten;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.Random;


public class InfinityModeFragment extends Fragment {

    private int MAX_BUTTON = 31;
    private int MIN_BUTTON = 0;
    private int DURATION_OF_ALPHA = 3000;
    private int currentTileRandomPlace;
    private int randomColor;
    private int probability;
    private int numberToShowOnButton = 1;
    private int fakeTilePosition = -1;
    private int highScore;
    private int secondFakePosition = -1;

    boolean isMute = false;

    private SharedPreferences highScoreInfSharedPref;

    private AnimatorSet mAnimationSet;

    private LinearLayout topBar;
    private LinearLayout timerLinearLayout;

    private TextView pointsTV;
    private TextView currentScoreTV;
    private TextView timeInfo;
    private TextView highScoreInfo;

    private ImageButton backToMenu;

    private boolean isFirstClick;

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

    public InfinityModeFragment(boolean isMute) {
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
        View view = inflater.inflate(R.layout.fragment_infinity_mode, container, false);

        clickMp = MediaPlayer.create(getContext(), R.raw.level_up);
        levelUpMp = MediaPlayer.create(getContext(), R.raw.click);

        highScoreInfSharedPref = this.getActivity().getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);
        highScore = highScoreInfSharedPref.getInt("highScoreInfinity", 0000);

        isFirstClick = true;

        bindGridViews(view);

        setTopBar(View.VISIBLE);

//        stopper = new StopWatch(true, stopWatch); // class that it's a stop watch

        currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

        backToMenu.setOnClickListener(ReturnHomeListener);

        createTile(currentTileRandomPlace, 1, numberToShowOnButton++);

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

                    if (mAnimationSet != null) {
                        mAnimationSet.removeAllListeners();
                        mAnimationSet.end();
                        mAnimationSet.cancel();
                    }

                    tileCleanup(currentTileRandomPlace);

                    if (fakeTilePosition >= 0) {
                        tileCleanup(fakeTilePosition);
                    }
                    if (secondFakePosition >= 0) {
                        tileCleanup(secondFakePosition);
                    }

                    Object tapOfButtonPressed = view.getTag();

                    if (tapOfButtonPressed.equals(String.valueOf(currentTileRandomPlace))) {

                        if (!isMute) {
                            makeSounds();
                        }

                        DifficultyOfGame difficultyOfGame = new DifficultyOfGame(numberToShowOnButton);
                        DURATION_OF_ALPHA = difficultyOfGame.getDuration();
                        probability = difficultyOfGame.getProbability();

//                        stopper.startStopWatch();

                        currentScoreTV.setText(String.valueOf(numberToShowOnButton - 1)); // set current score

                        if (numberToShowOnButton > highScore) { // new high score!!!
                            pointsTV.setText(String.valueOf(numberToShowOnButton - 1));
                        }

                        currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                        randomColor = generateRandomNumber(colorsArray.length - 1, 0);

                        fadeOutButton(currentTileRandomPlace);

                        createTile(currentTileRandomPlace, randomColor, numberToShowOnButton++);

                        fakeTilePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

                        if (generateRandomNumber(100, 0) < probability && fakeTilePosition != currentTileRandomPlace) {

                            createTile(fakeTilePosition, numberToShowOnButton % colorsArray.length,
                                    generateRandomNumber(numberToShowOnButton / 2, 1)); // create fake tile with some arbitrary color

                            secondFakePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                            if (generateRandomNumber(100, 0) < (probability / 2) && secondFakePosition != fakeTilePosition) {

                                createTile(secondFakePosition, currentTileRandomPlace % colorsArray.length,
                                        generateRandomNumber(numberToShowOnButton / 2, 1));
                            } else {
                                secondFakePosition = -1;
                            }

                        } else {
                            fakeTilePosition = -1;
                        }


                    } else {
                        loseGame(true);
                    }
                }
            });
        }
    }

    public void loseGame(boolean isPressedReturn) {

        if (mAnimationSet != null) {
            mAnimationSet.removeAllListeners();
            mAnimationSet.end();
            mAnimationSet.cancel();
        }
//        stopper.stopStopWatch();

        currentScoreTV.setText("0");

        clickMp.release();
        levelUpMp.release();

        if (isPressedReturn) {
            GameOverDialog gameOverDialog = new GameOverDialog(getContext(), InfinityModeFragment.this, numberToShowOnButton - 1, mAnimationSet);
            gameOverDialog.setCancelable(false);
            gameOverDialog.setCanceledOnTouchOutside(false);
            gameOverDialog.show();
            gameOverDialog.resumeButton.setVisibility(View.INVISIBLE);

            if (numberToShowOnButton - 1 > highScore) { // new high score!!!
                SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
                editor.putInt("highScoreInfinity", numberToShowOnButton - 1);
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


    public void fadeOutButton(final int currentPlaceOfButton) {

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textViewArrayList.get(currentPlaceOfButton), "alpha", 1f, 0f);
        fadeOut.setDuration(DURATION_OF_ALPHA);

        mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                loseGame(false);

            }
        });
        mAnimationSet.start();
    }

    public void bindGridViews(View view) {

        // topBar binding
        topBar = getActivity().findViewById(R.id.top_bar);
        timerLinearLayout = getActivity().findViewById(R.id.timer_ll);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);

        pointsTV.setText(String.valueOf(highScore));
        timerLinearLayout.getLayoutParams().width = 110; // TODO - fix this static assignment
        currentScoreTV.setText("0"); // set current score

        initTextViews(view);
    }

    public int generateRandomNumber(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


    private void setTopBar(int showTopBar) {

        pointsTV.setVisibility(showTopBar);
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

            if (numberToShowOnButton != 2) {
                mAnimationSet.pause();

                GameOverDialog gameOverDialog = new GameOverDialog(getContext(), InfinityModeFragment.this, numberToShowOnButton - 1, mAnimationSet);
                gameOverDialog.setCancelable(false);
                gameOverDialog.setCanceledOnTouchOutside(false);
                gameOverDialog.show();
            }
        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
