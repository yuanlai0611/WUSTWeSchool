package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 99460 on 2017/10/14.
 */

public class Element_item_Adapter extends RecyclerView.Adapter<Element_item_Adapter.ViewHolder> implements View.OnClickListener{

    private List<element_item> element_items;

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View element_item_view;
        TextView element_item_text;
        TextView element_item_title;
        TextView element_item_time;
        public ViewHolder(View view){
            super(view);
            element_item_view = view;
            element_item_text = (TextView) view.findViewById (R.id.notification_1);
            element_item_title = (TextView) view.findViewById(R.id.news_title);
            element_item_time = (TextView) view.findViewById(R.id.publish_time);
        }
    }

    @Override
    public void onClick(View v){
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public Element_item_Adapter(List<element_item> element_items){
        this.element_items = element_items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        element_item element_item = element_items.get(position);
        holder.element_item_text.setText(element_item.getText());
        holder.element_item_time.setText(element_item.getNews_time());
        holder.element_item_title.setText(element_item.getNews_title());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount(){
        return element_items.size();
    }

}
