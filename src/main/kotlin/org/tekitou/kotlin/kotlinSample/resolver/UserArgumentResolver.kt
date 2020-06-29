package org.tekitou.kotlin.kotlinSample.resolver

import mu.KLogging
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import org.tekitou.kotlin.kotlinSample.model.User
import reactor.core.publisher.Mono
import javax.inject.Named
import kotlin.reflect.full.isSubclassOf


@Named
class UserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return User::class.isSubclassOf(parameter.parameterType.kotlin)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        val user = exchange.getAttribute<User>(USER_ATTRIBUTE)
        logger.info("user:{}", user)
        return Mono.just(user as Any)
    }

    companion object : KLogging() {
        val USER_ATTRIBUTE = "userAttr"
    }
}