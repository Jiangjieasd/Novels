package com.guuidea.inreading.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.MainPageDataBean
import com.guuidea.inreading.model.bean.MainType
import com.guuidea.inreading.ui.base.BaseFragment

/**
 * @file      DiscoverFragment
 * @description    主页
 * @author         江 杰
 * @createDate     2020/10/26 15:14
 */
class DiscoverFragment : BaseFragment() {

    override fun getContentId(): Int {
        return R.layout.fragment_discover_layout
    }
}