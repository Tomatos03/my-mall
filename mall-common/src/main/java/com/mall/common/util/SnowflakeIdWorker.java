package com.mall.common.util;

/**
 * Snowflake 算法生成分布式唯一ID, 基础结构:
 * 41 位的时间戳 + 5 位的数据中心 + 5 位的机器标识 + 12 位的序列号
 *
 * @author : Tomatos
 * @date : 2025/9/11
 */
public final class SnowflakeIdWorker {
    private static final int POS_SEQ = 0;   // 序号段从 0 开始
    private static final int POS_WORKER = 12;  // 机器号紧接序号
    private static final int POS_DC = 17;  // 机房号紧接机器号
    private static final int POS_TIMESTAMP = 22;  // 时间戳占最高 41 位

    private final long workerId;      // 机器ID
    private final long datacenterId;  // 数据中心ID
    private long sequence = 0L;       // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    private static final long MASK_SEQ       = ~(-1L << 12);  // 0xFFF
    private static final long MASK_WORKER    = ~(-1L << 5);   // 0x1F
    private static final long MASK_DC        = ~(-1L << 5);   // 0x1F

    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId < 0 || workerId > MASK_WORKER) {
            throw new IllegalArgumentException(String.format("Worker ID must be between 0 and %d", MASK_WORKER));
        }

        if (datacenterId < 0 || datacenterId > MASK_DC) {
            throw new IllegalArgumentException(String.format("Datacenter ID must be between 0 and %d", MASK_DC));
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MASK_SEQ; // 序列号最大值 4095
            if (sequence == 0) {
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else if (timestamp < lastTimestamp) {
            // 发生时钟回拨, 直接抛出异常
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return (timestamp << POS_TIMESTAMP)
                | (datacenterId << POS_DC)
                | (workerId << POS_WORKER)
                | (sequence << POS_SEQ);
    }
}
