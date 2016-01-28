package com.kyip.api.buildstartportal.external.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.pom._4_0.Model;
import org.apache.maven.pom._4_0.Model.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingUtil {
	private static final Logger logger = LoggerFactory.getLogger(SettingUtil.class);

	public String getJarPath(String basePath) throws FileNotFoundException, JAXBException {
		String jarName = getJarName(basePath) + "-" + getProjectVersion(basePath) + ".jar";
		String jarPath = basePath + getDwFolderPath(basePath) + "/target/" + jarName;
		return jarPath;
	}

	public String getYmlPath(String basePath) {
		String ymlFileName = getTargetFileName(basePath + getDwFolderPath(basePath), ".yml", false);
		if (StringUtils.isEmpty(ymlFileName)) {
			return null;
		}

		String ymlPath = basePath + getDwFolderPath(basePath) + "/" + ymlFileName;
		return ymlPath;
	}

	public String getLogPath(String basePath) {
		String logFolderName = getTargetFileName(basePath + getDwFolderPath(basePath), "log", true);
		String logFolderPath = StringUtils.isEmpty(logFolderName) ? "" : "/" + logFolderName;
		String logFileName = getTargetFileName(basePath + getDwFolderPath(basePath) + logFolderPath, ".log", false);
		if (StringUtils.isEmpty(logFileName)) {
			return null;
		}

		String logPath = basePath + getDwFolderPath(basePath) + logFolderPath + "/" + logFileName;
		return logPath;
	}

	private String getDwFolderPath(String basePath) {
		String dwFolderName = getTargetFileName(basePath, "-dw", true);
		String dwFolderPath = StringUtils.isEmpty(dwFolderName) ? "" : "/" + dwFolderName;
		return dwFolderPath;
	}

	private String getTargetFileName(String basePath, String targetFileHints, boolean isFolder) {
		File dir = new File(basePath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			List<File> matchedFiles = new ArrayList<>();
			for (File child : directoryListing) {
				if (isFolder) {
					if (child.isDirectory() && child.getName().contains(targetFileHints)) {
						return child.getName();
					}
				} else {
					if (child.isFile() && child.getName().contains(targetFileHints)) {
						matchedFiles.add(child);
						// return child.getName();
					}
				}
			}
			// get the DESC last modified file, same rules can apply to yml and log
			if (CollectionUtils.isNotEmpty(matchedFiles)) {
				File[] matchedFilesArray = matchedFiles.toArray(new File[matchedFiles.size()]);
				Arrays.sort(matchedFilesArray, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				return matchedFilesArray[0].getName();
			}
		}
		logger.error("No dw folder can be found for [PROJECT:{}]", basePath);
		return null;
	}

	private String getProjectVersion(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jaxbContext = JAXBContext.newInstance("org.apache.maven.pom._4_0");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		JAXBElement<Model> fhElement = (JAXBElement<Model>) unmarshaller.unmarshal(new FileInputStream(path + "/pom.xml"));
		Model model = fhElement.getValue();
		return model.getVersion();
	}

	private String getJarName(String path) throws JAXBException, FileNotFoundException {
		JAXBContext jaxbContext = JAXBContext.newInstance("org.apache.maven.pom._4_0");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		JAXBElement<Model> fhElement = (JAXBElement<Model>) unmarshaller.unmarshal(new FileInputStream(path + "/pom.xml"));
		Model model = fhElement.getValue();
		Modules ms = model.getModules();
		if (ms != null) {
			for (String name : ms.getModule()) {
				if (name.contains("dw")) {
					return name + "";
				}
			}
		} else {
			return model.getArtifactId();
		}
		return null;
	}
}
