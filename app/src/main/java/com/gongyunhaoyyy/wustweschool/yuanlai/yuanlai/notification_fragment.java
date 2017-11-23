package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Basefragment.LazyFragment;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.notification_activity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99460 on 2017/10/14.
 */



public class notification_fragment extends Fragment implements View.OnClickListener {

    private CustomPopupWindow mCustomPopupWindow;
    private Button mImageButton;//悬浮窗的关闭按钮
    private View mLayoutPopupWindowView;//悬浮窗的布局
    private TextView mTvActivityRule;//悬浮窗的内容

    private List<element_item> notifications = new ArrayList<>();

    private boolean isPrepared;
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
        final View v = inflater.inflate(R.layout.fragment_notification,parent,false);
        mRefreshLayout = (RefreshLayout) v.findViewById(R.id.swipe_refresh_1);
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        notifications = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_1);
        isPrepared = true;
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
                Intent intent = new Intent(getActivity(),notification_activity.class);
                intent.putExtra("url5",detailUrl);
                startActivity(intent);
            }
        });
        return v;
    }


//    @Override
//    protected void lazyLoad() {
//        if(!isPrepared || !isVisble) {
//            return;
//        }
//        mRefreshLayout.autoRefresh();
//    }

    private void initNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    notifications.clear();
                    Document document = Jsoup.connect("http://www.lib.wust.edu.cn/bullet/bullet.aspx").get();
                    Elements elements = document.select("a.other");
                    for (int i=4;i<elements.size();i++){
                        Document document1 = Jsoup.connect("http://www.lib.wust.edu.cn/bullet/"+elements.get(i).attr("href")).get();
                        element_item element_item = new element_item(document1.getElementById("content").select("tbody").first().select("tr").first().select("td").get(1).select("table").first().select("table").first().select("tbody").first().select("tr").first().select("td").first().select("div").get(4).text());
                        element_item.setNews_time(document1.getElementById("content").select("tbody").first().select("tr").first().select("td").get(1).select("table").first().select("tbody").first().select("tr").first().select("td").first().select("div").get(1).select("div").first().text().substring(0, 16));
                        element_item.setNews_title(elements.get(i).text());
                        element_item.setUrl("http://www.lib.wust.edu.cn/bullet/"+elements.get(i).attr("href"));
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
