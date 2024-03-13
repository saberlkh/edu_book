package com.edu.book.infrastructure.util.snowflake;

import java.math.BigInteger;

public interface IdWorker {

    /**
     * 获取ID
     * @return ID
     */
    BigInteger nextId();

    /**
     * 获取ID
     * @return ID
     */
    String nextStringId();

    long nextLongId();

    long workerId();


}