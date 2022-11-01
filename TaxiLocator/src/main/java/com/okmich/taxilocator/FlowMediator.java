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
public interface FlowMediator {

    /**
     *
     * @return
     */
    boolean connect();

    /**
     *
     * @return
     */
    boolean disconnect();

    /**
     *
     * @param message
     */
    void update(String message);

}
