package com.jie.jvm;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Demo GC log
 */
public class GCLogAnalysis {
    private static Random random = new Random();


    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMills = TimeUnit.SECONDS.toMillis(1L);
        long endMills = startMillis + timeoutMills;

        LongAdder counter = new LongAdder();
        System.out.println("execute ...");

        // cache some objects to The Old
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];

        while (System.currentTimeMillis() < endMills) {
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }

        System.out.println("execute completely, object generate " + counter.longValue() + " times");
    }

    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;

        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }

        return result;
    }


}
