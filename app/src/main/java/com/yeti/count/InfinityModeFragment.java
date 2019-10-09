package com.yeti.count;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.jinatonic.confetti.CommonConfetti;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;
import java.util.Random;

public class InfinityModeFragment extends Fragment {

    private int MAX_BUTTON = 31;
    private int MIN_BUTTON = 0;
    private int DURATION_OF_ALPHA = 3000;
    private int INFINITY = 1;

    private int currentTileRandomPlace = 4; // static number for the explain mode
    private int randomColor;
    private int probability;
    private int numberToShowOnButton = 1;
    private int fakeTilePosition = -1;
    private int highScore;
    private int secondFakePosition = -1;
    private int thirdFakePosition = -1;
    private int tapsCounterWhenUserLoses = 0;

    boolean isMute = false;
    boolean isHighScoreAcheviedForFirstTime = false; // this flag is for the YAY sound. i want it to be only one time when user pass high score
    boolean isTryAgainHappend = false;

    private SharedPreferences highScoreInfSharedPref;

    private AnimatorSet mAnimationSet0;
    private AnimatorSet mAnimationSet1;
    private AnimatorSet mAnimationSet2;
    private AnimatorSet mAnimationSet3;

    private LinearLayout topBar;
    private LinearLayout timerLinearLayout;

    private TextView pointsTV;
    private TextView currentScoreTV;
    private TextView timeInfo;
    private TextView highScoreInfo;
    private TextView hitTileExplainTV;

    private AnimatorListenerAdapter animatorListenerAdapter;

    private ImageButton backToMenu;

    private boolean explainMode = true;

    private ViewGroup viewGroup;

    MediaPlayer clickMp, levelUpMp, HighScoreYAY;

    MainMenuFragment mainMenuFragment;


    private RewardedVideoAd rewardedVideoAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";


    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31};

    int[] colorsArray = new int[]{R.color.menuRed, R.color.menuWhite, R.color.menuYellow, R.color.OliveDrab, R.color.SteelBlue,
            R.color.MediumPurple, R.color.MediumOrchid, R.color.MediumSpringGreen, R.color.MediumVioletRed, R.color.MediumSeaGreen};

    ArrayList<TextView> textViewArrayList = new ArrayList<TextView>(ids.length);

    public InfinityModeFragment(boolean isMute, RewardedVideoAd rewardedVideoAd, MainMenuFragment mainMenuFragment) {
        this.isMute = isMute;
        this.rewardedVideoAd = rewardedVideoAd;
        this.mainMenuFragment = mainMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = container;

        View view = inflater.inflate(R.layout.fragment_infinity_mode, container, false);

        clickMp = MediaPlayer.create(getContext(), R.raw.level_up);
        levelUpMp = MediaPlayer.create(getContext(), R.raw.click);
        HighScoreYAY = MediaPlayer.create(getContext(), R.raw.yay_high_score);

        highScoreInfSharedPref = this.getActivity().getSharedPreferences("yourHighScoreInfinity", Context.MODE_PRIVATE);
        highScore = highScoreInfSharedPref.getInt("yourHighScoreInfinity", 1);

        bindGridViews(view);

        setTopBar(View.VISIBLE);

//        currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

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

                    if (numberToShowOnButton > 1) {
                        backToMenu.setImageResource(R.drawable.ic_pause_black_24dp);
                    }

                    if (view.getTag().equals(String.valueOf(currentTileRandomPlace))) {

                        checkAndStopTileAnimation(mAnimationSet0); // remove all listeners and stops animation
                        checkAndStopTileAnimation(mAnimationSet1);
                        checkAndStopTileAnimation(mAnimationSet2);
                        checkAndStopTileAnimation(mAnimationSet3);

                        tileCleanup(currentTileRandomPlace); // clean tile and

                        if (fakeTilePosition >= 0) {
                            fakeTilePosition = tileCleanup(fakeTilePosition);
                        }
                        if (secondFakePosition >= 0) {
                            secondFakePosition = tileCleanup(secondFakePosition);
                        }
                        if (thirdFakePosition >= 0) {
                            thirdFakePosition = tileCleanup(thirdFakePosition);
                        }

                        if (!isMute) {
                            makeSounds(false);
                        }

                        DifficultyOfGame difficultyOfGame = new DifficultyOfGame(numberToShowOnButton);
                        DURATION_OF_ALPHA = difficultyOfGame.getDuration();
                        probability = difficultyOfGame.getProbability();

                        currentScoreTV.setText(String.valueOf(numberToShowOnButton)); // set current yourHighScoreInfinity

                        if (numberToShowOnButton > highScore) { // new high yourHighScoreInfinity!!!
                            pointsTV.setText(String.valueOf(numberToShowOnButton));
                            if (!isMute) { // if we are on quiet mode
                                makeSounds(true);
                            }

                            if (!isHighScoreAcheviedForFirstTime) { // play confetti on high score
                                CommonConfetti.rainingConfetti(viewGroup, new int[]{Color.RED, Color.WHITE, Color.YELLOW}).oneShot();
                            }
                            isHighScoreAcheviedForFirstTime = true;
                        }


                        if (explainMode) { // display 3 messages to the user that explain how to play
                            DURATION_OF_ALPHA = 9999; // make duration longer for reading explains

                            switch (numberToShowOnButton) // show on static places on the grid
                            {
                                case 1:
                                    currentTileRandomPlace = 4;
                                case 2:
                                    currentTileRandomPlace = 17;
                                    hitTileExplainTV.setText(R.string.game_explain_asc);
                                    break;
                                case 3:
                                    currentTileRandomPlace = 22;
                                    hitTileExplainTV.setText(R.string.game_explain_dont_confuse);
                                    break;
                                case 4:
                                    hitTileExplainTV.setVisibility(View.INVISIBLE);
                                    explainMode = false;
                                    break;
                            }
                        } else {

                            currentTileRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                        }

                        randomColor = generateRandomNumber(colorsArray.length - 1, 0);

                        mAnimationSet0 = fadeOutButton(currentTileRandomPlace);

                        createTile(currentTileRandomPlace, randomColor, numberToShowOnButton++);

                        fakeTilePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

                        while (fakeTilePosition == currentTileRandomPlace) {
                            fakeTilePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                        }

                        if (generateRandomNumber(100, 0) < probability && fakeTilePosition != currentTileRandomPlace // if all fake tile are in different position
                                && fakeTilePosition != secondFakePosition && fakeTilePosition != thirdFakePosition) {

                            createTile(fakeTilePosition, (randomColor + 1) % colorsArray.length,
                                    generateRandomNumber(numberToShowOnButton / 2, 1)); // create fake tile with some arbitrary color

                            mAnimationSet1 = fadeOutButton(fakeTilePosition);

                            secondFakePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON); // second fake tile

                            if (generateRandomNumber(100, 0) <= (probability) && secondFakePosition != fakeTilePosition
                                    && secondFakePosition != currentTileRandomPlace) {

                                createTile(secondFakePosition, (randomColor + 2) % colorsArray.length,
                                        generateRandomNumber(numberToShowOnButton / 2, 1));

                                mAnimationSet2 = fadeOutButton(secondFakePosition);

                            } else {
                                secondFakePosition = -1;
                            }

                            thirdFakePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON); // third fake tile

                            if (generateRandomNumber(100, 0) <= (probability) && thirdFakePosition != fakeTilePosition
                                    && thirdFakePosition != secondFakePosition && thirdFakePosition != currentTileRandomPlace) {

                                createTile(thirdFakePosition, (randomColor + 3) % colorsArray.length,
                                        generateRandomNumber(numberToShowOnButton / 2, 1));

                                mAnimationSet3 = fadeOutButton(thirdFakePosition);

                            } else {
                                thirdFakePosition = -1;
                            }

                        } else {
                            fakeTilePosition = -1;
                        }

                    } else {
                        if (tapsCounterWhenUserLoses == 0) {
                            loseGame();
                        }
                    }
                }
            });
        }
    }

    public void loseGame() {
        tapsCounterWhenUserLoses++; // this counter make sure that you press only one time on screen when losing

        GameOverDialog gameOverDialog = new GameOverDialog(getContext(), InfinityModeFragment.this,
                numberToShowOnButton - 1, mAnimationSet0, mAnimationSet1, mAnimationSet2, mAnimationSet3, INFINITY);
        gameOverDialog.setCancelable(false);
        gameOverDialog.setCanceledOnTouchOutside(false);
        gameOverDialog.show();

        if (isTryAgainHappend) {
            gameOverDialog.tryAgainButton.setVisibility(View.INVISIBLE);
            gameOverDialog.tryAgainButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }

    }

    public void startAnimationsAfterAd() {
        if (mAnimationSet0 != null) {
            mAnimationSet0.start();
            mAnimationSet0.pause();
        }
        if (mAnimationSet1 != null) {
            mAnimationSet1.start();
            mAnimationSet1.pause();
        }
        if (mAnimationSet2 != null) {
            mAnimationSet2.start();
            mAnimationSet2.pause();
        }
        if (mAnimationSet3 != null) {
            mAnimationSet3.start();
            mAnimationSet3.pause();
        }
    }

    public void onTryAgainPressed() {
        isTryAgainHappend = true;
        tapsCounterWhenUserLoses--; // set it back to 0
        mainMenuFragment.showRewardedVideo();
    }

    public void onMainMenuPressed() {
        checkAndStopTileAnimation(mAnimationSet0); // remove all listeners and stops animation
        checkAndStopTileAnimation(mAnimationSet1);
        checkAndStopTileAnimation(mAnimationSet2);
        checkAndStopTileAnimation(mAnimationSet3);

        currentScoreTV.setText("0");
        clickMp.release();
        levelUpMp.release();
        HighScoreYAY.release();

        if (numberToShowOnButton - 1 > highScore) { // new high yourHighScoreInfinity!!!
            SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
            editor.putInt("yourHighScoreInfinity", numberToShowOnButton - 1);
            editor.commit();
        }
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public int tileCleanup(int position) {
        textViewArrayList.get(position).setBackgroundResource(android.R.color.transparent);
        textViewArrayList.get(position).setText("");
        return 0;
    }

    public void createTile(final int position, final int randomColor, final int numberToShowOnButton) {
        textViewArrayList.get(position).setBackground(getResources().getDrawable(R.drawable.normal_style));
        textViewArrayList.get(position).getBackground().setTint(getResources().getColor(colorsArray[randomColor]));
        textViewArrayList.get(position).setText(String.valueOf(numberToShowOnButton));
        textViewArrayList.get(position).setTextSize(30);
    }

    public void checkAndStopTileAnimation(AnimatorSet animatorSet) {
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.end();
            animatorSet.cancel();
        }
    }

    public void makeSounds(boolean isHighScore) {
        if (isHighScore && isHighScoreAcheviedForFirstTime == false) {
            HighScoreYAY.start();
            Animation highScoreCounterAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            Animation highScoreInfoAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            pointsTV.startAnimation(highScoreCounterAnimation);
            highScoreInfo.startAnimation(highScoreInfoAnimation);
        } else {
            if (numberToShowOnButton % 10 == 0) {
                clickMp.start();
            } else {
                levelUpMp.start();
            }
        }
    }

    public AnimatorSet fadeOutButton(final int currentPlaceOfButton) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textViewArrayList.get(currentPlaceOfButton), "alpha", 1f, 0f);
        fadeOut.setDuration(DURATION_OF_ALPHA);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeOut);

        mAnimationSet.addListener(animatorListenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (tapsCounterWhenUserLoses == 0) {
                    loseGame();
                }
                if (mAnimationSet0 != null) {
                    mAnimationSet0.pause();
                }
                if (mAnimationSet1 != null) {
                    mAnimationSet1.pause();
                }
                if (mAnimationSet2 != null) {
                    mAnimationSet2.pause();
                }
                if (mAnimationSet3 != null) {
                    mAnimationSet3.pause();
                }
            }
        });
        mAnimationSet.start();

        return mAnimationSet;
    }

    public void bindGridViews(View view) {
        // topBar binding
        topBar = getActivity().findViewById(R.id.top_bar);
//        timerLinearLayout = getActivity().findViewById(R.id.timer_ll);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);

        pointsTV.setText(String.valueOf(highScore));
        //timerLinearLayout.getLayoutParams().width = 110; // TODO - fix this static assignment
        currentScoreTV.setText("1"); // set current yourHighScoreInfinity

        hitTileExplainTV = view.findViewById(R.id.game_explain);


        initTextViews(view);
    }

    public int generateRandomNumber(int max, int min) {
        Random r = new Random();
        int randomNum = r.nextInt(max - min + 1) + min;
        return randomNum;
    }

    private void setTopBar(int showTopBar) {
        pointsTV.setVisibility(showTopBar);
        currentScoreTV.setVisibility(showTopBar);
        backToMenu.setVisibility(showTopBar);
        highScoreInfo.setVisibility(showTopBar);

        if (numberToShowOnButton == 1) {
            backToMenu.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        }
    }

    @Override
    public void onDestroy() {
        setTopBar(View.INVISIBLE);
        super.onDestroy();
    }

    private View.OnClickListener ReturnHomeListener = new View.OnClickListener() { // pause or back button
        public void onClick(View v) {
            if (mAnimationSet0 != null) {
                mAnimationSet0.pause();
            }
            if (mAnimationSet1 != null) {
                mAnimationSet1.pause();
            }
            if (mAnimationSet2 != null) {
                mAnimationSet2.pause();
            }
            if (mAnimationSet3 != null) {
                mAnimationSet3.pause();
            }

            GameOverDialog gameOverDialog = new GameOverDialog(getContext(), InfinityModeFragment.this,
                    numberToShowOnButton - 1, mAnimationSet0, mAnimationSet1, mAnimationSet2, mAnimationSet3, INFINITY);
            gameOverDialog.setCancelable(false);
            gameOverDialog.setCanceledOnTouchOutside(false);
            gameOverDialog.show();

            gameOverDialog.tryAgainButton.setVisibility(View.INVISIBLE);
            gameOverDialog.tryAgainButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

//            gameOverDialog.resumeButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT)); // set the text width and height from (match_parent,0) to (match_parent,wrap_content)

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(30,30,30,30);
            gameOverDialog.resumeButton.setLayoutParams(params);

        }
    };

    public boolean isAdLoaded() {
        if (rewardedVideoAd.isLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

