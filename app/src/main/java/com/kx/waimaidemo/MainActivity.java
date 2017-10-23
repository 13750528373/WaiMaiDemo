package com.kx.waimaidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * kx
 * 外卖框架留档
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottomBar();
        selected(0);
    }

    //初始化底部导航栏
    private void initBottomBar() {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mMainBottomeSwitcherContainer.indexOfChild(child);
                    selected(index);
                }
            });

        }
    }

    //底部导航栏选中
    private void selected(int index) {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mMainBottomeSwitcherContainer.getChildAt(i);
            if (i == index) {
                setEnable(child, false);
            } else {
                setEnable(child, true);
            }
        }

       /* getFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                mFragmentLists.get(index)).commit();*/
    }

    //底部导航栏设置点击状态
    private void setEnable(View child, boolean isEnable) {
        child.setEnabled(isEnable);
        if (child instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) child;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View item = viewGroup.getChildAt(i);
                setEnable(item, isEnable);
            }
        }

    }
}
