package com.wubei_card;

/**
 * Created by wu on 2017/10/15.
 */

public class Money {
    private String date;
    private String smallmoney;
    private String place;
    private String totalmoney;

    public Money(String date, String smallmoney, String place, String totalmoney){
        this.date = date;
        this.smallmoney = smallmoney;
        this.place = place;
        this.totalmoney = totalmoney;
    }
    public String getDate(){
        return date;
    }
    public String getSmallmoney(){
        return smallmoney;
    }
    public String getPlace(){
        return place;
    }
    public String getTotalmoney(){
        return totalmoney;
    }
}
