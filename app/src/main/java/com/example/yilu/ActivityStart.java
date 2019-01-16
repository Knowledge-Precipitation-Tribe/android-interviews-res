package com.example.yilu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yilu.NewUser.FourPictureToWelcome;

public class ActivityStart extends AppCompatActivity {
    Button start;
    MianClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        iniInitialize();
        iniClick();//绑定监听事件
    }
    //实例化组件
    public  void iniInitialize(){
        listener = new MianClickListener();
        start = (Button) findViewById(R.id.start);
    }
    //绑定点击事件
    public void iniClick(){
        start.setOnClickListener(listener);
    }
    //实现监听类
    public class MianClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == start.getId()){
                startActivity(new Intent(ActivityStart.this,FourPictureToWelcome.class));
            }
        }
    }

}


