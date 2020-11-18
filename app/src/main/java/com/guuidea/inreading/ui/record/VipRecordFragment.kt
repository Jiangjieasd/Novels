package com.guuidea.inreading.ui.record

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.VIPRecord
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.RxUtils
import kotlinx.android.synthetic.main.fragment_novels.refresh
import kotlinx.android.synthetic.main.vip_purchase_record.*

/**
 * @file      VipRecordFragment
 * @description    VIP购买记录
 * @author         江 杰
 * @createDate     2020/10/28 17:29
 */
class VipRecordFragment : BaseFragment() {

    private val data: ArrayList<VIPRecord> = ArrayList()
    private var pageNumber: Int = 1
    private lateinit var recordAdapter: UniversalBaseAdapter<VIPRecord>

    companion object {
        fun newInstance(): VipRecordFragment {
            return VipRecordFragment()
        }

        private const val PAGE_SIZE = 10
    }

    override fun getContentId(): Int {
        return R.layout.vip_purchase_record
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

    override fun processLogic() {
        super.processLogic()
        loadData(pageNumber)
    }

    private fun setupAdapter() {
        recordAdapter = object : UniversalBaseAdapter<VIPRecord>(context, data) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_consumption_layout
            }

            override fun bindData(holder: UniversalViewHolder, item: VIPRecord, position: Int) {
                holder.setText(R.id.tv_title, item.vipType.toString())
                holder.setText(R.id.tv_time_value, item.createTime)
                holder.setText(R.id.tv_order_id_value, item.id.toString())
                holder.setText(R.id.tv_money_value, "$ ${item.price}")
                holder.getView<TextView>(R.id.tv_read_now).visibility = View.GONE
            }

        }
        rvRecords.adapter = recordAdapter
        rvRecords.layoutManager = LinearLayoutManager(context)
    }

    private fun loadData(pageNumber: Int) {
        val loadRecordDisposable = RemoteRepository.getInstance().purchaseVIPList(pageNumber, PAGE_SIZE)
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