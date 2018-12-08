package com.sunm0710.freechat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sunm0710.freechat.R;

public class FindPasswordActivity extends AppCompatActivity {

    EditText username;
    EditText emailadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
    }

    public void back(View view)
    {
        Intent intent = new Intent(FindPasswordActivity.this,LoginActivity.class);
        startActivity(intent);
        FindPasswordActivity.this.finish();
    }

    public void sendPwd2Email(View view)
    {
        username = (EditText) findViewById(R.id.fdpwd_username);
        emailadd = (EditText) findViewById(R.id.fdpwd_email);
        String u = username.getText().toString();
        String e = emailadd.getText().toString();
        if(checkValid(u,e))
        {
            sendPwd(u,e);
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.error_username_email_mismatch,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public boolean checkValid(String username,String emailadd)
    {
        //TODO:check if username@String and emailadd @String match
        return true;
    }

    public void sendPwd(String username,String emailadd)
    {
        //TODO:send username@String 's password to emailadd@String
    }


}
