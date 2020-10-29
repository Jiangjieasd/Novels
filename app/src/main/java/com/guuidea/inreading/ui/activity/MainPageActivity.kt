package com.guuidea.inreading.ui.activity

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.ui.fragment.*

class MainPageActivity : BaseActivity() {

    /**
     * 书架
     */
    var bookShelf: Fragment = BookShelfFragment()

    /**
     * 发现
     */
    var discover: Fragment = DiscoverFragment()

    /**
     * 搜索
     */
    var mine: Fragment = MineFragment()

    private val fragments = arrayOf(bookShelf, discover, mine)
    private var currentIndex = 1

    override fun getContentId(): Int {
        return R.layout.activity_main_page
    }

    override fun initWidget() {
        super.initWidget()
        val nav = findViewById<BottomNavigationView>(R.id.nav)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_content, discover)
                .show(discover)
                .commit()
        nav.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.book_shelf -> {
                    if (currentIndex != 0) {
                        switchFragment(currentIndex, 0)
                        currentIndex = 0
                    }
                    true
                }
                R.id.discover -> {
                    if (currentIndex != 1) {
                        switchFragment(currentIndex, 1)
                        currentIndex = 1
                    }
                    true
                }
                R.id.search -> {
                    if (currentIndex != 2) {
                        switchFragment(currentIndex, 2)
                        currentIndex = 2
                    }
                    true
                }

                else -> false
            }

        }
    }

    private fun switchFragment(preIndex: Int, newIndex: Int): Unit {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(fragments[preIndex])
        if (!fragments[newIndex].isAdded) {
            transaction.add(R.id.fragment_content, fragments[newIndex])
        }
        transaction.show(fragments[newIndex]).commitAllowingStateLoss()
    }

}