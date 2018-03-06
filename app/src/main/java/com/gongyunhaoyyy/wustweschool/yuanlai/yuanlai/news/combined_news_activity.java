package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.news;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.gongyunhaoyyy.wustweschool.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class combined_news_activity extends SwipeBackActivity {

    WebView webView1;
    String url;
    Button buttonReturn;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            String html = msg.obj.toString();
            webView1.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_news);
        webView1 = (WebView)findViewById(R.id.webView3);
        buttonReturn = (Button)findViewById(R.id.combined_news_return);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra("url2");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document document = Jsoup.connect(url).get();
                    document.select("img").attr("width","95%");
                    Element element2 = document.select("div.bar").first();
                    element2.remove();
                    Element element = document.getElementById("147814024244193036");
                    Message message = new Message();
                    message.obj = element.toString();
                    handler.sendMessage(message);


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
