package com.sunm0710.freechat.activity;


import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunm0710.freechat.R;
import com.sunm0710.freechat.fragment.ScenePracticeFragment;
import com.sunm0710.freechat.fragment.WordPracticeFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fragtab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        View btn_scene=getLayoutInflater().inflate(R.layout.tab_scene_btn,null);
        View btn_word=getLayoutInflater().inflate(R.layout.tab_word_btn,null);
        View btn_chat=getLayoutInflater().inflate(R.layout.tab_chat_btn,null);
        View btn_info=getLayoutInflater().inflate(R.layout.tab_info_btn,null);

        fragtab=(FragmentTabHost)findViewById(android.R.id.tabhost);
        fragtab.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        fragtab.addTab(fragtab.newTabSpec("word").setIndicator(btn_word), WordPracticeFragment.class, null);
        fragtab.addTab(fragtab.newTabSpec("scene").setIndicator(btn_scene), ScenePracticeFragment.class, null);
        fragtab.addTab(fragtab.newTabSpec("chat").setIndicator(btn_chat), ScenePracticeFragment.class, null);
        fragtab.addTab(fragtab.newTabSpec("info").setIndicator(btn_info), ScenePracticeFragment.class, null);
        fragtab.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        myToolbar.setNavigationIcon(R.drawable.ic_action_goback);

        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"back",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();
            }
        });

    }

    public void changeTitle(String title)
    {
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(title);
    }

    public void changeFragment(int flag)
    {
        switch (flag)
        {
            case 0://scenepractice

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    public void getback(MenuItem item) {
        //TODO
    }
}
