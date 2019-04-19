package org.gallant.gc.garbage.collect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kongyong
 * @date 2019/3/24
 */
public class CMSGarbageCollect {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(":::start:::");
        // -Xmx90m -Xms90m -Xss256k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC  -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:CMSInitiatingOccupancyFraction=90 -XX:+UseCMSInitiatingOccupancyOnly
        // -Xmx90m -Xms90m -Xss256k -XX:NewRatio=8 -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC  -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:CMSInitiatingOccupancyFraction=90 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=6 -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:-UseBiasedLocking
        // -Xmx90m 最大堆内存
        // -Xms90m 最小堆内存
        // -Xss256k stack_ssize
        // -XX:NewRatio=8 新老代内存比，老年代：新生代=8：1
        // -XX:SurvivorRatio=8 新生代内存比，Eden园：survivor区=8:1，由于存活区survivor有两个，所以是2/10比例
        // -XX:+UseConcMarkSweepGC 老年代使用cms收集器
        // -XX:+PrintGCApplicationStoppedTime 打印垃圾回收期间程序暂停的时间
        // -XX:+PrintGCDateStamps GC发生的时间信息
        // -XX:+PrintGCDetails 打印GC详细信息
        // -XX:CMSInitiatingOccupancyFraction=90 CMS垃圾收集器，当老年代内存使用达到90%时，触发CMS垃圾回收
        // -XX:+UseCMSInitiatingOccupancyOnly 指定HotSpot-VM总是使用-XX:CMSInitiatingOccupancyFraction的值作为old的空间使用率限制来启动CMS垃圾回收。如果没有使用-XX:+UseCMSInitiatingOccupancyOnly，那么HotSpot-VM只是利用这个值来启动第一次CMS垃圾回收，后面都是使用HotSpot-VM自动计算出来的值。
        // -XX:MaxTenuringThreshold=6 对象经历多少次Minor-GC才晋升到旧生代，默认值是15
        // -XX:+PrintSafepointStatistics 打印安全点统计的一些分析信息
        // -XX:PrintSafepointStatisticsCount=1 打印安全点统计信息count值,与上面的PrintSafepointStatistics参数同时开启使用才会打印统计信息
        // -XX:-UseBiasedLocking 关闭偏向锁
        // 90M的堆,80M老年代，10M新生代，8M Eden园，两个1M survivor区
        List<byte[]> list = new ArrayList<>();
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        list = new ArrayList<>();
        fullGC(7, 10);
        fullGC(7, 10);
        fullGC(7, 10);
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void ygc(int count, int size, List<byte[]> list){
        // 16M的Eden区必然一次ygc
        for (int i=0;i<count;i++) {
            list.add(new byte[1024 * 1024 * size]);
        }
    }

    private static void fullGC(int count, int size){
        for (int i=0;i<count;i++) {
            List<byte[]> list = new ArrayList<>();
            list.add(new byte[1024 * 1024 * size]);
        }
    }

}
