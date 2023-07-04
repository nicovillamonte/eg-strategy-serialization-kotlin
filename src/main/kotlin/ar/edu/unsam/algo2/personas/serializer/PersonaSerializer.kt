package ar.edu.unsam.algo2.personas.serializer

import ar.edu.unsam.algo2.personas.DTO.PersonaDTO
import ar.edu.unsam.algo2.personas.DTO.PersonaMap
import ar.edu.unsam.algo2.personas.Persona
import kotlin.reflect.KClass

/**
 * Este método es un ejemplo de cómo se puede hacer la conversion de un objeto de tipo
 * Persona a un DTO agregando el tipo de Persona que es en la materia.
 */
fun Persona.toDto(): PersonaDTO {
    return PersonaDTO(
        nombre = this.nombre,
        apellido = this.apellido,
        email = this.email,
        type = PersonaMap.filterValues { it == this::class }.keys.first()
    )
}

/**
 * Esta funcion es un ejemplo de cómo se puede hacer la conversion de un DTO a un objeto
 * de tipo Persona cuando sus subclases tienen un constructor vacio, lo que lo hace mas
 * sencillo.
 */
fun personaFromDto(personaDto: PersonaDTO): Persona? {
    val tipo: KClass<out Persona> = PersonaMap[personaDto.type]!!

    return tipo.constructors.firstOrNull()?.callBy(mapOf()).also {
        it?.nombre = personaDto.nombre
        it?.apellido = personaDto.apellido
    }
}

/**
 * Esta funcion es un ejemplo de cómo se puede hacer la conversion de un DTO a un objeto
 * de tipo Persona, en este caso, cuando sus subclases tienen constructor, a los cuales
 * debemos pasarle los parametros correspondientes antes de crear la instancia.
 */
fun personaFromDtoWithConstructor(personaDto: PersonaDTO): Persona? {
    val tipo: KClass<out Persona> = PersonaMap[personaDto.type]!!
    val constructor = tipo.constructors.firstOrNull()

    return constructor?.callBy(mapOf(
        constructor.parameters[0] to personaDto.nombre,
        constructor.parameters[1] to personaDto.apellido
    )).also {
        it?.email = personaDto.email
    }
}