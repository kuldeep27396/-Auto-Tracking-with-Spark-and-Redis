/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator.redis;

import com.okmich.taxilocator.Subscriber;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import com.okmich.taxilocator.FlowMediator;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Protocol;

/**
 *
 * @author datadev
 */
public class JedisSubsriber extends JedisPubSub implements Subscriber, Runnable {

    private FlowMediator flowMediator;
    private final static JedisSubsriber ME = new JedisSubsriber();
    private final Jedis jedis;

    private Thread thread;

    private static final Logger LOG = Logger.getLogger(JedisSubsriber.class.getName());

    public JedisSubsriber() {
        this.jedis = new Jedis(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    /**
     *
     * @return
     */
    public static Subscriber getSubscriber() {
        return ME;
    }

    @Override
    public void onMessage(String channel, String message) {
        ME.flowMediator.update(message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        LOG.log(Level.INFO, "{0} has been subscribed to", channel);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        LOG.log(Level.INFO, "{0} has been unsubscribed from", channel);
    }

    @Override
    public void registerMediator(FlowMediator fm) {
        this.flowMediator = fm;
    }

    @Override
    public void connect() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void disconnect() {
        this.unsubscribe();
        thread.interrupt();
    }

    @Override
    public void run() {
        this.jedis.subscribe(this, "taxis");
    }
}
