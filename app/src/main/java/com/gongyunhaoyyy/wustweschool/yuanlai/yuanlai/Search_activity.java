package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Timer;
import java.util.TimerTask;

public class Search_activity extends AppCompatActivity {
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initFlowView();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                openSoftKeyBoard(Search_activity.this);
            }
        },400);

    }
    public static void openSoftKeyBoard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(10998, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initFlowView() {
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        initData();
    }



    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                final String search[] = new String[100];


                try {

                    Document document = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/opac/top100.php").get();
                    Elements elements = document.select("table.thinBorder").first().select("tbody").first().select("a");
                    for (int i=0;i<elements.size();i++){
                        search[i] = elements.get(i).text();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < search.length; i++) {
                            TextView tv = (TextView) mInflater.inflate(
                                    R.layout.search_label_tv, mFlowLayout, false);
                            tv.setText(search[i]);
                            mFlowLayout.addView(tv);
                        }

                    }
                });

            }
        }).start();

    }



}
