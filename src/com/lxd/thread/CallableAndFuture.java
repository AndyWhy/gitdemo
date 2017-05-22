package com.lxd.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/5/3.
 */
public class CallableAndFuture {

    public static void main(String[] args){
        //use02();
        use03();
        System.out.println("over");
    }

    public static void use03(){
        ExecutorService pool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(pool);
        for(int i = 1;i < 5;i++){
            final int taskId = i;
            cs.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return taskId;
                }
            });
        }
        pool.shutdown();
        for(int i = 0;i < 5;i++){
            try{
                System.out.println(cs.take().get());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void use02(){
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<Integer> future = pool.submit(new Callable<Integer>(){
            public Integer call() throws Exception {
                //float a = 100 / 0;
                return new Random().nextInt(100);
            }
        });
        try {
            System.out.println(String.format(" function use02 sleep %s",5000));
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void use01(){
        Callable<Integer> callable = new Callable<Integer>(){
            public Integer call() throws Exception{
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try{
            Thread.sleep(5000);
            System.out.println(future.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
