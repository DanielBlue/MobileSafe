package com.example.mobilesafe74.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.view.SettingItemView;

public class PhoneSafeSetup2Activity extends AppCompatActivity implements View.OnClickListener{


    private SettingItemView siv_setup2;
    private String sim_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup2_phonesafe);
        initUI();
    }

    /**
     * 初始化sim卡是否绑定的显示及设置其点击事件
     */
    private void initUI() {
        siv_setup2 = (SettingItemView) findViewById(R.id.siv_setup2);
        //获取ConstantValue.SIM_NUM节点的sim卡数据
        sim_num = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUM, "");
        if (TextUtils.isEmpty(sim_num)) {
            //显示没有保存
            siv_setup2.setCheck(false);
        }else {
            //显示已保存
            siv_setup2.setCheck(true);
        }
        siv_setup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(sim_num)){
                    siv_setup2.setCheck(!siv_setup2.isCheck());
                    TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    sim_num = manager.getSimSerialNumber();
                    SpUtil.putString(getApplicationContext(), ConstantValue.SIM_NUM, sim_num);
                }else {
                    siv_setup2.setCheck(!siv_setup2.isCheck());
                    SpUtil.remove(getApplicationContext(), ConstantValue.SIM_NUM);
                }
            }
        });
        Button btn_set2_pre = (Button) findViewById(R.id.btn_set2_pre);
        Button btn_set2_next = (Button) findViewById(R.id.btn_set2_next);
        btn_set2_pre.setOnClickListener(this);
        btn_set2_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set2_pre:
                Intent toPre = new Intent(getApplicationContext(),PhoneSafeSetup1Activity.class);
                startActivity(toPre);
                break;
            case R.id.btn_set2_next:
                if(TextUtils.isEmpty(sim_num)){
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
