package com.ineat.quickadapter;

import java.util.List;

/**
 * Created by mslimani on 08/10/2016.
 */
public final class SimpleQuickAdapter<ITEM, HOLDER extends QuickItemRenderer<ITEM>> extends
        QuickAdapter<ITEM> {

    public SimpleQuickAdapter(List<ITEM> items, Class<HOLDER> holderClass) {
        super(items);
        initSingleHolder(holderClass);
    }

    public SimpleQuickAdapter(Class<HOLDER> holderClass) {
        super();
        initSingleHolder(holderClass);
    }

    private void initSingleHolder(final Class<HOLDER> holderClass) {
        registerHolder(holderClass);
        setQuickAdapterTypeFactory(new QuickAdapterTypeFactory<ITEM, QuickItemRenderer<ITEM>>() {
            @Override
            public Class<? extends QuickItemRenderer<ITEM>> getType(int position, ITEM item) {
                return holderClass;
            }
        });
    }





}
