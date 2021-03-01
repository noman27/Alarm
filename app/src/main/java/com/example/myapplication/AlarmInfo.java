package com.example.myapplication;

public class AlarmInfo {
    public String medName;
    public int hour;
    public int min;
    public int sec;
    public long mili;
    public int id;

    public AlarmInfo()
    {

    }
    public AlarmInfo(String medName,int hour,int min,int sec,int id,long mili)
    {
        this.medName=medName;
        this.hour=hour;
        this.min=min;
        this.sec=sec;
        this.mili=mili;
        this.id=id;
    }
}
