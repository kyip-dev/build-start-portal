package com.kyip.api.buildstartportal.external.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

	public void executeCommand(String command) {
		String line = command;
		try {
			CommandLine cmdLine = CommandLine.parse(line);
			DefaultExecutor executor = new DefaultExecutor();
			int exitValue = executor.execute(cmdLine);
			logger.trace("Exited with error code " + exitValue);
		} catch (ExecuteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getPidByPort(int port) {
		Integer pid = null;
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("cmd /c netstat -ano | find \"" + port + "\" | find \"LISTENING\"");

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = null;
			while ((line = input.readLine()) != null) {
				String[] aArray = line.split(" ");
				List<String> list = new ArrayList<String>(Arrays.asList(aArray));
				list.removeAll(Collections.singleton(""));
				pid = Integer.valueOf(list.get(4));
			}

			int exitVal = pr.waitFor();
			logger.trace("Exited with error code " + exitVal);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}

}
