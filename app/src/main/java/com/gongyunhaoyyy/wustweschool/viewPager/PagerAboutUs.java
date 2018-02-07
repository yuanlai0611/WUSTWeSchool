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

import com.gongyunhaoyyy.wustweschool.Activity.LoginActivity;
import com.gongyunhaoyyy.wustweschool.LitePal.Course;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.WaveView;

import org.litepal.crud.DataSupport;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerAboutUs extends Fragment implements View.OnClickListener{
    Button exit;
    private Context mContextUs;
    private ImageView imageView;
    private WaveView waveView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContextUs = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_aboutus, container, false);
        exit=(Button)view.findViewById( R.id.exit_button );
        exit.setOnClickListener( this );

        imageView = (ImageView) view.findViewById(R.id.image);
        waveView = (WaveView) view.findViewById(R.id.wave_view);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0,0,0,(int)y+15);
                imageView.setLayoutParams(lp);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor nameeditor = mContextUs.getSharedPreferences( "userdata", MODE_PRIVATE ).edit( );
        switch (v.getId()){
            case R.id.exit_button:
                Intent ex=new Intent( mContextUs, LoginActivity.class );
                nameeditor.putString( "getuserdata","" );
                nameeditor.apply();
                startActivity( ex );
//                finish方法无效，fragment依赖与Activity，不能在fragment中关闭活动
//                getActivity().finish();
//                模拟按返回键，销毁当前Activity，有效
                DataSupport.deleteAll( Course.class );
                getActivity().onBackPressed();
        }
    }
}
