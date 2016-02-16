package com.example.young_jin.jamong.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.young_jin.jamong.activities.GasStationDetailActivity;
import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.adpaters.GasStationAdapter;
import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.models.GasStation;

import java.util.ArrayList;

/**
 * Created by Young-Jin on 2016-02-14.
 */
public class GasStationListFragment extends Fragment implements GasStationAdapter.ClickListener{

    private RecyclerView recyclerView;
    private GasStationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<GasStation> alist;

    public static GasStationListFragment newInstance() {
        GasStationListFragment fragment = new GasStationListFragment();
        return fragment;
    }

    public GasStationListFragment() {

// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_gas_station_list, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.gas_station_list);
        adapter = new GasStationAdapter(getActivity(), MainActivity.alist);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setClickListener(this);

        RadioButton filter_distance = (RadioButton) layout.findViewById(R.id.filter_distance);
        RadioButton filter_price = (RadioButton) layout.findViewById(R.id.filter_price);

        return layout;

    }

    @Override
    public void itemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), GasStationDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("station", adapter.getItem(position));

        getActivity().startActivity(intent);

        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
    }
}

