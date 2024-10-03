package com.homeng.toast

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.homeng.bar.OnTitleBarListener
import com.homeng.bar.TitleBar
import com.homeng.permissions.Permission
import com.homeng.permissions.XXPermissions
import com.homeng.toast.style.BlackToastStyle
import com.homeng.toast.style.CustomToastStyle
import com.homeng.toast.style.WhiteToastStyle
import com.homeng.window.EasyWindow

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Toaster
 * time   : 2018/09/01
 * desc   : Toaster 使用案例
 */
class ToasterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toaster)

        val titleBar = findViewById<TitleBar>(R.id.tb_main_bar)
        titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onTitleClick(titleBar: TitleBar) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(titleBar.title.toString()))
                startActivity(intent)
            }
        })
    }

    fun showToast(v: View) {
        Toaster.show(R.string.demo_show_toast_result)
    }

    fun showShortToast(v: View?) {
        Toaster.showShort(R.string.demo_show_short_toast_result)
    }

    fun showLongToast(v: View?) {
        Toaster.showLong(R.string.demo_show_long_toast_result)
    }

    fun showCrossPageToast(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_show_cross_page_toast_result)
        params.crossPageShow = true
        Toaster.show(params)
    }

    fun delayShowToast(v: View?) {
        Toaster.delayedShow(R.string.demo_show_toast_with_two_second_delay_result, 2000)
    }

    fun threadShowToast(v: View?) {
        Thread { Toaster.show(R.string.demo_show_toast_in_the_subthread_result) }.start()
    }

    fun switchToastStyleToWhite(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_white_style_result)
        params.style = WhiteToastStyle()
        Toaster.show(params)
    }

    fun switchToastStyleToBlack(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_black_style_result)
        params.style = BlackToastStyle()
        Toaster.show(params)
    }

    fun switchToastStyleToInfo(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_info_style_result)
        params.style = CustomToastStyle(R.layout.toast_info)
        Toaster.show(params)
    }

    fun switchToastStyleToWarn(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_warn_style_result)
        params.style = CustomToastStyle(R.layout.toast_warn)
        Toaster.show(params)
    }

    fun switchToastStyleToSuccess(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_success_style_result)
        params.style = CustomToastStyle(R.layout.toast_success)
        Toaster.show(params)
    }

    fun switchToastStyleToError(v: View?) {
        val params = ToastParams()
        params.text = getString(R.string.demo_switch_to_error_style_result)
        params.style = CustomToastStyle(R.layout.toast_error)
        Toaster.show(params)
    }

    fun customGlobalToastStyle(v: View?) {
        Toaster.setView(R.layout.toast_custom_view)
        Toaster.setGravity(Gravity.CENTER)
        Toaster.show(R.string.demo_custom_toast_layout_result)
    }

    fun switchToastStrategy(v: View?) {
        Toaster.setStrategy(ToastStrategy(ToastStrategy.SHOW_STRATEGY_TYPE_QUEUE))
        Toaster.show(R.string.demo_switch_to_toast_queuing_strategy_result)
        findViewById<View>(R.id.tv_main_thrice_show).visibility = View.VISIBLE
    }

    fun showThriceToast(v: View?) {
        for (i in 0..2) {
            Toaster.show(String.format(getString(R.string.demo_show_three_toast_copywriting), i + 1))
        }
    }

    fun toBackgroundShowToast(v: View) {
        Snackbar.make(window.decorView, getString(R.string.demo_show_toast_in_background_state_hint), Snackbar.LENGTH_SHORT).show()

        v.postDelayed({
            Snackbar.make(window.decorView, getString(R.string.demo_show_toast_in_background_state_snack_bar), Snackbar.LENGTH_SHORT).show()
        }, 2000)

        v.postDelayed({
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
        }, 4000)

        v.postDelayed({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (XXPermissions.isGranted(this@ToasterActivity, Permission.SYSTEM_ALERT_WINDOW)) {
                    Toaster.show(R.string.demo_show_toast_in_background_state_result_1)
                } else {
                    Toaster.show(R.string.demo_show_toast_in_background_state_result_2)
                }
            } else {
                Toaster.show(R.string.demo_show_toast_in_background_state_result_3)
            }
        }, 5000)
    }

    fun combinationEasyWindowShow(v: View?) {
        EasyWindow<EasyWindow<*>>(this)
            .setDuration(1000) // 将 Toaster 中的 View 转移给 EasyWindow 来显示
            .setContentView(Toaster.getStyle().createView(application))
            .setAnimStyle(android.R.style.Animation_Translucent)
            .setText(android.R.id.message, R.string.demo_combining_window_framework_use_result)
            .setGravity(Gravity.BOTTOM)
            .setYOffset(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics).toInt())
            .show()
    }

    companion object {
        fun start(ctx: Context) {
            val intent = Intent(ctx, ToasterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(intent)
        }
    }
}