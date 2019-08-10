package com.example.one2ten;

public class numberSquare {

    public int rowCount;
    public int colCount;
    public int number;
    public int rowPosition;
    public int colPosition;
    public boolean correctSquare;

    public numberSquare(int rowCount, int colCount,int rowPosition, int colPosition, int number, boolean correctSquare) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.number = number;
        this.correctSquare = correctSquare;
    }


}
