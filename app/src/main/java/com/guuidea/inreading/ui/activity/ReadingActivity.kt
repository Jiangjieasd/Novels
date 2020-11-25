package com.guuidea.inreading.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.glong.reader.widget.ReaderView
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.BookChapterContent
import com.guuidea.inreading.model.bean.Chapter
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_reading.*

/**
 * @file      ReadingActivity
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/24 16:17
 */
class ReadingActivity : BaseActivity() {

    private lateinit var mBookId: String

    companion object {
        private const val BOOK_ID: String = "book_id"

        fun startReading(ctx: Context, bookId: String) {
            val intent = Intent(ctx, ReadingActivity::class.java)
            intent.putExtra(BOOK_ID, bookId)
            ctx.startActivity(intent)
        }
    }

    override fun getContentId(): Int {
        return R.layout.activity_reading
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mBookId = intent.getStringExtra(BOOK_ID)
    }

    override fun initWidget() {
        super.initWidget()
        reader_view.readerManager = ReaderView.ReaderManager()
        reader_view.adapter = object : ReaderView.Adapter<Chapter, BookChapterContent>() {
            override fun downLoad(k: Chapter?): BookChapterContent {
                //根据章节id进行下载操作
                return BookChapterContent("", 1, 1,
                        "", "", "","")
            }

            override fun obtainCacheKey(k: Chapter?): String {
                return k?.id.toString() + k?.chapterName
            }

            override fun obtainChapterName(k: Chapter?): String {
                return k?.chapterName.toString()
            }

            override fun obtainChapterContent(t: BookChapterContent?): String {
                return t?.chapterContent.toString()
            }

        }
    }

    private fun loadChapterList() {

    }
}