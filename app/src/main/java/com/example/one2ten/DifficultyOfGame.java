package com.example.one2ten;

public class DifficultyOfGame {

    private int LEVEL_1 = 3000;
    private int LEVEL_2 = 2500;
    private int LEVEL_3 = 2200;
    private int LEVEL_4 = 2000;
    private int LEVEL_5 = 1900;
    private int LEVEL_6 = 1700;
    private int LEVEL_7 = 1500;
    private int LEVEL_8 = 1400;
    private int LEVEL_9 = 1000;
    private int LEVEL_10 = 900;
    private int LEVEL_11 = 800;
    private int LEVEL_12 = 800;
    private int LEVEL_13 = 700;
    private int LEVEL_14 = 700;
    private int LEVEL_15 = 700;

//    private int LEVEL_1 = 6000;// test
//    private int LEVEL_2 = 6000;
//    private int LEVEL_3 = 6000;
//    private int LEVEL_4 = 6000;
//    private int LEVEL_5 = 6000;
//    private int LEVEL_6 = 6000;
//    private int LEVEL_7 = 6000;
//    private int LEVEL_8 = 6000;
//    private int LEVEL_9 = 6000;
//    private int LEVEL_10 = 6000;
//    private int LEVEL_11 = 6000;
//    private int LEVEL_12 = 6000;
//    private int LEVEL_13 = 6000;
//    private int LEVEL_14 = 6000;
//    private int LEVEL_15 = 6000;
//    private int LEVEL_16 = 500;
//    private int LEVEL_17 = 500;
//    private int LEVEL_18 = 500;
//    private int LEVEL_19 = 500;
//    private int LEVEL_20 = 500;

    private int PROB_1 = 0;
    private int PROB_2 = 40;
    private int PROB_3 = 50;
    private int PROB_4 = 60;
    private int PROB_5 = 70;
    private int PROB_6 = 70;
    private int PROB_7 = 70;
    private int PROB_8 = 80;
    private int PROB_9 = 90;
    private int PROB_10 = 100;
    private int PROB_11 = 100;
    private int PROB_12 = 100;
    private int PROB_13 = 100;
    private int PROB_14 = 100;
    private int PROB_15 = 100;

    public int level;
    public int duration;
    public int probability;

    public DifficultyOfGame(int level) {
        this.level = level;
    }

    public void getLevel(int level) {
        switch (level / 10) {
            case 0:
                probability = PROB_1;
                duration = LEVEL_1;
                break;
            case 1:
                probability = PROB_1;
                duration = LEVEL_1;
                break;
            case 2:
                probability = PROB_2;
                duration = LEVEL_2;
                break;
            case 3:
                probability = PROB_3;
                duration = LEVEL_3;
                break;
            case 4:
                probability = PROB_4;
                duration = LEVEL_4;
                break;
            case 5:
                probability = PROB_5;
                duration = LEVEL_5;
                break;
            case 6:
                probability = PROB_6;
                duration = LEVEL_6;
                break;
            case 7:
                probability = PROB_7;
                duration = LEVEL_7;
                break;
            case 8:
                probability = PROB_8;
                duration = LEVEL_8;
                break;
            case 9:
                probability = PROB_9;
                duration = LEVEL_9;
                break;
            case 10:
                probability = PROB_10;
                duration = LEVEL_9;
                break;
            case 11:
                probability = PROB_11;
                duration = LEVEL_10;
                break;
            case 12:
                probability = PROB_12;
                duration = LEVEL_11;
                break;
            case 13:
                probability = PROB_13;
                duration = LEVEL_12;
                break;
            case 14:
                probability = PROB_14;
                duration = LEVEL_13;
                break;
            case 15:
                probability = PROB_15;
                duration = LEVEL_14;
                break;
            default:
                probability = PROB_15;
                duration = LEVEL_15;
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
