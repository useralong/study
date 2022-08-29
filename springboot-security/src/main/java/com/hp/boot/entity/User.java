package com.hp.boot.entity;

import java.util.Date;

public class User {
    private Integer uId;

    private String uLoginName;

    private String uUserName;

    private String uPwd;

    private Date uBirthday;

    private Date uEntryTime;

    private Integer uIsdelete;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuLoginName() {
        return uLoginName;
    }

    public void setuLoginName(String uLoginName) {
        this.uLoginName = uLoginName;
    }

    public String getuUserName() {
        return uUserName;
    }

    public void setuUserName(String uUserName) {
        this.uUserName = uUserName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public Date getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(Date uBirthday) {
        this.uBirthday = uBirthday;
    }

    public Date getuEntryTime() {
        return uEntryTime;
    }

    public void setuEntryTime(Date uEntryTime) {
        this.uEntryTime = uEntryTime;
    }

    public Integer getuIsdelete() {
        return uIsdelete;
    }

    public void setuIsdelete(Integer uIsdelete) {
        this.uIsdelete = uIsdelete;
    }
}