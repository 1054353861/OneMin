package com.android.minute.components;

import java.util.List;

public abstract class DataSetHolder<T> {

    private FleetAdapter<T> adapter;
    
    public void notifyDataSetChanged() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }
    
    public void notifyDataSetInvalidated() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetInvalidated();
        }
    }
    
    public abstract List<T> getDataSet();
    
    public int getCount() {
        List<T> dataSet = getDataSet();
        if (dataSet != null) {
            return dataSet.size();
        }
        return 0;
    }
    
    public T getData(int position) {
        List<T> dataSet = getDataSet();
        if (dataSet != null) {
            return dataSet.get(position);
        }
        return null;
    }
    
    public FleetAdapter<T> getAdapter() {
        return adapter;
    }

    public void regAdapter(FleetAdapter<T> adapter) {
        this.adapter = adapter;
    }
    
}
