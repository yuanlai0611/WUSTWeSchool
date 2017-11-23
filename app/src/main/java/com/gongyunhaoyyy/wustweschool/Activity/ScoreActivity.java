package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Adapter.ScoreAdapter;
import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    String xh,score;
    TextView cjcx;
    private List<score> mScorelist=new ArrayList<>();
    ProgressDialog progressDialog;
    StaggeredGridLayoutManager layoutManager1;
    RecyclerView recycler_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_score );
        SharedPreferences ud=getSharedPreferences( "userdata", MODE_PRIVATE );
        String[] uddt=ud.getString( "getuserdata","" ).split( "," );
        xh=uddt[0];
        recycler_score=(RecyclerView)findViewById( R.id.recycler_score );
        layoutManager1=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        recycler_score.setLayoutManager( layoutManager1 );

        progressDialog=new ProgressDialog( ScoreActivity.this );
        progressDialog.setMessage( "微小园拼命加载中..." );
        progressDialog.setCancelable( true );
        progressDialog.show();

        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Ksoap2 ksoap2=new Ksoap2();
                    score=ksoap2.getScoreInfo( xh );
                    Gson gson=new Gson();
                    List<score> slist=gson.fromJson( score,new TypeToken<List<score>>(){}.getType());
//                    if (slist.size()<=1){
//                        progressDialog.setTitle( "加载失败" );
//                        progressDialog.setMessage( "小园:我尽力了..." );
//                        progressDialog.setCancelable( true );
//                        progressDialog.show();
//                    }
                    mScorelist.addAll( slist );
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            ScoreAdapter adapter=new ScoreAdapter(mScorelist);
                            recycler_score.setAdapter( adapter );
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();
    }

}
