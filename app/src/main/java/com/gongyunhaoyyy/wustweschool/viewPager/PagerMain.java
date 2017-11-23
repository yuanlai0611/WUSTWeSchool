package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Activity.ChooseLessonActivity;
import com.gongyunhaoyyy.wustweschool.Activity.EmptyClassActivity;
import com.gongyunhaoyyy.wustweschool.Activity.LoginActivity;
import com.gongyunhaoyyy.wustweschool.Activity.ScoreActivity;
import com.gongyunhaoyyy.wustweschool.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerMain extends Fragment {
    private Context mContext;
    private String uddt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.pager_main, container, false);

        SharedPreferences ud=mContext.getSharedPreferences( "userdata", MODE_PRIVATE );
        uddt=ud.getString( "getuserdata","" );

        view.findViewById(R.id.score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uddt.isEmpty()){
                    Toast.makeText( getActivity(),"请登录！",Toast.LENGTH_SHORT ).show();
                    Intent tologin=new Intent( mContext, LoginActivity.class );
                    startActivity( tologin );
                    getActivity().onBackPressed();
                }else {
                    Intent toscore=new Intent( mContext, ScoreActivity.class );
                    startActivity( toscore );
                }
            }
        });
        view.findViewById( R.id.choose_lesson ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent tolesson=new Intent( mContext, ChooseLessonActivity.class );
                startActivity( tolesson );
            }
        } );
        view.findViewById( R.id.empty_class ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent toemptyclass=new Intent( mContext, EmptyClassActivity.class );
                startActivity( toemptyclass );
            }
        } );


        return view;
    }
}
