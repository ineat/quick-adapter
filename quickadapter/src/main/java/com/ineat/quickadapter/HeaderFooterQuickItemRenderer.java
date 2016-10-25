package com.ineat.quickadapter;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by mslimani on 11/10/2016.
 */

abstract class HeaderFooterQuickItemRenderer extends QuickItemRenderer<Void> {

    public HeaderFooterQuickItemRenderer(View itemView) {
        super(itemView);
    }

    @Override
    public final void onBind(int position, @NonNull Void aVoid) {
        throw new OnBindIllegalAccessException();
    }

    protected abstract void onBind();

    static class OnBindIllegalAccessException extends RuntimeException {

        OnBindIllegalAccessException() {
            super("Method onBind not accessible in " +
                    HeaderFooterQuickItemRenderer.class.getName());
        }

    }


}
