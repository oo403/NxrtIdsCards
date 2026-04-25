package pl.sirox.nxrtidscards.service

import com.google.inject.Singleton
import java.util.UUID

@Singleton
class PlayerService {

    private val name: MutableMap<UUID, String> = mutableMapOf()
    private val surname: MutableMap<UUID, String> = mutableMapOf()
    private val age: MutableMap<UUID, Int> = mutableMapOf()

    fun getName(uuid: UUID): String? = name[uuid]
    fun getSurname(uuid: UUID): String? = surname[uuid]
    fun getAge(uuid: UUID): Int? = age[uuid]

    fun setName(uuid: UUID, name: String) {
        this.name[uuid] = name
    }
    fun setSurname(uuid: UUID, surname: String) {
        this.surname[uuid] = surname
    }
    fun setAge(uuid: UUID, age: Int) {
        this.age[uuid] = age
    }

}