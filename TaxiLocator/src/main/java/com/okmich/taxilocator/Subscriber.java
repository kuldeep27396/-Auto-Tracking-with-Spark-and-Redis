/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator;

/**
 *
 * @author datadev
 */
public interface Subscriber {

    void registerMediator(FlowMediator flowMediator);

    /**
     *
     */
    void connect();

    /**
     *
     */
    void disconnect();

}
