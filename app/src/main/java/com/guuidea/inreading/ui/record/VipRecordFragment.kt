package com.guuidea.inreading.ui.record

import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseFragment
import kotlin.contracts.Returns

/**
 * @file      VipRecordFragment
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/28 17:29
 */
class VipRecordFragment : BaseFragment() {

    companion object {
        fun newInstance(): VipRecordFragment {
            return VipRecordFragment()
        }
    }

    override fun getContentId(): Int {
        return R.layout.fragment_vip
    }
}