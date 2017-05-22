package com.lxd.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/3.
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(10);
        final ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 10; index++) {
            final int no = index + 1;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        /**
                         * 等待开始指令，业务程序跑动
                         * **/
                        start.await();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("nNo." + no + " arrived");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            pool.submit(run);
        }
        System.out.println("Game Start");
        start.countDown();
        /** 等待业务程序执行完毕，继续当前线程 **/
        end.await();
        System.out.println("Game Over");
        pool.shutdown();

    }
}