package com.example.reveil.models;

public class Reveil {
    int id;
    String hour, minute;
    Boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday, start;

    public Reveil (int id, String hour, String minute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Boolean start) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.start = start;
    }

    public Reveil () {

    }
    public void setId(int id) {
        this.id = id;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public Boolean isMonday() {
        return monday;
    }

    public Boolean isTuesday() {
        return tuesday;
    }

    public Boolean isWednesday() {
        return wednesday;
    }

    public Boolean isThursday() {
        return thursday;
    }

    public Boolean isFriday() {
        return friday;
    }

    public Boolean isSaturday() {
        return saturday;
    }

    public Boolean isSunday() {
        return sunday;
    }

    public Boolean isStart() {
        return start;
    }
}
