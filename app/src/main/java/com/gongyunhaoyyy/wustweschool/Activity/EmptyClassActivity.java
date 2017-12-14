package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
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
    String score,xh,xq;
    EditText xhao;
    TextView haha;
    Button fasong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_empty_class );
        haha=(TextView)findViewById( R.id.hahaha );
        fasong=(Button)findViewById( R.id.fasong );
        xhao=(EditText)findViewById( R.id.xuehao );
        fasong.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                xh=xhao.getText().toString();
//                xq=getDate();
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            Ksoap2 ksoap2=new Ksoap2();
                            score=ksoap2.getScoreInfo( xh );
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat2 = new SimpleDateFormat("MM");
        int mm= Integer.parseInt( sDateFormat2.format(new java.util.Date()) );
        if (mm<9&&mm>2){
            mm=2;
        }else {
            mm=1;
        }
        String date1 = sDateFormat.format(new java.util.Date());
        int nextyear=Integer.parseInt( date1 )+1;
        String date2=String.valueOf( nextyear );
        String mm2=String.valueOf( mm );
        String date3=date1+"-"+date2+"-"+mm2;
        return date3;
    }
}
