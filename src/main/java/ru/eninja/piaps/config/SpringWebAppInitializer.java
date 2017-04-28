package ru.eninja.piaps.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import ru.eninja.piaps.config.spring.ApplicationContextConfig;
import ru.eninja.piaps.config.spring.PersistenceConfig;
import ru.eninja.piaps.config.spring.ThymeleafConfig;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

/**
 * This class performs the function of `web.xml`.
 */
public class SpringWebAppInitializer implements WebApplicationInitializer {

    private String springActiveProfiles;

    public SpringWebAppInitializer() {
        Properties properties = new Properties();

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(resourceAsStream);
            springActiveProfiles = properties.getProperty("spring.profiles.active");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // root spring app context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();

        rootContext.register(ApplicationContextConfig.class, PersistenceConfig.class, ThymeleafConfig.class);

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet(
                "SpringDispatcher", new DispatcherServlet(rootContext));

        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setInitParameter("spring.profiles.active", springActiveProfiles);

        CharacterEncodingFilter charsetFilter = new CharacterEncodingFilter("UTF-8", true);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", charsetFilter);
        encodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        FilterRegistration.Dynamic hiddenHttpMethodFilter = servletContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter());
        hiddenHttpMethodFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}
