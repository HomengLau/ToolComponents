package com.homeng.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.homeng.bar.style.LightBarStyle
import com.homeng.toast.Toaster

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/TitleBar
 * time   : 2018/08/17
 * desc   : TitleBar 使用案例
 */
class TitleBarActivity : AppCompatActivity() {
    companion object {
        fun start(ctx: Context) {
            val intent = Intent(ctx, TitleBarActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_bar)

        val titleBar = findViewById<com.homeng.bar.TitleBar>(R.id.tb_main_bar_click)
        titleBar.setOnTitleBarListener(object : com.homeng.bar.OnTitleBarListener {
            override fun onLeftClick(titleBar: com.homeng.bar.TitleBar) {
                Toaster.show("左项 View 被点击")
            }

            override fun onTitleClick(titleBar: com.homeng.bar.TitleBar) {
                Toaster.show("中间 View 被点击")
            }

            override fun onRightClick(titleBar: com.homeng.bar.TitleBar) {
                Toaster.show("右项 View 被点击")
            }
        })
    }

    fun initTitleBar() {

        // 初始化 TitleBar 默认样式
        com.homeng.bar.TitleBar.setDefaultStyle(object : LightBarStyle() {
            override fun newTitleView(context: Context): TextView {
                return AppCompatTextView(context)
            }

            override fun newLeftView(context: Context): TextView {
                return AppCompatTextView(context)
            }

            override fun newRightView(context: Context): TextView {
                return AppCompatTextView(context)
            }
        })
    }
}