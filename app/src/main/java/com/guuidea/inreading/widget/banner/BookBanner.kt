package com.guuidea.inreading.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter

/**
 * TODO: document your custom view class.
 */
class BookBanner<T> : FrameLayout {

    private lateinit var rvBanner: ViewPager
    private lateinit var leftIndicator: ImageView
    private lateinit var rightIndicator: ImageView
    private lateinit var adapter: PagerAdapter
    private val leftClickListener: OnClickListener = OnClickListener {
        rvBanner.currentItem = rvBanner.currentItem - 1
    }
    private val rightClickListener: OnClickListener = OnClickListener {
        rvBanner.currentItem = rvBanner.currentItem + 1
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.BookBanner, defStyle, 0)
        a.recycle()
        initView()
    }


    private fun initView() {
        val itemView = LayoutInflater.from(context).inflate(R.layout.book_banner, null)
        rvBanner = itemView.findViewById(R.id.rv_banner)
        leftIndicator = itemView.findViewById(R.id.img_left)
        rightIndicator = itemView.findViewById(R.id.img_right)
        addView(itemView)
        bindOperate()
    }

    fun setAdapter(a: PagerAdapter) {
        this.adapter = a
        bindData()
    }

    private fun bindData() {
        rvBanner.setPageTransformer(false, ScalePageTransform())
        rvBanner.adapter = adapter
        rvBanner.currentItem = 1
    }

    private fun bindOperate() {

        /**
         * 左右跳转控制
         */
        leftIndicator.setOnClickListener(leftClickListener)
        rightIndicator.setOnClickListener(rightClickListener)

    }

    companion object {
        private const val TAG: String = "BookBanner"
    }

}