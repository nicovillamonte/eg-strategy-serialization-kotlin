package ar.edu.unsam.algo2.personas.serializer

import ar.edu.unsam.algo2.personas.DTO.PersonaDTO
import ar.edu.unsam.algo2.personas.DTO.PersonaMap
import ar.edu.unsam.algo2.personas.Persona
import kotlin.reflect.KClass

fun Persona.toDto(): PersonaDTO {
    return PersonaDTO(
        nombre = this.nombre,
        apellido = this.apellido,
        email = this.email,
        type = PersonaMap.filterValues { it == this::class }.keys.first()
    )
}

fun personaFromDto(personaDto: PersonaDTO): Persona {
    val tipo: KClass<out Persona> = PersonaMap[personaDto.type]!!
    return tipo.java.getDeclaredConstructor().newInstance().also {
        it.nombre = personaDto.nombre
        it.apellido = personaDto.apellido
        it.email = personaDto.email
    }
}