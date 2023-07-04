package ar.edu.unsam.algo2.personas

import ar.edu.unsam.algo2.personas.DTO.PersonaDTO
import ar.edu.unsam.algo2.personas.serializer.personaFromDto
import ar.edu.unsam.algo2.personas.serializer.personaFromDtoWithConstructor
import ar.edu.unsam.algo2.personas.serializer.toDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.lang.IndexOutOfBoundsException

class PersonaSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Test Estudiante") {
        val juan: Persona = Estudiante("Juan", "Perez").apply {
            email = "juan.perez@gmail.com"
        }
        val juanDTO: PersonaDTO = juan.toDto()

        it("debe presentarse") {
            juan.presentarse() shouldBe "Soy Juan y soy estudiante"
        }

        it("Conversion de objeto Estudiante a DTO") {
            juanDTO.nombre shouldBe "Juan"
            juanDTO.apellido shouldBe "Perez"
            juanDTO.email shouldBe "juan.perez@gmail.com"
            juanDTO.type shouldBe "Estudiante"
        }

        it("Conversion de DTO a objeto Estudiante sin constructor"){
            val juanFromDto = personaFromDto(juanDTO)

            // En este caso el nombre seria "" porque no se le pasa al constructor
            // (Hacemos de cuenta que es un constructor vacio)

            // Verificamos que el tipo de la instancia sea Estudiante
            juanFromDto!!::class shouldBe Estudiante::class
        }

        it("Conversion de DTO a objeto Estudiante con constructor"){
            val juanFromDto = personaFromDtoWithConstructor(juanDTO)

            juanFromDto!!.nombre shouldBe "Juan"
            juanFromDto!!::class shouldBe Estudiante::class
        }
    }

    describe("Test Profesor") {
        val fernando = Profesor("Fernando", "Dodino")
        val fernandoDTO = fernando.toDto()

        it("debe presentarse") {
            fernando.presentarse() shouldBe "Soy Fernando y soy profesor"
        }

        it("Conversion de objeto Profesor a DTO") {
            fernandoDTO.nombre shouldBe "Fernando"
            fernandoDTO.apellido shouldBe "Dodino"
            fernandoDTO.type shouldBe "Profesor"
        }

        it("Conversion de DTO a objeto Profesor sin constructor"){
            val fernandoFromDto = personaFromDto(fernandoDTO)

            // En este caso el nombre seria "" porque no se le pasa al constructor
            // (Hacemos de cuenta que es un constructor vacio)

            // Verificamos que el tipo de la instancia sea Estudiante
            fernandoFromDto!!::class shouldBe Profesor::class
        }

        it("Conversion de DTO a objeto Profesor con constructor"){
            val fernandoFromDto = personaFromDtoWithConstructor(fernandoDTO)

            fernandoFromDto!!.nombre shouldBe "Fernando"
            fernandoFromDto!!::class shouldBe Profesor::class
        }
    }

    describe("Test Tutor") {
        val jorge: Persona = Tutor()
        val jorgeDTO: PersonaDTO = jorge.toDto()

        it("debe presentarse") {
            jorge.presentarse() shouldBe "Soy Jorge y soy tutor"
        }

        it("Conversion de objeto Tutor a DTO") {
            jorgeDTO.nombre shouldBe "Jorge"
            jorgeDTO.apellido shouldBe "Luis"
            jorgeDTO.type shouldBe "Tutor"
        }

        it("Conversion de DTO a objeto Tutor sin constructor"){
            val jorgeFromDto = personaFromDto(jorgeDTO)

            // En este caso el nombre es siempre "Jorge Luis" porque no tiene constructor la clase
            // y se le asigna por defecto estos valores
            jorgeFromDto!!.nombre shouldBe "Jorge"
            jorgeFromDto!!.apellido shouldBe "Luis"

            // Verificamos que el tipo de la instancia sea Estudiante
            jorgeFromDto!!::class shouldBe Tutor::class
        }

        it("Conversion de DTO a objeto Tutor con constructor deberia arrojar error"){
            // Debe arrojar una excepcion ya que Tutor tiene constructor vacio y se espera enviarle dos argumentos
            shouldThrow<IndexOutOfBoundsException> { //RuntimeException
                personaFromDtoWithConstructor(jorgeDTO)
            }
        }
    }

})