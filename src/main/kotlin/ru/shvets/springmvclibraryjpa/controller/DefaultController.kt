package ru.shvets.springmvclibraryjpa.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.02.2023 14:49
 */

@Controller
@RequestMapping
class DefaultController {

    @GetMapping()
    fun index(): String{
        return "index"
    }
}