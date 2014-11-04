package com.android.minute.components;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class FleetAdapter<T> extends BaseAdapter {
    
    private LayoutInflater layoutInflater;
    private DataSetHolder<T> dataSetHolder;
    private ListView listView;
    
    {
        getDataSetHolderInternal();
    }

    public int getCount() {
        if (getDataSetHolderInternal() != null) {
            return getDataSetHolderInternal().getCount();
        }
        return 0;
    }

    public T getItem(int position) {
        if (getDataSetHolderInternal() != null) {
            return getDataSetHolderInternal().getData(position);
        }
        return null;
    }
    
    private synchronized DataSetHolder<T> getDataSetHolderInternal() {
        if (dataSetHolder == null) {
            dataSetHolder = getDataSetHoler();
            dataSetHolder.regAdapter(this);
        }
        return dataSetHolder;
    }
    
    public abstract DataSetHolder<T> getDataSetHoler();

    public long getItemId(int position) {
        return position;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (listView == null) {
            listView = (ListView) parent;
        }
        if (convertView == null) {
            ViewHolder<T> viewHolder = getViewHolder(position);
            viewHolder.regAdapter(this);
            convertView = getLayoutInflater(parent.getContext()).inflate(viewHolder.getViewResource(), parent, false);
            viewHolder.setRootView(convertView);
            convertView.setTag(viewHolder);
        }
        updateView(position, convertView);
        return convertView;
    }
    
    
    protected abstract ViewHolder<T> getViewHolder(int position);

    @SuppressWarnings("unchecked")
    private void updateView(int position, View convertView) {
        ViewHolder<T> viewHolder  = (ViewHolder<T>) convertView.getTag();
        if (viewHolder != null) {
            viewHolder.updateView(position, getItem(position));
        }
    }
    
    public void updateView(int position) {
        if (listView ==  null) return;
        View selectedView = listView.getChildAt(position - listView.getFirstVisiblePosition());
        if (selectedView == null) {
            return;
        }
        updateView(position, selectedView);
    }
    
    private LayoutInflater getLayoutInflater(Context context) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        }
        return layoutInflater;
    }
}
