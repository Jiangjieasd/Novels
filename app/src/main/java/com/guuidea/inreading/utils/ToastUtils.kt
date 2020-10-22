package com.guuidea.inreading.utils;

import android.widget.Toast;

import com.guuidea.inreading.App;

/**
 * Created by guuidea on 17-5-11.
 */

class ToastUtils private constructor(){

    companion object{
        fun show(msg:String) {
            Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }
}
