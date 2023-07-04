package ar.edu.unsam.algo2.personas

interface Persona {
    var nombre: String
    var apellido: String
    var email: String

    fun setEamil(mail: String) {
        this.email = mail
    }

    fun presentarse(): String
}


class Estudiante(override var nombre: String, override var apellido: String) : Persona {
    override var email: String = ""

    override fun presentarse(): String {
        return "Soy $nombre y soy estudiante"
    }
}

class Tutor(override var nombre: String, override var apellido: String) : Persona {
    override var email: String = ""

    override fun presentarse(): String {
        return "Soy $nombre y soy tutor"
    }

}

class Profesor(override var nombre: String, override var apellido: String) : Persona {
    override var email: String = ""

    override fun presentarse(): String {
        return "Soy $nombre y soy profesor"
    }
}