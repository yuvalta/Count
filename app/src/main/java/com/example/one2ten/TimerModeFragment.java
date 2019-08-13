package com.example.one2ten;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
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


public class TimerModeFragment extends Fragment {

    public TimerModeFragment() {
        // Required empty public constructor
    }

//    private OnFragmentInteractionListener mListener;

    int MAX_BUTTON = 31;
    int MIN_BUTTON = 0;

    Timer timer = new Timer();

    LinearLayout topBar;

    int currentRandomPlace;
    int numberToShowOnButton = 1;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;

    ImageButton backToMenu;

    boolean isFirstClick = true;

    Chronometer stopWatch;

    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31};

    ArrayList<TextView> textViewArrayList = new ArrayList<TextView>(ids.length);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_mode, container, false);


        bindGridViews(view);

        setTopBar(View.VISIBLE);

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
        }

        for (int i = 0; i < ids.length; i++) { // set listeners to all buttons

            textViewArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(R.color.transparent));
                    textViewArrayList.get(currentRandomPlace).setText("");

                    int previousRandomPlace = currentRandomPlace;

                    Object tapOfButtonPressed = view.getTag();

                    if (tapOfButtonPressed.equals(String.valueOf(currentRandomPlace))) {

                        startStopWatch();

                        currentScoreTV.setText(String.valueOf(numberToShowOnButton)); // set current score

                        currentRandomPlace = generateRandomNumber(MAX_BUTTON, MIN_BUTTON);

//                        fadeOutButton(currentRandomPlace, previousRandomPlace, tapOfButtonPressed);

                        // create the current number in square
                        textViewArrayList.get(currentRandomPlace).getBackground().setTint(getResources().getColor(R.color.white));
                        textViewArrayList.get(currentRandomPlace).setText(String.valueOf(numberToShowOnButton++));
                        textViewArrayList.get(currentRandomPlace).setTextSize(30);

                    } else {

                        loseGame();

                    }
                }
            });
        }
    }

    public void loseGame() {
//        for (int i = 0; i < textViewArrayList.size(); i++) {
//            textViewArrayList.get(i).getBackground().setTint(getResources().getColor(R.color.transparent));
//            textViewArrayList.get(i).setText("");
//        }
        currentScoreTV.setText("1");

        stopWatch.stop();
        stopWatch.setBase(SystemClock.elapsedRealtime());

        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public void startStopWatch() {
        if (isFirstClick) { // if it's the first click start stop watch
            stopWatch.setBase(SystemClock.elapsedRealtime());
            StartTimer();
            isFirstClick = false;
        }
    }

    public void fadeOutButton(final int currentPlaceOfButton, final int previousRandomPlace, final Object tapOfButtonPressed) {

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textViewArrayList.get(currentPlaceOfButton), "alpha", 1f, 0f);
        fadeOut.setDuration(5000);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (tapOfButtonPressed.equals(String.valueOf(currentPlaceOfButton))) {
                    Toast.makeText(getActivity(), tapOfButtonPressed.toString() + " " + previousRandomPlace, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getActivity(), tapOfButtonPressed.toString() + " -- " + previousRandomPlace, Toast.LENGTH_SHORT).show();

                    loseGame();
                }
            }
        });
        mAnimationSet.start();
    }

    public void bindGridViews(View view) {

        // topBar binding
        topBar = getActivity().findViewById(R.id.top_bar);
        topBar.setBackgroundResource(R.color.background_gradient_end);
        stopWatch = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MainMenuFragment mainMenuFragment = new MainMenuFragment();
            fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
            fragmentTransaction.commit();
        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
