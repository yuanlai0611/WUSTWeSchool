package com.gongyunhaoyyy.wustweschool.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;

public class EmptyClassActivity extends AppCompatActivity {
    String score,xh;
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
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            Ksoap2 ksoap2=new Ksoap2();
                            score=ksoap2.getCourseInfo( xh,"2017-2018-1" );
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
}
