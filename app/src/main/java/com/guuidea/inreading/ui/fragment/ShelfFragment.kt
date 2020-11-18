package com.guuidea.inreading.ui.fragment

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.BookOperateDescBean
import com.guuidea.inreading.model.bean.BookShelfBean
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import kotlinx.android.synthetic.main.fragment_shelf.*

/**
 * @file      ShelfFragment
 * @description    书架
 * @author         江 杰
 * @createDate     2020/11/17 15:56
 */
class ShelfFragment : BaseFragment() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private var pageNumber = 1

    /**
     * 书架书籍列表
     */
    private val data = ArrayList<BookShelfBean>()
    private lateinit var adapter: BookShelfAdapter

    override fun getContentId(): Int {
        return R.layout.fragment_shelf
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupAdapter()
        refresh.setOnRefreshListener {
            pageNumber = 1
            refresh.setEnableLoadMore(true)
            loadBookList(pageNumber)
        }
        refresh.setOnLoadMoreListener {
            pageNumber++
            loadBookList(pageNumber)
        }
    }

    private fun setupAdapter() {
        adapter = BookShelfAdapter(
                data,
                object : OnItemClickListener {
                    override fun onItemClick(bean: BookShelfBean) {
                        onItemClick()
                    }

                    override fun onItemLongClick(bean: BookShelfBean) {
                        onItemLongClick()
                    }
                })
        rvBooks.adapter = adapter
        rvBooks.layoutManager = GridLayoutManager(context, 3)
    }

    override fun processLogic() {
        super.processLogic()
        loadBookList(pageNumber)
    }

    private fun loadBookList(pageNumber: Int) {
        val disposable = RemoteRepository.getInstance().fetchReadingList(pageNumber, PAGE_SIZE)
                .compose(RxUtils::toSimpleSingle)
                .subscribe({
                    if (0 == it.totalCount) {
                        //当前无记录
                        refresh.setEnableLoadMore(false)
                    } else {
                        if (it.totalCount < PAGE_SIZE) {
                            //当前数据量不足一页
                            refresh.setEnableLoadMore(false)
                            data.clear()
                            it.data?.let { it1 -> data.addAll(it1) }
                        } else {
                            it.data?.let { it1 -> data.addAll(it1) }
                            if (data.size >= it.totalCount) {
                                refresh.setEnableLoadMore(false)
                            } else {
                                refresh.setEnableLoadMore(true)
                            }
                        }
                    }
                    refresh.finishRefresh()
                    refresh.finishLoadMore()
                    adapter.notifyDataSetChanged()
                    tip_book_number.text = "—  ${data.size} books you are reading  —"
                },
                        { t -> LogUtils.e(t) })
        addDisposable(disposable)
    }

    private fun onItemClick() {

    }

    private fun onItemLongClick() {
        openItemDialog()
    }

    private fun openItemDialog() {
        showBookOperateDialog()
    }

    /**
     * 显示底部弹窗操作
     */
    private fun showBookOperateDialog() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setCanceledOnTouchOutside(true)
        bottomSheetDialog?.setContentView(R.layout.dialog_book_operate)
        val rvOperates = bottomSheetDialog?.delegate?.findViewById<RecyclerView>(R.id.rv_operates)!!
        rvOperates.adapter = object : UniversalBaseAdapter<BookOperateDescBean>(context, productData()) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_book_operate
            }

            override fun bindData(holder: UniversalViewHolder, item: BookOperateDescBean, position: Int) {
                holder.setText(R.id.tv_operate_desc, item.operateDesc)
                holder.setImageRes(R.id.img_operate, item.operateImgId)
                holder.itemView.setOnClickListener { v: View? ->
                    when (item.type) {
                        BookOperateDescBean.VIEW_DETAIL -> {
                            bottomSheetDialog.dismiss()
                            viewDetail()
                        }
                        BookOperateDescBean.SHARE_NOVEL -> {
                            bottomSheetDialog.dismiss()
                            shareNovel()
                        }
                        BookOperateDescBean.REMOVE_NOVEL -> {
                            bottomSheetDialog.dismiss()
                            deleteBook()
                        }
                    }
                }
            }
        }
        rvOperates.layoutManager = LinearLayoutManager(context)
        bottomSheetDialog.show()
    }

    /**
     * 创建底部弹窗显示数据集
     */
    private fun productData(): ArrayList<BookOperateDescBean> {
        val datas = java.util.ArrayList<BookOperateDescBean>()
        datas.add(BookOperateDescBean(
                "View novel details",
                R.drawable.more,
                BookOperateDescBean.VIEW_DETAIL))
        datas.add(BookOperateDescBean(
                "share the novel",
                R.drawable.more,
                BookOperateDescBean.SHARE_NOVEL))
        datas.add(BookOperateDescBean(
                "remove from books",
                R.drawable.more,
                BookOperateDescBean.REMOVE_NOVEL))
        return datas
    }

    /**
     * 删除书籍
     */
    private fun deleteBook() {

    }

    /**
     * 查看书籍详情
     */
    private fun viewDetail() {

    }

    /**
     * 分享书籍
     */
    private fun shareNovel() {

    }

    class BookShelfAdapter(private val data: ArrayList<BookShelfBean>,
                           private val itemClickListener: OnItemClickListener) :
            RecyclerView.Adapter<BookShelfAdapter.BookShelfViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookShelfViewHolder =
                BookShelfViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_coll_book, parent, false))


        override fun getItemCount(): Int = if (!data.isNullOrEmpty()) data.size + 1 else 1


        override fun onBindViewHolder(holder: BookShelfViewHolder, position: Int) {
            if (1 == itemCount) {
                //当前无书籍添加
            } else {
                if (position == itemCount - 1) {
                    holder.coolBookCover.setImageResource(R.drawable.add_img)
                } else {
                    holder.collBookTvName.text = data[position].bookEnName
                    holder.tvReadRate.text = "${(data[position].readChapter % (data[position].allChapter)).toString()}%"
                    holder.itemView.setOnClickListener {
                        itemClickListener.onItemClick(data[position])
                    }
                    holder.itemView.setOnLongClickListener {
                        itemClickListener.onItemLongClick(data[position])
                        return@setOnLongClickListener true
                    }
                }

            }

        }

        class BookShelfViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
            val collBookTvName = root.findViewById<TextView>(R.id.coll_book_tv_name)
            val coolBookCover = root.findViewById<ImageView>(R.id.coll_book_iv_cover)
            val tvReadRate = root.findViewById<TextView>(R.id.tv_read_rate)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(bean: BookShelfBean)
        fun onItemLongClick(bean: BookShelfBean)
    }
}