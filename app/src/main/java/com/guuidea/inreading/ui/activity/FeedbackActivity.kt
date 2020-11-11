package com.guuidea.inreading.ui.activity

import android.view.View
import android.widget.TextView
import com.guuidea.inreading.R
import com.guuidea.inreading.model.bean.BaseResponseBean
import com.guuidea.inreading.model.remote.RemoteRepository
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.utils.RegexUtil
import com.guuidea.inreading.utils.RxUtils
import com.guuidea.inreading.utils.ToastUtils
import com.guuidea.inreading.widget.LinesEditText
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * @file      FeedbackActivity
 * @description    反馈页面
 * @author         江 杰
 * @createDate     2020/11/10 15:24
 */

class FeedbackActivity : BaseActivity(), View.OnClickListener {

    private val emailEd by lazy {
        findViewById<LinesEditText>(R.id.email)
    }
    private val descEd by lazy {
        findViewById<LinesEditText>(R.id.desc)
    }
    private val submit by lazy {
        findViewById<TextView>(R.id.submit)
    }

    override fun getContentId(): Int {
        return R.layout.activity_feedback
    }

    override fun initClick() {
        super.initClick()
        submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.submit -> {
            if (checkDesc(descEd.getContentText()) && checkEmail(emailEd.getContentText())) {
                submit()
            } else {
                ToastUtils.show("Please input validate info")
            }
        }
        else -> Unit
    }

    private fun submit() {
        val feedback = RemoteRepository.getInstance().feedback(descEd.getContentText())
                .compose(RxUtils::toSimpleSingle)
                .subscribe(Consumer<BaseResponseBean> { ToastUtils.show("Success") })
        addDisposable(feedback)
    }

    private fun checkDesc(desc: String?): Boolean = desc.isNullOrBlank()

    private fun checkEmail(email: String?): Boolean = email.isNullOrBlank() && RegexUtil.checkEmail(email!!)

}