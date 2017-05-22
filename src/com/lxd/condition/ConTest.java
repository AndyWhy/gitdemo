package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/6.
 */
public class ConTest {
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception {
        ConTest test = new ConTest();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
        consumer.start();
        Thread.sleep(1000);
        producer.start();
    }

    class Consumer extends Thread{
        @Override
        public void run() {
            consume();
        }

        private void consume(){
            lock.lock();
            try{
                System.out.println("我在等一个新信号"+this.currentThread().getName());
                condition.await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("拿到一个信号"+this.currentThread().getName());
                lock.unlock();
            }
        }
    }

    class Producer extends Thread{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            lock.lock();
            try {
                System.out.println("我拿到锁"+this.currentThread().getName());
                condition.signalAll();
                System.out.println("我发出了一个信号："+this.currentThread().getName());
            } finally{
                lock.unlock();
            }
        }
    }
}
