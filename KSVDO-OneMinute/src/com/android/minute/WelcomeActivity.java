package com.android.minute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by xiangyi.wu on 2014/10/29.
 */
public class WelcomeActivity extends Activity {
    private final int MSG_ANIM = 0;
    private final int MSG_OK = 1;
    private ImageView mWelcomeTitleIv;
    private ImageView mWelcomeDescIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity);
        initView();
        super.onCreate(savedInstanceState);
    }

    private void initView(){
        mWelcomeTitleIv = (ImageView) findViewById(R.id.welcome_title_iv);
        mWelcomeDescIv = (ImageView) findViewById(R.id.welcome_desc_iv);
        mHandler.sendEmptyMessageDelayed(MSG_ANIM,500);
    }
    private void initTitleAnimation(){
        Animation mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_push_in);
        mWelcomeTitleIv.startAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mWelcomeTitleIv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                initDescAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void initDescAnimation(){
        Animation mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_scale_in);
        mWelcomeDescIv.startAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mWelcomeDescIv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.sendEmptyMessageDelayed(MSG_OK,2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_ANIM:
                    initTitleAnimation();
                    break;
                case MSG_OK:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
