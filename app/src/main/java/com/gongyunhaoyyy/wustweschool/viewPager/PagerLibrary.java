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

public class PagerLibrary extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_library, container, false);


        /**
         * 袁来，建议你在这个界面加入推荐好书热门书用来吸引眼球
         * 就像学长的空手那样，如果能搜书，看书，下载书更好，尽力
         * 不然这个界面只是查找一些书就太废了，没人看这个界面
         */


        return view;
    }
}
