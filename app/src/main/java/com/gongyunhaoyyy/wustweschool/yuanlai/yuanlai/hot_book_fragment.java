package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 99460 on 2017/10/14.
 */

public class hot_book_fragment extends Fragment {

    private boolean isHasLaodOnce;
    private int num = 10;
    private boolean isCreate;
    private List<element_item> hot_books;
    private RefreshLayout mRefreshLayout;
    Book_Adapter adapter;
    GridLayoutManager layoutManager;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_hot_book,parent,false);
        mRefreshLayout = (RefreshLayout) v.findViewById(R.id.swipe_refresh_3);
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        hot_books = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_3);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initNotification();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
//
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            org.jsoup.nodes.Document document = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/top/top_book.php").get();
                            Elements elements = document.getElementsByClass("table_line").select("tr");
                            for (int i=num;i<num+10;i++) {
                                String s2 = "";
                                String s = elements.get(i).select("a").attr("href");
                                String author_name = elements.get(i).select("td").get(2).text();
                                String text =elements.get(i).select("a").text();
                                s = "http://opac.lib.wust.edu.cn:8080"+s.substring(2);
                                org.jsoup.nodes.Document document1 = Jsoup.connect(s).get();
                                org.jsoup.nodes.Element element = document1.getElementById("item_detail");
                                Elements elements1 = element.select("dl");
                                for (int j=0;j<elements1.size();j++) {
                                    if (elements1.get(j).select("dt").text().equals("ISBN及定价:")) {
                                        String s1 = elements1.get(j).select("dd").text();
                                        for (int k=0;k<s1.length();k++) {
                                            if(s1.charAt(k)=='/'||s1.charAt(k)==' ') {
                                                break;
                                            }else if (s1.charAt(k)!='-') {
                                                s2+=s1.charAt(k);
                                            }
                                        }
                                        System.out.println(s2);
                                        break;
                                    }
                                }
                                s2 = "http://opac.lib.wust.edu.cn:8080/opac/ajax_douban.php?isbn="+s2;
                                element_item hot_book = new element_item(text);
                                hot_book.setAuthor_name(author_name);
                                while(true) {
                                    OkHttpClient okHttpClient = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url(s2)
                                            .build();
                                    Response response = okHttpClient.newCall(request).execute();
                                    String responseData = response.body().string();
                                    responseData = "["+responseData+"]";
                                    try {
                                        JSONArray jsonArray = new JSONArray(responseData);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String imageSrc = jsonObject.getString("image");
                                        if(imageSrc == "..\\/tpl\\/images\\/nobook.jpg"){
                                            continue;}

                                        URL myFileUrl = null;
                                        Bitmap bitmap = null;
                                        try {
                                            myFileUrl = new URL(imageSrc);
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                                                    .openConnection();
                                            conn.setDoInput(true);
                                            conn.connect();
                                            InputStream is = conn.getInputStream();
                                            bitmap = BitmapFactory.decodeStream(is);
                                            is.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        hot_book.setImage(bitmap);
                                        hot_books.add(hot_book);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            num+=9;
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                               refreshlayout.finishLoadmore();
                            }
                        });
                    }
                }).start();


            }
        });
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Book_Adapter(hot_books);
        recyclerView.setAdapter(adapter);
        return v;
    }

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



    private void initNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    num =10;
                    hot_books.clear();
                    org.jsoup.nodes.Document document = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/top/top_book.php").get();
                    Elements elements = document.getElementsByClass("table_line").select("tr");
                    for (int i=1;i<10;i++) {
                        String s2 = "";
                        String s = elements.get(i).select("a").attr("href");
                        String author_name = elements.get(i).select("td").get(2).text();
                        String text =elements.get(i).select("a").text();
                        s = "http://opac.lib.wust.edu.cn:8080"+s.substring(2);
                        org.jsoup.nodes.Document document1 = Jsoup.connect(s).get();
                        org.jsoup.nodes.Element element = document1.getElementById("item_detail");
                        Elements elements1 = element.select("dl");
                        for (int j=0;j<elements1.size();j++) {
                            if (elements1.get(j).select("dt").text().equals("ISBN及定价:")) {
                                String s1 = elements1.get(j).select("dd").text();
                                for (int k=0;k<s1.length();k++) {
                                    if(s1.charAt(k)=='/'||s1.charAt(k)==' ') {
                                        break;
                                    }else if (s1.charAt(k)!='-') {
                                        s2+=s1.charAt(k);
                                    }
                                }
                                System.out.println(s2);
                                break;
                            }
                        }
                        s2 = "http://opac.lib.wust.edu.cn:8080/opac/ajax_douban.php?isbn="+s2;
                        element_item hot_book = new element_item(text);
                        hot_book.setAuthor_name(author_name);
                        while(true) {
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(s2)
                                    .build();
                            Response response = okHttpClient.newCall(request).execute();
                            String responseData = response.body().string();
                            responseData = "["+responseData+"]";
                            try {
                                JSONArray jsonArray = new JSONArray(responseData);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String imageSrc = jsonObject.getString("image");
                                if(imageSrc == "..\\/tpl\\/images\\/nobook.jpg"){
                                    continue;}


                                URL myFileUrl = null;
                                Bitmap bitmap = null;
                                try {
                                    myFileUrl = new URL(imageSrc);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    HttpURLConnection conn = (HttpURLConnection) myFileUrl
                                            .openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();
                                    InputStream is = conn.getInputStream();
                                    bitmap = BitmapFactory.decodeStream(is);
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                hot_book.setImage(bitmap);
                                hot_books.add(hot_book);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
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

