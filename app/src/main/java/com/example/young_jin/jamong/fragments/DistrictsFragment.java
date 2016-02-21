package com.example.young_jin.jamong.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.models.GasStation;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class DistrictsFragment extends Fragment {

    private static DistrictsFragment fragment;

    public static DistrictsFragment newInstance() {
        if (fragment == null) {
            fragment = new DistrictsFragment();
        }
        return fragment;
    }

    public DistrictsFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_districts, container, false);

        return layout;
    }

}
