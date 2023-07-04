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
