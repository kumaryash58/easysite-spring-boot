//package com.easybuild.site.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@ComponentScan(basePackages = { "com.easybuild.site.entity" })
//@PropertySource(value = { "classpath:properties/hibernate/hibernate.properties" })
//public class PersistenceConfig {
//	@Value("${hibernate.dialect}")
//	private String dialect;
//	@Value("${hibernate.show_sql}")
//	private String sqlStatus;
//	@Value("${hibernate.format_sql}")
//	private String formatSQL;
//	@Value("${hibernate.connection.useUnicode}")
//	private String useUnicode;
//	@Value("${hibernate.connection.characterEncoding}")
//	private String characterEncoding;
//
//	@Autowired
//	private DataSource dataSource;
//
//	/**
//	 * Data source configuration.
//	 * 
//	 * @return
//	 */
//	@Profile({ "dev", "test", "prod" })
//	@Bean
//	public BasicDataSource dataSource(@Value("${spring.datasource.driverClassName}") String driverClass,
//			@Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String username,
//			@Value("${spring.datasource.password}") String password
////			@Value("${jdbc.initialConnectionSize}") int initialConnectionSize,
////			@Value("${jdbc.maxIdleConnection}") int maxIdleConnection,
////			@Value("${jdbc.maxConnection}") int maxConnection
//			) {
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setDriverClassName(driverClass);
//		basicDataSource.setUrl(url);
//		basicDataSource.setUsername(username);
//		basicDataSource.setPassword(password);
////		basicDataSource.setInitialSize(initialConnectionSize);
////		basicDataSource.setMaxIdle(maxIdleConnection);
////		basicDataSource.setMaxTotal(maxConnection);
////		basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
//		return basicDataSource;
//	}
//
//	/**
//	 * Session factory configuration.
//	 * 
//	 * @return
//	 */
//	@Bean
//	public SessionFactory sessionFactory() {
//		LocalSessionFactoryBuilder sessionFactory = new LocalSessionFactoryBuilder(dataSource);
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", dialect);
//		properties.put("hibernate.show_sql", sqlStatus);
//		properties.put("hibernate.format_sql", formatSQL);
////		properties.put("hibernate.connection.useUnicode", useUnicode);
////		properties.put("hibernate.connection.characterEncoding", characterEncoding);
////		properties.put("hibernate.cache.region.factory_class",
////				"org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
////		properties.put("hibernate.cache.use_second_level_cache", true);
////		properties.put("hibernate.cache.use_query_cache", true);
////		properties.put("net.sf.ehcache.configurationResourceName", "/ehcache.xml");
//		sessionFactory.scanPackages("com.easybuild.site.entity").addProperties(properties);
//		return sessionFactory.buildSessionFactory();
//	}
//
//	/**
//	 * Hibernate transaction manager configuration.
//	 * 
//	 * @return
//	 */
//	@Bean
//	public HibernateTransactionManager transactionManager() {
//		return new HibernateTransactionManager(sessionFactory());
//	}
//
////	/**
////	 * jdbc template configuration.
////	 * 
////	 * @return
////	 */
////	@Bean
////	public JdbcTemplate getJDBCTemplate() {
////		return new JdbcTemplate(dataSource);
////	}
////
////	/**
////	 * Named parameter jdbc template configuration.
////	 * 
////	 * @return
////	 */
////	@Bean
////	public NamedParameterJdbcTemplate getNamedJDBCTemplate() {
////		return new NamedParameterJdbcTemplate(dataSource);
////	}
//}
