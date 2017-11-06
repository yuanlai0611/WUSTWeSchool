package com.gongyunhaoyyy.wustweschool.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gongyunhaoyyy.wustweschool.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        Button login=(Button)findViewById( R.id.login );
        final Intent intent=new Intent( LoginActivity.this,MainActivity.class );
        login.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( intent );
            }
        } );
    }
}
