package com.gongyunhaoyyy.wustweschool.viewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Adapter.ScoreAdapter;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2017/12/14.
 */

@SuppressLint("ValidFragment")
public class fragment_score_all extends Fragment {
    private List<score> mScorelist_all=new ArrayList<>();
    private TextView null_a;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recycler_score;

    @SuppressLint("ValidFragment")
    public fragment_score_all(List<score> scorelist_a) {
        mScorelist_all.addAll( scorelist_a );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.fragment_acore_all, container, false);

        recycler_score=(RecyclerView)view.findViewById( R.id.recycler_score_all );
        null_a=(TextView)view.findViewById( R.id.null_score_all );
        layoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        recycler_score.setLayoutManager( layoutManager );

//        if (mScorelist_all.isEmpty()){
//            null_a.setVisibility( View.VISIBLE );
//            recycler_score.setVisibility( View.GONE );
//        }else {
//            null_a.setVisibility( View.GONE );
//            recycler_score.setVisibility( View.VISIBLE );
            ScoreAdapter adapter=new ScoreAdapter(mScorelist_all);
            recycler_score.setAdapter( adapter );
            adapter.notifyDataSetChanged();
//        }

        return view;
    }
}
