package com.example.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.util.ToastUtil;

/**
 * Created by 毛琦 on 2016/7/4.
 */
public class HomeActicity extends Activity {
    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrableId;
    private String mobile_safe_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initData();
    }

    /**
     * 初始化GridView的数据
     */
    private void initData() {
        mTitleStr = new String[]{"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
        mDrableId = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

        MyAdapter myAdapter = new MyAdapter();
        gv_home.setAdapter(myAdapter);
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        showDialog();
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 根据是否有存储的密码选择弹出设置密码的对话框还是输入密码的对话框
     */
    private void showDialog() {
        mobile_safe_psd = SpUtil.getString(getApplicationContext(), "mobile_safe_psd", "");
        //判断是否设置了密码
        if (TextUtils.isEmpty(mobile_safe_psd)) {
            //设置密码
            setPassword();
        } else {
            //输入密码
            inputPassword();
        }
    }

    /**
     * 弹出输入密码的对话框
     */
    private void inputPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.input_password_dialog, null);
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.show();
        final EditText et_input_password_from_user = (EditText) view.findViewById(R.id.et_input_password_from_user);
        Button bt_confirm_from_user = (Button) view.findViewById(R.id.bt_confirm_from_user);
        Button bt_cancel_from_user = (Button) view.findViewById(R.id.bt_cancel_from_user);
        bt_confirm_from_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et_input_password_from_user.getText().toString();
                //拿到用户输入的密码并比对是否相等
                if(mobile_safe_psd.equals(str)){
                    enterPhoneSafeSetting();
                    dialog.dismiss();
                }else{
                    ToastUtil.show(getApplicationContext(),"密码错误");
                }
            }
        });
        bt_cancel_from_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 进入手机防盗界面
     */
    private void enterPhoneSafeSetting() {
        Intent intent = new Intent(HomeActicity.this,PhoneSafeActivity.class);
        startActivity(intent);
    }

    /**
     * 弹出设置密码的对话框
     */
    private void setPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        //绑定设置密码的视图
        View view = View.inflate(this, R.layout.set_password_dialog, null);
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.show();
        final EditText et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        final EditText et_confirm_password = (EditText) view.findViewById(R.id.et_confirm_password);
        Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_password_str = et_new_password.getText().toString();
                String confirm_password_str = et_confirm_password.getText().toString();
                if(!TextUtils.isEmpty(new_password_str)&&!TextUtils.isEmpty(confirm_password_str)){
                    if (new_password_str.equals(confirm_password_str)) {
                        SpUtil.putString(getApplicationContext(), "mobile_safe_psd", new_password_str);
                        dialog.dismiss();
                        enterPhoneSafeSetting();
                    } else {
                        ToastUtil.show(getApplicationContext(), "两次输入密码不一致");
                    }
                }else {
                    ToastUtil.show(getApplicationContext(), "密码不能为空");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 载入GridView布局
     */
    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //条目的总数
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int i) {
            //根据索引拿到的对象
            return mTitleStr[i];
        }

        @Override
        public long getItemId(int i) {
            //返回索引id
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            //设置每个条目所对应的布局
            View view = LayoutInflater.from(HomeActicity.this).inflate(R.layout.gridview_item, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_title.setText(mTitleStr[i]);
            iv_icon.setBackgroundResource(mDrableId[i]);
            return view;
        }
    }


}
