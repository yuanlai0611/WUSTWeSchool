package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class notification_activity extends SwipeBackActivity {

    WebView webView1;
    Button buttonReturn;
    String url;
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
        setContentView(R.layout.activity_notification);
        webView1 = (WebView)findViewById(R.id.webView5);
        buttonReturn = (Button)findViewById(R.id.notification_return);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        url = intent.getStringExtra("url5");
            Toast.makeText(notification_activity.this,url,Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document document = Jsoup.connect(url).get();
                    document.select("img").attr("width","95%");
                    Elements elements2 = document.select("img");
                    for (int j=0;j<elements2.size();j++) {

                        String formalsrc = elements2.get(j).attr("src");
                        elements2.get(j).attr("src","http://www.lib.wust.edu.cn"+formalsrc.substring(2));
                        //
                    }
                    //Element element2 = document.select("div.bar").first();
                    //element2.remove();
                    Element element = document.getElementById("content").select("tbody").first().select("tr").first().select("td").get(1).select("table").first().select("table").first().select("tbody").first().select("tr").first();
                    Element html = document.getElementById("content").select("tbody").first().select("tr").first().select("td").get(1).select("table").first().select("table").first().select("tbody").first().select("tr").first().select("td").first().select("div").get(1);
                    html.text("");
                    Element hh = document.getElementById("content").select("tbody").first().select("tr").first().select("td").get(1).select("table").first().select("table").first().select("tbody").first().select("tr").first().select("td").first().select("div").get(2);
                    hh.attr("style","font-size: 12px;  width:100%");
                    Message message = new Message();
                    message.obj = "<div>"+element.toString()+"</div>";
                    handler.sendMessage(message);


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
