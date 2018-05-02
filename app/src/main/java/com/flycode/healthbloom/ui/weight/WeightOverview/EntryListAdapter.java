package com.flycode.healthbloom.ui.weight.WeightOverview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;

import java.util.List;

import lombok.Setter;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.ViewHolder>{

    @Setter
    private List<WeightMeasurement> listItems;
    @Setter
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.weight_entry_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeightMeasurement w = listItems.get(position);
        holder.tv_weight.setText(String.valueOf(w.Weight.get()));
        holder.tv_bmi.setText(String.valueOf(w.BMI.get()));

        /*Calendar cal = Calendar.getInstance();
        cal.setTime(w.Date);

        holder.tv_day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.tv_mon_yr.setText(String.valueOf(cal.get(Calendar.MONTH) + ", " + cal.get(Calendar.YEAR) ));*/
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_day;
        TextView tv_mon_yr;
        TextView tv_weight;
        TextView tv_bmi;
        ImageView progress_pic;


        ViewHolder(View itemView) {
            super(itemView);

            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_mon_yr = (TextView) itemView.findViewById(R.id.tv_mon_yr);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_bmi = (TextView) itemView.findViewById(R.id.tv_bmi);
        }
    }
}
