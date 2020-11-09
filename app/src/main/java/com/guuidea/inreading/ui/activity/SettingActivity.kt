package com.guuidea.inreading.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.guuidea.inreading.R
import com.guuidea.inreading.SingleItem
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.utils.CacheUtil
import com.guuidea.inreading.utils.ToastUtils
import java.io.File

/**
 * @file      SettingActivity
 * @description    设置页面
 * @author         江 杰
 * @createDate     2020/11/9 15:08
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    private val version: SingleItem by lazy {
        findViewById<SingleItem>(R.id.version)
    }
    private val cache: SingleItem by lazy {
        findViewById<SingleItem>(R.id.cache)
    }
    private val btnLogOut: TextView by lazy {
        findViewById<TextView>(R.id.btn_log_out)
    }

    private val file1: File = File("/sdcard/Android/data/{pkg}/cache".replace("{pkg}", packageName))
    private val file2: File = File("/data/data/{pkg}/cache".replace("{pkg}", packageName))

    override fun getContentId(): Int {
        return R.layout.layout_setting
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        version.subContent = getAppVersion()
        cache.subContent = CacheUtil.byte2FitSize(CacheUtil.getTotalSizeOfFilesInDir(file1)
                + CacheUtil.getTotalSizeOfFilesInDir(file2))
    }

    override fun initClick() {
        super.initClick()
        version.setOnClickListener(this)
        cache.setOnClickListener(this)
        btnLogOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.version -> {
                checkVersion()
            }
            R.id.cache -> {
                clearCache()
                cache.subContent = "0 MB"
                ToastUtils.show("Cleared")
            }
            R.id.btn_log_out -> {
                logOut()
            }
            else -> return
        }
    }

    /**
     * 检查版本号
     */
    private fun checkVersion() {
        TODO("待确认是否需要实现版本检查")
    }

    private fun getAppVersion(): String = packageManager.getPackageInfo(packageName, 0).versionName

    private fun clearCache() {
        CacheUtil.deleteFolderFile(file1.absolutePath, true)
        CacheUtil.deleteFolderFile(file2.absolutePath, true)
    }

    private fun logOut() {
        TODO("待实现")
    }
}