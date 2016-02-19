package com.example.young_jin.jamong.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.young_jin.jamong.adpaters.FragmentAdpater;
import com.example.young_jin.jamong.fragments.GasStationMapFragment;
import com.example.young_jin.jamong.R;


public class GasStationActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private FragmentAdpater mFragmentAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Toolbar toolbar2;
    private TextView toolbar_title2;
    private TextView toolbar_title;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(getSupportActionBar().getTitle());
        getSupportActionBar().setTitle("");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mFragmentAdapter = new FragmentAdpater(getSupportFragmentManager());
        GasStationMapFragment gasStationMapFragment = GasStationMapFragment.newInstance();
        mFragmentAdapter.addFragment(gasStationMapFragment, "지도");
//        mFragmentAdapter.addFragment(GasStationListFragment.newInstance(), "목록");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mFragmentAdapter);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gas_station, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_station_list) {
            Intent intent = new Intent(this, GasStationListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);

            overridePendingTransition(R.anim.slide_up, R.anim.hold);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
        } else {

            super.onBackPressed();

        }

    }

    public interface onKeyBackPressedListener {
        public void onBack();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }
}
