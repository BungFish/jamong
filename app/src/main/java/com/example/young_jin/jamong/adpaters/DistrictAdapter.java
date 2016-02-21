package com.example.young_jin.jamong.adpaters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.young_jin.jamong.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Young-Jin on 2016-02-21.
 */
public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<String> data;
    private Activity activity;
    private ClickListener clickListener;
    private String current;

    private int selectedItem = -1;

    public DistrictAdapter(Activity activity) {
        inflater = LayoutInflater.from(activity);
        this.activity = activity;

        this.data = new ArrayList<String>(Arrays.asList(activity.getResources().getStringArray(R.array.district)));
    }

    public void setData(int id){
        Log.i("========", id + "");
        this.data = new ArrayList<String>(Arrays.asList(activity.getResources().getStringArray(id)));
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.city_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        current = data.get(position);
        holder.title.setText(current);

        if(selectedItem == position){
            holder.title.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.city_list_item.setBackgroundColor(activity.getResources().getColor(R.color.second_background));
//            holder.check_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu_manage));
        } else {
            holder.title.setTextColor(activity.getResources().getColor(R.color.primary_text));
            holder.city_list_item.setBackgroundColor(activity.getResources().getColor(R.color.icons));
//            holder.check_image.setImageDrawable(null);
        }

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout city_list_item;
        private final ImageView check_image;
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.city_title);
            city_list_item = (LinearLayout) itemView.findViewById(R.id.city_list_item);
            check_image = (ImageView) itemView.findViewById(R.id.check_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            selectedItem = getPosition();
            notifyDataSetChanged();

            if (clickListener != null) {
                clickListener.itemClick(v, getPosition());
            }

        }
    }

    public interface ClickListener {

        public void itemClick(View view, int position);
    }

    public String getItem(int position) {
        return data.get(position);
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void clearSelected(){
        selectedItem = -1;
    }


}
