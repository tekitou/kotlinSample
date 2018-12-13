package org.tekitou.kotlin.kotlinSample.context

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.tekitou.kotlin.kotlinSample.interceptor.CookieInterceptor
import org.tekitou.kotlin.kotlinSample.resolver.UserArgumentResolver

@Configuration
@EnableWebMvc
class WebMvcConfig : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(CookieInterceptor())
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(UserArgumentResolver())
    }
}
