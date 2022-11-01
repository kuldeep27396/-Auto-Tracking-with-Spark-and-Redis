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
public interface RecordCache {

    /**
     *
     * @param taxiNo
     * @param record
     */
    void setTaxiRecord(String taxiNo, String record);

    /**
     *
     * @param taxiNo
     * @return
     */
    String getTaxiRecord(String taxiNo);

}
