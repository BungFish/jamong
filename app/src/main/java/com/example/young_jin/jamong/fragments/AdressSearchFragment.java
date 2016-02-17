package com.example.young_jin.jamong.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.young_jin.jamong.R;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class AdressSearchFragment extends Fragment {

    private FragmentManager fragmentManager;

    public static AdressSearchFragment newInstance() {
        AdressSearchFragment fragment = new AdressSearchFragment();
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

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==true){
                    v.setBackgroundColor(getResources().getColor(R.color.icons));
                    ((Button)v).setTextColor(getResources().getColor(R.color.colorPrimary));
                    ((Button)v).setTextSize(14);
                    if(v.getId() == R.id.cities){
                        fragmentManager.beginTransaction().replace(R.id.adress_search_context, CitiesFragment.newInstance()).commit();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.adress_search_context, DistrictsFragment.newInstance()).commit();
                    }

                } else {
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    ((Button)v).setTextColor(getResources().getColor(R.color.icons));
                    ((Button)v).setTextSize(12);
                }
            }
        };

        fragmentManager = getFragmentManager();

        Button cities = (Button) layout.findViewById(R.id.cities);
        cities.setOnFocusChangeListener(onFocusChangeListener);

        Button districts = (Button) layout.findViewById(R.id.districts);
        districts.setOnFocusChangeListener(onFocusChangeListener);

        cities.requestFocus();

        return layout;
    }

    }
