package ru.eninja.piaps.config.spring.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
@Profile("embedded")
public class EmbeddedDataSource {

    @Bean("dataSource")
    public EmbeddedDatabase getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .setScriptEncoding("UTF-8")
                .addScript("test-db/schema.sql")
                .addScript("test-db/test-data.sql")
                .build();
    }
}
