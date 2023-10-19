package ru.berte.statbuddyservice.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import ru.berte.statbuddyservice.infrastructure.config.ServiceProps

@Component
class LoggingWebFilter(private val props: ServiceProps) : WebFilter {

    private val log = LoggerFactory.getLogger(LoggingResponseDecorator::class.java)

    @Suppress("kotlin:S6508")
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val finalExchange = if (shouldLog(exchange.request.uri.path)) LoggingWebExchange(exchange) else exchange
        return chain.filter(finalExchange)
    }

    private fun shouldLog(path: String): Boolean {
        var result = true
        props.logging.web.exclusions.forEach { if (path.contains(it)) result = false }
        return result
    }
}
