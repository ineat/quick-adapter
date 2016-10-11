package com.ineat.sample.quickadapter.itemrenderer;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ineat.quickadapter.QuickItemRenderer;
import com.ineat.quickadapter.QuickLayout;
import com.ineat.quickadapter.sample.R;
import com.ineat.sample.quickadapter.Ineatien;

/**
 * Created by mslimani on 08/10/2016.
 */
@QuickLayout(R.layout.item_new_ineatien)
public class NewIneatienQuickItemRenderer extends QuickItemRenderer<Ineatien> {

    private TextView mFirstnameTextView;
    private TextView mLastnameTextView;

    public NewIneatienQuickItemRenderer(View itemView) {
        super(itemView);
        mFirstnameTextView = (TextView) itemView.findViewById(R.id.text_firstname);
        mLastnameTextView = (TextView) itemView.findViewById(R.id.text_lastname);
    }


    @Override
    public void onBind(int position, @NonNull Ineatien ineatien) {
        mFirstnameTextView.setText(ineatien.getFirstName());
        mLastnameTextView.setText(ineatien.getLastName());
    }

}
