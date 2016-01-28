package com.kyip.api.buildstartportal.external.util;

import io.dropwizard.views.View;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class ViewUtil {
	public static String render(View view, Locale locale) throws Exception {
		// View renderer in dropwizard 0.8 does not render when content type is "application/javascript"
		FreemarkerViewRenderer renderer = new FreemarkerViewRenderer();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		renderer.render(view, locale, stream);
		return new String(stream.toByteArray(), "UTF-8");
	}
}
