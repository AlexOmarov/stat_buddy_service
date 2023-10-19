package ru.berte.statbuddyservice.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
data class ServiceProps(val contour: AppProps, val logging: LoggingProps) {
    data class AppProps(val instance: String)
    data class LoggingProps(val web: LoggingWebProps)
    data class LoggingWebProps(val exclusions: List<String>)
}
