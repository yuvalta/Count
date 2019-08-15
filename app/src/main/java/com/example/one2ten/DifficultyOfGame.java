package com.example.one2ten;

import android.util.Log;

public class DifficultyOfGame {

    private int LEVEL_1 = 3000;
    private int LEVEL_2 = 2500;
    private int LEVEL_3 = 2200;
    private int LEVEL_4 = 1800;
    private int LEVEL_5 = 1750;
    private int LEVEL_6 = 1500;
    private int LEVEL_7 = 1250;
    private int LEVEL_8 = 1100;
    private int LEVEL_9 = 1000;
    private int LEVEL_10 = 900;
    private int LEVEL_11 = 850;
    private int LEVEL_12 = 750;
    private int LEVEL_13 = 700;
    private int LEVEL_14 = 650;
//    private int LEVEL_15 = 500;
//    private int LEVEL_16 = 500;
//    private int LEVEL_17 = 500;
//    private int LEVEL_18 = 500;
//    private int LEVEL_19 = 500;
//    private int LEVEL_20 = 500;

    public int level;
    public int duration;
    public int probability;

    public DifficultyOfGame(int level) {
        this.level = level;
    }

    public void getLevel(int level) {
        switch (level / 10) {
            case 0:
                probability = 5;
                duration = LEVEL_1;
                break;
            case 1:
                probability = 10;
                duration = LEVEL_1;
                break;
            case 2:
                probability = 20;
                duration = LEVEL_2;
                break;
            case 3:
                probability = 30;
                duration = LEVEL_3;
                break;
            case 4:
                probability = 50;
                duration = LEVEL_4;
                break;
            case 5:
                probability = 60;
                duration = LEVEL_5;
                break;
            case 6:
                probability = 60;
                duration = LEVEL_6;
                break;
            case 7:
                probability = 70;
                duration = LEVEL_7;
                break;
            case 8:
                probability = 70;
                duration = LEVEL_8;
                break;
            case 9:
                probability = 70;
                duration = LEVEL_9;
                break;
            case 10:
                probability = 70;
                duration = LEVEL_9;
                break;
            case 11:
                probability = 70;
                duration = LEVEL_10;
                break;
            case 12:
                probability = 80;
                duration = LEVEL_11;
                break;
            case 13:
                probability = 80;
                duration = LEVEL_12;
                break;
            case 14:
                probability = 90;
                duration = LEVEL_13;
                break;
            default:
                duration = LEVEL_14;
                break;
        }
    }


    public int getDuration() {
        getLevel(level);
        return duration;
    }

    public int getProbability() {
        getLevel(level);
        return probability;
    }
}
