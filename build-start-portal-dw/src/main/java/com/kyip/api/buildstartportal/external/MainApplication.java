package com.kyip.api.buildstartportal.external;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.tomcools.dropwizard.websocket.WebsocketBundle;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kyip.api.buildstartportal.external.health.Ping;
import com.kyip.api.buildstartportal.external.modules.BuildStartModule;
import com.kyip.api.buildstartportal.external.resource.BuildResourceImpl;
import com.kyip.api.buildstartportal.external.resource.CalendarResourceImpl;
import com.kyip.api.buildstartportal.external.resource.HtmlParserResourceImpl;
import com.kyip.api.buildstartportal.external.resource.MessageResourceImpl;
import com.kyip.api.buildstartportal.external.resource.StartupResourceImpl;
import com.kyip.api.buildstartportal.external.websocket.MessageSender;
import com.kyip.api.buildstartportal.external.websocket.WebSocketTailerListener;
import com.kyip.api.buildstartportal.external.websocket.WebSocketTest;
import com.mpayme.dw.common.rs.resource.provider.CustomJsonReaderProvider;
import com.mpayme.dw.common.rs.resource.provider.CustomJsonWriterProvider;

public class MainApplication extends Application<MainConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	private final WebsocketBundle websocket = new WebsocketBundle();

	@Override
	public String getName() {
		return "build-start-portal";
	}

	public static void main(String[] args) throws Exception {
		new MainApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<MainConfiguration> bootstrap) {
		bootstrap.addBundle(new ViewBundle<MainConfiguration>() {
			@Override
			public ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(MainConfiguration configuration) {
				return configuration.getViewRendererConfiguration();
			}
		});
		bootstrap.addBundle(new AssetsBundle("/assets"));
		bootstrap.addBundle(websocket);

		LOGGER.info("initialized");
	}

	@Override
	public void run(MainConfiguration configuration, Environment environment) throws Exception {
		// register health checks
		environment.healthChecks().register("ping", new Ping(configuration));

		// register resource binds
		Injector injector = Guice.createInjector(new BuildStartModule(configuration));

		environment.jersey().register(injector.getInstance(BuildResourceImpl.class));
		environment.jersey().register(injector.getInstance(StartupResourceImpl.class));
		environment.jersey().register(injector.getInstance(MessageResourceImpl.class));
		environment.jersey().register(injector.getInstance(CalendarResourceImpl.class));
		environment.jersey().register(injector.getInstance(HtmlParserResourceImpl.class));

		// register providers
		environment.jersey().register(injector.getInstance(CustomJsonReaderProvider.class));
		environment.jersey().register(injector.getInstance(CustomJsonWriterProvider.class));

		environment.jersey().register(new LoggingFilter(java.util.logging.Logger.getLogger(MainApplication.class.getName()), true));

		// read projects.json
		String projectRegistires = getSettings("projects.json");
		configuration.setProjectRegistries(projectRegistires);

		String profileRegistires = getSettings("profiles2.json");
		configuration.setProfileRegistries(profileRegistires);

		// Annotated endpoint
		websocket.addEndpoint(WebSocketTest.class);
		websocket.addEndpoint(MessageSender.class);
		websocket.addEndpoint(WebSocketTailerListener.class);

		LOGGER.info("run completed");
	}

	private String getSettings(String fileName) {
		try (FileInputStream inputStream = new FileInputStream(fileName)) {
			return IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
