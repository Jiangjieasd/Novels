package com.guuidea.inreading.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guuidea.inreading.BuildConfig
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.utils.ToastUtils

/**
 * @file      LoginWithFirebaseUI
 * @description    FirebaseUI登录解决方案
 * @author         江 杰
 * @createDate     2020/11/5 14:47
 */
class LoginWithFirebaseUI : BaseActivity(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun getContentId(): Int {
        TODO("Not yet implemented")
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mAuth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        updateUI(mAuth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RC_SIGN_IN == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                updateUI(mAuth.currentUser)
            } else {
                updateUI(null)
            }
        }
    }

    private fun startSignIn() {
        // Build FirebaseUI sign in intent. For documentation on this operation and all
        // possible customization see: https://github.com/firebase/firebaseui-android
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
                .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onClick(v: View?) = when (v?.id) {
        else -> {

        }
    }

    private fun updateUI(user: FirebaseUser?) = if (null != user) {
        ToastUtils.show("login success")
    } else {
        ToastUtils.show("login fail")
    }
}