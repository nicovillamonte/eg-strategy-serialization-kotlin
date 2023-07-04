package ar.edu.unsam.algo2.personas.DTO

import ar.edu.unsam.algo2.personas.Estudiante
import ar.edu.unsam.algo2.personas.Persona
import ar.edu.unsam.algo2.personas.Profesor
import ar.edu.unsam.algo2.personas.Tutor
import kotlin.reflect.KClass

data class PersonaDTO(
    var nombre: String,
    var apellido: String,
    var email: String,
    var type: String
) {}


val PersonaMap: Map<String, KClass<out Persona>> = mapOf(
    "Profesor" to Profesor::class,
    "Tutor" to Tutor::class,
    "Estudiante" to Estudiante::class
)