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
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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
import com.example.young_jin.jamong.activities.GasStationActivity;
import com.example.young_jin.jamong.activities.GasStationDetailActivity;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Young-Jin on 2016-02-14.
 */
public class GasStationMapFragment extends Fragment implements GasStationActivity.onKeyBackPressedListener {

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
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private FragmentManager fragmentManager;
    private Toolbar toolbar2;
    private TextView toolbar_title2;
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

        slide_up_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        slide_down_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);

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


        map_layout = (RelativeLayout) layout.findViewById(R.id.map_layout);
        DisplayMetrics dM = getActivity().getResources().getDisplayMetrics();
        heightOfScreen = dM.heightPixels;
        //상세정보 레이아웃 높이 설정
        view_instance = (View)detail_view;
        ViewGroup.LayoutParams params = view_instance.getLayoutParams();
        params.height = (int) (heightOfScreen * 0.4);
        view_instance.setLayoutParams(params);


        custom_marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

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

        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                current_location, 14.5f));

        mapCircle = map.addCircle(new CircleOptions().radius(3000).center(current_location).fillColor(getResources().getColor(R.color.colorMapRadius)).
                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));

        map.addMarker(new MarkerOptions()
//                .title("현재위치")
//                .snippet("현재위치입니다.")
                .position(current_location));

//        new CurrentLocationTask().execute(location);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (detail_view.getVisibility() == View.VISIBLE) {
                    detail_view.setVisibility(View.GONE);
                    detail_view.startAnimation(slide_down_anim);
                }
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
                                        for (GasStation gasStation : alist) {
                                            if (gasStation.getmDistance() / 1000 <= 1) {
                                                mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                                            }
                                        }
                                        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map,
                                                mClusterManager));

                                        mapCircle = map.addCircle(new CircleOptions().radius(1000).center(GasStationMapFragment.getCurrent_location()).fillColor(getResources().getColor(R.color.colorMapRadius)).
                                                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                                current_location, 14.5f), 1000, null);
                                        radiusButton.setText("반경: 1km");
                                        break;
                                    case 1:
                                        for (GasStation gasStation : alist) {
                                            if (gasStation.getmDistance() / 1000 <= 3) {
                                                mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                                            }
                                        }
                                        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map,
                                                mClusterManager));

                                        mapCircle = map.addCircle(new CircleOptions().radius(3000).center(GasStationMapFragment.getCurrent_location()).fillColor(getResources().getColor(R.color.colorMapRadius)).
                                                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 12.9f), 1000, null);
                                        radiusButton.setText("반경: 3km");

                                        break;
                                    case 2:
                                        for (GasStation gasStation : alist) {
                                            if (gasStation.getmDistance() / 1000 <= 5) {
                                                mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                                            }
                                        }
                                        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map,
                                                mClusterManager));

                                        mapCircle = map.addCircle(new CircleOptions().radius(5000).center(GasStationMapFragment.getCurrent_location()).fillColor(getResources().getColor(R.color.colorMapRadius)).
                                                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 12.1f), 1000, null);
                                        radiusButton.setText("반경: 5km");
                                        break;
                                    case 3:
                                        for (GasStation gasStation : alist) {
                                            if (gasStation.getmDistance() / 1000 <= 10) {
                                                mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                                            }
                                        }
                                        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map,
                                                mClusterManager));

                                        mapCircle = map.addCircle(new CircleOptions().radius(10000).center(GasStationMapFragment.getCurrent_location()).fillColor(getResources().getColor(R.color.colorMapRadius)).
                                                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 11.2f), 1000, null);
                                        radiusButton.setText("반경: 10km");
                                        break;
                                    case 4:
                                        for (GasStation gasStation : alist) {
                                            if (gasStation.getmDistance() / 1000 <= 20) {
                                                mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                                            }
                                        }
                                        mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map,
                                                mClusterManager));

                                        mapCircle = map.addCircle(new CircleOptions().radius(20000).center(GasStationMapFragment.getCurrent_location()).fillColor(getResources().getColor(R.color.colorMapRadius)).
                                                strokeColor(getResources().getColor(R.color.colorPrimary)).strokeWidth(1f));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 10.2f), 1000, null);
                                        radiusButton.setText("반경: 20km");
                                        break;
                                }

                                dialog.cancel();
                            }
                        }).show();
            }
        });


        toolbar2 = (Toolbar) layout.findViewById(R.id.app_bar2);
        toolbar_title2 = (TextView) toolbar2.findViewById(R.id.toolbar_title);

        sub_layout = (LinearLayout) layout.findViewById(R.id.sub_layout);

        fragmentManager = getChildFragmentManager();

        View cover = layout.findViewById(R.id.coverLyaout);
        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (sub_layout.getVisibility() == View.VISIBLE) {
                    sub_layout.setVisibility(View.GONE);
                    sub_layout.startAnimation(slide_down_anim);
                    return true;
                }
                return false;
            }
        });

        Button button = (Button) layout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sub_layout.getVisibility() == View.GONE) {
                    fragmentManager.beginTransaction().replace(R.id.sub_context, AdressSearchFragment.newInstance()).commit();
                    toolbar_title2.setText("주소검색");

                    view_instance = (View)sub_layout;
                    ViewGroup.LayoutParams params = view_instance.getLayoutParams();
                    params.height = (int) (heightOfScreen * 0.7);
                    view_instance.setLayoutParams(params);

                    sub_layout.setVisibility(View.VISIBLE);//0.8
                    sub_layout.startAnimation(slide_up_anim);


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

        Button button3 = (Button) layout.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sub_layout.getVisibility() == View.GONE) {
                    fragmentManager.beginTransaction().replace(R.id.sub_context, SearchSettingFragment.newInstance()).commit();
                    toolbar_title2.setText("검색설정");

                    view_instance = (View)sub_layout;
                    ViewGroup.LayoutParams params = view_instance.getLayoutParams();
                    params.height = (int) (heightOfScreen * 0.4);
                    view_instance.setLayoutParams(params);

                    sub_layout.setVisibility(View.VISIBLE);//0.45
                    sub_layout.startAnimation(slide_up_anim);
                }
            }
        });

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
                        // FragmentManager manager =
                        // getSupportFragmentManager();
                        // MyDialogFragment dialog = new MyDialogFragment();
                        //
                        // dialog.show(manager, "dialog");

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(cluster.getPosition().latitude,
                                                cluster.getPosition().longitude), map
                                                .getCameraPosition().zoom + 2), 700,
                                null);

                        return true;
                    }
                });

        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<GasStationMarker>() {

                    @Override
                    public boolean onClusterItemClick(GasStationMarker marker) {
                        station_name.setText(marker.getGasStation().getmTItle());
                        distance.setText(String.format("%.1fkm", marker.getGasStation().getmDistance() / 1000));
                        station_adress.setText(marker.getGasStation().getmAddress());
                        station_phone.setText(PhoneNumberUtils.formatNumber(marker.getGasStation().getmPhone()));

//                        gas.setText(marker.getGasStation().getmTItle());
//                        disel.setText(marker.getGasStation().getmAddress());
//                        hgas.setText(marker.getGasStation().getmPhone());
                        if (marker.getGasStation().isConvstore().equals("Y")) {
                            conv_store.setVisibility(View.VISIBLE);
                        } else {
                            conv_store.setVisibility(View.GONE);
                        }
                        if (marker.getGasStation().isSelf().equals("Y")) {
                            self.setVisibility(View.VISIBLE);
                        } else {
                            self.setVisibility(View.GONE);
                        }
                        if (marker.getGasStation().isDirect().equals("Y")) {
                            direct.setVisibility(View.VISIBLE);
                        } else {
                            direct.setVisibility(View.GONE);
                        }
                        if (marker.getGasStation().isRepair().equals("Y")) {
                            repair.setVisibility(View.VISIBLE);
                        } else {
                            repair.setVisibility(View.GONE);
                        }
                        if (marker.getGasStation().isWash().equals("Y")) {
                            wash.setVisibility(View.VISIBLE);
                        } else {
                            wash.setVisibility(View.GONE);
                        }

                        if(detail_view.getVisibility() == View.GONE) {
                            detail_view.setVisibility(View.VISIBLE);
                            detail_view.startAnimation(slide_up_anim);
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

                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
            }
        });

        if(MainActivity.alist != null){

            for (GasStation gasStation:alist) {
                if (gasStation.getmDistance()/1000 <= 3) {
                    mClusterManager.addItem(new GasStationMarker(new LatLng(gasStation.getmLatitude(), gasStation.getmLongitude()), gasStation));
                }
            }
            mClusterManager.setRenderer(new MyClusterRenderer(getActivity(), map, mClusterManager));
        } else {
            Toast.makeText(getActivity(), "데이터를 아직 받아오지 못했습니다.", Toast.LENGTH_LONG).show();
        }

    }

    class MyClusterRenderer extends DefaultClusterRenderer<GasStationMarker> {

        private static final int MIN_CLUSTER_SIZE = 4;

        public MyClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<GasStationMarker> clusterManager) {
            super(context, map, clusterManager);

        }

        @Override
        protected void onBeforeClusterItemRendered(GasStationMarker item,
                                                   MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);

        }

        @Override
        protected void onClusterItemRendered(GasStationMarker clusterItem,
                                             Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);

            marker.setTitle(clusterItem.getGasStation().getmTItle());
            marker.setSnippet(clusterItem.getGasStation().getIndex() + "");
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), custom_marker)));
//            TextView textView = (TextView) custom_marker.findViewById(R.id.gas_station_title);
//            textView.setText(clusterItem.getGasStation().getmTItle());

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<GasStationMarker> cluster) {
            // start clustering if at least 5 items overlap
            return cluster.getSize() > MIN_CLUSTER_SIZE;
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
    public void onBack() {

        if(detail_view.getVisibility() == View.VISIBLE){
            detail_view.setVisibility(View.GONE);
            detail_view.startAnimation(slide_down_anim);
        } else {
            if (sub_layout.getVisibility() == View.VISIBLE) {
                sub_layout.setVisibility(View.GONE);
                sub_layout.startAnimation(slide_down_anim);
            } else {
                GasStationActivity activity = (GasStationActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                activity.onBackPressed();
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((GasStationActivity) activity).setOnKeyBackPressedListener(this);
    }
}