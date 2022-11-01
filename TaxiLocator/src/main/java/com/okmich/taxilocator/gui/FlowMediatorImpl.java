/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator.gui;

import com.okmich.taxilocator.FlowMediator;
import com.okmich.taxilocator.RecordCache;
import com.okmich.taxilocator.Subscriber;
import com.okmich.taxilocator.gui.model.DashboardModel;
import com.okmich.taxilocator.gui.model.TaxiData;
import com.okmich.taxilocator.util.Utili;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.Duration;

/**
 *
 * @author datadev
 */
public class FlowMediatorImpl implements FlowMediator, Runnable {

    private static final Logger LOG = Logger.getLogger("FlowMediatorImpl");

    private final ConcurrentLinkedQueue<String> buffer = new ConcurrentLinkedQueue<>();
    private final DashboardModel model;
    private final RecordCache recordCache;
    private final Subscriber subscriber;
    private Thread thread;

    public FlowMediatorImpl(DashboardModel dashboardModel,
            RecordCache recordCache,
            Subscriber subscriber) {
        this.model = dashboardModel;
        this.recordCache = recordCache;
        this.subscriber = subscriber;
        this.subscriber.registerMediator(this);
    }

    @Override
    public boolean connect() {
        try {
            subscriber.connect();
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            subscriber.disconnect();
            thread.interrupt();
            thread = null;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public void update(String message) {
        buffer.offer(message);
    }

    /**
     * @return the model
     */
    public DashboardModel getDashboardModel() {
        return model;
    }

    private TaxiData processValue(String s) {
        TaxiData data = TaxiData.fromString(s);
        if (data == null) {
            return null;
        }
        String key = String.valueOf(data.getNo());
        String cached = recordCache.getTaxiRecord(key);
        if (cached != null) {
            TaxiData prevData = TaxiData.fromString(cached);
            //calculate the difference in time
            float mins = new Duration(prevData.getTimeAsDate().getTime(),
                    data.getTimeAsDate().getTime()).getStandardSeconds() / 60f;

            //calculate the distance covered in this space of time
            float distance = (float) Utili.distance(prevData.getLat(), data.getLat(),
                    prevData.getLon(), data.getLon(), 0f, 0f);

            //calculate the current speed
            float speed = (mins == 0) ? 0 : distance / (mins * 60);

            //update the data
            data.setCurrentSpeed(speed);
            data.setTotDist(prevData.getTotDist() + distance);
            data.setTotNumMins(prevData.getTotNumMins() + mins);
        }

        recordCache.setTaxiRecord(key, data.toString());
        return data;
    }

    @Override
    public void run() {
        String value;
        while (true) {
            value = buffer.poll();
            if (value != null && !value.trim().isEmpty()) {
                //process the data and send to model for display
                getDashboardModel().add(processValue(value));
            }
        }
    }

}
