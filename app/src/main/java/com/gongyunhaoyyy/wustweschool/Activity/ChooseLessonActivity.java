package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.Adapter.XKJDAdapter;
import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Xkjieduan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ChooseLessonActivity extends SwipeBackActivity{
    private AlertDialog dialog;
    private String strxkjd,xh;
    private List<Xkjieduan> myXKjd=new ArrayList<>();
    private RecyclerView rec_xkjd;
    StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_lesson );
        SharedPreferences userdate=getSharedPreferences( "userdata",MODE_PRIVATE );
        String[] uddt=userdate.getString( "getuserdata","" ).split( "," );
        xh=uddt[0];
        layoutManager=new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL );
        View view= LayoutInflater.from(this).inflate
                (R.layout.toast_loading,null);
        AVLoadingIndicatorView avl=(AVLoadingIndicatorView) view.findViewById(R.id.avl);
        avl.show();
        TextView tv=view.findViewById(R.id.tv);
        tv.setText("拼命加载中...");
        dialog=new AlertDialog.Builder(ChooseLessonActivity.this,R.style.CustomDialog)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();
        rec_xkjd= (RecyclerView) findViewById( R.id.rec_choose_skjd );


        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Thread.sleep( 500 );
                    Ksoap2 ksoap2=new Ksoap2();
                    strxkjd=ksoap2.getXkjd( xh );
                    Gson gson=new Gson();
                    List<Xkjieduan> xkjdlist=gson.fromJson( strxkjd,new TypeToken<List<Xkjieduan>>(){}.getType());
                    myXKjd.addAll( xkjdlist );
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            XKJDAdapter myXKJDadapter=new XKJDAdapter( myXKjd );
                            rec_xkjd.setLayoutManager( layoutManager );
                            rec_xkjd.setAdapter( myXKJDadapter );
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();

    }

}
