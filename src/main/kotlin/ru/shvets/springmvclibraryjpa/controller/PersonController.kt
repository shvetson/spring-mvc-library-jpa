package ru.shvets.springmvclibraryjpa.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.shvets.springmvclibraryjpa.model.Person
import ru.shvets.springmvclibraryjpa.service.PersonService
import ru.shvets.springmvclibraryjpa.util.PersonValidator

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.02.2023 09:40
 */

@Controller
@RequestMapping("/people")
class PersonController(
    private val personService: PersonService,
    private val personValidator: PersonValidator
) {

    @GetMapping()
    fun index(model: Model): String {
        model.addAttribute("people", personService.getAll())
        return "/people/index"
    }

    @GetMapping("/add")
    fun addPerson(@ModelAttribute("person") person: Person): String {
        return "/people/new"
    }

    @PostMapping()
    fun addPerson(
        @ModelAttribute("person") @Valid person: Person,
        bindingResult: BindingResult
    ): String {
        personValidator.validate(person, bindingResult)

        if (bindingResult.hasErrors()) {
            return "people/new"
        }

        personService.save(person)
        return "redirect:/people"
    }

    @GetMapping("/{id}")
    fun showPerson(model: Model, @PathVariable("id") id: Long): String {
        model.addAttribute("person", personService.findById(id).get())
        model.addAttribute("books", personService.findById(id).get().books)
        return "/people/show"
    }

    @GetMapping("/{id}/edit")
    fun editPerson(model: Model, @PathVariable("id") id: Long): String {
        model.addAttribute("person", personService.findById(id).get())
        return "/people/edit"
    }

//    @PatchMapping("/{id}")
    @PostMapping("/{id}/edit")
    fun updatePerson(
        @PathVariable("id") id: Long,
        @ModelAttribute("person") @Valid person: Person,
        bindingResult: BindingResult
    ): String {
//        personValidator.validate(person, bindingResult)

        if (bindingResult.hasErrors()) {
            return "people/edit"
        }

        personService.update(id, person)
        return "redirect:/people"
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: Long): String {
        personService.delete(id)
        return "redirect:/people"
    }
}