package com.example.young_jin.jamong.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.models.GasStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class GasStationDetailActivityFragment extends Fragment {

    private Intent intent;
    private TextView title;
    private TextView adress;
    private GoogleMap map;
    private GasStation gasStation;
    private TextView conv_store;
    private TextView self;
    private TextView direct;
    private TextView repair;
    private TextView wash;

    public GasStationDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_gas_station_detail, container, false);

        intent = getActivity().getIntent();
        gasStation = intent.getParcelableExtra("station");

//        Log.d("======================", gasStation.toString());

        title = (TextView) layout.findViewById(R.id.title);
        adress = (TextView) layout.findViewById(R.id.adress);
        conv_store = (TextView) layout.findViewById(R.id.conv_store);
        self = (TextView) layout.findViewById(R.id.self);
        direct = (TextView) layout.findViewById(R.id.direct);
        repair = (TextView) layout.findViewById(R.id.repair);
        wash = (TextView) layout.findViewById(R.id.wash);

        if (gasStation.isConvstore().equals("Y")) {
            conv_store.setBackgroundResource(R.drawable.primary_rectangle_border);
        } else {
            conv_store.setBackgroundResource(R.drawable.rectangle_border);
        }
        if (gasStation.isSelf().equals("Y")) {
            self.setBackgroundResource(R.drawable.primary_rectangle_border);
        } else {
            self.setBackgroundResource(R.drawable.rectangle_border);
        }
        if (gasStation.isDirect().equals("Y")) {
            direct.setBackgroundResource(R.drawable.primary_rectangle_border);
        } else {
            direct.setBackgroundResource(R.drawable.rectangle_border);
        }
        if (gasStation.isRepair().equals("Y")) {
            repair.setBackgroundResource(R.drawable.primary_rectangle_border);
        } else {
            repair.setBackgroundResource(R.drawable.rectangle_border);
        }
        if (gasStation.isWash().equals("Y")) {
            wash.setBackgroundResource(R.drawable.primary_rectangle_border);
        } else {
            wash.setBackgroundResource(R.drawable.rectangle_border);
        }


        title.setText(gasStation.getmTItle());
        adress.setText(gasStation.getmAddress());

        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        map.getUiSettings().setMapToolbarEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gasStation.getmLatitude(),gasStation.getmLongitude()) , 14.5f));
        map.addMarker(new MarkerOptions().position(new LatLng(gasStation.getmLatitude(),gasStation.getmLongitude())));

        return layout;
    }
}
