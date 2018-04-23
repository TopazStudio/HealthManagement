package com.flycode.healthbloom.models;

import java.util.Calendar;

import lombok.Data;

@Data
public class User {
    private int Id;
    private String Fullname;
    private String Gender;
    private Calendar DOB;
    private int Age;
    private int InitWeight;
    private int InitHeight;
}
