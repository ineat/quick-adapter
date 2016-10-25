package com.ineat.quickadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.FrameLayout;

import com.ineat.quickadapter.exceptions.IllegalRegisterItemRendererException;
import com.ineat.quickadapter.exceptions.NotAccessItemRendererException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mslimani on 12/10/2016.
 * run test
 * <pre>
 *     ./gradlew clean createDebugCoverageReport jacocoTestReport
 * </pre>
 */
@RunWith(AndroidJUnit4.class)
public class QuickAdapterTest {

    private static List<String> ITEMS = Arrays.asList("item1", "item2", "item3");
    private QuickAdapter<String> mAdapter;

    @Before
    public void setUp() throws Exception {
        mAdapter = new QuickAdapter<>(ITEMS);
    }

    // region illegal register

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testRegisterItemRenderer_withNullObject() {
        mAdapter.registerItemRenderer(null);
    }

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testRegisterItemRenderer_withoutQuickLayoutAnnotation() {
        mAdapter.registerItemRenderer(NotAnnotationQuickItemRenderer.class);
    }

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testRegisterHeader_withoutAnnotation(){
        mAdapter.registerHeader(HeaderWithoutAnnotationQuickItemRenderer.class);
    }

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testRegisterFooter_withoutAnnotation(){
        mAdapter.registerFooter(FooterWithoutAnnotationQuickItemRenderer.class);
    }

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testGetItemViewType_withoutRegister() {
        mAdapter.getItemViewType(0);
    }

    // endregion illegal register

    // region valid register

    @Test
    public void testRegisterItemRenderer_withValidRenderer() {
        // register
        mAdapter.registerItemRenderer(ATestQuickItemRenderer.class);
        assertEquals(1, mAdapter.getRegisterItemRendererSparseArray().size());
        // re-register
        mAdapter.registerItemRenderer(ATestQuickItemRenderer.class);
        assertEquals(1, mAdapter.getRegisterItemRendererSparseArray().size());

        mAdapter.registerItemRenderer(BTestQuickItemRenderer.class);
        assertEquals(2, mAdapter.getRegisterItemRendererSparseArray().size());
    }



    @Test
    public void testRegisterUnregisterHeaderFooter_withAnnotation() {
        assertEquals(ITEMS.size(), mAdapter.getItemCount());
        // register
        mAdapter.registerHeader(HeaderWithAnnotationQuickItemRenderer.class);
        assertEquals(ITEMS.size() + 1, mAdapter.getItemCount());
        mAdapter.registerFooter(FooterWithAnnotationQuickItemRenderer.class);
        assertEquals(ITEMS.size() + 2, mAdapter.getItemCount());
        mAdapter.unregisterHeader();
        assertEquals(ITEMS.size() + 1, mAdapter.getItemCount());
        mAdapter.unregisterFooter();
        assertEquals(ITEMS.size(), mAdapter.getItemCount());
    }

    // endregion valid register

    // region item view type

    @Test
    public void testGetType() {
        mAdapter.registerItemRenderer(ATestQuickItemRenderer.class);
        mAdapter.registerItemRenderer(BTestQuickItemRenderer.class);
        int typeA = ATestQuickItemRenderer.class.getName().hashCode();
        int typeB = BTestQuickItemRenderer.class.getName().hashCode();
        assertEquals(typeA, mAdapter.getType(ATestQuickItemRenderer.class));
        assertEquals(typeB, mAdapter.getType(BTestQuickItemRenderer.class));
    }

    @Test
    public void testGetItemViewType_withRegisterInnerStatic() {
        mAdapter.registerHeader(HeaderWithAnnotationQuickItemRenderer.class);
        mAdapter.registerFooter(FooterWithAnnotationQuickItemRenderer.class);
        mAdapter.registerItemRenderer(ATestQuickItemRenderer.class);
        mAdapter.setQuickAdapterTypeFactory(new QuickAdapter.QuickAdapterTypeFactory<String, QuickItemRenderer<String>>() {
            @Override
            public Class<? extends QuickItemRenderer<String>> getType(int position, String s) {
                return ATestQuickItemRenderer.class;
            }
        });

        int headerType = mAdapter.getType(HeaderWithAnnotationQuickItemRenderer.class);
        int footerType = mAdapter.getType(FooterWithAnnotationQuickItemRenderer.class);
        int itemType = mAdapter.getType(ATestQuickItemRenderer.class);
        assertEquals(headerType, mAdapter.getItemViewType(0));
        for (int i = 0; i < ITEMS.size(); i++) {
            assertEquals(itemType, mAdapter.getItemViewType(i + 1));
        }

        assertEquals(footerType, mAdapter.getItemViewType(ITEMS.size() + 1));
    }

    @Test(expected = NotAccessItemRendererException.class)
    public void testGetItemViewType_withRegisterInnerNotStatic() {
        final Context context = InstrumentationRegistry.getContext();
        FrameLayout itemContainer = new FrameLayout(context);

        mAdapter.registerItemRenderer(InnerNotStaticTestQuickItemRenderer.class);
        mAdapter.setQuickAdapterTypeFactory(new QuickAdapter.QuickAdapterTypeFactory<String, QuickItemRenderer<String>>() {
            @Override
            public Class<? extends QuickItemRenderer<String>> getType(int position, String s) {
                return InnerNotStaticTestQuickItemRenderer.class;
            }
        });
        mAdapter.onCreateViewHolder(itemContainer, mAdapter.getItemViewType(0));
    }

    // endregion item view type

    // region holder

    @Test(expected = IllegalRegisterItemRendererException.class)
    public void testOnCreateViewHolder() {
        mAdapter.onCreateViewHolder(null, mAdapter.getItemViewType(0));
    }

    @Test
    public void testOnCreateViewHolder_withRegister() {
        final Context context = InstrumentationRegistry.getContext();
        FrameLayout itemContainer = new FrameLayout(context);
        mAdapter.registerHeader(HeaderWithAnnotationQuickItemRenderer.class);
        mAdapter.registerFooter(FooterWithAnnotationQuickItemRenderer.class);
        mAdapter.registerItemRenderer(ATestQuickItemRenderer.class);
        mAdapter.setQuickAdapterTypeFactory(new QuickAdapter.QuickAdapterTypeFactory<String, QuickItemRenderer<String>>() {
            @Override
            public Class<? extends QuickItemRenderer<String>> getType(int position, String s) {
                return ATestQuickItemRenderer.class;
            }
        });
        QuickItemRenderer itemRenderer = mAdapter.onCreateViewHolder(itemContainer, mAdapter
                .getItemViewType(0));
        assertEquals(HeaderWithAnnotationQuickItemRenderer.class, itemRenderer.getClass());

        itemRenderer = mAdapter.onCreateViewHolder(itemContainer, mAdapter
                .getItemViewType(mAdapter.getItemCount() - 1));
        assertEquals(FooterWithAnnotationQuickItemRenderer.class, itemRenderer.getClass());

        for (int i = 1; i < ITEMS.size(); i++) {
            itemRenderer = mAdapter.onCreateViewHolder(itemContainer, mAdapter
                    .getItemViewType(i));
            assertEquals(ATestQuickItemRenderer.class, itemRenderer.getClass());
        }
    }

    // endregion holder

    // region items

    @Test
    public void testSetItems() {
        mAdapter.setItems(Collections.<String>emptyList());
        assertEquals(0, mAdapter.getItemCount());
        mAdapter.setItems(null);
        assertEquals(0, mAdapter.getItemCount());
        mAdapter.setItems(ITEMS);
        assertEquals(ITEMS.size(), mAdapter.getItemCount());
    }

    @Test
    public void testAddItem() {
        final String newItem = "New Item";
        mAdapter.setItems(new ArrayList<>(ITEMS));
        mAdapter.addItem(newItem);
        assertEquals(newItem, mAdapter.getItemAtPosition(mAdapter.getItemCount() - 1));

        final String newFirstItem = "First !!";
        mAdapter.addItem(0, newFirstItem);
        assertEquals(newFirstItem, mAdapter.getItemAtPosition(0));

        final String newMiddleItem = "Middle";
        mAdapter.addItem(2, newMiddleItem);
        assertEquals(newMiddleItem, mAdapter.getItemAtPosition(2));

        mAdapter.removeItems();
        mAdapter.addItems(ITEMS);
        assertEquals(ITEMS.size(), mAdapter.getItemCount());
    }

    @Test
    public void testRemoveItem() {
        mAdapter.setItems(new ArrayList<>(ITEMS));
        mAdapter.removeItem(0);
        assertEquals(ITEMS.get(1), mAdapter.getItemAtPosition(0));
    }

    @Test
    public void testRemoveItems() {
        // Arrays.asList cannot call clear()
        mAdapter.setItems(new ArrayList<>(ITEMS));
        assertEquals(ITEMS.size(), mAdapter.getItemCount());

        mAdapter.removeItems();
        assertEquals(0, mAdapter.getItemCount());

        mAdapter.removeItems();
        assertEquals(0, mAdapter.getItemCount());
    }

    @Test
    public void testSwap() {
        mAdapter.setItems(new ArrayList<>(ITEMS));
        assertEquals(ITEMS.get(0), mAdapter.getItemAtPosition(0));
        assertEquals(ITEMS.get(1), mAdapter.getItemAtPosition(1));
        mAdapter.swap(0, 1);
        assertEquals(ITEMS.get(0), mAdapter.getItemAtPosition(1));
        assertEquals(ITEMS.get(1), mAdapter.getItemAtPosition(0));
    }



    // endregion items

    // region inner renderer

    private static class NotAnnotationQuickItemRenderer extends QuickItemRenderer<String> {

        public NotAnnotationQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position, @NonNull String s) {

        }
    }


    @QuickLayout(com.ineat.quickadapter.test.R.layout.item)
    private static class ATestQuickItemRenderer extends QuickItemRenderer<String> {

        public ATestQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position, @NonNull String s) {
            // do nothing
        }
    }

    private static class BTestQuickItemRenderer extends ATestQuickItemRenderer {

        public BTestQuickItemRenderer(View itemView) {
            super(itemView);
        }
    }

    private static class HeaderWithoutAnnotationQuickItemRenderer extends com.ineat.quickadapter
            .HeaderQuickItemRenderer {

        public HeaderWithoutAnnotationQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind() {

        }
    }

    @QuickLayout(com.ineat.quickadapter.test.R.layout.header_item)
    private static class HeaderWithAnnotationQuickItemRenderer extends com.ineat.quickadapter
            .HeaderQuickItemRenderer {

        public HeaderWithAnnotationQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind() {

        }
    }

    private static class FooterWithoutAnnotationQuickItemRenderer extends com.ineat.quickadapter
            .FooterQuickItemRenderer {

        public FooterWithoutAnnotationQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind() {

        }
    }

    @QuickLayout(com.ineat.quickadapter.test.R.layout.footer_item)
    private static class FooterWithAnnotationQuickItemRenderer extends com.ineat.quickadapter
            .FooterQuickItemRenderer {

        public FooterWithAnnotationQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind() {

        }
    }

    @QuickLayout(com.ineat.quickadapter.test.R.layout.item)
    private class InnerNotStaticTestQuickItemRenderer extends QuickItemRenderer<String> {

        public InnerNotStaticTestQuickItemRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position, @NonNull String s) {
            // do nothing
        }
    }

    // endregion inner renderer

    @Test
    public void testIsAutoNotify() {
        mAdapter.setAutoNotify(true);
        assertTrue(mAdapter.isAutoNotify());
        mAdapter.setAutoNotify(false);
        assertFalse(mAdapter.isAutoNotify());
    }

}