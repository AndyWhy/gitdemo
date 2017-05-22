package com.lxd.lock;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/5/3.
 */
public class BarrierTest {

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try{
                Thread.sleep(5000);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                this.cyclicBarrier.await();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }

    public static void main(String[] args) throws Exception{
        int N = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("条件已经触发."  + Thread.currentThread().getName());
            }
        });
        for(int i = 0;i < N;i++){
            new Writer(cyclicBarrier).start();
        }
        System.out.println("休眠25秒，CyclicBarrier对象重用");
        Thread.sleep(25000);
        for(int i = 0;i < N;i++){
            new Writer(cyclicBarrier).start();
        }
    }
}
