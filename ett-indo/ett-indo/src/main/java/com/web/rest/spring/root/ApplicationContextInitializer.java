package com.web.rest.spring.root;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * @author ankur
 */
public class ApplicationContextInitializer implements org.springframework.context.ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        final ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        try {

            final String target = environment.getProperty("target");
            environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:env-" + target + ".properties"));

            final String activeProfiles = environment.getProperty("spring.profiles.active");
            LOGGER.info("The active profile is: " + activeProfiles);

            environment.setActiveProfiles(activeProfiles.split(","));
        } catch (final IOException e) {
            // it's OK if the file is not there. we will just log that info.
            LOGGER.info("didn't find env.properties in classpath so not loading it in the AppContextInitialized");
        }
    }
}
