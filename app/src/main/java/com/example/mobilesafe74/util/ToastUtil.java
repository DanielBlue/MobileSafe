package com.example.mobilesafe74.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast的的工具类
 * Created by 毛琦 on 2016/7/4.
 */
public class ToastUtil {
    /**
     * 显示Toast
     * @param context 上下文
     * @param msg   要显示的信息
     */
    public static void show(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
