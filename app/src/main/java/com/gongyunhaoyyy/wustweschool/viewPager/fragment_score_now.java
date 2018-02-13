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
public class fragment_score_now extends Fragment {
    TextView null_n,averige_n;
    private List<score> mScorelist_now=new ArrayList<>();
    private int number=0;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recycler_score;

    @SuppressLint("ValidFragment")
    public fragment_score_now(List<score> scorelist_n) {
        mScorelist_now.addAll( scorelist_n );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.fragment_acore_all, container, false);

        recycler_score=(RecyclerView)view.findViewById( R.id.recycler_score_all );
        null_n=(TextView)view.findViewById( R.id.null_score_now );
        averige_n=view.findViewById( R.id.averige_now );
//        averige_n.setText( "平均绩点： "+ComputeAverigeScore() );
        layoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        recycler_score.setLayoutManager( layoutManager );

//        for (score ssss:mScorelist_now){
//            number++;
//        }
//        Toast.makeText( getActivity(), String.valueOf( i ),Toast.LENGTH_SHORT ).show();
        //空的成绩~~
        if (number<1){
//            这句话有问题，不知道为什么
//            null_n.setVisibility( View.VISIBLE );
//            recycler_score.setVisibility( View.GONE );
        }else {
//            null_n.setVisibility( View.GONE );
//            recycler_score.setVisibility( View.VISIBLE );
//            for (int csy=0;csy<mScorelist_now.size();csy++){
//                Log.d( ">>>>>>>>>>>>>>", mScorelist_now.get( csy ).getKkxq());
//            }

            ScoreAdapter adapter=new ScoreAdapter(mScorelist_now);
            recycler_score.setAdapter( adapter );
            adapter.notifyDataSetChanged();
        }

        return view;
    }

    /**
     * 计算平均绩点
     * 平均绩点=∑学分*绩点　÷　∑学分
     * @return
     */
    public double ComputeAverigeScore(){
        double xfccjh=0,xfzh=0;//学分×成绩和，学分总和
        if (mScorelist_now.size()<1){
            return 0.0;
        }else {
            for (score mysc:mScorelist_now){
                xfccjh+=Double.parseDouble( mysc.getJd() )*Double.parseDouble( mysc.getXf() );
                xfzh+=Double.parseDouble( mysc.getXf() );
            }
            if (xfzh==0){
                return 0;
            }
            return xfccjh/xfzh;
        }
    }

}
