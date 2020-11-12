package com.guuidea.inreading.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guuidea.inreading.R
import com.guuidea.inreading.ui.activity.AgreementAndPolicyActivity
import com.guuidea.inreading.ui.base.BaseActivity
import com.guuidea.inreading.utils.ToastUtils


/**
 * @file      Login
 * @description    登录界面(google、facebook)
 * @author         江 杰
 * @createDate     2020/10/22 11:31
 */
class Login : BaseActivity(), View.OnClickListener {


    private lateinit var mAuth: FirebaseAuth

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var callbackManager: CallbackManager

    private lateinit var btnLoginFacebook: LoginButton

    override fun getContentId(): Int {
        return R.layout.layout_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        //Google init
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = Firebase.auth

        //Facebook init
        callbackManager = CallbackManager.Factory.create()
        btnLoginFacebook = findViewById(R.id.btn_login_facebook)
        btnLoginFacebook.setReadPermissions("email", "public_profile")
        btnLoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                updateUI(null)
            }

            override fun onError(error: FacebookException?) {
                updateUI(null)
            }

        })
    }

    override fun initWidget() {
        super.initWidget()
        val desc = "If you continue,you agree the Services Agreement & Privacy Policy"
        val spannableDesc = SpannableString(desc)
        spannableDesc.setSpan(object : ClickableSpan() {

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#999999")
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                AgreementAndPolicyActivity.startAgreementOrPrivacy(this@Login, 0)
            }

        }, 31, 48, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannableDesc.setSpan(object : ClickableSpan() {

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#999999")
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                AgreementAndPolicyActivity.startAgreementOrPrivacy(this@Login, 1)
            }

        }, 52, 65, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RC_SIGN_IN == requestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                updateUI(null)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val crential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(crential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        updateUI(mAuth.currentUser)
                    } else {
                        updateUI(null)
                    }
                }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            updateUI(mAuth.currentUser)
                        } else {
                            updateUI(null)
                        }
                    }
                })
    }

    // [START signin]
    private fun signIn() = startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)

    private fun updateUI(user: FirebaseUser?) = if (null != user) {
        //登录成功后页面ui变化
        ToastUtils.show("111")
    } else {
        //登陆失败后页面ui变化
        ToastUtils.show("1331")
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.btn_login_google -> {
            signIn()
        }
        else -> {
            ToastUtils.show("111")
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}