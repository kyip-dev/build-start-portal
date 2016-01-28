package com.kyip.api.buildstartportal.external.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/messages/send")
public class MessageSender {

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(final Session session) {
		// add the new session to the set
		sessions.add(session);
	}

	@OnClose
	public void onClose(final Session session) {
		// remove the session from the set
		sessions.remove(session);
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		synchronized (sessions) {
			for (Session s : sessions) {
				if (s.isOpen()) {
					try {
						s.getBasicRemote().sendText(msg);
					} catch (IOException ex) {
					}
				}
			}
		}
	}

	public void sendMessage(String msg) {
		synchronized (sessions) {
			for (Session s : sessions) {
				if (s.isOpen()) {
					try {
						s.getBasicRemote().sendText(msg);
					} catch (IOException ex) {
					}
				}
			}
		}
	}

}
