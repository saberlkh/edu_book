package cn.tulingxueyuan.container.map;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentHashMapDemo {

    private static ConcurrentHashMap<String, AtomicLong> concurrentHashMap = new ConcurrentHashMap<>();
    // 创建一个 CountDownLatch 对象用于统计线程控制
    private static CountDownLatch countDownLatch = new CountDownLatch(3);
    // 模拟文本文件中的单词
    private static String[] words = {"we", "it", "is"};

    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {
            public void run() {
                for(int i=0; i<3; i++) {
                    // 模拟从文本文件中读取到的单词
                    String word = words[new Random().nextInt(3)];
                    //System.out.println(Thread.currentThread().getName() +"读取到单词："+word);
                    // 尝试获取全局统计结果
                    AtomicLong number = concurrentHashMap.get(word);
                    // 在未获取到的情况下，进行初次统计结果设置
                    if (number == null) {
                        // 在设置时发现如果不存在则初始化
                        AtomicLong newNumber = new AtomicLong(0);
                        number = concurrentHashMap.putIfAbsent(word, newNumber);
                        if (number == null) {
                            number = newNumber;
                        }
                    }
                    // 在获取到的情况下，统计次数直接加1
                    number.incrementAndGet();

                    System.out.println(Thread.currentThread().getName() + ":" + word + " 出现" + number + " 次");
                }
                //计数器减1
                countDownLatch.countDown();
            }
        };

        new Thread(task, "线程1").start();
        new Thread(task, "线程2").start();
        new Thread(task, "线程3").start();

        try {
            //计数器为0之前等待
            countDownLatch.await();
            System.out.println(concurrentHashMap.toString());
        } catch (Exception e) {}
    }
}
