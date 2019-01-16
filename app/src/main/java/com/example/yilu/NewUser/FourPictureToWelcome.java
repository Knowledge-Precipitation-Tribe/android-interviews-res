package com.example.yilu.NewUser;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yilu.MainActivities.MainActivity;
import com.example.yilu.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

public class FourPictureToWelcome extends AppCompatActivity {
    Button start;
    FourPictureClickListener listener;
    RollPagerView mRollViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_picture_to_welcome);
        iniInitialize();
        iniClick();//绑定监听事件

        mRollViewPager = (RollPagerView) findViewById(R.id.four_picture_to_welcome);
        mRollViewPager.setPlayDelay(1000000);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setAdapter(new TestNormalAdapter());
        mRollViewPager.setHintView(new ColorPointHintView(this, Color.RED,Color.WHITE));
    }

    public  void iniInitialize(){
        listener = new FourPictureClickListener();
        start = (Button) findViewById(R.id.start);
    }

    public void iniClick(){
        start.setOnClickListener(listener);
    }

    private class TestNormalAdapter extends StaticPagerAdapter {

        private int[] imgs = {

                R.drawable.four_picture_to_welcome_1,

                R.drawable.four_picture_to_welcome_2,

                R.drawable.four_picture_to_welcome_3,

                R.drawable.four_picture_to_welcome_4,

        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;

        }

        @Override
        public int getCount() {
            return imgs.length;
        }

    }

    public class FourPictureClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == start.getId()){
                startActivity(new Intent(FourPictureToWelcome.this, MainActivity.class));
            }
        }
    }
}
