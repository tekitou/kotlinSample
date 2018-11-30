package org.tekitou.kotlin.kotlinSample.controller

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping
class HelloWorldController {
    @GetMapping("hello")
    @ResponseBody
    fun hello(
        @RequestParam(
            value = "name",
            required = false,
            defaultValue = "A"
        ) name: String, model: Model
    ): String {
        logger.info("name={}", name)
        return name
    }

    companion object : KLogging()
}
