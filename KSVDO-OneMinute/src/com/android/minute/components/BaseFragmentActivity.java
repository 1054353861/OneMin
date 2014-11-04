package com.android.minute.components;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import me.ele.utils.SoftInputManager;

public abstract class BaseFragmentActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
    }

    public String getTag() {
        return getClass().getSimpleName();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public Context getContext() {
        return this;
    }

    public ActionBarActivity getActivity() {
        return this;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void finish() {
        SoftInputManager.hideSoftInput(this);
        super.finish();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
