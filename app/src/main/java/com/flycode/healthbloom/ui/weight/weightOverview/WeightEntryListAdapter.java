package com.flycode.healthbloom.ui.weight.weightOverview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Calendar;
import java.util.List;

import lombok.Setter;

public class WeightEntryListAdapter extends RecyclerView.Adapter<WeightEntryListAdapter.ViewHolder>{

    @Setter
    private List<WeightMeasurement> listItems;
    @Setter
    private Context context;
    @Setter
    private OnCardViewClicked onCardViewClicked;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.weight_entry_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WeightMeasurement w = listItems.get(position);
        holder.tv_weight.setText(String.valueOf(w.Weight.get()));
        holder.tv_bmi.setText(String.valueOf(w.BMI.get()));

        holder.tv_weight_units.setText("kg(s)");
        holder.tv_bmi_units.setText("kg/m");

        Calendar cal = Calendar.getInstance();
        cal.setTime(w.Date.get());

        holder.tv_day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.tv_mon_yr.setText(String.valueOf(cal.get(Calendar.MONTH) + ", " + cal.get(Calendar.YEAR) ));

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardViewClicked.onClicked(w);
            }
        });
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
        TextView tv_weight_units;
        TextView tv_bmi_units;

        public FlexboxLayout view_foreground;
        LinearLayout view_background;
        CardView card_view;


        ViewHolder(View itemView) {
            super(itemView);

            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_mon_yr = (TextView) itemView.findViewById(R.id.tv_mon_yr);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_bmi = (TextView) itemView.findViewById(R.id.tv_bmi);
            tv_weight_units = (TextView) itemView.findViewById(R.id.tv_weight_units);
            tv_bmi_units = (TextView) itemView.findViewById(R.id.tv_bmi_units);
            view_foreground = (FlexboxLayout) itemView.findViewById(R.id.view_foreground);
            view_background = (LinearLayout) itemView.findViewById(R.id.view_background);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public void removeItem(int position) {
        listItems.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(WeightMeasurement weightMeasurement, int position) {
        listItems.add(position, weightMeasurement);
        notifyItemInserted(position);
    }

    interface OnCardViewClicked{
        void onClicked(WeightMeasurement weightMeasurement);
    }
}
