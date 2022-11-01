/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator.redis;

import com.okmich.taxilocator.RecordCache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

/**
 *
 * @author datadev
 */
public class RecordCacheImpl implements RecordCache {

    private final Jedis jedis;
    private static final String PREFIX = "tx-";

    public RecordCacheImpl() {
        jedis = new Jedis(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    @Override
    public void setTaxiRecord(String taxiNo, String record) {
        jedis.set(PREFIX + taxiNo, record);
    }

    @Override
    public String getTaxiRecord(String taxiNo) {
        return jedis.get(PREFIX + taxiNo);
    }
}
