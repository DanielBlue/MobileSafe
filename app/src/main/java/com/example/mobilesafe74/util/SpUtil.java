package com.example.mobilesafe74.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 毛琦 on 2016/7/6.
 */
public class SpUtil {
    public static SharedPreferences sp ;

    /**
     * 写入一个boolean类型的变量至sp中
     * @param context   上下文
     * @param key   储存节点名称
     * @param value 储存的值
     */
    public static void putBoolean(Context context,String key,boolean value){
        if(sp ==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 从sp中读取boolean值
     * @param context   上下文
     * @param key   储存节点名称
     * @param defValue  读取不到时返回的默认结果
     * @return  读取到的值或者默认值
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        if(sp ==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }

    /**
     * 写入一个String类型的变量至sp中
     * @param context   上下文
     * @param key   储存节点名称
     * @param value 储存的值
     */
    public static void putString(Context context,String key,String value){
        if(sp ==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    /**
     * 从sp中读取String值
     * @param context   上下文
     * @param key   储存节点名称
     * @param defValue  读取不到时返回的默认结果
     * @return  读取到的值或者默认值
     */
    public static String getString(Context context,String key,String defValue){
        if(sp ==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

    /**
     * 从sp中删除节点
     * @param context   上下文
     * @param key   要删除的节点名称
     */
    public static void remove(Context context, String key) {
        if(sp ==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
}
