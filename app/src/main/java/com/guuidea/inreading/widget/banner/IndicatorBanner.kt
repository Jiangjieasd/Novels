package com.guuidea.inreading.widget.banner

import android.content.Context
import android.content.res.TypedArray
import android.media.Image
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.guuidea.inreading.R
import com.guuidea.inreading.utils.LogUtils
import com.guuidea.inreading.utils.ScreenUtils
import com.guuidea.inreading.widget.page.PageLoader

/**
 * @file      IndicatorBanner
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/11/3 14:32
 */
class IndicatorBanner : FrameLayout {

    private lateinit var vg: ViewPager

    /**
     * 指示器容器
     */
    private lateinit var indicators: LinearLayout

    /**
     * 单指示器显示图片资源
     */
    private var indicatorImg: Int = 0

    /**
     * 单指示器显示图片资源
     */
    private var indicatorSelectedImg: Int = 0

    private var adapter: IndicatorAdapter? = null

    private val indicatorsList: ArrayList<ImageView> = ArrayList()

    private var onPageChange: OnPageChange? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        initAttrs(attrs)
        initView()
    }

    /**
     * 自定义属性解析
     */
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.IndicatorBanner)
        indicatorImg =
                typedArray.getResourceId(R.styleable.IndicatorBanner_indicator_img, 0)
        indicatorSelectedImg =
                typedArray.getResourceId(R.styleable.IndicatorBanner_indicator_selected_img, 0)
        typedArray.recycle()
    }

    /***
     * 布局引入
     */
    private fun initView() {
        val rootView: View =
                LayoutInflater.from(context).inflate(R.layout.view_indicator_banner,
                        null)
        vg = rootView.findViewById(R.id.vg)
        indicators = rootView.findViewById(R.id.indicators)
        addView(rootView)
    }

    private fun bindEvent() {
        vg.adapter = adapter
        vg.currentItem = 0
        vg.offscreenPageLimit = 3
        vg.pageMargin = 40
        productIndicator(vg.adapter?.count!!)
        vg.setPageTransformer(false, ScalePageTransform())
        vg.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                LogUtils.d("$state")
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                LogUtils.d("$position")
            }

            override fun onPageSelected(position: Int) {
                //切换指示器列表中指定index位置的显示
                indicatorsList.forEach {
                    it.setImageResource(indicatorImg)
                }
                indicatorsList[position].setImageResource(indicatorSelectedImg)
                onPageChange?.onPageChange(position)

            }

        })
    }

    fun setAdapter(adapter: IndicatorAdapter) {
        this.adapter = adapter
        bindEvent()
    }

    fun setOnPageChange(onPageChange: OnPageChange) {
        this.onPageChange = onPageChange
    }

    private fun productIndicator(count: Int) {
        for (index in 0 until count) {
            val indicator: ImageView = ImageView(context)
            indicator.setImageResource(indicatorImg)
            indicatorsList.add(indicator)
            indicators.addView(indicator)
        }
    }

    fun haveScrollToLast(): Boolean {
        return vg.currentItem == vg.adapter?.count
    }

    interface OnPageChange {
        fun onPageChange(position: Int)
    }

    abstract class IndicatorAdapter : PagerAdapter() {

        abstract fun initPage(container: ViewGroup, position: Int): View

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View = initPage(container, position)
            container.addView(view)
            return view
        }
    }

}