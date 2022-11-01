/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxi.streaming.service.jedis;

import java.io.Serializable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

/**
 *
 * @author datadev
 */
public class JedisPublisher implements Serializable {

    private final Jedis jedis;
    private static JedisPublisher _instance;

    public static final String CHANNEL = "taxis";

    private JedisPublisher() {
        this.jedis = new Jedis(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public static JedisPublisher getInstance() {
        if (_instance == null) {
            _instance = new JedisPublisher();
        }
        return _instance;
    }

    /**
     *
     * @param event
     */
    public void publish(String event) {
        jedis.publish(CHANNEL, event);
    }

}
