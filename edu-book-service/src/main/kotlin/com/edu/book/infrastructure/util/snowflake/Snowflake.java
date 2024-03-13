package com.edu.book.infrastructure.util.snowflake;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class Snowflake {
    /**
     * 时间基准点(对应2016/9/6 17:31:27 的毫秒数)
     */
    private static final long EPOCH = 1473154287634L;
    /**
     * 机器id所占的位数（源设计为5位，这里取消dataCenterId，采用10位，既1024台）
     */
    private static final long WORKER_ID_BITS = 10L;
    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID最大值: 1023 (从0开始)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 时间戳向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)，12位
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 批量申请ID限制为 十万
     */
    private static final int HUNDRED_K = 100_000;
    /**
     * 服务器ID
     */
    @Getter
    private final long workerId;
    /**
     * 并发控制，毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * @param workerId 服务器ID
     */
    private Snowflake(long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            log.error("服务器ID不能大于{}或小于0", MAX_WORKER_ID);
            throw new IllegalArgumentException("服务器ID不能大于" + MAX_WORKER_ID + "或小于0");
        }
        this.workerId = workerId;
    }

    /**
     * 获取 Snowflake 实例
     *
     * @param workerId 服务器ID
     * @return Snowflake 实例
     */
    public static Snowflake create(long workerId) {
        return new Snowflake(workerId);
    }

    /**
     * 获取唯一ID
     *
     * @return 唯一ID
     */
    public synchronized BigInteger nextId() {
        long timestamp = System.currentTimeMillis();
        //如果出现毫秒内并发的情况则，使用sequence递增[0-4096]
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & SEQUENCE_MASK;
            //毫秒内并发量超过4096个
            if (this.sequence == 0) {
                //阻塞到下一毫秒
                timestamp = this.getNextMillis(this.lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            this.sequence = 0;
        }
        //判断服务器时间是否出现回退，如果有回退则抛出异常
        if (timestamp < this.lastTimestamp) {
            log.error("系统时间出现回退，上一次获取到的毫秒数是:{} 本次获取到的毫秒数是:{}", this.lastTimestamp, timestamp);
            throw new RuntimeException("系统时间出现回退，上一次获取到的毫秒数是:" + this.lastTimestamp + " 本次获取到的毫秒数是:" + timestamp);
        }
        this.lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return BigInteger.valueOf(timestamp - EPOCH << TIMESTAMP_LEFT_SHIFT | this.workerId << WORKER_ID_SHIFT | this.sequence);
    }

    /**
     * 获取指定时间点的下一毫秒
     *
     * @param lastTimestamp 指定时间点(毫秒数)
     * @return 下一毫秒数
     */
    private long getNextMillis(long lastTimestamp) {
        long tempTimestamp = System.currentTimeMillis();
        //如果获取毫秒数的过程中服务器时间被修改，则此处会一直循环到下一毫秒为止
        while (tempTimestamp <= lastTimestamp) {
            tempTimestamp = System.currentTimeMillis();
        }
        return tempTimestamp;
    }
}

