package com.kx.waimaidemo.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kx.waimaidemo.R;

/*
 *  创建者:   KX
 *  创建时间:  2017/10/23 16:16
 *  描述：    user
 */
public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_, null);
        ((TextView)view.findViewById(R.id.tv)).setText("user");
        return view;
    }
}
