package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.Element_item_Adapter;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.element_item;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99460 on 2017/10/28.
 */

public class academy_news_fragment extends Fragment {

    private boolean isHasLaodOnce;
    private boolean isCreate;
    private static boolean isFirstIn = true;
    private RefreshLayout mRefreshLayout;
    Element_item_Adapter adapter;
    private List<element_item> notifications ;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        isCreate=true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisbleToUser){
        super.setUserVisibleHint(isVisbleToUser);
      load();
    }

    private void load() {
        if (isCreate && getUserVisibleHint() && !isHasLaodOnce){
            mRefreshLayout.autoRefresh();
            isCreate = false;
            isHasLaodOnce = true;
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_academy_news,parent,false);
        mRefreshLayout = (RefreshLayout) v.findViewById(R.id.swipe_refresh_7);
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        notifications = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_7);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initNotification();
            }
        });
//        if (isFirstIn == true) {
//            mRefreshLayout.autoRefresh();
//            isFirstIn = false;
//        }

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Element_item_Adapter(notifications);
        recyclerView.setAdapter(adapter);

        adapter.setmOnItemClickListener(new Element_item_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final String detailUrl = notifications.get(position).getUrl();
                Intent intent = new Intent(getActivity(),academy_news_activity.class);
                intent.putExtra("url3",detailUrl);
                startActivity(intent);
            }
        });
        return v;
    }



    private void initNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    notifications.clear();
                    org.jsoup.nodes.Document document = Jsoup.connect("http://www.cnwust.com/default.html").get();
                    Elements elements = document.getElementsByClass("newslist_147813878340454123").select("div.con").select("li");
                    for (int i=0;i<elements.size();i++){
                        org.jsoup.nodes.Document document1 = Jsoup.connect(elements.get(i).select("a").attr("href")).get();
                        Elements elements1 = document1.select("div.con").select("div.xwcon").select("p");
                        String s = elements1.text();
                        element_item element_item = new element_item(s);
                        element_item.setNews_time(document1.select("div.title").select("h4").select("span.pubtime").text());
                        element_item.setNews_title(elements.get(i).select("a").attr("title"));
                        element_item.setUrl(elements.get(i).select("a").attr("href"));
                        notifications.add(element_item);
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.finishRefresh();
                    }
                });
            }
        }).start();
    }
}
