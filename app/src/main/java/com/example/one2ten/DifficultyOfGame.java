package com.example.one2ten;

import android.util.Log;

public class DifficultyOfGame {

    private int LEVEL_1 = 3000;
    private int LEVEL_2 = 2000;
    private int LEVEL_3 = 1750;
    private int LEVEL_4 = 1500;
    private int LEVEL_5 = 1250;
    private int LEVEL_6 = 1000;
    private int LEVEL_7 = 800;
    private int LEVEL_8 = 700;
    private int LEVEL_9 = 500;
    private int LEVEL_10 = 400;
    private int LEVEL_11 = 300;
    private int LEVEL_12 = 200;
    private int LEVEL_13 = 100;
    private int LEVEL_14 = 50;
//    private int LEVEL_15 = 500;
//    private int LEVEL_16 = 500;
//    private int LEVEL_17 = 500;
//    private int LEVEL_18 = 500;
//    private int LEVEL_19 = 500;
//    private int LEVEL_20 = 500;

    public int duration;
    public int level;

    public DifficultyOfGame(int duration, int level) {
        this.duration = duration;
        this.level = level;
    }

    public int getDurationDevideByTen() {

        Log.d("ggg", "");
        switch (level / 10) {
            case 0:
                return LEVEL_1;
            case 1:
                return LEVEL_1;
            case 2:
                return LEVEL_2;
            case 3:
                return LEVEL_3;
            case 4:
                return LEVEL_4;
            case 5:
                return LEVEL_5;
            case 6:
                return LEVEL_6;
            case 7:
                return LEVEL_7;
            case 8:
                return LEVEL_8;
            case 9:
                return LEVEL_9;
            case 10:
                return LEVEL_9;
            case 11:
                return LEVEL_10;
            case 12:
                return LEVEL_11;
            case 13:
                return LEVEL_12;
            case 14:
                return LEVEL_13;
            default:
                return LEVEL_14;
        }
    }
}
