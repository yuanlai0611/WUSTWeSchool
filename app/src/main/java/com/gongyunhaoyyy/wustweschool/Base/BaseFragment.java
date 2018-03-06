package com.gongyunhaoyyy.wustweschool.Base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.bean.score;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//    ┏┓　   ┏┓
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//
//  Creste by GongYunHao on 2018/2/21
// 
public class BaseFragment extends Fragment{

    public Intent getIntent(Class clazz) {
        return new Intent(getActivity(), clazz);
    }

    public void startIntent(Class clazz) {
        startActivity(getIntent(clazz));
    }

    public Toast toast;
    public void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     *
     */
    public String[] getUserData(){
        SharedPreferences userdate=getActivity().getSharedPreferences( "userdata",MODE_PRIVATE );
        String[] uddt=userdate.getString( "getuserdata","" ).split( "," );
        return uddt;
    }

    public String getDateForXq(){
        int year2=2017;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat2 = new SimpleDateFormat("MM");
        int mm= Integer.parseInt( sDateFormat2.format(new java.util.Date()) );
        String year = sDateFormat.format(new java.util.Date());
        int year1= Integer.parseInt( year );
        if (mm>2&&mm<9){
            year1--;
            year2=year1+1;
            mm=2;
        }else if (mm<=2){
            year1--;
            year2=year1+1;
            mm=1;
        }else if (mm>=9){
            year2=year1++;
        }
        String date1=String.valueOf( year1 );
        String date2=String.valueOf( year2 );
        String mm2=String.valueOf( mm );
        String date3=date1+"-"+date2+"-"+mm2;
        return date3;
    }

    /**
     * 计算平均绩点
     * 平均绩点=∑学分*绩点　÷　∑学分
     * @return
     */
    public double ComputeAverigeScore(List<score> mScorelist){
        double xfccjh=0,xfzh=0;//学分×成绩和，学分总和
        if (mScorelist.size()<1){
            return 0.0;
        }else {
            for (score mysc:mScorelist){
                xfccjh+=Double.parseDouble( mysc.getJd() )*Double.parseDouble( mysc.getXf() );
                xfzh+=Double.parseDouble( mysc.getXf() );
            }
            if (xfzh==0){
                return 0;
            }
            return xfccjh/xfzh;
        }
    }

}
