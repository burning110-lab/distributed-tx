package com.qu.lele.config;

/**
 * @author: 屈光乐
 * @create: 2022-03-14 21-03
 */
public class ConstantMQ {
    public static final String QUEUE_ORDER = "queueOrder";

    public static final String EXCHANGE_ORDER = "exchangeOrder";

    public static final String ROUTING_ORDER = "#.order.#";


    public static final String QUEUE_DEAD_LETTER = "deadLetterQueue";

    public static final String EXCHANGE_DEAD_LETTER = "deadLetterExchange";

    public static final String ROUTING_DEAD_LETTER = "deadLetter";
}
