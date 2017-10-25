package com.gongyunhaoyyy.wustweschool.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2017/10/23.
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private List<score> mlist=new ArrayList<>( );

    static class ViewHolder extends RecyclerView.ViewHolder {
        View scoreview;
        TextView bx,kcmc,ksxz,xf,jd,cj;

        public ViewHolder(View itemView) {
            super( itemView );
            scoreview=itemView;
            bx=(TextView)itemView.findViewById( R.id.bx );
            kcmc=(TextView)itemView.findViewById( R.id.kcmc );
            ksxz=(TextView)itemView.findViewById( R.id.ksxz );
            xf=(TextView)itemView.findViewById( R.id.xf );
            jd=(TextView)itemView.findViewById( R.id.jd );
            cj=(TextView)itemView.findViewById( R.id.cj );
        }
    }
    public ScoreAdapter(List<score> scorelist){
        mlist=scorelist;
    }

    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.score_item,parent,false );
                final ScoreAdapter.ViewHolder viewHolder=new ScoreAdapter.ViewHolder( view );
                viewHolder.scoreview.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        int position=viewHolder.getAdapterPosition();
                        score ss=mlist.get( position );
                        Toast.makeText( v.getContext(),ss.getKcmc(),Toast.LENGTH_SHORT ).show();
                    }
                } );
                return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
        score s=mlist.get( position );
        holder.bx.setText( s.getKclbmc() );
        holder.kcmc.setText( s.getKcmc() );
        holder.ksxz.setText( s.getKsxzmc() );
        holder.xf.setText( "学分:"+s.getXf() );
        holder.jd.setText( "绩点："+s.getJd() );
        holder.cj.setText( "成绩："+s.getZcj() );
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}