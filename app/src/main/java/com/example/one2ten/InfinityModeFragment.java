package com.example.one2ten;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Random;


public class InfinityModeFragment extends Fragment {

    private int MAX_BUTTON = 31;
    private int MIN_BUTTON = 0;
    private int DURATION_OF_ALPHA = 3000;
    private int currentRandomPlace;
    private int randomColor;
    private int probability;
    private int numberToShowOnButton = 1;
    private int fakeTilePosition;
    private int highScore;

    private SharedPreferences highScoreInfSharedPref;

    private AnimatorSet mAnimationSet;

    private LinearLayout topBar;

    private TextView pointsTV;
    private TextView currentScoreTV;
    private TextView timeInfo;
    private TextView highScoreInfo;

    private ImageButton backToMenu;

    private boolean isFirstClick;

    private Chronometer stopWatch;

    private StopWatch stopper;

    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31};

    int[] colorsArray = new int[]{R.color.menuRed, R.color.menuWhite, R.color.menuYellow, R.color.OliveDrab, R.color.SteelBlue,
            R.color.MediumPurple, R.color.MediumOrchid, R.color.MediumSpringGreen, R.color.MediumVioletRed, R.color.MediumSeaGreen};

    ArrayList<TextView> textViewArrayList = new ArrayList<TextView>(ids.length);

    public InfinityModeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_mode, container, false);

        highScoreInfSharedPref = this.getActivity().getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);
        highScore = highScoreInfSharedPref.getInt("highScoreInfinity", 0000);

        isFirstClick = true;

        bindGridViews(view);

        setTopBar(View.VISIBLE);

        stopper = new StopWatch(true, stopWatch); // class that it's a stop watch

        currentRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

        backToMenu.setOnClickListener(ReturnHomeListener);

        textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(R.color.white));
        textViewArrayList.get(currentRandomPlace).setText(String.valueOf(numberToShowOnButton++));
        textViewArrayList.get(currentRandomPlace).setTextSize(40);

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

                    textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(R.color.transparent));
                    textViewArrayList.get(currentRandomPlace).setText("");

                    if (fakeTilePosition > 0) {
                        textViewArrayList.get(fakeTilePosition).getBackground().setTint(getResources().getColor(R.color.transparent));
                        textViewArrayList.get(fakeTilePosition).setText("");
                    }


                    Object tapOfButtonPressed = view.getTag();

                    if (tapOfButtonPressed.equals(String.valueOf(currentRandomPlace))) {

                        DifficultyOfGame difficultyOfGame = new DifficultyOfGame(numberToShowOnButton);
                        DURATION_OF_ALPHA = difficultyOfGame.getDuration();
                        probability = difficultyOfGame.getProbability();

                        stopper.startStopWatch();

                        currentScoreTV.setText(String.valueOf(numberToShowOnButton - 1)); // set current score

                        if (numberToShowOnButton > highScore) { // new high score!!!
                            pointsTV.setText(String.valueOf(numberToShowOnButton));
                        }

                        currentRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                        randomColor = generateRandomNumber(colorsArray.length - 1, 0);

                        fadeOutButton(currentRandomPlace);

                        // create the current number in square
                        textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(colorsArray[randomColor]));
                        textViewArrayList.get(currentRandomPlace).setText(String.valueOf(numberToShowOnButton++));
                        textViewArrayList.get(currentRandomPlace).setTextSize(30);

                        if (generateRandomNumber(100, 0) < probability) {
                            fakeTilePosition = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);
                            textViewArrayList.get(fakeTilePosition).getBackground().setTint(getResources().getColor(colorsArray[randomColor - 1]));
                            textViewArrayList.get(fakeTilePosition).setText(String.valueOf(generateRandomNumber(numberToShowOnButton - 1, 1)));
                            textViewArrayList.get(fakeTilePosition).setTextSize(30);
                        } else {
                            fakeTilePosition = -1;
                        }


                    } else {
                        loseGame(false);
                    }
                }
            });
        }
    }

    public void loseGame(boolean isPressedReturn) {

        if (!isPressedReturn) {

            if (mAnimationSet != null) {
                mAnimationSet.removeAllListeners();
                mAnimationSet.end();
                mAnimationSet.cancel();
            }

            if (numberToShowOnButton - 1 > highScore) { // new high score!!!
                SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
                editor.putInt("highScoreInfinity", numberToShowOnButton - 1);
                editor.commit();

            }
        }

        stopper.stopStopWatch();

        currentScoreTV.setText("0");

        GameOverDialog gameOverDialog = new GameOverDialog(getContext(), getActivity(), numberToShowOnButton - 1);
        gameOverDialog.show();

        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getActivity().getSupportFragmentManager().popBackStack();
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
        stopWatch = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);

        pointsTV.setText(String.valueOf(highScore));

        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);

        currentScoreTV.setText("0"); // set current score

        initTextViews(view);
    }

    public void StartTimer() {
        stopWatch.start();
    }

    public int generateRandomNumber(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


    private void setTopBar(int showTopBar) {

        stopWatch.setVisibility(showTopBar);
        pointsTV.setVisibility(showTopBar);
        currentScoreTV.setVisibility(showTopBar);
        backToMenu.setVisibility(showTopBar);
        timeInfo.setVisibility(showTopBar);
        highScoreInfo.setVisibility(showTopBar);
    }

    @Override
    public void onDestroy() {
        setTopBar(View.INVISIBLE);
        super.onDestroy();
    }

    private View.OnClickListener ReturnHomeListener = new View.OnClickListener() {
        public void onClick(View v) {
            loseGame(true);
        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
