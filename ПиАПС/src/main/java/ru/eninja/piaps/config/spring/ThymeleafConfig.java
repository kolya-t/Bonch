package ru.eninja.piaps.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.List;


@Configuration
@EnableWebMvc
public class ThymeleafConfig extends WebMvcConfigurerAdapter {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Bean(name = "templateResolver")
    public ITemplateResolver getTemplateResolver() {
        SpringResourceTemplateResolver resolver =
                new SpringResourceTemplateResolver();

        resolver.setPrefix(env.getRequiredProperty("template_resolver.prefix"));
        resolver.setSuffix(env.getRequiredProperty("template_resolver.suffix"));
        resolver.setTemplateMode(
                env.getProperty("template_resolver.template_mode", "HTML5"));

        resolver.setCharacterEncoding("UTF-8");

        return resolver;
    }

    @Bean(name = "templateEngine")
    public TemplateEngine getTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setTemplateResolver(getTemplateResolver());
        engine.addDialect(new SpringDataDialect());

        return engine;
    }

    @Bean(name = "viewResolver")
    public ViewResolver getViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(getTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/static/js/")
                .addResourceLocations("/static/node_modules/jquery/dist/")
                .addResourceLocations("/static/node_modules/bootstrap/dist/js/")
                .addResourceLocations("/static/node_modules/bootstrap-select/dist/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/static/css/")
                .addResourceLocations("/static/node_modules/bootstrap/dist/css/")
                .addResourceLocations("/static/node_modules/bootstrap-select/dist/css/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/static/node_modules/bootstrap/dist/fonts/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}
