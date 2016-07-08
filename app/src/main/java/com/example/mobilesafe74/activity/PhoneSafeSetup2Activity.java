package com.example.mobilesafe74.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.view.SettingItemView;

public class PhoneSafeSetup2Activity extends AppCompatActivity implements View.OnClickListener{

    private SettingItemView siv_setup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup2_phonesafe);
        initUI();
        Button btn_set2_pre = (Button) findViewById(R.id.btn_set2_pre);
        Button btn_set2_next = (Button) findViewById(R.id.btn_set2_next);
        btn_set2_pre.setOnClickListener(this);
        btn_set2_next.setOnClickListener(this);
    }

    /**
     * 初始化sim卡是否绑定的显示及设置其点击事件
     */
    private void initUI() {
        siv_setup2 = (SettingItemView) findViewById(R.id.siv_setup2);
        final boolean setup2_phonesafe = SpUtil.getBoolean(this, ConstantValue.SETUP2_PHONESAFE,false);
        siv_setup2.setCheck(setup2_phonesafe);
        siv_setup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = siv_setup2.isCheck();
                siv_setup2.setCheck(!isCheck);
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.SETUP2_PHONESAFE,!isCheck);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set2_pre:
                Intent toPre = new Intent(getApplicationContext(),PhoneSafeSetup1Activity.class);
                startActivity(toPre);
                break;
            case R.id.btn_set2_next:
                if(!siv_setup2.isCheck()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PhoneSafeSetup2Activity.this);
                    builder.setTitle("警告");
                    builder.setMessage("必须绑定sim卡");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认",null);
                    builder.show();
                }else {
                    Intent toNext = new Intent(getApplicationContext(), PhoneSafeSetup3Activity.class);
                    startActivity(toNext);
                }
                break;
        }
    }
}
