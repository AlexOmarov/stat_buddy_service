package ru.berte.statbuddyservice.infrastructure.http

import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels
import java.util.*

class LoggingRequestDecorator internal constructor(delegate: ServerHttpRequest) : ServerHttpRequestDecorator(delegate) {

    private val log = LoggerFactory.getLogger(LoggingRequestDecorator::class.java)

    private val body: Flux<DataBuffer>?

    override fun getBody(): Flux<DataBuffer> {
        return body!!
    }

    init {
        val path = delegate.uri.path
        val query = delegate.uri.query
        val method = Optional.ofNullable(delegate.method).orElse(HttpMethod.GET).name()
        val headers = delegate.headers.toString()
        log.info(
            "{} {}\n {}", method, path + (if (StringUtils.hasText(query)) "?$query" else ""), headers
        )
        body = super.getBody().publishOn(Schedulers.boundedElastic()).doOnNext { buffer: DataBuffer ->
            val bodyStream = ByteArrayOutputStream()
            val channel = Channels.newChannel(bodyStream)
            buffer.readableByteBuffers().forEach { channel.write(it) }
            log.debug("{}: {}", "request", String(bodyStream.toByteArray()))
        }
    }
}
