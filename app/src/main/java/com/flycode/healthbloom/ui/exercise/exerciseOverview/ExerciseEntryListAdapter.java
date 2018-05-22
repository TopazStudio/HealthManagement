package com.flycode.healthbloom.ui.exercise.exerciseOverview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Steps;

import java.util.Calendar;
import java.util.List;

import lombok.Setter;

public class ExerciseEntryListAdapter extends RecyclerView.Adapter<ExerciseEntryListAdapter.ViewHolder>{

    @Setter
    private List<Steps> listItems;
    @Setter
    private Context context;
    @Setter
    private OnCardViewClicked onCardViewClicked;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.exercise_entry_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Steps s = listItems.get(position);
        holder.tv_calories.setText(String.valueOf(s.Calories.get()));
        holder.tv_distance.setText(String.valueOf(s.Distance.get()));

        holder.tv_calories_units.setText(s.CalorieUnits.get());
        holder.tv_distance_units.setText(s.DistanceUnits.get());

        Calendar cal = Calendar.getInstance();
        cal.setTime(s.EndTime.get());

        holder.tv_day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.tv_mon_yr.setText(String.valueOf(cal.get(Calendar.MONTH) + ", " + cal.get(Calendar.YEAR) ));

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardViewClicked.onClicked(s);
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
        TextView tv_calories;
        TextView tv_distance;
        TextView tv_calories_units;
        TextView tv_distance_units;
        CardView card_view;


        ViewHolder(View itemView) {
            super(itemView);

            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_mon_yr = (TextView) itemView.findViewById(R.id.tv_mon_yr);
            tv_calories = (TextView) itemView.findViewById(R.id.tv_calories);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            tv_calories_units = (TextView) itemView.findViewById(R.id.tv_calories_units);
            tv_distance_units = (TextView) itemView.findViewById(R.id.tv_distance_units);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    interface OnCardViewClicked{
        void onClicked(Steps steps);
    }
}
