package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 99460 on 2017/10/23.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"教务处", "先锋在线", "综合新闻","学院新闻"};

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new online_news_fragment();
        } else if (position == 2) {
            return new combined_news_fragment();
        }else if (position == 3){
            return new academy_news_fragment();
        }
        return new dean_office_fragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
