package com.wubei_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.R;

import java.util.List;


/**
 * Created by wu on 2017/10/15.
 */

public class MoneyAdapter extends ArrayAdapter<Money> {

    public MoneyAdapter(Context context, int textViewResourceId, List<Money> objects){
        super(context, textViewResourceId,objects);
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    Money money = getItem(position);
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(position, parent, false);
        viewHolder = new ViewHolder();
        viewHolder.place = (TextView) view.findViewById(R.id.merchants);
        viewHolder.smallMoney = (TextView) view.findViewById(R.id.volume);
        viewHolder.date = (TextView) view.findViewById(R.id.datetime);
        viewHolder.totalMoney = (TextView) view.findViewById(R.id.balance);
        view.setTag(viewHolder);
    } else {
        view = convertView;
        viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.date.setText(money.getDate());
        viewHolder.smallMoney.setText(money.getSmallmoney());
        viewHolder.smallMoney.setText(money.getPlace());
        viewHolder.smallMoney.setText(money.getTotalmoney());
    return view;
}

    class ViewHolder{
        TextView date;
        TextView smallMoney;
        TextView place;
        TextView totalMoney;
    }
}
