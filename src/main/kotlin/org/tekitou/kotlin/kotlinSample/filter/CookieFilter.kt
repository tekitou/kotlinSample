package org.tekitou.kotlin.kotlinSample.filter

import mu.KLogging
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.tekitou.kotlin.kotlinSample.model.User
import org.tekitou.kotlin.kotlinSample.resolver.UserArgumentResolver
import reactor.core.publisher.Mono
import javax.inject.Named

@Named
class CookieFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val cookie = exchange.request.cookies["cookieName"]?.first()
        exchange.attributes[UserArgumentResolver.USER_ATTRIBUTE] = User(cookie?.value ?: "new cookie")
        return chain.filter(exchange)
            .doOnRequest { v -> onRequest(exchange, v) }
            .doOnSuccess { onSuccess() }
            .doOnError { t -> doOnError(t) }
    }

    private fun onRequest(exchange: ServerWebExchange, value: Long) {
        logger.info("CookieFilter:doOnRequest:{}, {}", value, exchange)
    }

    private fun onSuccess() {
        logger.info("CookieFilter:doOnSuccess")
    }

    private fun doOnError(t: Throwable) {
        logger.error("CookieFilter:error", t)
    }

    companion object : KLogging()
}
