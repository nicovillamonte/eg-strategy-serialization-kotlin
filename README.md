# Ejercicio Personas - Serializacion en patrón strategy

Ejercicio con fines educativos para explicar la serializacion de objetos al utilizar el patrón strategy. Se verá:

* Cómo construir un mapa para serializar objetos con el patrón strategy
* Cómo convertir de un objeto a un DTO
* Cómo convertir un DTO a un objeto

## Dominio

Nos interesa modelar personas dentro de una materia (La cual no se desarrollará).
Las personas pueden ser de tres tipos: 

- Alumnos
- Tutores
- Profesores

Cada una podrá presentarse a sí misma de una manera diferente dependiendo de que tipo de persona sean dentro de la materia.

## Serializador

Serializaremos a las personas en dos direcciones. Para ella debemos tener un mapa o diccionario de los tipos de personas referenciados a cada una de sus clases. Para ello, el mapa debe tener como clave un String o un caracter si se quiere con el nombre del tipo en cuestión y con un valor de `KClass`, es decir de una _clase de Kotlin_ que pertenezca a un tipo covariante de `Persona`. Esto último significa que cualquier clase que sea un subtipo de Persona se puede utilizar como valor del mapa en este caso y se marca con la palabra clave `out`.

``` kotlin
val PersonaMap: Map<String, KClass<out Persona>> = mapOf(
    "Profesor" to Profesor::class,
    "Tutor" to Tutor::class,
    "Estudiante" to Estudiante::class
)
```

### Objeto a DTO
La primera conversión se realizará de un objeto de tipo persona a un DTO (Data Transfer Object). Para lo que debemos agregarle el tipo de persona utilizando el mapa creado anteriormente:

``` kotlin
fun Persona.toDto(): PersonaDTO {
    return PersonaDTO(
        nombre = this.nombre,
        apellido = this.apellido,
        email = this.email,
        type = PersonaMap.filterValues { it == this::class }.keys.first()
    )
}
```

### DTO a Objeto con constructor vacío

Cuando obtenemos un DTO y queremos utilizarlo en nuestro software, debemos volver a convertirlo en un objeto de la clase correspondiente al tipo de persona. En principio, hagamos de cuenta que el objeto al que queremos serializar tiene un constructor va cío:

``` kotlin
fun personaFromDto(personaDto: PersonaDTO): Persona? {
    val tipo: KClass<out Persona> = PersonaMap[personaDto.type]!!

    return tipo.constructors.firstOrNull()?.callBy(mapOf()).also {
        it?.nombre = personaDto.nombre
        it?.apellido = personaDto.apellido
    }
}
```
Lo que estamos haciendo es, en un principio, obtener el tipo de persona del dto, para luego hacernos de sus constructores, elegir el primero (el único existenete) y hacer una llamada del mismo con sus argumentos vacíos, lo que devolverá la instancia de la clase correspondiente y se le podrán realizar los cambios necesarios con el método `also` o `apply`.

En caso de que no exista el constructor devolverá un `null`.

### DTO a Objeto con parametros en el constructor

Cuando el constructor no es un constructor vacío, se deben pasar los parametros obligatorios en la llamada al mismo, lo que se realiza de la siguiente manera:

``` kotlin
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
```

Es importante en este caso mantener el mismo orden en el que se encuentran definidos los parametros del constructor.
