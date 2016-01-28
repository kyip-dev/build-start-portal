package com.kyip.api.buildstartportal.external.util;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.Tailer;

import com.kyip.api.buildstartportal.external.websocket.WebSocketTailerListener;

public class ThreadUtil {

	private static final Map<String, Thread> threads = Collections.synchronizedMap(new HashMap<String, Thread>());
	private static int delay = 1000;

	public void addThread(String key, Thread t) {
		threads.put(key, t);
	}

	public void start(String filePath, WebSocketTailerListener listener) {
		Tailer tailer = new Tailer(new File(filePath), listener, delay);
		Thread thread = new Thread(tailer);
		thread.setDaemon(true); // optional
		thread.start();

		threads.put(filePath, thread);
	}

	public void stop(String filePath) {
		if (threads.containsKey(filePath)) {
			Thread t = threads.get(filePath);
			t.stop();
			threads.remove(filePath);
		}
	}
}
