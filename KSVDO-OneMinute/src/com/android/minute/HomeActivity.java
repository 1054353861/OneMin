package com.android.minute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.minute.components.BaseFragment;
import com.android.minute.components.BaseFragmentActivity;
import com.android.minute.home.menu.HomeMenuFragment;

import me.ele.utils.ToastUtil;

/**
 * Created by xiangyi.wu on 2014/11/3.
 */
public class HomeActivity extends BaseFragmentActivity {
    private DrawerLayout mDrawerLayout;
    private HomeMenuFragment mHomeMenuFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private Context mContext;
    private Exit exit = new Exit();

    public static View mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mView = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item,null);
        setContentView(R.layout.activity_home);
        initActionBar();
        initView();
    }
    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("壹分钟");
    }


    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.ic_drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.setDrawerListener(new HomeDrawerListener());
    }

    private class HomeDrawerListener implements DrawerLayout.DrawerListener{

        @Override
        public void onDrawerSlide(View view, float v) {
            mDrawerToggle.onDrawerSlide(view, v);
        }

        @Override
        public void onDrawerOpened(View view) {
            mDrawerToggle.onDrawerOpened(view);
//            if (mHomeMenuFragment != null) {
////                mHomeMenuFragment.onDrawerOpened();
//            }
        }

        @Override
        public void onDrawerClosed(View view) {
            mDrawerToggle.onDrawerClosed(view);
        }

        @Override
        public void onDrawerStateChanged(int i) {
            mDrawerToggle.onDrawerStateChanged(i);
        }
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(mContext,SettingActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
            if(mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1)))
                mDrawerLayout.closeDrawers();
            else{
                mDrawerLayout.openDrawer(mDrawerLayout.getChildAt(1));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment跳转
     *
     * @param fm
     * @param fragmentClass
     * @param tag
     * @param args
     */
    public void turnToFragment(FragmentManager fm, Class<? extends BaseFragment> fragmentClass, String tag, Bundle args, boolean isAddToBackStack) {
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment.isAdded()) {
            return;
        }
        if (args != null && !args.isEmpty()) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.right_in, R.anim.left_out_offset, R.anim.right_in, R.anim.left_out_offset);
        if (isFragmentExist) {
            ft.replace(R.id.home_activity_content_container, fragment);
        } else {
            ft.replace(R.id.home_activity_content_container, fragment, tag);
        }

        if (isAddToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        pressAgainExit();
    }

    private void pressAgainExit() {
        if (exit.isExit()) {
            this.finish();
        } else {
            ToastUtil.showToastShort(this, "再按一次退出程序");
            exit.doExitInOneSecond();
        }
    }

    class Exit {
        private static final long DELAY = 2000;
        private boolean isExit;
        private Runnable task = new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        };

        public void doExitInOneSecond() {
            isExit = true;
            new Handler().postDelayed(task, DELAY);
        }

        public boolean isExit() {
            return isExit;
        }

        public void setExit(boolean isExit) {
            this.isExit = isExit;
        }
    }


}
