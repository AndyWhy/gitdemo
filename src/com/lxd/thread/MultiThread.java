package com.lxd.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by lxd on 2017/5/13.
 */
public class MultiThread {

    public static void main(String[] args) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] infos  = bean.dumpAllThreads(false,false);
        for (ThreadInfo info : infos){
            System.out.println(info.getThreadId() + "\t" + info.getThreadName());
        }
    }
}
