package com.example.young_jin.jamong.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.young_jin.jamong.R;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class SearchSettingFragment extends Fragment {

    public static SearchSettingFragment newInstance() {
        SearchSettingFragment fragment = new SearchSettingFragment();
        return fragment;
    }

    public SearchSettingFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_search_setting, container, false);

        Switch convSwich = (Switch) layout.findViewById(R.id.switch1);
        convSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
            }
        });

        return layout;
    }

}
