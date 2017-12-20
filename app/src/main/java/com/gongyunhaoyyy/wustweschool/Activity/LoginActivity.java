package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username,et_password;
    private TextView nologin;
    private String login_result,user,pass,userdt;
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
                    if (user.length()!=12||pass.length()<1){
                        Toast.makeText( LoginActivity.this,"输入有误",Toast.LENGTH_SHORT ).show();
                    } else {
                        dialog.show();
                        new Thread( new Runnable( ) {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep( 800 );
                                    Ksoap2 ksoap2=new Ksoap2();
                                    login_result=ksoap2.getLoginInfo( user,pass );
                                    if (login_result.length()>5){
                                        reslut2=login_result.split( "," );
                                        //回到主线程更新UI
                                        runOnUiThread( new Runnable( ) {
                                            @Override
                                            public void run() {
                                                if (Objects.equals( reslut2[1], "服务器错误" )){
                                                    Toast.makeText( LoginActivity.this,"登录成功",Toast.LENGTH_SHORT ).show();
                                                    nameeditor.putString( "getuserdata",userdt );
                                                    nameeditor.apply();
                                                    startActivity( intent );
                                                    dialog.dismiss();
                                                    finish();
                                                }else {
                                                    Toast.makeText( LoginActivity.this,reslut2[1],Toast.LENGTH_SHORT ).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        } );
                                    }else {
                                        runOnUiThread( new Runnable( ) {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Toast.makeText( LoginActivity.this,"请检查网络连接",Toast.LENGTH_SHORT ).show();
                                        }
                                    } );
                                    }
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

    private void init(){
        et_username=(EditText)findViewById( R.id.et_username );
        et_password=(EditText)findViewById( R.id.et_password );
        nologin=(TextView)findViewById( R.id.no_login_tv );
    }
}
