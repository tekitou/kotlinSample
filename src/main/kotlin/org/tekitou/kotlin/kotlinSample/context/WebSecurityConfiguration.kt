package org.tekitou.kotlin.kotlinSample.context

import mu.KLogging
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.DefaultCsrfToken
import org.springframework.security.web.util.matcher.RequestMatcher
import java.security.SecureRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.random.asKotlinRandom

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Override
    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
            ?.antMatchers("/hello/**")?.permitAll()
            ?.and()
            ?.csrf()
            ?.requireCsrfProtectionMatcher(RequestMatcher { r -> r.method == HttpMethod.POST.name && r.requestURI == "/hello" })
            ?.csrfTokenRepository(CustomCsrfTokenRepository())
    }

    @Override
    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }
}

class CustomCsrfTokenRepository : CsrfTokenRepository {
    override fun loadToken(request: HttpServletRequest?): CsrfToken {
        logger.info("load token:${request?.getParameter("_csrf")}")
        return generateToken(request)
    }

    override fun saveToken(token: CsrfToken?, request: HttpServletRequest?, response: HttpServletResponse?) {
        logger.info("save token:$token")
    }

    override fun generateToken(request: HttpServletRequest?): CsrfToken {
        val nextLong = SecureRandom().asKotlinRandom().nextLong();
        logger.info("generateToken:$nextLong")
        return DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "token")
    }

    companion object : KLogging()
}
