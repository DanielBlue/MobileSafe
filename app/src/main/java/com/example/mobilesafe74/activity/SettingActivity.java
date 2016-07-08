package com.example.mobilesafe74.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.view.SettingItemView;

/**
 * 进入设置界面
 * Created by 毛琦 on 2016/7/6.
 */
public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initPhoneFrom();
    }

    /**
     * 设置来点归属地显示是否开启
     */
    private void initPhoneFrom() {
        final SettingItemView siv_phone_from = (SettingItemView) findViewById(R.id.siv_phone_from);
        final boolean open_phone_from = SpUtil.getBoolean(this,ConstantValue.OPEN_PHONE_FROM,false);
        siv_phone_from.setCheck(open_phone_from);
        siv_phone_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = siv_phone_from.isCheck();
                siv_phone_from.setCheck(!isCheck);
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_PHONE_FROM,!isCheck);
            }
        });
    }

    /**
     * 设置点击事件，开启或关闭自动更新
     */
    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        final boolean open_update = SpUtil.getBoolean(this,ConstantValue.OPEN_UPDATE,false);
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = siv_update.isCheck();
                siv_update.setCheck(!isCheck);
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE,!isCheck);
            }
        });
    }
}
