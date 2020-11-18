package com.guuidea.inreading.ui.record

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.BookPurchaseList
import com.guuidea.inreading.model.bean.BookRecord
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_novels.*

/**
 * @file      NovelsRecordFragment
 * @description    购买数据记录列表
 * @author         江 杰
 * @createDate     2020/10/28 17:29
 */
class NovelsRecordFragment : BaseFragment() {

    private val data: ArrayList<BookRecord> = ArrayList()
    private var pageNumber: Int = 1
    private lateinit var recordAdapter: UniversalBaseAdapter<BookRecord>

    companion object {
        fun newInstance(): NovelsRecordFragment {
            return NovelsRecordFragment()
        }

        private const val PAGE_SIZE = 10
    }

    override fun getContentId(): Int {
        return R.layout.fragment_novels
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setupAdapter()
        refresh.setOnRefreshListener {
            pageNumber = 1
            refresh.setEnableLoadMore(true)
            loadData(pageNumber)
        }
        refresh.setOnLoadMoreListener {
            pageNumber++
            loadData(pageNumber)
        }
    }

    private fun setupAdapter() {
        recordAdapter = object : UniversalBaseAdapter<BookRecord>(context, data) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_consumption_layout
            }

            override fun bindData(holder: UniversalViewHolder, item: BookRecord, position: Int) {
                holder.setText(R.id.tv_title, item.bookName)
                holder.setText(R.id.tv_time_value, item.createTime)
                holder.setText(R.id.tv_order_id_value, item.id.toString())
                holder.setText(R.id.tv_money_value, "$ ${item.price}")
            }
        }
        rv_records.adapter = recordAdapter
        rv_records.layoutManager = LinearLayoutManager(context)
    }

    override fun processLogic() {
        super.processLogic()
        loadData(pageNumber)
    }

    private fun loadData(pageNumber: Int) {
        val loadRecordDisposable = RemoteRepository.getInstance().purchaseList(pageNumber, PAGE_SIZE)
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
                    recordAdapter.notifyDataSetChanged()
                }, { t -> LogUtils.e(t) })
        addDisposable(loadRecordDisposable)
    }
}