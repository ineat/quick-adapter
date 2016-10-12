package com.ineat.sample.quickadapter.itemrenderer;

import android.view.View;

import com.ineat.quickadapter.HeaderQuickItemRenderer;
import com.ineat.quickadapter.QuickLayout;
import com.ineat.quickadapter.sample.R;

/**
 * Created by mslimani on 11/10/2016.
 */
@QuickLayout(R.layout.item_header_ineat)
public class HeaderIneatItemRenderer extends HeaderQuickItemRenderer {

    public HeaderIneatItemRenderer(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind() {
        // do nothing
    }
}
