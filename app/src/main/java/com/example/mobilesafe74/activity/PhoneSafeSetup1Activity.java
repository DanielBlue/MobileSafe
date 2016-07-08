package com.example.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobilesafe74.R;

/**
 * Created by 毛琦 on 2016/7/7.
 */
public class PhoneSafeSetup1Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup1_phonesafe);
        Button btn_set1_next = (Button) findViewById(R.id.btn_set1_next);
        btn_set1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PhoneSafeSetup2Activity.class);
                startActivity(intent);
            }
        });
    }
}
