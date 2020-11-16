package com.guuidea.inreading.ui.fragment

import android.content.Intent
import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.guuidea.inreading.R
import com.guuidea.inreading.UserInfoManager
import com.guuidea.inreading.ui.activity.FeedbackActivity
import com.guuidea.inreading.ui.activity.SettingActivity
import com.guuidea.inreading.ui.base.BaseFragment
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.mine_function_area.*
import kotlinx.android.synthetic.main.mine_vip_right.*

/**
 * @file      MineFragment
 * @description    个人中心
 * @author         江 杰
 * @createDate     2020/10/26 16:28
 */
class MineFragment : BaseFragment() {

    private var scene1: Scene? = null
    private var scene2: Scene? = null
    private val vipRightsRes = ArrayList<Int>()

    override fun getContentId(): Int {
        return R.layout.fragment_mine
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initTransition()
        initVipRightsRes()
    }

    private fun initVipRightsRes() {
        vipRightsRes.add(R.drawable.vip_right_1)
        vipRightsRes.add(R.drawable.vip_right_2)
    }

    override fun initWidget(savedInstanceState: Bundle?) {
        super.initWidget(savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        vip_rights.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vip_rights.adapter = object : UniversalBaseAdapter<Int>(context, vipRightsRes) {
            override fun getItemLayoutId(): Int {
                return R.layout.item_vip_right_desc
            }

            override fun bindData(holder: UniversalViewHolder, item: Int, position: Int) {
                holder.setImageRes(R.id.img_vip_right_desc, item)
            }

        }
    }

    override fun initClick() {
        super.initClick()
        //当前使用者未登录，出发登录/当前使用者已登录
        login_area.setOnClickListener {
            if (null == UserInfoManager.getCurrentInfo()) {
                //未登录
                loginIn()
            } else {
                //已登录
                reactClick()
            }
        }
        rechargeHistory.setItemClicker(View.OnClickListener { TODO("Not yet implemented") })
        rating.setItemClicker(View.OnClickListener { })
        feedback.setItemClicker(View.OnClickListener {
            startActivity(Intent(context, FeedbackActivity::class.java))
        })
        setting.setItemClicker(View.OnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        })
    }

    private fun loginIn() {

    }

    /**
     * 用户登录成功后，进行动画过度
     */
    private fun transitionForLoginSuccess() = TransitionManager.go(scene2!!, ChangeBounds())


    private fun initTransition() {
        scene1 = context?.let { Scene.getSceneForLayout(login_area, R.layout.no_login_head_info, it) }
        scene2 = context?.let { Scene.getSceneForLayout(login_area, R.layout.login_head_info, it) }
        TransitionManager.go(scene1!!)
    }

    private fun reactClick() {

    }
}