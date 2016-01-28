package com.kyip.api.buildstartportal.external.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessBuilderUtil {
	private static final Logger logger = LoggerFactory.getLogger(ProcessBuilderUtil.class);

	public Process buildTask(String command) throws Exception {

		List<String> commands = new ArrayList<>();
		commands.add("cmd.exe");
		commands.add("/c");
		commands.add(command);

		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		return process;
	}

	public boolean monitorProcess(Process process) throws IOException {
		List<String> logLines = new ArrayList<>();
		// task.setLogLines(logLines);

		InputStream inputStream = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			logger.info(line);
			logLines.add(line);
		}

		try {
			int exitCode = process.waitFor();
			logger.info("exit code: {}", exitCode);
			return (0 == exitCode);
		} catch (InterruptedException e) {
			logger.info("interruped", e);
			return false;
		}
	}

}
