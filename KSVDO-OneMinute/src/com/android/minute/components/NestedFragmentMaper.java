package com.android.minute.components;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.minute.utils.Loger;


@SuppressLint("UseSparseArrays")
public class NestedFragmentMaper {

    private Map<Integer, Object> nestedFragmentIndentifierMap;
    private Fragment currentFragment;
    private Fragment rootFragment;
    
    public NestedFragmentMaper(Fragment currentFragment) {
        super();
        this.currentFragment = currentFragment;
        this.rootFragment  = findRootFragment(currentFragment);
    }
    
    public void putIntoIndentifierMap(int requestCode, Fragment childFragment) throws Exception {
     
        Object childFragmentIndentifier = getFragmentIdentifier(childFragment);
        int count = getChildFragmentCount(childFragment, childFragmentIndentifier);
        if (count > 1) {
            throw new Exception(getCanNotFindErrorMsg(currentFragment, childFragment, childFragmentIndentifier, count));
        }
        if (nestedFragmentIndentifierMap == null) {
            nestedFragmentIndentifierMap = new HashMap<Integer, Object>();
        }
        Loger.e(currentFragment.getClass().getName(), "put nested fragment " + childFragment.getClass().getSimpleName()
                + " " + childFragmentIndentifier
                + " for request code " + requestCode);
        nestedFragmentIndentifierMap.put(requestCode, childFragmentIndentifier);
    }
    
    private String getCanNotFindErrorMsg(Fragment parentFragment, Fragment childFrag, Object childFragmentIndentifier, int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("fragment ");
        sb.append(parentFragment.getClass().getName());
        sb.append(" can not find child ");
        sb.append(childFrag.getClass());
        sb.append(" because find ");
        sb.append(count);
        sb.append(" fragments ");
        sb.append(" by ");
        sb.append(childFragmentIndentifier instanceof Integer ? "id "
                : "tag ");
        sb.append(childFragmentIndentifier == null ? "null" : childFragmentIndentifier);
        return sb.toString();
    }
    
    @SuppressWarnings("unchecked")
    private int getChildFragmentCount(Fragment childFragment, Object identifier) {
        int count = 0;
        try {
            Field mAdded = childFragment.getFragmentManager().getClass().getDeclaredField("mAdded");
            mAdded.setAccessible(true);
            List<Fragment> mAddedList = (ArrayList<Fragment>) mAdded
                    .get(childFragment.getFragmentManager());
            if (mAddedList == null) {
                return 0;
            }
            if (identifier instanceof Integer) {
                int id = (Integer) identifier;
                for (int i = mAddedList.size() - 1; i >= 0; i--) {
                    Fragment f = mAddedList.get(i);
                    if (f != null && f.getId() == id) {
                        count++;
                    }
                }
                return count;
            }
            if (identifier instanceof String) {
                String tag = (String) identifier;
                for (int i = mAddedList.size() - 1; i >= 0; i--) {
                    Fragment f = mAddedList.get(i);
                    if (f != null && tag.equals(f.getTag())) {
                        count++;
                    }
                }
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    private Object getFragmentIdentifier(Fragment fragment) {
        if (fragment == null) {
            return null;
        }
        if (fragment.getTag() != null) {
            return fragment.getTag();
        }
        if (fragment.getId() != 0) {
            return fragment.getId();
        }
        return null;
    }
    
    private Fragment findRootFragment(Fragment currentFragment) {
        Fragment parent = currentFragment.getParentFragment();
        Fragment root = currentFragment;
        while (parent != null) {
            root = parent;
            parent = parent.getParentFragment();
        }
        return root;
    }
    
    public boolean handleActivityResult(int generatedGequestCode, int resultCode, Intent data) {
        Fragment deepestFragment = findDeepestFragment(generatedGequestCode);
        if (deepestFragment == null) {
            return false;
        }
        if (isNotTargetFragment(generatedGequestCode, deepestFragment)) {
            return false;
        }
        Integer rawRequestCode = getRawRequestCode(generatedGequestCode);
        Loger.e(currentFragment.getClass().getName(), "deepest fragment: "
                + deepestFragment.getClass().getName()
                + " onActivityResult");
        Loger.e(currentFragment.getClass().getName(), "raw requestcode is " + rawRequestCode
                + " for generated request code " + generatedGequestCode);
        if (rawRequestCode == null) {
            return false;
        }
        if (FragmentResultHelper.FragmentHelperDelegate.class.isInstance(deepestFragment)) {
            ((FragmentResultHelper.FragmentHelperDelegate) deepestFragment).onFragmentResult(rawRequestCode, resultCode, data);
            return true;
        }
        return false;
    }
    
    public Integer getRawRequestCode(int generatedGequestCode) {
        if (!FragmentResultHelper.FragmentHelperDelegate.class.isInstance(rootFragment)) {
            return null;
        }
        FragmentResultHelper helper = ((FragmentResultHelper.FragmentHelperDelegate) rootFragment).getFragmentHelper();
        RootFragmentMaper rootFragmentMaper = helper.getRootFragmentMaper();
        if (rootFragmentMaper == null) {
            return null;
        }
        return rootFragmentMaper.getRawRequestCode(generatedGequestCode);
    }
    
    public boolean isNotTargetFragment(int generatedGequestCode, Fragment targetFragment) {
        if (!FragmentResultHelper.FragmentHelperDelegate.class.isInstance(rootFragment)) {
            return true;
        }
        FragmentResultHelper helper = ((FragmentResultHelper.FragmentHelperDelegate) rootFragment).getFragmentHelper();
        RootFragmentMaper rootFragmentMaper = helper.getRootFragmentMaper();
        if (rootFragmentMaper == null) {
            return true;
        }
        return rootFragmentMaper.isNotTargetFragment(generatedGequestCode, targetFragment);
    }
    
    private Fragment findDeepestFragment(int generatedGequestCode) {
        if (nestedFragmentIndentifierMap == null) {
            return currentFragment;
        }
        Fragment deepestFragment = this.currentFragment;
        Fragment currentFragment = this.currentFragment;

        Object deepestFragmentIndenfier = getFragmentIndentifier(generatedGequestCode);
        while (deepestFragmentIndenfier != null) {
            if (deepestFragment != null) {
                currentFragment = deepestFragment;
            }
            if (deepestFragmentIndenfier instanceof Integer) {
                deepestFragment = currentFragment.getChildFragmentManager()
                        .findFragmentById((Integer) deepestFragmentIndenfier);
            } else if (deepestFragmentIndenfier instanceof String) {
                deepestFragment = currentFragment.getChildFragmentManager()
                        .findFragmentByTag((String) deepestFragmentIndenfier);
            }
            deepestFragmentIndenfier = getFragmentIndentifier(deepestFragment, generatedGequestCode);
        }
        return deepestFragment;
    }
    
    private static Object getFragmentIndentifier(Fragment f,
            int generatedGequestCode) {
        if (FragmentResultHelper.FragmentHelperDelegate.class.isInstance(f)) {
            FragmentResultHelper fragmentResultHelper = ((FragmentResultHelper.FragmentHelperDelegate) f)
                    .getFragmentHelper();
            if (fragmentResultHelper == null) {
                return null;
            }
            return fragmentResultHelper.getNestedFragmentMaper().getFragmentIndentifier(generatedGequestCode);
        }
        return null;
    }
    
    private Object getFragmentIndentifier(int generatedGequestCode) {
        if (nestedFragmentIndentifierMap == null) {
            return null;
        }
        return nestedFragmentIndentifierMap.get(generatedGequestCode);
    }

    public void saveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(getClass().getName(), (Serializable) nestedFragmentIndentifierMap);
        }
    }

    @SuppressWarnings("unchecked")
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            nestedFragmentIndentifierMap = (Map<Integer, Object>) savedInstanceState.getSerializable(getClass().getName());
        }
    }
    
}
