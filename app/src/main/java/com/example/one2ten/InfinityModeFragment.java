package com.example.one2ten;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class InfinityModeFragment extends Fragment {

    int MAX_BUTTON = 31;
    int MIN_BUTTON = 0;
    int DURATION_OF_ALPHA = 3000;

    int highScore;

    SharedPreferences highScoreInfSharedPref;

    AnimatorSet mAnimationSet;

    LinearLayout topBar;

    int currentRandomPlace;
    int numberToShowOnButton = 1;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;

    ImageButton backToMenu;

    boolean isFirstClick;

    Chronometer stopWatch;

    StopWatch stopper;

    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31};

    ArrayList<TextView> textViewArrayList = new ArrayList<TextView>(ids.length);

    public InfinityModeFragment() {

//        this.highScoreInfSharedPref = this.getActivity().getSharedPreferences("highScoreInfinity", Context.MODE_PRIVATE);
//        this.highScore = highScoreInfSharedPref.getInt("highScoreInfinity", 0000);
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

                    Object tapOfButtonPressed = view.getTag();

                    if (tapOfButtonPressed.equals(String.valueOf(currentRandomPlace))) {


                        DifficultyOfGame difficultyOfGame = new DifficultyOfGame(DURATION_OF_ALPHA, numberToShowOnButton);
                        DURATION_OF_ALPHA = difficultyOfGame.getDurationDevideByTen();

                        stopper.startStopWatch();

                        currentScoreTV.setText(String.valueOf(numberToShowOnButton)); // set current score

                        currentRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

                        fadeOutButton(currentRandomPlace);


                        // create the current number in square
                        textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(R.color.white));
                        textViewArrayList.get(currentRandomPlace).setText(String.valueOf(numberToShowOnButton++));
                        textViewArrayList.get(currentRandomPlace).setTextSize(30);


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

            if (numberToShowOnButton > highScore) { // new high score!!!
                SharedPreferences.Editor editor = highScoreInfSharedPref.edit();
                editor.putInt("highScoreInfinity", numberToShowOnButton);
                editor.commit();

                Toast.makeText(getActivity(), "New High Score!!! - " + numberToShowOnButton, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "You LOSE!", Toast.LENGTH_SHORT).show();
            }
        }

//        stopWatch.stop();
//        stopWatch.setBase(SystemClock.elapsedRealtime());

        stopper.stopStopWatch();

        currentScoreTV.setText("1");

        GameOverDialog gameOverDialog = new GameOverDialog(getContext(), getActivity());
        gameOverDialog.show();

        if (gameOverDialog.returnOrRetry) {
            gameOverDialog.dismiss();
        }
        else {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
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
//        topBar.setBackgroundResource(R.color.background_gradient_end);
        stopWatch = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);

        pointsTV.setText(String.valueOf(highScore));

        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);

        currentScoreTV.setText("1"); // set current score

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
