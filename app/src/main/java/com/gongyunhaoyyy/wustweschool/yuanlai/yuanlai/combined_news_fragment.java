package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

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

import com.gongyunhaoyyy.wustweschool.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99460 on 2017/10/28.
 */

public class combined_news_fragment extends Fragment implements View.OnClickListener {

    private CustomPopupWindow mCustomPopupWindow;
    private Button mImageButton;//悬浮窗的关闭按钮
    private View mLayoutPopupWindowView;//悬浮窗的布局
    private TextView mTvActivityRule;//悬浮窗的内容


    private List<element_item> notifications = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_combined_news,parent,false);

        initNotification();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_6);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Element_item_Adapter adapter = new Element_item_Adapter(notifications);
        recyclerView.setAdapter(adapter);
        //setScrollListener();
        Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT);
        adapter.setmOnItemClickListener(new Element_item_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                mLayoutPopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout
                        .popupwindow_activity_rule, null);
                mCustomPopupWindow = new CustomPopupWindow(v.findViewById(R.id.test_6),
                        getActivity(), mLayoutPopupWindowView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                        .LayoutParams.WRAP_CONTENT, true);
                mCustomPopupWindow.setOnPopupWindowListener(new CustomPopupWindow
                        .PopupWindowListener() {

                    // TODO 设置活动内容
                    @Override
                    public void initView() {
                        mImageButton = (Button) mLayoutPopupWindowView.findViewById(R.id
                                .i_know);
                        mImageButton.setOnClickListener(combined_news_fragment.this);
                        mTvActivityRule = (TextView) mLayoutPopupWindowView.findViewById(R.id
                                .popupwindow_activity_rule_text);
                        mTvActivityRule.setText(notifications.get(position).toString());

                    }
                });
                mCustomPopupWindow.showView();
                Animation scaleAanimation = AnimationUtils.loadAnimation(getActivity(),R.anim.popupwindow_fade_in);
                mLayoutPopupWindowView.startAnimation(scaleAanimation);
                mCustomPopupWindow.setBackgroundAlpha(0.85f);
            }
        });
        return v;
    }


    private void initNotification() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("http://www.cnwust.com/default.html").get();
                    Elements url = document.getElementsByClass("newslist_147813868166571340").select("div.con").select("a");
                     for (int i=0 ; i<url.size();i++){
                         Document document1 = Jsoup.connect(url.get(i).attr("href")).get();
                         Element element1 = document1.getElementById("xwcontentdisplay");
	                     Elements elements1 = element1.select("p").select("span");
	                     String s = "";
                         for (int j=0;j<elements1.size();j++) {
	                	  String s1 = elements1.get(j).text();
	                	  if (s1!="") {
	                		 s = s + s1;
	                	  }
                         }
                         element_item element_item = new element_item(s);
                         notifications.add(element_item);
                     }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();

    }
    public void onClick(View v) {
        switch (v.getId()) {

            //关闭悬浮窗
            case R.id.i_know:
                mCustomPopupWindow.dismiss();
                mCustomPopupWindow.setBackgroundAlpha(1);
                break;
        }
    }

}
