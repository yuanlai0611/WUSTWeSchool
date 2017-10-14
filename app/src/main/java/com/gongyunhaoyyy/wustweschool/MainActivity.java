package com.gongyunhaoyyy.wustweschool;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.gongyunhaoyyy.wustweschool.Adapter.AbsGridAdapter;
import com.gongyunhaoyyy.wustweschool.Adapter.ViewPagerAdapter;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerAboutUs;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerCourse;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerLibrary;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerMain;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerNews;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    private List<View> listView;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private List<String> list_title;
    private TabLayout tab_gyh;
    private Spinner spinner;
    private GridView detailCource;
    private String[][] contents;
    private AbsGridAdapter absGridAdapter;
    private List<String> dataList;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        init();
        spinner = (Spinner)findViewById(R.id.switchWeek);
        detailCource = (GridView)findViewById(R.id.courceDetail);
        fillStringArray();
        absGridAdapter = new AbsGridAdapter(this);
        absGridAdapter.setContent(contents, 6, 7);
//        这一句总提示空？？？
//        detailCource.setAdapter( absGridAdapter );
        //创建Spinner数据
        fillDataList();
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        这一句总提示空？？？
//        spinner.setAdapter(spinnerAdapter);

        tab_gyh.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener( ) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //用于设置选中时的图片
                if (tab==tab_gyh.getTabAt( 0 )){
                    tab_gyh.getTabAt( 0 ).setIcon( R.drawable.tab_main_se );
                    tab_gyh.getTabAt( 1 ).setIcon( R.drawable.tab_library_not );
                    tab_gyh.getTabAt( 2 ).setIcon( R.drawable.tab_course_not );
                    tab_gyh.getTabAt( 3 ).setIcon( R.drawable.tab_news_not );
                    tab_gyh.getTabAt( 4 ).setIcon( R.drawable.tab_us_not );
                }else if (tab==tab_gyh.getTabAt( 1 )){
                    tab_gyh.getTabAt( 1 ).setIcon( R.drawable.tab_library_se );
                    tab_gyh.getTabAt( 0 ).setIcon( R.drawable.tab_main_not );
                    tab_gyh.getTabAt( 2 ).setIcon( R.drawable.tab_course_not );
                    tab_gyh.getTabAt( 3 ).setIcon( R.drawable.tab_news_not );
                    tab_gyh.getTabAt( 4 ).setIcon( R.drawable.tab_us_not );
                }else if (tab==tab_gyh.getTabAt( 2 )){
                    tab_gyh.getTabAt( 2 ).setIcon( R.drawable.tab_course_se );
                    tab_gyh.getTabAt( 0 ).setIcon( R.drawable.tab_main_not );
                    tab_gyh.getTabAt( 1 ).setIcon( R.drawable.tab_library_not );
                    tab_gyh.getTabAt( 3 ).setIcon( R.drawable.tab_news_not );
                    tab_gyh.getTabAt( 4 ).setIcon( R.drawable.tab_us_not );
                }else if (tab==tab_gyh.getTabAt( 3 )){
                    tab_gyh.getTabAt( 3 ).setIcon( R.drawable.tab_news_se );
                    tab_gyh.getTabAt( 1 ).setIcon( R.drawable.tab_library_not );
                    tab_gyh.getTabAt( 0 ).setIcon( R.drawable.tab_main_not );
                    tab_gyh.getTabAt( 2 ).setIcon( R.drawable.tab_course_not );
                    tab_gyh.getTabAt( 4 ).setIcon( R.drawable.tab_us_not );
                }else if (tab==tab_gyh.getTabAt( 4 )){
                    tab_gyh.getTabAt( 4 ).setIcon( R.drawable.tab_us_se );
                    tab_gyh.getTabAt( 1 ).setIcon( R.drawable.tab_library_not );
                    tab_gyh.getTabAt( 0 ).setIcon( R.drawable.tab_main_not );
                    tab_gyh.getTabAt( 2 ).setIcon( R.drawable.tab_course_not );
                    tab_gyh.getTabAt( 3 ).setIcon( R.drawable.tab_news_not );
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //用于设置未被选中时的图片
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }
    /**
     * 准备数据
     */
    public void fillStringArray() {
        contents = new String[6][7];
        contents[0][0] = "我是第一节\n&11304";
        contents[1][0] = "我是第二节\n&11304";
        contents[2][0] = "我是第三节\n&11304";
        contents[3][0] = "我是第四节\n&11304";
        contents[4][0] = "";
        contents[5][0] = "";
        contents[0][1] = "我是第七节\n&11304";
        contents[1][1] = "";
        contents[2][1] = "面向对象程序设计\n&30305";
        contents[3][1] = "面向对象程序设计\nA309";
        contents[4][1] = "";
        contents[5][1] = "";
        contents[0][2] = "微机原理及应用\nE203";
        contents[1][2] = "电磁场理论\nA212";
        contents[2][2] = "现代测试技术\nB211";
        contents[3][2] = "";
        contents[4][2] = "";
        contents[5][2] = "";
        contents[0][3] = "面向对象程序设计\nA309";
        contents[1][3] = "传感器电子测量A\nC309";
        contents[2][3] = "";
        contents[3][3] = "";
        contents[4][3] = "";
        contents[5][3] = "";
        contents[0][4] = "数据结构与算法\nB211";
        contents[1][4] = "微机原理及应用\nE203";
        contents[2][4] = "";
        contents[3][4] = "";
        contents[4][4] = "";
        contents[5][4] = "";
        contents[0][5] = "";
        contents[1][5] = "";
        contents[2][5] = "";
        contents[3][5] = "";
        contents[4][5] = "";
        contents[5][5] = "";
        contents[0][6] = "";
        contents[1][6] = "";
        contents[2][6] = "";
        contents[3][6] = "";
        contents[4][6] = "";
        contents[5][6] = "龚云浩\n哈哈|:)";
    }

    public void fillDataList() {
        dataList = new ArrayList<>();
        for(int i = 1; i < 21; i++) {
            dataList.add("第" + i + "周");
        }
    }

    private void init() {
        listView = new ArrayList<View>();
        tab_gyh=(TabLayout)findViewById( R.id.tabs );
        //按名称加载tab名字列表.
        list_title = new ArrayList<>();
        list_title.add("首页");
        list_title.add("图书馆");
        list_title.add("课表");
        list_title.add("新闻");
        list_title.add("我的");
        //设置TabLayout的模式
        tab_gyh.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        for (int i = 0; i < 5; i++) {
            tab_gyh.addTab(tab_gyh.newTab().setText(list_title.get(i)));
        }
        //List<view>放在适配器ViewPagerAdapter中
        List<Fragment> list_fragment=new ArrayList<Fragment>();
        list_fragment.add(new PagerMain());
        list_fragment.add(new PagerLibrary());
        list_fragment.add(new PagerCourse());
        list_fragment.add(new PagerNews());
        list_fragment.add(new PagerAboutUs());
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment);
        //获取ViewPage,设置适配器;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOffscreenPageLimit(5);
        //让刚打开app时，页面显示在第0个pager.
        viewPager.setCurrentItem(0);
        //将TabLayout和ViewPager关联起来.
        tab_gyh.setupWithViewPager(viewPager);

    }

}
