package com.kyip.api.buildstartportal.external.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.joda.time.LocalTime;

@ServerEndpoint("/clock")
public class WebSocketTest {
	static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

	private static Set<Session> allSessions;

	@OnOpen
	public void showTime(Session session) {
		System.out.println("onOpen==========");
		allSessions = session.getOpenSessions();

		// start the scheduler on the very first connection
		// to call sendTimeToAll every second
		// if (allSessions.size() == 1) {
		timer.scheduleAtFixedRate(sendTimeToAll(session), 0, 1, TimeUnit.SECONDS);
		// }
	}

	private Runnable sendTimeToAll(final Session session) {
		Runnable aRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					session.getBasicRemote().sendText("Local time: " + LocalTime.now());
				} catch (IOException ioe) {
					// System.out.println(ioe.getMessage());
				}
			}
		};

		return aRunnable;

	}
}
