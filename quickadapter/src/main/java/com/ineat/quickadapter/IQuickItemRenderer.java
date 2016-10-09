package com.ineat.quickadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Mehdi on 08/10/2016.
 */

interface IQuickItemRenderer<ITEM> {

    Context getContext();
    void onCreate(View view);
    void onBind(int position, @NonNull ITEM item);

}
