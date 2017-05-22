package com.lxd.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/6.
 */
public class ThreadTest2 {
}

class Business{
    private boolean bool = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void main(int loop) throws InterruptedException{
        lock.lock();
        try{
            while(bool){
                condition.await();
            }
            for(int i = 0;i < 3;i++){
                System.out.println("main thread seq of " + i + ", loop of " + loop);
            }
            bool = true;
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void sub(int loop) throws Exception{
        lock.lock();
        try{
            while(!bool) {
                condition.await();//this.wait();
            }
            for(int i = 0; i < 2; i++) {
                System.out.println("sub thread seq of " + i + ", loop of " + loop);
            }
            bool = false;
            condition.signal();//this.notify();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception{
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    threadExecute(business, "sub");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        threadExecute(business, "main");

    }

    public static void threadExecute(Business business, String threadType) throws Exception {
        for(int i = 0; i < 5; i++) {
            try {
                if("main".equals(threadType)) {
                    business.main(i);
                } else {
                    business.sub(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
