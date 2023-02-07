package ru.shvets.springmvclibraryjpa.controller

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import ru.shvets.springmvclibraryjpa.model.Book
import ru.shvets.springmvclibraryjpa.model.Person
import ru.shvets.springmvclibraryjpa.service.BookService
import ru.shvets.springmvclibraryjpa.service.PersonService
import ru.shvets.springmvclibraryjpa.util.BookValidator

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.02.2023 14:57
 */

@Controller
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
    private val personService: PersonService,
    private val bookValidator: BookValidator
) {

    @GetMapping()
    fun index(
        model: Model,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "books_per_page", defaultValue = "3") booksPerPage: Int,
        @RequestParam(name = "sort_by_year", defaultValue = "true") sortByYear: Boolean
    ): String {
        model.addAttribute("books", bookService.getAllByPage(page, booksPerPage, sortByYear).get())
        return "/books/index"
    }

    @GetMapping("/find")
    fun search(
//        model: Model,
//        @RequestParam(name = "param", defaultValue = "") book: String,
//        redirectAttributes: RedirectAttributes
    ): String {
//        model.addAttribute("book", book)
        return "/books/search"
    }

    @GetMapping("/search")
    fun result(
        model: Model,
        @RequestParam("param") book: String
    ): String {
        model.addAttribute("result", bookService.search(book))
        return "/books/result"
    }

    @GetMapping("/add")
    fun addBook(@ModelAttribute("book") book: Book): String {
        return "/books/new"
    }

    @PostMapping()
    fun addBook(
        @ModelAttribute("book") @Valid book: Book,
        bindingResult: BindingResult
    ): String {
        bookValidator.validate(book, bindingResult)

        if (bindingResult.hasErrors()) {
            return "/books/new"
        }

        bookService.save(book)
        return "redirect:/books"
    }

    @GetMapping("/{id}")
    fun showBook(
        model: Model,
        @PathVariable("id") id: Long,
        @ModelAttribute("personTo") person: Person
    ): String {
        model.addAttribute("book", bookService.findById(id).get())
        model.addAttribute("person", bookService.findById(id).get().reader)
        model.addAttribute("people", personService.getAll())
        return "/books/show"
    }

    //    @PatchMapping("/{id}/free")
    @PostMapping("/{id}/free")
    fun freeBook(
        @PathVariable("id") id: Long
    ): String {
        bookService.free(id)
        return "redirect:/books/{id}"
    }

    //    @PatchMapping("/{id}/take")
    @PostMapping("/{id}/take")
    fun takeBook(
        @PathVariable("id") id: Long,
        @ModelAttribute("personTo") person: Person
    ): String {
        bookService.take(id, person)
        return "redirect:/books/{id}"
    }

    @GetMapping("/{id}/edit")
    fun editBook(model: Model, @PathVariable("id") id: Long): String {
        model.addAttribute("book", bookService.findById(id).get())
        return "/books/edit"
    }

    //    @PatchMapping("/{id}")
    @PostMapping("/{id}/edit")
    fun updateBook(
        @PathVariable("id") id: Long,
        @ModelAttribute("book") @Valid book: Book,
        bindingResult: BindingResult
    ): String {
//        bookValidator.validate(book, bindingResult)

        if (bindingResult.hasErrors()) {
            return "/books/edit"
        }
        bookService.update(id, book)
        return "redirect:/books"
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    fun deleteBook(@PathVariable("id") id: Long): String {
        bookService.delete(id)
        return "redirect:/books"
    }
}