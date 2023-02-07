package ru.shvets.springmvclibraryjpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.shvets.springmvclibraryjpa.model.Book
import ru.shvets.springmvclibraryjpa.model.Person
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  05.02.2023 11:30
 */

@Repository
interface BookRepository: JpaRepository<Book, Long> {
    fun findByName(name: String): Optional<Book>
    fun findByNameContainingIgnoreCase(name: String): List<Book>?
}