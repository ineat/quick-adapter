package com.ineat.sample.quickadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ineat.quickadapter.QuickAdapter;
import com.ineat.quickadapter.SimpleQuickAdapter;
import com.ineat.quickadapter.sample.R;
import com.ineat.sample.quickadapter.data.Provider;
import com.ineat.sample.quickadapter.itemrenderer.IneatienQuickItemRenderer;
import com.ineat.sample.quickadapter.itemrenderer.NewIneatienQuickItemRenderer;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    //region android lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        List<Ineatien> ineatienList = Arrays.asList(Provider.INEATIENS);
        SimpleQuickAdapter<Ineatien, IneatienQuickItemRenderer> simpleQuickAdapter =
                new SimpleQuickAdapter<>(ineatienList, IneatienQuickItemRenderer.class);



        QuickAdapter<Ineatien> quickAdapter = new QuickAdapter<>(ineatienList);
        quickAdapter.registerHolder(IneatienQuickItemRenderer.class);
        quickAdapter.registerHolder(NewIneatienQuickItemRenderer.class);
        quickAdapter.setQuickAdapterTypeFactory((position, ineatien) -> {
            if (ineatien.isNew()) {
                return NewIneatienQuickItemRenderer.class;
            }

            return IneatienQuickItemRenderer.class;
        });

        mRecyclerView.setAdapter(quickAdapter);
    }
    //endregion android lifecycle

}