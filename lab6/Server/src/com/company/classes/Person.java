package com.company.classes;

import com.company.Enums.*;

public class Person implements Comparable<Person>{
    private String name;
    private String passportID;
    private ColorY eyeColor;
    private ColorH hairColor;
    private Country nationality;

    public Person(String name, String passportID, ColorY eyeColor, ColorH hairColor,Country nationality){
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorY getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(ColorY eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public ColorH getHairColor() {
        return hairColor;
    }

    public void setHairColor(ColorH hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
