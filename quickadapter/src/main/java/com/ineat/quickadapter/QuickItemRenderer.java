package com.ineat.quickadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mslimani on 08/10/2016.
 */
public abstract class QuickItemRenderer<ITEM> extends RecyclerView.ViewHolder implements
        IQuickItemRenderer<ITEM> {

    public QuickItemRenderer(View itemView) {
        super(itemView);
    }

    @Override
    public final Context getContext() {
        return super.itemView.getContext();
    }

}
