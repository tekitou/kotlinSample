package org.tekitou.kotlin.kotlinSample.context

import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.security.web.server.csrf.DefaultCsrfToken
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.SecureRandom
import java.util.function.Supplier
import kotlin.random.asKotlinRandom


@Configuration
@EnableWebFluxSecurity
class WebfluxSecurityConfiguration {
    @Bean
    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http
                .csrf()
                .csrfTokenRepository(CustomCsrfTokenRepository())
                .requireCsrfProtectionMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/hello"))
                .and()
                .headers().frameOptions().disable()
                .cache().disable()
                .and()
                .authorizeExchange()
                .matchers(ServerWebExchangeMatchers.pathMatchers("/hello/**"))
                .permitAll()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .build()
    }
}

class CustomCsrfTokenRepository : ServerCsrfTokenRepository {

    override fun loadToken(exchange: ServerWebExchange?): Mono<CsrfToken> {
        return Mono.defer {
            val csrfToken = exchange?.request?.queryParams?.getFirst(CSRF_PARAMETER_NAME)
            logger.info("load token:${csrfToken}");
            Mono.just(generateTokenInternal(csrfToken))
        }.doOnNext { token -> logger.info("loadedToken:${token}") }
    }

    override fun saveToken(exchange: ServerWebExchange?, token: CsrfToken?): Mono<Void> {
        return Mono.defer<Void> {
            logger.info("save token:${token}")
            Mono.empty()
        }
    }

    override fun generateToken(exchange: ServerWebExchange?): Mono<CsrfToken> {
        return Mono.defer(Supplier { Mono.just(generateTokenInternal(null)) })
                .doOnNext { token -> logger.info("generatedToken:${token}") }
    }

    private fun generateTokenInternal(token: String?): CsrfToken {
        logger.info("generateTokenInternal:${token}")
        return DefaultCsrfToken("X-CSRF-TOKEN", CSRF_PARAMETER_NAME, token
                ?: SecureRandom().asKotlinRandom().nextLong().toString()) as CsrfToken
    }

    companion object : KLogging() {
        private const val CSRF_PARAMETER_NAME = "_csrf"
    }
}
