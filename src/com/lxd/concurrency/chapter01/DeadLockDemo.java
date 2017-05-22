package com.lxd.concurrency.chapter01;

/**
 * Created by lxd on 2017/5/11.
 */
public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock(){
       Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    try{
                        Thread.currentThread().sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                synchronized (B){
                    try{
                        //Thread.currentThread().sleep(2000);
                        System.out.println("1");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(B){
                    synchronized (A){
                        try {
                            System.out.println("2");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
