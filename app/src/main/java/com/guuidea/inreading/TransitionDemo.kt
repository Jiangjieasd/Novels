package com.guuidea.inreading

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.guuidea.inreading.utils.ToastUtils
import com.guuidea.inreading.widget.banner.IndicatorBanner
import com.guuidea.inreading.widget.banner.IndicatorBanner.IndicatorAdapter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * @file      TransitionDemo
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/30 9:49
 */

class TransitionDemo : AppCompatActivity() {

    private var scene1: Scene? = null
    private var scene2: Scene? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_demo)
//        initTransition()
//        findViewById<CircleImageView>(R.id.user_profile).setOnClickListener {
//            TransitionManager.go(scene2!!, ChangeBounds())
//        }

        findViewById<IndicatorBanner>(R.id.banner).setAdapter(object : IndicatorAdapter() {
            override fun initPage(container: ViewGroup, position: Int): View {
                val tc = TextView(container.context)
                tc.text = "Position:$position"
                tc.setTextColor(Color.WHITE)
                tc.textSize = 10F
                tc.gravity = Gravity.CENTER
                tc.setBackgroundResource(R.drawable.button_black_5_background)
                return tc
            }

            override fun getCount(): Int {
                return 3
            }

        })
        findViewById<IndicatorBanner>(R.id.banner).setOnPageChange(object : IndicatorBanner.OnPageChange {
            override fun onPageChange(position: Int) {
                ToastUtils.show("Position:$position")
            }

        })


    }

    private fun initTransition() {
        val sceneRoot = findViewById<ViewGroup>(R.id.login_area)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.no_login_head_info, this)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.login_head_info, this)
        TransitionManager.go(scene1!!)
    }

    private fun test() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                TODO("Not yet implemented")
            }

        }).subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                TODO("Not yet implemented")
            }

            override fun onNext(t: Int) {
                TODO("Not yet implemented")
            }

            override fun onComplete() {
                TODO("Not yet implemented")
            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}