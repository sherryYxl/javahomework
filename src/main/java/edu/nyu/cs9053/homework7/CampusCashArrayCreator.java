package edu.nyu.cs9053.homework7;

public class CampusCashArrayCreator implements ArrayCreator {

    @Override public CampusCash[] create(int size) {
        return new CampusCash[size];
    }
}