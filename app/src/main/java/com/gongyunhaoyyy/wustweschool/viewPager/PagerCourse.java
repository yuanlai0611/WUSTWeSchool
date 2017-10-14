package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;

/**
 * Created by GongYunHao on 2017/10/10.
 */

public class PagerCourse extends Fragment {
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.pager_course,container,false);
        Log.d( "PagerCourse","onCreate execute" );
        view.findViewById(R.id.course_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是课程表按钮", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
