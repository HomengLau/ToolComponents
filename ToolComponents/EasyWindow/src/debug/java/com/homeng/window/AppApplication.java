package com.homeng.window;

import android.app.Application;

import com.homeng.toast.Toaster;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyWindow
 *    time   : 2021/01/24
 *    desc   : 应用入口
 */
public final class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toaster.init(this);
    }
}