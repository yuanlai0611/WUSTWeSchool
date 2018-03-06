package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news.academy_news_fragment;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news.combined_news_fragment;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news.dean_office_fragment;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news.online_news_fragment;

/**
 * Created by 99460 on 2017/10/23.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

//    private FragmentTransaction mCurTransaction = null;
//    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
//    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
//    private  FragmentManager mFragmentManager ;


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }



    private String[] mTitles = new String[]{"学术动态", "聚焦科大", "综合新闻","学院新闻"};

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
    public CharSequence getPageTitle(int position) {return mTitles[position];}




}
