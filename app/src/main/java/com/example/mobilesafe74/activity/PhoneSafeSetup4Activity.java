package com.example.mobilesafe74.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.view.SettingItemView;

public class PhoneSafeSetup4Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup4_phonesafe);
        initUI();
        Button btn_set4_pre = (Button) findViewById(R.id.btn_set4_pre);
        Button btn_set4_next = (Button) findViewById(R.id.btn_set4_next);
        btn_set4_pre.setOnClickListener(this);
        btn_set4_next.setOnClickListener(this);
    }

    private void initUI() {
        final SettingItemView siv_setup4 = (SettingItemView) findViewById(R.id.siv_setup4);
        final boolean setup4_phonesafe = SpUtil.getBoolean(this, ConstantValue.SETUP4_PHONESAFE,false);
        siv_setup4.setCheck(setup4_phonesafe);
        siv_setup4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = siv_setup4.isCheck();
                siv_setup4.setCheck(!isCheck);
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.SETUP4_PHONESAFE,!isCheck);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set4_pre:
                Intent toPre = new Intent(getApplicationContext(), PhoneSafeSetup3Activity.class);
                startActivity(toPre);
                break;
            case R.id.btn_set4_next:
                Intent toNext = new Intent(getApplicationContext(), PhoneSafeActivity.class);
                startActivity(toNext);
                break;
        }
    }
}
