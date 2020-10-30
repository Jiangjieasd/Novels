package com.guuidea.inreading

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.guuidea.inreading.widget.CircleImageView


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
        setContentView(R.layout.fragment_mine)
        initTransition()
        findViewById<CircleImageView>(R.id.user_profile).setOnClickListener {
            TransitionManager.go(scene2!!, ChangeBounds())
        }
    }

    private fun initTransition() {
        val sceneRoot = findViewById<ViewGroup>(R.id.login_area)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.no_login_head_info, this)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.login_head_info, this)
        TransitionManager.go(scene1!!)
    }
}