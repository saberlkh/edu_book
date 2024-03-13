package com.edu.book.infrastructure.util.snowflake;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class SnowflakeGenerator implements IdWorker, GeneratorConnector {
    /**
     * 支持的最大机器数
     */
    public static final int MAX_WORKER_NUM = 1024;

    private boolean isWorking = false;
    /**
     * 雪花算法
     */
    private Snowflake snowflake;
    /**
     * 机器注册服务
     */
    private IdWorkerRegister register;

    public SnowflakeGenerator(IdWorkerRegister register) {
        this.register = register;
    }

    /**
     * 添加监听并初始化连接
     */
    @PostConstruct
    public synchronized void init() {
        // 添加监听, 监听成功后会自动进行注册
        try {
            register.init(this);
        } catch (Exception e) {
            throw new RegException("雪花算法初始化失败", e);
        }
    }

    /**
     * 初始化连接
     */
    @Override
    public void register() {

        try {
            long workerId = register.register();
            log.info("wokerId 注册成功 {}", workerId);
            snowflake = Snowflake.create(workerId);
            isWorking = true;
        } catch (Throwable e) {
            log.error("zk节点注册失败", e);
            snowflake = createRandomSnowFlake();
            log.info("wokerId 临时注册成功 " + snowflake.getWorkerId());
        }
    }

    /**
     * 在session lost的情况下为了能暂时能用需要随机创建一个临时的id以免冲突
     */
    @Override
    public void lost() {
        snowflake = createRandomSnowFlake();
        log.info("wokerId 临时注册成功 " + snowflake.getWorkerId());
    }

    @Override
    public BigInteger nextId() {
        if (isWorking) {
            return snowflake.nextId();
        } else {
            //防止因为注册zk慢而导致暂时没法生成id的问题
            snowflake = createRandomSnowFlake();
            log.info("wokerId 临时注册成功 " + snowflake.getWorkerId());
            isWorking = true;
            return snowflake.nextId();
        }
    }

    @Override
    public String nextStringId() {
        return String.valueOf(snowflake.nextId());
    }

    @Override
    public long nextLongId() {
        return snowflake.nextId()
                .longValue();
    }

    @Override
    public long workerId() {
        return snowflake.getWorkerId();
    }

    private Snowflake createRandomSnowFlake() {
        long randomWorkerId = ThreadLocalRandom.current()
                .nextInt(MAX_WORKER_NUM);
        return Snowflake.create(randomWorkerId);
    }

}
