package com.lxd.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/3.
 */
public class ThreadJoinTest {

    class CustomThread1 extends Thread{
        public void run(){
            String tn = Thread.currentThread().getName();
            System.out.println(tn + " start.");
            try{
                for (int i = 0;i < 5;i++){
                    System.out.println(tn + " loop at " + i);
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class CustomThread extends Thread{
        CustomThread1 t1 ;
        public CustomThread(CustomThread1 t1){
            this.t1 = t1;
        }
        public void run(){
            String tname = Thread.currentThread().getName();
            System.out.println(tname + " start.");
            try{
                this.t1.join();
                System.out.println(tname + " end.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        ThreadJoinTest test = new ThreadJoinTest();
        String tname = Thread.currentThread().getName();
        System.out.println(tname  + " Start");
        CustomThread1 t1 = test.new CustomThread1();
        CustomThread  t  = test.new CustomThread(t1);
        try{
            t1.start();
            Thread.sleep(2000);
            t.start();
            //t.join();
        }catch (Exception e){
            System.out.println("Exception from main");
        }
        System.out.println(tname + " end");

    }
}