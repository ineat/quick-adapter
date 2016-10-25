package com.ineat.sample.quickadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ineat.quickadapter.FooterQuickItemRenderer;
import com.ineat.quickadapter.QuickAdapter;
import com.ineat.quickadapter.QuickLayout;
import com.ineat.quickadapter.SimpleQuickAdapter;
import com.ineat.quickadapter.sample.R;
import com.ineat.sample.quickadapter.data.Provider;
import com.ineat.sample.quickadapter.itemrenderer.FooterIneatItemRenderer;
import com.ineat.sample.quickadapter.itemrenderer.HeaderIneatItemRenderer;
import com.ineat.sample.quickadapter.itemrenderer.IneatienQuickItemRenderer;
import com.ineat.sample.quickadapter.itemrenderer.NewIneatienQuickItemRenderer;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region android lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        final List<Ineatien> ineatienList = Arrays.asList(Provider.INEATIENS);
        SimpleQuickAdapter<Ineatien, IneatienQuickItemRenderer> simpleQuickAdapter =
                new SimpleQuickAdapter<>(ineatienList, IneatienQuickItemRenderer.class);
        simpleQuickAdapter.registerHeader(HeaderIneatItemRenderer.class);
        simpleQuickAdapter.registerFooter(FooterIneatItemRenderer.class);
        //recyclerView.setAdapter(simpleQuickAdapter);
        /*simpleQuickAdapter.setOnItemClickListener((rv, view, position, ineatien) -> {
            Toast.makeText(MainActivity.this, ineatien.toString(), Toast.LENGTH_LONG).show();
        });*/

        //Mode multi cell

        QuickAdapter<Ineatien> quickAdapter = new QuickAdapter<>(ineatienList);
        quickAdapter.registerItemRenderer(IneatienQuickItemRenderer.class);
        quickAdapter.registerItemRenderer(NewIneatienQuickItemRenderer.class);
        quickAdapter.setQuickAdapterTypeFactory((position, ineatien) -> {
            if (ineatien.isNew()) {
                return NewIneatienQuickItemRenderer.class;
            }

            return IneatienQuickItemRenderer.class;
        });
        quickAdapter.registerHeader(HeaderIneatItemRenderer.class);
        quickAdapter.registerFooter(FooterIneatItemRenderer2.class);
        recyclerView.setAdapter(quickAdapter);

    }
    //endregion android lifecycle

    @QuickLayout(R.layout.item_footer_ineat)
    private static class FooterIneatItemRenderer2 extends FooterQuickItemRenderer {

        private FooterIneatItemRenderer2(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind() {
            // do nothing
        }

    }
}
