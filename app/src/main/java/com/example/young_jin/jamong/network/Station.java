package com.example.young_jin.jamong.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sngjoong on 16. 1. 25..
 */
public class Station implements Parcelable {

    private int stationSEQ;
    private double stationLONG;
    private double stationLAT;
    private String stationTel;
    private String stationName;
    private String stationAddr;
    private String isSelfStation;
    private String isDirectStation;
    private String stationHasWash;
    private String stationHasHgas;
    private String stationHasRepair;
    private String stationHasConvStore;
    private String isFavoriteStation;

    private double stationDistanceFromCurrent;

    public Station() {
    }

    protected Station(Parcel in) {
        stationSEQ = in.readInt();
        stationLONG = in.readDouble();
        stationLAT = in.readDouble();
        stationTel = in.readString();
        stationName = in.readString();
        stationAddr = in.readString();
        isSelfStation = in.readString();
        isDirectStation = in.readString();
        stationHasWash = in.readString();
        stationHasHgas = in.readString();
        stationHasRepair = in.readString();
        stationHasConvStore = in.readString();
        isFavoriteStation = in.readString();
        stationDistanceFromCurrent = in.readDouble();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    public void setStationDistanceFromCurrent(double stationDistanceFromCurrent) {
        this.stationDistanceFromCurrent = stationDistanceFromCurrent;
    }



    public void setStationSEQ(int stationSEQ) {
        this.stationSEQ = stationSEQ;
    }

    public void setStationLONG(double stationLONG) {
        this.stationLONG = stationLONG;
    }

    public void setStationLAT(double stationLAT) {
        this.stationLAT = stationLAT;
    }

    public void setStationTel(String stationTel) {
        this.stationTel = stationTel;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setStationAddr(String stationAddr) {
        this.stationAddr = stationAddr;
    }

    public void setIsSelfStation(String isSelfStation) {
        this.isSelfStation = isSelfStation;
    }

    public void setIsDirectStation(String isDirectStation) {
        this.isDirectStation = isDirectStation;}

    public void setStationHasWash(String stationHasWash) {
        this.stationHasWash = stationHasWash;
    }

    public void setStationHasHgas(String stationHasHgas) {
        this.stationHasHgas = stationHasHgas;
    }

    public void setStationHasRepair(String stationHasRepair) {
        this.stationHasRepair = stationHasRepair;
    }

    public void setStationHasConvStore(String stationHasConvStore) {
        this.stationHasConvStore = stationHasConvStore;
    }

    public void setIsFavoriteStation(String isFavoriteStation) {
        this.isFavoriteStation = isFavoriteStation;
    }

    public int getStationSEQ() {
        return stationSEQ;
    }

    public double getStationLONG() {
        return stationLONG;
    }

    public double getStationLAT() {
        return stationLAT;
    }

    public String getStationTel() {
        return stationTel;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationAddr() {
        return stationAddr;
    }

    public String getIsSelfStation() {
        return isSelfStation;
    }

    public String getIsDirectStation() {
        return isDirectStation;
    }

    public String getStationHasWash() {
        return stationHasWash;
    }

    public String getStationHasHgas() {
        return stationHasHgas;
    }

    public String getStationHasRepair() {
        return stationHasRepair;
    }

    public String getStationHasConvStore() {
        return stationHasConvStore;
    }

    public double getStationDistanceFromCurrent() {
        return stationDistanceFromCurrent;
    }

    public String getIsFavoriteStation() {
        return isFavoriteStation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stationSEQ);
        dest.writeDouble(stationLONG);
        dest.writeDouble(stationLAT);
        dest.writeString(stationTel);
        dest.writeString(stationName);
        dest.writeString(stationAddr);
        dest.writeString(isSelfStation);
        dest.writeString(isDirectStation);
        dest.writeString(stationHasWash);
        dest.writeString(stationHasHgas);
        dest.writeString(stationHasRepair);
        dest.writeString(stationHasConvStore);
        dest.writeString(isFavoriteStation);
        dest.writeDouble(stationDistanceFromCurrent);
    }
}
