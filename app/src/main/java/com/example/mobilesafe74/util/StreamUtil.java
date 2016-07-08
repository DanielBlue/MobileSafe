package com.example.mobilesafe74.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 这是一个流对象转成字符串的工具类
 *
 * Created by 毛琦 on 2016/7/4.
 */
public class StreamUtil {
    /**
     * 将InputStream转换成字符串的方法，返回null则异常
     * @param in 一个InputStream对象
     * @return  转成的String对象
     */
    public static String streamToString(InputStream in) {
        StringBuilder response;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            response = new StringBuilder();
            String line =null;
            while((line = reader.readLine())!=null){
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
