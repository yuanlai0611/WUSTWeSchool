package com.gongyunhaoyyy.wustweschool.viewPager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.R;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerNews extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_news, container, false);
        return view;
    }
}
