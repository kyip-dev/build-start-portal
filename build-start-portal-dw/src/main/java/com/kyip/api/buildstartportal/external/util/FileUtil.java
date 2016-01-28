package com.kyip.api.buildstartportal.external.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.kyip.api.buildstartportal.external.datatype.Database;
import com.kyip.api.buildstartportal.external.websocket.MessageSender;

/**
 * FileUtil to replace profile for startup
 * 
 * @author kay IP
 * 
 */
public class FileUtil {
	private static final Map<String, BufferedReader> readers = Collections.synchronizedMap(new HashMap<String, BufferedReader>());

	public String[] readOriginalFile(String filename) throws IOException {
		List<String> lines = new ArrayList<>();

		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}

	@Deprecated
	// updated to use WebSocketTailerListerner
	public void readUpdatingFile(String filename, MessageSender messageSender) throws IOException, InterruptedException {
		BufferedReader bufferedReader = null;
		if (readers.containsKey(filename)) {
			bufferedReader = readers.get(filename);
		}

		List<String> lines = new ArrayList<>();
		String line = null;

		if (bufferedReader == null) {
			FileReader fileReader = new FileReader(filename);
			bufferedReader = new BufferedReader(fileReader);
			readers.put(filename, bufferedReader);
		}

		// int count = 0;
		while (true) {
			// long startTime = System.currentTimeMillis();
			line = bufferedReader.readLine();
			if (line != null) {
				// count = 0;
				lines.add(line);
				messageSender.sendMessage(line);
			} else {
				// count = count + 1;
				Thread.sleep(1000); // DELAY could be 100 (ms) for example
				// if (count == 100) {
				// bufferedReader.close();
				// messageSender.sendMessage("reader closed");
				// break;
				// }
			}
		}
		// bufferedReader.close();
		// return lines.toArray(new String[lines.size()]);
	}

	public void closeReaders(String filename) throws IOException {
		if (readers.containsKey(filename)) {
			BufferedReader reader = readers.get(filename);
			reader.close();
		}
	}

	public String[] replaceFileForMuliDbProfiles(String[] lines, List<Database> targetProfiles, List<Database> dbProfiles) {
		String[] contentToProcess = lines;

		List<Database> dbProfilesFinal = getCorrectProfile(targetProfiles, dbProfiles);

		for (Database db : dbProfilesFinal) { // run same file times
			Map<String, String> replaceMap = new HashMap<String, String>();
			Map<String, Boolean> replacedMap = new HashMap<String, Boolean>();
			replaceMap.put("  url: ", db.getUrl());
			replaceMap.put("  user: ", db.getUser());
			replaceMap.put("  password: ", db.getPassword());
			replacedMap.put("  url: ", false);
			replacedMap.put("  user: ", false);
			replacedMap.put("  password: ", false);

			String[] replacedContent = replaceFileInternal(contentToProcess, replaceMap, replacedMap, db.getName());
			contentToProcess = replacedContent;
		}

		return contentToProcess;
	}

	private List<Database> getCorrectProfile(List<Database> targetProfiles, List<Database> dbProfiles) {
		List<Database> dbProfilesFinal = new ArrayList<>();
		for (Database target : targetProfiles) {
			for (Database profile : dbProfiles) {
				if (profile.getName().equals(target.getName())) {
					dbProfilesFinal.add(profile);
				}
			}
		}

		if (CollectionUtils.isEmpty(dbProfilesFinal)) {
			// set default profile if no db name match
			Database defaultProfile = dbProfiles.get(0);
			defaultProfile.setName("database");
			dbProfilesFinal.add(defaultProfile);
		}

		return dbProfilesFinal;
	}

	public void writeToNewFile(String[] lines, String fileName) throws IOException {
		FileWriter writer = new FileWriter(fileName);
		for (String str : lines) {
			writer.write(str);
			writer.write(System.lineSeparator());
		}
		writer.close();
	}

	/**
	 * loop all the line of a file and perform replace for 1 db profile
	 * 
	 * @param lines
	 * @param replaceMap
	 * @param dbProfileName
	 * @return
	 */
	private String[] replaceFileInternal(String[] lines, Map<String, String> replaceMap, Map<String, Boolean> replacedMap, String dbProfileName) {
		ArrayList<String> newLines = new ArrayList<String>();
		boolean correntDbProfile = false;

		boolean profileReplaced = false;
		for (String line : lines) {
			if (!profileReplaced) {
				for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
					// check is it a correct database profile to replace
					if (line.toLowerCase().contains("database") && line.contains(dbProfileName)) {
						correntDbProfile = true;
					}

					if (correntDbProfile && line.contains(entry.getKey())) {
						String processLine = line;
						int start = processLine.indexOf(": ") + 2;
						String valueToReplace = line.substring(start, processLine.length());
						String newLine = line.replace(valueToReplace, entry.getValue());
						line = newLine;

						replacedMap.put(entry.getKey(), true);
						break;
					}
				}

				for (Map.Entry<String, Boolean> entry : replacedMap.entrySet()) {
					boolean result = true;
					result = result && entry.getValue();
					profileReplaced = result;
				}
			}
			newLines.add(line);
		}

		return newLines.toArray(new String[newLines.size()]);
	}

}
