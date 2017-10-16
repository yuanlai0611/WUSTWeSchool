package com.gongyunhaoyyy.wustweschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.R;

import java.util.List;

/**
 * Created by acer on 2017/10/14.
 */

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context ;
    private List<String> list;

    public MySpinnerAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate( R.layout.spinner_getview, null);
        TextView tvgetView=(TextView) convertView.findViewById(R.id.tvgetview);
        tvgetView.setText(getItem(position).toString());
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.spinner_getdropdownview, null);
        TextView tvdropdowview=(TextView) convertView.findViewById(R.id.tvgetdropdownview);
        tvdropdowview.setText(getItem(position).toString());
        return convertView;
    }
}
