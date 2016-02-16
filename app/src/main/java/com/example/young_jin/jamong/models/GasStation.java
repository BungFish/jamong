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

    public GasStation() {
    }

    public GasStation(int index, double mLatitude, double mLongitude, String mTItle, double mDistance, String mAddress, String mPhone, String isConvstore, String isSelf, String isDirect, String isRepair, String isWash) {

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

    public void setmTItle(String mTItle) {
        this.mTItle = mTItle;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public double getmDistance() {
        return mDistance;
    }

    public void setmDistance(int mDistance) {
        this.mDistance = mDistance;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String isWash() {
        return isWash;
    }

    public void setIsWash(String isWash) {
        this.isWash = isWash;
    }

    public String isConvstore() {
        return isConvstore;
    }

    public void setIsConvstore(String isConvstore) {
        this.isConvstore = isConvstore;
    }

    public String isSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public String isDirect() {
        return isDirect;
    }

    public void setIsDirect(String isDirect) {
        this.isDirect = isDirect;
    }

    public String isRepair() {
        return isRepair;
    }

    public void setIsRepair(String isRepair) {
        this.isRepair = isRepair;
    }

    public int getIndex() {
        return index;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
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
    }
}
