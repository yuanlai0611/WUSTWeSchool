package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;

import java.text.SimpleDateFormat;

public class EmptyClassActivity extends AppCompatActivity {
    String score,xh;
    TextView haha;
    Button fasong;
    String jx0502id="59",xklbname="1",xnxq="2017-2018-2",xkfs="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_empty_class );
        /**
         * 0学号 1密码 2姓名 3学生照片 4性别 5身份(普通本科)
         */
        SharedPreferences userdate=getSharedPreferences( "userdata",MODE_PRIVATE );
        String[] uddt=userdate.getString( "getuserdata","" ).split( "," );
        xh=uddt[0];
        haha=(TextView)findViewById( R.id.hahaha );
        fasong=(Button)findViewById( R.id.fasong );
        fasong.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
//                xq=getDate();
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            Ksoap2 ksoap2=new Ksoap2();
//                            score=ksoap2.getKxkc( xh,jx0502id,xnmc );
//                            score=ksoap2.getTerm( );
                            score=ksoap2.getKxkc( xh,jx0502id,xnxq,null,null,null );
                            //回到主线程更新UI
                            runOnUiThread( new Runnable( ) {
                                @Override
                                public void run() {
                                    haha.setText( score );
                                }
                            } );
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } ).start();
            }
        } );

    }

    public String getDate(){
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
}
