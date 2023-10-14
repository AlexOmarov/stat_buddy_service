package ru.berte.statbuddyservice.infrastructure.http

import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels

class LoggingResponseDecorator internal constructor(delegate: ServerHttpResponse) :
    ServerHttpResponseDecorator(delegate) {
    private val log = LoggerFactory.getLogger(LoggingResponseDecorator::class.java)

    @Suppress("squid:S6508")
    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
        return super.writeWith(
            Flux.from(body)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext { buffer: DataBuffer ->
                    val bodyStream = ByteArrayOutputStream()
                    val channel = Channels.newChannel(bodyStream)
                    buffer.readableByteBuffers().forEach { channel.write(it) }
                    log.info(
                        "{}: {} - {} : {}", "response", String(bodyStream.toByteArray()),
                        "header", delegate.headers.toSingleValueMap()
                    )
                })
    }
}
