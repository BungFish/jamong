package com.example.young_jin.jamong.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.young_jin.jamong.R;

/**
 * Created by Young-Jin on 2016-02-16.
 */
public class SearchSettingFragment extends Fragment implements View.OnClickListener {

    private static SearchSettingFragment fragment;
    private Button gas;
    private Button digel;
    private Button hgas;
    private Button cancel;
    private Button confirm;
    private Button conv_store;
    private Button self;
    private Button direct;
    private Button wash;
    private Button repair;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            switch (v.getId()){
                case R.id.cancel:
                    clickListener.itemClick(R.id.cancel);
                    break;
                case R.id.confirm:
                    clickListener.itemClick(R.id.confirm);
                    break;
            }
        }
    }

    private ClickListener clickListener;

    public interface ClickListener {

        public void itemClick(int id);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public static SearchSettingFragment newInstance() {
        if(fragment == null) {
            fragment = new SearchSettingFragment();
        }
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_search_setting, container, false);

        gas = (Button) layout.findViewById(R.id.gas);
        digel = (Button) layout.findViewById(R.id.digel);
        hgas = (Button) layout.findViewById(R.id.hgas);

        gas.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        digel.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
        hgas.setBackgroundColor(getResources().getColor(R.color.setting_item_background));

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gas.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                digel.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
                hgas.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
            }
        });

        digel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gas.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
                digel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                hgas.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
            }
        });

        hgas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gas.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
                digel.setBackgroundColor(getResources().getColor(R.color.setting_item_background));
                hgas.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        conv_store = (Button) layout.findViewById(R.id.conv_store);
        self = (Button) layout.findViewById(R.id.self);
        direct = (Button) layout.findViewById(R.id.direct);
        repair = (Button) layout.findViewById(R.id.repair);
        wash = (Button) layout.findViewById(R.id.wash);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Button)v).isSelected()){
                    ((Button)v).setBackgroundColor(getResources().getColor(R.color.third_background));
                    ((Button)v).setSelected(false);
                } else {
                    ((Button)v).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    ((Button)v).setSelected(true);
                }
            }
        };

        conv_store.setOnClickListener(onClickListener);
        self.setOnClickListener(onClickListener);
        direct.setOnClickListener(onClickListener);
        repair.setOnClickListener(onClickListener);
        wash.setOnClickListener(onClickListener);

        cancel = (Button) layout.findViewById(R.id.cancel);
        confirm = (Button) layout.findViewById(R.id.confirm);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);


        return layout;
    }

}
