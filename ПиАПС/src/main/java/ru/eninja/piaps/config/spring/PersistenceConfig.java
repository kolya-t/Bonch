package ru.eninja.piaps.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.eninja.piaps.config.spring.datasource.EmbeddedDataSource;
import ru.eninja.piaps.config.spring.datasource.ProductionDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@Import({ProductionDataSource.class, EmbeddedDataSource.class})
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories("ru.eninja.piaps.dao")
@EnableSpringDataWebSupport
public class PersistenceConfig {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setPackagesToScan(env.getRequiredProperty("packages_to_scan.entities"));
        entityManagerFactory.setJpaProperties(getHibernateProperties());

        return entityManagerFactory;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager(
            EntityManagerFactory entityManagerFactory, DataSource dataSource) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return new JpaTransactionManager();
    }

    @Bean(name = "exceptionTranslation")
    public PersistenceExceptionTranslationPostProcessor getExceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        for (String propertyName : new String[]{
                "hibernate.show_sql",
                "hibernate.dialect",
                "hibernate.hbm2ddl.auto",
                "hibernate.enable_lazy_load_no_trans"
        }) {
            properties.put(propertyName, env.getProperty(propertyName));
        }

        return properties;
    }
}
