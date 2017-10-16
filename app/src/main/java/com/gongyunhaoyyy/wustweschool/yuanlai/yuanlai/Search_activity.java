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

import java.util.Timer;
import java.util.TimerTask;

public class Search_activity extends AppCompatActivity {
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private String[] mVals = new String[]{"Java", "Android", "iOS", "Python",
            "Mac OS", "PHP", "JavaScript", "Objective-C",
            "Groovy", "Pascal", "Ruby", "Go", "Swift"};//数据模拟，实际应从网络获取此数据
    EditText editText;



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
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
                    Toast.makeText(Search_activity.this, str, Toast.LENGTH_LONG).show();
                }
            });
            mFlowLayout.addView(tv);
        }
    }



}
