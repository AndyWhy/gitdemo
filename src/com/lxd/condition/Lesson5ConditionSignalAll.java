package com.lxd.condition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/7.
 */
public class Lesson5ConditionSignalAll implements Runnable{
    public static Lock lock = new ReentrantLock();
    public static Condition con = lock.newCondition();
    public static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) {
        for (int i = 0;i < 10;i++){
            new Thread(new Lesson5ConditionSignalAll()).start();
        }
        try{
            System.out.println("线程停顿5秒钟");
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        lock.lock();
        try{
            con.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        try{
            latch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("线程结束");
    }
    @Override
    public void run() {
        lock.lock();
        try{
            System.out.println("thread "+ Thread.currentThread().getId() + " 开始等待");
            con.await();
            System.out.println("thread "+ Thread.currentThread().getId() + " 等待完毕");
            latch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
