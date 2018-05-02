package com.flycode.healthbloom.customUI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flycode.healthbloom.R;

public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ViewHolder>{
    private int maximumSize;
    private Context context;
    private String typeOfUnit;

    public ScaleAdapter(Context context, int maximumSize, String typeOfUnit) {
        this.maximumSize = maximumSize;
        this.context = context;
        this.typeOfUnit = typeOfUnit;
    }

    @NonNull
    @Override
    public ScaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.scale_unit,parent,false);
        return new ScaleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScaleAdapter.ViewHolder holder, int position) {
        holder.one_unit_value.setText(String.valueOf((position + 1) + typeOfUnit));
    }

    @Override
    public int getItemCount() {
        return maximumSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView one_unit_value;

        ViewHolder(View itemView) {
            super(itemView);
            one_unit_value = (TextView) itemView.findViewById(R.id.one_unit_value);
        }
    }
}
