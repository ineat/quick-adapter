package com.ineat.quickadapter;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mehdi on 18/10/2016.
 */
@RunWith(AndroidJUnit4.class)
public class HeaderFooterQuickItemRendererTest {

    private HeaderFooterQuickItemRenderer mRenderer;

    @Before
    public void setUp() throws Exception {
        final View view = new View(InstrumentationRegistry.getContext());
        mRenderer = new HeaderFooterQuickItemRenderer(view) {
            @Override
            protected void onBind() {
                // do nothing
            }
        };
    }

    @Test(expected = HeaderFooterQuickItemRenderer.OnBindIllegalAccessException.class)
    public void testOnBind_CannotBeCalled() throws Exception{
        mRenderer.onBind(0, null);
    }

}
