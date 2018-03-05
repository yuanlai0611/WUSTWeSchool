package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.Activity.ChooseLessonActivity;
import com.gongyunhaoyyy.wustweschool.Activity.ScoreActivity;
import com.gongyunhaoyyy.wustweschool.Activity.TeachingAssessmentActivity;
import com.gongyunhaoyyy.wustweschool.Base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.R;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerMain extends BaseFragment {
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

        view.findViewById(R.id.score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent( ScoreActivity.class );
            }
        });
        view.findViewById( R.id.choose_lesson ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startIntent( ChooseLessonActivity.class );
            }
        } );
        view.findViewById( R.id.empty_class ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startIntent( TeachingAssessmentActivity.class );
            }
        } );
        view.findViewById( R.id.more_thing ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

            }
        } );

        return view;
    }
}
