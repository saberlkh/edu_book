package com.edu.book.infrastructure.util.snowflake;

public interface GeneratorConnector  {


    /**
     * 注册获取服务的workerid, 根据sessionId去获取, 因此有只要在一个session里面就不会重复
     */
    void register() ;


    /**
     * session断开, 目前策略是随机生成workerId, 以免服务出现异常
     */
    void lost();


}