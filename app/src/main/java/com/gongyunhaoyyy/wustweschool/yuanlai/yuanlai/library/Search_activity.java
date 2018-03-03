package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class Search_activity extends SwipeBackActivity {
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private TextView textViewSearchButton;
    private EditText editTextSearch;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        textViewSearchButton = (TextView)findViewById(R.id.button_search);
        editTextSearch = (EditText) findViewById(R.id.search_text);
        textViewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextSearch.getText().toString().isEmpty()){
                    String searchText = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?strSearchType=title&strText="+editTextSearch.getText().toString();
                    Intent intent = new Intent(Search_activity.this,search_book_list_activity.class);
                    intent.putExtra("searchText",searchText);
                    startActivity(intent);
                }else{
                    Toast.makeText(Search_activity.this,"不能输入为空！",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initFlowView();

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                openSoftKeyBoard(Search_activity.this);
//            }
//        },400);

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
                        for (i = 0; i < search.length; i++) {
                            final TextView tv = (TextView) mInflater.inflate(
                                    R.layout.search_label_tv, mFlowLayout, false);
                            tv.setText(search[i]);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String text = tv.getText().toString();
                                    int i = text.lastIndexOf("(");
                                    String temp = text.substring(0,i);
                                    String searchText = "http://opac.lib.wust.edu.cn:8080/opac/openlink.php?strSearchType=title&strText="+temp;
                                    Intent intent = new Intent(Search_activity.this,search_book_list_activity.class);
                                    intent.putExtra("searchText",searchText);
                                    startActivity(intent);

                                }
                            });
                            mFlowLayout.addView(tv);
                        }

                    }
                });

            }
        }).start();

    }



}
