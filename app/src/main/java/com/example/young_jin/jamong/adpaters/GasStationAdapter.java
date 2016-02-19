package com.example.young_jin.jamong.adpaters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.young_jin.jamong.activities.MainActivity;
import com.example.young_jin.jamong.singleton.GasLab;
import com.example.young_jin.jamong.R;
import com.example.young_jin.jamong.activities.GasStationDetailActivity;
import com.example.young_jin.jamong.models.GasStation;

import java.util.ArrayList;

/**
 * Created by Jin on 2015-06-04.
 */
public class GasStationAdapter extends RecyclerView.Adapter<GasStationAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<GasStation> data;
    private Activity activity;
    private ClickListener clickListener;
    private GasStation current;

    public GasStationAdapter(Activity activity, ArrayList<GasStation> data) {
        inflater = LayoutInflater.from(activity);
        if(data != null){
            this.data = data;
        } else {
            this.data = new ArrayList<GasStation>();
        }
        this.activity = activity;
    }

    public void clearData(){
        this.data.clear();
    }

    public void setData(ArrayList<GasStation> data){
        if(data != null){
            this.data = data;
        }
    }

    public void addData(GasStation gasStation){
        this.data.add(gasStation);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gas_station_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        current = data.get(position);
        holder.title.setText(current.getmTItle());
        holder.distance.setText(String.format("%.1fkm", current.getmDistance() / 1000));

        holder.adress.setText(current.getmAddress());
        if (current.isConvstore().equals("Y")) {
            holder.conv_store.setVisibility(View.VISIBLE);
        } else {
            holder.conv_store.setVisibility(View.GONE);
        }
        if (current.isSelf().equals("Y")) {
            holder.self.setVisibility(View.VISIBLE);
        } else {
            holder.self.setVisibility(View.GONE);
        }
        if (current.isDirect().equals("Y")) {
            holder.direct.setVisibility(View.VISIBLE);
        } else {
            holder.direct.setVisibility(View.GONE);
        }
        if (current.isRepair().equals("Y")) {
            holder.repair.setVisibility(View.VISIBLE);
        } else {
            holder.repair.setVisibility(View.GONE);
        }
        if (current.isWash().equals("Y")) {
            holder.wash.setVisibility(View.VISIBLE);
        } else {
            holder.wash.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(position);
                return false;
            }
        });

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private final TextView conv_store;
        private final TextView self;
        private final TextView direct;
        private final TextView repair;
        private final TextView wash;
        private final TextView distance;
        private TextView title;
        private TextView adress;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            distance = (TextView) itemView.findViewById(R.id.distance);

            adress = (TextView) itemView.findViewById(R.id.adress);
            conv_store = (TextView) itemView.findViewById(R.id.conv_store);
            self = (TextView) itemView.findViewById(R.id.self);
            direct = (TextView) itemView.findViewById(R.id.direct);
            repair = (TextView) itemView.findViewById(R.id.repair);
            wash = (TextView) itemView.findViewById(R.id.wash);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClick(v, getPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

//            menu.setHeaderTitle(getItem(getPosition()).getmTItle());
//            menu.add(0, R.id.menu_item_delete_crime, 0, R.string.delete_crime);//groupId, itemId, order, title
//            menu.add(0, v.getId(), 0, "돌아가기");
        }
    }

    public interface ClickListener {

        public void itemClick(View view, int position);
    }

    public GasStation getItem(int position) {
        return data.get(position);
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
