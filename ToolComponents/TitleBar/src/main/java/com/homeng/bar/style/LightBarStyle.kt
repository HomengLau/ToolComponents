package com.homeng.bar.style

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.homeng.bar.R
import com.homeng.bar.SelectorDrawable
import com.homeng.bar.TitleBarSupport

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/TitleBar
 * time   : 2020/09/19
 * desc   : 日间主题样式实现（对应布局属性：app:barStyle="light"）
 */
open class LightBarStyle : CommonBarStyle() {
    override fun getTitleBarBackground(context: Context): Drawable {
        return ColorDrawable(-0x1)
    }

    override fun getLeftTitleBackground(context: Context): Drawable {
        return SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x0C000000))
            .setPressed(ColorDrawable(0x0C000000))
            .build()
    }

    override fun getRightTitleBackground(context: Context): Drawable {
        return SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x0C000000))
            .setPressed(ColorDrawable(0x0C000000))
            .build()
    }

    override fun getBackButtonDrawable(context: Context): Drawable {
        return TitleBarSupport.getDrawable(context, R.drawable.bar_arrows_left_black)
    }

    override fun getTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0xddddde)
    }

    override fun getLeftTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0x99999a)
    }

    override fun getRightTitleColor(context: Context): ColorStateList {
        return ColorStateList.valueOf(-0x5b5b5c)
    }

    override fun getLineDrawable(context: Context): Drawable {
        return ColorDrawable(-0x131314)
    }
}