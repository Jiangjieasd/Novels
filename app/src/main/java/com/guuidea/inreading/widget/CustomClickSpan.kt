package com.guuidea.inreading.widget

import android.content.Intent
import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

/**
 * @file      CustomClickSapn
 * @description     String str = "想要设置成不同颜色或者有点击事件的字符串"
                    SpannableString   span = new SpannableString(str);
                    ClickableSpan clickSpan = new CustomClickSpan(str, this);
                    span.setSpan(clickSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    //在这里通过ForegroundColorSpan来给部分字体设置颜色。可以设置成功
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.XXX)), 0, str.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    mText.setText("正常部分的字符串");
                    mText.append(span);
                    mText.setMovementMethod(LinkMovementMethod.getInstance());
 * @author         江 杰
 * @createDate     2020/11/3 9:44
 */

class CustomClickSpan : ClickableSpan() {

    private lateinit var target: Class<*>

    override fun onClick(widget: View) {

        if (widget is TextView) {
            //将TextView系统设置颜色置空
            widget.highlightColor = Color.TRANSPARENT
        }
        widget.context.startActivity(Intent(widget.context, target))
    }

    override fun updateDrawState(ds: TextPaint) {
        //关闭下划线
        ds.isUnderlineText = false

    }

}