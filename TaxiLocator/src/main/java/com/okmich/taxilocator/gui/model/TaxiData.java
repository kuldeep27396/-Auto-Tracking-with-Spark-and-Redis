/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxilocator.gui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public final class TaxiData {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
    private int no;
    private float lat;
    private float lon;
    private String time;
    private float totNumMins = 0;
    private float totDist = 0;
    private float currentSpeed = 0f;

    public TaxiData() {
    }

    public TaxiData(int no, float lat, float lon, String time) {
        this.no = no;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @return the lat
     */
    public float getLat() {
        return lat;
    }

    /**
     * @return the lon
     */
    public float getLon() {
        return lon;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the time as a date object
     */
    public Date getTimeAsDate() {
        try {
            return SDF.parse(time);
        } catch (ParseException ex) {
            Logger.getLogger(TaxiData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the totNumMins
     */
    public float getTotNumMins() {
        return totNumMins;
    }

    /**
     * @param totNumMins the totNumMins to set
     */
    public void setTotNumMins(float totNumMins) {
        this.totNumMins = totNumMins;
    }

    /**
     * @return the totDist
     */
    public float getTotDist() {
        return totDist;
    }

    /**
     * @param totDist the totDist to set
     */
    public void setTotDist(float totDist) {
        this.totDist = totDist;
    }

    /**
     * @return the currentSpeed
     */
    public float getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * @param currentSpeed the currentSpeed to set
     */
    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    @Override
    public int hashCode() {
        return no;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaxiData other = (TaxiData) obj;
        return this.no == other.no;
    }

    /**
     * @param s
     * @return
     */
    public static TaxiData fromString(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        String[] parts = s.split(",");
        if (parts.length < 4) {
            return null;
        }
        Integer key = Integer.parseInt(parts[0]);
        TaxiData data = new TaxiData(key,
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3]),
                parts[1]);
        if (parts.length > 4) {
            data.setTotNumMins(Float.parseFloat(parts[4]));
            data.setTotDist(Float.parseFloat(parts[5]));
            data.setCurrentSpeed(Float.parseFloat(parts[6]));
        }
        return data;
    }

    @Override
    public String toString() {
        return no + "," + time + "," + lat + "," + lon + "," + totNumMins + "," + totDist + "," + currentSpeed;
    }

}
