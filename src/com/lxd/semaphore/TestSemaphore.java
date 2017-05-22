package com.lxd.semaphore;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/5/5.
 */
public class TestSemaphore {

    public static void main(String[] args) throws Exception {
        //runUseBarrier();
        runUseCountDownLatch();
    }

    private static void runUseBarrier() throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("条件满足，解除等待");
            }
        });
        final Semaphore semaphore = new Semaphore(5);
        for (int index = 0;index < 20;index++){
            final int no = index;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try{
                        semaphore.acquire();
                        System.out.println("Accessing : " + no);
                        Thread.sleep((long)(Math.random() * 10000));
                        semaphore.release();
                        cyclicBarrier.await();
                        System.out.println("release : " + no + " and count down " + cyclicBarrier.getParties() );
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
        System.out.println("it's over");
    }

    private static void runUseCountDownLatch() throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(20);
        final Semaphore semaphore = new Semaphore(5,true);
        for (int index = 0;index < 20;index++){
            final int no = index;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try{
                        semaphore.acquire();
                        System.out.println("Accessing : " + no);
                        Thread.sleep((long)(Math.random() * 10000));
                        semaphore.release();
                        latch.countDown();
                        System.out.println("release : " + no + " and count down " + latch.getCount() );
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
        latch.await();
        System.out.println("it's over");
    }
}
