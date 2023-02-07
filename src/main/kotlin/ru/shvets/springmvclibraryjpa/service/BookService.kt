package ru.shvets.springmvclibraryjpa.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shvets.springmvclibraryjpa.model.Book
import ru.shvets.springmvclibraryjpa.model.Person
import ru.shvets.springmvclibraryjpa.repository.BookRepository
import ru.shvets.springmvclibraryjpa.repository.PersonRepository
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  05.02.2023 11:35
 */

@Service
@Transactional(readOnly = true)
class BookService(
    private val bookRepository: BookRepository,
    private val personRepository: PersonRepository
) {
    fun getAll(): List<Book> {
        return bookRepository
            .findAll(Sort.by("year").ascending())
    }

    fun getAllByPage(page: Int, booksPerPage: Int, sortByYear: Boolean): Page<Book> {
        return bookRepository
            .findAll(PageRequest.of(page, booksPerPage, if (sortByYear) Sort.by("year") else Sort.unsorted()))
    }

    fun findById(id: Long): Optional<Book> {
        return bookRepository.findById(id)
    }

    fun findByName(name: String): Optional<Book> {
        return bookRepository.findByName(name)
    }

    fun search(name: String): List<Book>? {
        return bookRepository.findByNameContainingIgnoreCase(name)
    }

    @Transactional
    fun save(book: Book) {
        bookRepository.save(book)
    }

    @Transactional
    fun update(id: Long, book: Book) {
        bookRepository.save(book.apply { this.id = id })
    }

    @Transactional
    fun delete(id: Long) {
        bookRepository.deleteById(id)
    }

    @Transactional
    fun free(id: Long) {
        val book: Book = findById(id).get()
        book.apply {
            this.reader = null
            this.tookAt = null
        }
        bookRepository.save(book)
    }

    @Transactional
    fun take(id: Long, person: Person) {
        val book: Book = findById(id).get()
        book.apply {
            this.reader = personRepository.findById(person.id!!).get()
            this.tookAt = Date()
        }
        bookRepository.save(book)
    }
}