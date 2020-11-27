package com.guuidea.inreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 江 杰
 * @file JUCDemo
 * @description TODO
 * @createDate 2020/11/27 10:16
 */
public class JUCDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> test(), "A").start();
        new Thread(() -> test(), "B").start();
    }

    public static void test() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁");
        }
    }
}
