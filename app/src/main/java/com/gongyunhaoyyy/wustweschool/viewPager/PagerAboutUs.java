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

public class PagerAboutUs extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_aboutus, container, false);


        /**
         * 吴贝
         * 我的建议是在这个界面头部放user的详细信息（类似支付宝）
         * 然后下方item有我的资产（我的余额，我的书架之类的）仿支付宝*_*
         * 所以这个页面不仅仅是关于我们哦
         */



        return view;
    }
}
