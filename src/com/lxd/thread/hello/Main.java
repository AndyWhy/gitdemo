package com.lxd.thread.hello;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(Runtime.getRuntime().availableProcessors());
        int COUNT_BITS = Integer.SIZE - 3;
        int RUNNING    = -1 << COUNT_BITS;
        int SHUTDOWN   =  0 << COUNT_BITS;
        int STOP       =  1 << COUNT_BITS;
        int TIDYING    =  2 << COUNT_BITS;
        int TERMINATED =  3 << COUNT_BITS;

        System.out.println(String.format("COUNT_BITS = %S",COUNT_BITS));
        System.out.println(String.format("RUNNING = %S",RUNNING));
        System.out.println(String.format("SHUTDOWN = %S",SHUTDOWN));
        System.out.println(String.format("STOP = %S",STOP));
        System.out.println(String.format("TIDYING = %S",TIDYING));
        System.out.println(String.format("TERMINATED = %S",TERMINATED));

    }
}
