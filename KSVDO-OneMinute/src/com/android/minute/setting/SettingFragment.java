package com.android.minute.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.minute.R;
import com.android.minute.components.BaseFragment;

/**
 * Created by xiangyi.wu on 2014/11/4.
 */
public class SettingFragment extends BaseFragment {
    public static final String TAG="SettingFragment";
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,container,false);
        return mView;
    }
}
