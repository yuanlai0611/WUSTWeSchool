package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gongyunhaoyyy.wustweschool.Activity.BaseActivity;
import com.gongyunhaoyyy.wustweschool.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class book_detail_activity extends SwipeBackActivity {

    public static final int SECOND_DOCUMENT = 1;
    public static final int FIRST_DOCUMENT = 2;
    public static final int FIRST_DOCUMENT_TOAST = 3;
    public static final int SECOND_DOCUMENT_TOAST = 4;
    private String bookUrl;
    private ImageView imageViewBook;
    private TextView textViewWriter;
    private TextView textViewPublisher;
    private TextView textViewPrice;
    private TextView textViewIntroduction;
    private TextView textViewCallNumber;
    private TextView textViewBarCode;
    private TextView textViewCampus;
    private TextView textViewIsBorrowed;
    private CardView cardViewIntroduction;
    private View cardView;
    private LinearLayout linearLayoutContainer;
    private Button buttonBack;
    private AlertDialog dialog;
    private String Tag = "book_detail_activity";
    private Document document;
    private Document document1;
    private String url;

    public void setContentView(){ setContentView(R.layout.activity_book_detail); }

    public void initViews(){
        linearLayoutContainer = (LinearLayout)findViewById(R.id.book_where_container);
        imageViewBook = (ImageView)findViewById(R.id.book_img);
        textViewWriter = (TextView)findViewById(R.id.writer);
        textViewPublisher = (TextView)findViewById(R.id.publisher);
        textViewPrice = (TextView)findViewById(R.id.price);
        textViewIntroduction = (TextView)findViewById(R.id.introduction);
        cardViewIntroduction = (CardView)findViewById(R.id.introduction_cardview);
        buttonBack = (Button)findViewById(R.id.back);
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

    public void initListeners(){}

    public void initData(){}

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SECOND_DOCUMENT_TOAST:
                    Toast.makeText(book_detail_activity.this,"无法获取书本简介和图片:(",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case FIRST_DOCUMENT_TOAST:
                    Toast.makeText(book_detail_activity.this,"无法获取详细的书本信息:(",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case FIRST_DOCUMENT:
                    Document document = (Document)msg.obj;
                    textViewWriter.setText(document.getElementById("item_detail").select("dl").get(0).select("dd").text());
                    Log.d(Tag,document.getElementById("item_detail").select("dl").get(0).select("dd").text());
                    textViewPublisher.setText(document.getElementById("item_detail").select("dl").get(1).select("dd").text());
                    Log.d(Tag,document.getElementById("item_detail").select("dl").get(1).select("dd").text());
                    textViewPrice.setText(document.getElementById("item_detail").select("dl").get(2).select("dd").text());
                    Log.d(Tag,document.getElementById("item_detail").select("dl").get(2).select("dd").text());
                    Elements elements = document.getElementById("tab_item").select("table").select("tbody").select("tr");
                    for (int i=1 ; i<elements.size() ; i++){
                        cardView = View.inflate(book_detail_activity.this,R.layout.book_where,null);
                        textViewCallNumber = cardView.findViewById(R.id.call_number);
                        textViewBarCode = cardView.findViewById(R.id.barcode);
                        textViewCampus = cardView.findViewById(R.id.campus);
                        textViewIsBorrowed = cardView.findViewById(R.id.isBorrowed);
                        textViewCallNumber.setText(elements.get(i).select("td").get(0).text());
                        Log.d(Tag,i+". textViewCallNumber:"+elements.get(i).select("td").get(0).text());
                        textViewBarCode.setText(elements.get(i).select("td").get(1).text());
                        Log.d(Tag,i+". textViewBarCode:"+elements.get(i).select("td").get(1).text());
                        textViewCampus.setText(elements.get(i).select("td").get(3).text());
                        Log.d(Tag,i+". textViewCampus:"+elements.get(i).select("td").get(3).text());
                        textViewIsBorrowed.setText(elements.get(i).select("td").get(4).text());
                        Log.d(Tag,i+". textViewIsBorrowed:"+elements.get(i).select("td").get(4).text());
                        linearLayoutContainer.addView(cardView);
                    }
                    break;
                case SECOND_DOCUMENT:
                    Document document1 = (Document)msg.obj;
                    Glide.with(book_detail_activity.this).load(document1.getElementById("mainpic").select("img").get(0).attr("src")).into(imageViewBook);
                    Log.d(Tag,document1.getElementById("mainpic").select("img").get(0).attr("src"));

                    String introduction;
                    try{
                         introduction = document1.getElementById("link-report")
                                .select("div.intro")
                                .select("p")
                                .get(0).text();
                        cardViewIntroduction.setVisibility(View.VISIBLE);
                        textViewIntroduction.setText(introduction);
                        Log.d(Tag,introduction);
                    }catch (Exception e){
                        Toast.makeText(book_detail_activity.this,"无法获取简介:(",Toast.LENGTH_SHORT).show();

                    }


                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onBackPressed(){
//        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        Intent intent = getIntent();
        bookUrl = intent.getStringExtra("url");
        initViews();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialog = lodingDialog("正在加载内容",false);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                    try{
                    document = Jsoup.connect(bookUrl).timeout(5000).get();
                    Message message = new Message();
                    message.what = FIRST_DOCUMENT;
                    message.obj = document;
                    handler.sendMessage(message);
                        Element element = document
                                .select("ul.sharing_zy")
                                .select("li")
                                .get(0)
                                .select("a")
                                .get(0);
                    url = element.attr("href");
                    Log.d(Tag,url);
                    }catch (IOException e){
                        Message message = new Message();
                        message.what = FIRST_DOCUMENT_TOAST;
                        handler.sendMessage(message);
                        finish();
                        e.printStackTrace();
                    }
                    Log.d(Tag,"获得到第一个document");


                    try{
                        document1 = Jsoup.connect(url).timeout(5000).get();
                        Message message = new Message();
                        message.what = SECOND_DOCUMENT;
                        message.obj = document1;
                        handler.sendMessage(message);
                    }catch (IOException e){
                        Message message = new Message();
                        message.what = SECOND_DOCUMENT_TOAST;
                        handler.sendMessage(message);
                        e.printStackTrace();
                    }
                    Log.d(Tag,"获得到第二个document");





            }
        }).start();


    }
}
