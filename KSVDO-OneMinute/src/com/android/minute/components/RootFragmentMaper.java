package com.android.minute.components;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.minute.utils.Loger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootFragmentMaper {

    private Fragment root;
    // generate request code is key, class name is value
    private Map<Integer, Object> classMap;
    // generate request code is key, raw request code is value
    private Map<Integer, Integer> requestCodeMap;
    
    public RootFragmentMaper(Fragment root) {
        super();
        this.root = root;
    }
    
    public boolean isNotTargetFragment(int generatedGequestCode, Fragment targetFragment) {
        return !targetFragment.getClass().getName().equals(getCachedClassName(generatedGequestCode));
    }
    
    public synchronized void startActivityFromFragment(Fragment from, Intent intent, int rawRequestCode) {
        try {
            int generatedRequestCode = genarateRequestCode(from, rawRequestCode);
            putIntoCodeAndClassMap(generatedRequestCode, rawRequestCode, from);
            FragmentActivity activity = root.getActivity();
            if (activity != null) {
                Loger.e(getTag(), from.getClass().getName() + " startActivity");
                activity.startActivityFromFragment(root, intent, generatedRequestCode);
            }
        } catch (Exception e) {
            Loger.e(this, e.getMessage());
        }
    }

    public int genarateRequestCode(Fragment targerFragment, int rawRequestCode) throws Exception {
        Loger.e(getTag(), "raw request code is " + rawRequestCode);
        int generatedRequestCode = genarateRequestCode(rawRequestCode, targerFragment);
        Loger.e(getTag(), "generated request code is " + generatedRequestCode);
        List<Fragment> parentFragments = getParentFragments(targerFragment);
        Fragment childFragment = targerFragment;
        for (int i = 0; i < parentFragments.size(); i++) {
            Fragment parentFragment = parentFragments.get(i);
            if (i > 0) {
                childFragment = parentFragments.get(i - 1);
            }
            if (FragmentResultHelper.FragmentHelperDelegate.class.isInstance(parentFragment)) {
                FragmentResultHelper helper = ((FragmentResultHelper.FragmentHelperDelegate) parentFragment).getFragmentHelper();
                helper.getNestedFragmentMaper().putIntoIndentifierMap(generatedRequestCode, childFragment);
            }
        }
        return generatedRequestCode;
    }

    private String getTag() {
        return root.getClass().getName();
    }
    
    private List<Fragment> getParentFragments(Fragment targerFragment) {
        List<Fragment> fragments = new ArrayList<Fragment>();
        Fragment parent = targerFragment.getParentFragment();
        while (parent != null) {
            fragments.add(parent);
            parent = parent.getParentFragment();
        }
        return fragments;
    }
    
    public Integer getRawRequestCode(int generatedGequestCode) {
        if (requestCodeMap == null) {
            return null;
        }
        return requestCodeMap.get(generatedGequestCode);
    }
    
//    @SuppressLint("UseSparseArrays")
    private void putIntoCodeAndClassMap(int generatedCode, int rawRequestCode, Fragment targetFragment) {
        if (requestCodeMap == null) {
            requestCodeMap = new HashMap<Integer, Integer>();
        }
        requestCodeMap.put(generatedCode, rawRequestCode);
        if (classMap == null) {
            classMap = new HashMap<Integer, Object>();
        }
        classMap.put(generatedCode, targetFragment.getClass().getName());
    }
    
    private int genarateRequestCode(int rawRequestCode, Fragment targerFragment) {
        Integer cachedGeneratedRequestCode = getCachedGeneratedRequestCode(rawRequestCode, targerFragment);
        Loger.e(root.getClass().getName(), "cached generated request Code " + cachedGeneratedRequestCode);
        if (cachedGeneratedRequestCode == null) {
            return requestCodeMap == null ? 0 : requestCodeMap.size();
        }
        if (cachedGeneratedRequestCode <= 0) {
            return 0;
        }
        return cachedGeneratedRequestCode;
    }

    private String getCachedClassName(int generatedGequestCode) {
        if (classMap == null) {
            return null;
        }
        return (String) classMap.get(generatedGequestCode);
    }
    
    private Integer getCachedGeneratedRequestCode(int rawRequestCode, Fragment targerFragment) {

        if (requestCodeMap == null) {
            return null;
        }
        for (Integer key : requestCodeMap.keySet()) {
            if (requestCodeMap.get(key) == rawRequestCode) {
                if (!targerFragment.getClass().getName().equals(classMap.get(key))) {
                    continue;
                }
                return key;
            }
        }
        return null;
    }

    public void saveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(getClass().getName() + "classMap", (Serializable) classMap);
            outState.putSerializable(getClass().getName() + "requestCodeMap", (Serializable) requestCodeMap);
        }
    }

    @SuppressWarnings("unchecked")
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            classMap = (Map<Integer, Object>) savedInstanceState.getSerializable(getClass().getName() + "classMap");
            requestCodeMap = (Map<Integer, Integer>) savedInstanceState.getSerializable(getClass().getName() + "requestCodeMap");
        }
    }
}
