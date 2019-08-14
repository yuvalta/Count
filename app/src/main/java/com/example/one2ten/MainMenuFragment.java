package com.example.one2ten;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class MainMenuFragment extends Fragment {
    private TimerModeFragment.OnFragmentInteractionListener mListener;

    LinearLayout topBar;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;

    Button timerModeButton;
    Button infinityModeButton;
    Button backwardModeButton;
    ImageButton backToMenu;

    boolean isPressedBackTwice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        isPressedBackTwice = false;

        topBar = getActivity().findViewById(R.id.top_bar);
        topBar.setBackgroundResource(R.color.background_gradient_end);

        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);

        timerModeButton = view.findViewById(R.id.timer_mode_button);
        infinityModeButton = view.findViewById(R.id.infinity_mode_button);
        backwardModeButton = view.findViewById(R.id.backward_mode_button);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);

        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);

        timerModeButton.setOnClickListener(modeSelectionListener);
        infinityModeButton.setOnClickListener(modeSelectionListener);
        backwardModeButton.setOnClickListener(modeSelectionListener);

        setTopBar(View.INVISIBLE);

        return view;

    }

    private View.OnClickListener modeSelectionListener = new View.OnClickListener() {
        public void onClick(View v) {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (v.getId()) {
                case R.id.timer_mode_button:

                    break;

                case R.id.infinity_mode_button:

                    TimerModeFragment timerModeFragment = new TimerModeFragment();
                    fragmentTransaction.add(R.id.frame_layout_for_fragments, timerModeFragment).addToBackStack("TimerFragment");
                    fragmentTransaction.commit();

                    break;

                case R.id.backward_mode_button:

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

//    @Override
//    public void onDestroy() {
//        if(!isPressedBackTwice) {
//            Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_LONG).show();
//            isPressedBackTwice = true;
//        }
//        else {
//            super.onDestroy();
//        }
//    }

    public MainMenuFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
