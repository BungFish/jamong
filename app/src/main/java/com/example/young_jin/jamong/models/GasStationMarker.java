package com.example.young_jin.jamong.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class GasStationMarker implements ClusterItem {

    private int index;
    private LatLng mPosition;
    private GasStation gasStation;

    public GasStationMarker(int index, LatLng mPosition, GasStation gasStation) {
        this.index = index;
        this.mPosition = mPosition;
        this.gasStation = gasStation;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public int getIndex() {
        return index;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }
}
