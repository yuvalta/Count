package com.example.one2ten;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TimerModeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    LinearLayout topBar;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;

    TextView cell0;

    ImageButton backToMenu;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_mode, container, false);

        bindGridViews();

//        topBar = getActivity().findViewById(R.id.top_bar);
//        topBar.setBackgroundResource(R.color.background_gradient_end);
//
//        timerTV = getActivity().findViewById(R.id.timerTV);
//        pointsTV = getActivity().findViewById(R.id.pointsTV);
//        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
//        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
//        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
//        timeInfo = getActivity().findViewById(R.id.timer_infoTV);

        setTopBar(View.VISIBLE);

        backToMenu.setOnClickListener(ReturnHomeListener);



        return view;
    }

    private void setTopBar(int showTopBar) {
        timerTV.setVisibility(showTopBar);
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
            FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MainMenuFragment mainMenuFragment = new MainMenuFragment();
            fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
            fragmentTransaction.commit();
        }
    };

    public void bindGridViews() {

        topBar = getActivity().findViewById(R.id.top_bar);
        topBar.setBackgroundResource(R.color.background_gradient_end);

        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);


    }


    public TimerModeFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
