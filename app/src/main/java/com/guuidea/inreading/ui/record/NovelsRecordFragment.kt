package com.guuidea.inreading.ui.record

import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseFragment

/**
 * @file      NovelsRecordFragment
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/28 17:29
 */
class NovelsRecordFragment : BaseFragment() {

    companion object {
        fun newInstance(): NovelsRecordFragment {
            return NovelsRecordFragment()
        }
    }

    override fun getContentId(): Int {
        return R.layout.fragment_novels
    }
}