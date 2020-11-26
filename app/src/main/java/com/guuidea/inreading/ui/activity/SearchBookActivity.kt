package com.guuidea.inreading.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.RecommendBook
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import kotlinx.android.synthetic.main.activity_search_book.*

/**
 * @file      SearchBookActivity
 * @description    搜索页面
 * @author         江 杰
 * @createDate     2020/11/23 10:12
 */

class SearchBookActivity : BaseActivity() {

    private val popularTag = ArrayList<String>()
    private lateinit var mRecommendAdapter: SearchBookRecommendAdapter
    private val datas: ArrayList<RecommendBook> = ArrayList()

    override fun getContentId(): Int {
        return R.layout.activity_search_book
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        prepareInitData()
    }

    private fun prepareInitData() {
        popularTag.add("Fantasy")
        popularTag.add("WuXia")
        popularTag.add("Modern")
    }

    override fun initWidget() {
        super.initWidget()
        setupAdapter()
    }

    private fun setupAdapter() {
        rv_popular.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        rv_popular.adapter = object : UniversalBaseAdapter<String>(this, popularTag) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_popular_tag
            }

            override fun bindData(holder: UniversalViewHolder, item: String, position: Int) {
                holder.setText(R.id.tv_tag, item)
            }

        }

        mRecommendAdapter = SearchBookRecommendAdapter(this, datas, object : OnItemClickListener {
            override fun onItemClick(book: RecommendBook) {
                jumpToDetail(book)
            }
        })
        rv_recommend.layoutManager = LinearLayoutManager(this)
        rv_recommend.adapter = mRecommendAdapter
    }

    override fun initClick() {
        super.initClick()
        search_et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchBook(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                LogUtils.i(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search_iv_delete.visibility = if (s.isNullOrBlank()) View.GONE else View.VISIBLE
            }

        })
    }

    /**
     * 书籍搜索
     */
    private fun searchBook(bookTitle: String) {

    }

    override fun processLogic() {
        super.processLogic()
        loadRecommendBook()
    }

    /**
     * 点击结果item跳转方法
     */
    private fun jumpToDetail(book: RecommendBook) {
        ExtendReaderActivity.startRead(this, book.id.toString())
    }


    private fun loadRecommendBook() {
        val disposable = RemoteRepository.getInstance().fetchRecommendBookInReading()
                .compose(RxUtils::toSimpleSingle)
                .subscribe({
                    if (0 == it.code) {
                        if (!it.data.isNullOrEmpty()) {
                            datas.addAll(it.data)
                            mRecommendAdapter.notifyDataSetChanged()
                        }
                    }
                },
                        { t -> LogUtils.e(t) })

        addDisposable(disposable)
    }

    class SearchBookRecommendAdapter(private val ctx: Context,
                                     private val data: ArrayList<RecommendBook>,
                                     private val itemClick: OnItemClickListener) :
            RecyclerView.Adapter<SearchBookRecommendAdapter.SearchBookRecommendVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookRecommendVH {
            return SearchBookRecommendVH(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_recommend, parent, false))
        }

        override fun getItemCount(): Int {
            return if (data.isNullOrEmpty()) 0 else data.size
        }

        override fun onBindViewHolder(holder: SearchBookRecommendVH, position: Int) {
//            Glide.with(ctx).load(data[position].bookIconUrl)
//                    .into(holder.imgRankCover)
            holder.tvRankBookName.text = data[position].name
            holder.tvRankViewed.text = "${data[position].views} viewed"
            holder.tvRankClass.text = when (data[position].tagId) {
                1 -> {
                    "Fantasy"
                }
                2 -> {
                    "XianXia"
                }
                4 -> {
                    "WuXia"
                }
                6 -> {
                    "Science"
                }
                7 -> {
                    "Modern"
                }
                8 -> {
                    "Mystery"
                }
                9 -> {
                    "Romance"
                }
                else -> "Fantasy"
            }
            holder.itemView.setOnClickListener {
                itemClick.onItemClick(data[position])
            }
        }

        inner class SearchBookRecommendVH(private val root: View) : RecyclerView.ViewHolder(root) {
            val imgRankCover = root.findViewById<ImageView>(R.id.img_rank_cover)
            val tvRankBookName = root.findViewById<TextView>(R.id.tv_rank_bookName)
            val tvRankClass = root.findViewById<TextView>(R.id.tv_rank_class)
            val tvRankViewed = root.findViewById<TextView>(R.id.tv_rank_viewed)
        }
    }

    class SearchBookResultAdapter(private val data: ArrayList<RecommendBook>) :
            RecyclerView.Adapter<SearchBookResultAdapter.SearchBookResultVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookResultVH {
            return SearchBookResultVH(LayoutInflater.from(parent.context).inflate(
                    R.layout.item_book,
                    parent,
                    false
            ))
        }

        override fun getItemCount(): Int {
            return if (!data.isNullOrEmpty()) data.size else 0
        }

        override fun onBindViewHolder(holder: SearchBookResultVH, position: Int) {
            holder.tvClassify.visibility = View.GONE
        }

        inner class SearchBookResultVH(private val root: View) : RecyclerView.ViewHolder(root) {
            val img_book_cover: ImageView = root.findViewById(R.id.img_book_cover)
            val tv_book_name: TextView = root.findViewById(R.id.tv_book_name)
            val tvClassify: TextView = root.findViewById(R.id.tvClassify)
            val tv_book_desc: TextView = root.findViewById(R.id.tv_book_desc)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(book: RecommendBook)
    }
}