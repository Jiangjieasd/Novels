package com.guuidea.inreading.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder

/**
 * @file      BookItemAdapter
 * @description    书籍排行单item适配器
 * @author         江 杰
 * @createDate     2020/11/12 13:49
 */
class BookItemAdapter(val context: Context, val datas: ArrayList<BookItem>) :
        UniversalBaseAdapter<BookItem>(context, datas) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_book
    }

    override fun bindData(holder: UniversalViewHolder, item: BookItem, position: Int) {
        holder.setText(R.id.tv_book_name, item.bookName)
        holder.setText(R.id.tvClassify, item.bookClassify)
        holder.setText(R.id.tv_book_desc, item.bookDesc)
        Glide.with(context).load(item.coverLink)
                .into(holder.getView<ImageView>(R.id.img_book_cover))
        holder.setText(R.id.tv_book_rank, item.bookRank.toString())
    }

}

/**
 * 单本书籍model
 */
data class BookItem(
        val bookName: String,
        val coverLink: String,
        val bookAuthor: String,
        val bookClassify: String,
        val bookDesc: String,
        val bookRank: Int
)
