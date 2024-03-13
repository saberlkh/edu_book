package com.edu.book.infrastructure.util.snowflake;

public interface IdWorkerRegister {
    void init(GeneratorConnector snowflakeGenerator) throws Exception;

    /**
     * 注册workerId
     *
     * @return 注册成功的worker id
     */
    long register() throws Exception;

    /**
     * 退出注册
     */
    void logout() throws Exception;
}