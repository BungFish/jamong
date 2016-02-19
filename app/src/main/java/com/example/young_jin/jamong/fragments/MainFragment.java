package com.example.young_jin.jamong.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.activities.GasStationListActivity;
import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.animation.RippleStrokeBackGround;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by slogup on 2016. 2. 19..
 */
public class MainFragment extends Fragment implements MainActivity.onKeyBackPressedListener {

    private static MainFragment fragment;
    private GoogleMap map;
    private LinearLayout info;
    private LinearLayout buttons;
    private Button quick;
    private RippleStrokeBackGround rippleBackground;
    private Animation slide_up_anim;
    private Animation slide_down_anim;

    public static MainFragment newInstance() {
        if(fragment == null) {
            fragment = new MainFragment();
        }
        return fragment;
    }

    public MainFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        slide_up_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_to_top);
        slide_down_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_main);


        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(MainActivity.location.getLatitude(), MainActivity.location.getLongitude()), 12.9f));

        info = (LinearLayout) layout.findViewById(R.id.info);
        buttons = (LinearLayout) layout.findViewById(R.id.buttons);

        quick = (Button) layout.findViewById(R.id.quick);

        rippleBackground = (RippleStrokeBackGround)layout.findViewById(R.id.content);
//        ImageView imageView=(ImageView)findViewById(R.id.centerImage);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        rippleBackground.setOnClickListener(new View.OnClickListener() {

            private Intent intent;

            @Override
            public void onClick(View v) {
                info.startAnimation(slide_up_anim);
                info.setVisibility(View.GONE);
                buttons.startAnimation(slide_down_anim);
                buttons.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_context, GasStationMapFragment.newInstance()).commit();
//                        intent = new Intent(MainActivity.this, GasStationActivity.class);
//
//                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                        startActivity(intent);

//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 700);
            }
        });

        rippleBackground.startRippleAnimation();

        return layout;
    }

    @Override
    public void onBack() {

        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setOnKeyBackPressedListener(this);
    }

}
