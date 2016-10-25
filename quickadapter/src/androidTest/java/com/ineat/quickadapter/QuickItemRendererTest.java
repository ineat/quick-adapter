package com.ineat.quickadapter;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mehdi on 18/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class QuickItemRendererTest {


    @Test
    public void testGetContext() {
        final View view = new View(InstrumentationRegistry.getContext());
        QuickItemRenderer<Void> renderer = createRenderer(view);
        assertEquals(view.getContext(), renderer.getContext());
    }

    private QuickItemRenderer<Void> createRenderer(View view) {
        return new QuickItemRenderer<Void>(view) {
            @Override
            public void onBind(int position, @NonNull Void aVoid) {
                // do nothing
            }
        };
    }
}
