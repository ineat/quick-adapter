package com.ineat.quickadapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mslimani on 08/10/2016.
 */
public class QuickAdapter<ITEM> extends RecyclerView.Adapter<QuickItemRenderer<ITEM>> {

    private final SparseArray<Class<? extends QuickItemRenderer<ITEM>>> mViewTypeSparseArray;
    private final SparseArray<Class<? extends QuickItemRenderer<ITEM>>> mCacheHashClassSparseArray;
    private QuickAdapterTypeFactory<ITEM, QuickItemRenderer<ITEM>> mQuickAdapterTypeFactory;
    private List<ITEM> mItems;
    private boolean mAutoNotify;

    public QuickAdapter() {
        mViewTypeSparseArray = new SparseArray<>();
        mCacheHashClassSparseArray = new SparseArray<>();
        mItems = new ArrayList<>();
    }

    public QuickAdapter(@NonNull List<ITEM> items) {
        this();
        mItems = items;
    }

    public QuickAdapter(@NonNull List<ITEM> items, QuickAdapterTypeFactory<ITEM, QuickItemRenderer<ITEM>>
            quickAdapterTypeFactory) {
        this(items);
        mQuickAdapterTypeFactory = quickAdapterTypeFactory;
    }

    public void setQuickAdapterTypeFactory(QuickAdapterTypeFactory<ITEM, QuickItemRenderer<ITEM>>
                                                   quickAdapterTypeFactory) {
        mQuickAdapterTypeFactory = quickAdapterTypeFactory;
    }

    @Override
    public int getItemViewType(int position) {
        if (mQuickAdapterTypeFactory == null) {
            throw new IllegalArgumentException("QuickAdapterTypeFactory not exist, call method : " +
                    "setQuickAdapterTypeFactory(...)");
        }

        Class<? extends QuickItemRenderer<ITEM>> holderClass = mQuickAdapterTypeFactory.getType
                (position, getItemAtPosition(position));
        int type = getType(holderClass);
        mCacheHashClassSparseArray.put(type, holderClass);
        return type;
    }

    @Override
    public QuickItemRenderer<ITEM> onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Class<? extends QuickItemRenderer<ITEM>> holderClass = mViewTypeSparseArray.get(viewType);
        if (holderClass == null) {
            throw new IllegalArgumentException(mCacheHashClassSparseArray.get(viewType)
                    .getSimpleName() + " is not registered");
        }

        QuickLayout quickLayout = holderClass.getAnnotation(QuickLayout.class);
        if (quickLayout == null) {
            throw new IllegalArgumentException(holderClass + " is not annoted by " + QuickLayout
                    .class.getSimpleName());
        }

        @LayoutRes int layoutRes = quickLayout.value();
        final View view = inflater.inflate(layoutRes, parent, false);
        QuickItemRenderer<ITEM> holder;
        try {
            holder = holderClass.getDeclaredConstructor(View.class).newInstance(view);
            holder.onCreate(holder.itemView);
        } catch (Exception e) {
            throw new IllegalArgumentException(holderClass.getSimpleName() + " doit être " +
                    "accessible ainsi qu'un seul constructeur public avec un seul paramètre " +
                    "Holder(android.view.View view)");
        }
        return holder;
    }

    public boolean isAutoNotify() {
        return mAutoNotify;
    }

    public void setAutoNotify(boolean autoNotify) {
        mAutoNotify = autoNotify;
    }

    public void registerHolder(Class<? extends QuickItemRenderer<ITEM>> holderClass) {
        if (!holderClass.isAnnotationPresent(QuickLayout.class)) {
            throw new IllegalArgumentException(holderClass + " is not annoted by " + QuickLayout
                    .class.getSimpleName());
        }

        final QuickLayout quickLayout = holderClass.getAnnotation(QuickLayout.class);
        registerType(getType(holderClass), holderClass);
    }

    @Override
    public void onBindViewHolder(QuickItemRenderer<ITEM> holder, int position) {
        final ITEM item = getItemAtPosition(position);
        holder.onBind(position, item);
    }

    public ITEM getItemAtPosition(int position) {
        final int count = getItemCount();
        if (count == 0 || position >= count) {
            return null;
        }
        return mItems.get(position);
    }

    public void setItems(List<ITEM> items) {
        mItems = items;
        if (mAutoNotify) {
            notifyDataSetChanged();
        }
    }

    public void addItem(@NonNull ITEM item) {
        int position = getItemCount();
        addItem(position, item);
    }

    public void addItem(int position, @NonNull ITEM item) {
        mItems.add(position, item);
        if (mAutoNotify) {
            notifyItemInserted(position);
        }
    }

    public void addItems(@NonNull List<ITEM> items) {
        final int count = getItemCount();
        mItems.addAll(items);
        if (mAutoNotify) {
            notifyItemRangeInserted(count, items.size());
        }
    }

    public void swap(int first, int end) {
        Collections.swap(mItems, first, end);
        if (mAutoNotify) {
            notifyItemMoved(first, end);
        }
    }

    public void removeItem(int position) {
        mItems.remove(position);
        if (mAutoNotify) {
            notifyItemRemoved(position);
        }
    }

    public void removeItems() {
        final int count = getItemCount();
        if (count == 0) {
            return;
        }

        mItems.clear();

        if (mAutoNotify) {
            for (int i = count - 1; i > 0; i--) {
                notifyItemRangeRemoved(0, count);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void registerType(int type, Class<? extends QuickItemRenderer<ITEM>> holderClass) {
        mViewTypeSparseArray.put(type, holderClass);
        if (mAutoNotify) {
            notifyDataSetChanged();
        }
    }

    private int getType(Class<? extends IQuickItemRenderer<ITEM>> holderClass) {
        return holderClass.getName().hashCode();
    }


    public interface QuickAdapterTypeFactory<ITEM, HOLDER> {
        Class<? extends HOLDER> getType(int position, ITEM item);
    }

}
