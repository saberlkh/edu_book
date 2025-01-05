package cn.tulingxueyuan.disruptor;

import cn.tulingxueyuan.disruptor.consumer.OrderEventHandler;
import cn.tulingxueyuan.disruptor.event.OrderEvent;
import cn.tulingxueyuan.disruptor.event.OrderEventFactory;
import cn.tulingxueyuan.disruptor.producer.OrderEventProducer;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;


import java.util.concurrent.Executors;

/**
 * @author Fox
 */
public class DisruptorDemo2 {

    public static void main(String[] args) throws Exception {

        //创建disruptor
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                new OrderEventFactory(),
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI, //多生产者
                new YieldingWaitStrategy()  //等待策略
        );
        
        //设置消费者用于处理RingBuffer的事件
        disruptor.handleEventsWith(new OrderEventHandler());
        //设置多消费者,消息会被重复消费
        //disruptor.handleEventsWith(new OrderEventHandler(),new OrderEventHandler());
        //设置多消费者,消费者要实现WorkHandler接口，一条消息只会被一个消费者消费
        //disruptor.handleEventsWithWorkerPool(new OrderEventHandler(), new OrderEventHandler());

        //启动disruptor
        disruptor.start();

        //创建ringbuffer容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        new Thread(()->{
            //创建生产者
            OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
            // 发送消息
            for(int i=0;i<100;i++){
                eventProducer.onData(i,"Fox"+i);
            }
        },"producer1").start();

        new Thread(()->{
            //创建生产者
            OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
            // 发送消息
            for(int i=0;i<100;i++){
                eventProducer.onData(i,"monkey"+i);
            }
        },"producer2").start();


        //disruptor.shutdown();

    }
}
