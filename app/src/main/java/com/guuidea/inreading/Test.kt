package com.guuidea.inreading

import io.reactivex.Observable


/**
 * @file      Test
 * @description    TODO
 * @author         江 杰
 * @createDate     2020/10/20 15:56
 */
class Test {

    companion object {
//        @SuppressLint("CheckResult")
//        fun hello(vararg args: String) {
//            Flowable.fromArray<Any>(args).subscribe { s: Any -> println("Hello $s!") }
//        }

//        fun hello(vararg args: String) {
//            Flowable.fromArray<Any>(args).subscribe(object : Consumer<String>{
//                override fun accept(t: String?) {
//
//                }
//            })
//        }
    }

    fun main() {
        val o: Observable<String> = Observable.fromArray("1", "2", "2")


    }
}

