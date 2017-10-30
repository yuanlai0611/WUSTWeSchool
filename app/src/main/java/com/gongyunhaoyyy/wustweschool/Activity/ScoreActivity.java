package com.gongyunhaoyyy.wustweschool.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private List<score> mScorelist=new ArrayList<>();
    StaggeredGridLayoutManager layoutManager1;
    EditText xuehao2;
    Button fasong2;
    RecyclerView recycler_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_score );
        recycler_score=(RecyclerView)findViewById( R.id.recycler_score );
        fasong2=(Button)findViewById( R.id.fasong2 );
        xuehao2=(EditText)findViewById( R.id.xuehao2 );
        layoutManager1=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        recycler_score.setLayoutManager( layoutManager1 );

        fasong2.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                xh=xuehao2.getText().toString();
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            Ksoap2 ksoap2=new Ksoap2();
                            score=ksoap2.getScoreInfo( xh );
                            Gson gson=new Gson();
                            List<score> slist=gson.fromJson( score,new TypeToken<List<score>>(){}.getType());
                            mScorelist.addAll( slist );
                            //回到主线程更新UI
                            runOnUiThread( new Runnable( ) {
                                @Override
                                public void run() {
                                    ScoreAdapter adapter=new ScoreAdapter(mScorelist);
                                    recycler_score.setAdapter( adapter );
                                    adapter.notifyDataSetChanged();
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
