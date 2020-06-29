package org.tekitou.kotlin.kotlinSample.controller

import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.Timer
import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.tekitou.kotlin.kotlinSample.model.User
import org.tekitou.kotlin.kotlinSample.settings.HelloSettings

@Controller
@RequestMapping
class HelloWorldController(meterRegistry: MeterRegistry, helloSettings: HelloSettings) {
    private var helloSettings: HelloSettings = helloSettings
    var counter: Counter = meterRegistry.counter("controller_counter", Tags.of("name", "hello"))
    var timer: Timer = meterRegistry.timer("controller_timer", Tags.of("name", "hello"))

    @Timed
    @GetMapping("hello")
    @ResponseBody
    fun hello(
        @RequestParam(
            value = "name",
                required = false
        ) name: String?, user: User, model: Model
    ): String {
        timer.record { Thread.sleep(1000) }
        logger.info("name=${name}, user=${user}")
        counter.increment()
        return name ?: user.name ?: helloSettings.name
    }

    companion object : KLogging()
}
