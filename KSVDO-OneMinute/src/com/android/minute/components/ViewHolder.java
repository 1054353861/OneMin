package com.android.minute.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import roboguice.inject.InjectViewListener;

public abstract class ViewHolder<T> {

    private View rootView;
    private T currentData;
    private FleetAdapter<T> adapter;
    
    public abstract int getViewResource();
    public int getDataSetCount() {
        if (getAdapter() == null) {
            return 0;
        }
        return getAdapter().getCount();
    }

    protected void onViewCreated(View createdView) {
        
    }
    
    protected abstract void onUpdateView(int position, T data);

    protected void setRootView(View view) {
        this.rootView = view;
//        InjectViewListener.ViewMembersInjector.injectViews(this, view);
        onViewCreated(view);
    }
    
    public void bindView(Context context) {
        setRootView(LayoutInflater.from(context).inflate(getViewResource(), null, false));
    }
    
    public void updateView(int position, T data) {
        this.currentData = data;
        onUpdateView(position, data);
    }
    
    public View getRootView() {
        return rootView;
    }
    
    public Context getContext() {
        return getRootView().getContext();
    }

    private FleetAdapter<T> getAdapter() {
        return adapter;
    }

    protected void regAdapter(FleetAdapter<T> adapter) {
        this.adapter = adapter;
    }
    public T getCurrentData() {
        return currentData;
    }
    public void setCurrentData(T currentData) {
        this.currentData = currentData;
    }
}
