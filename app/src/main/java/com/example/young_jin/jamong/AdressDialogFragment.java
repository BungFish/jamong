package com.example.young_jin.jamong;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.young_jin.jamong.adpaters.CityAdapter;
import com.example.young_jin.jamong.adpaters.DistrictAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by slogup on 2016. 2. 22..
 */
public class AdressDialogFragment extends DialogFragment implements CityAdapter.ClickListener, View.OnClickListener{
    int mNum;
    private RecyclerView city_list;
    private CityAdapter city_adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView district_list;
    private DistrictAdapter district_adapter;
    private Button cancel;
    private Button confirm;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */

    private ClickListener clickListener;
    private static AdressDialogFragment fragment;

    public interface ClickListener {

        public void itemClick(int id, double lat, double lng);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static AdressDialogFragment newInstance(int num) {
        if(fragment == null) {
            fragment = new AdressDialogFragment();
        }

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
//        switch ((mNum-1)%6) {
//            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
//            case 4: style = DialogFragment.STYLE_NORMAL; break;
//            case 5: style = DialogFragment.STYLE_NORMAL; break;
//            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
//            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
//            case 8: style = DialogFragment.STYLE_NORMAL; break;
//        }
//        switch ((mNum-1)%6) {
//            case 4: theme = android.R.style.Theme_Holo; break;
//            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
//            case 6: theme = android.R.style.Theme_Holo_Light; break;
//            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
//            case 8: theme = android.R.style.Theme_Holo_Light; break;
//        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_adress_search, container, false);

        getDialog().setTitle("원하는 지역을 선택하세요.");

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
                    clickListener.itemClick(R.id.cancel, 0, 0);
                    break;
                case R.id.confirm:
                    if(district_adapter.getSelectedItem() == -1){
                        Toast.makeText(getActivity(), "시/군/구를 선택해 주세요.", Toast.LENGTH_LONG).show();
                    } else if(district_adapter.getSelectedItem() == 0) {
                        new GetLocationTask().execute(city_adapter.getItem(city_adapter.getSelectedItem()));
                    } else {
                        new GetLocationTask().execute(district_adapter.getItem(district_adapter.getSelectedItem()));
                    }
                    break;
            }
        }
    }

    class GetLocationTask extends AsyncTask<String, Void, Void> {

        private Geocoder mCoder;
        private List<Address> addr;
        private double lat;
        private double lng;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String keyword = params[0];
            try {
                mCoder = new Geocoder(getActivity());
                addr = mCoder.getFromLocationName(keyword, 1);
                lat = addr.get(0).getLatitude();
                lng = addr.get(0).getLongitude();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "주소 정보를 가져올 수 없습니다.. 네트워크 상태를 확인하세요.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            clickListener.itemClick(R.id.confirm, lat, lng);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}