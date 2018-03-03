package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.element_item;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.Book_Adapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 99460 on 2017/10/14.
 */

public class history_fragment extends Fragment {

    private RecyclerView recyclerView;
    private boolean isHasLaodOnce;
    private boolean isCreate;
    private RefreshLayout mRefreshLayout;
    private List<element_item> history_books;
    private Book_Adapter adapter;
    private GridLayoutManager layoutManager;
    private String cookie;
    private boolean isLoginLibrary;
    private Document document;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        isCreate = true;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_history,parent,false);

        mRefreshLayout = (RefreshLayout) v.findViewById(R.id.swipe_refresh_2);
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        history_books = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_2);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Book_Adapter(history_books);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new Book_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),book_detail_activity.class);
                intent.putExtra("url",history_books.get(position).getBookUrl());
                startActivity(intent);

            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initBooks();
            }
        });


        return v;
    }

    private void initBooks(){

        history_books.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    Connection.Response response = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/book_hist.php")
                            .cookie("PHPSESSID",cookie)
                            .execute();

                    if (response.url().equals("http://opac.lib.wust.edu.cn:8080/reader/login.php")){
                        Intent intent = new Intent(getActivity(),library_login_activity.class);
                        startActivity(intent);
                    }else {
                        document = response.parse();
                        Elements elements = document.getElementsByClass("table_line").select("tr");
                        for (int i=1;i<elements.size();i++) {
                            String s2 = "";
                            String s = elements.get(i).select("a").attr("href");
                            String author_name = elements.get(i).select("td").get(2).text();
                            String text =elements.get(i).select("a").text();
                            s = "http://opac.lib.wust.edu.cn:8080"+s.substring(2);
                            Document document1 = Jsoup.connect(s).get();
                            Element element = document1.getElementById("item_detail");
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
                                    break;
                                }
                            }
                            s2 = "http://opac.lib.wust.edu.cn:8080/opac/ajax_douban.php?isbn="+s2;
                            element_item history_book = new element_item(text);
                            history_book.setBookUrl(s);
                            history_book.setAuthor_name(author_name);
                            while(true) {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(s2)
                                        .build();
                                Response response1 = okHttpClient.newCall(request).execute();
                                String responseData = response1.body().string();
                                responseData = "["+responseData+"]";
                                try {
                                    JSONArray jsonArray = new JSONArray(responseData);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String imageSrc = jsonObject.getString("image");
                                    if(imageSrc == "..\\/tpl\\/images\\/nobook.jpg"){
                                        continue;}

                                    Connection.Response response2 = Jsoup.connect(imageSrc)
                                            .ignoreContentType(true)
                                            .execute();
                                    byte[] data = response2.bodyAsBytes();
                                    history_book.setByte(data);
                                    history_books.add(history_book);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
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

    @Override
    public void setUserVisibleHint(boolean isVisbleToUser){
        super.setUserVisibleHint(isVisbleToUser);

        load();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        load();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
        cookie = sharedPreferences.getString("PHPSESSID","");
        isLoginLibrary = sharedPreferences.getBoolean("isLoginLibrary",false);
        load();
    }

    private void load() {
        if (isCreate && getUserVisibleHint() && !isHasLaodOnce && isLoginLibrary){
            mRefreshLayout.autoRefresh();
            isCreate = false;
            isHasLaodOnce = true;
        }
    }




}

