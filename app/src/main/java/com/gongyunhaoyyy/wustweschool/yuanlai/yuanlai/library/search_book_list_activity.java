package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.gongyunhaoyyy.wustweschool.Activity.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.Element_item_Adapter;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.element_item;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class search_book_list_activity extends SwipeBackActivity {

    private List<element_item> searchBooks;
    private RecyclerView recyclerView;
    private Element_item_Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String url;
    private AlertDialog dialog;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initViews();
        Intent intent = getIntent();
        url = intent.getStringExtra("searchText");
        searchBooks = new ArrayList<>();
        dialog = lodingDialog( "正在搜索图书...",false );
        dialog.show();
        initSearchBooks();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Element_item_Adapter(searchBooks);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new Element_item_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String bookDetailUrl = searchBooks.get(position).getUrl();
                Intent intent1 = new Intent(search_book_list_activity.this,book_detail_activity.class);
                intent1.putExtra("url",bookDetailUrl);
                startActivity(intent1);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public AlertDialog lodingDialog(String text,boolean cancelable){
        View view= LayoutInflater.from(this).inflate
                (R.layout.toast_loading,null);
        AVLoadingIndicatorView avl=(AVLoadingIndicatorView) view.findViewById(R.id.avl);
        avl.show();
        TextView tv=view.findViewById(R.id.tv);
        tv.setText(text);
        AlertDialog dialog=new AlertDialog.Builder(this,R.style.CustomDialog)
                .setView(view)
                .setCancelable(cancelable)
                .create();
        return dialog;
    }

    public void setContentView(){ setContentView(R.layout.activity_search_book_list); }

    public void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.book_search_recyclerview);
        buttonBack = (Button)findViewById(R.id.back);
    }

    public void initListeners(){

    }

    public void initData(){}

    public void initSearchBooks(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    Document document = Jsoup.connect(url)
                            .get();
                    Elements elements = document.getElementById("search_book_list")
                            .select("li");
                    for (int i = 0 ; i < elements.size(); i++){
                        element_item elementItem = new element_item(elements.get(i).select("p").text().replaceAll(elements.get(i).select("p").select("span").text(),""));
                        //Log.d("搜索",elements.get(i).select("p").text().replaceAll(elements.get(i).select("p").select("span").text(),""));
                        elementItem.setUrl("http://opac.lib.wust.edu.cn:8080/opac/"+elements.get(i).select("h3").select("a").attr("href"));
                        //Log.d("搜索","http://opac.lib.wust.edu.cn:8080/opac/"+elements.get(i).select("h3").select("a").attr("href"));
                        elementItem.setNews_title(elements.get(i).select("h3").select("a").text());
                        //Log.d("搜索",elements.get(i).select("h3").select("a").text());
                        elementItem.setNews_time(elements.get(i).select("p").select("span").text());
                        //Log.d("搜索",elements.get(i).select("p").select("span").text());
                        searchBooks.add(elementItem);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

            }
        }).start();

    }
}
