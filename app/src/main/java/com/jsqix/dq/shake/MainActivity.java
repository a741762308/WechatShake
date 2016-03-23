package com.jsqix.dq.shake;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout shake_top_layout, shake_bottom_layout;
    private ImageView shake_top_line, shake_bottom_line;
    private ImageView shakeBg;


    ShakeListener mShakeListener = null;
    Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        shake_top_layout = (RelativeLayout) findViewById(R.id.shake_top_layout);
        shake_bottom_layout = (RelativeLayout) findViewById(R.id.shake_bottom_layout);
        shake_top_line = (ImageView) findViewById(R.id.shake_top_line);
        shake_bottom_line = (ImageView) findViewById(R.id.shake_bottom_line);
        shake_top_line.setVisibility(View.GONE);
        shake_bottom_line.setVisibility(View.GONE);
        shakeBg= (ImageView) findViewById(R.id.shakeBg);
//        shakeBg.setVisibility(View.GONE);
        mShakeListener = new ShakeListener(MainActivity.this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                startAnim();  //开始 摇一摇手掌动画
                mShakeListener.stop();

                startVibrato(); //开始 震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", Toast.LENGTH_LONG).show();
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
    }

    public void startAnim() {   //定义摇一摇动画动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.8f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.8f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        shake_top_line.setVisibility(View.VISIBLE);
        shake_top_layout.startAnimation(animup);
//        shakeBg.setVisibility(View.VISIBLE);
        mytranslateanimup1.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shake_top_line.setVisibility(View.GONE);
//                shakeBg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.8f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.8f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        shake_bottom_line.setVisibility(View.VISIBLE);
        shake_bottom_layout.startAnimation(animdn);
        mytranslateanimdn1.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shake_bottom_line.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startVibrato() {
        MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.awe);
        player.setLooping(false);
        player.start();
        //定义震动
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}
