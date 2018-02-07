package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.DrawView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username,et_password;
    private TextView nologin;
    private String login_result,user,pass,userdt,xm;
    private String[] reslut2;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        SharedPreferences ud=getSharedPreferences( "userdata", MODE_PRIVATE );
        String uddt=ud.getString( "getuserdata","" );
        if (!uddt.isEmpty()){
            Intent it=new Intent( LoginActivity.this,MainActivity.class );
            startActivity( it );
            finish();
        }else {
            setContentView( R.layout.activity_login );
            @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor nameeditor = getSharedPreferences( "userdata", MODE_PRIVATE ).edit( );
            init();
            //透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            drawBackGround();
            View view= LayoutInflater.from(this).inflate
                    (R.layout.toast_loading,null);
            AVLoadingIndicatorView avl=(AVLoadingIndicatorView) view.findViewById(R.id.avl);
            avl.show();
            TextView tv=view.findViewById(R.id.tv);
            tv.setText("小园登陆中...");
            dialog=new AlertDialog.Builder(LoginActivity.this,R.style.CustomDialog)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            Button login=(Button)findViewById( R.id.login );
            final Intent intent=new Intent( LoginActivity.this,MainActivity.class );
            login.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    user=et_username.getText().toString();
                    pass=et_password.getText().toString();
                    userdt=user+","+pass;
                    if (user.length()!=12||pass.length()<4){
                        Toast.makeText( LoginActivity.this,"输入有误",Toast.LENGTH_SHORT ).show();
                    } else {
                        dialog.show();
                        new Thread( new Runnable( ) {
                            @Override
                            public void run() {
                                try {
                                    //这样看起来比较流畅
                                    Thread.sleep( 600 );
                                    Ksoap2 ksoap2=new Ksoap2();
                                    login_result=ksoap2.getLoginInfo( user,pass );
                                    reslut2=login_result.split( "," );
                                        //回到主线程更新UI
                                        runOnUiThread( new Runnable( ) {
                                            @Override
                                            public void run() {
                                                if (reslut2[0].equals( "0" )){
                                                    Toast.makeText( LoginActivity.this,reslut2[1],Toast.LENGTH_SHORT ).show();
                                                    dialog.dismiss();
                                                }else if (reslut2[0].equals( "1" )){
                                                    String userData=parseJSONwithJSONObject(login_result.substring( 3,login_result.length()-1 ));
                                                    /**
                                                     * 0学号 1密码 2姓名 3学生照片 4性别 5身份(普通本科)
                                                     */
                                                    userdt=userdt+","+userData;
                                                    //获取图像失败了......
//                                                    ImageView imageView=findViewById( R.id.tuxiang );
//                                                    String strURL=userdt.split( "," )[3];
//                                                    Bitmap bitmap= null;
//                                                    try {
//                                                        bitmap = getBitmap(strURL);
//                                                    } catch (IOException e) {
//                                                        e.printStackTrace( );
//                                                    }
//                                                    imageView.setImageBitmap( bitmap );
//                                                    Log.d( "user信息~~~~~~~~~~~>",strURL );
                                                    Toast.makeText( LoginActivity.this,xm+",欢迎你~",Toast.LENGTH_SHORT ).show();
                                                    nameeditor.putString( "getuserdata",userdt );
                                                    nameeditor.apply();
                                                    startActivity( intent );
                                                    dialog.dismiss();
                                                    finish();
                                                }else {
                                                    Toast.makeText( LoginActivity.this,R.string.nointernet,Toast.LENGTH_SHORT ).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        } );

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        } ).start();
                    }
                }
            } );

            nologin.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    startActivity( intent );
                    finish();
                }
            } );
        }
    }

    private String parseJSONwithJSONObject(String jsonData){
        String parseData = null;
        try {
                JSONObject jsonObject=new JSONObject( jsonData );
                xm=jsonObject.getString( "xm" );
                String xszp=jsonObject.getString( "xszp" );
                String xb=jsonObject.getString( "xb" );
                String sf=jsonObject.getString( "sf" );
                parseData=xm+","+xszp+","+xb+","+sf;

        }catch (Exception e){
            e.printStackTrace();
        }
        return parseData;
    }

//    public Bitmap getBitmap(String path) throws IOException {
//
//        URL imgUrl = null;
//        Bitmap bitmap = null;
//        try {
//            imgUrl = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) imgUrl
//                    .openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

    private void drawBackGround(){
        FrameLayout ll=(FrameLayout) findViewById(R.id.login_frame);
        final DrawView view=new DrawView(LoginActivity.this);
        view.setMinimumHeight(100);
        view.setMinimumWidth(400);
        //通知view组件重绘
        view.invalidate();
        ll.addView(view);
    }

    private void init(){
        et_username=(EditText)findViewById( R.id.et_username );
        et_password=(EditText)findViewById( R.id.et_password );
        nologin=(TextView)findViewById( R.id.no_login_tv );
    }
}
