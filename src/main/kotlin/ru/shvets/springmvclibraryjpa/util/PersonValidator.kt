package ru.shvets.springmvclibraryjpa.util

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import ru.shvets.springmvclibraryjpa.model.Person
import ru.shvets.springmvclibraryjpa.service.PersonService

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.02.2023 23:24
 */

@Component
class PersonValidator(
    private val personService: PersonService
): Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return Person::class.java == clazz
    }

    override fun validate(target: Any, errors: Errors) {
        val person = target as Person
        if (personService.findByName(person.name!!).isPresent) {
            errors.rejectValue("name", "", "Это имя уже используется")
        }
    }
}