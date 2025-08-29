package com.mall.constant;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/30
 */
public final class MQConst {
    /**
     * excel导出交换机
     */
    public static final String EXCEL_EXPORT_EXCHANGE = "excel_export_exchange";
    /**
     * excel导出队列
     */
    public static final String EXCEL_EXPORT_QUEUE = "excel_export_queue";
    /**
     * excel导出路由key
     */
    public static final String EXCEL_EXPORT_QUEUE_ROUTING_KEY = "excel_export.#";
    /**
     * 延迟时间 （单位：ms(毫秒)）
     */
    public static final Integer DELAY_TIME = 10000;
}
