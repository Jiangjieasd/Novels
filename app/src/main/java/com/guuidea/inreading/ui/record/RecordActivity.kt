package com.guuidea.inreading.ui.record

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.widget.CustomActionbar

/**
 * @file      RecordActivity
 * @description    会员记录与书籍记录页面
 * @author         江 杰
 * @createDate     2020/10/28 17:14
 */
class RecordActivity : BaseActivity() {

    private val actionBar: CustomActionbar by lazy {
        findViewById<CustomActionbar>(R.id.action_bar)
    }
    private val tabs: TabLayout by lazy {
        findViewById<TabLayout>(R.id.tabs)
    }
    private val vgFragments: ViewPager by lazy {
        findViewById<ViewPager>(R.id.vg_fragments)
    }
    private val fragments = ArrayList<BaseFragment>()

    override fun getContentId(): Int {
        return R.layout.activity_record
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        productFragments()
        vgFragments.adapter = TabAdapter(supportFragmentManager, fragments, arrayOf("VIP", "Novels"))
        tabs.setupWithViewPager(vgFragments)
        for (index in 0 until 2) {
            tabs.getTabAt(index)?.text = vgFragments.adapter?.getPageTitle(index)
        }

        actionBar.title="Expenses record"
        actionBar.imgClicker= View.OnClickListener {
            finish()
        }

    }

    private fun productFragments() {
        fragments.add(VipRecordFragment.newInstance())
        fragments.add(NovelsRecordFragment.newInstance())
    }

    inner class TabAdapter constructor(private val fragmentManager: FragmentManager,
                                       private val fragments: ArrayList<BaseFragment>,
                                       private val titles: Array<String>)
        : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        init {
            require(fragments.size == titles.size)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }

}