package ru.shvets.springmvclibraryjpa.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.02.2023 09:35
 */

@Entity
@Table(name ="people")
class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    var id: Long? = null

    @field:NotEmpty(message = "Имя не должно быть пустым")
    @field:Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов")
    @Column(name="name", nullable = false)
    var name: String? = null

    @field:Min(value = 1900, message = "Год рождения должен быть больше 1900")
    @Column(name="year_of_birth", nullable = false)
    var year: Int = 0

    @OneToMany(mappedBy = "reader")
    var books: List<Book>? = null

    constructor()
    constructor(id: Long?, name: String?, year: Int) {
        this.id = id
        this.name = name
        this.year = year
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}