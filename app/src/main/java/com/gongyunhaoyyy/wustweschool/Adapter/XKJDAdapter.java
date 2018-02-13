package com.gongyunhaoyyy.wustweschool.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Xkjieduan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/2/10.
 */

public class XKJDAdapter extends RecyclerView.Adapter<XKJDAdapter.ViewHolder>{
    private List<Xkjieduan> myXK=new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_recycler_xkjd,parent,false );
        final XKJDAdapter.ViewHolder viewHolder=new XKJDAdapter.ViewHolder( view );
        viewHolder.xkjdview.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Xkjieduan myxkjdcreate=myXK.get( position );
                Toast.makeText( v.getContext(),"你点击了"+myxkjdcreate.getXklb(),Toast.LENGTH_SHORT ).show();
            }
        } );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Xkjieduan myxkjdbind=myXK.get( position );
        holder.xklb.setText( "类别："+myxkjdbind.getXklb() );
        holder.xkkssj.setText( "开始："+myxkjdbind.getXkkssj() );
        holder.xkjzsj.setText( "结束："+myxkjdbind.getXkjzsj() );
        holder.xkjd.setText( "阶段："+myxkjdbind.getXkjd() );
        holder.xnmc.setText( "学期："+myxkjdbind.getXnmc() );
    }

    public XKJDAdapter(List<Xkjieduan> myXK) {
        this.myXK = myXK;
    }

    @Override
    public int getItemCount() {
        return myXK.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout xkjdview;
        TextView xklb,xkkssj,xkjzsj,xkjd,xnmc;

        public ViewHolder(View itemView) {
            super( itemView );
            xklb=itemView.findViewById( R.id.g_xklb );
            xkkssj=itemView.findViewById( R.id.g_xkkssj );
            xkjzsj=itemView.findViewById( R.id.g_xkjzsj );
            xkjd=itemView.findViewById( R.id.g_xkjd );
            xnmc=itemView.findViewById( R.id.g_xnmc );
            xkjdview=itemView.findViewById( R.id.g_xkjd_view );
        }
    }
}
