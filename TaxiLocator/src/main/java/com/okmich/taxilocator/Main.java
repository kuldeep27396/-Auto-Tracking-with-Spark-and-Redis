/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator;

import com.okmich.taxilocator.gui.AppFrame;
import com.okmich.taxilocator.gui.FlowMediatorImpl;
import com.okmich.taxilocator.gui.model.DashboardModel;
import com.okmich.taxilocator.redis.JedisSubsriber;
import com.okmich.taxilocator.redis.RecordCacheImpl;

/**
 *
 * @author datadev
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        DashboardModel dashboardModel = new DashboardModel();
        RecordCache recordCache = new RecordCacheImpl();
        Subscriber subscriber = JedisSubsriber.getSubscriber();
        final FlowMediator flowMediator = new FlowMediatorImpl(dashboardModel, recordCache, subscriber);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AppFrame(flowMediator).setVisible(true);
            }
        });
    }
}
