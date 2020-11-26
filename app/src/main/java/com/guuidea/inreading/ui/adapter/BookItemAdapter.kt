package com.guuidea.inreading.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.RecommendBook
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder

/**
 * @file      BookItemAdapter
 * @description    书籍排行单item适配器
 * @author         江 杰
 * @createDate     2020/11/12 13:49
 */
class BookItemAdapter(val context: Context, val datas: ArrayList<RecommendBook>) :
        UniversalBaseAdapter<RecommendBook>(context, datas) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_book
    }

    override fun bindData(holder: UniversalViewHolder, item: RecommendBook, position: Int) {
        holder.setText(R.id.tv_book_name, item.name)
        holder.setText(R.id.tvClassify, item.tagName)
        holder.setText(R.id.tv_book_desc, item.brief)
        Glide.with(context).load(item.bookIconUrl)
                .into(holder.getView(R.id.img_book_cover))
        holder.setText(R.id.tv_book_rank, position.toString())
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
