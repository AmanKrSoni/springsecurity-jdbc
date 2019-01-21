package com.cvt.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cvt")
@PropertySource("classpath:jdbc-mysql.properties")
public class DispatcherServletConfig {

    //setup variable to hold the properties
    @Autowired
    private Environment env;

    //setup the logger for diagnostic
    Logger logger=Logger.getLogger(getClass().getName());

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return  viewResolver;
    }

    //define bean for security datasource
    @Bean
    public DataSource securityDataSource(){
        //create a connection pool
        ComboPooledDataSource securityDataSource=new ComboPooledDataSource();
        //get jdbc connection
        try{
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        }catch (Exception e){throw new RuntimeException(e);}

        //LOG THE CONNECTION props
        logger.info(">>> jdbc.url "+env.getProperty("jdbc.url"));
        logger.info(">>> jdbc.user "+env.getProperty("jdbc.user"));

        //setup the database connection props
        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));

        // setup the connectionpool props
        securityDataSource.setInitialPoolSize(getIntProperties("connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(getIntProperties("connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(getIntProperties("connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(getIntProperties("connection.pool.maxIdleTime"));

        return securityDataSource;
    }


    //Hibernate
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(securityDataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.cvt.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    //need a method to get properties value as integer
    private int getIntProperties(String propName){
        String pName=env.getProperty(propName);
        int pValue=Integer.parseInt(pName);
        return pValue;
    }
}
