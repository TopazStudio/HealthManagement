package com.flycode.healthbloom.customUI;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.Setter;

public class WeightScaleSpacer extends RecyclerView.ItemDecoration {
    @Setter
    private int Offset = 0;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //TODO: check if first or last item
        if (parent.getChildAdapterPosition(view) == 0) {
            //FIRST CHILD
            outRect.left = Offset;
        }else if (parent.getChildAdapterPosition(view) == (state.getItemCount() - 1)){
            //LAST CHILD
            outRect.right = Offset;
        }

    }
}
