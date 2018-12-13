package org.tekitou.kotlin.kotlinSample.resolver

import mu.KLogging
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.tekitou.kotlin.kotlinSample.model.User
import javax.servlet.http.HttpServletRequest
import kotlin.reflect.full.isSubclassOf


class UserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return User::class.isSubclassOf(parameter.parameterType::class)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val user = request?.getAttribute(USER_ATTRIBUTE)
        logger.info("user:{}", user)
        return user
    }


    companion object : KLogging() {
        val USER_ATTRIBUTE = "userAttr"
    }
}