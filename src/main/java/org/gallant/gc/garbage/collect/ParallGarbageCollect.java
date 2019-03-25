package org.gallant.gc.garbage.collect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kongyong
 * @date 2019/3/24
 */
public class ParallGarbageCollect {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(":::start:::");
        // -Xmx90m -Xms90m -Xss256k -XX:NewRatio=8 -XX:SurvivorRatio=8
        // 90M的堆,80M老年代，10M新生代，8M Eden园，两个1M survivor区
        List<byte[]> list = new ArrayList<>();
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        ygc(8, 1, list);
        list = new ArrayList<>();
        fullGC(1, 50);
        fullGC(1, 50);
        fullGC(1, 50);
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
        List<byte[]> list = new ArrayList<>();
        for (int i=0;i<count;i++) {
            list.add(new byte[1024 * 1024 * size]);
        }
    }

}
