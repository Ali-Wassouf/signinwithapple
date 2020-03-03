package com.oisou.repository

import de.flapdoodle.embed.process.runtime.Network
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import ru.yandex.qatools.embed.postgresql.PostgresProcess
import ru.yandex.qatools.embed.postgresql.PostgresStarter
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig
import ru.yandex.qatools.embed.postgresql.distribution.Version
import java.io.IOException
import java.util.Properties
import javax.sql.DataSource

@Configuration
@ConditionalOnProperty(name = ["embedded.postgres.test"], havingValue = "true", matchIfMissing = false)
class DbConfig(
    @Value("\${embedded.postgres.models}")
    val modelPckage: String,
    @Value("\${embedded.postgres.repositories}")
    val repoPckage: String
) {

    private val DEFAULT_ADDITIONAL_INIT_DB_PARAMS = listOf("--nosync", "--locale=en_US.UTF-8")

    @Bean
    @DependsOn("postgresProcess")
    fun dataSource(postgresConfig: PostgresConfig): DataSource {
        val driverManager = DriverManagerDataSource()
        driverManager.setDriverClassName("org.postgresql.Driver");

        driverManager.url = String.format("jdbc:postgresql://%s:%s/%s", postgresConfig.net().host(),
            postgresConfig.net().port(), postgresConfig.storage().dbName())

        driverManager.username = postgresConfig.credentials().username()
        driverManager.password = postgresConfig.credentials().password()
        return driverManager
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val lcemfb = LocalContainerEntityManagerFactoryBean()
        lcemfb.dataSource = dataSource
        lcemfb.setPackagesToScan(modelPckage, repoPckage)
        lcemfb.jpaVendorAdapter = HibernateJpaVendorAdapter()
        lcemfb.setJpaProperties(getHibernateProperties())
        lcemfb.afterPropertiesSet()
        return lcemfb
    }

    @Bean
    fun transactionManager(localContainerEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = localContainerEntityManagerFactoryBean.`object`
        return transactionManager
    }

    @Bean
    fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    @Bean
    @Throws(IOException::class)
    fun postgresConfig(): PostgresConfig {

        val postgresConfig = PostgresConfig(Version.V9_6_8,
            AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
            AbstractPostgresConfig.Storage("test"),
            AbstractPostgresConfig.Timeout(),
            AbstractPostgresConfig.Credentials("user", "pass")
        )

        postgresConfig.additionalInitDbParams.addAll(DEFAULT_ADDITIONAL_INIT_DB_PARAMS)

        return postgresConfig
    }

    @Bean(destroyMethod = "stop")
    @Throws(IOException::class)
    fun postgresProcess(config: PostgresConfig): PostgresProcess {
        val runtime = PostgresStarter.getDefaultInstance()
        val exec = runtime.prepare(config)
        return exec.start()
    }

    private fun getHibernateProperties(): Properties {
        val ps = Properties()
        ps["hibernate.temp.use_jdbc_metadata_defaults"] = "false"
        ps["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQL95Dialect"
        ps["hibernate.hbm2ddl.auto"] = "update"
        ps["hibernate.connection.characterEncoding"] = "UTF-8"
        ps["hibernate.connection.charSet"] = "UTF-8"

        ps[AvailableSettings.FORMAT_SQL] = "true"
        ps[AvailableSettings.SHOW_SQL] = "true"
        return ps

    }

}