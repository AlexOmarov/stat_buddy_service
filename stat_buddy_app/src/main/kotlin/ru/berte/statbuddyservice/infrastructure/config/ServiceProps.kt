package ru.berte.statbuddyservice.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
data class ServiceProps(val contour: AppProps) {
    data class AppProps(val instance: String)
}
