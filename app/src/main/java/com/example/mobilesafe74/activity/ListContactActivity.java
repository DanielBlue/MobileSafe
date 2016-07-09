package com.example.mobilesafe74.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe74.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListContactActivity extends AppCompatActivity {
    private static final String tag = "ListContactActivity";
    private ArrayList<HashMap<String,String>> list = new ArrayList<>();
    private ListView list_contacts;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //进行ui更改
            mAdapter = new MyAdapter();
            list_contacts.setAdapter(mAdapter);
        }
    };
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        initUI();
        initData();
    }

    /**
     * 获取系统联系人的数据
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                //查raw_contacts表的contact_id列获取联系人的数量和id
                Cursor raw_contacts = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"},null,null,null);
                //遍历，拿到每个id
                while(raw_contacts.moveToNext()){
                    String id = raw_contacts.getString(0);
                    Log.i(tag,"id = "+id);
                    /*再查data表的data1列和mimetype列上每个id所对应的值
                    * data1中存储的有联系人号码和姓名
                    * mimetype值为"vnd.android.cursor.item/phone_v2"对应的就是联系人的姓名
                    * mimetype值为"vnd.android.cursor.item/name"对应的就是联系人的号码*/
                    Cursor data = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1","mimetype"},
                            "raw_contact_id == ?",
                            new String[]{id},null);
                    //创建Map集合，
                    HashMap<String,String> map = new HashMap<>();
                    while(data.moveToNext()){
                        String data1 = data.getString(0);
                        String mimetype_id = data.getString(1);
                        if(mimetype_id.equals("vnd.android.cursor.item/phone_v2")){
                            //如果mimetype类型为"vnd.android.cursor.item/phone_v2"，则data1将"numble"作为key值存储到Map中
                            if(!TextUtils.isEmpty(data1)){
                                map.put("numble",data1);
                            }
                        }else if(mimetype_id.equals("vnd.android.cursor.item/name")){
                            if(!TextUtils.isEmpty(data1)){
                                map.put("name",data1);
                            }
                        }
                        Log.i(tag,"data1 = "+data1);
                        Log.i(tag,"mimetype_id = "+mimetype_id);
                    }
                    data.close();
                    //将Map添加到List中，每一个Map有两个键值对，分别是对应姓名和电话号码
                    list.add(map);
                }
                raw_contacts.close();
                //发送空消息，调用handler的handleMessage()方法，更改UI
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    /**
     * 初始化listview，并设置点击事件
     */
    private void initUI() {
        list_contacts = (ListView) findViewById(R.id.list_contacts);
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mAdapter!=null){
                    HashMap<String,String> hashMap= mAdapter.getItem(i);
                    String numble = hashMap.get("numble");
                    Intent intent = new Intent();
                    intent.putExtra("numble",numble);
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }

    /**
     * listview的过滤器
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public HashMap<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View list_contact_item = View.inflate(getApplicationContext(),R.layout.list_contact_item,null);
            TextView tv_contact_name = (TextView) list_contact_item.findViewById(R.id.tv_contact_name);
            TextView tv_contact_num = (TextView) list_contact_item.findViewById(R.id.tv_contact_num);
            tv_contact_name.setText(list.get(i).get("name"));
            tv_contact_num.setText(list.get(i).get("numble"));
            return list_contact_item;
        }
    }
}
