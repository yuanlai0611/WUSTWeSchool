package com.gongyunhaoyyy.wustweschool.viewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Adapter.AbsGridAdapter;
import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.LitePal.Course;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.courseSetPopwindow;
import com.gongyunhaoyyy.wustweschool.bean.Coursebean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/10.
 */

public class PagerCourse extends Fragment implements View.OnClickListener{
    private courseSetPopwindow mCoursePop;
    private View mLayoutPopView;//悬浮窗的布局
    private Button set_yes;
    private TextView set_xq,set_zs;
    private Context mContext;
    private GridView detailCource;
    private String[][][] contents;
    private AbsGridAdapter absGridAdapter;
    private List<Coursebean> course_list=new ArrayList<>(  );
    ImageView set;
    int i,j;
    String xh,coursestr,xq;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.pager_course,container,false);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor zceditor = mContext.getSharedPreferences( "zhouci", MODE_PRIVATE ).edit( );
        detailCource = (GridView)view.findViewById(R.id.courceDetail);
        set=(ImageView) view.findViewById( R.id.course_setting );
        SharedPreferences ud=mContext.getSharedPreferences( "userdata", MODE_PRIVATE );
        String uddt=ud.getString( "getuserdata","" );
        if (uddt.isEmpty()){
        }else {
            String[] uddt1=ud.getString( "getuserdata","" ).split( "," );
            xh=uddt1[0];
            List<Course> coud= DataSupport.findAll( Course.class );
            for (Course sss:coud){
                Log.d( "*******", sss.getKcmc());
            }

            if (coud.size()<1){
                DataSupport.deleteAll( Course.class );
                LinkServiseWithCourse();
            }else {
                for (Course cs:coud){
                    course_list.add( new Coursebean(cs.getId(),cs.getDwmc(),cs.getJsmc(),cs.getKcxzmc(),cs.getKcsj(),cs.getKtmc(),cs.getKcsxm(),cs.getJsxm(),cs.getXkjd(),cs.getZxs(),cs.getKkzc(),cs.getKcmc(),cs.getXf() ) );
                }

                ParseCourse();
                fillStringArray();
                absGridAdapter = new AbsGridAdapter(mContext);
                absGridAdapter.setContent(contents, 6, 7);
                detailCource.setAdapter( absGridAdapter );
            }

            set.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    mLayoutPopView = LayoutInflater.from(getActivity()).inflate(R.layout
                            .pop_course_setting, null);
                    mCoursePop = new courseSetPopwindow(view.findViewById(R.id.pager_course), getActivity(), mLayoutPopView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    mCoursePop.setOnPopupWindowListener( new courseSetPopwindow.PopupWindowListener( ) {
                        @Override
                        public void initView() {
                            set_yes=(Button)mLayoutPopView.findViewById( R.id.course_set_yes );
                            set_xq=(TextView)mLayoutPopView.findViewById( R.id.course_xq );
                            set_zs=(TextView)mLayoutPopView.findViewById( R.id.course_zs );
                            set_xq.setText( getDate() );
                            set_yes.setOnClickListener( PagerCourse.this );
                            set_xq.setOnClickListener( PagerCourse.this );
                            set_zs.setOnClickListener( PagerCourse.this );
                        }
                    } );

                    zceditor.putInt( "huoquzhouci",10 );
                    zceditor.apply();

                    mCoursePop.showView();
                    Animation scaleAanimation = AnimationUtils.loadAnimation(getActivity(),R.anim.popwindow_coursesetting);
                    mLayoutPopView.startAnimation(scaleAanimation);
                    mCoursePop.setBackgroundAlpha(1.0f);
                }
            } );
        }

        return view;
    }

    public void LinkServiseWithCourse(){
        xq=getDate();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Ksoap2 ksoap2=new Ksoap2();
                    coursestr= ksoap2.getCourseInfo( xh,xq );
                    Gson gson=new Gson();
                    List<Coursebean> slist=gson.fromJson(coursestr,new TypeToken<List<Coursebean>>(){}.getType());
                    if (slist.size()<1){

                    }else {
                        course_list.clear();
                        course_list.addAll( slist );
                        //解析
                        ParseCourse();
                        //保存到数据库
                        fillCourseData();
                        //填充数据
                        fillStringArray();
                        //回到主线程更新UI
                        getActivity().runOnUiThread( new Runnable( ) {
                            @Override
                            public void run() {
                                absGridAdapter = new AbsGridAdapter(mContext);
                                absGridAdapter.setContent(contents, 6, 7);
                                detailCource.setAdapter( absGridAdapter );
                            }
                        } );
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();
    }

    /**
     * 准备数据
     */
    public void ParseCourse(){
        for (int l=0;l<course_list.size();l++){
            course_list.get( l ).setJsmc1( getjsmc(course_list.get( l ),1) );
            course_list.get( l ).setJsmc2( getjsmc(course_list.get( l ),2 ) );
            course_list.get( l ).setKkzc1s( getkkzc( course_list.get( l ),1,1 ) );
            course_list.get( l ).setKkzc1e( getkkzc( course_list.get( l ),1,2 ) );
            course_list.get( l ).setKkzc2s( getkkzc( course_list.get( l ),2,1 ) );
            course_list.get( l ).setKkzc2e( getkkzc( course_list.get( l ),2,2 ) );
            course_list.get( l ).setKcxq1( getkcxq( course_list.get( l ),1) );
            course_list.get( l ).setKcxq2( getkcxq( course_list.get( l ),2) );
            course_list.get( l ).setKcjc1( getkcjc( course_list.get( l ),1) );
            course_list.get( l ).setKcjc2( getkcjc( course_list.get( l ),2) );
        }
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

    public void fillCourseData(){
        for (int k=0;k<course_list.size();k++){
            Course coursedata=new Course( );
            coursedata.setKcxq1( course_list.get( k ).getKcxq1() );
            coursedata.setKcjc2( course_list.get( k ).getKcjc2() );
            coursedata.setKkzc1s( course_list.get( k ).getKkzc1s() );
            coursedata.setKkzc1e( course_list.get( k ).getKkzc1e() );
            coursedata.setKkzc2s( course_list.get( k ).getKkzc2s() );
            coursedata.setKkzc2e( course_list.get( k ).getKkzc2e() );
            coursedata.setDwmc( course_list.get( k ).getDwmc() );
            coursedata.setJsmc( course_list.get( k ).getJsmc() );
            coursedata.setJsxm( course_list.get( k ).getJsxm() );
            coursedata.setKcmc( course_list.get( k ).getKcmc() );
            coursedata.setKcsxm( course_list.get( k ).getKcsxm() );
            coursedata.setKcxzmc( course_list.get( k ).getKcxzmc() );
            coursedata.setKtmc( course_list.get( k ).getKtmc() );
            coursedata.setXf( course_list.get( k ).getXf() );
            coursedata.setXkjd( course_list.get( k ).getXkjd() );
            coursedata.setZxs( course_list.get( k ).getZxs() );
            coursedata.setKcsj1( course_list.get( k ).getKcsj1() );
            coursedata.setKcsj2( course_list.get( k ).getKcsj2() );
            coursedata.save();
            course_list.get( k ).setId( coursedata.getId() );
        }
    }

    public void fillStringArray() {
        SharedPreferences zzcc=mContext.getSharedPreferences( "zhouci", MODE_PRIVATE );
        int myZC=zzcc.getInt( "huoquzhouci",1 );
        contents = new String[6][7][2];
        for (i=0;i<6;i++){
            for (j=0;j<7;j++){
                contents[i][j][0]="";
            }
        }
        for (int s=0;s<course_list.size();s++){
            int kkzc1s=course_list.get( s ).getKkzc1s();
            int kkzc1e=course_list.get( s ).getKkzc1e();
            int kkzc2s=course_list.get( s ).getKkzc2s();
            int kkzc2e=course_list.get( s ).getKkzc2e();
            int kcxq1=course_list.get( s ).getKcxq1();
            int kcxq2=course_list.get( s ).getKcxq2();
            int kcjc1=course_list.get( s ).getKcjc1();
            int kcjc2=course_list.get( s ).getKcjc2();
            if (kkzc1s<=myZC&&kkzc1e>=myZC){
                if (kcjc1==-1||kcjc2==-1){
                    continue;
                }else {
                    contents[kcjc1][kcxq1-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc1();
                    contents[kcjc1][kcxq1-1][1]= String.valueOf( course_list.get( s ).getId() );
                }
            }
            if (kkzc2s<=myZC&&kkzc2e>=myZC){
                if (kcjc1==-1||kcjc2==-1){
                    continue;
                }else {
                    contents[kcjc2][kcxq2-1][0]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc2();
                    contents[kcjc2][kcxq2-1][1]= String.valueOf( course_list.get( s ).getId() );
                }
            }
        }

    }
    private String getjsmc(Coursebean c,int a){
        if (c.getJsmc()==null) {
            return null;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getJsmc().length()-1;i++) {
            String getstr=c.getJsmc().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
//            String reg = "[\\u4e00-\\u9fa5]+";
            //全为汉字
                return c.getJsmc();
        }else {
            String[] sj=c.getJsmc().split( "," );
            return sj[a-1];
        }
    }
    private int getkkzc(Coursebean c,int a,int b){
        if (c.getKkzc()==null){
            return -1;
        }
        if (c.getKkzc().length()==1||c.getKkzc().length()==2){
            return Integer.parseInt( c.getKkzc() );
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKkzc().length()-1;i++) {
            String getstr=c.getKkzc().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
            String[] shj=c.getKkzc().split( "-" );
            return Integer.parseInt( shj[b-1] );
        }else {
            String[] sj=c.getKkzc().split( "," );
            if (sj[0].length()==1||sj[0].length()==2){
                return Integer.parseInt( sj[0] );
            }
            if (sj[1].length()==1||sj[1].length()==2){
                return Integer.parseInt( sj[1] );
            }
            String[] sjdetail=sj[a-1].split( "-" );
            return Integer.parseInt( sjdetail[b-1] );
        }
    }
    private int getkcxq(Coursebean c,int a){
        if (c.getKcsj()==null){
            return -1;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKcsj().length()-1;i++) {
            String getstr=c.getKcsj().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
            return Integer.parseInt( c.getKcsj().substring( 0,1 ) );
        }else {
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
//            Log.d( "Course**------------->",sjdetail.substring( 0,1 ) );
            return Integer.parseInt( sjdetail.substring( 0,1 ) );
        }
    }
    private int getkcjc(Coursebean c,int a){
        if (c.getKcsj()==null){
            return -1;
        }
        int x=0;
        //遍历数组的每个元素
        for(int i=0;i<=c.getKcsj().length()-1;i++) {
            String getstr=c.getKcsj().substring(i,i+1);
            if(getstr.equals(",")){
                x++;
            }
        }
        if (x==0){
            String sjjc=c.getKcsj().substring( 1,5 );
            if (Objects.equals( sjjc, "0102" )){
                return 0;
            }else if (Objects.equals( sjjc, "0304" )){
                return 1;
            }else if (Objects.equals( sjjc, "0506" )){
                return 2;
            }else if (Objects.equals( sjjc, "0708" )){
                return 3;
            }else if (Objects.equals( sjjc, "0910" )){
                return 4;
            }else{
                return 5;
            }
        }else {
            String[] sj=c.getKcsj().split( "," );
            String sjdetail=sj[a-1];
            String sjjc=sjdetail.substring( 1,5 );
            if (Objects.equals( sjjc, "0102" )){
                return 0;
            }else if (Objects.equals( sjjc, "0304" )){
                return 1;
            }else if (Objects.equals( sjjc, "0506" )){
                return 2;
            }else if (Objects.equals( sjjc, "0708" )){
                return 3;
            }else if (Objects.equals( sjjc, "0910" )){
                return 4;
            }else{
                return 5;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course_set_yes:
                mCoursePop.dismiss();
                mCoursePop.setBackgroundAlpha(1);
                break;
            case R.id.course_zs:
                mCoursePop.dismiss();
                mCoursePop.setBackgroundAlpha(1);
                break;
        }

    }
}
