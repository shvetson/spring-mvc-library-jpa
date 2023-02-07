package ru.shvets.springmvclibraryjpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.shvets.springmvclibraryjpa.model.Person
import java.util.Optional

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  05.02.2023 11:33
 */

@Repository
interface PersonRepository: JpaRepository<Person, Long> {
    fun findByName(name: String): Optional<Person>
}