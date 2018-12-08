package com.sunm0710.freechat.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sunm0710.freechat.NetUtil;
import com.sunm0710.freechat.R;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View view)
    {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    public void findPassword(View view)
    {
        Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    public void login(View view)
    {
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        final String u = username.getText().toString();
        final String p = password.getText().toString();

        String state = "";

        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String state = NetUtil.LoginGET(u,p);
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, state, Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                }
            });
        thread.start();
            //state = state0;
        //Toast.makeText(LoginActivity.this, state, Toast.LENGTH_SHORT).show();

    }

    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(LoginActivity.this,R.string.doubleclk_exit,Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
