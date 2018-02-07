package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Adapter.ScoreAdapter;
import com.gongyunhaoyyy.wustweschool.Adapter.ViewPagerAdapter;
import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.score;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerLibrary;
import com.gongyunhaoyyy.wustweschool.viewPager.PagerMain;
import com.gongyunhaoyyy.wustweschool.viewPager.fragment_score_all;
import com.gongyunhaoyyy.wustweschool.viewPager.fragment_score_now;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    String xh,score;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter vpAdapter;
    private int flag=0;
    private List<score> mScorelist_all=new ArrayList<>();
    private List<score> mScorelist_now=new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    private List<Fragment> list_fragment=new ArrayList<>();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_score );
        SharedPreferences ud=getSharedPreferences( "userdata", MODE_PRIVATE );
        String[] uddt=ud.getString( "getuserdata","" ).split( "," );
        xh=uddt[0];
        mTitles.add( "全部成绩" );
        mTitles.add( "本学期成绩" );
        mViewPager=(ViewPager)findViewById( R.id.pager_score );
        mTabLayout=(TabLayout)findViewById( R.id.tab_score );

        View view= LayoutInflater.from(this).inflate
                (R.layout.toast_loading,null);
        AVLoadingIndicatorView avl=(AVLoadingIndicatorView) view.findViewById(R.id.avl);
        avl.show();
        TextView tv=view.findViewById(R.id.tv);
        tv.setText("小园拼命加载中...");
        dialog=new AlertDialog.Builder(ScoreActivity.this,R.style.CustomDialog)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Thread.sleep( 500 );
                    Ksoap2 ksoap2=new Ksoap2();
                    score=ksoap2.getScoreInfo( xh );
                    Gson gson=new Gson();
                    List<score> slist=gson.fromJson( score,new TypeToken<List<score>>(){}.getType());
                    mScorelist_all.addAll( slist );
                    for (int i=0;i<slist.size();i++){
                        if (slist.get( i ).getKkxq().equals( getDate() ))
                        mScorelist_now.add( slist.get( i ) );
                    }
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            list_fragment.add(new fragment_score_all(mScorelist_all));
                            list_fragment.add(new fragment_score_now(mScorelist_now));

                            vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment, mTitles);
                            mViewPager.setAdapter(vpAdapter);
                            mTabLayout.setupWithViewPager( mViewPager );
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();
    }

    public String getDate(){
        int year2=2017;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat2 = new SimpleDateFormat("MM");
        int mm= Integer.parseInt( sDateFormat2.format(new java.util.Date()) );
        String year = sDateFormat.format(new java.util.Date());
        int year1= Integer.parseInt( year );
        if (mm>2&&mm<9){
            year1--;
            year2=year1+1;
            mm=2;
        }else if (mm<=2){
            year1--;
            year2=year1+1;
            mm=1;
        }else if (mm>=9){
            year2=year1++;
        }
        String date1=String.valueOf( year1 );
        String date2=String.valueOf( year2 );
        String mm2=String.valueOf( mm );
        String date3=date1+"-"+date2+"-"+mm2;
        return date3;
    }

}
