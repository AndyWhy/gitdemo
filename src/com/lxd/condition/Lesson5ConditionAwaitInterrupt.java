package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/6.
 */
public class Lesson5ConditionAwaitInterrupt implements  Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(new Lesson5ConditionAwaitInterrupt());
        t1.start();
        Thread.sleep(5000);
        System.out.println("开始中断线程");
        t1.interrupt();

    }

    @Override
    public void run() {
        lock.lock();
        try{
            while(true){
                System.out.println("开始等待signal的通知");
                //condition.await();
                condition.awaitUninterruptibly();
                System.out.println("中断后执行代码.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
