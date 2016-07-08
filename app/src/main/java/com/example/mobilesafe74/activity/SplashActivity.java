package com.example.mobilesafe74.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe74.R;
import com.example.mobilesafe74.util.ConstantValue;
import com.example.mobilesafe74.util.SpUtil;
import com.example.mobilesafe74.util.StreamUtil;
import com.example.mobilesafe74.util.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    private static final String tag = "MainActivity";
    private static final int NOTIFY_UPDATE = 100;
    private static final int NEXT_ACTIVITY = 101;
    private static final int URI_EXCETION = 102;
    private static final int IO_EXCEPTION = 103;
    private static final int JSON_EXCEPTION = 104;
    String mVersionDes;
    String mDownloadUrl;
    private TextView tv_version_name;
    private int mLocalVersionCode;
    RelativeLayout rl_root;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NOTIFY_UPDATE:
                    showUpdateDiolog();
                    break;
                case NEXT_ACTIVITY:
                    enterHome();
                    break;
                case URI_EXCETION:
                    ToastUtil.show(getApplicationContext(),"Url解析异常");
                    enterHome();
                    break;
                case IO_EXCEPTION:
                    ToastUtil.show(getApplicationContext(),"读取异常");
                    enterHome();
                    break;
                case JSON_EXCEPTION:
                    ToastUtil.show(getApplicationContext(),"Json解析异常");
                    enterHome();
                    break;
            }
        }
    };

    /**
     * 弹出更新提示框
     */
    private void showUpdateDiolog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("更新提醒");
        adb.setMessage(mVersionDes);
        adb.setPositiveButton("确认更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadApk();
            }
        });
        adb.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterHome();
            }
        });
        adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                enterHome();
            }
        });
        adb.show();
    }

    private void downloadApk() {
        //如果有sd卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //设置下载的文件保存路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    File.separator+"mobilesafe.apk";
            //使用xUtils框架
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //将下载的文件绑定在file
                    File file = responseInfo.result;
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                }
            });
        }
    }

    /**
     * 更新的apk安装
     * @param file  新版本的apk
     */
    private void installApk(File file) {
        //跳转到系统安装程序的Activity，Intent隐式启动
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivityForResult(intent,0);
    }

    /**
     * 如果用户取消安装，则跳转到HomeActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跳转到程序的主页面
     */
    private void enterHome() {
        Intent intent = new Intent(this,HomeActicity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
        initData();
        initAnimation();
    }

    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        rl_root.startAnimation(alphaAnimation);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //将版本名称设置到UI上
        tv_version_name.setText("版本名称:"+getVersionName());
        //得到版本名称本地版本名称
        mLocalVersionCode = getLocalVersionCode();
        if(SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE,false)) {
            checkVersion();
        }else{
            mHandler.sendEmptyMessageDelayed(NEXT_ACTIVITY,4000);
        }
    }

    /**
     * 检查本地应用版本与服务器版本是否一致
     */
    private void checkVersion() {
        new Thread(new Runnable() {
            Message msg = new Message();
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8080/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestMethod("GET");
                    if(connection.getResponseCode() ==200){
                        InputStream in = connection.getInputStream();
                        String json = StreamUtil.streamToString(in);
                        JSONObject jsonobject = new JSONObject(json);
                        String versionName = jsonobject.getString("versionName");
                        String versionCode = jsonobject.getString("versionCode");
                        mVersionDes = jsonobject.getString("versionDes");
                        mDownloadUrl = jsonobject.getString("downloadUrl");
                        Log.i(tag,versionCode);
                        Log.i(tag,versionName);
                        Log.i(tag,mVersionDes);
                        Log.i(tag,mDownloadUrl);
                        //如果本地版本号小于服务器版本号,则提醒更新.
                        if(mLocalVersionCode<Integer.parseInt(versionCode)){
                            msg.what = NOTIFY_UPDATE;
                        }else{
                            msg.what = NEXT_ACTIVITY;
                        }
                        long endTime = System.currentTimeMillis();
                        if((endTime-startTime)<4000) {
                            //如果splashActivity运行时间小于4秒，则等待到4秒
                            Thread.sleep(4000-(endTime-startTime));
                        }
                    }
                } catch (MalformedURLException e){
                    msg.what = URI_EXCETION;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = IO_EXCEPTION;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = JSON_EXCEPTION;
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    /**
     * 得到本地版本号
     * @return 如果返回0则异常
     */
    private int getLocalVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(this.getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到版本名称
     * @return 返回版本名称字符串，如果返回null则异常
     *
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(this.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化UI方法
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_text);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }
}
