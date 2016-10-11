package com.ineat.quickadapter;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Mehdi on 11/10/2016.
 */

public abstract class HeaderFooterQuickItemRenderer extends QuickItemRenderer<Void> {

    public HeaderFooterQuickItemRenderer(View itemView) {
        super(itemView);
    }

    @Override
    public final void onBind(int position, @NonNull Void aVoid) {
        onBind();
    }

    protected abstract void onBind();

}
