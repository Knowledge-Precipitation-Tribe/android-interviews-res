package com.example.yilu.MainActivities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yilu.ActivityStart;
import com.example.yilu.NewUser.FourPictureToWelcome;
import com.example.yilu.R;

public class MainActivity extends AppCompatActivity {
    MianClickListener listener;
    ImageButton qrCode/*二维码*/,search;
    Button primePage,goods,homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment,fragment);
        transaction.commit();
    }
    //实例化组件
    public  void iniInitialize(){
        listener = new MianClickListener();
        qrCode = (ImageButton) findViewById(R.id.main_qr_code);
        primePage = (Button) findViewById(R.id.main_prime_page);
        goods = (Button) findViewById(R.id.gone);
        homePage = (Button) findViewById(R.id.main_home_page);
        search = (ImageButton) findViewById(R.id.main_search);
    }
    //绑定点击事件
    public void iniClick(){
        qrCode.setOnClickListener(listener);
        primePage.setOnClickListener(listener);
        goods.setOnClickListener(listener);
        homePage.setOnClickListener(listener);
        search.setOnClickListener(listener);
    }
    //实现监听类
    public class MianClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == qrCode.getId()){
                startActivity(new Intent(MainActivity.this, FourPictureToWelcome.class));
            }else if (v.getId() == primePage.getId()){
                replaceFragment();
            }else if (v.getId() == goods.getId()){
                replaceFragment();
            }else if (v.getId() == homePage.getId()){
                replaceFragment();
            }else if (v.getId() == search.getId()){
                startActivity(new Intent(MainActivity.this, FourPictureToWelcome.class));
            }
        }
    }
}
