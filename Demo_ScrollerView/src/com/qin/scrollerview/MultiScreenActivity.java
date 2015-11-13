package com.qin.scrollerview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.tencent.commontools.LogUtils;

/**
 * @author http://http://blog.csdn.net/qinjuning
 */
//带有可以切换屏的Activity
public class MultiScreenActivity extends Activity implements OnClickListener {
    public static int screenWidth;  // 屏幕宽度
    public static int scrrenHeight;  //屏幕高度
    private static String TAG = "MultiScreenActivity";

    private Button bt_scrollLeft;
    private Button bt_scrollRight;
    private MultiViewGroup mulTiViewGroup;
    private TextView infoView;
    private Switch aSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得屏幕分辨率大小
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        scrrenHeight = metric.heightPixels;
        System.out.println("screenWidth * scrrenHeight --->" + screenWidth + " * " + scrrenHeight);

        setContentView(R.layout.multiview);

        infoView = (TextView) findViewById(R.id.tv_hello);
        //获取自定义视图的空间引用
        mulTiViewGroup = (MultiViewGroup) findViewById(R.id.mymultiViewGroup);

        mulTiViewGroup.registerListener(new MultiViewGroup.ScrollListener() {
            @Override
            public void onScrollX(int x) {
                LogUtils.d(TAG, "scroll to:" + x);
//                try {
//                    infoView.setText(x);
//                } catch (Exception e) {
////                    e.printStackTrace();
//                }
            }
        });
        bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
        bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);
        findViewById(R.id.bt_test).setOnClickListener(this);
        findViewById(R.id.bt_DefaultInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_AccelerateDecelerateInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_AccelerateInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_AnticipateInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_AnticipateOvershootInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_BounceInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_CycleInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_DecelerateInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_LinearInterpolator).setOnClickListener(this);
        findViewById(R.id.bt_OvershootInterpolator).setOnClickListener(this);
        bt_scrollLeft.setOnClickListener(this);
        bt_scrollRight.setOnClickListener(this);
        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mulTiViewGroup.isScrollAble = isChecked;
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // bt_AccelerateDecelerateInterpolator 动画效果：开始和结束都是缓慢的 通过中间时候加速
        // bt_AccelerateInterpolator 动画效果：开始缓慢 之后加速
        // bt_AnticipateInterpolator 动画效果：开始后退 然后前进
        // bt_AnticipateOvershootInterpolator 动画效果：开始后退 之后前进并超过终点位置 最终退回到终点
        // bt_BounceInterpolator 动画效果：慢慢反弹到 弹性衰减到结束
        // bt_CycleInterpolator 动画效果：重复循环动画 速度变化遵循正弦定律
        // bt_DecelerateInterpolator 动画效果：刚开始快速 之后减速
        // bt_LinearInterpolator 动画效果：不断的变化
        // bt_OvershootInterpolator 动画效果：像前超越最终点然后回来
        switch (v.getId()) {
            case R.id.bt_scrollLeft:
                mulTiViewGroup.startMove(); //下一屏
                break;
            case R.id.bt_scrollRight:
                mulTiViewGroup.stopMove(); //停止滑动
                break;
            case R.id.bt_test: {
            }
            break;
            case R.id.bt_DefaultInterpolator: {
                mulTiViewGroup.setInterpolator(null);
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_AccelerateDecelerateInterpolator: {
                mulTiViewGroup.setInterpolator(new AccelerateInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_AccelerateInterpolator: {
                mulTiViewGroup.setInterpolator(new AccelerateInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_AnticipateInterpolator: {
                mulTiViewGroup.setInterpolator(new AnticipateInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_AnticipateOvershootInterpolator: {
                mulTiViewGroup.setInterpolator(new AnticipateOvershootInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_BounceInterpolator: {
                mulTiViewGroup.setInterpolator(new BounceInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_CycleInterpolator: {
                mulTiViewGroup.setInterpolator(new CycleInterpolator(2));
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_DecelerateInterpolator: {
                mulTiViewGroup.setInterpolator(new DecelerateInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_LinearInterpolator: {
                mulTiViewGroup.setInterpolator(new LinearInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
            case R.id.bt_OvershootInterpolator: {
                mulTiViewGroup.setInterpolator(new OvershootInterpolator());
                mulTiViewGroup.startMove();
            }
            break;
        }
    }

}
