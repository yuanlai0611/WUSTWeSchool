package com.gongyunhaoyyy.wustweschool.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.gongyunhaoyyy.wustweschool.Adapter.KYKCAdapter;
import com.gongyunhaoyyy.wustweschool.Base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.KYkecheng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TeachingAssessmentActivity extends BaseActivity {
    /**
     * 这是测试用的Activity，暂时就这样
     */
    private String xklbmx,xh;
    private List<KYkecheng> mKYkechengmx=new ArrayList<>(  );
    private String jx0502id="59",xklbname="1",xnxq="2017-2018-2",xkfs="0";
    private StaggeredGridLayoutManager mlayoutManager;
    private RecyclerView hhaha;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /**
         * 0学号 1密码 2姓名 3学生照片 4性别 5身份(普通本科)
         */
        setContentView( R.layout.activity_teaching_assessment );
        xh=getUserData()[0];
        hhaha=(RecyclerView)findViewById( R.id.hhhhhhhhh );
        dialog=loadingDialog( "拼命加载中...",false );
        mlayoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        dialog.show();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    Thread.sleep( 500 );
                    Ksoap2 ksoap2=new Ksoap2();
                    xklbmx=ksoap2.getKxkc( xh,jx0502id,xnxq,null,null,null );
                    Gson gson=new Gson();
                    List<KYkecheng> xkjdlist=gson.fromJson( xklbmx,new TypeToken<List<KYkecheng>>(){}.getType());
                    mKYkechengmx.addAll( xkjdlist );
                    //回到主线程更新UI
                    runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            KYKCAdapter myKYKCadapter=new KYKCAdapter( mKYkechengmx );
                            hhaha.setLayoutManager( mlayoutManager );
                            hhaha.setAdapter( myKYKCadapter );
                            dialog.dismiss();
                        }
                    } );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ).start();

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }


}
