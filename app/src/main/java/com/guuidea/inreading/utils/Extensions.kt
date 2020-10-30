package com.guuidea.inreading.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.guuidea.inreading.R

/**
 * @file      Extensions
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/30 16:10
 */

fun ImageView.load(ctx: Context, url: String) {
    Glide.with(ctx)
            .load(url)
            .into(this)
}

/**********************************String系列******************************************/
fun String.toast(ctx: Context) {
    ToastUtils.show(this)
}

fun String.v(tag: String) {
    Log.v(tag, this)
}

fun String.d(tag: String) {
    Log.d(tag, this)
}

fun String.i(tag: String) {
    Log.i(tag, this)
}

fun String.e(tag: String) {
    Log.e(tag, this)
}
