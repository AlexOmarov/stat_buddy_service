package ru.berte.statbuddyservice.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.Optional

@Component
class LoggingWebFilter : WebFilter {

    private val log = LoggerFactory.getLogger(LoggingResponseDecorator::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (
            exchange.request.uri.path.contains("swagger") ||
            exchange.request.uri.path.contains("api-docs")
        ) {
            return chain.filter(exchange)
        }
        LoggingWebExchange(exchange).apply {
            return chain.filter(this).doOnSuccess {
                if (this.response.headers.contentLength < 0) {
                    logResponse(this.request, this.response)
                }
            }
        }
    }

    fun logResponse(request: ServerHttpRequest, response: ServerHttpResponse) {
        val path = request.uri.path
        if (!(path.contains("actuator") || path.contains("swagger") || path.contains("api-docs"))) {
            val query = request.uri.query
            val fullPath = path + (if (StringUtils.hasText(query)) "?$query" else "")
            val method = Optional.ofNullable(request.method).orElse(HttpMethod.GET).name()
            val responseMap = hashMapOf<String, Any>(
                "headers" to response.headers.toSingleValueMap(),
                "method" to method,
                "path" to path
            ).also { m -> response.statusCode?.let { m["status"] = it.value() } }

            log.info(
                "<- {} {} {} [{}]",
                request.id,
                method,
                fullPath,
                responseMap
            )
        }
    }
}
