package ru.shvets.springmvclibraryjpa.service

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.shvets.springmvclibraryjpa.model.Person
import ru.shvets.springmvclibraryjpa.repository.PersonRepository
import java.util.Optional

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  05.02.2023 11:36
 */

@Service
@Transactional(readOnly = true)
class PersonService(
    private val personRepository: PersonRepository
) {
    fun getAll(): List<Person> {
        return personRepository.findAll(Sort.by("year").ascending())
    }

    fun findById(id: Long): Optional<Person> {
        return personRepository.findById(id)
    }

    fun findByName(name: String): Optional<Person> {
        return personRepository.findByName(name)
    }

    @Transactional
    fun save(person: Person) {
        personRepository.save(person)
    }

    @Transactional
    fun update(id: Long, person: Person) {
        personRepository.save(person.apply {
            this.id = id
        })
    }

    @Transactional
    fun delete(id: Long) {
        personRepository.deleteById(id)
    }
}