package ru.berte.statbuddyservice.infrastructure.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfig(private val env: Environment) {
    @Bean(initMethod = "migrate")
    fun flyway() = Flyway(
        Flyway.configure().baselineOnMigrate(true).dataSource(
            /* url = */ env.getRequiredProperty("spring.flyway.url"),
            /* user = */ env.getRequiredProperty("spring.flyway.user"),
            /* password = */ env.getRequiredProperty("spring.flyway.password")
        ).defaultSchema(env.getRequiredProperty("spring.flyway.default-schema"))
    )
}
