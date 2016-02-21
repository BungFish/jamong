package com.example.young_jin.jamong.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.adpaters.CityAdapter;
import com.example.young_jin.jamong.adpaters.DistrictAdapter;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class AdressSearchFragment extends Fragment implements CityAdapter.ClickListener, View.OnClickListener {

    private FragmentManager fragmentManager;
    private static AdressSearchFragment fragment;
    private Button cities;
    private Button districts;
    private RecyclerView city_list;
    private CityAdapter city_adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView district_list;
    private DistrictAdapter district_adapter;
    private Button cancel;
    private Button confirm;

    private ClickListener clickListener;

    public interface ClickListener {

        public void itemClick(int id);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static AdressSearchFragment newInstance() {
        if(fragment == null) {
            fragment = new AdressSearchFragment();
        }
        return fragment;
    }

    public AdressSearchFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_adress_search, container, false);

//        fragmentManager = getChildFragmentManager();
//
//        cities = (Button) layout.findViewById(R.id.cities);
//        districts = (Button) layout.findViewById(R.id.districts);
//
//        cities.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!CitiesFragment.newInstance().isAdded()) {
//                    fragmentManager.beginTransaction().replace(R.id.adress_search_context, CitiesFragment.newInstance()).commit();
//
//                    cities.setBackgroundColor(getResources().getColor(R.color.icons));
//                    cities.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    cities.setTextSize(14);
//
//                    districts.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                    districts.setTextColor(getResources().getColor(R.color.icons));
//                    districts.setTextSize(12);
//                }
//            }
//        });
//        districts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!DistrictsFragment.newInstance().isAdded()) {
//                    fragmentManager.beginTransaction().replace(R.id.adress_search_context, DistrictsFragment.newInstance()).commit();
//
//                    districts.setBackgroundColor(getResources().getColor(R.color.icons));
//                    districts.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    districts.setTextSize(14);
//
//                    cities.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                    cities.setTextColor(getResources().getColor(R.color.icons));
//                    cities.setTextSize(12);
//                }
//            }
//        });
//
//        cities.performClick();

        cancel = (Button) layout.findViewById(R.id.cancel);
        confirm = (Button) layout.findViewById(R.id.confirm);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);


        city_list = (RecyclerView) layout.findViewById(R.id.city_list);
        city_adapter = new CityAdapter(getActivity());
        city_list.setAdapter(city_adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        city_list.setLayoutManager(linearLayoutManager);
        city_adapter.setClickListener(this);

        district_list = (RecyclerView) layout.findViewById(R.id.district_list);
        district_adapter = new DistrictAdapter(getActivity());
        district_list.setAdapter(district_adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        district_list.setLayoutManager(linearLayoutManager);

        return layout;
    }

    @Override
    public void itemClick(View view, int position) {

        if(position == 0){
            district_adapter.setData(R.array.district);
        } else if(position == 1) {
            district_adapter.setData(R.array.district2);
        } else {
            district_adapter.setData(R.array.district3);
        }

        district_adapter.clearSelected();
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            switch (v.getId()){
                case R.id.cancel:
                    clickListener.itemClick(R.id.cancel);
                    break;
                case R.id.confirm:
                    if(district_adapter.getSelectedItem() == -1){
                        Toast.makeText(getActivity(), "시/군/구를 선택해 주세요.", Toast.LENGTH_LONG).show();
                    } else {
                        clickListener.itemClick(R.id.confirm);
                    }
                    break;
            }
        }
    }

}
