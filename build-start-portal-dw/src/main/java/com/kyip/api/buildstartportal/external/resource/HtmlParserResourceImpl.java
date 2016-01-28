package com.kyip.api.buildstartportal.external.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Demonstrate how to use jsoup to get different elements from a http html page
 * 
 * https requires certs and authentication
 * 
 * http://jsoup.org/
 * 
 * @author kay IP
 * 
 */
public class HtmlParserResourceImpl implements HtmlParserResource {

	private static final Logger logger = LoggerFactory.getLogger(HtmlParserResourceImpl.class);

	@Override
	public Response getLinks(String url) throws Exception {
		Elements links = getElements(url, "a[href]");

		List<String> linkArrayList = new ArrayList<>();
		for (Element link : links) {
			linkArrayList.add(link.text());
			logger.info(" * a: <{}>  ({})", link.attr("abs:href"), link.text());
		}

		return Response.ok(linkArrayList).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8")).build();
	}

	@Override
	public Response getMedias(String url) throws Exception {
		Elements media = getElements(url, "[src]");

		List<String> linkArrayList = new ArrayList<>();
		for (Element src : media) {
			if (src.tagName().equals("img")) {
				logger.info(" * {}: <{}> {}x{} ({})", src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"), src.attr("alt"));
			} else {
				logger.info(" * {}: <{}>", src.tagName(), src.attr("abs:src"));
			}
			linkArrayList.add(src.tagName() + "" + src.attr("abs:src"));
		}

		return Response.ok(linkArrayList).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8")).build();
	}

	private Elements getElements(String url, String docType) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select(docType);
		return elements;
	}
}
