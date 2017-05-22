package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/7.
 */
public class Lesson5ConditionAwaitWithLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception{
        Thread t = new Thread(new Lesson5ConditionAwaitWithLock());
        t.start();
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        lock.lock();
        try {
            condition.signal();
            System.out.println("main thread signal..");
            condition.await();
            System.out.println("main thread await...");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        lock.lock();
        try{
            lock.lock();
            condition.signal();
            System.out.println("通知信号发送完毕");
            condition.await();
            System.out.println("接到信号通知");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        lock.lock();
        try{
            condition.signal();
            System.out.println("sub thread unlock2");
        }finally {
            lock.unlock();
        }
    }
}
