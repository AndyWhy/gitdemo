package com.lxd.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/4/28.
 */
public class SingleThreadTest {

    public static void main(String[] args){
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(new MyThread());
        pool.shutdown();
        while(true){
            if(pool.isTerminated()){
                System.out.println("I am out");
                break;
            }
        }
    }


}


class MyThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyThread:" + i);
        }
    }
}
