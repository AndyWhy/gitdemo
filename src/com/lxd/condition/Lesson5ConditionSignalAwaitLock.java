package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/7.
 */
public class Lesson5ConditionSignalAwaitLock {
    public static Lock lock = new ReentrantLock();
    public static Condition c1 = lock.newCondition();

    public static void main(String [] args) throws InterruptedException{
        for(int i=0;i<3;i++){
            new Thread(new ThreadAwait()).start();
        }
        Thread.currentThread().sleep(1000L);
        new Thread(new ThreadSignal()).start();
        Thread.currentThread().sleep(1000L);
        for(int i=0;i<3;i++){
            new Thread(new ThreadLock()).start();
        }
    }

}

class ThreadAwait implements Runnable{
    @Override
    public void run() {
        Lock lock = Lesson5ConditionSignalAwaitLock.lock;
        lock.lock();
        try{
            System.out.println("ThreadAwait 's thread "+ Thread.currentThread().getId() + " 开始等待");
            Lesson5ConditionSignalAwaitLock.c1.await();
            System.out.println("ThreadAwait 's thread "+ Thread.currentThread().getId() + " 等待完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class ThreadSignal implements Runnable{
    @Override
    public void run() {
        Lock lock = Lesson5ConditionSignalAwaitLock.lock;
        lock.lock();
        try{
            System.out.println("ThreadSignal 's thread "+ Thread.currentThread().getId() + " 拿到了锁");
            Thread.currentThread().sleep(2000L);
            new Thread(new ThreadLock()).start();
            System.out.println("ThreadSignal 's thread "+ Thread.currentThread().getId() + " 开始通知");
            Lesson5ConditionSignalAwaitLock.c1.signal();
            Lesson5ConditionSignalAwaitLock.c1.signal();
            Lesson5ConditionSignalAwaitLock.c1.signal();
            System.out.println("ThreadSignal 's thread "+ Thread.currentThread().getId() + " 通知完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class ThreadLock implements Runnable{
    @Override
    public void run() {
        Lock lock = Lesson5ConditionSignalAwaitLock.lock;
        System.out.println("ThreadLock 's thread "+ Thread.currentThread().getId() + " 进入线程开始拿锁");
        lock.lock();
        try{
            System.out.println("ThreadLock 's thread "+ Thread.currentThread().getId() + " 拿到了锁");
        }finally {
            lock.unlock();
        }
    }
}