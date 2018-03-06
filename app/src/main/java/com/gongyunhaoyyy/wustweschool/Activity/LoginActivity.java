package com.gongyunhaoyyy.wustweschool.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gongyunhaoyyy.wustweschool.Base.BaseActivity;
import com.gongyunhaoyyy.wustweschool.util.Ksoap2;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.UI.DrawView;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private EditText et_username,et_password;
    private Button login;
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
            setContentView();
            @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor nameeditor = getSharedPreferences( "userdata", MODE_PRIVATE ).edit( );
            //透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            initViews();
            dialog=loadingDialog( "小园登陆中...",false );
            login.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    user=et_username.getText().toString();
                    pass=et_password.getText().toString();
                    userdt=user+","+pass;
                    if (user.length()!=12||pass.length()<4){
                        showToast( "输入有误" );
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
                                                    showToast( reslut2[1] );
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
                                                    showToast( xm+"，欢迎你~" );
                                                    nameeditor.putString( "getuserdata",userdt );
                                                    nameeditor.apply();
                                                    startIntent( MainActivity.class );
                                                    dialog.dismiss();
                                                    finish();
                                                }else {
                                                    showToast( R.string.nointernet );
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

        }
    }


    @Override
    public void setContentView() {
        setContentView( R.layout.activity_login );
    }

    @Override
    public void initViews() {
        et_username=(EditText)findViewById( R.id.et_username );
        et_password=(EditText)findViewById( R.id.et_password );
        login=(Button)findViewById( R.id.img_login );
        drawBackGround();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

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
}
