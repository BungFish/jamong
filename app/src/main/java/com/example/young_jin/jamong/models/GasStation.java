package com.example.young_jin.jamong.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Young-Jin on 2016-02-08.
 */
public class GasStation implements Parcelable {

    private int index;
    private double mLatitude;
    private double mLongitude;
    private String mTItle;
    private double mDistance;
    private String mAddress;
    private String mPhone;
    private String isConvstore;
    private String isSelf;
    private String isDirect;
    private String isRepair;
    private String isWash;
    private String isFavorite;

    public GasStation() {
    }

    public GasStation(int index, double mLatitude, double mLongitude, String mTItle, double mDistance, String mAddress, String mPhone, String isConvstore, String isSelf, String isDirect, String isRepair, String isWash, String isFavorite) {

        this.index = index;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mTItle = mTItle;
        this.mDistance = mDistance;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        this.isConvstore = isConvstore;
        this.isSelf = isSelf;
        this.isDirect = isDirect;
        this.isRepair = isRepair;
        this.isWash = isWash;
        this.isFavorite = isFavorite;

    }

    protected GasStation(Parcel in) {
        index = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mTItle = in.readString();
        mDistance = in.readDouble();
        mAddress = in.readString();
        mPhone = in.readString();
        isConvstore = in.readString();
        isSelf = in.readString();
        isDirect = in.readString();
        isRepair = in.readString();
        isWash = in.readString();
        isFavorite = in.readString();
    }

    public static final Creator<GasStation> CREATOR = new Creator<GasStation>() {
        @Override
        public GasStation createFromParcel(Parcel in) {
            return new GasStation(in);
        }

        @Override
        public GasStation[] newArray(int size) {
            return new GasStation[size];
        }
    };

    public String getmTItle() {
        return mTItle;
    }


    public String getmAddress() {
        return mAddress;
    }

    public double getmDistance() {
        return mDistance;
    }

    public String getmPhone() {
        return mPhone;
    }


    public String isWash() {
        return isWash;
    }

    public String isConvstore() {
        return isConvstore;
    }

    public String isSelf() {
        return isSelf;
    }

    public String isDirect() {
        return isDirect;
    }

    public String isRepair() {
        return isRepair;
    }

    public String isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite){
        this.isFavorite = isFavorite;
    }


    public int getIndex() {
        return index;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mTItle);
        dest.writeDouble(mDistance);
        dest.writeString(mAddress);
        dest.writeString(mPhone);
        dest.writeString(isConvstore);
        dest.writeString(isSelf);
        dest.writeString(isDirect);
        dest.writeString(isRepair);
        dest.writeString(isWash);
        dest.writeString(isFavorite);
    }
}
