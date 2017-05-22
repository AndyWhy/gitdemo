package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/7.
 */
public class Lesson5ConditonsRelation {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition c1 = lock.newCondition();
    public static Condition c2 = lock.newCondition();

    public static void main(String[] args) {
        for (int i = 0;i < 3;i++){
            new Thread(new UseCondition1()).start();
            new Thread(new UseConditionTwo()).start();
        }
        try{
            System.out.println("主线程停顿5秒钟");
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        lock.lock();
        try{
            System.out.println("唤醒条件c1");
            Lesson5ConditonsRelation.c1.signal();
        }finally {
            lock.unlock();
        }
        try{
            System.out.println("主线程停顿5秒钟");
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }

        lock.lock();
        try{
            System.out.println("唤醒条件c2");
            Lesson5ConditonsRelation.c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

class  UseCondition1 implements Runnable{
    @Override
    public void run() {
        Lock lock = Lesson5ConditonsRelation.lock;
        lock.lock();
        try{
            System.out.println("UsedConditionOne 's thread "+ Thread.currentThread().getId() + " 开始等待");
            Lesson5ConditonsRelation.c1.await();
            System.out.println("UsedConditionOne 's thread "+ Thread.currentThread().getId() + " 等待完毕");
            Lesson5ConditonsRelation.c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class UseConditionTwo implements Runnable{
    @Override
    public void run() {
        Lock lock = Lesson5ConditonsRelation.lock;
        lock.lock();
        try{
            System.out.println("UsedConditionTwo 's thread "+ Thread.currentThread().getId() + " 开始等待");
            Lesson5ConditonsRelation.c2.await();
            System.out.println("UsedConditionTwo 's thread "+ Thread.currentThread().getId() + " 等待完毕");
            Lesson5ConditonsRelation.c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}