package com.ineat.quickadapter;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mehdi on 18/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleQuickAdapterTest {

    private static List<String> ITEMS = new ArrayList<>(Arrays.asList(
            "item 1",
            "null",
            null
    ));

    @Test
    public void test() {
        SimpleQuickAdapter<String, SimpleQuickItemRenderer> adapter = new
                SimpleQuickAdapter<>(ITEMS, SimpleQuickItemRenderer.class);

        for (int i = 0; i < ITEMS.size(); i++) {
            final String item = ITEMS.get(i);
            assertEquals(SimpleQuickItemRenderer.class, adapter.getQuickAdapterTypeFactory()
                    .getType(i, item));
        }


        adapter = new SimpleQuickAdapter<>(SimpleQuickItemRenderer.class);
        adapter.setItems(null);
        assertEquals(SimpleQuickItemRenderer.class, adapter.getQuickAdapterTypeFactory()
                .getType(Integer.MAX_VALUE, null));
    }

    @QuickLayout(com.ineat.quickadapter.test.R.layout.item)
    private static class SimpleQuickItemRenderer extends QuickItemRenderer<String> {

        public SimpleQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position, @NonNull String s) {

        }
    }
}
