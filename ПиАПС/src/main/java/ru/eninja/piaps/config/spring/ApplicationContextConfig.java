package ru.eninja.piaps.config.spring;

import org.springframework.context.annotation.*;


@Configuration
@Import({PersistenceConfig.class, ThymeleafConfig.class})
@PropertySource("classpath:app.properties")
@ComponentScan("ru.eninja.piaps")
public class ApplicationContextConfig {
}