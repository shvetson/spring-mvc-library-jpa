package ru.shvets.springmvclibraryjpa.util

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import ru.shvets.springmvclibraryjpa.model.Book
import ru.shvets.springmvclibraryjpa.service.BookService

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  01.02.2023 23:24
 */

@Component
class BookValidator(
    private val bookService: BookService
): Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return Book::class.java == clazz
    }

    override fun validate(target: Any, errors: Errors) {
        val book = target as Book
        if (bookService.findByName(book.name!!).isPresent) {
            errors.rejectValue("name", "", "Это книга уже есть")
        }
    }
}