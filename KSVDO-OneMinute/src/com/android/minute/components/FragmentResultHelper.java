package com.android.minute.components;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentResultHelper {

    public interface FragmentHelperDelegate {
        FragmentResultHelper getFragmentHelper(); 
        void onFragmentResult(int requestCode, int resultCode, Intent data);
    }
    
    private Fragment currentFragment;
    private RootFragmentMaper rootFragmentMaper;
    private NestedFragmentMaper nestedFragmentMaper;
    
    public FragmentResultHelper(Fragment currentFragment) {
        this.currentFragment = currentFragment;
        rootFragmentMaper = new RootFragmentMaper(currentFragment);
        nestedFragmentMaper = new NestedFragmentMaper(currentFragment);
    }
    
    public void saveInstanceState(Bundle outState) {
        if (rootFragmentMaper != null) {
            rootFragmentMaper.saveInstanceState(outState);
        }
        if (nestedFragmentMaper != null) {
            nestedFragmentMaper.saveInstanceState(outState);
        }
    }
    
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (rootFragmentMaper != null) {
            rootFragmentMaper.restoreInstanceState(savedInstanceState);
        }
        if (nestedFragmentMaper != null) {
            nestedFragmentMaper.restoreInstanceState(savedInstanceState);
        }
    }

    public synchronized void startActivityForResult(Intent intent, int rawRequestCode) {
        if (currentFragment.getParentFragment() == null) {
            rootFragmentMaper.startActivityFromFragment(currentFragment, intent, rawRequestCode);
        } else {
            Fragment root = findRootFragment();
            if (!FragmentHelperDelegate.class.isInstance(root)) {
                return;
            }
            FragmentResultHelper helper = ((FragmentHelperDelegate) root).getFragmentHelper();
            if (helper.rootFragmentMaper == null) {
                return;
            }
            helper.rootFragmentMaper.startActivityFromFragment(currentFragment, intent, rawRequestCode);
        }
    }

    public boolean handleActivityResult(int generatedGequestCode, int resultCode, Intent data) {
        return nestedFragmentMaper.handleActivityResult(generatedGequestCode, resultCode, data);
    }
    
    private Fragment findRootFragment() {
        Fragment parent = currentFragment.getParentFragment();
        Fragment root = currentFragment;
        while (parent != null) {
            root = parent;
            parent = parent.getParentFragment();
        }
        return root;
    }
    
    public RootFragmentMaper getRootFragmentMaper() {
        return rootFragmentMaper;
    }


    public NestedFragmentMaper getNestedFragmentMaper() {
        return nestedFragmentMaper;
    }
    
    
}
