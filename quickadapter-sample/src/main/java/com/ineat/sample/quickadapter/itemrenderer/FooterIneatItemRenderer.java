package com.ineat.sample.quickadapter.itemrenderer;

import android.view.View;

import com.ineat.quickadapter.FooterQuickItemRenderer;
import com.ineat.quickadapter.QuickLayout;
import com.ineat.quickadapter.sample.R;

/**
 * Created by mslimani on 11/10/2016.
 */
@QuickLayout(R.layout.item_footer_ineat)
public class FooterIneatItemRenderer extends FooterQuickItemRenderer {

    public FooterIneatItemRenderer(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind() {
        // do nothing
    }

}
