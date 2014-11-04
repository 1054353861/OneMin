package com.android.minute.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;

import com.android.minute.utils.Loger;


public abstract class BaseFragment extends Fragment implements FragmentResultHelper.FragmentHelperDelegate {
    
    private FragmentResultHelper fragmentResultHelper = new FragmentResultHelper(this);
    
    
    public MenuInflater getSupportMenuInflater() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof ActionBarActivity) {
            return ((ActionBarActivity) activity).getMenuInflater();
        }
        if (activity == null) {
            throw new RuntimeException("activity is null");
        }
        throw new RuntimeException("activity is not instanceof ActionBarActivity " + activity.toString());
    }
    

    public ActionBar getSupportActionBar() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof ActionBarActivity) {
            return ((ActionBarActivity) activity).getSupportActionBar();
        }
        if (activity == null) {
            throw new RuntimeException("activity is null");
        }
        throw new RuntimeException("activity is not instanceof ActionBarActivity " + activity.toString());
    }
    
    public final FragmentResultHelper getFragmentHelper() {
        return fragmentResultHelper;
    }

    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (getFragmentHelper() == null) {
            super.startActivityForResult(intent, requestCode);
            return;
        }
        fragmentResultHelper.startActivityForResult(intent, requestCode);
    }
    

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getFragmentHelper() != null) {
            fragmentResultHelper.handleActivityResult(requestCode, resultCode, data);
        } else {
            onFragmentResult(requestCode, resultCode, data);
        }
    }
    
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        Loger.e(this, "onFragmentResult requestCode " + requestCode);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentResultHelper.saveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        fragmentResultHelper.restoreInstanceState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }
    
}
