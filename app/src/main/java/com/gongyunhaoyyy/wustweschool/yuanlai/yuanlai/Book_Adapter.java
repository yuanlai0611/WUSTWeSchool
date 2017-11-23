package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.R;

import java.util.List;

/**
 * Created by 99460 on 2017/10/14.
 */

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.ViewHolder> implements View.OnClickListener {

    private List<element_item> books;

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View bookView;
        TextView book_name;
        TextView book_author;
        ImageView book_pic;
        public ViewHolder(View view){
            super(view);
            bookView = view;
            book_name = (TextView) view.findViewById (R.id.book_name);
            book_pic = (ImageView) view.findViewById(R.id.book_pic);
            book_author = (TextView) view.findViewById(R.id.author_name);
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

    public Book_Adapter(List<element_item> books){
      this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        element_item book = books.get(position);
        holder.book_name.setText(book.getText());
        holder.book_pic.setImageBitmap(book.getBitmap());
        holder.book_author.setText(book.getAuthor_name());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount(){
        return books.size();
    }




}
