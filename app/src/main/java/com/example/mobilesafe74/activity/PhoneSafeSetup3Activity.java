package com.example.mobilesafe74.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;

public class PhoneSafeSetup3Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_safe_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup3_phonesafe);
        initUI();
    }



    /**
     * 初始化UI界面,设置点击事件
     */
    private void initUI() {
        et_safe_contact = (EditText) findViewById(R.id.et_safe_contact);
        et_safe_contact.setText(SpUtil.getString(getApplicationContext(),ConstantValue.SAFE_CONTACT,""));
        Button bt_choose_contact  = (Button) findViewById(R.id.bt_choose_contact);
        Button btn_set3_pre = (Button) findViewById(R.id.btn_set3_pre);
        Button btn_set3_next = (Button) findViewById(R.id.btn_set3_next);
        btn_set3_pre.setOnClickListener(this);
        btn_set3_next.setOnClickListener(this);
        bt_choose_contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set3_pre:
                SpUtil.putString(getApplicationContext(), ConstantValue.SAFE_CONTACT,et_safe_contact.getText().toString());
                Intent toPre = new Intent(getApplicationContext(),PhoneSafeSetup2Activity.class);
                startActivity(toPre);
                break;
            case R.id.btn_set3_next:
                if(et_safe_contact.getText().toString()==""){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PhoneSafeSetup3Activity.this);
                    builder.setTitle("警告");
                    builder.setMessage("必须输入安全号码");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确认",null);
                    builder.show();
                }else {
                    SpUtil.putString(getApplicationContext(), ConstantValue.SAFE_CONTACT,et_safe_contact.getText().toString());
                    Intent toNext = new Intent(getApplicationContext(),PhoneSafeSetup4Activity.class);
                    startActivity(toNext);
                }
                break;
            case R.id.bt_choose_contact:
                Intent intent = new Intent(getApplicationContext(),ListContactActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String numble = data.getStringExtra("numble");
        et_safe_contact.setText(numble);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
