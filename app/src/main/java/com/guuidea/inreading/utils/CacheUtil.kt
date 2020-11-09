package com.guuidea.inreading.utils

import java.io.File
import java.util.*

/**
 * @file      CacheUtil
 * @description    缓存相关工具方法
 * @author         江 杰
 * @createDate     2020/11/9 15:37
 */
object CacheUtil {
    /**
     * Byte与Byte的倍数
     */
    private const val BYTE = 1

    /**
     * KB与Byte的倍数
     */
    private const val KB = 1024

    /**
     * KB与Byte的倍数
     */
    private const val MB = 1048576

    /**
     * GB与Byte的倍数
     */
    private const val GB = 1073741824

    /**
     * 计算文件大小
     */
    fun getTotalSizeOfFilesInDir(file: File): Long {
        if (file.isFile) {
            return file.length()
        }
        val children: Array<File> = file.listFiles()
        var total: Long = 0
        if (!children.isNullOrEmpty()) {
            children.forEach {
                total += getTotalSizeOfFilesInDir(it)
            }
        }
        return total
    }

    /**
     * 转换文件大小显示格式
     */
    fun byte2FitSize(byteNum: Long): String {
        when {
            byteNum < 0 -> {
                return "shouldn't be less than zero!"
            }
            byteNum < KB -> {
                return java.lang.String.format(Locale.getDefault(), "%.3fB", byteNum.toDouble())
            }
            byteNum < MB -> {
                return java.lang.String.format(Locale.getDefault(), "%.3fKB", byteNum.toDouble() / KB)
            }
            byteNum < GB -> {
                return java.lang.String.format(Locale.getDefault(), "%.3fMB", byteNum.toDouble() / MB)
            }
            else -> {
                return java.lang.String.format(Locale.getDefault(), "%.3fGB", byteNum.toDouble() / GB)
            }
        }
    }

    /**
     * 删除文件
     */
    fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
        if (filePath.isNotEmpty()) {
            val file = File(filePath)
            if (file.isDirectory) {
                val files = file.listFiles()
                files.forEach {
                    deleteFolderFile(it.absolutePath, true)
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory) {
                    file.delete()
                } else {
                    if (file.listFiles().isEmpty()) {
                        file.delete()
                    }
                }
            }
        }
    }

}
