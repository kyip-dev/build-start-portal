/**
 * Copyright (C) 2015 Powa Technology
 * All rights reserved.
 */
package com.kyip.api.buildstartportal.external.health;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import com.codahale.metrics.health.HealthCheck;
import com.kyip.api.buildstartportal.external.MainConfiguration;

/**
 * @author Powa Technology
 * 
 */
public class Ping extends HealthCheck {
	private final MainConfiguration configuration;

	/**
	 * @param configuration
	 */
	public Ping(MainConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected Result check() throws Exception {
		StringBuilder sb = new StringBuilder();
		int count = 0;

		if (count < 2) {
			return Result.unhealthy(sb.toString());
		}
		return Result.healthy(sb.toString());
	}

	private boolean isUrlReachable(String url) throws MalformedURLException {
		URL remote = new URL(url);
		SocketAddress socketAddress = new InetSocketAddress(remote.getHost(), remote.getPort());
		Socket socket = new Socket();
		try {
			socket.connect(socketAddress, 10000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}