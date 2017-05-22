package com.lxd.lock;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/5/3.
 */
public class TestCyclicBarrier {

    private static final int THREAD_NUM = 5;

    public class WorkerThread implements Runnable{
        CyclicBarrier cyclicBarrier;
        public WorkerThread(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try{
                System.out.println("Worker's waiting");
                this.cyclicBarrier.await();
                System.out.println("ID: " + Thread.currentThread().getId() + " working");
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
        }
    }

    public static void main(String[] args) {
        TestCyclicBarrier test = new TestCyclicBarrier();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM, new Runnable() {
            @Override
            public void run() {
                System.out.println("Inside Barrier");
            }
        });
        for (int i = 0;i < THREAD_NUM;i++){
            new Thread(test.new WorkerThread(cyclicBarrier)).start();
        }
    }
}
