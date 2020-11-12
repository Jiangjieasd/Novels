package com.guuidea.inreading.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.guuidea.inreading.R
import com.guuidea.inreading.UserInfoManager
import com.guuidea.inreading.ui.activity.FeedbackActivity
import com.guuidea.inreading.ui.activity.SettingActivity
import com.guuidea.inreading.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.mine_function_area.*

/**
 * @file      MineFragment
 * @description    个人中心
 * @author         江 杰
 * @createDate     2020/10/26 16:28
 */
class MineFragment : BaseFragment() {

    private var scene1: Scene? = null
    private var scene2: Scene? = null

    override fun getContentId(): Int {
        return R.layout.fragment_mine
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initTransition()
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
        rechargeHistory.itemClicker = View.OnClickListener { }
        rating.itemClicker = View.OnClickListener { }
        feedback.itemClicker = View.OnClickListener {
            startActivity(Intent(context, FeedbackActivity::class.java))
        }
        setting.itemClicker = View.OnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
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