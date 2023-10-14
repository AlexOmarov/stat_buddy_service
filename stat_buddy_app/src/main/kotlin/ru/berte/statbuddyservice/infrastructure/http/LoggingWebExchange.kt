package ru.berte.statbuddyservice.infrastructure.http

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator

class LoggingWebExchange(delegate: ServerWebExchange) : ServerWebExchangeDecorator(delegate) {

    private val requestDecorator = LoggingRequestDecorator(delegate.request)

    private val responseDecorator = LoggingResponseDecorator(delegate.response)

    override fun getRequest(): ServerHttpRequest = requestDecorator

    override fun getResponse(): ServerHttpResponse = responseDecorator
}
