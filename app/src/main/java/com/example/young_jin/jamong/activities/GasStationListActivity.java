package com.example.young_jin.jamong.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.fragments.GasStationListFragment;

/**
 * Created by slogup on 2016. 2. 17..
 */
public class GasStationListActivity extends ActionBarActivity {

    private Button filter_distance;
    private Button filter_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_gas_station_list, GasStationListFragment.newInstance());

        filter_distance = (Button) toolbar.findViewById(R.id.filter_distance);
        filter_price = (Button) toolbar.findViewById(R.id.filter_price);

        filter_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_distance.setBackgroundColor(getResources().getColor(R.color.icons));
                filter_distance.setTextColor(getResources().getColor(R.color.colorPrimary));

                filter_price.setBackgroundColor(getResources().getColor(R.color.colorPrimary));;
                filter_price.setTextColor(getResources().getColor(R.color.icons));
            }
        });

        filter_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_distance.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                filter_distance.setTextColor(getResources().getColor(R.color.icons));

                filter_price.setBackgroundColor(getResources().getColor(R.color.icons));
                filter_price.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

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

        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold_short, R.anim.slide_down);
    }
}
