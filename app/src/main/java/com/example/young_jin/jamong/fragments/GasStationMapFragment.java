package com.example.young_jin.jamong.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.activities.GasStationDetailActivity;
import com.example.young_jin.jamong.activities.GasStationListActivity;
import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.adpaters.GasStationAdapter;
import com.example.young_jin.jamong.models.GasStation;
import com.example.young_jin.jamong.models.GasStationMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Young-Jin on 2016-02-14.
 */
public class GasStationMapFragment extends Fragment implements MainActivity.onKeyBackPressedListener, GasStationAdapter.ClickListener, AdressSearchFragment.ClickListener, SearchSettingFragment.ClickListener {

    private static final String DESCRIBABLE_KEY = "Jamong";

    private static GoogleMap map;
    private TextView my_location_textview;
    private LocationManager locationManager;
    private String locationProvider;
    private Location location;
    private Geocoder mCoder;
    private List<Address> addr;
    private String adress;
    private static Circle mapCircle;
    private LinearLayout detail_view;
    private TextView station_name;
    private TextView station_adress;
    private TextView station_phone;
    private Animation slide_up_anim;
    private Animation slide_down_anim;
    private RelativeLayout map_layout;
    private GasStationAdapter.ClickListener clickListener;
    private FragmentManager fragmentManager;
    private TextView gas;
    private TextView disel;
    private TextView hgas;
    private TextView conv_store;
    private TextView self;
    private TextView direct;
    private TextView repair;
    private TextView wash;
    private TextView distance;
    private LinearLayout sub_layout;
    private int heightOfScreen;
    private View view_instance;
    private ArrayList<GasStationMarker> markerList;
    private RecyclerView recyclerView;
    private GasStationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout station_list_view;
    private Animation slide_in_right_anim;
    private Animation slide_down_from_top_anim;
    private Animation slide_up_to_top_anim;
    private LinearLayout settings_layout;
    private Animation slide_out_to_right_anim;
    private Animation slide_in_left_anim;
    private Animation slide_out_to_left_anim;
    private View highlighted_custom_marker;
    private MyClusterRenderer myClusterRenderer;
    private Marker selectedMarker;
    private GasStationMarker selectedClusterItem;
    private int radius;
    private Button button;
    private Button button3;

    public static Circle getMapCircle() {
        return mapCircle;
    }

    public static void setMapCircle(Circle mapCircle) {
        GasStationMapFragment.mapCircle = mapCircle;
    }

    public static LatLng getCurrent_location() {
        return current_location;
    }

    private static LatLng current_location;
    private ClusterManager<GasStationMarker> mClusterManager;
    private ArrayList<GasStation> alist = new ArrayList<>();

    private static GasStationMapFragment fragment;
    private View custom_marker;

    public static GasStationMapFragment newInstance() {
        if(fragment==null) {
            fragment = new GasStationMapFragment();
        }

        return fragment;
    }

    public GasStationMapFragment() {

// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_gas_station_map, container, false);
        this.alist = MainActivity.alist;

        setHasOptionsMenu(true);

        slide_up_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        slide_up_to_top_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_to_top);
        slide_down_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        slide_down_from_top_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_from_top);
        slide_in_right_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_right);
        slide_out_to_right_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_right);

        slide_in_left_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_left);
        slide_out_to_left_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_left);


        detail_view = (LinearLayout) layout.findViewById(R.id.detail_view);
        station_name = (TextView) layout.findViewById(R.id.station_name);
        distance = (TextView) layout.findViewById(R.id.distance);
        station_adress = (TextView) layout.findViewById(R.id.station_adress);
        station_phone = (TextView) layout.findViewById(R.id.station_phone);
        gas = (TextView) layout.findViewById(R.id.gas);
        disel = (TextView) layout.findViewById(R.id.disel);
        hgas = (TextView) layout.findViewById(R.id.hgas);

        conv_store = (TextView) layout.findViewById(R.id.conv_store);
        self = (TextView) layout.findViewById(R.id.self);
        direct = (TextView) layout.findViewById(R.id.direct);
        repair = (TextView) layout.findViewById(R.id.repair);
        wash = (TextView) layout.findViewById(R.id.wash);

        station_list_view = (LinearLayout) layout.findViewById(R.id.station_list_view);
        recyclerView = (RecyclerView) layout.findViewById(R.id.gas_station_list);
        adapter = new GasStationAdapter(getActivity(), null);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setClickListener(this);


        map_layout = (RelativeLayout) layout.findViewById(R.id.map_layout);

        DisplayMetrics dM = getActivity().getResources().getDisplayMetrics();
        heightOfScreen = dM.heightPixels;

        //상세정보 레이아웃 높이 설정
        view_instance = (View)detail_view;
        ViewGroup.LayoutParams params = view_instance.getLayoutParams();
        params.height = (int) (heightOfScreen * 0.4);
        view_instance.setLayoutParams(params);

        view_instance = (View)station_list_view;
        params.height = (int) (heightOfScreen * 0.4);
        view_instance.setLayoutParams(params);


        custom_marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        highlighted_custom_marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.highlighted_custom_marker, null);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationProvider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            location = locationManager.getLastKnownLocation(locationProvider);
        }


        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
//        my_location_textview = (TextView) layout.findViewById(R.id.my_location);

        setUpClusterer();

        current_location = new LatLng(location.getLatitude(), location.getLongitude());

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                current_location, 12.9f));

        addMapCircle(3000, current_location);

//        map.addMarker(new MarkerOptions()
////                .title("현재위치")
////                .snippet("현재위치입니다.")
//                .position(current_location));

//        new CurrentLocationTask().execute(location);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                button.setSelected(false);
                button3.setSelected(false);

                if (detail_view.getVisibility() == View.VISIBLE) {
                    detail_view.setVisibility(View.GONE);
                    detail_view.startAnimation(slide_down_anim);
                }

                if (station_list_view.getVisibility() == View.VISIBLE) {
                    station_list_view.setVisibility(View.GONE);
                    station_list_view.startAnimation(slide_down_anim);
                }

                if (sub_layout.getVisibility() == View.VISIBLE) {
                    sub_layout.setVisibility(View.GONE);
                    sub_layout.startAnimation(slide_up_to_top_anim);
                }

//                if (selectedClusterItem != null) {
//                    selectedClusterItem.setIsSelected(false);
//
//                    selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
//                    if(selectedMarker != null) {
//                        selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
//                    }
//
//                    selectedClusterItem = null;
//                }
                setMarkerUnselected();
            }
        });

        final Button radiusButton = (Button) layout.findViewById(R.id.radius);
        radiusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("반경 선택")
                        .setSingleChoiceItems(R.array.distance, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mClusterManager.clearItems();
                                mapCircle.remove();
                                switch (which) {
                                    case 0:
                                        radius = 1000;
                                        for (int i = 0; i < alist.size(); i++) {
                                            if (alist.get(i).getmDistance() <= radius) {
                                                mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                                            }
                                        }

                                        addMapCircle(radius, current_location);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                                current_location, 14.5f), 1000, null);
                                        radiusButton.setText("반경: 1km");
                                        break;
                                    case 1:
                                        radius = 3000;
                                        for (int i = 0; i < alist.size(); i++) {
                                            if (alist.get(i).getmDistance() <= radius) {
                                                mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                                            }
                                        }

                                        addMapCircle(radius, current_location);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 12.9f), 1000, null);
                                        radiusButton.setText("반경: 3km");

                                        break;
                                    case 2:
                                        radius = 5000;
                                        for (int i = 0; i < alist.size(); i++) {
                                            if (alist.get(i).getmDistance() <= radius) {
                                                mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                                            }
                                        }

                                        addMapCircle(radius, current_location);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 12.1f), 1000, null);
                                        radiusButton.setText("반경: 5km");
                                        break;
                                    case 3:
                                        radius = 10000;
                                        for (int i = 0; i < alist.size(); i++) {
                                            if (alist.get(i).getmDistance() <= radius) {
                                                mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                                            }
                                        }

                                        addMapCircle(radius, current_location);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 11.2f), 1000, null);
                                        radiusButton.setText("반경: 10km");
                                        break;
                                    case 4:
                                        radius = 20000;
                                        for (int i = 0; i < alist.size(); i++) {
                                            if (alist.get(i).getmDistance() <= radius) {
                                                mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                                            }
                                        }

                                        addMapCircle(radius, current_location);
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 10.2f), 1000, null);
                                        radiusButton.setText("반경: 20km");
                                        break;
                                }

                                mClusterManager.cluster();

                                dialog.cancel();
                            }
                        }).show();
            }
        });

        sub_layout = (LinearLayout) layout.findViewById(R.id.sub_layout);

        fragmentManager = getChildFragmentManager();

//        View cover = layout.findViewById(R.id.coverLyaout);
//        cover.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                button.setSelected(false);
//                button3.setSelected(false);
//
//                if (sub_layout.getVisibility() == View.VISIBLE) {
//                    sub_layout.setVisibility(View.GONE);
//                    sub_layout.startAnimation(slide_up_to_top_anim);
//                    return true;
//                }
//                return false;
//            }
//        });

        button = (Button) layout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setSelected(true);
                button3.setSelected(false);

                if (!AdressSearchFragment.newInstance().isAdded()) {
                    fragmentManager.beginTransaction().replace(R.id.sub_context, AdressSearchFragment.newInstance()).commit();
                }
                if (sub_layout.getVisibility() == View.GONE) {

//                    view_instance = sub_layout;
//                    ViewGroup.LayoutParams params = view_instance.getLayoutParams();
//                    params.height = (int) (heightOfScreen * 0.7);
//                    view_instance.setLayoutParams(params);

                    sub_layout.setVisibility(View.VISIBLE);//0.8
                    sub_layout.startAnimation(slide_in_left_anim);

                }
            }
        });

        Button button2 = (Button) layout.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasStationMapFragment.gotoMylocation();
            }
        });

        button3 = (Button) layout.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setSelected(false);
                button3.setSelected(true);

                if (!SearchSettingFragment.newInstance().isAdded()) {
                    fragmentManager.beginTransaction().replace(R.id.sub_context, SearchSettingFragment.newInstance()).commit();
                }
                if (sub_layout.getVisibility() == View.GONE) {

//                    view_instance = sub_layout;
//                    ViewGroup.LayoutParams params = view_instance.getLayoutParams();
//                    params.height = (int) (heightOfScreen * 0.4);
//                    view_instance.setLayoutParams(params);

                    sub_layout.setVisibility(View.VISIBLE);//0.45
                    sub_layout.startAnimation(slide_in_right_anim);
                }
            }
        });

        settings_layout = (LinearLayout) layout.findViewById(R.id.settings_layout);
        settings_layout.startAnimation(slide_down_from_top_anim);

        AdressSearchFragment.newInstance().setClickListener(this);
        SearchSettingFragment.newInstance().setClickListener(this);

        return layout;

    }

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<GasStationMarker>(getActivity(), map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraChangeListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        map.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        myClusterRenderer = new MyClusterRenderer(getActivity(), map, mClusterManager);
        mClusterManager.setRenderer(myClusterRenderer);

        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getActivity().getLayoutInflater().inflate(
                                R.layout.info_window, null);

                        TextView tv1 = (TextView) v
                                .findViewById(R.id.textView1);
                        TextView tv2 = (TextView) v
                                .findViewById(R.id.textView2);

                        tv1.setText(marker.getTitle());
                        tv2.setText(String.format("%.1fkm", alist.get(Integer.valueOf(marker.getSnippet())).getmDistance() / 1000));

                        return v;
                    }
                });


        mClusterManager
                .setOnClusterClickListener(new ClusterManager.OnClusterClickListener<GasStationMarker>() {

                    @Override
                    public boolean onClusterClick(Cluster<GasStationMarker> cluster) {

                        markerList = new ArrayList<GasStationMarker>(cluster.getItems());

                        adapter.clearData();
                        adapter.notifyDataSetChanged();

                        for (GasStationMarker gasStationMarker : markerList
                                ) {
                            adapter.addData(gasStationMarker.getGasStation());
                        }


                        if (station_list_view.getVisibility() == View.GONE) {
                            station_list_view.setVisibility(View.VISIBLE);
                            station_list_view.startAnimation(slide_up_anim);
                        }

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(cluster.getPosition().latitude, cluster.getPosition().longitude), map.getCameraPosition().zoom), 500, null);

                        if (detail_view.getVisibility() == View.VISIBLE) {
                            detail_view.setVisibility(View.GONE);
                        }

                        return false;
                    }
                });

        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<GasStationMarker>() {

                    @Override
                    public boolean onClusterItemClick(GasStationMarker clusterItem) {

//                        if (selectedClusterItem != null) {
//                            selectedClusterItem.setIsSelected(false);
//
//                            selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
//                            if(selectedMarker != null) {
//                                selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
//                            }
//                        }
//
//                        selectedClusterItem = clusterItem;
//
//                        if (selectedClusterItem != null) {
//                            selectedClusterItem.setIsSelected(true);
//
//                            selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
//                            selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), highlighted_custom_marker)));
//                        }

                        setMarkerSelected(clusterItem);

                        populateDetailView(clusterItem.getGasStation());

                        if (detail_view.getVisibility() == View.GONE) {
                            detail_view.setVisibility(View.VISIBLE);
                            detail_view.startAnimation(slide_up_anim);
                        }

                        if (station_list_view.getVisibility() == View.VISIBLE) {
                            station_list_view.setVisibility(View.GONE);
                        }

                        return false;
                    }
                });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(), GasStationDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                GasStation gasStation = alist.get(Integer.valueOf(marker.getSnippet()));

                intent.putExtra("station", gasStation);

                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left_half);
            }
        });

        if(alist != null){
            radius = 3000;
            for (int i = 0; i < alist.size(); i++) {
                if (alist.get(i).getmDistance() <= 3000) {
                    mClusterManager.addItem(new GasStationMarker(i, new LatLng(alist.get(i).getmLatitude(), alist.get(i).getmLongitude()), alist.get(i), false));
                }
            }
        } else {
            Toast.makeText(getActivity(), "데이터를 아직 받아오지 못했습니다.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void itemClick(int id) {
        switch (id){
            case R.id.cancel:
                if (sub_layout.getVisibility() == View.VISIBLE) {
                    sub_layout.setVisibility(View.GONE);
                    sub_layout.startAnimation(slide_up_to_top_anim);

                    button.setSelected(false);
                    button3.setSelected(false);
                }
                break;
            case R.id.confirm:
                if (sub_layout.getVisibility() == View.VISIBLE) {
                    sub_layout.setVisibility(View.GONE);
                    sub_layout.startAnimation(slide_up_to_top_anim);

                    button.setSelected(false);
                    button3.setSelected(false);
                }
                break;
        }


    }

    class MyClusterRenderer extends DefaultClusterRenderer<GasStationMarker> {

        private static final int MIN_CLUSTER_SIZE = 5;

        public MyClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<GasStationMarker> clusterManager) {
            super(context, map, clusterManager);

        }

        @Override
        protected void onBeforeClusterItemRendered(GasStationMarker item,
                                                   MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);

            if(item.getIsSelected()){
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), highlighted_custom_marker)));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
            }

        }

        @Override
        protected void onClusterItemRendered(GasStationMarker clusterItem,
                                             Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
            marker.setTitle(clusterItem.getGasStation().getmTItle());
            marker.setSnippet(clusterItem.getGasStation().getIndex() + "");

//            if(clusterItem.getIsSelected()){
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), highlighted_custom_marker)));
//            } else {
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
//            }

//            TextView textView = (TextView) custom_marker.findViewById(R.id.gas_station_title);
//            textView.setText(clusterItem.getGasStation().getmTItle());

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<GasStationMarker> cluster) {
            // start clustering if at least 5 items overlap
            return cluster.getSize() > MIN_CLUSTER_SIZE;
        }

        @Override
        public void onClustersChanged(Set<? extends Cluster<GasStationMarker>> clusters) {
            super.onClustersChanged(clusters);

            if(selectedMarker != null) {
                selectedMarker = null;
            }
        }
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    class CurrentLocationTask extends AsyncTask<Location, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Location... params) {
            Location location = params[0];
            try {
                mCoder = new Geocoder(getActivity());
                addr = mCoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 5);
                adress = addr.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "현재위치를 알수없음.. 네트워크 상태를 확인하세요.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            my_location_textview.setText("현재위치 : " + adress);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public static GoogleMap getGoogleMap() {
        return map;
    }

    public static void gotoMylocation(){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                GasStationMapFragment.getCurrent_location(), 14.5f), 1000, null);
    }

    @Override
    public void itemClick(View view, int position) {

        populateDetailView(adapter.getItem(position));
        if(detail_view.getVisibility() == View.GONE){
            detail_view.setVisibility(View.VISIBLE);
            detail_view.startAnimation(slide_in_right_anim);
        }

        if(station_list_view.getVisibility() == View.VISIBLE) {
            station_list_view.setVisibility(View.GONE);
            station_list_view.startAnimation(slide_down_anim);
        }

//        if (selectedClusterItem != null) {
//            selectedClusterItem.setIsSelected(false);
//        }
//
//        selectedClusterItem = markerList.get(position);
//        selectedClusterItem.setIsSelected(true);

        setMarkerSelected(markerList.get(position));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(adapter.getItem(position).getmLatitude(), adapter.getItem(position).getmLongitude()), 14.5f), 1000, null);
        
    }

    @Override
    public void onBack() {

        if(detail_view.getVisibility() == View.VISIBLE){
            detail_view.setVisibility(View.GONE);
            detail_view.startAnimation(slide_down_anim);

            setMarkerUnselected();
        } else if(station_list_view.getVisibility() == View.VISIBLE) {
            station_list_view.setVisibility(View.GONE);
            station_list_view.startAnimation(slide_down_anim);
        } else {
            if (sub_layout.getVisibility() == View.VISIBLE) {
                sub_layout.setVisibility(View.GONE);
                sub_layout.startAnimation(slide_up_to_top_anim);

                button.setSelected(false);
                button3.setSelected(false);
            } else {
//                GasStationActivity activity = (GasStationActivity) getActivity();
//                activity.setOnKeyBackPressedListener(null);
//                activity.onBackPressed();

                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_context, MainFragment.newInstance()).commit();

            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setOnKeyBackPressedListener(this);
    }

    public void setMarkerSelected(GasStationMarker gasStationMarker){
            if (selectedClusterItem != null) {
                selectedClusterItem.setIsSelected(false);

                selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
                if(selectedMarker != null) {
                    selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
                }
            }

            selectedClusterItem = gasStationMarker;

            if (selectedClusterItem != null) {
                selectedClusterItem.setIsSelected(true);

                selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
                if(selectedMarker != null) {
                    selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), highlighted_custom_marker)));
                }
            }

    }

    public void setMarkerUnselected(){
        if (selectedClusterItem != null) {
            selectedClusterItem.setIsSelected(false);

            selectedMarker = myClusterRenderer.getMarker(selectedClusterItem);
            if(selectedMarker != null) {
                selectedMarker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
            }

            selectedClusterItem = null;
        }
    }
    
    public void populateDetailView(GasStation gasStation){
        station_name.setText(gasStation.getmTItle());
        distance.setText(String.format("%.1fkm", gasStation.getmDistance() / 1000));
        station_adress.setText(gasStation.getmAddress());
        station_phone.setText(PhoneNumberUtils.formatNumber(gasStation.getmPhone()));

//                        gas.setText(gasStation.getmTItle());
//                        disel.setText(gasStation.getmAddress());
//                        hgas.setText(gasStation.getmPhone());

//        if (gasStation.isConvstore().equals("Y")) {
//            conv_store.setBackgroundResource(R.drawable.primary_rectangle_border);
//        } else {
//            conv_store.setBackgroundResource(R.drawable.rectangle_border);
//        }
//        if (gasStation.isSelf().equals("Y")) {
//            self.setBackgroundResource(R.drawable.primary_rectangle_border);
//        } else {
//            self.setBackgroundResource(R.drawable.rectangle_border);
//        }
//        if (gasStation.isDirect().equals("Y")) {
//            direct.setBackgroundResource(R.drawable.primary_rectangle_border);
//        } else {
//            direct.setBackgroundResource(R.drawable.rectangle_border);
//        }
//        if (gasStation.isRepair().equals("Y")) {
//            repair.setBackgroundResource(R.drawable.primary_rectangle_border);
//        } else {
//            repair.setBackgroundResource(R.drawable.rectangle_border);
//        }
//        if (gasStation.isWash().equals("Y")) {
//            wash.setBackgroundResource(R.drawable.primary_rectangle_border);
//        } else {
//            wash.setBackgroundResource(R.drawable.rectangle_border);
//        }

        if (gasStation.isConvstore().equals("Y")) {
            conv_store.setBackgroundResource(R.color.colorPrimary);
        } else {
            conv_store.setBackgroundResource(R.color.third_background);
        }
        if (gasStation.isSelf().equals("Y")) {
            self.setBackgroundResource(R.color.colorPrimary);
        } else {
            self.setBackgroundResource(R.color.third_background);
        }
        if (gasStation.isDirect().equals("Y")) {
            direct.setBackgroundResource(R.color.colorPrimary);
        } else {
            direct.setBackgroundResource(R.color.third_background);
        }
        if (gasStation.isRepair().equals("Y")) {
            repair.setBackgroundResource(R.color.colorPrimary);
        } else {
            repair.setBackgroundResource(R.color.third_background);
        }
        if (gasStation.isWash().equals("Y")) {
            wash.setBackgroundResource(R.color.colorPrimary);
        } else {
            wash.setBackgroundResource(R.color.third_background);
        }


    }

    public void addMapCircle(int radius, LatLng current_location){
        mapCircle = map.addCircle(new CircleOptions().radius(radius).center(current_location).fillColor(getResources().getColor(R.color.colorMapRadius)).
                strokeColor(getResources().getColor(R.color.colorMapStroke)).strokeWidth(10f));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gas_station, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_station_list) {
            Intent intent = new Intent(getActivity(), GasStationListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            intent.putExtra("station", alist);
            intent.putExtra("radius", radius);

            startActivity(intent);

            getActivity().overridePendingTransition(R.anim.slide_up_fast, R.anim.hold);
            return true;
        }

        if (id == R.id.settings) {
            MainActivity.drawerFragment.getmDrawerLayout().openDrawer(Gravity.RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}