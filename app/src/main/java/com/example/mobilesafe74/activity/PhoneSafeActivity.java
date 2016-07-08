package com.example.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;

/**
 * Created by 毛琦 on 2016/7/7.
 */
public class PhoneSafeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_phonesafe = SpUtil.getBoolean(this, ConstantValue.SETUP_PHONESAFE,false);
        if(setup_phonesafe){
            setContentView(R.layout.activity_phonesafe);
        }else {
            Intent intent = new Intent(getApplicationContext(),PhoneSafeSetup1Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
