package com.gongyunhaoyyy.wustweschool.viewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gongyunhaoyyy.wustweschool.Adapter.AbsGridAdapter;
import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.LitePal.Course;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.courseSetPopwindow;
import com.gongyunhaoyyy.wustweschool.bean.Coursebean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/10.
 */

public class PagerCourse extends Fragment implements View.OnClickListener{
    private courseSetPopwindow mCoursePop;
    private View mLayoutPopView;//悬浮窗的布局
    Button set_yes;
    private Context mContext;
    private GridView detailCource;
    private String[][] contents;
    private AbsGridAdapter absGridAdapter;
    private List<Coursebean> course_list=new ArrayList<>(  );
    EditText xuehao3;
    ImageView set;
    int i,j,s,zhouc;
    String xh,coursestr;

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
        xuehao3=(EditText)view.findViewById( R.id.xuehao3 );
        set=(ImageView) view.findViewById( R.id.course_setting );

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
                        set_yes.setOnClickListener( PagerCourse.this );
                    }
                } );
//                zceditor.putInt( "huoquzhouci",8 );
//                zceditor.apply();

                mCoursePop.showView();
                mCoursePop.setBackgroundAlpha(0.9f);
            }
        } );

        view.findViewById(R.id.fasong3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xh=xuehao3.getText().toString();
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            Ksoap2 ksoap2=new Ksoap2();
                            coursestr= ksoap2.getCourseInfo( xh,"2017-2018-1" );
                            Gson gson=new Gson();
                            List<Coursebean> slist=gson.fromJson(coursestr,new TypeToken<List<Coursebean>>(){}.getType());
                            course_list.addAll( slist );
                            for (int k=0;k<slist.size();k++){
                                Course coursedata=new Course( );
                                coursedata.setDwmc( course_list.get( k ).getDwmc() );
                                coursedata.setJsmc( course_list.get( k ).getJsmc() );
                                coursedata.setJsxm( course_list.get( k ).getJsxm() );
                                coursedata.setKcmc( course_list.get( k ).getKcmc() );
                                coursedata.setKcsj( course_list.get( k ).getKcsj() );
                                coursedata.setKcsxm( course_list.get( k ).getKcsxm() );
                                coursedata.setKcxzmc( course_list.get( k ).getKcxzmc() );
                                coursedata.setKkzc( course_list.get( k ).getKkzc() );
                                coursedata.setKtmc( course_list.get( k ).getKtmc() );
                                coursedata.setXf( course_list.get( k ).getXf() );
                                coursedata.setXkjd( course_list.get( k ).getXkjd() );
                                coursedata.setZxs( course_list.get( k ).getZxs() );
                                coursedata.save();
                            }
                            //回到主线程更新UI
                            getActivity().runOnUiThread( new Runnable( ) {
                                @Override
                                public void run() {
                                    fillStringArray();
                                    absGridAdapter = new AbsGridAdapter(mContext);
                                    absGridAdapter.setContent(contents, 6, 7);
                                    detailCource.setAdapter( absGridAdapter );
                                }
                            } );
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } ).start();
            }
        });

        return view;
    }

    /**
     * 准备数据
     */
    public void fillStringArray() {
        String kkzc,kcsj;
        contents = new String[6][7];
        for (i=0;i<6;i++){
            for (j=0;j<7;j++){
                contents[i][j]="";
            }
        }
        for (s=0;s<course_list.size();s++){
            course_list.get( s ).getKkzc();
            if (isOKtoSetContent(course_list.get( s ).getKkzc(),course_list.get( s ).getKcsj())){
                contents[i][j]=course_list.get( s ).getKcmc()+"@"+course_list.get( s ).getJsmc();
            }
        }

    }

    private boolean isOKtoSetContent(String kkzc,String kcsj) {
        String[] zc1=kkzc.split(",");

        for(int i=0,len=zc1.length;i<len;i++){
            System.out.println(zc1[i].toString());
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course_set_yes:
                mCoursePop.dismiss();
                mCoursePop.setBackgroundAlpha(1);
                break;
        }

    }
}
