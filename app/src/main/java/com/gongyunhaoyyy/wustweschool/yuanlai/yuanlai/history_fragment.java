package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99460 on 2017/10/14.
 */

public class history_fragment extends Fragment {


    private List<element_item> history_books = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_history,parent,false);
        initNotification();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_2);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        Book_Adapter adapter = new Book_Adapter(history_books);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void initNotification() {
        for (int i=0; i<2 ;i++){
            element_item notification_1 = new element_item("呼啸山庄");
            history_books.add(notification_1);
            element_item notification_2 = new element_item("钢铁是怎么炼成的");
            history_books.add(notification_2);
            element_item notification_3 = new element_item("大学物理");
            history_books.add(notification_3);
            element_item notification_4 = new element_item("金瓶梅");
            history_books.add(notification_4);
            element_item notification_5 = new element_item("汤姆叔叔的小屋");
            history_books.add(notification_5);
            element_item notification_6 = new element_item("三体");
            history_books.add(notification_6);
            element_item notification_7 = new element_item("三国演义");
            history_books.add(notification_7);
            element_item notification_8 = new element_item("上下五千年");
            history_books.add(notification_8);
            element_item notification_9 = new element_item("水浒传");
            history_books.add(notification_9);
            element_item notification_10 = new element_item("共产党宣言");
            history_books.add(notification_10);
        }
    }




}

