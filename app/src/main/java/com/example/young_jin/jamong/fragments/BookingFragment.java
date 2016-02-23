package com.example.young_jin.jamong.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.young_jin.jamong.R;

/**
 * Created by slogup on 2016. 2. 23..
 */
public class BookingFragment extends Fragment {

    private static BookingFragment fragment;

    public static BookingFragment newInstance() {
        if (fragment == null) {
            fragment = new BookingFragment();
        }
        return fragment;
    }

    public BookingFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_booking, container, false);


        return layout;
    }

}
