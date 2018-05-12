package com.flycode.healthbloom.customUI;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flycode.healthbloom.R;

import lombok.Setter;

public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ViewHolder>{
    private Context context;

    @Setter
    private int maximumSize;
    @Setter
    private String typeOfUnit;
    @Setter
    private int backgroundColor = Color.TRANSPARENT;

    public static final int HORIZONTAL_ORIENTATION = 1;
    public static final int VERTICAL_ORIENTATION = 2;
    private int currentOrientation;
    private View view;

    public ScaleAdapter(Context context,int orientation) {
        this.context = context;
        this.currentOrientation = orientation;
    }

    @NonNull
    @Override
    public ScaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (currentOrientation == 1)
            view = LayoutInflater.from(context)
                    .inflate(R.layout.horizontal_scale_unit,parent,false);
        else
            view = LayoutInflater.from(context)
                    .inflate(R.layout.vertical_scale_unit,parent,false);

        return new ScaleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScaleAdapter.ViewHolder holder, int position) {
        holder.one_unit_value.setText(String.valueOf((position + 1) + typeOfUnit));
        holder.main_view.setBackgroundColor(backgroundColor);
    }

    @Override
    public int getItemCount() {
        return maximumSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView one_unit_value;
        RelativeLayout main_view;

        ViewHolder(View itemView) {
            super(itemView);
            one_unit_value = (TextView) itemView.findViewById(R.id.one_unit_value);
            main_view = (RelativeLayout) itemView.findViewById(R.id.main_view);
        }
    }
}
