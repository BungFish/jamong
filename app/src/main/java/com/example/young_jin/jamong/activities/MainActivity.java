package com.example.young_jin.jamong.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.young_jin.jamong.NavigationDrawerFragment;
import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.models.GasStation;
import com.example.young_jin.jamong.network.APIKeyStore;
import com.example.young_jin.jamong.network.APIRequester;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private JSONArray stationList;
    private JSONObject json;
    public static ArrayList<GasStation> alist;
    private LocationManager locationManager;
    private String locationProvider;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationProvider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            location = locationManager.getLastKnownLocation(locationProvider);
        }

        getStationDate();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(getSupportActionBar().getTitle());
        getSupportActionBar().setTitle("");

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getStationDate(){
        JSONObject fields = new JSONObject();
        try {
            fields.put(APIKeyStore.APP_INIT_REQ_UPD_DATE, "");
            fields.put(APIKeyStore.APP_INIT_REQ_OS_GBN, "A");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequester requester = new APIRequester(this);
        JSONObject reqParams = requester.makeRequestParams(null,"COMAppInitDataPub",fields);
        requester.requestPOST(reqParams, new APIRequester.APICallbackListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(Object object) {
                Toast.makeText(MainActivity.this, "데이터를 성공적으로 가져왔습니다..", Toast.LENGTH_LONG).show();

                if (object instanceof JSONObject) {

                    json = (JSONObject) object;
                    addItems();

                }
            }

            @Override
            public void onFail(Error error) {
                Toast.makeText(MainActivity.this, "데이터를 가져오는데 실패하였습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addItems(){
        try {

            alist = new ArrayList<>();

            JSONObject dataSet = json.getJSONObject("dataSet");
            JSONObject recordSets = dataSet.getJSONObject("recordSets");

            JSONObject arr_shop = recordSets.getJSONObject("ARR_SHOP");
            stationList = arr_shop.getJSONArray("nc_list");
            int leng = stationList.length();

            Log.i("==============", stationList.toString());

            for (int i = 0; i < leng; i++) {
                JSONObject station = stationList.getJSONObject(i);

                Location my_location = new Location("my_location");
                my_location.setLatitude(location.getLatitude());
                my_location.setLongitude(location.getLongitude());

                Location station_location = new Location("station_location");
                station_location.setLatitude(Double.valueOf(station.getString("LOC_LAT")));
                station_location.setLongitude(Double.valueOf(station.getString("LOC_LONG")));

                double distance = my_location.distanceTo(station_location);
                alist.add(new GasStation(i,
                        Double.valueOf(station.getString("LOC_LAT")),
                        Double.valueOf(station.getString("LOC_LONG")),
                        station.getString("NM"),
                        distance,
                        station.getString("ADDR"),
                        station.getString("TEL"),
                        station.getString("OPT_CONVSTORE_YN"),
                        station.getString("OPT_SELF_YN"),
                        station.getString("OPT_DIRECT_YN"),
                        station.getString("OPT_REPAIR_YN"),
                        station.getString("OPT_WASH_YN")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
