package com.gongyunhaoyyy.wustweschool.viewPager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.CustomPopupWindow;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.MyFragmentPagerAdapter;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.Search_activity;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerLibrary extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CustomPopupWindow mCustomPopupWindow;
    private View mLayoutPopupWindowView;//悬浮窗的布局
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    public static LinearLayout linearLayout;
    Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_library, container, false);



        search = (Button) view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity().getApplicationContext(),Search_activity.class);
                startActivity(intent);
            }
        });

        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        /**
         * 袁来，建议你在这个界面加入推荐好书热门书用来吸引眼球
         * 就像学长的空手那样，如果能搜书，看书，下载书更好，尽力
         * 不然这个界面只是查找一些书就太废了，没人看这个界面
         */
        return view;
    }


}
