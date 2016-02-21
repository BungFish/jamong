package com.example.young_jin.jamong.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.young_jin.jamong.activities.GasStationDetailActivity;
import com.example.young_jin.jamong.activities.GasStationListActivity;
import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.adpaters.GasStationAdapter;
import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.models.GasStation;
import com.example.young_jin.jamong.models.GasStationMarker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Young-Jin on 2016-02-14.
 */
public class GasStationListFragment extends Fragment implements GasStationAdapter.ClickListener{

    private RecyclerView recyclerView;
    private GasStationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<GasStation> alist;
    private ArrayList<GasStation> GasStationList = new ArrayList<GasStation>();
    private Intent intent;

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

        intent = getActivity().getIntent();
        alist = intent.getParcelableArrayListExtra("station");

        setHasOptionsMenu(true);

        for (int i = 0; i < alist.size(); i++) {
            if (alist.get(i).getmDistance() <= intent.getIntExtra("radius", 3000)) {
                GasStationList.add(alist.get(i));
            }
        }

        recyclerView = (RecyclerView) layout.findViewById(R.id.gas_station_list);
        adapter = new GasStationAdapter(getActivity(), GasStationList);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setClickListener(this);

        return layout;

    }

    @Override
    public void itemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), GasStationDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("station", adapter.getItem(position));

        getActivity().startActivity(intent);

        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left_half);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gas_station_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}

