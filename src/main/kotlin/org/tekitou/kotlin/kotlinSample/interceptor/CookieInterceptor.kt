package org.tekitou.kotlin.kotlinSample.interceptor

import mu.KLogging
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.util.WebUtils
import org.tekitou.kotlin.kotlinSample.model.User
import org.tekitou.kotlin.kotlinSample.resolver.UserArgumentResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("preHandle")
        val cookie = WebUtils.getCookie(request, "cookieName")
        request.setAttribute(UserArgumentResolver.USER_ATTRIBUTE, User(cookie?.value ?: "new cookie"))
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info("postHandle")
        super.postHandle(request, response, handler, modelAndView)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        logger.info("afterCompletion")
        super.afterCompletion(request, response, handler, ex)
    }

    companion object : KLogging()
}