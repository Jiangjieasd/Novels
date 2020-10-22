package com.guuidea.inreading.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.guuidea.inreading.App

/**
 * @file      SharedPreutilKt
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/20 10:50
 */
@SuppressLint("CommitPrefEdits")
class SharedPreutilKt private constructor() {

    private val sharedReadable: SharedPreferences
    private val sharedWritable: SharedPreferences.Editor

    init {
        sharedReadable = App.getContext()
                .getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS)
        sharedWritable = sharedReadable.edit()
    }

    companion object {
        private const val SHARED_NAME = "IReader_pref"
        val sInstance: SharedPreutilKt by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SharedPreutilKt()
        }
    }

    fun getString(key: String?): String? {
        return sharedReadable.getString(key, "")
    }

    fun putString(key: String?, value: String?) {
        sharedWritable.putString(key, value)
        sharedWritable.commit()
    }

    fun putInt(key: String?, value: Int) {
        sharedWritable.putInt(key, value)
        sharedWritable.commit()
    }

    fun putBoolean(key: String?, value: Boolean) {
        sharedWritable.putBoolean(key, value)
        sharedWritable.commit()
    }

    fun getInt(key: String?, def: Int): Int {
        return sharedReadable.getInt(key, def)
    }

    fun getBoolean(key: String?, def: Boolean): Boolean {
        return sharedReadable.getBoolean(key, def)
    }

}