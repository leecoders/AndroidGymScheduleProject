package com.example.hdh.smgproject;

public class PT {

    String userID;

    int ptID;
    String ptYear;
    String ptMonth;
    String ptDay;
    String ptTrainer;
    String ptTime;
    String ptDOTW;
    int FeedBackValue;


    public PT(int ptID, String ptYear, String ptMonth, String ptDay, String ptTrainer, String ptTime , String ptDOTW ) {
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTrainer = ptTrainer;
        this.ptTime = ptTime;
        this.ptDOTW = ptDOTW;
    }

    public PT(int ptID, String ptYear, String ptMonth, String ptDay, String ptTrainer, String ptTime , String ptDOTW , int FeedBackValue) {
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTrainer = ptTrainer;
        this.ptTime = ptTime;
        this.ptDOTW = ptDOTW;
        this.FeedBackValue = FeedBackValue;
    }

    public PT(String userID , int ptID, String ptYear, String ptMonth, String ptDay, String ptTime ,String ptTrainer , String ptDOTW , int FeedBackValue) {
        this.userID = userID;
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTime = ptTime;
        this.ptTrainer = ptTrainer;
        this.ptDOTW = ptDOTW;
        this.FeedBackValue = FeedBackValue;

    }

    public PT(String userID , int ptID, String ptYear, String ptMonth, String ptDay, String ptTime ,String ptTrainer , String ptDOTW ) {
        this.userID = userID;
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTime = ptTime;
        this.ptTrainer = ptTrainer;
        this.ptDOTW = ptDOTW;

    }
    public PT(int ptID, String ptYear, String ptMonth, String ptDay, String ptTime , String ptDOTW ) {
        this.ptID = ptID;
        this.ptYear = ptYear;
        this.ptMonth = ptMonth;
        this.ptDay = ptDay;
        this.ptTime = ptTime;
        this.ptDOTW = ptDOTW;
    }


    public int getPtID() {
        return ptID;
    }

    public void setPtID(int ptID) {
        this.ptID = ptID;
    }

    public String getPtYear() {
        return ptYear;
    }

    public void setPtYear(String ptYear) {
        this.ptYear = ptYear;
    }

    public String getPtMonth() {
        return ptMonth;
    }

    public void setPtMonth(String ptMonth) {
        this.ptMonth = ptMonth;
    }

    public String getPtDay() {
        return ptDay;
    }

    public void setPtDay(String ptDay) {
        this.ptDay = ptDay;
    }

    public String getPtTrainer() {
        return ptTrainer;
    }

    public void setPtTrainer(String ptTrainer) {
        this.ptTrainer = ptTrainer;
    }

    public String getPtTime() {
        return ptTime;
    }

    public void setPtTime(String ptTime) {
        this.ptTime = ptTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPtDOTW() {
        return ptDOTW;
    }

    public void setPtDOTW(String ptDOTW) {
        this.ptDOTW = ptDOTW;
    }

    public int getFeedBackValue() {
        return FeedBackValue;
    }

    public void setFeedBackValue(int feedBackValue) {
        FeedBackValue = feedBackValue;
    }
}
