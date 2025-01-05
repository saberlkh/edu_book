package cn.tulingxueyuan.disruptor.consumer;

import cn.tulingxueyuan.disruptor.event.OrderEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author Fox
 * 消费者
 */
public class OrderEventHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        // TODO 消费逻辑
        System.out.println("EventHandler 消费者"+ Thread.currentThread().getName()
                +"获取数据value:"+ event.getValue()+",name:"+event.getName());
    }

    @Override
    public void onEvent(OrderEvent event) throws Exception {
        // TODO 消费逻辑
        System.out.println("WorkHandler 消费者"+ Thread.currentThread().getName()
                +"获取数据value:"+ event.getValue()+",name:"+event.getName());
    }
}
