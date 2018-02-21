package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Activity.LoginActivity;
import com.gongyunhaoyyy.wustweschool.Basefragment.BaseFragment;
import com.gongyunhaoyyy.wustweschool.LitePal.Course;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.WaveView;

import org.litepal.crud.DataSupport;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerAboutUs extends BaseFragment implements View.OnClickListener{
    private Button exit;
    private Context mContextUs;
    private ImageView ima_xb,ima_what;
    private TextView stuname,stusf,stuid;
    private String[] uddt_detail;
    private boolean isboy;
//    private ImageView imageView;
//    private WaveView waveView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContextUs = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_aboutus, container, false);
        SharedPreferences ud=mContextUs.getSharedPreferences( "userdata", MODE_PRIVATE );
        String uddt=ud.getString( "getuserdata","" );
        uddt_detail=uddt.split( "," );
        init(view);
        setOnClickListener();
        inflateData();
        //实现图标和wave上下联动的代码
//        imageView = (ImageView) view.findViewById(R.id.image);
//        waveView = (WaveView) view.findViewById(R.id.wave_view);
//        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
//        lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
//        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
//            @Override
//            public void OnWaveAnimation(float y) {
//                lp.setMargins(0,0,0,(int)y+15);
//                imageView.setLayoutParams(lp);
//            }
//        });

        return view;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor nameeditor = mContextUs.getSharedPreferences( "userdata", MODE_PRIVATE ).edit( );
        switch (v.getId()){
            case R.id.exit_button:
                nameeditor.putString( "getuserdata","" );
                nameeditor.apply();
                startIntent( LoginActivity.class );
//                finish方法无效，fragment依赖与Activity，不能在fragment中关闭活动
//                getActivity().finish();
//                模拟按返回键，销毁当前Activity，有效
                DataSupport.deleteAll( Course.class );
                getActivity().onBackPressed();
        }
    }

    private void setOnClickListener(){
        exit.setOnClickListener( this );
    }

    /**
     * 0学号 1密码 2姓名 3学生照片 4性别 5身份(普通本科)
     */
    private void inflateData(){
        stuname.setText( uddt_detail[2] );
        stusf.setText( uddt_detail[5] );
        stuid.setText( "ID："+uddt_detail[0] );
        if (isboy){
            ima_xb.setImageResource( R.drawable.icon_boy );
        }else {
            ima_xb.setImageResource( R.drawable.icon_girl );
        }
    }

    private void init(View view){
        isboy = uddt_detail[4].equals( "男" );
        exit=(Button)view.findViewById( R.id.exit_button );
        stuname=(TextView)view.findViewById( R.id.student_name );
        stusf=(TextView)view.findViewById( R.id.student_sf );
        stuid=(TextView)view.findViewById( R.id.student_id );
        ima_xb=(ImageView)view.findViewById( R.id.image_xingbie );
        ima_what=(ImageView)view.findViewById( R.id.image_user_what );
    }
}
