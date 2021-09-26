package com.example.ipbeacons_mobile_app.api;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Info implements Serializable {

    private String informationText;
 /*   private int inforamtionID;
    private String datetime;
    private int beaconID;
    public int getInforamtionID() {
        return inforamtionID;
    }



    public void setInforamtionID(int inforamtionID) {
        this.inforamtionID = inforamtionID;
    }



    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }*/
    public String getInformationText() {
        return informationText;
    }

}
