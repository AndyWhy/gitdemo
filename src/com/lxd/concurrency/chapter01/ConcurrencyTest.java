package com.lxd.concurrency.chapter01;

/**
 * Created by lxd on 2017/5/11.
 */
public class ConcurrencyTest {
    private static final long count = 1000000000l;

    private  static void serial(){
        long start = System.currentTimeMillis();
        int  a = 0;
        for(long i = 0;i < count;i++){
            a += 5;
        }
        int b = 0;
        for (long i = 0;i < count;i++){
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial : " + time + "ms,b=" + b + ",a=" + a);
    }

    private static void concurrentcy() throws Exception{
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for(long i = 0;i < count;i++){
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0;i < count;i++){
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency : " + time + "ms,b=" + b );
    }

    public static void main(String[] args) {
        try {
            concurrentcy();
            serial();
        }catch (Exception  e){
            e.printStackTrace();
        }
    }


}
