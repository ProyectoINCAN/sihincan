package pacientes

class CorreoElectronico {

    String direccion
    Boolean habilitado = true

    static constraints = {
        direccion size: 1..70, blank: false, email: true
    }
}
