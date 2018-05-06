package com.flycode.healthbloom.ui.tags;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Tag;
import com.pchmn.materialchips.ChipView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder>{

    @Setter @Getter
    private List<Tag> tagList;
    @Setter
    private Context context;
    @Setter
    private OnTagDeletedListener onTagDeletedListener;
    @Setter
    private OnTagClickedListener onTagClickedListener;


    TagsAdapter(Context context) {
        this.context = context;
        this.tagList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tag_view,parent,false);
        return new TagsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.ViewHolder holder, int position) {
        final Tag currentTag = tagList.get(position);
//        holder.chip_view.setChip(tagList.get(position));
        holder.chip_view.setLabel(currentTag.Name.get());
        holder.chip_view.setLabelColor(tagList.get(position).PrimaryColor.get() != 0 ?
                tagList.get(position).PrimaryColor.get() : Color.WHITE);

        holder.chip_view.setDeletable(true);
        holder.chip_view.setDeleteIconColor(tagList.get(position).PrimaryColor.get() != 0 ?
                tagList.get(position).PrimaryColor.get() : Color.WHITE);

        holder.chip_view.setChipBackgroundColor(tagList.get(position).SecondaryColor.get() != 0 ?
                tagList.get(position).SecondaryColor.get() : Color.LTGRAY);



        //ON DELETE TAG
        holder.chip_view.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagDeletedListener.onTagDeleted(currentTag);
            }
        });

        //ON UPDATE TAG
        holder.chip_view.setOnChipClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagClickedListener.onTagClicked(currentTag);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    interface OnTagClickedListener {
        void onTagClicked(Tag tag);
    }

    interface OnTagDeletedListener {
        void onTagDeleted(Tag tag);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ChipView chip_view;


        ViewHolder(View itemView) {
            super(itemView);
            chip_view = itemView.findViewById(R.id.chip_view);
        }
    }
}
