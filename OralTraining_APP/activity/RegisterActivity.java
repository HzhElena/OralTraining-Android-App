package com.sunm0710.freechat.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sunm0710.freechat.NetUtil;
import com.sunm0710.freechat.R;

public class RegisterActivity extends AppCompatActivity {
    CheckBox agree;
    EditText username;
    EditText emailadd;
    EditText password;
    EditText pwd_rpt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void back(View view)
    {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    public void showUserAgreement(View view)
    {
        // TODO:add User Agreement showing process
    }

    public void showPrivacyPolicy(View view)
    {
        // TODO:add Privacy Policy showing process
    }

    public void doRegister(View view)
    {
        agree = (CheckBox) findViewById(R.id.reg_agree_files);
        if(!agree.isChecked()) {
            Toast.makeText(getApplicationContext(),
                    R.string.error_agree_unchecked,
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        username = (EditText) findViewById(R.id.reg_username);
        emailadd = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_pwd);
        pwd_rpt = (EditText) findViewById(R.id.reg_pwd_rpt);
        final String u = username.getText().toString();
        final String e = emailadd.getText().toString();
        final String p = password.getText().toString();
        final String r = pwd_rpt.getText().toString();
        if(!p.equals(r))
        {
            Toast.makeText(getApplicationContext(),
                    R.string.error_unequal_password, Toast.LENGTH_SHORT).show();
            pwd_rpt.setText("");
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String state = NetUtil.RegisterGET(u,e,p);
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, state, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            });
            thread.start();
        }


    }
    public int regWithInfo(String username,String email,String pwd,String pwd_rpt)
    {
        if(pwd.equals(pwd_rpt)) return 0;
        else return 1;
    }

}
