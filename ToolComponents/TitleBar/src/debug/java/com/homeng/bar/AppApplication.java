package com.homeng.bar;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.homeng.bar.style.LightBarStyle;
import com.homeng.toast.Toaster;

public final class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 Toast
        Toaster.init(this);

        // 初始化 TitleBar 默认样式
        TitleBar.setDefaultStyle(new LightBarStyle() {

            @Override
            public TextView newTitleView(Context context) {
                return new AppCompatTextView(context);
            }

            @Override
            public TextView newLeftView(Context context) {
                return new AppCompatTextView(context);
            }

            @Override
            public TextView newRightView(Context context) {
                return new AppCompatTextView(context);
            }
        });
    }
}