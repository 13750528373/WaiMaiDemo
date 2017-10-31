package com.kx.waimaidemo.ui.fragment;

import android.animation.ArgbEvaluator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.waimaidemo.R;
import com.kx.waimaidemo.model.bean.Seller;
import com.kx.waimaidemo.presenter.HomeFragmentPresenter;
import com.kx.waimaidemo.ui.adapter.HomeRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  创建者:   KX
 *  创建时间:  2017/10/23 16:16
 *  描述：    home
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.rv_home)
    android.support.v7.widget.RecyclerView mRvHome;
    @BindView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    private HomeRvAdapter mHomeRvAdapter;
    private List<String> mData;
    private HomeFragmentPresenter mHomeFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        mHomeFragmentPresenter = new HomeFragmentPresenter(this);
        mData = new ArrayList<>();

        mRvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHomeRvAdapter = new HomeRvAdapter();
        mRvHome.setAdapter(mHomeRvAdapter);
        return view;
    }

    private int sumY;
    float distance = 300.0f; //背景距离
    int start = 0x553190E8;//背景颜色  半透明
    int end = 0xFF3190E8;//不透明
    int bgColor;
    ArgbEvaluator evaluator = new ArgbEvaluator();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //请求数据
//        for (int i = 0; i < 88; i++) {
//            mData.add("数据"+i);
//        }
//        mHomeRvAdapter.setData(mData,getActivity());
        mHomeFragmentPresenter.loadhomeInfo();


        //滑动监听
        mRvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sumY += dy;
                if (sumY <= 0) {
                    bgColor = start;
                } else if (sumY > distance) {
                    bgColor = end;
                } else {
                    bgColor = (int) evaluator.evaluate(sumY / distance, start, end);
                }
                mLlTitleContainer.setBackgroundColor(bgColor);
            }
        });
    }

    List<Seller> allSellers = new ArrayList<>();
    public void loadhomeSuccess(List<Seller> nearbySellers, List<Seller> otherSellerLists) {
        allSellers.clear();//重复请求，保证集合唯一，每次先清空   成员变量需要
        allSellers.addAll(nearbySellers);
        allSellers.addAll(otherSellerLists);

        mHomeRvAdapter.setData(allSellers, getActivity());
    }


    public void loadhomeFailed(String localizedMessage) {
        Toast.makeText(getActivity(), "服务器繁忙", Toast.LENGTH_SHORT).show();
    }
}
