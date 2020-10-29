package com.guuidea.inreading.ui.login

import android.app.Activity
import android.content.Intent
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.FacebookBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.PhoneBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.TwitterBuilder
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.base.BaseActivity
import java.util.*


/**
 * @file      Login
 * @description    登录界面
 * @author         江 杰
 * @createDate     2020/10/22 11:31
 */
class Login : BaseActivity() {


    override fun getContentId(): Int {
        return R.layout.layout_login
    }

    override fun initWidget() {
        super.initWidget()
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            createSignInIntent()
        }
    }

    private fun createSignInIntent(): Unit {
        // Choose authentication providers

        // Choose authentication providers
        val providers: List<IdpConfig> = Arrays.asList(
                EmailBuilder().build(),
                PhoneBuilder().build(),
                GoogleBuilder().build(),
                FacebookBuilder().build(),
                TwitterBuilder().build()
                )

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.

            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}