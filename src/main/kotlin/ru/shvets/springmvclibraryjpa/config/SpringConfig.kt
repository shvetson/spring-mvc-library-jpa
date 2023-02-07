package ru.shvets.springmvclibraryjpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  31.01.2023 23:37
 */

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableJpaRepositories("ru.shvets.springmvclibraryjpa.repository")
@EnableTransactionManagement
class SpringConfig(
    private val environment: Environment
) {

    @Bean
    fun datasource(): DataSource {
        val dataSource: DriverManagerDataSource = DriverManagerDataSource()
        environment.getProperty("hibernate.driver-class")?.let { dataSource.setDriverClassName(it) }
        dataSource.url = environment.getProperty("hibernate.connection.url")
        dataSource.username = environment.getProperty("hibernate.connection.username")
        dataSource.password = environment.getProperty("hibernate.connection.password")
        return dataSource
    }

    fun hibernateProperties(): Properties {
        val properties: Properties = Properties()
        properties["hibernate.dialect"] = environment.getRequiredProperty("hibernate.dialect")
        properties["hibernate.show_sql"] = environment.getRequiredProperty("hibernate.show_sql")
        return properties
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManager: LocalContainerEntityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManager.dataSource = datasource()
        entityManager.setPackagesToScan("ru.shvets.springmvclibraryjpa.model")

        val vendorAdapter: HibernateJpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManager.jpaVendorAdapter = vendorAdapter
        entityManager.setJpaProperties(hibernateProperties())
        return entityManager
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManagement:JpaTransactionManager = JpaTransactionManager()
        transactionManagement.entityManagerFactory = entityManagerFactory().getObject()
        return transactionManagement
    }
}