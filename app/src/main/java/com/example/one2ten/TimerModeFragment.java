package com.example.one2ten;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TimerModeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    LinearLayout topBar;

    TextView timerTV;
    TextView pointsTV;
    TextView currentScoreTV;
    TextView timeInfo;
    TextView highScoreInfo;

    ImageButton backToMenu;

//    private TextView cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10;
//    private TextView cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20;
//    private TextView cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28, cell29, cell30;
//    private TextView cell31, cell32, cell33, cell34, cell35, cell36, cell37, cell38, cell39, cell40;
//    private TextView cell41, cell42, cell43, cell44, cell45, cell46, cell47, cell48, cell49, cell50;
//    private TextView cell51, cell52, cell53, cell54, cell55, cell56, cell57, cell58, cell59, cell60;
//    private TextView cell61, cell62, cell63, cell64, cell65, cell66, cell67, cell68, cell69, cell70;
//    private TextView cell71, cell72, cell73, cell74, cell75, cell76, cell77, cell78, cell79, cell80;
//    private TextView cell81, cell82, cell83, cell84, cell85, cell86, cell87, cell88, cell89, cell90;
//    private TextView cell91, cell92, cell93, cell94, cell95, cell96, cell97, cell98, cell99, cell100;
//    private TextView cell101, cell102, cell103, cell104, cell105, cell106, cell107, cell108, cell109;
//    private TextView cell110, cell111, cell112, cell113, cell114, cell115, cell116, cell117, cell118;
//    private TextView cell119, cell120, cell121, cell122, cell123, cell124, cell125, cell126, cell127;

    int[] ids = new int[]{R.id.cell0, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10,
            R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20,
            R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30,
            R.id.cell31, R.id.cell32, R.id.cell33, R.id.cell34, R.id.cell35, R.id.cell36, R.id.cell37, R.id.cell38, R.id.cell39, R.id.cell40,
            R.id.cell41, R.id.cell42, R.id.cell43, R.id.cell44, R.id.cell45, R.id.cell46, R.id.cell47, R.id.cell48, R.id.cell49, R.id.cell50,
            R.id.cell51, R.id.cell52, R.id.cell53, R.id.cell54, R.id.cell55, R.id.cell56, R.id.cell57, R.id.cell58, R.id.cell59, R.id.cell60,
            R.id.cell61, R.id.cell62, R.id.cell63, R.id.cell64, R.id.cell65, R.id.cell66, R.id.cell67, R.id.cell68, R.id.cell69, R.id.cell70,
            R.id.cell71, R.id.cell72, R.id.cell73, R.id.cell74, R.id.cell75, R.id.cell76, R.id.cell77, R.id.cell78, R.id.cell79, R.id.cell80,
            R.id.cell81, R.id.cell82, R.id.cell83, R.id.cell84, R.id.cell85, R.id.cell86, R.id.cell87, R.id.cell88, R.id.cell89, R.id.cell90,
            R.id.cell91, R.id.cell92, R.id.cell93, R.id.cell94, R.id.cell95, R.id.cell96, R.id.cell97, R.id.cell98, R.id.cell99, R.id.cell100,
            R.id.cell101, R.id.cell102, R.id.cell103, R.id.cell104, R.id.cell105, R.id.cell106, R.id.cell107, R.id.cell108, R.id.cell109,
            R.id.cell110, R.id.cell111, R.id.cell112, R.id.cell113, R.id.cell114, R.id.cell115, R.id.cell116, R.id.cell117, R.id.cell118,
            R.id.cell119, R.id.cell120, R.id.cell121, R.id.cell122, R.id.cell123, R.id.cell124, R.id.cell125, R.id.cell126, R.id.cell127};

    ArrayList<TextView> listArray = new ArrayList<TextView>(ids.length);

//    private ArrayList<View> listArray = new ArrayList<View>(Arrays.asList(cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10,
//            cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20,
//            cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28, cell29, cell30,
//            cell31, cell32, cell33, cell34, cell35, cell36, cell37, cell38, cell39, cell40,
//            cell41, cell42, cell43, cell44, cell45, cell46, cell47, cell48, cell49, cell50,
//            cell51, cell52, cell53, cell54, cell55, cell56, cell57, cell58, cell59, cell60,
//            cell61, cell62, cell63, cell64, cell65, cell66, cell67, cell68, cell69, cell70,
//            cell71, cell72, cell73, cell74, cell75, cell76, cell77, cell78, cell79, cell80,
//            cell81, cell82, cell83, cell84, cell85, cell86, cell87, cell88, cell89, cell90,
//            cell91, cell92, cell93, cell94, cell95, cell96, cell97, cell98, cell99, cell100,
//            cell101, cell102, cell103, cell104, cell105, cell106, cell107, cell108, cell109,
//            cell110, cell111, cell112, cell113, cell114, cell115, cell116, cell117, cell118,
//            cell119, cell120, cell121, cell122, cell123, cell124, cell125, cell126, cell127));

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
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MainMenuFragment mainMenuFragment = new MainMenuFragment();
            fragmentTransaction.add(R.id.frame_layout_for_fragments, mainMenuFragment);
            fragmentTransaction.commit();
        }
    };

    public void bindGridViews(View view) {

        // topBar binding
        topBar = getActivity().findViewById(R.id.top_bar);
        topBar.setBackgroundResource(R.color.background_gradient_end);
        timerTV = getActivity().findViewById(R.id.timerTV);
        pointsTV = getActivity().findViewById(R.id.pointsTV);
        backToMenu = getActivity().findViewById(R.id.back_to_menu_btn);
        currentScoreTV = getActivity().findViewById(R.id.current_scoreTV);
        highScoreInfo = getActivity().findViewById(R.id.high_score_infoTV);
        timeInfo = getActivity().findViewById(R.id.timer_infoTV);

        //cells binding TODO - loop!
//        cell0 = view.findViewById(R.id.cell0);
//        cell1 = view.findViewById(R.id.cell1);
//        cell2 = view.findViewById(R.id.cell2);
//        cell3 = view.findViewById(R.id.cell3);
//        cell4 = view.findViewById(R.id.cell4);
//        cell5 = view.findViewById(R.id.cell5);
//        cell6 = view.findViewById(R.id.cell6);
//        cell7 = view.findViewById(R.id.cell7);
//        cell8 = view.findViewById(R.id.cell8);
//        cell9 = view.findViewById(R.id.cell9);
//        cell10 = view.findViewById(R.id.cell10);
//        cell11 = view.findViewById(R.id.cell11);
//        cell12 = view.findViewById(R.id.cell12);
//        cell13 = view.findViewById(R.id.cell13);
//        cell14 = view.findViewById(R.id.cell14);
//        cell15 = view.findViewById(R.id.cell15);
//        cell16 = view.findViewById(R.id.cell16);
//        cell17 = view.findViewById(R.id.cell17);
//        cell18 = view.findViewById(R.id.cell18);
//        cell19 = view.findViewById(R.id.cell19);
//        cell20 = view.findViewById(R.id.cell20);
//        cell21 = view.findViewById(R.id.cell21);
//        cell22 = view.findViewById(R.id.cell22);
//        cell23 = view.findViewById(R.id.cell23);
//        cell24 = view.findViewById(R.id.cell24);
//        cell25 = view.findViewById(R.id.cell25);
//        cell26 = view.findViewById(R.id.cell26);
//        cell27 = view.findViewById(R.id.cell27);
//        cell28 = view.findViewById(R.id.cell28);
//        cell29 = view.findViewById(R.id.cell29);
//        cell30 = view.findViewById(R.id.cell30);
//        cell31 = view.findViewById(R.id.cell31);
//        cell32 = view.findViewById(R.id.cell32);
//        cell33 = view.findViewById(R.id.cell33);
//        cell34 = view.findViewById(R.id.cell34);
//        cell35 = view.findViewById(R.id.cell35);
//        cell36 = view.findViewById(R.id.cell36);
//        cell37 = view.findViewById(R.id.cell37);
//        cell38 = view.findViewById(R.id.cell38);
//        cell39 = view.findViewById(R.id.cell39);
//        cell40 = view.findViewById(R.id.cell40);
//        cell41 = view.findViewById(R.id.cell41);
//        cell42 = view.findViewById(R.id.cell42);
//        cell43 = view.findViewById(R.id.cell43);
//        cell44 = view.findViewById(R.id.cell44);
//        cell45 = view.findViewById(R.id.cell45);
//        cell46 = view.findViewById(R.id.cell46);
//        cell47 = view.findViewById(R.id.cell47);
//        cell48 = view.findViewById(R.id.cell48);
//        cell49 = view.findViewById(R.id.cell49);
//        cell50 = view.findViewById(R.id.cell50);
//        cell51 = view.findViewById(R.id.cell51);
//        cell52 = view.findViewById(R.id.cell52);
//        cell53 = view.findViewById(R.id.cell53);
//        cell54 = view.findViewById(R.id.cell54);
//        cell55 = view.findViewById(R.id.cell55);
//        cell56 = view.findViewById(R.id.cell56);
//        cell57 = view.findViewById(R.id.cell57);
//        cell58 = view.findViewById(R.id.cell58);
//        cell59 = view.findViewById(R.id.cell59);
//        cell60 = view.findViewById(R.id.cell60);
//        cell61 = view.findViewById(R.id.cell61);
//        cell62 = view.findViewById(R.id.cell62);
//        cell63 = view.findViewById(R.id.cell63);
//        cell64 = view.findViewById(R.id.cell64);
//        cell65 = view.findViewById(R.id.cell65);
//        cell66 = view.findViewById(R.id.cell66);
//        cell67 = view.findViewById(R.id.cell67);
//        cell68 = view.findViewById(R.id.cell68);
//        cell69 = view.findViewById(R.id.cell69);
//        cell70 = view.findViewById(R.id.cell70);
//        cell71 = view.findViewById(R.id.cell71);
//        cell72 = view.findViewById(R.id.cell72);
//        cell73 = view.findViewById(R.id.cell73);
//        cell74 = view.findViewById(R.id.cell74);
//        cell75 = view.findViewById(R.id.cell75);
//        cell76 = view.findViewById(R.id.cell76);
//        cell77 = view.findViewById(R.id.cell77);
//        cell78 = view.findViewById(R.id.cell78);
//        cell79 = view.findViewById(R.id.cell79);
//        cell80 = view.findViewById(R.id.cell80);
//        cell81 = view.findViewById(R.id.cell81);
//        cell82 = view.findViewById(R.id.cell82);
//        cell83 = view.findViewById(R.id.cell83);
//        cell84 = view.findViewById(R.id.cell84);
//        cell85 = view.findViewById(R.id.cell85);
//        cell86 = view.findViewById(R.id.cell86);
//        cell87 = view.findViewById(R.id.cell87);
//        cell88 = view.findViewById(R.id.cell88);
//        cell89 = view.findViewById(R.id.cell89);
//        cell90 = view.findViewById(R.id.cell90);
//        cell91 = view.findViewById(R.id.cell91);
//        cell92 = view.findViewById(R.id.cell92);
//        cell93 = view.findViewById(R.id.cell93);
//        cell94 = view.findViewById(R.id.cell94);
//        cell95 = view.findViewById(R.id.cell95);
//        cell96 = view.findViewById(R.id.cell96);
//        cell97 = view.findViewById(R.id.cell97);
//        cell98 = view.findViewById(R.id.cell98);
//        cell99 = view.findViewById(R.id.cell99);
//        cell100 = view.findViewById(R.id.cell100);
//        cell101 = view.findViewById(R.id.cell101);
//        cell102 = view.findViewById(R.id.cell102);
//        cell103 = view.findViewById(R.id.cell103);
//        cell104 = view.findViewById(R.id.cell104);
//        cell105 = view.findViewById(R.id.cell105);
//        cell106 = view.findViewById(R.id.cell106);
//        cell107 = view.findViewById(R.id.cell107);
//        cell108 = view.findViewById(R.id.cell108);
//        cell109 = view.findViewById(R.id.cell109);
//        cell110 = view.findViewById(R.id.cell110);
//        cell111 = view.findViewById(R.id.cell111);
//        cell112 = view.findViewById(R.id.cell112);
//        cell113 = view.findViewById(R.id.cell113);
//        cell114 = view.findViewById(R.id.cell114);
//        cell115 = view.findViewById(R.id.cell115);
//        cell116 = view.findViewById(R.id.cell116);
//        cell117 = view.findViewById(R.id.cell117);
//        cell118 = view.findViewById(R.id.cell118);
//        cell119 = view.findViewById(R.id.cell119);
//        cell120 = view.findViewById(R.id.cell120);
//        cell121 = view.findViewById(R.id.cell121);
//        cell122 = view.findViewById(R.id.cell122);
//        cell123 = view.findViewById(R.id.cell123);
//        cell124 = view.findViewById(R.id.cell124);
//        cell125 = view.findViewById(R.id.cell125);
//        cell126 = view.findViewById(R.id.cell126);
//        cell127 = view.findViewById(R.id.cell127);

        initTextViews(view);
    }

    private void initTextViews(View view) {
        for (int i = 0; i < ids.length; i++) {
            TextView currentTextView = view.findViewById(ids[i]);
            listArray.add(currentTextView);
        }

    }


    public TimerModeFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
