package ru.shvets.springmvclibraryjpa.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.jvm.Transient
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  02.02.2023 09:38
 */

@Entity
@Table(name = "books")
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @field:NotEmpty(message = "Наименование книги не должно быть пустым")
    @field:Size(min = 2, max = 50, message = "Наименование книги должно быть от 2 до 50 символов")
    @Column(name = "name", nullable = false)
    var name: String? = null

    @field:NotEmpty(message = "Имя автора не должно быть пустым")
    @field:Size(min = 2, max = 50, message = "Имя автора должно быть от 2 до 50 символов")
    @Column(name = "author", nullable = false)
    var author: String? = null

    @field:Min(value = 0, message = "Год написания (издания) книги должен быть больше 0")
    @Column(name = "year_of_publication", nullable = false)
    var year: Int? = null

    @Column(name = "took_at")
    @Temporal(TemporalType.TIMESTAMP)
    var tookAt: Date? = null

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    var reader: Person? = null

    constructor()
    constructor(id: Long?, name: String?, author: String?, year: Int?, tookAt: Date?, reader: Person?) {
        this.id = id
        this.name = name
        this.author = author
        this.year = year
        this.tookAt = tookAt
        this.reader = reader
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    fun isExpired(criteria: Int): Boolean {
        return if (this.tookAt != null) {
            val today = Date().time / 1000
            val took = this.tookAt!!.time / 1000
            val period = (today - took) / (60 * 60 * 24)
            return period > criteria
        } else false
    }
}