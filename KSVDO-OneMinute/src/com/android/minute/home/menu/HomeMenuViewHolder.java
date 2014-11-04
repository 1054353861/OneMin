package com.android.minute.home.menu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.minute.HomeActivity;
import com.android.minute.R;
import com.android.minute.components.ViewHolder;
import com.android.minute.model.DrawerItem;

import roboguice.inject.annotation.InjectView;

/**
 * Created by xiangyi.wu on 2014/11/4.
 */
public class HomeMenuViewHolder extends ViewHolder<DrawerItem>{
    @InjectView(R.id.drawer_item_icon)
    private ImageView mDrawerItemIcon;
    @InjectView(R.id.drawer_item_name)
    private TextView mDrawerItemName;
    private View view = null;
    private void initView(DrawerItem data){
        view  = HomeActivity.mView;
        mDrawerItemIcon = (ImageView)view.findViewById(R.id.drawer_item_icon);
        mDrawerItemName = (TextView)view.findViewById(R.id.drawer_item_name);
        mDrawerItemIcon.setImageResource(data.getImg());
        mDrawerItemName.setText(data.getName());
    }
    @Override
    public int getViewResource() {
        return R.layout.drawer_list_item;
    }
    @Override
    protected void onUpdateView(int position, DrawerItem data) {
        initView(data);
    }

}
