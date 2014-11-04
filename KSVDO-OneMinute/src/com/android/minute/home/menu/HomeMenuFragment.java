package com.android.minute.home.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.minute.HomeActivity;
import com.android.minute.R;
import com.android.minute.components.BaseFragment;
import com.android.minute.components.DataSetHolder;
import com.android.minute.components.FleetAdapter;
import com.android.minute.components.ViewHolder;
import com.android.minute.favorite.FavoriteFragment;
import com.android.minute.follow.FollowFragment;
import com.android.minute.model.DrawerItem;
import com.android.minute.setting.SettingFragment;
import com.android.minute.video.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyi.wu on 2014/11/3.
 */
public class HomeMenuFragment extends BaseFragment {
    private ListView mListView;
    private List<DrawerItem> mDrawerItems;
    private View mView;
    private DrawerAdapter mDrawerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.home_menu_fragment,container,false);
        initView(mView);
        turnToVideoListFragment();
        setListener();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDrawerList();
    }

    private void initView(View view){
        mDrawerItems = new ArrayList<DrawerItem>();
        mListView = (ListView) view.findViewById(R.id.drawer_lv);
        mDrawerAdapter = new DrawerAdapter();
        mListView.setAdapter(mDrawerAdapter);
    }

    private void getDrawerList() {
        mDrawerItems.clear();
        mDrawerItems.add(new DrawerItem(getString(R.string.menu_guanzhu), R.drawable.menu_guanzhu));
        mDrawerItems.add(new DrawerItem(getString(R.string.menu_fav),R.drawable.menu_fav));
        mDrawerItems.add(new DrawerItem(getString(R.string.menu_setting), R.drawable.menu_setting));
        mDrawerItems.add(new DrawerItem(getString(R.string.menu_about),R.drawable.menu_about));
        mDrawerAdapter.notifyDataSetChanged();
    }

    private void setListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        closeDrawer();
                        turnToVideoListFragment();
                        break;
                    case 1:
                        closeDrawer();
                        turnToFollowFragment();
                        break;
                    case 2:
                        closeDrawer();
                        turnToFavoriteFragment();
                        break;
                    case 3:
                        closeDrawer();
                        turnToSettingFragment();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void turnToVideoListFragment(){
        ((HomeActivity) getActivity()).turnToFragment(getFragmentManager(), VideoListFragment.class, VideoListFragment.TAG, null, true);
    }
    private void turnToFollowFragment(){
        ((HomeActivity) getActivity()).turnToFragment(getFragmentManager(), FollowFragment.class, FollowFragment.TAG, null, true);
    }
    private void turnToFavoriteFragment(){
        ((HomeActivity) getActivity()).turnToFragment(getFragmentManager(), FavoriteFragment.class, FavoriteFragment.TAG, null, true);
    }
    private void turnToSettingFragment(){
        ((HomeActivity) getActivity()).turnToFragment(getFragmentManager(), SettingFragment.class, SettingFragment.TAG, null, true);
    }
    private void closeDrawer() {
        ((HomeActivity) getActivity()).closeDrawer();
    }



    private class DrawerAdapter extends FleetAdapter<DrawerItem>{

        @Override
        public DataSetHolder<DrawerItem> getDataSetHoler() {
            return new DataSetHolder<DrawerItem>(){

                @Override
                public List<DrawerItem> getDataSet() {
                    return mDrawerItems;
                }
            };
        }

        @Override
        protected ViewHolder<DrawerItem> getViewHolder(int position) {
            return new HomeMenuViewHolder();
        }
    }
}
