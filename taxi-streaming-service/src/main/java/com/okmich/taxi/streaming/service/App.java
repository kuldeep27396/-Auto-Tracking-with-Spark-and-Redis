/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.taxi.streaming.service;

import com.okmich.taxi.streaming.service.jedis.JedisPublisher;
import java.io.Serializable;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.flume.FlumeUtils;
import org.apache.spark.streaming.flume.SparkFlumeEvent;

/**
 *
 * @author datadev
 */
public class App implements Serializable {

	private final transient JavaStreamingContext javaStreamingContext;
	private final String host;
	private final int port;

	/**
	 *
	 * @param master
	 * @param appName
	 * @param periodInMillis
	 * @param host
	 * @param port
	 */
	public App(String appName, int periodInMillis, String host,
			int port) {
		SparkConf conf = new SparkConf().setAppName(appName);
		javaStreamingContext = new JavaStreamingContext(conf,
				Durations.milliseconds(periodInMillis));
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
		if (args.length < 4) {
			System.err
					.println("Usage:: <appName> <period> <host> <port_number> \n");
			return;
		}
		try {
			int period = Integer.parseInt(args[1]);
			int port = Integer.parseInt(args[3]);

			App app = new App(args[0], period, args[2], port);
			app.initiateStreaming();
		} catch (Exception ex) {
			System.err.println("Error occured :: " + ex.getMessage());
		}
	}

	/**
     *
     */
	public void initiateStreaming() {
		JavaReceiverInputDStream<SparkFlumeEvent> flumeStream = FlumeUtils
				.createStream(javaStreamingContext, host, port);

		flumeStream.foreachRDD(new VoidFunction<JavaRDD<SparkFlumeEvent>>() {

			@Override
			public void call(JavaRDD<SparkFlumeEvent> javaRDD) throws Exception {
				javaRDD.foreach(new VoidFunction<SparkFlumeEvent>() {
					@Override
					public void call(SparkFlumeEvent sparkFlumeEvent)
							throws Exception {
						byte[] bytes = sparkFlumeEvent.event().getBody()
								.array();
						JedisPublisher.getInstance().publish(new String(bytes));
					}
				});
			}
		});

		javaStreamingContext.start();
		javaStreamingContext.awaitTermination();
	}
}
