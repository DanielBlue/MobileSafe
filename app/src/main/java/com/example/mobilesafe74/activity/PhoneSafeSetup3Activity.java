package com.example.mobilesafe74.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilesafe74.R;

public class PhoneSafeSetup3Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup3_phonesafe);
        Button btn_set3_pre = (Button) findViewById(R.id.btn_set3_pre);
        Button btn_set3_next = (Button) findViewById(R.id.btn_set3_next);
        btn_set3_pre.setOnClickListener(this);
        btn_set3_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set3_pre:
                Intent toPre = new Intent(getApplicationContext(),PhoneSafeSetup2Activity.class);
                startActivity(toPre);
                break;
            case R.id.btn_set3_next:
                Intent toNext = new Intent(getApplicationContext(),PhoneSafeSetup4Activity.class);
                startActivity(toNext);
                break;
        }
    }
}
