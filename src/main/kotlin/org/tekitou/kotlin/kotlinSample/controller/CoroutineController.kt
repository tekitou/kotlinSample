package org.tekitou.kotlin.kotlinSample.controller

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("coroutine")
class CoroutineController {
    @GetMapping("global")
    fun coroutine(
        @RequestParam(
            name = "value",
            required = false,
            defaultValue = "v"
        ) value: String
    ): CompletableFuture<String> {
        val sb = StringBuilder()
        // top level coroutine
        GlobalScope.launch {
            delay(5000L)
            logger.info("global scope launch")
            sb.appendln("global scope launch")
        }
        logger.info("out of global scope")
        sb.appendln("out of global scope")
        runBlocking {
            delay(500L)
            logger.info("blocking")
            sb.appendln("blocking")
        }
        return CompletableFuture.completedFuture(sb.appendln(value).toString())
    }

    companion object : KLogging()
}