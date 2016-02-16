package com.example.young_jin.jamong.singleton;

import android.content.Context;

import com.example.young_jin.jamong.models.GasStation;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Young-Jin on 2016-02-08.
 */
public class GasLab {
    private ArrayList<GasStation> mGasStation;

    private static GasLab sGasLab;
    private Context mAppContext;

    public GasLab(Context mAppContext) {
        this.mAppContext = mAppContext;
        mGasStation = new ArrayList<GasStation>();
        for (int i = 0; i < 15; i++) {
            GasStation c = new GasStation();
            c.setmTItle("주유소 #" + i);
            mGasStation.add(c);
        }
    }

    public static GasLab get(Context c){
        if(sGasLab == null){
            sGasLab = new GasLab(c.getApplicationContext());
        }
        return sGasLab;
    }

    public ArrayList<GasStation> getmGasStation(){
        return mGasStation;
    }

    public GasStation getCrime(UUID id){
//        for(GasStation c : mGasStation){
//            if(c.getmID().equals(id))
//                return c;
//        }
        return null;
    }

    public void deleteCrime(GasStation c){
        mGasStation.remove(c);
    }
}
