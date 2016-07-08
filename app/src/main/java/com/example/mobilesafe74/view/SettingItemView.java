package com.example.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe74.R;

/**
 * Created by 毛琦 on 2016/7/6.
 */
public class SettingItemView extends RelativeLayout {
    /**
     * 自定义组合控件的命名空间
     */
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.mobilesafe74";
    private String title;
    private String des_on;
    private String des_off;
    private CheckBox cb_update;
    private TextView tv_des;
    TextView tv_setting_title;
    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将子布局加载进来
        View.inflate(context,R.layout.setting_item,this);
        /*等同于
        * View view = View.inflate(context,R.layout.setting_item,null);
          this.addView(view);
        */
        tv_setting_title = (TextView) findViewById(R.id.tv_setting_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_update = (CheckBox) findViewById(R.id.cb_update);
        initAttrs(attrs);
        tv_setting_title.setText(title);
    }

    /**
     * 获取属性并设置
     * @param attrs 指定的属性值的集合
     */
    private void initAttrs(AttributeSet attrs) {
        title = attrs.getAttributeValue(NAMESPACE,"title");
        des_on = attrs.getAttributeValue(NAMESPACE,"des_on");
        des_off = attrs.getAttributeValue(NAMESPACE,"des_off");
    }

    /**
     * cb_update 是否被选中
     * @return  true 被选中
     *           false 未被选中
     */
    public boolean isCheck(){
        return cb_update.isChecked();
    }

    /**
     * 根据cb_update是否被选中，来更改textview
     * @param isCheck   cb_update是否被选中的boolean值
     */
    public void setCheck(boolean isCheck){
        cb_update.setChecked(isCheck);
        if(isCheck){
            tv_des.setText(des_on);
        }else {
            tv_des.setText(des_off);
        }
    }


}
