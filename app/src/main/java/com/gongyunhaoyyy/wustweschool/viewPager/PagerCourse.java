package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Adapter.AbsGridAdapter;
import com.gongyunhaoyyy.wustweschool.Adapter.MySpinnerAdapter;
import com.gongyunhaoyyy.wustweschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GongYunHao on 2017/10/10.
 */

public class PagerCourse extends Fragment {
    private Context mContext;
//    private Spinner spinner;
    private GridView detailCource;
    private String[][] contents;
    private AbsGridAdapter absGridAdapter;
    private List<String> dataList;
    private MySpinnerAdapter mySpinnerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.pager_course,container,false);
        Log.d( "PagerCourse","onCreate execute" );
//        spinner = (Spinner)view.findViewById(R.id.switchWeek);
        detailCource = (GridView)view.findViewById(R.id.courceDetail);
        fillStringArray();
        absGridAdapter = new AbsGridAdapter(mContext);
        absGridAdapter.setContent(contents, 6, 7);
        detailCource.setAdapter( absGridAdapter );
        //创建Spinner数据
//        mySpinnerAdapter=new MySpinnerAdapter(mContext, getData());
//        spinner.setAdapter(mySpinnerAdapter);

        view.findViewById(R.id.course_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是课程表按钮", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /**
     * 准备数据
     */
    public void fillStringArray() {
        contents = new String[6][7];
        contents[0][0] = "我是第一节\n&11304";
        contents[1][0] = "我是第二节\n&11304";
        contents[2][0] = "我是第三节\n&11304";
        contents[3][0] = "我是第四节\n&11304";
        contents[4][0] = "";
        contents[5][0] = "";
        contents[0][1] = "我是第七节\n&11304";
        contents[1][1] = "";
        contents[2][1] = "面向对象程序设计\n&30305";
        contents[3][1] = "面向对象程序设计\nA309";
        contents[4][1] = "";
        contents[5][1] = "";
        contents[0][2] = "微机原理及应用\nE203";
        contents[1][2] = "电磁场理论\nA212";
        contents[2][2] = "现代测试技术\nB211";
        contents[3][2] = "";
        contents[4][2] = "";
        contents[5][2] = "";
        contents[0][3] = "面向对象程序设计\nA309";
        contents[1][3] = "传感器电子测量A\nC309";
        contents[2][3] = "";
        contents[3][3] = "";
        contents[4][3] = "";
        contents[5][3] = "";
        contents[0][4] = "数据结构与算法\nB211";
        contents[1][4] = "微机原理及应用\nE203";
        contents[2][4] = "";
        contents[3][4] = "";
        contents[4][4] = "";
        contents[5][4] = "";
        contents[0][5] = "";
        contents[1][5] = "";
        contents[2][5] = "";
        contents[3][5] = "";
        contents[4][5] = "";
        contents[5][5] = "";
        contents[0][6] = "";
        contents[1][6] = "";
        contents[2][6] = "";
        contents[3][6] = "";
        contents[4][6] = "";
        contents[5][6] = "龚云浩\n哈哈|:)";
    }

//    public List<String> getData() {
//        dataList = new ArrayList<>();
//        for(int i = 1; i < 21; i++) {
//            dataList.add("第" + i + "周");
//        }
//        return dataList;
//    }

}
