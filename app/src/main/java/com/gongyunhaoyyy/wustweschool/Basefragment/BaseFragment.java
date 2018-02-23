package com.gongyunhaoyyy.wustweschool.Basefragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.bean.score;

import java.util.List;

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
