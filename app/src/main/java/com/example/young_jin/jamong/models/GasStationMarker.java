package com.example.young_jin.jamong.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class GasStationMarker implements ClusterItem {

    private LatLng mPosition;
    private GasStation gasStation;

    public GasStationMarker(LatLng mPosition, GasStation gasStation) {
        this.mPosition = mPosition;
        this.gasStation = gasStation;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }
}
