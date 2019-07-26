package org.tekitou.kotlin.kotlinSample.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.lang.NonNull
import org.springframework.validation.annotation.Validated
import javax.inject.Named

@Named
@Validated
@ConfigurationProperties(prefix = "hello")
class HelloSettings {
    @NonNull
    lateinit var name: String
}
